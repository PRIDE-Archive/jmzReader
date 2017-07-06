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
public class PRIDETitleTest {

    private MgfFile mgfFile;
    private File sourceFile;

    @Before
    public void setUp() throws Exception {
        mgfFile = new MgfFile();
    }

    @Test
    public void loadTestFile() {
        URL testFile = getClass().getClassLoader().getResource("pride_title.mgf");
        Assert.assertNotNull("Error loading mgf test file", testFile);

        try {
            sourceFile = new File(testFile.toURI());
            mgfFile = new MgfFile(sourceFile, true);
            Iterator<Ms2Query> it = mgfFile.getMs2QueryIterator();
            Assert.assertTrue("NULL SPECTRUM Encountered!", it.hasNext());

            //Checks First title
            Assert.assertEquals(it.next().getTitle(),"id=PXD000021;PRIDE_Exp_Complete_Ac_27179.xml;spectrum=0");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void testLoadFile() {
        loadTestFile();
    }

}
