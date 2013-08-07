
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for patentDetailsInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="patentDetailsInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="countryCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="typeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="typeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="classifierList" type="{http://webservice.cdb.ebi.ac.uk/}patentClassifierList" minOccurs="0"/>
 *         &lt;element name="application" type="{http://webservice.cdb.ebi.ac.uk/}patentApplication" minOccurs="0"/>
 *         &lt;element name="priorityList" type="{http://webservice.cdb.ebi.ac.uk/}patentPriorityList" minOccurs="0"/>
 *         &lt;element name="familyList" type="{http://webservice.cdb.ebi.ac.uk/}patentFamilyList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patentDetailsInfo", propOrder = {
    "countryCode",
    "country",
    "typeCode",
    "typeDescription",
    "classifierList",
    "application",
    "priorityList",
    "familyList"
})
public class PatentDetailsInfo {

    protected String countryCode;
    protected String country;
    protected String typeCode;
    protected String typeDescription;
    protected PatentClassifierList classifierList;
    protected PatentApplication application;
    protected PatentPriorityList priorityList;
    protected PatentFamilyList familyList;

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the typeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeCode(String value) {
        this.typeCode = value;
    }

    /**
     * Gets the value of the typeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeDescription() {
        return typeDescription;
    }

    /**
     * Sets the value of the typeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeDescription(String value) {
        this.typeDescription = value;
    }

    /**
     * Gets the value of the classifierList property.
     * 
     * @return
     *     possible object is
     *     {@link PatentClassifierList }
     *     
     */
    public PatentClassifierList getClassifierList() {
        return classifierList;
    }

    /**
     * Sets the value of the classifierList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatentClassifierList }
     *     
     */
    public void setClassifierList(PatentClassifierList value) {
        this.classifierList = value;
    }

    /**
     * Gets the value of the application property.
     * 
     * @return
     *     possible object is
     *     {@link PatentApplication }
     *     
     */
    public PatentApplication getApplication() {
        return application;
    }

    /**
     * Sets the value of the application property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatentApplication }
     *     
     */
    public void setApplication(PatentApplication value) {
        this.application = value;
    }

    /**
     * Gets the value of the priorityList property.
     * 
     * @return
     *     possible object is
     *     {@link PatentPriorityList }
     *     
     */
    public PatentPriorityList getPriorityList() {
        return priorityList;
    }

    /**
     * Sets the value of the priorityList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatentPriorityList }
     *     
     */
    public void setPriorityList(PatentPriorityList value) {
        this.priorityList = value;
    }

    /**
     * Gets the value of the familyList property.
     * 
     * @return
     *     possible object is
     *     {@link PatentFamilyList }
     *     
     */
    public PatentFamilyList getFamilyList() {
        return familyList;
    }

    /**
     * Sets the value of the familyList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatentFamilyList }
     *     
     */
    public void setFamilyList(PatentFamilyList value) {
        this.familyList = value;
    }

}
