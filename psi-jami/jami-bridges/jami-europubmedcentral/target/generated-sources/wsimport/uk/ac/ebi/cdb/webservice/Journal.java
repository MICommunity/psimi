
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for journal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="journal">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ISOAbbreviation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="medlineAbbreviation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NLMid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ISSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ESSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "journal", propOrder = {
    "title",
    "isoAbbreviation",
    "medlineAbbreviation",
    "nlMid",
    "issn",
    "essn"
})
public class Journal {

    protected String title;
    @XmlElement(name = "ISOAbbreviation")
    protected String isoAbbreviation;
    protected String medlineAbbreviation;
    @XmlElement(name = "NLMid")
    protected String nlMid;
    @XmlElement(name = "ISSN")
    protected String issn;
    @XmlElement(name = "ESSN")
    protected String essn;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the isoAbbreviation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISOAbbreviation() {
        return isoAbbreviation;
    }

    /**
     * Sets the value of the isoAbbreviation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISOAbbreviation(String value) {
        this.isoAbbreviation = value;
    }

    /**
     * Gets the value of the medlineAbbreviation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedlineAbbreviation() {
        return medlineAbbreviation;
    }

    /**
     * Sets the value of the medlineAbbreviation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedlineAbbreviation(String value) {
        this.medlineAbbreviation = value;
    }

    /**
     * Gets the value of the nlMid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNLMid() {
        return nlMid;
    }

    /**
     * Sets the value of the nlMid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNLMid(String value) {
        this.nlMid = value;
    }

    /**
     * Gets the value of the issn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISSN() {
        return issn;
    }

    /**
     * Sets the value of the issn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISSN(String value) {
        this.issn = value;
    }

    /**
     * Gets the value of the essn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESSN() {
        return essn;
    }

    /**
     * Sets the value of the essn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESSN(String value) {
        this.essn = value;
    }

}
