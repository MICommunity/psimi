
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for labsProviderCounts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="labsProviderCounts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="providerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="linksCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "labsProviderCounts", propOrder = {
    "providerName",
    "linksCount"
})
public class LabsProviderCounts {

    protected String providerName;
    protected Integer linksCount;

    /**
     * Gets the value of the providerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * Sets the value of the providerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderName(String value) {
        this.providerName = value;
    }

    /**
     * Gets the value of the linksCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLinksCount() {
        return linksCount;
    }

    /**
     * Sets the value of the linksCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLinksCount(Integer value) {
        this.linksCount = value;
    }

}
