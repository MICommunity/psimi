
package uk.ac.ebi.cdb.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dbCrossReference complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dbCrossReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dbName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dbCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dbCrossReferenceInfo" type="{http://webservice.cdb.ebi.ac.uk/}dbCrossReferenceInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dbCrossReference", propOrder = {
    "dbName",
    "dbCount",
    "dbCrossReferenceInfo"
})
public class DbCrossReference {

    protected String dbName;
    protected int dbCount;
    @XmlElement(nillable = true)
    protected List<DbCrossReferenceInfo> dbCrossReferenceInfo;

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
     * Gets the value of the dbCount property.
     * 
     */
    public int getDbCount() {
        return dbCount;
    }

    /**
     * Sets the value of the dbCount property.
     * 
     */
    public void setDbCount(int value) {
        this.dbCount = value;
    }

    /**
     * Gets the value of the dbCrossReferenceInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dbCrossReferenceInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDbCrossReferenceInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DbCrossReferenceInfo }
     * 
     * 
     */
    public List<DbCrossReferenceInfo> getDbCrossReferenceInfo() {
        if (dbCrossReferenceInfo == null) {
            dbCrossReferenceInfo = new ArrayList<DbCrossReferenceInfo>();
        }
        return this.dbCrossReferenceInfo;
    }

}
