
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for minedSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="minedSummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="term" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="altNameList" type="{http://webservice.cdb.ebi.ac.uk/}minedAltName" minOccurs="0"/>
 *         &lt;element name="dbName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dbIdList" type="{http://webservice.cdb.ebi.ac.uk/}minedDbIDs" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "minedSummary", propOrder = {
    "term",
    "count",
    "altNameList",
    "dbName",
    "dbIdList"
})
public class MinedSummary {

    protected String term;
    protected int count;
    protected MinedAltName altNameList;
    protected String dbName;
    protected MinedDbIDs dbIdList;

    /**
     * Gets the value of the term property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerm() {
        return term;
    }

    /**
     * Sets the value of the term property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerm(String value) {
        this.term = value;
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
     * Gets the value of the altNameList property.
     * 
     * @return
     *     possible object is
     *     {@link MinedAltName }
     *     
     */
    public MinedAltName getAltNameList() {
        return altNameList;
    }

    /**
     * Sets the value of the altNameList property.
     * 
     * @param value
     *     allowed object is
     *     {@link MinedAltName }
     *     
     */
    public void setAltNameList(MinedAltName value) {
        this.altNameList = value;
    }

    /**
     * Gets the value of the dbName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * Sets the value of the dbName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDbName(String value) {
        this.dbName = value;
    }

    /**
     * Gets the value of the dbIdList property.
     * 
     * @return
     *     possible object is
     *     {@link MinedDbIDs }
     *     
     */
    public MinedDbIDs getDbIdList() {
        return dbIdList;
    }

    /**
     * Sets the value of the dbIdList property.
     * 
     * @param value
     *     allowed object is
     *     {@link MinedDbIDs }
     *     
     */
    public void setDbIdList(MinedDbIDs value) {
        this.dbIdList = value;
    }

}
