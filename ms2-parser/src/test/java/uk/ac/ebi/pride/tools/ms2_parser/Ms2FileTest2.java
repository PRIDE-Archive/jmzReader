package uk.ac.ebi.pride.tools.ms2_parser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

import junit.framework.TestCase;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.ms2_parser.model.Ms2Spectrum;

public class Ms2FileTest2 extends TestCase {
	Ms2File ms2File;

	@Override
	protected void setUp() throws Exception {
		URL testFile = getClass().getClassLoader().getResource("test2.ms2");
		assertNotNull("Error loading test directory", testFile);
		File sourceFile;

		try {
			sourceFile = new File(testFile.toURI());

			ms2File = new Ms2File(sourceFile);
		} catch (URISyntaxException e) {
			fail("Faild to load test file");
		}
	}

	public void testMs2File() {
		// check that the header was parsed correctly
		assertEquals(14, ms2File.getHeader().size());
		assertEquals("Data-Dependent", ms2File.getHeader().get("AcquisitionMethod"));
		assertEquals("RawConverter written by Lin He, 2014", ms2File.getHeader().get("Comments"));
		assertEquals("RawConverter modified by Yen-Yin Chu, 2015", ms2File.getHeader().get("Comments_1"));
		assertNotNull(ms2File.getHeader().get("Creation Date"));
		assertNotNull(ms2File.getExtractor());
		assertNotNull(ms2File.getExtractorVersion());
		assertNotNull(ms2File.getExtractorOptions());

		assertEquals(33521, ms2File.getSpectraCount());
	}

	public void testGetSpectrum() {
		Ms2Spectrum s;
		try {
			s = ms2File.getSpectrum(5);

			assertEquals(553.88568, s.getPrecursorMZ());
			assertEquals("2.21", s.getAdditionalInformation().get("RetTime"));
			assertEquals("FTMS + c NSI d Full ms2 553.89@hcd26.00 [76.67-1150.00]",
					s.getAdditionalInformation().get("Filter"));

			assertEquals(1, s.getCharges().size());
			assertEquals(1106.76408, s.getCharges().get(2));

			assertEquals(0, s.getChargeDependentData().size());

			assertEquals(12, s.getPeakList().size());
			assertEquals(2660.8, s.getPeakList().get(324.0381));
		} catch (JMzReaderException e) {
			fail(e.getMessage());
		}
	}

	public void testGetSpectrumIterator() {
		Iterator<Ms2Spectrum> it;
		try {
			it = ms2File.getMs2SpectrumIterator();

			int nSpecCount = 0;

			while (it.hasNext()) {
				assertNotNull(it.next());

				nSpecCount++;
			}

			assertEquals(33521, nSpecCount);
		} catch (JMzReaderException e) {
			fail(e.getMessage());
		}
	}
}
