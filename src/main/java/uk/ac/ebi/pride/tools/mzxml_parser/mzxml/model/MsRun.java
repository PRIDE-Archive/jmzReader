
package uk.ac.ebi.pride.tools.mzxml_parser.mzxml.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;
import uk.ac.ebi.pride.tools.mzxml_parser.mzxml.xml.util.NonNegativeIntegerAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="parentFile" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;anySimpleType"&gt;
 *                 &lt;attribute name="fileName" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" /&gt;
 *                 &lt;attribute name="fileType" use="required"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                       &lt;enumeration value="RAWData"/&gt;
 *                       &lt;enumeration value="processedData"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *                 &lt;attribute name="fileSha1" use="required"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                       &lt;length value="40"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="msInstrument" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="msManufacturer"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;extension base="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"&gt;
 *                         &lt;/extension&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="msModel" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"/&gt;
 *                   &lt;element name="msIonisation" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"/&gt;
 *                   &lt;element name="msMassAnalyzer"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;extension base="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"&gt;
 *                         &lt;/extension&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="msDetector" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"/&gt;
 *                   &lt;element ref="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}software"/&gt;
 *                   &lt;element name="msResolution" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType" minOccurs="0"/&gt;
 *                   &lt;element ref="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}operator" minOccurs="0"/&gt;
 *                   &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;element name="nameValue" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}namevalueType"/&gt;
 *                     &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;/sequence&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="msInstrumentID" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="dataProcessing" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element ref="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}software"/&gt;
 *                   &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;element name="processingOperation" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}namevalueType"/&gt;
 *                     &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;/sequence&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="intensityCutoff" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *                 &lt;attribute name="centroided" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                 &lt;attribute name="deisotoped" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                 &lt;attribute name="chargeDeconvoluted" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                 &lt;attribute name="spotIntegration" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="separation" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element ref="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}separationTechnique" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="spotting" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="plate" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="plateManufacturer" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"/&gt;
 *                             &lt;element name="plateModel" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"/&gt;
 *                             &lt;element name="pattern" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="spottingPattern" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"/&gt;
 *                                       &lt;element name="orientation"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;attribute name="firstSpotID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                               &lt;attribute name="secondSpotID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="spot" maxOccurs="unbounded"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="maldiMatrix" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"/&gt;
 *                                     &lt;/sequence&gt;
 *                                     &lt;attribute name="spotID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                     &lt;attribute name="spotXPosition" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                     &lt;attribute name="spotYPosition" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                     &lt;attribute name="spotDiameter" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="plateID" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *                           &lt;attribute name="spotXCount" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *                           &lt;attribute name="spotYCount" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="robot" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="robotManufacturer" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"/&gt;
 *                             &lt;element name="robotModel" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"/&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="timePerSpot" use="required" type="{http://www.w3.org/2001/XMLSchema}duration" /&gt;
 *                           &lt;attribute name="deadVolume" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element ref="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}scan" maxOccurs="unbounded"/&gt;
 *         &lt;element name="sha1" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;length value="40"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="scanCount" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *       &lt;attribute name="startTime" type="{http://www.w3.org/2001/XMLSchema}duration" /&gt;
 *       &lt;attribute name="endTime" type="{http://www.w3.org/2001/XMLSchema}duration" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "parentFile",
    "msInstrument",
    "dataProcessing",
    "separation",
    "spotting",
    "scan",
    "sha1"
})
@XmlRootElement(name = "msRun")
public class MsRun
    implements Serializable, MzXMLObject
{

    private final static long serialVersionUID = 320L;
    @XmlElement(required = true)
    protected List<ParentFile> parentFile;
    protected List<MsInstrument> msInstrument;
    @XmlElement(required = true)
    protected List<DataProcessing> dataProcessing;
    protected Separation separation;
    protected Spotting spotting;
    @XmlElement(required = true)
    protected List<Scan> scan;
    protected String sha1;
    @XmlAttribute
    @XmlJavaTypeAdapter(NonNegativeIntegerAdapter.class)
    @XmlSchemaType(name = "positiveInteger")
    protected Long scanCount;
    @XmlAttribute
    protected Duration startTime;
    @XmlAttribute
    protected Duration endTime;

    /**
     * Gets the value of the parentFile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parentFile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParentFile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParentFile }
     * 
     * 
     */
    public List<ParentFile> getParentFile() {
        if (parentFile == null) {
            parentFile = new ArrayList<>();
        }
        return this.parentFile;
    }

    /**
     * Gets the value of the msInstrument property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the msInstrument property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMsInstrument().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MsInstrument }
     * 
     * 
     */
    public List<MsInstrument> getMsInstrument() {
        if (msInstrument == null) {
            msInstrument = new ArrayList<>();
        }
        return this.msInstrument;
    }

    /**
     * Gets the value of the dataProcessing property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataProcessing property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataProcessing().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataProcessing }
     * 
     * 
     */
    public List<DataProcessing> getDataProcessing() {
        if (dataProcessing == null) {
            dataProcessing = new ArrayList<>();
        }
        return this.dataProcessing;
    }

    /**
     * Gets the value of the separation property.
     * 
     * @return
     *     possible object is
     *     {@link Separation }
     *     
     */
    public Separation getSeparation() {
        return separation;
    }

    /**
     * Sets the value of the separation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Separation }
     *     
     */
    public void setSeparation(Separation value) {
        this.separation = value;
    }

    /**
     * Gets the value of the spotting property.
     * 
     * @return
     *     possible object is
     *     {@link Spotting }
     *     
     */
    public Spotting getSpotting() {
        return spotting;
    }

    /**
     * Sets the value of the spotting property.
     * 
     * @param value
     *     allowed object is
     *     {@link Spotting }
     *     
     */
    public void setSpotting(Spotting value) {
        this.spotting = value;
    }

    /**
     * Gets the value of the scan property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scan property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScan().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Scan }
     * 
     * 
     */
    public List<Scan> getScan() {
        if (scan == null) {
            scan = new ArrayList<>();
        }
        return this.scan;
    }

    /**
     * Gets the value of the sha1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSha1() {
        return sha1;
    }

    /**
     * Sets the value of the sha1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSha1(String value) {
        this.sha1 = value;
    }

    /**
     * Gets the value of the scanCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Long getScanCount() {
        return scanCount;
    }

    /**
     * Sets the value of the scanCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScanCount(Long value) {
        this.scanCount = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setStartTime(Duration value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setEndTime(Duration value) {
        this.endTime = value;
    }

}
