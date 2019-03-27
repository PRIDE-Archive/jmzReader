package uk.ac.ebi.pride.tools.jmzreader;


import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 * The {@link JMzIterableReader} is an interface that only provides methods to read one Spectrum after another
 * This interface and the classes behind allow to read an entire file without needs to index the entire file. For
 * indexed and random access to peak list files please use the JmzReader interface.
 *
 * @author yperez
 */
public interface JMzIterableReader {


    /**
     * This method returns if the collection has more spectra after read the current
     * {@link Spectrum}
     *
     * @return Boolean if the file contains another spectra
     */
    boolean hasNext();

    /**
     * Return the next Spectra
     * @return
     */
    Spectrum next() throws JMzReaderException;

    /**
     * Return the batch of spectra
     * @param batch Number of spectra tu be retrieve
     * @return
     */

    Stream<Spectrum> next(int batch);

    /**
     * Close the respective channel and File use to parse the Spectra
     */
    void close() throws JMzReaderException;

}
