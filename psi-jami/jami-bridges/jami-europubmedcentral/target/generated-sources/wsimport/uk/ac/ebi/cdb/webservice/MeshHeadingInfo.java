
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for meshHeadingInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="meshHeadingInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="majorTopic_YN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descriptorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="meshQualifierList" type="{http://webservice.cdb.ebi.ac.uk/}meshQualifierList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "meshHeadingInfo", propOrder = {
    "majorTopicYN",
    "descriptorName",
    "meshQualifierList"
})
public class MeshHeadingInfo {

    @XmlElement(name = "majorTopic_YN")
    protected String majorTopicYN;
    protected String descriptorName;
    protected MeshQualifierList meshQualifierList;

    /**
     * Gets the value of the majorTopicYN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajorTopicYN() {
        return majorTopicYN;
    }

    /**
     * Sets the value of the majorTopicYN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajorTopicYN(String value) {
        this.majorTopicYN = value;
    }

    /**
     * Gets the value of the descriptorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescriptorName() {
        return descriptorName;
    }

    /**
     * Sets the value of the descriptorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescriptorName(String value) {
        this.descriptorName = value;
    }

    /**
     * Gets the value of the meshQualifierList property.
     * 
     * @return
     *     possible object is
     *     {@link MeshQualifierList }
     *     
     */
    public MeshQualifierList getMeshQualifierList() {
        return meshQualifierList;
    }

    /**
     * Sets the value of the meshQualifierList property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeshQualifierList }
     *     
     */
    public void setMeshQualifierList(MeshQualifierList value) {
        this.meshQualifierList = value;
    }

}
