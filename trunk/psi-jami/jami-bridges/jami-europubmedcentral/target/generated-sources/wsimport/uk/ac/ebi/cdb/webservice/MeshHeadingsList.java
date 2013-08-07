
package uk.ac.ebi.cdb.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for meshHeadingsList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="meshHeadingsList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="meshHeading" type="{http://webservice.cdb.ebi.ac.uk/}meshHeadingInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "meshHeadingsList", propOrder = {
    "meshHeading"
})
public class MeshHeadingsList {

    @XmlElement(nillable = true)
    protected List<MeshHeadingInfo> meshHeading;

    /**
     * Gets the value of the meshHeading property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the meshHeading property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMeshHeading().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MeshHeadingInfo }
     * 
     * 
     */
    public List<MeshHeadingInfo> getMeshHeading() {
        if (meshHeading == null) {
            meshHeading = new ArrayList<MeshHeadingInfo>();
        }
        return this.meshHeading;
    }

}
