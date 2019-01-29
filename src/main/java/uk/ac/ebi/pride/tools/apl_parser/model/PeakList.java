package uk.ac.ebi.pride.tools.apl_parser.model;

import uk.ac.ebi.pride.tools.apl_parser.AplFile;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;
import uk.ac.ebi.pride.tools.jmzreader.model.impl.ParamGroup;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PeakList implements Spectrum{

    private static Pattern peakPattern = Pattern.compile("\\s*([0-9.]+)\\t([0-9.]+)\\s*");
    private static Pattern headerPattern = Pattern.compile("\\s*RawFile: (.*) Index: ([0-9]+)\\s*");

    /**
     * The spectrum's peaks
     */
    private Map<Double, Double> peakList;
    private String header;
    private Double mz;
    private String fragmentation;
    private String chargeState;
    private Integer index;

    public PeakList(String mgfQuery, int index) throws JMzReaderException {
        this.index = index;

        // process the mgf section line by line
        String[] lines = mgfQuery.trim().split("\n");
        boolean inAttributeSection = true;

        for (int nLineNumber = 0; nLineNumber < lines.length; nLineNumber++) {
            String line = lines[nLineNumber].trim();

            // ignore empty lines
            if (line.length() < 1)
                continue;

            // first line must be "BEGIN IONS" and last line must be "END IONS"
            if (nLineNumber == 0 && !"peaklist start".equals(line))
                throw new JMzReaderException("MS2 query must start with 'peaklist start'");

            if (nLineNumber == 0) continue;

            if (nLineNumber == lines.length - 1 && !"peaklist end".equals(line))
                throw new JMzReaderException("MS2 query must end with 'peaklist end'");

            if (nLineNumber == lines.length -1) continue;

            // check if it's a property
            Matcher attributeMatcher = AplFile.attributePattern.matcher(line);

            if (inAttributeSection && attributeMatcher.find()) {
                if (attributeMatcher.groupCount() != 2)
                    throw new JMzReaderException("Invalid attribute line encountered in MS2 query.");

                String name 	= attributeMatcher.group(1);
                String value	= attributeMatcher.group(2);

                // save the attribute
                saveAttribute(name, value);
            }
            else {
                Matcher peakMatcher = peakPattern.matcher(line);

                if (!peakMatcher.find() || peakMatcher.groupCount() != 2)
                    throw new JMzReaderException("Invalid line encountered in MS2 query: " + line);

                // add the peak
                addPeak(Double.parseDouble(peakMatcher.group(1)), Double.parseDouble(peakMatcher.group(2)));

                inAttributeSection = false;
            }
        }
    }

    /**
     * Stores the attribute in the respective member variable.
     * @param name The attribute's name
     * @param value The attribute's value
     * @throws JMzReaderException
     */
    private void saveAttribute(String name, String value) throws JMzReaderException {
        if ("header".equals(name))
            header = value;
        else if ("mz".equals(name))
            mz = Double.parseDouble(value);
        else if ("fragmentation".equals(name))
            fragmentation = value;
        else if ("charge".equals(name))
            chargeState = value;
        else
            throw new JMzReaderException("Unknown peptide property '" + name + "' encountered");
    }

    /**
     * Adds a peak to the spectrum.
     * @param intensity
     */
    public void addPeak(Double mz, Double intensity) {
        if (peakList == null)
            peakList = new HashMap<>(1);

        peakList.put(mz, intensity);
    }

    @Override
    public String getId() {
        if(index == null)
            return null;

        return index.toString();
    }

    @Override
    public Integer getPrecursorCharge() {
        try {
            // if there are multiple chargeState states, give up
            if (chargeState.contains(","))
                return null;

            if (chargeState.contains("-")) {
                return Integer.parseInt(chargeState);
            }
            else {
                return Integer.parseInt(chargeState.replace("+", ""));
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public Double getPrecursorMZ() {
        return mz;
    }

    @Override
    public Double getPrecursorIntensity() {
        return null;
    }

    @Override
    public Map<Double, Double> getPeakList() {
        return peakList;
    }

    @Override
    public Integer getMsLevel() {
        return 2;
    }

    @Override
    public ParamGroup getAdditional() {
        return null;
    }

    @Override
    public String toString(){
        StringBuilder query = new StringBuilder("peaklist start\n");

        // process the optional attribtues
        if (mz != null)
            query.append("mz=").append(mz).append('\n');
        if (fragmentation != null)
            query.append("fragmentation=").append(fragmentation).append('\n');
        if (chargeState != null)
            query.append("charge=").append(chargeState).append('\n');
        if (header != null)
            query.append("header=").append(header).append('\n');

        List<Double> masses = new ArrayList<>(peakList.keySet());
        Collections.sort(masses);

        // process the peak list
        for (Double mz : masses)
            query.append(mz).append('\t').append(peakList.get(mz)).append('\n');

        query.append("peaklist end\n");

        return query.toString();
    }
}
