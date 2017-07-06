package uk.ac.ebi.pride.tools.mgf_parser;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StrangeEolTest {

	private MgfFile mgfFile;
	private File sourceFile;

	@Before
	public void setUp() throws Exception {
		mgfFile = new MgfFile();
	}

	@Test
	public void loadTestFile() {
		URL testFile = getClass().getClassLoader().getResource("strange_eol.mgf");
        Assert.assertNotNull("Error loading mgf test file", testFile);
        
		try {
			sourceFile = new File(testFile.toURI());
			
			mgfFile = new MgfFile(sourceFile);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Faild to load test file");
		}
	}

	@Test
	public void testLoadFile() {
		loadTestFile();
		Assert.assertEquals(5, mgfFile.getMs2QueryCount());
	}
}
