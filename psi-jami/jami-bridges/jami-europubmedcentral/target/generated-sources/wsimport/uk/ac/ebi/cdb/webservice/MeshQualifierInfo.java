
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for meshQualifierInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="meshQualifierInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="abbreviation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="qualifierName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="majorTopic_YN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "meshQualifierInfo", propOrder = {
    "abbreviation",
    "qualifierName",
    "majorTopicYN"
})
public class MeshQualifierInfo {

    protected String abbreviation;
    protected String qualifierName;
    @XmlElement(name = "majorTopic_YN")
    protected String majorTopicYN;

    /**
     * Gets the value of the abbreviation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Sets the value of the abbreviation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbbreviation(String value) {
        this.abbreviation = value;
    }

    /**
     * Gets the value of the qualifierName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualifierName() {
        return qualifierName;
    }

    /**
     * Sets the value of the qualifierName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualifierName(String value) {
        this.qualifierName = value;
    }

    /**
     * Gets the value of the majorTopicYN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajorTopicYN() {
        return majorTopicYN;
    }

    /**
     * Sets the value of the majorTopicYN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajorTopicYN(String value) {
        this.majorTopicYN = value;
    }

}
