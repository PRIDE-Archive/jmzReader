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

public class DtaFile_DirectoryTest {

	private DtaFile dtaFile;

	@Before
	public void setUp() throws Exception {
		URL testFile = getClass().getClassLoader().getResource("QSTAR1a");
        Assert.assertNotNull("Error loading test directory", testFile);
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
		Assert.assertEquals(10, dtaFile.getSpectraCount());
	}

	@Test
	public void testGetSpectrum() {
		DtaSpectrum s;
		try {
			s = dtaFile.getDtaSpectrum("1a.280.2.0792.0792.2.dta");
			Assert.assertNotNull(s);
			Assert.assertEquals(559.3281, s.getMhMass(), 0.0);
			Assert.assertEquals(new Integer(2), s.getPrecursorCharge());
			Assert.assertEquals(26, s.getPeakList().size());
			Assert.assertEquals(2.0, s.getPeakList().get(276.1458), 0.0);
		} catch (JMzReaderException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSpectrumIterator() {
		Iterator<DtaSpectrum> it = dtaFile.getDtaSpectrumIterator();
		int nSpecCount = 0;
		while (it.hasNext()) {
			Assert.assertNotNull(it.next());
			nSpecCount++;
		}
		Assert.assertEquals(10, nSpecCount);
	}

	@Test
	public void testGetSpectraIds() {
		List<String> ids = dtaFile.getSpectraIds();
		Assert.assertEquals(10, ids.size());
		Assert.assertTrue(ids.contains("1a.278.2.1902.1902.2.dta"));
	}

}
