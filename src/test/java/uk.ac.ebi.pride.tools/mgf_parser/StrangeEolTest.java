package uk.ac.ebi.pride.tools.mgf_parser;

import java.io.File;
import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StrangeEolTest {

	private MgfFile mgfFile = new MgfFile();

	@Before
	public void setUp() throws Exception {
		loadTestFile();
	}

	private void loadTestFile() throws Exception{
		URL testFile = getClass().getClassLoader().getResource("strange_eol.mgf");
		Assert.assertNotNull("Error loading mgf test file", testFile);
		File sourceFile = new File(testFile.toURI());
		mgfFile = new MgfFile(sourceFile);
	}

	@Test
	public void testGetMs2QueryCount() {
		Assert.assertEquals(5, mgfFile.getMs2QueryCount());
	}
}
