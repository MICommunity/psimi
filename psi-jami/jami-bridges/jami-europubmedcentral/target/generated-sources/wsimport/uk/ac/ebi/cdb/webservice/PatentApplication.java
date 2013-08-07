
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for patentApplication complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="patentApplication">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applicationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="applicationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
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
@XmlType(name = "patentApplication", propOrder = {
    "applicationNumber",
    "applicationDate",
    "orderIn"
})
public class PatentApplication {

    protected String applicationNumber;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar applicationDate;
    protected Short orderIn;

    /**
     * Gets the value of the applicationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationNumber() {
        return applicationNumber;
    }

    /**
     * Sets the value of the applicationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationNumber(String value) {
        this.applicationNumber = value;
    }

    /**
     * Gets the value of the applicationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getApplicationDate() {
        return applicationDate;
    }

    /**
     * Sets the value of the applicationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setApplicationDate(XMLGregorianCalendar value) {
        this.applicationDate = value;
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
