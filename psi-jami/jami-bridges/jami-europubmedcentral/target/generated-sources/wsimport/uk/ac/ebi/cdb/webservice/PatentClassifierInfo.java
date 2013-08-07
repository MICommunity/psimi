
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for patentClassifierInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="patentClassifierInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="classification" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="classificationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hyperlink" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patentClassifierInfo", propOrder = {
    "classification",
    "classificationType",
    "hyperlink"
})
public class PatentClassifierInfo {

    protected String classification;
    protected String classificationType;
    protected String hyperlink;

    /**
     * Gets the value of the classification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassification() {
        return classification;
    }

    /**
     * Sets the value of the classification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassification(String value) {
        this.classification = value;
    }

    /**
     * Gets the value of the classificationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassificationType() {
        return classificationType;
    }

    /**
     * Sets the value of the classificationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassificationType(String value) {
        this.classificationType = value;
    }

    /**
     * Gets the value of the hyperlink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHyperlink() {
        return hyperlink;
    }

    /**
     * Sets the value of the hyperlink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHyperlink(String value) {
        this.hyperlink = value;
    }

}
