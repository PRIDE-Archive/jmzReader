package uk.ac.ebi.pride.tools.apl_parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.tools.apl_parser.model.PeakList;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.jmzreader.model.IndexElement;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TestAplFile{

    private AplFile aplFile;
    private File sourceFile;

    @Before
    public  void setUp() {
        URL testFile = TestAplFile.class.getClassLoader().getResource("allSpectra.CID.ITMS.sil0.apl");
        Assert.assertNotNull("Error loading apl test file", testFile);

        try {
            sourceFile = new File(testFile.toURI());

            aplFile = new AplFile(sourceFile);
        } catch (Exception e) {
            System.out.println("Faild to load test file");
        }
    }

    @Test
    public void testGetFormat() {
        Assert.assertEquals("Andromeda peaklist file", aplFile.getFormat());
    }

    @Test
    public void testSetPeakLists() {
        PeakList query;
        try {
            query = new PeakList("peaklist start\nmz=1271.13935636076\ncharge=4\nheader=RawFile: 20080830_Orbi6_NaNa_SA_BiotechVariation_MH03_02 Index: 16236 Silind: 43885\n380.02725\t7.750772\n419.57687\t11.58331\n423.23862\t10.6417\npeaklist end\n", 1);

            ArrayList<PeakList> queries = new ArrayList<>();
            queries.add(query);

            aplFile.setPeakLists(queries);

            Assert.assertEquals("peaklist start\nmz=1271.13935636076\ncharge=4\nheader=RawFile: 20080830_Orbi6_NaNa_SA_BiotechVariation_MH03_02 Index: 16236 Silind: 43885\n380.02725\t7.750772\n419.57687\t11.58331\n423.23862\t10.6417\npeaklist end\n\n", aplFile.toString());
        } catch (JMzReaderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPeakListCount() {
        Assert.assertEquals(10, aplFile.getPeakListCount());
    }

    @Test
    public void testGetPeakList() {
        try {
            Assert.assertNotNull(aplFile.getPeakList(7));
        } catch (JMzReaderException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetPeakListIterator() {
        int queryCount = 0;

        try {
            for (PeakList q : aplFile.getPeakListIterator()) {
                Assert.assertNotNull(q);
                queryCount++;
            }
            Assert.assertEquals(10, queryCount);
        } catch (Exception x){
            System.out.println(x.getMessage());
        }
    }

    @Test
    public void testAplFile() {
        // get the index
        List<IndexElement> index = aplFile.getIndex();

        // create the new file
        AplFile newFile;
        try {
            newFile = new AplFile(sourceFile, index);

            Iterator<PeakList> it1 = aplFile.getPeakListIterator();
            Iterator<PeakList> it2 = newFile.getPeakListIterator();

            while (it1.hasNext() && it2.hasNext()) {
                Assert.assertEquals(it1.next().toString(), it2.next().toString());
            }
        } catch (JMzReaderException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetIndex() {
        try {
            List<IndexElement> index = aplFile.getMsNIndexes(2);

            Spectrum s = aplFile.getSpectrumByIndex(3);

            Spectrum s1 = AplFile.getIndexedSpectrum(sourceFile, index.get(2));

            Assert.assertEquals(s.toString(), s1.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
