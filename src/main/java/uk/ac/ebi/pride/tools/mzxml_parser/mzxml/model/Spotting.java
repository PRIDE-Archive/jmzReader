
package uk.ac.ebi.pride.tools.mzxml_parser.mzxml.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="plate" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="plateManufacturer" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"/&gt;
 *                   &lt;element name="plateModel" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"/&gt;
 *                   &lt;element name="pattern" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="spottingPattern" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"/&gt;
 *                             &lt;element name="orientation"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;attribute name="firstSpotID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                     &lt;attribute name="secondSpotID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="spot" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="maldiMatrix" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"/&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="spotID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="spotXPosition" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="spotYPosition" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="spotDiameter" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="plateID" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *                 &lt;attribute name="spotXCount" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *                 &lt;attribute name="spotYCount" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="robot" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="robotManufacturer" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"/&gt;
 *                   &lt;element name="robotModel" type="{http://sashimi.sourceforge.net/schema_revision/mzXML_3.2}ontologyEntryType"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="timePerSpot" use="required" type="{http://www.w3.org/2001/XMLSchema}duration" /&gt;
 *                 &lt;attribute name="deadVolume" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *               &lt;/restriction&gt;
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
@XmlType(name = "", propOrder = {
    "plate",
    "robot"
})
public class Spotting
    implements Serializable, MzXMLObject
{

    private final static long serialVersionUID = 320L;
    @XmlElement(required = true)
    protected List<Plate> plate;
    protected Robot robot;

    /**
     * Gets the value of the plate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the plate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Plate }
     * 
     * 
     */
    public List<Plate> getPlate() {
        if (plate == null) {
            plate = new ArrayList<>();
        }
        return this.plate;
    }

    /**
     * Gets the value of the robot property.
     * 
     * @return
     *     possible object is
     *     {@link Robot }
     *     
     */
    public Robot getRobot() {
        return robot;
    }

    /**
     * Sets the value of the robot property.
     * 
     * @param value
     *     allowed object is
     *     {@link Robot }
     *     
     */
    public void setRobot(Robot value) {
        this.robot = value;
    }

}
