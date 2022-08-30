package uk.ac.ebi.pride.tools.dta_parser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.tools.dta_parser.model.DtaSpectrum;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;

public class DtaFile_FileTest {

    DtaFile dtaFile;

    @Before
    public void setUp() throws Exception {
        URL testFile = getClass().getClassLoader().getResource("QSTAR1a_concat");
        Assert.assertNotNull("Error loading test file", testFile);
        File sourceFile;

        try {
            sourceFile = new File(testFile.toURI());

            dtaFile = new DtaFile(sourceFile);
        } catch (URISyntaxException e) {
            System.out.println("Faild to load test file");
        }
    }

    @Test
    public void testGetSpectraCount() {
        Assert.assertEquals(389, dtaFile.getSpectraCount());
    }

    @Test
    public void testGetSpectrum() {
        DtaSpectrum s;
        try {
            s = dtaFile.getDtaSpectrum(3);
            Assert.assertEquals(2010.9163, s.getMhMass(), 0.0);
            Assert.assertEquals(new Integer(2), s.getPrecursorCharge());
            Assert.assertEquals(51, s.getPeakList().size());
            Assert.assertEquals(3.0, s.getPeakList().get(462.2336), 0.0);
        } catch (JMzReaderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetSpectrumIterator() {
        Iterator<DtaSpectrum> it = dtaFile.getDtaSpectrumIterator();

        int count = 0;

        while (it.hasNext()) {
            Assert.assertNotNull(it.next());
            count++;
        }

        Assert.assertEquals(389, count);
    }

    @Test
    public void testGetSpectraIds() {
        List<String> ids = dtaFile.getSpectraIds();
        Assert.assertEquals(389, ids.size());
        for (int i = 0; i < 389; i++) {
            String id = ids.get(i);
            int index = i + 1;
            Assert.assertEquals(Integer.toString(index), id);
        }
    }

}
