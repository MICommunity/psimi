
package uk.ac.ebi.cdb.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for patentFamilyList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="patentFamilyList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="family" type="{http://webservice.cdb.ebi.ac.uk/}patentFamilyData" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patentFamilyList", propOrder = {
    "family"
})
public class PatentFamilyList {

    @XmlElement(nillable = true)
    protected List<PatentFamilyData> family;

    /**
     * Gets the value of the family property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the family property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFamily().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PatentFamilyData }
     * 
     * 
     */
    public List<PatentFamilyData> getFamily() {
        if (family == null) {
            family = new ArrayList<PatentFamilyData>();
        }
        return this.family;
    }

}
