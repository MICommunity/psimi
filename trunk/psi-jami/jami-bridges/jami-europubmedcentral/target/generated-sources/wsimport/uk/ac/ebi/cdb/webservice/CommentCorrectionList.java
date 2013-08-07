
package uk.ac.ebi.cdb.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for commentCorrectionList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="commentCorrectionList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="commentCorrection" type="{http://webservice.cdb.ebi.ac.uk/}commentCorrection" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "commentCorrectionList", propOrder = {
    "commentCorrection"
})
public class CommentCorrectionList {

    @XmlElement(nillable = true)
    protected List<CommentCorrection> commentCorrection;

    /**
     * Gets the value of the commentCorrection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commentCorrection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommentCorrection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommentCorrection }
     * 
     * 
     */
    public List<CommentCorrection> getCommentCorrection() {
        if (commentCorrection == null) {
            commentCorrection = new ArrayList<CommentCorrection>();
        }
        return this.commentCorrection;
    }

}
