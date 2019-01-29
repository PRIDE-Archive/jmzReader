package uk.ac.ebi.pride.tools.mgf_parser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.tools.jmzreader.model.IndexElement;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;
import uk.ac.ebi.pride.tools.mgf_parser.model.Ms2Query;
import uk.ac.ebi.pride.tools.mgf_parser.model.PmfQuery;

public class TestMgfFile{

    private MgfFile mgfFile = new MgfFile();
    private File sourceFile;

    @Before
    public void setUp() throws Exception {
        loadTestFile();
    }

    private void loadTestFile() throws Exception {
        URL testFile = getClass().getClassLoader().getResource("F001257.mgf");
        Assert.assertNotNull("Error loading mgf test file", testFile);
        sourceFile = new File(testFile.toURI());
        mgfFile = new MgfFile(sourceFile);
    }

    @Test
    public void testGetAccessions() {
        Assert.assertEquals(3, mgfFile.getAccessions().size());
        Assert.assertEquals("P12346", mgfFile.getAccessions().get(1));
        Assert.assertEquals("P12347", mgfFile.getAccessions().get(2));
    }

    @Test
    public void testSetAccessions() {
        ArrayList<String> accessions = new ArrayList<>();
        accessions.add("P12345");
        accessions.add("P12346");
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setAccessions(accessions);
        Assert.assertEquals("P12345,P12346", String.join(",", mgfFile.getAccessions()));
    }

    @Test
    public void testGetCharge() {
        Assert.assertEquals("2+,3+,4+,5+", mgfFile.getCharge());
    }

    @Test
    public void testSetCharge() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setCharge("8-,5-,4-,3-");
        Assert.assertEquals("8-,5-,4-,3-", modifiedMgfFile.getCharge());
    }

    /**
     * Tests getting a spectrum from the test MGF file.
     */
    @Test
    public void testGetSpectrum() throws Exception{
        Ms2Query specturm;
        List<String> allSpectra = mgfFile.getSpectraIds();
        specturm = (Ms2Query) mgfFile.getSpectrumById(allSpectra.get(3));
        Assert.assertNotNull(specturm);
        Assert.assertEquals("4", specturm.getId());
        Assert.assertEquals("PRIDE_Exp_mzData_Ac_9266.xml_id_4", specturm.getTitle());
        Assert.assertEquals(17, specturm.getPeakList().size());
        Assert.assertEquals(new Integer(2), specturm.getMsLevel());
        Assert.assertEquals("2+,3+", specturm.getChargeState());
        Assert.assertNull(specturm.getPrecursorCharge());
        Assert.assertEquals(413.2861, specturm.getPrecursorMZ(), 0.0);
        Assert.assertEquals(413.2861, specturm.getPeptideMass(), 0.0);
        Assert.assertEquals(null, specturm.getPrecursorIntensity());
        Assert.assertEquals(1, specturm.getAdditional().getCvParams().size());
        Assert.assertNull(specturm.getComposition());
        Assert.assertNull(specturm.getErrorTolerantTags());
        Assert.assertNull(specturm.getInstrument());
        Assert.assertNull(specturm.getPeptideIntensity());
        Assert.assertNull(specturm.getRetentionTime());
        Assert.assertNull( specturm.getScan());
        Assert.assertNull(specturm.getSequenceQualifiers());
        Assert.assertNull(specturm.getTags());
        Assert.assertNull(specturm.getTolerance());
        Assert.assertNull(specturm.getToleranceUnit());
        Assert.assertEquals(0, specturm.getUserTags().size());
        Assert.assertNull(specturm.getVariableModifications());
    }


    @Test
    public void testGetEnzyme() {
        Assert.assertEquals("Trypsin", mgfFile.getEnzyme());
    }

    @Test
    public void testSetEnzyme() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setEnzyme("Trypsin");
        Assert.assertEquals("Trypsin", modifiedMgfFile.getEnzyme());
    }

    @Test
    public void testGetSearchTitle() {
        Assert.assertEquals("First test experiment (values are not real)", mgfFile.getSearchTitle());
    }

    @Test
    public void testSetSearchTitle() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setSearchTitle("My first test experiment");
        Assert.assertEquals("My first test experiment", modifiedMgfFile.getSearchTitle());
    }

    @Test
    public void testGetPrecursorRemoval() {
        Assert.assertEquals("20,120", mgfFile.getPrecursorRemoval());
    }

    @Test
    public void testSetPrecursorRemoval() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setPrecursorRemoval("10,120");
        Assert.assertEquals("10,120", modifiedMgfFile.getPrecursorRemoval());
    }

    @Test
    public void testGetDatabase() {
        Assert.assertEquals("SwissProt v57", mgfFile.getDatabase());
    }

    @Test
    public void testSetDatabase() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setDatabase("UniProt 1");
        Assert.assertEquals("UniProt 1", modifiedMgfFile.getDatabase());
    }

    @Test
    public void testGetPerformDecoySearch() {
        Assert.assertEquals(Boolean.FALSE, mgfFile.getPerformDecoySearch());
    }

    @Test
    public void testSetPerformDecoySearch() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setPerformDecoySearch(true);
        Assert.assertEquals(true, mgfFile.getPerformDecoySearch());
    }

    @Test
    public void testGetIsErrorTolerant() {
        Assert.assertEquals(Boolean.TRUE, mgfFile.getIsErrorTolerant());
    }

    @Test
    public void testSetIsErrorTolerant() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setIsErrorTolerant(false);
        Assert.assertEquals(false, mgfFile.getIsErrorTolerant());
    }

    @Test
    public void testGetFormat() {
        Assert.assertEquals("Mascot generic", mgfFile.getFormat());
    }

    @Test
    public void testSetFormat() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setFormat("Sequest (.DTA)");
        Assert.assertEquals("Sequest (.DTA)", modifiedMgfFile.getFormat());
    }

    @Test
    public void testGetFrames() {
        Assert.assertEquals(6, mgfFile.getFrames().size());
        Assert.assertEquals(new Integer(5), mgfFile.getFrames().get(4));
        Assert.assertEquals(new Integer(3), mgfFile.getFrames().get(2));
    }

    @Test
    public void testSetFrames() {
        ArrayList<Integer> frames = new ArrayList<>();
        frames.add(2);
        frames.add(4);
        frames.add(5);
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setFrames(frames);
        Assert.assertEquals("[2, 4, 5]", modifiedMgfFile.getFrames().toString());
    }

    @Test
    public void testGetInstrument() {
        Assert.assertEquals("ESI-QUAD", mgfFile.getInstrument());
    }

    @Test
    public void testSetInstrument() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setInstrument("Default");
        Assert.assertEquals("Default", modifiedMgfFile.getInstrument());
    }

    @Test
    public void testGetVariableModifications() {
        Assert.assertEquals("Oxidation (M)", mgfFile.getVariableModifications());
    }

    @Test
    public void testSetVariableModifications() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setVariableModifications("My mod");
        Assert.assertEquals("My mod", modifiedMgfFile.getVariableModifications());
    }

    @Test
    public void testGetFragmentIonTolerance() {
        Assert.assertEquals(0.5, mgfFile.getFragmentIonTolerance(), 0.0);
    }

    @Test
    public void testSetFragmentIonTolerance() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setFragmentIonTolerance(0.3);
        Assert.assertEquals(new Double(0.3), modifiedMgfFile.getFragmentIonTolerance());
    }

    @Test
    public void testGetFragmentIonToleranceUnit() {
        Assert.assertEquals(MgfFile.FragmentToleranceUnits.DA, mgfFile.getFragmentIonToleranceUnit());
    }

    @Test
    public void testSetFragmentIonToleranceUnit() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setFragmentIonToleranceUnit(MgfFile.FragmentToleranceUnits.MMU);
        Assert.assertEquals(MgfFile.FragmentToleranceUnits.MMU, modifiedMgfFile.getFragmentIonToleranceUnit());
    }

    @Test
    public void testGetMassType() {
        Assert.assertEquals(MgfFile.MassType.MONOISOTOPIC, mgfFile.getMassType());
    }

    @Test
    public void testSetMassType() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setMassType(MgfFile.MassType.AVERAGE);
        Assert.assertEquals(MgfFile.MassType.AVERAGE, modifiedMgfFile.getMassType());
    }

    @Test
    public void testGetFixedMofications() {
        Assert.assertEquals("Carbamidomethylation (C)", mgfFile.getFixedMofications());
    }

    @Test
    public void testSetFixedMofications() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setFixedMofications("Another mod");
        Assert.assertEquals("Another mod", modifiedMgfFile.getFixedMofications());
    }

    @Test
    public void testGetPeptideIsotopeError() {
        Assert.assertEquals(1.3, mgfFile.getPeptideIsotopeError(), 0.0);
    }

    @Test
    public void testSetPeptideIsotopeError() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setPeptideIsotopeError(1.9);
        Assert.assertEquals(new Double(1.9), modifiedMgfFile.getPeptideIsotopeError());
    }

    @Test
    public void testGetPartials() {
        Assert.assertEquals(new Integer(1), mgfFile.getPartials());
    }

    @Test
    public void testSetPartials() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setPartials(2);
        Assert.assertEquals(new Integer(2), modifiedMgfFile.getPartials());
    }

    @Test
    public void testGetPrecursor() {
        Assert.assertEquals(1047.0, mgfFile.getPrecursor(),0.0);
    }

    @Test
    public void testSetPrecursor() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setPrecursor(1011.0);
        Assert.assertEquals(new Double(1011.0), modifiedMgfFile.getPrecursor());
    }

    @Test
    public void testGetQuantitation() {
        Assert.assertEquals("iTRAQ 4plex", mgfFile.getQuantitation());
    }

    @Test
    public void testSetQuantitation() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setQuantitation("SILAC");
        Assert.assertEquals("SILAC", modifiedMgfFile.getQuantitation());
    }

    @Test
    public void testGetMaxHitsToReport() {
        Assert.assertEquals("1500", mgfFile.getMaxHitsToReport());
    }

    @Test
    public void testSetMaxHitsToReport() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setMaxHitsToReport("Auto");
        Assert.assertEquals("Auto", modifiedMgfFile.getMaxHitsToReport());
    }

    @Test
    public void testGetReportType() {
        Assert.assertEquals(MgfFile.ReportType.PEPTIDE, mgfFile.getReportType());
    }

    @Test
    public void testSetReportType() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setReportType(MgfFile.ReportType.PROTEIN);
        Assert.assertEquals(MgfFile.ReportType.PROTEIN, modifiedMgfFile.getReportType());
    }

    @Test
    public void testGetSearchType() {
        Assert.assertEquals(MgfFile.SearchType.MIS, mgfFile.getSearchType());
    }

    @Test
    public void testSetSearchType() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setSearchType(MgfFile.SearchType.PMF);
        Assert.assertEquals(MgfFile.SearchType.PMF, modifiedMgfFile.getSearchType());
    }

    @Test
    public void testGetProteinMass() {
        Assert.assertEquals("10489", mgfFile.getProteinMass());
    }

    @Test
    public void testSetProteinMass() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setProteinMass("1010");
        Assert.assertEquals("1010", modifiedMgfFile.getProteinMass());
    }

    @Test
    public void testGetTaxonomy() {
        Assert.assertEquals("Human 9606", mgfFile.getTaxonomy());
    }

    @Test
    public void testSetTaxonomy() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setTaxonomy("My taxon");
        Assert.assertEquals("My taxon", modifiedMgfFile.getTaxonomy());
    }

    @Test
    public void testGetPeptideMassTolerance() {
        Assert.assertEquals(0.2, mgfFile.getPeptideMassTolerance(), 0.0);
    }

    @Test
    public void testSetPeptideMassTolerance() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setPeptideMassTolerance(0.3);
        Assert.assertEquals(new Double(0.3), modifiedMgfFile.getPeptideMassTolerance());
    }

    @Test
    public void testGetPeptideMassToleranceUnit() {
        Assert.assertEquals(MgfFile.PeptideToleranceUnit.PPM, mgfFile.getPeptideMassToleranceUnit());
    }

    @Test
    public void testSetPeptideMassToleranceUnit() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setPeptideMassToleranceUnit(MgfFile.PeptideToleranceUnit.PERCENT);
        Assert.assertEquals(MgfFile.PeptideToleranceUnit.PERCENT, modifiedMgfFile.getPeptideMassToleranceUnit());
    }

    @Test
    public void testGetUserParameter() {
        Assert.assertEquals(3, mgfFile.getUserParameter().size());
        Assert.assertEquals("2nd user param", mgfFile.getUserParameter().get(1));
    }

    @Test
    public void testSetUserParameter() {
        ArrayList<String> params = new ArrayList<>();
        params.add("My param");
        params.add("Another param");
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setUserParameter(params);
        Assert.assertEquals(2, modifiedMgfFile.getUserParameter().size());
    }

    @Test
    public void testGetUserMail() {
        Assert.assertEquals("jgriss@ebi.ac.uk", mgfFile.getUserMail());
    }

    @Test
    public void testSetUserMail() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setUserMail("another@mail");
        Assert.assertEquals("another@mail", modifiedMgfFile.getUserMail());
    }

    @Test
    public void testGetUserName() {
        Assert.assertEquals("Johannes Griss", mgfFile.getUserName());
    }

    @Test
    public void testSetUserName() {
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setUserName("Another name");
        Assert.assertEquals("Another name", modifiedMgfFile.getUserName());
    }

    @Test
    public void testGetPmfQueries() {
        Assert.assertEquals(6, mgfFile.getPmfQueries().size());
        Assert.assertEquals(1223.145, mgfFile.getPmfQueries().get(1).getMass(), 0.0);
        Assert.assertEquals(3092.0, mgfFile.getPmfQueries().get(3).getIntensity(),0.0);
    }

    @Test
    public void testSetPmfQueries() {
        ArrayList<PmfQuery> queries = new ArrayList<>();
        queries.add(new PmfQuery(10.0, 10.0));
        queries.add(new PmfQuery(20.0, 20.0));
        queries.add(new PmfQuery(30.0, null));
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setPmfQueries(queries);
        Assert.assertEquals(3, modifiedMgfFile.getPmfQueries().size());
    }

    @Test
    public void testSetMs2Queries() throws Exception {
        Ms2Query query = new Ms2Query("BEGIN IONS\nPEPMASS=406.283\n145.119100 8\n217.142900 75\n409.221455 11\n438.314735 46\n567.400183 24\nEND IONS\n", 1, false);
        ArrayList<Ms2Query> queries = new ArrayList<>();
        queries.add(query);
        MgfFile modifiedMgfFile = mgfFile;
        modifiedMgfFile.setMs2Queries(queries);
        Assert.assertEquals(1, modifiedMgfFile.getMs2QueryCount());
        Assert.assertEquals(query.toString(), modifiedMgfFile.getMs2Query(0).toString());
    }

    @Test
    public void testGetMs2QueryCount() {
        Assert.assertEquals(10, mgfFile.getMs2QueryCount());
    }

    @Test
    public void testGetMs2Query() throws Exception{
        Assert.assertNotNull(mgfFile.getMs2Query(7));
    }

    @Test
    public void testGetMs2QueryIterator() throws Exception {
        int queryCount = 0;
        for (Ms2Query q : mgfFile.getMs2QueryIterator()) {
            Assert.assertNotNull(q);
            queryCount++;
        }
        Assert.assertEquals(10, queryCount);
    }

    @Test
    public void testMgfFile() throws Exception {
        Iterator<Ms2Query> it1 = mgfFile.getMs2QueryIterator();
        Iterator<Ms2Query> it2 = mgfFile.getMs2QueryIterator();
        while (it1.hasNext() && it2.hasNext()) {
            Assert.assertEquals(it1.next().toString(), it2.next().toString());
        }
    }

    @Test
    public void testGetIndex() throws Exception {
        loadTestFile();
        List<IndexElement> index = mgfFile.getMsNIndexes(2);
        Spectrum s = mgfFile.getSpectrumByIndex(3);
        Spectrum s1 = MgfFile.getIndexedSpectrum(sourceFile, index.get(2));
        Assert.assertEquals(s.toString(), s1.toString());
    }
}
