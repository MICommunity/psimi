
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for request complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="request">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="queryString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataSet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="profileType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="offSet" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="database" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="semanticType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="synonym" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "request", propOrder = {
    "queryString",
    "dataSet",
    "resultType",
    "profileType",
    "id",
    "source",
    "offSet",
    "database",
    "semanticType",
    "synonym",
    "email",
    "providerId"
})
public class Request {

    protected String queryString;
    protected String dataSet;
    protected String resultType;
    protected String profileType;
    protected String id;
    protected String source;
    protected Integer offSet;
    protected String database;
    protected String semanticType;
    protected Boolean synonym;
    protected String email;
    protected String providerId;

    /**
     * Gets the value of the queryString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryString() {
        return queryString;
    }

    /**
     * Sets the value of the queryString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryString(String value) {
        this.queryString = value;
    }

    /**
     * Gets the value of the dataSet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSet() {
        return dataSet;
    }

    /**
     * Sets the value of the dataSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSet(String value) {
        this.dataSet = value;
    }

    /**
     * Gets the value of the resultType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultType() {
        return resultType;
    }

    /**
     * Sets the value of the resultType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultType(String value) {
        this.resultType = value;
    }

    /**
     * Gets the value of the profileType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfileType() {
        return profileType;
    }

    /**
     * Sets the value of the profileType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfileType(String value) {
        this.profileType = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the offSet property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOffSet() {
        return offSet;
    }

    /**
     * Sets the value of the offSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOffSet(Integer value) {
        this.offSet = value;
    }

    /**
     * Gets the value of the database property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Sets the value of the database property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatabase(String value) {
        this.database = value;
    }

    /**
     * Gets the value of the semanticType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSemanticType() {
        return semanticType;
    }

    /**
     * Sets the value of the semanticType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSemanticType(String value) {
        this.semanticType = value;
    }

    /**
     * Gets the value of the synonym property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSynonym() {
        return synonym;
    }

    /**
     * Sets the value of the synonym property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSynonym(Boolean value) {
        this.synonym = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the providerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * Sets the value of the providerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderId(String value) {
        this.providerId = value;
    }

}
