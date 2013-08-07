
package uk.ac.ebi.cdb.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for profileListBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="profileListBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="source" type="{http://webservice.cdb.ebi.ac.uk/}profile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pubType" type="{http://webservice.cdb.ebi.ac.uk/}profile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="subset" type="{http://webservice.cdb.ebi.ac.uk/}profile" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "profileListBean", propOrder = {
    "source",
    "pubType",
    "subset"
})
public class ProfileListBean {

    @XmlElement(nillable = true)
    protected List<Profile> source;
    @XmlElement(nillable = true)
    protected List<Profile> pubType;
    @XmlElement(nillable = true)
    protected List<Profile> subset;

    /**
     * Gets the value of the source property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the source property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Profile }
     * 
     * 
     */
    public List<Profile> getSource() {
        if (source == null) {
            source = new ArrayList<Profile>();
        }
        return this.source;
    }

    /**
     * Gets the value of the pubType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pubType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPubType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Profile }
     * 
     * 
     */
    public List<Profile> getPubType() {
        if (pubType == null) {
            pubType = new ArrayList<Profile>();
        }
        return this.pubType;
    }

    /**
     * Gets the value of the subset property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subset property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubset().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Profile }
     * 
     * 
     */
    public List<Profile> getSubset() {
        if (subset == null) {
            subset = new ArrayList<Profile>();
        }
        return this.subset;
    }

}
