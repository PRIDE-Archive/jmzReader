package uk.ac.ebi.pride.tools.mzdata_parser;

import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.junit.Assert;
import org.junit.Before;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.jmzreader.model.IndexElement;
import uk.ac.ebi.pride.tools.mzdata_parser.mzdata.model.CvLookup;
import uk.ac.ebi.pride.tools.mzdata_parser.mzdata.model.MzData.Description;
import uk.ac.ebi.pride.tools.mzdata_parser.mzdata.model.Spectrum;

public class MzDataFileTest{

	private static File sourcefile;
	private static MzDataFile mzDataFile;

	@Before
	public void setUp() throws Exception {
		if (mzDataFile != null)
			return;
		
		URL testFile = getClass().getClassLoader().getResource("PRIDE_Exp_mzData_Ac_8869.xml");
        Assert.assertNotNull("Error loading mzData test file", testFile);
        
		try {
			sourcefile = new File(testFile.toURI());
			
			Assert.assertNotNull(sourcefile);
			
			mzDataFile = new MzDataFile(sourcefile);
			
			Assert.assertNotNull(mzDataFile);
		} catch (Exception e) {
			System.out.println("Faild to load test file");
		}
	}

	public void testGetMzDataAttributes() {
		Map<String, String> attr = mzDataFile.getMzDataAttributes();
		
		Assert.assertNotNull(attr);
		Assert.assertEquals("1.05", attr.get("version"));
		Assert.assertEquals("8869", attr.get("accessionNumber"));
	}

	public void testGetCvLookups() {
		List<CvLookup> cvs;
		try {
			cvs = mzDataFile.getCvLookups();
			
			Assert.assertNotNull(cvs);
			Assert.assertEquals(6, cvs.size());
			Assert.assertEquals("BRENDA tissue / enzyme source", cvs.get(0).getFullName());
			Assert.assertEquals("http://psidev.sourceforge.net/ontology/", cvs.get(5).getAddress());
		} catch (JMzReaderException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void testGetDescription() {
		try {
			Description description = mzDataFile.getDescription();
			
			Assert.assertNotNull(description);
			Assert.assertEquals("MS-060322ng_DC-LPS-CYT", description.getAdmin().getSampleName());
			Assert.assertEquals(4, description.getAdmin().getSampleDescription().getCvParams().size());
			Assert.assertEquals("GO:0005737", description.getAdmin().getSampleDescription().getCvParams().get(2).getAccession());
			
			Assert.assertEquals(1, description.getAdmin().getContact().size());
			Assert.assertEquals("Christopher Gerner", description.getAdmin().getContact().get(0).getName());
			Assert.assertEquals("XCT Ultra, Agilent", description.getInstrument().getInstrumentName());
			Assert.assertEquals(3, description.getInstrument().getSource().getCvParams().size());
			Assert.assertEquals(1, description.getInstrument().getAnalyzerList().getAnalyzer().size());
			
			Assert.assertEquals("A.03.03", description.getDataProcessing().getSoftware().getVersion());
		} catch (JMzReaderException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void testGetSpectraCount() {
		Assert.assertEquals(2139, mzDataFile.getSpectraCount());
	}

	public void testGetSpectraIds() {
		List<String> ids = mzDataFile.getSpectraIds();
		
		Assert.assertEquals(2139, ids.size());
		
		for (Integer i = 1; i <= 2139; i++)
			Assert.assertEquals(i.toString(), ids.get(i - 1));
	}

	public void testGetMzDataSpectrumById() {
		try {
			Spectrum s = mzDataFile.getMzDataSpectrumById(3);
			
			Assert.assertNotNull(s);
			Assert.assertEquals(336 * 4, s.getIntenArrayBinary().getData().getValue().length);
			Assert.assertEquals(1800.0f, s.getSpectrumDesc().getSpectrumSettings().getSpectrumInstrument().getMzRangeStop(),0.0);
		} catch (JMzReaderException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void testGetSpectrumById() {
		try {
			uk.ac.ebi.pride.tools.jmzreader.model.Spectrum s = mzDataFile.getSpectrumById("3");			
			
			Assert.assertNotNull(s);
			Assert.assertEquals(336, s.getPeakList().size());
			Assert.assertNull(s.getPrecursorCharge());
			Assert.assertNull(s.getPrecursorIntensity());
			Assert.assertNull(s.getPrecursorMZ());
		} catch (JMzReaderException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void testGetMzDataSpectrumByIndex() {
		try {
			Spectrum s = mzDataFile.getMzDataSpectrumByIndex(3);
			
			Assert.assertNotNull(s);
			Assert.assertEquals(336 * 4, s.getIntenArrayBinary().getData().getValue().length);
			Assert.assertEquals(1800.0f, s.getSpectrumDesc().getSpectrumSettings().getSpectrumInstrument().getMzRangeStop(), 0.0);
		} catch (JMzReaderException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void testGetSpectrumByIndex() {
		try {
			uk.ac.ebi.pride.tools.jmzreader.model.Spectrum s = mzDataFile.getSpectrumByIndex(3);
			Assert.assertNotNull(s);
			Assert.assertEquals(336, s.getPeakList().size());
			Assert.assertNull(s.getPrecursorCharge());
			Assert.assertNull(s.getPrecursorIntensity());
			Assert.assertNull(s.getPrecursorMZ());
		} catch (JMzReaderException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void testGetSpectrumIterator() {
		Iterator<uk.ac.ebi.pride.tools.jmzreader.model.Spectrum> it = mzDataFile.getSpectrumIterator();
		
		int count = 0;
		
		while (it.hasNext()) {
			uk.ac.ebi.pride.tools.jmzreader.model.Spectrum s = it.next();
			
			Assert.assertNotNull(s);
			
			Assert.assertEquals("" + (count + 1), s.getId());
			count++;
		}
		
		Assert.assertEquals(2139, count);
	}

	public void testGetMzDataSpectrumIterator() {
		Iterator<Spectrum> it = mzDataFile.getMzDataSpectrumIterator();
		
		int count = 0;
		
		while (it.hasNext()) {
			Spectrum s = it.next();
			
			Assert.assertNotNull(s);
			
			Assert.assertEquals(count + 1, s.getId());
			
			count++;
		}
		
		Iterator<uk.ac.ebi.pride.tools.jmzreader.model.Spectrum> it2 = mzDataFile.getSpectrumIterator();
		
		while (it.hasNext()) {
			uk.ac.ebi.pride.tools.jmzreader.model.Spectrum s = it2.next();
			
			Assert.assertNotNull(s);
			
			Assert.assertEquals(2, s.getMsLevel().intValue());
		}
		
		Assert.assertEquals(2139, count);
	}

	public void testGetIndexedSpectrum() {
		try {
			List<IndexElement> index = mzDataFile.getMsNIndexes(2);
			
			Assert.assertNotNull(index);
			
			uk.ac.ebi.pride.tools.jmzreader.model.Spectrum s1 = mzDataFile.getSpectrumByIndex(3);
			uk.ac.ebi.pride.tools.jmzreader.model.Spectrum s2 = MzDataFile.getIndexedSpectrum(sourcefile, index.get(2));
			
			Assert.assertEquals(s1.getPeakList(), s2.getPeakList());
			Assert.assertEquals(s1.getPrecursorMZ(), s2.getPrecursorMZ());
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
