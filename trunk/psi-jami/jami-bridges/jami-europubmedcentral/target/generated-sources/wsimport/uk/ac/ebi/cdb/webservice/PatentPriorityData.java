
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for patentPriorityData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="patentPriorityData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="priorityNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priorityDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="orderIn" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patentPriorityData", propOrder = {
    "priorityNumber",
    "priorityDate",
    "orderIn"
})
public class PatentPriorityData {

    protected String priorityNumber;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar priorityDate;
    protected Short orderIn;

    /**
     * Gets the value of the priorityNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriorityNumber() {
        return priorityNumber;
    }

    /**
     * Sets the value of the priorityNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriorityNumber(String value) {
        this.priorityNumber = value;
    }

    /**
     * Gets the value of the priorityDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPriorityDate() {
        return priorityDate;
    }

    /**
     * Sets the value of the priorityDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPriorityDate(XMLGregorianCalendar value) {
        this.priorityDate = value;
    }

    /**
     * Gets the value of the orderIn property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getOrderIn() {
        return orderIn;
    }

    /**
     * Sets the value of the orderIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setOrderIn(Short value) {
        this.orderIn = value;
    }

}
