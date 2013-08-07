
package uk.ac.ebi.cdb.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fullTextUrlList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fullTextUrlList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fullTextUrl" type="{http://webservice.cdb.ebi.ac.uk/}fullTextURLInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fullTextUrlList", propOrder = {
    "fullTextUrl"
})
public class FullTextUrlList {

    @XmlElement(nillable = true)
    protected List<FullTextURLInfo> fullTextUrl;

    /**
     * Gets the value of the fullTextUrl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fullTextUrl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFullTextUrl().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FullTextURLInfo }
     * 
     * 
     */
    public List<FullTextURLInfo> getFullTextUrl() {
        if (fullTextUrl == null) {
            fullTextUrl = new ArrayList<FullTextURLInfo>();
        }
        return this.fullTextUrl;
    }

}
