package uk.ac.ebi.pride.tools.pkl_parser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.pkl_parser.model.PklSpectrum;

public class PklFile_DirTest {

	private PklFile pklFile;

	@Before
	public void setUp() throws Exception {
		URL testFile = getClass().getClassLoader().getResource("pkl_dir");
        Assert.assertNotNull("Error loading test file", testFile);
        File sourceFile;
        
		try {
			sourceFile = new File(testFile.toURI());
			pklFile = new PklFile(sourceFile);
		} catch (URISyntaxException e) {
			System.out.println("Faild to load test file");
		}
	}

	@Test
    public void testGetSpectraCount() {
		Assert.assertEquals(16, pklFile.getSpectraCount());
	}

	@Test
    public void testGetSpectrum() {
		PklSpectrum s;
		try {
			s = pklFile.getSpectrum("110208A.1708.1708.0.pkl");
			
			Assert.assertNotNull(s);
			Assert.assertEquals(648.20, s.getObservedMZ(), 0.0);
			Assert.assertEquals(171079939.0, s.getObservedIntensity(), 0.0);
			
			Assert.assertEquals(399, s.getPeakList().size());
			Assert.assertEquals(19208.9, s.getPeakList().get(235.03), 0.0);
		} catch (JMzReaderException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGetSpectrumIterator() {
		Iterator<PklSpectrum> it = pklFile.getPklSpectrumIterator();
		int nSpecCount = 0;
		while (it.hasNext()) {
			Assert.assertNotNull(it.next());
			nSpecCount++;
		}
		Assert.assertEquals(16, nSpecCount);
	}

	@Test
	public void testGetSpectraIds() {
		List<String> ids = pklFile.getSpectraIds();
		Assert.assertEquals(16, ids.size());
		Assert.assertTrue(ids.contains("110208A.1715.1715.2.pkl"));
	}

}
