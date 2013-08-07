
package uk.ac.ebi.cdb.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for searchTerms complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="searchTerms">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="searchTerms" type="{http://webservice.cdb.ebi.ac.uk/}SearchTerm" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchTerms", propOrder = {
    "searchTerms"
})
public class SearchTerms {

    @XmlElement(nillable = true)
    protected List<SearchTerm> searchTerms;

    /**
     * Gets the value of the searchTerms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the searchTerms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSearchTerms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SearchTerm }
     * 
     * 
     */
    public List<SearchTerm> getSearchTerms() {
        if (searchTerms == null) {
            searchTerms = new ArrayList<SearchTerm>();
        }
        return this.searchTerms;
    }

}
