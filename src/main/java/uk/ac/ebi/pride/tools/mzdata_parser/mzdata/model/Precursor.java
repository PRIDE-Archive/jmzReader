
package uk.ac.ebi.pride.tools.mzdata_parser.mzdata.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * The method of precursor ion selection and activation
 *
 * <p>Java class for precursorType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="precursorType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ionSelection" type="{}paramType"/&gt;
 *         &lt;element name="activation" type="{}paramType"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="msLevel" use="required" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}int" /&gt;
 *       &lt;attribute name="spectrumRef" use="required" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "precursorType", propOrder = {
    "ionSelection",
    "activation"
})
public class Precursor
    implements Serializable, MzDataObject
{

    private final static long serialVersionUID = 105L;
    @XmlElement(required = true)
    protected Param ionSelection;
    @XmlElement(required = true)
    protected Param activation;
    @XmlAttribute(required = true)
    protected int msLevel;
    @XmlAttribute(required = true)
    protected int spectrumRef;

    /**
     * Gets the value of the ionSelection property.
     * 
     * @return
     *     possible object is
     *     {@link Param }
     *     
     */
    public Param getIonSelection() {
        return ionSelection;
    }

    /**
     * Sets the value of the ionSelection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Param }
     *     
     */
    public void setIonSelection(Param value) {
        this.ionSelection = value;
    }

    /**
     * Gets the value of the activation property.
     * 
     * @return
     *     possible object is
     *     {@link Param }
     *     
     */
    public Param getActivation() {
        return activation;
    }

    /**
     * Sets the value of the activation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Param }
     *     
     */
    public void setActivation(Param value) {
        this.activation = value;
    }

    /**
     * Gets the value of the msLevel property.
     * 
     */
    public int getMsLevel() {
        return msLevel;
    }

    /**
     * Sets the value of the msLevel property.
     * 
     */
    public void setMsLevel(int value) {
        this.msLevel = value;
    }

    /**
     * Gets the value of the spectrumRef property.
     * 
     */
    public int getSpectrumRef() {
        return spectrumRef;
    }

    /**
     * Sets the value of the spectrumRef property.
     * 
     */
    public void setSpectrumRef(int value) {
        this.spectrumRef = value;
    }

}
