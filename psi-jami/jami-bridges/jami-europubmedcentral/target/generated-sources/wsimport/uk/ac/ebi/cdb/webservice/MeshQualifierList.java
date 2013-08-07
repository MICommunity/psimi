
package uk.ac.ebi.cdb.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for meshQualifierList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="meshQualifierList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="meshQualifier" type="{http://webservice.cdb.ebi.ac.uk/}meshQualifierInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "meshQualifierList", propOrder = {
    "meshQualifier"
})
public class MeshQualifierList {

    @XmlElement(nillable = true)
    protected List<MeshQualifierInfo> meshQualifier;

    /**
     * Gets the value of the meshQualifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the meshQualifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMeshQualifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MeshQualifierInfo }
     * 
     * 
     */
    public List<MeshQualifierInfo> getMeshQualifier() {
        if (meshQualifier == null) {
            meshQualifier = new ArrayList<MeshQualifierInfo>();
        }
        return this.meshQualifier;
    }

}
