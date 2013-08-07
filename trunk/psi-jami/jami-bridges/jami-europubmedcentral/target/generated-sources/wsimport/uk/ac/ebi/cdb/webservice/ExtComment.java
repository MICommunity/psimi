
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for extComment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="extComment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="extCommentSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="info1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="info2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "extComment", propOrder = {
    "extCommentSource",
    "info1",
    "info2"
})
public class ExtComment {

    protected String extCommentSource;
    protected String info1;
    protected String info2;

    /**
     * Gets the value of the extCommentSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtCommentSource() {
        return extCommentSource;
    }

    /**
     * Sets the value of the extCommentSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtCommentSource(String value) {
        this.extCommentSource = value;
    }

    /**
     * Gets the value of the info1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfo1() {
        return info1;
    }

    /**
     * Sets the value of the info1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfo1(String value) {
        this.info1 = value;
    }

    /**
     * Gets the value of the info2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfo2() {
        return info2;
    }

    /**
     * Sets the value of the info2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfo2(String value) {
        this.info2 = value;
    }

}
