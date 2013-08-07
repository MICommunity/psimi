
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CitationData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CitationData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="citationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authorString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="journalAbbreviation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pubYear" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="volume" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ISSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="issue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pageInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="citedByCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CitationData", propOrder = {
    "id",
    "source",
    "citationType",
    "title",
    "authorString",
    "journalAbbreviation",
    "pubYear",
    "volume",
    "issn",
    "issue",
    "pageInfo",
    "citedByCount",
    "text"
})
public class CitationData {

    protected String id;
    protected String source;
    protected String citationType;
    protected String title;
    protected String authorString;
    protected String journalAbbreviation;
    protected int pubYear;
    protected String volume;
    @XmlElement(name = "ISSN")
    protected String issn;
    protected String issue;
    protected String pageInfo;
    protected int citedByCount;
    protected String text;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the citationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitationType() {
        return citationType;
    }

    /**
     * Sets the value of the citationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitationType(String value) {
        this.citationType = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the authorString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorString() {
        return authorString;
    }

    /**
     * Sets the value of the authorString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorString(String value) {
        this.authorString = value;
    }

    /**
     * Gets the value of the journalAbbreviation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJournalAbbreviation() {
        return journalAbbreviation;
    }

    /**
     * Sets the value of the journalAbbreviation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJournalAbbreviation(String value) {
        this.journalAbbreviation = value;
    }

    /**
     * Gets the value of the pubYear property.
     * 
     */
    public int getPubYear() {
        return pubYear;
    }

    /**
     * Sets the value of the pubYear property.
     * 
     */
    public void setPubYear(int value) {
        this.pubYear = value;
    }

    /**
     * Gets the value of the volume property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVolume() {
        return volume;
    }

    /**
     * Sets the value of the volume property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVolume(String value) {
        this.volume = value;
    }

    /**
     * Gets the value of the issn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISSN() {
        return issn;
    }

    /**
     * Sets the value of the issn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISSN(String value) {
        this.issn = value;
    }

    /**
     * Gets the value of the issue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssue() {
        return issue;
    }

    /**
     * Sets the value of the issue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssue(String value) {
        this.issue = value;
    }

    /**
     * Gets the value of the pageInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPageInfo() {
        return pageInfo;
    }

    /**
     * Sets the value of the pageInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPageInfo(String value) {
        this.pageInfo = value;
    }

    /**
     * Gets the value of the citedByCount property.
     * 
     */
    public int getCitedByCount() {
        return citedByCount;
    }

    /**
     * Sets the value of the citedByCount property.
     * 
     */
    public void setCitedByCount(int value) {
        this.citedByCount = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

}
