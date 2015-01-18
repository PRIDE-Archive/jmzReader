package uk.ac.ebi.pride.tools.apl_parser;

import junit.framework.TestCase;
import uk.ac.ebi.pride.tools.apl_parser.model.PeakList;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.jmzreader.model.IndexElement;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by neuhause on 11/02/14.
 */

public class TestAplFile extends TestCase {
    private AplFile aplFile;
    private File sourceFile;

    protected void setUp() throws Exception {
        aplFile = new AplFile();
    }

    private void loadTestFile() {
        URL testFile = TestAplFile.class.getClassLoader().getResource("allSpectra.CID.ITMS.sil0.apl");
        assertNotNull("Error loading apl test file", testFile);

        try {
            sourceFile = new File(testFile.toURI());

            aplFile = new AplFile(sourceFile);
        } catch (Exception e) {
            fail("Faild to load test file");
        }
    }

    public void testGetFormat() {
        loadTestFile();
        assertEquals("Andromeda peaklist file", aplFile.getFormat());
    }

    public void testSetPeakLists() {
        PeakList query;
        try {
            query = new PeakList("peaklist start\nmz=1271.13935636076\ncharge=4\nheader=RawFile: 20080830_Orbi6_NaNa_SA_BiotechVariation_MH03_02 Index: 16236 Silind: 43885\n380.02725\t7.750772\n419.57687\t11.58331\n423.23862\t10.6417\npeaklist end\n", 1);

            ArrayList<PeakList> queries = new ArrayList<PeakList>();
            queries.add(query);

            aplFile.setPeakLists(queries);

            assertEquals("peaklist start\nmz=1271.13935636076\ncharge=4\nheader=RawFile: 20080830_Orbi6_NaNa_SA_BiotechVariation_MH03_02 Index: 16236 Silind: 43885\n380.02725\t7.750772\n419.57687\t11.58331\n423.23862\t10.6417\npeaklist end\n\n", aplFile.toString());
        } catch (JMzReaderException e) {
            e.printStackTrace();
        }
    }

    public void testGetPeakListCount() {
        loadTestFile();
        assertEquals(10, aplFile.getPeakListCount());
    }

    public void testGetPeakList() {
        loadTestFile();

        try {
            assertNotNull(aplFile.getPeakList(7));
        } catch (JMzReaderException e) {
            fail(e.getMessage());
        }
    }

    public void testGetPeakListIterator() {
        loadTestFile();

        int queryCount = 0;

        try {
            for (PeakList q : aplFile.getPeakListIterator()) {
                assertNotNull(q);
                queryCount++;
            }
            assertEquals(10, queryCount);
        } catch (JMzReaderException e) {
            fail(e.getMessage());
        } catch (Exception x){
            fail(x.getMessage());
        }
    }

    public void testAplFile() {
        loadTestFile();

        // get the index
        List<IndexElement> index = aplFile.getIndex();

        // create the new file
        AplFile newFile;
        try {
            newFile = new AplFile(sourceFile, index);

            Iterator<PeakList> it1 = aplFile.getPeakListIterator();
            Iterator<PeakList> it2 = newFile.getPeakListIterator();

            while (it1.hasNext() && it2.hasNext()) {
                assertEquals(it1.next().toString(), it2.next().toString());
            }
        } catch (JMzReaderException e) {
            fail(e.getMessage());
        }
    }

    public void testGetIndex() {
        try {
            loadTestFile();
            List<IndexElement> index = aplFile.getMsNIndexes(2);

            Spectrum s = aplFile.getSpectrumByIndex(3);

            Spectrum s1 = AplFile.getIndexedSpectrum(sourceFile, index.get(2));

            assertEquals(s.toString(), s1.toString());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
