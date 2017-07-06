package uk.ac.ebi.pride.tools.ms2_parser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.ms2_parser.model.Ms2Spectrum;

public class Ms2FileTest2  {

    Ms2File ms2File;

    @Before
    public void setUp() throws Exception {
        URL testFile = getClass().getClassLoader().getResource("test2.ms2");
        Assert.assertNotNull("Error loading test directory", testFile);
        File sourceFile;

        try {
            sourceFile = new File(testFile.toURI());

            ms2File = new Ms2File(sourceFile);
        } catch (URISyntaxException e) {
            System.out.println("Faild to load test file");
        }
    }

    @Test
    public void testMs2File() {
        // check that the header was parsed correctly
        Assert.assertEquals(14, ms2File.getHeader().size());
        Assert.assertEquals("Data-Dependent", ms2File.getHeader().get("AcquisitionMethod"));
        Assert.assertEquals("RawConverter written by Lin He, 2014", ms2File.getHeader().get("Comments"));
        Assert.assertEquals("RawConverter modified by Yen-Yin Chu, 2015", ms2File.getHeader().get("Comments_1"));
        Assert.assertNotNull(ms2File.getHeader().get("Creation Date"));
        Assert.assertNotNull(ms2File.getExtractor());
        Assert.assertNotNull(ms2File.getExtractorVersion());
        Assert.assertNotNull(ms2File.getExtractorOptions());

        Assert.assertEquals(33521, ms2File.getSpectraCount());
    }

    @Test
    public void testGetSpectrum() {
        Ms2Spectrum s;
        try {
            s = ms2File.getSpectrum(5);

            Assert.assertEquals(553.88568, s.getPrecursorMZ(), 0.0);
            Assert.assertEquals("2.21", s.getAdditionalInformation().get("RetTime"));
            Assert.assertEquals("FTMS + c NSI d Full ms2 553.89@hcd26.00 [76.67-1150.00]",
                    s.getAdditionalInformation().get("Filter"));

            Assert.assertEquals(1, s.getCharges().size());
            Assert.assertEquals(1106.76408, s.getCharges().get(2), 0.0);

            Assert.assertEquals(0, s.getChargeDependentData().size());

            Assert.assertEquals(12, s.getPeakList().size());
            Assert.assertEquals(2660.8, s.getPeakList().get(324.0381), 0.0);
        } catch (JMzReaderException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetSpectrumIterator() {
        Iterator<Ms2Spectrum> it;
        it = ms2File.getMs2SpectrumIterator();
        int nSpecCount = 0;
        while (it.hasNext()) {
            Assert.assertNotNull(it.next());
            nSpecCount++;
        }
        Assert.assertEquals(33521, nSpecCount);
    }
}
