package uk.ac.ebi.pride.tools.pride_wrapper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.tools.jmzreader.model.IndexElement;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;
import uk.ac.ebi.pride.tools.jmzreader.model.impl.CvParam;
import uk.ac.ebi.pride.tools.jmzreader.model.impl.ParamGroup;

import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Rui Wang
 * @version $Id$
 */
public class PRIDEXmlWrapperTest {

    private PRIDEXmlWrapper wrapper;

    @Before
    public void setUp() throws Exception {
        URL url = PRIDEXmlWrapperTest.class.getClassLoader().getResource("test-pride.xml");
        if (url == null) {
            throw new IllegalStateException("no file for input found!");
        }
        File inputFile = new File(url.toURI());
        wrapper = new PRIDEXmlWrapper(inputFile);
    }

    @After
    public void tearDown() throws Exception {
        wrapper = null;
    }

    @Test
    public void testGetSpectraCount() throws Exception {
        Assert.assertTrue("The number of spectrum should be 100", wrapper.getSpectraCount() == 100);
    }

    @Test
    public void testAcceptsFile() throws Exception {
        Assert.assertTrue("This wrapper should allow file", wrapper.acceptsFile());
    }

    @Test
    public void testAcceptsDirectory() throws Exception {
        Assert.assertFalse("This wrapper should not allow folder", wrapper.acceptsDirectory());
    }

    @Test
    public void testGetSpectraIds() throws Exception {
        List<String> ids = wrapper.getSpectraIds();
        // check size
        Assert.assertEquals(100, ids.size());
        // check existence
        Assert.assertTrue("This wrapper should contain spectrum", ids.contains("2"));
    }

    @Test
    public void testGetSpectrumById() throws Exception {
        Spectrum spectrum = wrapper.getSpectrumById("2");
        // id
        Assert.assertEquals("2", spectrum.getId());
        // ms level
        Assert.assertEquals(0, spectrum.getMsLevel().intValue());
        // precursor charge
        Assert.assertEquals(2, spectrum.getPrecursorCharge().intValue());
        // precursor m/z
        Assert.assertEquals(898, spectrum.getPrecursorMZ().intValue());
        // precursor intensity
        Assert.assertNull(spectrum.getPrecursorIntensity());
        // param group
        ParamGroup paramGroup = spectrum.getAdditional();
        Assert.assertNotNull(paramGroup);
        // cv params
        List<CvParam> cvParams = paramGroup.getCvParams();
    }

    @Test
    public void testGetSpectrumByIndex() throws Exception {
        Spectrum spectrum = wrapper.getSpectrumByIndex(1);

        // id
        Assert.assertEquals("2", spectrum.getId());
        // ms level
        Assert.assertEquals(0, spectrum.getMsLevel().intValue());
        // precursor charge
        Assert.assertEquals(2, spectrum.getPrecursorCharge().intValue());
        // precursor m/z
        Assert.assertEquals(898, spectrum.getPrecursorMZ().intValue());
        // precursor intensity
        Assert.assertNull(spectrum.getPrecursorIntensity());
        // param group
        ParamGroup paramGroup = spectrum.getAdditional();
        Assert.assertNotNull(paramGroup);
        // cv params
        List<CvParam> cvParams = paramGroup.getCvParams();
    }

    @Test
    public void testGetSpectrumIterator() throws Exception {
        Iterator<Spectrum> iter = wrapper.getSpectrumIterator();

        Assert.assertNotNull(iter);

        iter.next();

        Spectrum spectrum = iter.next();

        // id
        Assert.assertEquals("2", spectrum.getId());
        // ms level
        Assert.assertEquals(0, spectrum.getMsLevel().intValue());
        // precursor charge
        Assert.assertEquals(2, spectrum.getPrecursorCharge().intValue());
        // precursor m/z
        Assert.assertEquals(898, spectrum.getPrecursorMZ().intValue());
        // precursor intensity
        Assert.assertNull(spectrum.getPrecursorIntensity());
        // param group
        ParamGroup paramGroup = spectrum.getAdditional();
        Assert.assertNotNull(paramGroup);
        // cv params
        List<CvParam> cvParams = paramGroup.getCvParams();
    }

    @Test
    public void testGetMsNIndexes() throws Exception {
        List<IndexElement> indexElements = wrapper.getMsNIndexes(0);
        Assert.assertEquals(1, indexElements.size());
    }

    @Test
    public void testGetMsLevels() throws Exception {
        List<Integer> msLevels = wrapper.getMsLevels();
        Assert.assertEquals(2, msLevels.size());
        Assert.assertTrue(msLevels.contains(2));
        Assert.assertTrue(msLevels.contains(0));
    }

    @Test
    public void testGetIndexElementForIds() throws Exception {
        Map<String, IndexElement> indexElements = wrapper.getIndexElementForIds();
        Assert.assertNotNull(indexElements);
        Assert.assertNotNull(indexElements.get("2"));
    }
}
