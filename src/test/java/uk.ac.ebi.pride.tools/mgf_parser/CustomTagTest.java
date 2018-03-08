package uk.ac.ebi.pride.tools.mgf_parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.tools.mgf_parser.model.Ms2Query;

import java.io.File;
import java.net.URL;
import java.util.Iterator;

public class CustomTagTest{

    private MgfFile  mgfFile = new MgfFile();

    @Before
    public void setUp() throws Exception {
        loadTestFile();
    }

    private void loadTestFile() throws Exception{
        URL testFile = getClass().getClassLoader().getResource("custom_tags.mgf");
        Assert.assertNotNull("Error loading mgf test file", testFile);
        File sourceFile;
        try {
            sourceFile = new File(testFile.toURI());
            mgfFile = new MgfFile(sourceFile, false);
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "Unknown attribute '_DISTILLER_MDRO_VERSION' encountered");
        }
        sourceFile = new File(testFile.toURI());
        mgfFile = new MgfFile(sourceFile, true);
    }

    @Test
    public void testgGtMs2QueryIterator() throws Exception{
        Iterator<Ms2Query> it = mgfFile.getMs2QueryIterator();
        Assert.assertNotNull("NULL SPECTRUM Encountered!", it.next());
    }

}
