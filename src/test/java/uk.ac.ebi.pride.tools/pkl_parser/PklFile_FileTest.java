package uk.ac.ebi.pride.tools.pkl_parser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.jmzreader.model.IndexElement;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;
import uk.ac.ebi.pride.tools.pkl_parser.model.PklSpectrum;

public class PklFile_FileTest {

	private PklFile pklFile;
	private File sourceFile;
	private Map<Integer, IndexElement> fileIndex;

	@Before
	public void setUp() throws Exception {
		URL testFile = getClass().getClassLoader().getResource("testfile.pkl");
        Assert.assertNotNull("Error loading test file", testFile);
        
		try {
			sourceFile = new File(testFile.toURI());
			
			pklFile = new PklFile(sourceFile);
		} catch (URISyntaxException e) {
			System.out.println("Faild to load test file");
		}
	}

	@Test
	public void testGetSpectraCount() {
		Assert.assertEquals(3, pklFile.getSpectraCount());
	}

	@Test
	public void testGetSpectrum() {			
		PklSpectrum s;
		try {
			s = pklFile.getSpectrum(2);
		
			Assert.assertNotNull(s);
			Assert.assertEquals(11370039.0, s.getObservedIntensity(), 0.0);
			Assert.assertEquals(940.12, s.getObservedMZ(), 0.0);
			Assert.assertEquals(new Integer(2), s.getPrecursorCharge());
			
			Assert.assertEquals(491, s.getPeakList().size());
			
			Assert.assertEquals(533.4, s.getPeakList().get(212.87), 0.0);
		
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
		
		Assert.assertEquals(3, nSpecCount);
	}

	@Test
	public void testGetFileIndex() {
		fileIndex = pklFile.getFileIndex();
		
		Assert.assertEquals(3, fileIndex.size());
	}

	@Test
	public void testPklFile() {
		fileIndex = pklFile.getFileIndex();
		
		PklFile newPklFile;
		try {
			newPklFile = new PklFile(sourceFile, fileIndex);
		
			// make sure the two files return the same results
			Iterator<PklSpectrum> it = pklFile.getPklSpectrumIterator();
			Iterator<PklSpectrum> it2 = newPklFile.getPklSpectrumIterator();
			
			while (it.hasNext() && it2.hasNext()) {
				Assert.assertEquals(it.next().toString(), it2.next().toString());
			}
		} catch (JMzReaderException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGetSpectraIds() {
		List<String> ids = pklFile.getSpectraIds();
		
		Assert.assertEquals(3, ids.size());
		
		for (Integer i = 1; i <= 3; i++)
			Assert.assertEquals(i.toString(), ids.get(i - 1));
	}

	@Test
	public void testGetIndexedSpectrum() {
		List<IndexElement> index = pklFile.getMsNIndexes(2);
		
		try {
			Spectrum s1 = pklFile.getSpectrumByIndex(2);
			Spectrum s2 = PklFile.getIndexedSpectrum(sourceFile, index.get(1));
			
			Assert.assertEquals(s1.getPeakList(), s2.getPeakList());
		} catch (JMzReaderException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
