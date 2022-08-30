
package uk.ac.ebi.pride.tools.mzxml_parser.mzxml.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import uk.ac.ebi.pride.tools.mzxml_parser.mzxml.xml.util.AnySimpleTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;<a href="http://www.w3.org/2001/XMLSchema&gt;anySimpleType">...</a>"&gt;
 *       &lt;attribute name="fileName" use="required" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}anyURI" /&gt;
 *       &lt;attribute name="fileType" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}string"&gt;
 *             &lt;enumeration value="RAWData"/&gt;
 *             &lt;enumeration value="processedData"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="fileSha1" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}string"&gt;
 *             &lt;length value="40"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "value"
})
public class ParentFile
    implements Serializable, MzXMLObject
{

    private final static long serialVersionUID = 320L;
    @XmlValue
    @XmlJavaTypeAdapter(AnySimpleTypeAdapter.class)
    @XmlSchemaType(name = "anySimpleType")
    protected String value;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "anyURI")
    protected java.lang.String fileName;
    @XmlAttribute(required = true)
    protected java.lang.String fileType;
    @XmlAttribute(required = true)
    protected java.lang.String fileSha1;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setFileName(java.lang.String value) {
        this.fileName = value;
    }

    /**
     * Gets the value of the fileType property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getFileType() {
        return fileType;
    }

    /**
     * Sets the value of the fileType property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setFileType(java.lang.String value) {
        this.fileType = value;
    }

    /**
     * Gets the value of the fileSha1 property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getFileSha1() {
        return fileSha1;
    }

    /**
     * Sets the value of the fileSha1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setFileSha1(java.lang.String value) {
        this.fileSha1 = value;
    }

}
