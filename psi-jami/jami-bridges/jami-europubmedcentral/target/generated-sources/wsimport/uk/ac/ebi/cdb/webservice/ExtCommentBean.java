
package uk.ac.ebi.cdb.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for extCommentBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="extCommentBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="extComment" type="{http://webservice.cdb.ebi.ac.uk/}extComment" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "extCommentBean", propOrder = {
    "extComment"
})
public class ExtCommentBean {

    @XmlElement(nillable = true)
    protected List<ExtComment> extComment;

    /**
     * Gets the value of the extComment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extComment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtComment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtComment }
     * 
     * 
     */
    public List<ExtComment> getExtComment() {
        if (extComment == null) {
            extComment = new ArrayList<ExtComment>();
        }
        return this.extComment;
    }

}
