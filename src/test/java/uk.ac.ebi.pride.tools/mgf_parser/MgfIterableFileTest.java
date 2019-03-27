package uk.ac.ebi.pride.tools.mgf_parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.*;

public class MgfIterableFileTest {

    private File sourceFile;
    private MgfIterableFile mgfFile;

    @Before
    public void setUp() throws Exception {
        loadTestFile();
    }

    private void loadTestFile() throws Exception {
        URL testFile = getClass().getClassLoader().getResource("F001257.mgf");
        Assert.assertNotNull("Error loading mgf test file", testFile);
        sourceFile = new File(testFile.toURI());
        mgfFile = new MgfIterableFile(sourceFile,true, false, true);
    }

    @Test
    public void next() {
        try {
            while (mgfFile.hasNext()){
                Spectrum spectrum = mgfFile.next();
                System.out.println(spectrum);
            }

        } catch (JMzReaderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void performanceTime() throws JMzReaderException, URISyntaxException {
        long time = System.currentTimeMillis();
        URL testFile = getClass().getClassLoader().getResource("F001257.mgf");
        Assert.assertNotNull("Error loading mgf test file", testFile);
        sourceFile = new File(testFile.toURI());
        mgfFile = new MgfIterableFile(sourceFile,true, false, true);
        while (mgfFile.hasNext()){
            Spectrum spectrum = mgfFile.next();
            System.out.println(spectrum.getId());
        }
        System.out.println(System.currentTimeMillis() - time);


    }
}