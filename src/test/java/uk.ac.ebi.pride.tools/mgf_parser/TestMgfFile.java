package uk.ac.ebi.pride.tools.mgf_parser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.jmzreader.model.IndexElement;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;
import uk.ac.ebi.pride.tools.mgf_parser.model.Ms2Query;
import uk.ac.ebi.pride.tools.mgf_parser.model.PmfQuery;

public class TestMgfFile{

    private MgfFile mgfFile;
    private File sourceFile;

    @Before
    public void setUp() throws Exception {
        mgfFile = new MgfFile();
    }

    @Test
    public void loadTestFile() {
        URL testFile = getClass().getClassLoader().getResource("F001257.mgf");
        Assert.assertNotNull("Error loading mgf test file", testFile);

        try {
            sourceFile = new File(testFile.toURI());
            mgfFile = new MgfFile(sourceFile);
        } catch (Exception e) {
            System.out.println("Faild to load test file");
        }
    }

    @Test
    public void testGetAccessions() {
        loadTestFile();
        Assert.assertEquals(3, mgfFile.getAccessions().size());
        Assert.assertEquals("P12346", mgfFile.getAccessions().get(1));
        Assert.assertEquals("P12347", mgfFile.getAccessions().get(2));
    }

    @Test
    public void testSetAccessions() {
        ArrayList<String> accessions = new ArrayList<>();
        accessions.add("P12345");
        accessions.add("P12346");

        mgfFile.setAccessions(accessions);

        String mgfString = mgfFile.toString();

        Assert.assertEquals("ACCESSION=\"P12345\",\"P12346\"\n", mgfString);
    }

    @Test
    public void testGetCharge() {
        loadTestFile();
        Assert.assertEquals("2+,3+,4+,5+", mgfFile.getCharge());
    }

    @Test
    public void testSetCharge() {
        mgfFile.setCharge("8-,5-,4-,3-");
        Assert.assertEquals("CHARGE=8-,5-,4-,3-\n", mgfFile.toString());
    }

    @Test
    public void testGetEnzyme() {
        loadTestFile();
        Assert.assertEquals("Trypsin", mgfFile.getEnzyme());
    }

    @Test
    public void testSetEnzyme() {
        mgfFile.setEnzyme("Trypsin");
        Assert.assertEquals("CLE=Trypsin\n", mgfFile.toString());
    }

    @Test
    public void testGetSearchTitle() {
        loadTestFile();
        Assert.assertEquals("First test experiment (values are not real)", mgfFile.getSearchTitle());
    }

    public void testSetSearchTitle() {
        mgfFile.setSearchTitle("My first test experiment");
        Assert.assertEquals("COM=My first test experiment\n", mgfFile.toString());
    }

    public void testGetPrecursorRemoval() {
        loadTestFile();
        Assert.assertEquals("20,120", mgfFile.getPrecursorRemoval());
    }

    public void testSetPrecursorRemoval() {
        mgfFile.setPrecursorRemoval("10,120");
        Assert.assertEquals("CUTOUT=10,120\n", mgfFile.toString());
    }

    public void testGetDatabase() {
        loadTestFile();
        Assert.assertEquals("SwissProt v57", mgfFile.getDatabase());
    }

    public void testSetDatabase() {
        mgfFile.setDatabase("UniProt 1");
        Assert.assertEquals("DB=UniProt 1\n", mgfFile.toString());
    }

    public void testGetPerformDecoySearch() {
        loadTestFile();
        Assert.assertEquals(Boolean.FALSE, mgfFile.getPerformDecoySearch());
    }

    public void testSetPerformDecoySearch() {
        mgfFile.setPerformDecoySearch(true);
        Assert.assertEquals("DECOY=1\n", mgfFile.toString());
    }

    public void testGetIsErrorTolerant() {
        loadTestFile();
        Assert.assertEquals(Boolean.TRUE, mgfFile.getIsErrorTolerant());
    }

    public void testSetIsErrorTolerant() {
        mgfFile.setIsErrorTolerant(false);
        Assert.assertEquals("ERRORTOLERANT=0\n", mgfFile.toString());
    }

    public void testGetFormat() {
        loadTestFile();
        Assert.assertEquals("Mascot generic", mgfFile.getFormat());
    }

    public void testSetFormat() {
        mgfFile.setFormat("Sequest (.DTA)");
        Assert.assertEquals("FORMAT=Sequest (.DTA)\n", mgfFile.toString());
    }

    public void testGetFrames() {
        loadTestFile();
        Assert.assertEquals(6, mgfFile.getFrames().size());
        Assert.assertEquals(new Integer(5), mgfFile.getFrames().get(4));
        Assert.assertEquals(new Integer(3), mgfFile.getFrames().get(2));
    }

    public void testSetFrames() {
        ArrayList<Integer> frames = new ArrayList<>();
        frames.add(2);
        frames.add(4);
        frames.add(5);

        mgfFile.setFrames(frames);
        Assert.assertEquals("FRAMES=2,4,5\n", mgfFile.toString());
    }

    public void testGetInstrument() {
        loadTestFile();
        Assert.assertEquals("ESI-QUAD", mgfFile.getInstrument());
    }

    public void testSetInstrument() {
        mgfFile.setInstrument("Default");
        Assert.assertEquals("INSTRUMENT=Default\n", mgfFile.toString());
    }

    public void testGetVariableModifications() {
        loadTestFile();
        Assert.assertEquals("Oxidation (M)", mgfFile.getVariableModifications());
    }

    public void testSetVariableModifications() {
        mgfFile.setVariableModifications("My mod");
        Assert.assertEquals("IT_MODS=My mod\n", mgfFile.toString());
    }

    public void testGetFragmentIonTolerance() {
        loadTestFile();
        Assert.assertEquals(0.5, mgfFile.getFragmentIonTolerance(), 0.0);
    }

    public void testSetFragmentIonTolerance() {
        mgfFile.setFragmentIonTolerance(0.3);
        Assert.assertEquals("ITOL=0.3\n", mgfFile.toString());
    }

    public void testGetFragmentIonToleranceUnit() {
        loadTestFile();
        Assert.assertEquals(MgfFile.FragmentToleranceUnits.DA, mgfFile.getFragmentIonToleranceUnit());
    }

    public void testSetFragmentIonToleranceUnit() {
        mgfFile.setFragmentIonToleranceUnit(MgfFile.FragmentToleranceUnits.MMU);
        Assert.assertEquals("ITOLU=mmu\n", mgfFile.toString());
    }

    public void testGetMassType() {
        loadTestFile();
        Assert.assertEquals(MgfFile.MassType.MONOISOTOPIC, mgfFile.getMassType());
    }

    public void testSetMassType() {
        mgfFile.setMassType(MgfFile.MassType.AVERAGE);
        Assert.assertEquals("MASS=Average\n", mgfFile.toString());
    }

    public void testGetFixedMofications() {
        loadTestFile();
        Assert.assertEquals("Carbamidomethylation (C)", mgfFile.getFixedMofications());
    }

    public void testSetFixedMofications() {
        mgfFile.setFixedMofications("Another mod");
        Assert.assertEquals("MODS=Another mod\n", mgfFile.toString());
    }

    public void testGetPeptideIsotopeError() {
        loadTestFile();
        Assert.assertEquals(1.3, mgfFile.getPeptideIsotopeError(), 0.0);
    }

    public void testSetPeptideIsotopeError() {
        mgfFile.setPeptideIsotopeError(1.9);
        Assert.assertEquals("PEP_ISOTOPE_ERROR=1.9\n", mgfFile.toString());
    }

    public void testGetPartials() {
        loadTestFile();
        Assert.assertEquals(new Integer(1), mgfFile.getPartials());
    }

    public void testSetPartials() {
        mgfFile.setPartials(2);
        Assert.assertEquals("PFA=2\n", mgfFile.toString());
    }

    public void testGetPrecursor() {
        loadTestFile();
        Assert.assertEquals(1047.0, mgfFile.getPrecursor(),0.0);
    }

    public void testSetPrecursor() {
        mgfFile.setPrecursor(1011.0);
        Assert.assertEquals("PRECURSOR=1011.0\n", mgfFile.toString());
    }

    public void testGetQuantitation() {
        loadTestFile();
        Assert.assertEquals("iTRAQ 4plex", mgfFile.getQuantitation());
    }

    public void testSetQuantitation() {
        mgfFile.setQuantitation("SILAC");
        Assert.assertEquals("QUANTITATION=SILAC\n", mgfFile.toString());
    }

    public void testGetMaxHitsToReport() {
        loadTestFile();
        Assert.assertEquals("1500", mgfFile.getMaxHitsToReport());
    }

    public void testSetMaxHitsToReport() {
        mgfFile.setMaxHitsToReport("Auto");
        Assert.assertEquals("REPORT=Auto\n", mgfFile.toString());
    }

    public void testGetReportType() {
        loadTestFile();
        Assert.assertEquals(MgfFile.ReportType.PEPTIDE, mgfFile.getReportType());
    }

    public void testSetReportType() {
        mgfFile.setReportType(MgfFile.ReportType.PROTEIN);
        Assert.assertEquals("REPTYPE=protein\n", mgfFile.toString());
    }

    public void testGetSearchType() {
        loadTestFile();
        Assert.assertEquals(MgfFile.SearchType.MIS, mgfFile.getSearchType());
    }

    public void testSetSearchType() {
        mgfFile.setSearchType(MgfFile.SearchType.PMF);
        Assert.assertEquals("SEARCH=PMF\n", mgfFile.toString());
    }

    public void testGetProteinMass() {
        loadTestFile();
        Assert.assertEquals("10489", mgfFile.getProteinMass());
    }

    public void testSetProteinMass() {
        mgfFile.setProteinMass("1010");
        Assert.assertEquals("SEG=1010\n", mgfFile.toString());
    }

    public void testGetTaxonomy() {
        loadTestFile();
        Assert.assertEquals("Human 9606", mgfFile.getTaxonomy());
    }

    public void testSetTaxonomy() {
        mgfFile.setTaxonomy("My taxon");
        Assert.assertEquals("TAXONOMY=My taxon\n", mgfFile.toString());
    }

    public void testGetPeptideMassTolerance() {
        loadTestFile();
        Assert.assertEquals(0.2, mgfFile.getPeptideMassTolerance(), 0.0);
    }

    public void testSetPeptideMassTolerance() {
        mgfFile.setPeptideMassTolerance(0.3);
        Assert.assertEquals("TOL=0.3\n", mgfFile.toString());
    }

    public void testGetPeptideMassToleranceUnit() {
        loadTestFile();
        Assert.assertEquals(MgfFile.PeptideToleranceUnit.PPM, mgfFile.getPeptideMassToleranceUnit());
    }

    public void testSetPeptideMassToleranceUnit() {
        mgfFile.setPeptideMassToleranceUnit(MgfFile.PeptideToleranceUnit.PERCENT);
        Assert.assertEquals("TOLU=%\n", mgfFile.toString());
    }

    public void testGetUserParameter() {
        loadTestFile();
        Assert.assertEquals(3, mgfFile.getUserParameter().size());
        Assert.assertEquals("2nd user param", mgfFile.getUserParameter().get(1));
    }

    public void testSetUserParameter() {
        ArrayList<String> params = new ArrayList<>();
        params.add("My param");
        params.add("Another param");

        mgfFile.setUserParameter(params);

        Assert.assertEquals("USER00=My param\nUSER01=Another param\n", mgfFile.toString());
    }

    public void testGetUserMail() {
        loadTestFile();
        Assert.assertEquals("jgriss@ebi.ac.uk", mgfFile.getUserMail());
    }

    public void testSetUserMail() {
        mgfFile.setUserMail("another@mail");
        Assert.assertEquals("USEREMAIL=another@mail\n", mgfFile.toString());
    }

    public void testGetUserName() {
        loadTestFile();
        Assert.assertEquals("Johannes Griss", mgfFile.getUserName());
    }

    public void testSetUserName() {
        mgfFile.setUserName("Another name");
        Assert.assertEquals("USERNAME=Another name\n", mgfFile.toString());
    }

    public void testGetPmfQueries() {
        loadTestFile();
        Assert.assertEquals(6, mgfFile.getPmfQueries().size());
        Assert.assertEquals(1223.145, mgfFile.getPmfQueries().get(1).getMass(), 0.0);
        Assert.assertEquals(3092.0, mgfFile.getPmfQueries().get(3).getIntensity(),0.0);
    }

    public void testSetPmfQueries() {
        ArrayList<PmfQuery> queries = new ArrayList<>();

        queries.add(new PmfQuery(10.0, 10.0));
        queries.add(new PmfQuery(20.0, 20.0));
        queries.add(new PmfQuery(30.0, null));

        mgfFile.setPmfQueries(queries);

        Assert.assertEquals("10.0 10.0\n20.0 20.0\n30.0\n\n", mgfFile.toString());
    }

    public void testSetMs2Queries() {
        Ms2Query query;
        try {
            query = new Ms2Query("BEGIN IONS\nPEPMASS=406.283\n145.119100 8\n217.142900 75\n409.221455 11\n438.314735 46\n567.400183 24\nEND IONS\n", 1, false);

            ArrayList<Ms2Query> queries = new ArrayList<>();
            queries.add(query);

            mgfFile.setMs2Queries(queries);

            Assert.assertEquals("BEGIN IONS\nPEPMASS=406.283\n145.1191 8.0\n217.1429 75.0\n409.221455 11.0\n438.314735 46.0\n567.400183 24.0\nEND IONS\n\n", mgfFile.toString());
        } catch (JMzReaderException e) {
            e.printStackTrace();
        }
    }

    public void testGetMs2QueryCount() {
        loadTestFile();
        Assert.assertEquals(10, mgfFile.getMs2QueryCount());
    }

    public void testGetMs2Query() {
        loadTestFile();

        try {
            Assert.assertNotNull(mgfFile.getMs2Query(7));
        } catch (JMzReaderException e) {
            System.out.println(e.getMessage());
        }
    }

    public void testGetMs2QueryIterator() {
        loadTestFile();

        int queryCount = 0;

        for (Ms2Query q : mgfFile.getMs2QueryIterator()) {
            Assert.assertNotNull(q);
            queryCount++;
        }
        Assert.assertEquals(10, queryCount);
    }

    public void testMgfFile() {
        loadTestFile();

        // get the index
        List<IndexElement> index = mgfFile.getIndex();

        // create the new file
        MgfFile newFile;
        try {
            newFile = new MgfFile(sourceFile, index);

            Iterator<Ms2Query> it1 = mgfFile.getMs2QueryIterator();
            Iterator<Ms2Query> it2 = newFile.getMs2QueryIterator();

            while (it1.hasNext() && it2.hasNext()) {
                Assert.assertEquals(it1.next().toString(), it2.next().toString());
            }
        } catch (JMzReaderException e) {
            System.out.println(e.getMessage());
        }
    }

    public void testGetIndex() {
        try {
            loadTestFile();
            List<IndexElement> index = mgfFile.getMsNIndexes(2);

            Spectrum s = mgfFile.getSpectrumByIndex(3);

            Spectrum s1 = MgfFile.getIndexedSpectrum(sourceFile, index.get(2));

            Assert.assertEquals(s.toString(), s1.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
