package uk.ac.ebi.pride.tools.mgf_parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.tools.braf.BufferedRandomAccessFile;
import uk.ac.ebi.pride.tools.jmzreader.JMzReader;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.jmzreader.model.IndexElement;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;
import uk.ac.ebi.pride.tools.jmzreader.model.impl.IndexElementImpl;
import uk.ac.ebi.pride.tools.mgf_parser.model.Ms2Query;
import uk.ac.ebi.pride.tools.mgf_parser.model.PmfQuery;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a MgfFile.
 *
 * @author jg
 */
public class MgfFile implements JMzReader {

    public static final Logger logger = LoggerFactory.getLogger(MgfFile.class);

    public enum FragmentToleranceUnits {DA, MMU}

    public enum MassType {MONOISOTOPIC, AVERAGE}

    public enum SearchType {
        PMF("PMF"), SQ("SQ"), MIS("MIS");
        private String name;

        SearchType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum ReportType {
        PROTEIN("protein"), PEPTIDE("peptide"), ARCHIVE("archive"),
        CONCISE("concise"), SELECT("select"), UNASSIGNED("unassigned");

        private String name;

        ReportType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum PeptideToleranceUnit {
        PERCENT("%"), PPM("ppm"), MMU("mmu"), DA("Da");

        private String name;

        PeptideToleranceUnit(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Regex to capture mgf comments in mgf files.
     */
    public static final String mgfCommentRegex = "^[#;!/].*";
    /**
     * Regex to recognize a attribute and extract its name and value
     */
    public static final Pattern attributePattern = Pattern.compile("(\\w+)\\[?\\d?\\]?\\s*=(.*)\\s*");

    /**
     * ---------- OPTIONAL PARAMETERS THAT CAN BE SET IN A MGF FILE --------------
     */
    private List<String> accessions;
    private String charge;
    private String enzyme;
    private String searchTitle;
    private String precursorRemoval;
    private String database;
    private Boolean performDecoySearch;
    private Boolean isErrorTolerant;
    private String format;
    private List<Integer> frames;
    private String instrument;
    private String variableModifications;
    private Double fragmentIonTolerance;
    private FragmentToleranceUnits fragmentIonToleranceUnit;
    private MassType massType;
    private String fixedMofications;
    private Double peptideIsotopeError;
    private Integer partials;
    private Double precursor;
    private String quantitation;
    private String maxHitsToReport;
    private ReportType reportType;
    private SearchType searchType;
    private String proteinMass;
    private String taxonomy;
    private Double peptideMassTolerance;
    private PeptideToleranceUnit peptideMassToleranceUnit;
    private List<String> userParameter;
    private String userMail;
    private String userName;

    /**
     * The source file if this object was generated from a file
     */
    private File sourceFile;
    /**
     * Position from the "BEGIN IONS" fields in the file to
     * the "END IONS"
     */
    private List<IndexElement> index = new ArrayList<>();
    /**
     * PMF Queries
     */
    private List<PmfQuery> pmfQueries = new ArrayList<>();
    /**
     * MS2 queries. The index of the query in the file as key
     * and the respective query as value.
     */
    private HashMap<Integer, Ms2Query> ms2Queries = new HashMap<>();
    /**
     * Indicates whether the cache should be used
     */
    private boolean useCache = false;
    /**
     * Indicates whether the parser will fail on unknown tags
     */
    private boolean allowCustomTags = DEFAULT_ALLOW_CUSTOM_TAGS;
    private static final boolean DEFAULT_ALLOW_CUSTOM_TAGS = false;
    /**
     * If this option is set, comments are not removed
     * from MGF files. This speeds up parsing considerably
     * but causes problems if MGF files do contain comments.
     */
    private boolean disableCommentSupport = false;

    /**
     * Process a given attribute line and saves the variable
     * in the respective member variable.
     *
     * @param name  The attribute's name
     * @param value The attribute's value
     */
    private void processAttribute(String name, String value) {
        if ("ACCESSION".equals(name)) {
            // remove all "
            value = value.replace("\"", "");
            // extract the accessions
            String[] accs = value.split(",");
            // save the accessions
            accessions = Arrays.asList(accs);
        } else if ("CHARGE".equals(name)) {
            charge = value;
        } else if ("CLE".equals(name)) {
            enzyme = value;
        } else if ("COM".equals(name)) {
            searchTitle = value;
        } else if ("CUTOUT".equals(name)) {
            precursorRemoval = value;
        } else if ("DB".equals(name)) {
            database = value;
        } else if ("DECOY".equals(name)) {
            performDecoySearch = value.equals("1");
        } else if ("ERRORTOLERANT".equals(name)) {
            isErrorTolerant = value.equals("1");
        } else if ("FORMAT".equals(name)) {
            format = value;
        } else if ("FRAMES".equals(name)) {
            String[] frames = value.split(",");
            this.frames = new ArrayList<>();
            for (String frame : frames)
                this.frames.add(Integer.parseInt(frame));
        } else if ("INSTRUMENT".equals(name)) {
            instrument = value;
        } else if ("IT_MODS".equals(name)) {
            variableModifications = value;
        } else if ("ITOL".equals(name)) {
            fragmentIonTolerance = Double.parseDouble(value);
        } else if ("ITOLU".equals(name)) {
            fragmentIonToleranceUnit = (value.equals("mmu")) ? FragmentToleranceUnits.MMU : FragmentToleranceUnits.DA;
        } else if ("MASS".equals(name)) {
            massType = (value.equals("Average")) ? MassType.AVERAGE : MassType.MONOISOTOPIC;
        } else if ("MODS".equals(name)) {
            fixedMofications = value;
        } else if ("PEP_ISOTOPE_ERROR".equals(name)) {
            peptideIsotopeError = Double.parseDouble(value);
        } else if ("PFA".equals(name)) {
            partials = Integer.parseInt(value);
        } else if ("PRECURSOR".equals(name)) {
            precursor = Double.parseDouble(value);
        } else if ("QUANTITATION".equals(name)) {
            quantitation = value;
        } else if ("REPORT".equals(name)) {
            maxHitsToReport = value;
        } else if ("REPTYPE".equals(name)) {
            reportType = null;

            if ("protein".equalsIgnoreCase(value)) reportType = ReportType.PROTEIN;
            if ("peptide".equalsIgnoreCase(value)) reportType = ReportType.PEPTIDE;
            if ("archive".equalsIgnoreCase(value)) reportType = ReportType.ARCHIVE;
            if ("concise".equalsIgnoreCase(value)) reportType = ReportType.CONCISE;
            if ("select".equalsIgnoreCase(value)) reportType = ReportType.SELECT;
            if ("unassigned".equalsIgnoreCase(value)) reportType = ReportType.UNASSIGNED;

            if (reportType == null)
                throw new IllegalStateException("Invalid report type set");
        } else if ("SEARCH".equals(name)) {
            searchType = null;

            if ("PMF".equalsIgnoreCase(value)) searchType = SearchType.PMF;
            if ("SQ".equalsIgnoreCase(value)) searchType = SearchType.SQ;
            if ("MIS".equalsIgnoreCase(value)) searchType = SearchType.MIS;

            if (searchType == null)
                throw new IllegalStateException("Invalid search type set");
        } else if ("SEG".equals(name)) {
            proteinMass = value;
        } else if ("TAXONOMY".equals(name)) {
            taxonomy = value;
        } else if ("TOL".equals(name)) {
            peptideMassTolerance = Double.parseDouble(value);
        } else if ("TOLU".equals(name)) {
            peptideMassToleranceUnit = null;

            if ("%".equalsIgnoreCase(value)) peptideMassToleranceUnit = PeptideToleranceUnit.PERCENT;
            if ("ppm".equalsIgnoreCase(value)) peptideMassToleranceUnit = PeptideToleranceUnit.PPM;
            if ("mmu".equalsIgnoreCase(value)) peptideMassToleranceUnit = PeptideToleranceUnit.MMU;
            if ("Da".equalsIgnoreCase(value)) peptideMassToleranceUnit = PeptideToleranceUnit.DA;

            if (peptideMassToleranceUnit == null)
                throw new IllegalStateException("Invalid peptide mass tolerance unit set");
        } else if (name.startsWith("USER")) {
            if ("USEREMAIL".equals(name)) {
                userMail = value;
            } else if ("USERNAME".equals(name)) {
                userName = value;
            } else {
                if (userParameter == null) userParameter = new ArrayList<>();
                userParameter.add(value);
            }
        } else {
            if (!allowCustomTags) {
                throw new IllegalStateException("Unknown attribute '" + name + "' encountered");
            } else {
                logger.warn("Ignored custom tag: " + name);
            }
        }
    }

    public static Spectrum getIndexedSpectrum(File sourcefile, IndexElement indexElement, boolean disableCommentSupport) throws JMzReaderException {
        // make sure the parameters are set
        if (sourcefile == null)
            throw new JMzReaderException("Required parameter sourcefile must not be null.");
        if (indexElement == null)
            throw new JMzReaderException("Required parameter indexElement must not be null.");

        // load the spectrum from the file
        return loadIndexedQueryFromFile(sourcefile, indexElement, 1, disableCommentSupport);
    }

    /**
     * Loads a (MS2) spectrum from an MGF file who's
     * position in the file is already known.
     *
     * @param sourcefile   The MGF file to load the spectrum from.
     * @param indexElement IndexElement specifying the position of the MS2 spectrum in the MGF file.
     * @return The unmarshalled spectrum object.
     * @throws JMzReaderException
     */
    public static Spectrum getIndexedSpectrum(File sourcefile, IndexElement indexElement) throws JMzReaderException {
        return loadIndexedQueryFromFile(sourcefile, indexElement, 1, false);
    }

    /**
     * Default constructor generating an empty mgf file object.
     */
    public MgfFile() {

    }

    /**
     * Creates the mgf file object from an existing
     * mgf file.
     *
     * @param file The mgf file
     * @throws JMzReaderException
     */
    public MgfFile(File file) throws JMzReaderException {
        this(file, false);
    }

    /**
     * Creates the mgf file object from an existing
     * mgf file.
     *
     * @param file            The mgf file
     * @param allowCustomTags Indicates if the parser should throw an exception when encountering non-standard tags
     * @throws JMzReaderException
     */
    public MgfFile(File file, boolean allowCustomTags) throws JMzReaderException {

        setAllowCustomTags(allowCustomTags);

        // open the file
        try {
            // save the file
            sourceFile = file;

            BufferedRandomAccessFile braf = new BufferedRandomAccessFile(sourceFile.getAbsolutePath(), "r", 1024 * 100);

            // process the file line by line
            String line;
            boolean inHeader = true; // indicates whether we're still in the attribute section
            boolean inMs2 = false;
            long lastPosition = 0;
            long beginIonsIndex = 0; // the index where the last "BEGIN IONS" was encountered

            while ((line = braf.getNextLine()) != null) {

                // remove any comments from the line (if the line will be processed)
                if (!inMs2)
                    line = line.replaceAll(mgfCommentRegex, "").trim();

                // ignore empty lines
                if (line.length() < 1) {

                    //always update file pointer before continue
                    lastPosition = braf.getFilePointer();
                    continue;
                }

                // check if a ms2 block started
                if (!inMs2 && line.contains("BEGIN IONS")) {
                    // save the offset of the spectrum
                    beginIonsIndex = lastPosition;
                    inMs2 = true;
                }
                if (inMs2 && line.contains("END IONS")) {
                    inMs2 = false;

                    //index.add(new IndexElement(beginIonsIndex, reader.getFilePointer()));
                    int size = (int) (braf.getFilePointer() - beginIonsIndex);
                    index.add(new IndexElementImpl(beginIonsIndex, size));

                    //always update file pointer before continue
                    lastPosition = braf.getFilePointer();
                    continue;
                }

                if (inMs2) {
                    //always update file pointer before continue
                    lastPosition = braf.getFilePointer();
                    continue;
                }

                // check if it's an attribute line
                if (inHeader && line.contains("=")) {
                    Matcher matcher = attributePattern.matcher(line);

                    if (!matcher.find())
                        throw new JMzReaderException("Malformatted attribute encountered");
                    if (matcher.groupCount() != 2)
                        throw new JMzReaderException("Malformatted attribute encountered");

                    // process the attribute
                    processAttribute(matcher.group(1), matcher.group(2));

                    //always update file pointer before continue
                    lastPosition = braf.getFilePointer();
                    continue;

                } else if (!inHeader && line.contains("=")) {
                    throw new JMzReaderException("Attribute encountered at illegal position. Attributes must all be at the beginning of the file");
                } else {
                    inHeader = false;
                }

                // if we're not in the header and it's not a ms2 it must be a pmf query
                if (!inHeader) {
                    PmfQuery query = new PmfQuery(line);
                    pmfQueries.add(query);
                }

                //always update file pointer before continue
                lastPosition = braf.getFilePointer();
            }

            braf.close();
        } catch (FileNotFoundException e) {
            throw new JMzReaderException("MgfFile does not exist.", e);
        } catch (IOException e) {
            throw new JMzReaderException("Failed to read from mgf file.", e);
        }
    }

    /**
     * Creates the mgf file object from an existing
     * mgf file with a pre-parsed index of ms2 spectra.
     * The index must hold the offsets of all "BEGIN IONS"
     * lines in the order they appear in the file.
     *
     * @param file  The mgf file
     * @param index An ArrayList holding the
     * @param allowCustomTags If set to true the parser will not fail if
     *                        unsupported tags are encountered in the MGF file.
     *                        Otherwise an Exception is thrown.
     * @throws JMzReaderException
     */
    public MgfFile(File file, List<IndexElement> index, boolean allowCustomTags) throws JMzReaderException {
        setAllowCustomTags(allowCustomTags);

        // open the file
        try {
            // save the file
            sourceFile = file;
            // save the index
            this.index = index;

            BufferedRandomAccessFile reader = new BufferedRandomAccessFile(sourceFile, "r", 1024 * 1000);

            // process the file line by line
            String line;
            boolean inHeader = true; // indicates whether we're still in the attribute section

            while ((line = reader.getNextLine()) != null) {
                // remove any comments from the line (if the line will be processed)
                line = line.replaceAll(mgfCommentRegex, "").trim();

                // ignore empty lines
                if (line.length() < 1) {
                    continue;
                }

                // break the loop as soon as a ms2 query is encountered
                if (line.contains("BEGIN IONS"))
                    break;

                // check if it's an attribute line
                if (inHeader && line.contains("=")) {
                    Matcher matcher = attributePattern.matcher(line);

                    if (!matcher.find())
                        throw new JMzReaderException("Malformatted attribute encountered");
                    if (matcher.groupCount() != 2)
                        throw new JMzReaderException("Malformatted attribute encountered");

                    // process the attribute
                    processAttribute(matcher.group(1), matcher.group(2));

                    continue;
                } else if (!inHeader && line.contains("=")) {
                    throw new JMzReaderException("Attribute encountered at illegal position. Attributes must all be at the beginning of the file");
                } else {
                    inHeader = false;
                }

                // if we're not in the header and it's not a ms2 it must be a pmf query
                if (!inHeader) {
                    PmfQuery query = new PmfQuery(line);
                    pmfQueries.add(query);
                }
            }

            reader.close();
        } catch (FileNotFoundException e) {
            throw new JMzReaderException("MgfFile does not exist.", e);
        } catch (IOException e) {
            throw new JMzReaderException("Failed to read from mgf file.", e);
        }
    }

    /**
     * Creates the mgf file object from an existing
     * mgf file with a pre-parsed index of ms2 spectra.
     * The index must hold the offsets of all "BEGIN IONS"
     * lines in the order they appear in the file.
     *
     * @param file  The mgf file
     * @param index An ArrayList holding the
     * @throws JMzReaderException
     */
    public MgfFile(File file, List<IndexElement> index) throws JMzReaderException {
        this(file, index, DEFAULT_ALLOW_CUSTOM_TAGS);
    }

    public List<String> getAccessions() {
        return accessions;
    }

    public void setAccessions(List<String> accessions) {
        this.accessions = accessions;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getEnzyme() {
        return enzyme;
    }

    public void setEnzyme(String enzyme) {
        this.enzyme = enzyme;
    }

    public String getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

    public String getPrecursorRemoval() {
        return precursorRemoval;
    }

    public void setPrecursorRemoval(String precursorRemoval) {
        this.precursorRemoval = precursorRemoval;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Boolean getPerformDecoySearch() {
        return performDecoySearch;
    }

    public void setPerformDecoySearch(Boolean performDecoySearch) {
        this.performDecoySearch = performDecoySearch;
    }

    public Boolean getIsErrorTolerant() {
        return isErrorTolerant;
    }

    public void setIsErrorTolerant(Boolean isErrorTolerant) {
        this.isErrorTolerant = isErrorTolerant;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<Integer> getFrames() {
        return frames;
    }

    public void setFrames(List<Integer> frames) {
        this.frames = frames;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getVariableModifications() {
        return variableModifications;
    }

    public void setVariableModifications(String variableModifications) {
        this.variableModifications = variableModifications;
    }

    public Double getFragmentIonTolerance() {
        return fragmentIonTolerance;
    }

    public void setFragmentIonTolerance(Double fragmentIonTolerance) {
        this.fragmentIonTolerance = fragmentIonTolerance;
    }

    public FragmentToleranceUnits getFragmentIonToleranceUnit() {
        return fragmentIonToleranceUnit;
    }

    public void setFragmentIonToleranceUnit(
            FragmentToleranceUnits fragmentIonToleranceUnit) {
        this.fragmentIonToleranceUnit = fragmentIonToleranceUnit;
    }

    public MassType getMassType() {
        return massType;
    }

    public void setMassType(MassType massType) {
        this.massType = massType;
    }

    public String getFixedMofications() {
        return fixedMofications;
    }

    public void setFixedMofications(String fixedMofications) {
        this.fixedMofications = fixedMofications;
    }

    public Double getPeptideIsotopeError() {
        return peptideIsotopeError;
    }

    public void setPeptideIsotopeError(Double peptideIsotopeError) {
        this.peptideIsotopeError = peptideIsotopeError;
    }

    public Integer getPartials() {
        return partials;
    }

    public void setPartials(Integer partials) {
        this.partials = partials;
    }

    public Double getPrecursor() {
        return precursor;
    }

    public void setPrecursor(Double precursor) {
        this.precursor = precursor;
    }

    public String getQuantitation() {
        return quantitation;
    }

    public void setQuantitation(String quantitation) {
        this.quantitation = quantitation;
    }

    public String getMaxHitsToReport() {
        return maxHitsToReport;
    }

    public void setMaxHitsToReport(String maxHitsToReport) {
        this.maxHitsToReport = maxHitsToReport;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public String getProteinMass() {
        return proteinMass;
    }

    public void setProteinMass(String proteinMass) {
        this.proteinMass = proteinMass;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public Double getPeptideMassTolerance() {
        return peptideMassTolerance;
    }

    public void setPeptideMassTolerance(Double peptideMassTolerance) {
        this.peptideMassTolerance = peptideMassTolerance;
    }

    public PeptideToleranceUnit getPeptideMassToleranceUnit() {
        return peptideMassToleranceUnit;
    }

    public void setPeptideMassToleranceUnit(
            PeptideToleranceUnit peptideMassToleranceUnit) {
        this.peptideMassToleranceUnit = peptideMassToleranceUnit;
    }

    public List<String> getUserParameter() {
        return userParameter;
    }

    public void setUserParameter(List<String> userParameter) {
        this.userParameter = userParameter;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<PmfQuery> getPmfQueries() {
        return pmfQueries;
    }

    public void setPmfQueries(List<PmfQuery> pmfQueries) {
        this.pmfQueries = pmfQueries;
    }

    public boolean isUseCache() {
        return useCache;
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    /**
     * Set the MS2 queries of the MGF file. If this object was generated
     * from an existing MGF file the connection to this MGF file is lost.
     *
     * @param ms2Queries
     */
    public void setMs2Queries(List<Ms2Query> ms2Queries) {
        // remove the source file link
        sourceFile = null;
        index.clear();

        // save the queries in the HashMap
        for (int index = 0; index < ms2Queries.size(); index++)
            this.ms2Queries.put(index, ms2Queries.get(index));
    }

    /**
     * Returns the number of Ms2 queries in the file.
     *
     * @return The number of MS2 queries.
     */
    public int getMs2QueryCount() {
        return (sourceFile != null) ? index.size() : ms2Queries.size();
    }

    /**
     * Returns the MS2 query with the given (0-based) index
     * in the file. To get the number of queries call
     * getMs2QueryCount().
     *
     * @param nIndex
     * @return
     */
    public Ms2Query getMs2Query(int nIndex) throws JMzReaderException {
        // check if the ms2 query was already loaded
        if (ms2Queries.containsKey(nIndex))
            return ms2Queries.get(nIndex);

        // if there is no file to load the query from throw an Exception
        if (sourceFile == null)
            throw new JMzReaderException("MS2 query with index " + (nIndex + 1) + " does not exist");

        // make sure the index is valid
        if (nIndex < 0 || nIndex > index.size() - 1)
            throw new JMzReaderException("MS2 query with index " + (nIndex + 1) + " does not exist in the MGF file");

        // load the query from the file
        Ms2Query query;

        query = loadIndexedQueryFromFile(nIndex);

        // save the query in the HashMap
        if (useCache)
            ms2Queries.put(nIndex, query);

        return query;
    }

    /**
     * Loads a query from the mgf file.
     *
     * @param file         The file to read the query from.
     * @param indexElement The index element pointing to that specific ms2 query.
     * @return
     * @oaram index The query's 1-based index in the MGF file. This index is stored in the returned Ms2Query object.
     */
    private static Ms2Query loadIndexedQueryFromFile(File file, IndexElement indexElement, int index, boolean disableCommentSupport) throws JMzReaderException {
        try (RandomAccessFile accFile = new RandomAccessFile(file, "r")) {

            // read the indexed element
            byte[] byteBuffer = new byte[indexElement.getSize()];

            // read the file from there
            accFile.seek(indexElement.getStart());
            accFile.read(byteBuffer);
            String ms2Buffer = new String(byteBuffer);
            // create the query

            return new Ms2Query(ms2Buffer, index, disableCommentSupport);
        } catch (FileNotFoundException e) {
            throw new JMzReaderException("MGF file could not be found.", e);
        } catch (IOException e) {
            throw new JMzReaderException("Failed to read from MGF file", e);
        }
        // ignore
    }

    /**
     * Loads a query from the mgf file who's index was buffered.
     *
     * @param nQueryIndex The queries index.
     * @return
     */
    private Ms2Query loadIndexedQueryFromFile(int nQueryIndex) throws JMzReaderException {
        if (nQueryIndex < 0 || nQueryIndex > index.size() - 1)
            throw new JMzReaderException("Tried to load non existing query from file");

        // read the indexed element
        IndexElement indexElement = index.get(nQueryIndex);

        return loadIndexedQueryFromFile(sourceFile, indexElement, nQueryIndex + 1, disableCommentSupport);
    }

    /**
     * Marshalls the mgf file object to a file. If the file
     * already exists it will be overwritten.
     *
     * @param file The file to marshall the mgf file to.
     */
    public void marshallToFile(File file) throws JMzReaderException {
        try {
            // create the object to write the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            // process all additional parameters
            String parameters = marshallAdditionalParameters();

            // write the parameters
            writer.write(parameters);

            // process the pmf spectra
            for (PmfQuery q : pmfQueries)
                writer.write(q.toString() + '\n');

            writer.write("\n");

            // write the spectra
            for (Integer index = 0; index < 1000000; index++) {
                if (!ms2Queries.containsKey(index))
                    continue;

                writer.write(ms2Queries.get(index).toString() + '\n');
            }

            writer.close();

        } catch (IOException e) {
            throw new JMzReaderException("Failed to write output file", e);
        }
    }


    @Override
    public String toString() {
        // add the parameters
        StringBuilder string = new StringBuilder(marshallAdditionalParameters());

        // process the pmf spectra
        for (PmfQuery q : pmfQueries)
            string.append(q.toString()).append('\n');

        if (pmfQueries.size() > 0)
            string.append('\n');

        // write the spectra
        for (Integer index = 0; index < 1000000; index++) {
            if (!ms2Queries.containsKey(index))
                continue;

            string.append(ms2Queries.get(index).toString()).append('\n');
        }

        return string.toString();
    }

    /**
     * Marshalls the additional parameters and returns them as a string.
     *
     * @return A string holding the marshalled parameters
     */
    private String marshallAdditionalParameters() {
        StringBuilder parameters = new StringBuilder();

        if (accessions != null && accessions.size() > 0) {
            parameters.append("ACCESSION=");
            for (int i = 0; i < accessions.size(); i++)
                parameters.append((i > 0) ? "," : "").append('"').append(accessions.get(i)).append('"');
            parameters.append('\n');
        }

        if (charge != null)
            parameters.append("CHARGE=").append(charge).append('\n');

        if (enzyme != null)
            parameters.append("CLE=").append(enzyme).append('\n');

        if (searchTitle != null)
            parameters.append("COM=").append(searchTitle).append('\n');

        if (precursorRemoval != null)
            parameters.append("CUTOUT=").append(precursorRemoval).append('\n');

        if (database != null)
            parameters.append("DB=").append(database).append('\n');

        if (performDecoySearch != null)
            parameters.append("DECOY=").append((performDecoySearch) ? "1" : "0").append('\n');

        if (isErrorTolerant != null)
            parameters.append("ERRORTOLERANT=").append((isErrorTolerant) ? "1" : "0").append('\n');

        if (format != null)
            parameters.append("FORMAT=").append(format).append('\n');

        if (frames != null && frames.size() > 0) {
            parameters.append("FRAMES=");
            for (int i = 0; i < frames.size(); i++)
                parameters.append((i > 0) ? "," : "").append(frames.get(i).toString());
            parameters.append('\n');
        }

        if (instrument != null)
            parameters.append("INSTRUMENT=").append(instrument).append('\n');

        if (variableModifications != null)
            parameters.append("IT_MODS=").append(variableModifications).append('\n');

        if (fragmentIonTolerance != null)
            parameters.append("ITOL=").append(fragmentIonTolerance.toString()).append('\n');

        if (fragmentIonToleranceUnit != null)
            parameters.append("ITOLU=").append((fragmentIonToleranceUnit == FragmentToleranceUnits.MMU) ? "mmu" : "Da").append('\n');

        if (massType != null)
            parameters.append("MASS=").append((massType == MassType.AVERAGE) ? "Average" : "Monoisotopic").append('\n');

        if (fixedMofications != null)
            parameters.append("MODS=").append(fixedMofications).append('\n');

        if (peptideIsotopeError != null)
            parameters.append("PEP_ISOTOPE_ERROR=").append(peptideIsotopeError.toString()).append('\n');

        if (partials != null)
            parameters.append("PFA=").append(partials.toString()).append('\n');

        if (precursor != null)
            parameters.append("PRECURSOR=").append(precursor.toString()).append('\n');

        if (quantitation != null)
            parameters.append("QUANTITATION=").append(quantitation).append('\n');

        if (maxHitsToReport != null)
            parameters.append("REPORT=").append(maxHitsToReport).append('\n');

        if (reportType != null)
            parameters.append("REPTYPE=").append(reportType.toString()).append('\n');

        if (searchType != null)
            parameters.append("SEARCH=").append(searchType.toString()).append('\n');

        if (proteinMass != null)
            parameters.append("SEG=").append(proteinMass).append('\n');

        if (taxonomy != null)
            parameters.append("TAXONOMY=").append(taxonomy).append('\n');

        if (peptideMassTolerance != null)
            parameters.append("TOL=").append(peptideMassTolerance.toString()).append('\n');

        if (peptideMassToleranceUnit != null)
            parameters.append("TOLU=").append(peptideMassToleranceUnit.toString()).append('\n');

        if (userMail != null)
            parameters.append("USEREMAIL=").append(userMail).append('\n');

        if (userName != null)
            parameters.append("USERNAME=").append(userName).append('\n');

        if (userParameter != null) {
            for (int i = 0; i < userParameter.size(); i++) {
                parameters.append("USER").append((i < 10) ? "0" : "").append(Integer.toString(i)).append('=').append(userParameter.get(i)).append('\n');
            }
        }

        return parameters.toString();
    }

    /**
     * Returns an iterator over all the ms2 queries.
     *
     * @return
     */
    public Ms2QueryIterator getMs2QueryIterator() {
        return new Ms2QueryIterator();
    }

    private class SpectrumIterator implements Iterator<Spectrum> {
        Iterator<Ms2Query> it;

        @SuppressWarnings("unchecked")
        public SpectrumIterator() {
            it = new Ms2QueryIterator();
        }

        public boolean hasNext() {
            return it.hasNext();
        }

        public Spectrum next() {
            return it.next();
        }

        public void remove() {
            it.remove();
        }
    }

    public class Ms2QueryIterator implements Iterator<Ms2Query>, Iterable<Ms2Query> {
        /**
         * Either the index in the elements of in the array of the source file.
         */
        private Integer currentPosition = 0;
        /**
         * A list of keys in the ms2Query HashMap
         */
        private ArrayList<Integer> keys = new ArrayList<>(ms2Queries.keySet());

        /**
         * Creates a Ms2QueryIterator. In case a source file is
         * used a FileNotFoundException might be thrown.
         *
         * @throws FileNotFoundException
         */
        protected Ms2QueryIterator() {

        }

        public Iterator<Ms2Query> iterator() {
            return this;
        }

        public boolean hasNext() {
            if (sourceFile == null) {
                return currentPosition < keys.size();
            } else {
                return currentPosition < index.size();
            }
        }

        public Ms2Query next() {
            // if there is not file set, get the object from the HashMap
            if (sourceFile == null) {
                // make sure the current position is valid
                if (currentPosition < 0 || currentPosition >= keys.size())
                    throw new IllegalStateException(new IndexOutOfBoundsException());

                // get the key
                Integer key = keys.get(currentPosition++);

                // make sure the key exists
                if (!ms2Queries.containsKey(key))
                    throw new IllegalStateException("Key not found in hashmap");

                return ms2Queries.get(key);
            } else {
                // check if the object was cached
                if (ms2Queries.containsKey(currentPosition))
                    return ms2Queries.get(currentPosition);

                // read the query from file
                Ms2Query query;
                try {
                    query = loadIndexedQueryFromFile(currentPosition);

                    // if caching is enabled do so
                    if (useCache)
                        ms2Queries.put(currentPosition, query);

                    // move to the next position
                    currentPosition++;

                    // return the query
                    return query;
                } catch (JMzReaderException e) {
                    throw new RuntimeException("Failed to load query from file.", e);
                }
            }
        }

        public void remove() {
            // this function is not supported
            throw new IllegalStateException("Function not supported");
        }
    }

    /**
     * Returns the index of ms2 queries in the mgf file.
     * This ArrayList contains the offsets of all "BEGIN IONS"
     * lines until the end of the "END IONS" lines
     * in the file in the order they are present.
     *
     * @return An array of "BEGIN IONS" lines offsets.
     */
    public List<IndexElement> getIndex() {
        return new ArrayList<>(index);
    }

    /**
     * Functions required by the
     * PeakListParser interface.
     */

    public int getSpectraCount() {
        return getMs2QueryCount();
    }

    public boolean acceptsFile() {
        return true;
    }

    public boolean acceptsDirectory() {
        return false;
    }

    public List<String> getSpectraIds() {
        // simply create a list of ids 1..size
        List<String> ids = new ArrayList<>(getMs2QueryCount());

        for (int id = 1; id <= getMs2QueryCount(); id++)
            ids.add(Integer.toString(id));

        return ids;
    }

    // ToDo PSI ID (in case of MGF) is: id = 'index=12' for the 13th spectrum
    public Spectrum getSpectrumById(String id) throws JMzReaderException {
        // create an integer
        int index = Integer.parseInt(id);

        return getMs2Query(index - 1);
    }

    public Spectrum getSpectrumByIndex(int index) throws JMzReaderException {
        return getMs2Query(index - 1);
    }

    public Iterator<Spectrum> getSpectrumIterator() {
        return new SpectrumIterator();
    }

    @Override
    public List<uk.ac.ebi.pride.tools.jmzreader.model.IndexElement> getMsNIndexes(
            int msLevel) {
        if (msLevel != 2)
            return Collections.emptyList();

        return new ArrayList<>(index);
    }

    @Override
    public List<Integer> getMsLevels() {
        // MGF files can only contain MS 2
        List<Integer> msLevels = new ArrayList<>(1);
        msLevels.add(2);

        return msLevels;
    }

    @Override
    public Map<String, IndexElement> getIndexElementForIds() {
        Map<String, IndexElement> idToIndexMap = new HashMap<>(index.size());

        for (int i = 0; i < index.size(); i++) {
            idToIndexMap.put(String.format("%d", i + 1), index.get(i));
        }

        return idToIndexMap;
    }

    public void setAllowCustomTags(boolean allowCustomTags) {
        this.allowCustomTags = allowCustomTags;
    }

    public void setDisableCommentSupport(boolean disableCommentSupport) {
        this.disableCommentSupport = disableCommentSupport;
    }
}
