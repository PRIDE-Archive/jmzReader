
package uk.ac.ebi.pride.tools.mzdata_parser.mzdata.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Description of the parameters for the mass spectrometer for a given acquisition (or list of)
 *
 * <p>Java class for spectrumSettingsType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="spectrumSettingsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="acqSpecification" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="acquisition" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;extension base="{}paramType"&gt;
 *                           &lt;attribute name="acqNumber" use="required" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}int" /&gt;
 *                         &lt;/extension&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="spectrumType" use="required"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}string"&gt;
 *                       &lt;enumeration value="discrete"/&gt;
 *                       &lt;enumeration value="continuous"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *                 &lt;attribute name="methodOfCombination" use="required" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}string" /&gt;
 *                 &lt;attribute name="count" use="required" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}int" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="spectrumInstrument"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{}paramType"&gt;
 *                 &lt;attribute name="msLevel" use="required" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}int" /&gt;
 *                 &lt;attribute name="mzRangeStart" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}float" /&gt;
 *                 &lt;attribute name="mzRangeStop" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}float" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "spectrumSettingsType", propOrder = {
    "acqSpecification",
    "spectrumInstrument"
})
public class SpectrumSettings
    implements Serializable, MzDataObject
{

    private final static long serialVersionUID = 105L;
    protected SpectrumSettings.AcqSpecification acqSpecification;
    @XmlElement(required = true)
    protected SpectrumSettings.SpectrumInstrument spectrumInstrument;

    /**
     * Gets the value of the acqSpecification property.
     * 
     * @return
     *     possible object is
     *     {@link SpectrumSettings.AcqSpecification }
     *     
     */
    public SpectrumSettings.AcqSpecification getAcqSpecification() {
        return acqSpecification;
    }

    /**
     * Sets the value of the acqSpecification property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpectrumSettings.AcqSpecification }
     *     
     */
    public void setAcqSpecification(SpectrumSettings.AcqSpecification value) {
        this.acqSpecification = value;
    }

    /**
     * Gets the value of the spectrumInstrument property.
     * 
     * @return
     *     possible object is
     *     {@link SpectrumSettings.SpectrumInstrument }
     *     
     */
    public SpectrumSettings.SpectrumInstrument getSpectrumInstrument() {
        return spectrumInstrument;
    }

    /**
     * Sets the value of the spectrumInstrument property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpectrumSettings.SpectrumInstrument }
     *     
     */
    public void setSpectrumInstrument(SpectrumSettings.SpectrumInstrument value) {
        this.spectrumInstrument = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="acquisition" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;extension base="{}paramType"&gt;
     *                 &lt;attribute name="acqNumber" use="required" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}int" /&gt;
     *               &lt;/extension&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="spectrumType" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}string"&gt;
     *             &lt;enumeration value="discrete"/&gt;
     *             &lt;enumeration value="continuous"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *       &lt;attribute name="methodOfCombination" use="required" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}string" /&gt;
     *       &lt;attribute name="count" use="required" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}int" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "acquisition"
    })
    public static class AcqSpecification
        implements Serializable, MzDataObject
    {

        private final static long serialVersionUID = 105L;
        @XmlElement(required = true)
        protected List<SpectrumSettings.AcqSpecification.Acquisition> acquisition;
        @XmlAttribute(required = true)
        protected String spectrumType;
        @XmlAttribute(required = true)
        protected String methodOfCombination;
        @XmlAttribute(required = true)
        protected int count;

        /**
         * Gets the value of the acquisition property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the acquisition property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAcquisition().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SpectrumSettings.AcqSpecification.Acquisition }
         * 
         * 
         */
        public List<SpectrumSettings.AcqSpecification.Acquisition> getAcquisition() {
            if (acquisition == null) {
                acquisition = new ArrayList<>();
            }
            return this.acquisition;
        }

        /**
         * Gets the value of the spectrumType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSpectrumType() {
            return spectrumType;
        }

        /**
         * Sets the value of the spectrumType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSpectrumType(String value) {
            this.spectrumType = value;
        }

        /**
         * Gets the value of the methodOfCombination property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMethodOfCombination() {
            return methodOfCombination;
        }

        /**
         * Sets the value of the methodOfCombination property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMethodOfCombination(String value) {
            this.methodOfCombination = value;
        }

        /**
         * Gets the value of the count property.
         * 
         */
        public int getCount() {
            return count;
        }

        /**
         * Sets the value of the count property.
         * 
         */
        public void setCount(int value) {
            this.count = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;extension base="{}paramType"&gt;
         *       &lt;attribute name="acqNumber" use="required" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}int" /&gt;
         *     &lt;/extension&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Acquisition
            extends Param
            implements Serializable
        {

            private final static long serialVersionUID = 105L;
            @XmlAttribute(required = true)
            protected int acqNumber;

            /**
             * Gets the value of the acqNumber property.
             * 
             */
            public int getAcqNumber() {
                return acqNumber;
            }

            /**
             * Sets the value of the acqNumber property.
             * 
             */
            public void setAcqNumber(int value) {
                this.acqNumber = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;extension base="{}paramType"&gt;
     *       &lt;attribute name="msLevel" use="required" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}int" /&gt;
     *       &lt;attribute name="mzRangeStart" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}float" /&gt;
     *       &lt;attribute name="mzRangeStop" type="{<a href="http://www.w3.org/2001/XMLSchema">...</a>}float" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SpectrumInstrument
        extends Param
        implements Serializable
    {

        private final static long serialVersionUID = 105L;
        @XmlAttribute(required = true)
        protected int msLevel;
        @XmlAttribute
        protected Float mzRangeStart;
        @XmlAttribute
        protected Float mzRangeStop;

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
         * Gets the value of the mzRangeStart property.
         * 
         * @return
         *     possible object is
         *     {@link Float }
         *     
         */
        public Float getMzRangeStart() {
            return mzRangeStart;
        }

        /**
         * Sets the value of the mzRangeStart property.
         * 
         * @param value
         *     allowed object is
         *     {@link Float }
         *     
         */
        public void setMzRangeStart(Float value) {
            this.mzRangeStart = value;
        }

        /**
         * Gets the value of the mzRangeStop property.
         * 
         * @return
         *     possible object is
         *     {@link Float }
         *     
         */
        public Float getMzRangeStop() {
            return mzRangeStop;
        }

        /**
         * Sets the value of the mzRangeStop property.
         * 
         * @param value
         *     allowed object is
         *     {@link Float }
         *     
         */
        public void setMzRangeStop(Float value) {
            this.mzRangeStop = value;
        }

    }

}
