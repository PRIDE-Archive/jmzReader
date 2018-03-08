package uk.ac.ebi.pride.tools.mgf_parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.tools.mgf_parser.model.Ms2Query;

import java.io.File;
import java.net.URL;
import java.util.Iterator;

public class PRIDETitleTest {

    private MgfFile mgfFile = new MgfFile();

    @Before
    public void setUp() throws Exception {
        loadTestFile();
    }

    private void loadTestFile() throws Exception  {
        URL testFile = getClass().getClassLoader().getResource("pride_title.mgf");
        Assert.assertNotNull("Error loading mgf test file", testFile);
        File sourceFile = new File(testFile.toURI());
        mgfFile = new MgfFile(sourceFile, true);
    }

    @Test
    public void testGetTitle() throws Exception {
        Iterator<Ms2Query> it = mgfFile.getMs2QueryIterator();
        Assert.assertTrue("NULL SPECTRUM Encountered!", it.hasNext());
        //Checks First title
        Assert.assertEquals(it.next().getTitle(),"id=PXD000021;PRIDE_Exp_Complete_Ac_27179.xml;spectrum=0");
    }
}
