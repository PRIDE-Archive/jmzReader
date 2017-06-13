package uk.ac.ebi.pride.tools.jmzreader.model;

/**
 * Describes a param used to report
 * additional data found in the supported
 * file formats.
 * 
 * @author jg
 *
 */
public interface Param {
	/**
	 * Returns the parameter's name.
	 * @return The parameter's name
	 */
    String getName();
	
	/**
	 * Return's the parameter's value.
	 * @return The parameter's value.
	 */
	String getValue();
	
	/**
	 * Sets the parameter's name.
	 * @param name The parameter's new name.
	 */
	void setName(String name);
	
	/**
	 * Sets the parameter's value.
	 * @param value The parameter's new value.
	 */
	void setValue(String value);
}
