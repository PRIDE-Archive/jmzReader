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

public class Ms2FileTest {

	Ms2File ms2File;

	@Before
	public void setUp() throws Exception {
		URL testFile = getClass().getClassLoader().getResource("test.ms2");
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
		Assert.assertEquals("700", ms2File.getHeader().get("MinimumMass"));
		Assert.assertEquals("RawXtract written by John Venable, 2003", ms2File.getHeader().get("Comments"));
		
		Assert.assertNotNull(ms2File.getCreationDate());
		Assert.assertNotNull(ms2File.getExtractor());
		Assert.assertNotNull(ms2File.getExtractorVersion());
		Assert.assertNotNull(ms2File.getExtractorOptions());
		
		Assert.assertEquals(16421, ms2File.getSpectraCount());
	}

	@Test
	public void testGetSpectrum() {
		Ms2Spectrum s;
		try {
			s = ms2File.getSpectrum(5);
			
			Assert.assertEquals(492.41000, s.getPrecursorMZ(), 0.0);
			Assert.assertEquals("0.33", s.getAdditionalInformation().get("RetTime"), "0.33");
			Assert.assertEquals("hupo_06_itms.ms1", s.getAdditionalInformation().get("PrecursorFile"));
			
			Assert.assertEquals(3, s.getCharges().size());
			Assert.assertEquals(1966.61652, s.getCharges().get(4), 0.0);
			
			Assert.assertEquals(0, s.getChargeDependentData().size());
			
			Assert.assertEquals(35, s.getPeakList().size());
			Assert.assertEquals(2.2, s.getPeakList().get(336.9019), 0.0);
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
		Assert.assertEquals(16421, nSpecCount);
	}
}
