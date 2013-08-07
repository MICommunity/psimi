
package uk.ac.ebi.cdb.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for patentClassifierList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="patentClassifierList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="classifier" type="{http://webservice.cdb.ebi.ac.uk/}patentClassifierInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patentClassifierList", propOrder = {
    "classifier"
})
public class PatentClassifierList {

    @XmlElement(nillable = true)
    protected List<PatentClassifierInfo> classifier;

    /**
     * Gets the value of the classifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the classifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClassifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PatentClassifierInfo }
     * 
     * 
     */
    public List<PatentClassifierInfo> getClassifier() {
        if (classifier == null) {
            classifier = new ArrayList<PatentClassifierInfo>();
        }
        return this.classifier;
    }

}
