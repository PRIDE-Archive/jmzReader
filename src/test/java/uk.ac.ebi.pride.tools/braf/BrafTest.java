package uk.ac.ebi.pride.tools.braf;

import java.io.File;
import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BrafTest {

	private File sourcefile;
	private File smallFile;
	private final int BUF_SIZE = 1024 * 1000;

	@Before
	public void setUp() throws Exception {
		URL testFile = getClass().getClassLoader().getResource("testfile.mzXML");
		URL smallFileUrl = getClass().getClassLoader().getResource("testfile.mzXML");
        Assert.assertNotNull("Error loading mgf test file", testFile);
        
		try {
			sourcefile = new File(testFile.toURI());	
			smallFile = new File(smallFileUrl.toURI());

			Assert.assertTrue(sourcefile.exists());
			Assert.assertTrue(smallFile.exists());
		} catch (Exception e) {
			System.out.println("Faild to load test file");
		}
	}

	@Test
	public void testFirstSteps() {
		try {
			BufferedRandomAccessFile reader = new BufferedRandomAccessFile(sourcefile, "r", BUF_SIZE);
			
			String line;
			int lineCount = 0;
			
			while ((line = reader.getNextLine()) != null) {
				// do something
				System.out.println(reader.getFilePointer() + ": " + line.trim());

				if (lineCount > 10)
					break;
				
				if (lineCount %1000000 == 0) {
					System.out.println(((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024*1024)) + " MB used");
				}
				
				lineCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testSmallFile() {
		int lineNumber = 0;

		long timeInit = System.currentTimeMillis();
		
		try {
			BufferedRandomAccessFile reader = new BufferedRandomAccessFile(smallFile, "r", BUF_SIZE);
			
			String line;
			long position = 0L;
			
			Assert.assertEquals(position, reader.getFilePointer());
			
			while ((line = reader.getNextLine()) != null) {
				if (lineNumber == 0)
					Assert.assertEquals("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>", line);
				if (lineNumber == 1)
					Assert.assertEquals("<mzXML xmlns=\"http://sashimi.sourceforge.net/schema_revision/mzXML_3.0\"", line);
				if (lineNumber == 2)
					Assert.assertEquals(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", line);
				if (lineNumber == 3)
					Assert.assertEquals(" xsi:schemaLocation=\"http://sashimi.sourceforge.net/schema_revision/mzXML_3.0 http://sashimi.sourceforge.net/schema_revision/mzXML_3.0/mzXML_idx_3.0.xsd\" >", line);
				
				lineNumber++;
				
				position += line.length() + 1; // add 1 for the \n character

			}
            reader.close();
        } catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}


		System.out.println(System.currentTimeMillis() - timeInit);
	}

//	@Test
//	public void testSeek() {
//		try {
//			BufferedRandomAccessFile reader = new BufferedRandomAccessFile(smallFile, "r", BUF_SIZE);
//			reader.seek(9);
//			String line = reader.readLine();
//			Assert.assertEquals("sion=\"1.0\" encoding=\"ISO-8859-1\"?>", line);
//			reader.seek(8);
//			line = reader.readLine();
//			Assert.assertEquals("rsion=\"1.0\" encoding=\"ISO-8859-1\"?>", line);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
//	}
}

