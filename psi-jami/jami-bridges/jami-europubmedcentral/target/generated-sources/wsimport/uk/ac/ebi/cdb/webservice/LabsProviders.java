
package uk.ac.ebi.cdb.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for labsProviders complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="labsProviders">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="provider" type="{http://webservice.cdb.ebi.ac.uk/}labsProvider" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "labsProviders", propOrder = {
    "provider"
})
public class LabsProviders {

    @XmlElement(nillable = true)
    protected List<LabsProvider> provider;

    /**
     * Gets the value of the provider property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the provider property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProvider().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LabsProvider }
     * 
     * 
     */
    public List<LabsProvider> getProvider() {
        if (provider == null) {
            provider = new ArrayList<LabsProvider>();
        }
        return this.provider;
    }

}
