
package uk.ac.ebi.cdb.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for minedTermsList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="minedTermsList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="semanticType" type="{http://webservice.cdb.ebi.ac.uk/}minedTerms" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "minedTermsList", propOrder = {
    "semanticType"
})
public class MinedTermsList {

    @XmlElement(nillable = true)
    protected List<MinedTerms> semanticType;

    /**
     * Gets the value of the semanticType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the semanticType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSemanticType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MinedTerms }
     * 
     * 
     */
    public List<MinedTerms> getSemanticType() {
        if (semanticType == null) {
            semanticType = new ArrayList<MinedTerms>();
        }
        return this.semanticType;
    }

}
