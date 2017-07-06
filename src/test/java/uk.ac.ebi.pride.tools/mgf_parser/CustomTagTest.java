package uk.ac.ebi.pride.tools.mgf_parser;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.tools.mgf_parser.model.Ms2Query;

import java.io.File;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: rcote
 * Date: 20/06/12
 * Time: 12:07
 */
public class CustomTagTest{

    private MgfFile mgfFile;

    @Before
    public void setUp() throws Exception {
        mgfFile = new MgfFile();
    }

    @Test
    public void loadTestFile() {
        URL testFile = getClass().getClassLoader().getResource("custom_tags.mgf");
        Assert.assertNotNull("Error loading mgf test file", testFile);

        File sourceFile;
        try {
            sourceFile = new File(testFile.toURI());
            mgfFile = new MgfFile(sourceFile, false);
        } catch (Exception e) {
            System.out.println(e.getMessage() + " thrown and captured");
            Assert.assertEquals(e.getMessage(), "Unknown attribute '_DISTILLER_MDRO_VERSION' encountered");
        }

        try {
            sourceFile = new File(testFile.toURI());
            mgfFile = new MgfFile(sourceFile, true);
            Iterator<Ms2Query> it = mgfFile.getMs2QueryIterator();
            Assert.assertNotNull("NULL SPECTRUM Encountered!", it.next());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testLoadFile() {
        loadTestFile();
    }

}
