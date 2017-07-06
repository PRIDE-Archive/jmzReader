package uk.ac.ebi.pride.tools.mzxml_parser;

import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.tools.jmzreader.model.IndexElement;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;
import uk.ac.ebi.pride.tools.mzxml_parser.mzxml.model.DataProcessing;
import uk.ac.ebi.pride.tools.mzxml_parser.mzxml.model.MsInstrument;
import uk.ac.ebi.pride.tools.mzxml_parser.mzxml.model.ParentFile;
import uk.ac.ebi.pride.tools.mzxml_parser.mzxml.model.Scan;

import junit.framework.TestCase;

public class MzXMLFileTest{


	private static MzXMLFile mzxmlFile;
	private File sourcefile;

	@Before
	public void setUp() throws Exception {

		 // create the mzxml dao
        try {
            URL testFile = getClass().getClassLoader().getResource("testfile.mzXML");
            Assert.assertNotNull("Error loading mzXML test file", testFile);
            sourcefile = new File(testFile.toURI());
            
            if (mzxmlFile == null)
            	mzxmlFile = new MzXMLFile(sourcefile);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
	}

	@Test
	public void testGetParentFile() {
		try {
			List<ParentFile> parentFiles = mzxmlFile.getParentFile();
			Assert.assertEquals(1, parentFiles.size());
			ParentFile file = parentFiles.get(0);
			Assert.assertNotNull(file);
			Assert.assertEquals("R1_RG59_B4_1.RAW", file.getFileName());
			Assert.assertEquals("RAWData", file.getFileType());
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to get parent file.");
		}
	}

	@Test
	public void testGetMsInstrument() {
		try {
			List<MsInstrument> instruments = mzxmlFile.getMsInstrument();
			
			Assert.assertNotNull(instruments);
			Assert.assertEquals(1, instruments.size());
			
			MsInstrument instrument = instruments.get(0);
			Assert.assertNotNull(instrument);
			Assert.assertNotNull(instrument.getMsDetector());
			Assert.assertEquals("unknown", instrument.getMsDetector().getTheValue());
			Assert.assertEquals("FTMS", instrument.getMsMassAnalyzer().getTheValue());
			
			Assert.assertNull(instrument.getMsInstrumentID());
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to get parent file.");
		}
	}

	@Test
	public void testGetDataProcessing() {
		try {
			List<DataProcessing> processings = mzxmlFile.getDataProcessing();
			
			Assert.assertEquals(1, processings.size());
			
			DataProcessing p = processings.get(0);
			Assert.assertNotNull(p);
			Assert.assertEquals("ReAdW", p.getSoftware().getName());
			Assert.assertNull(p.getIntensityCutoff());
			Assert.assertEquals(0, p.getProcessingOperationAndComment().size());
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to get parent file.");
		}
	}

	@Test
	public void testGetSpearation() {
		try {
			Assert.assertNull(mzxmlFile.getSpearation());
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to get parent file.");
		}
	}

	@Test
	public void testGetSpotting() {
		try {
			Assert.assertNull(mzxmlFile.getSpotting());
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to get parent file.");
		}
	}

	@Test
	public void testGetScanCount() {
		Assert.assertEquals(6449, mzxmlFile.getMS1ScanCount() + mzxmlFile.getMS2ScanCount());
	}

	@Test
	public void testGetScanIterator() {
		Iterable<Scan> scans = mzxmlFile.geMS1ScanIterator();
		
		int scanCount = 0;
		
		for (Scan s : scans) {
			scanCount++;
			Assert.assertNotNull(s);
			Assert.assertEquals(new Long(1), s.getMsLevel());
		}
		
		scans = mzxmlFile.getMS2ScanIterator();
		
		for (Scan s : scans) {
			scanCount++;
			Assert.assertNotNull(s);
			Assert.assertEquals(new Long(2), s.getMsLevel());
		}
		Assert.assertEquals(6449, scanCount);
	}

	@Test
	public void testGetSpectrumIterator() {
		Iterator<Spectrum> it = mzxmlFile.getSpectrumIterator();
		
		int count = 0;
		
		while (it.hasNext()) {
			Spectrum s = it.next();
			Assert.assertNotNull(s);
			count++;
		}
		Assert.assertEquals(6449, count);
	}

	@Test
	public void testConvertPeaksToMap() {
		Iterator<Scan> scans = mzxmlFile.getScanIterator();
		
		// just test the first scan
		Scan scan = scans.next();
		
		try {
			Assert.assertEquals(922.5859985351562, MzXMLFile.convertPeaksToMap(scan.getPeaks().get(0)).get(1186.860595703125), 0.0);
		} catch (MzXMLParsingException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public void testGetScanNumbers() {
		List<Long> scanNumbers = mzxmlFile.getScanNumbers();
		
		Assert.assertNotNull(scanNumbers);
		Assert.assertEquals(6449, scanNumbers.size());
	}
	
	public void testGetScanByNum() {
		try {
			Scan scan = mzxmlFile.getScanByNum((long) 2011);
			
			Assert.assertNotNull(scan);
			Assert.assertEquals(new Long(2), scan.getMsLevel());
			Assert.assertEquals(new Long(210), scan.getPeaksCount());
			Assert.assertEquals("PT2160.4S", scan.getRetentionTime().toString());
			Assert.assertEquals(6151.1f, scan.getTotIonCurrent(), 0.0);
			
			Assert.assertEquals(1, scan.getPeaks().size());
			Map<Double, Double> peakList = MzXMLFile.convertPeaksToMap(scan.getPeaks().get(0));
			
			Assert.assertEquals(210, peakList.size());
		} catch (MzXMLParsingException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGetIndexedSpectrum() {
		List<IndexElement> index = mzxmlFile.getMsNIndexes(2);
		try {
			//Spectrum s1 = mzxmlFile.getSpectrumByIndex(14);
			Spectrum s2 = MzXMLFile.getIndexedSpectrum(sourcefile, index.get(9));
			
			Assert.assertNotNull(s2);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
