
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for referenceInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="referenceInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="citationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authorString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="journalAbbreviation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="issue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pubYear" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="volume" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ISSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ESSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ISBN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pageInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="publicationTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="publisherLoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="publisherName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="seriesName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="edition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="editors" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="doi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unstructuredInformation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="externalLink" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="citedOrder" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="match" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "referenceInfo", propOrder = {
    "id",
    "source",
    "citationType",
    "title",
    "authorString",
    "journalAbbreviation",
    "issue",
    "pubYear",
    "volume",
    "issn",
    "essn",
    "isbn",
    "pageInfo",
    "publicationTitle",
    "publisherLoc",
    "publisherName",
    "seriesName",
    "edition",
    "editors",
    "doi",
    "unstructuredInformation",
    "externalLink",
    "comments",
    "citedOrder",
    "match"
})
public class ReferenceInfo {

    protected String id;
    protected String source;
    protected String citationType;
    protected String title;
    protected String authorString;
    protected String journalAbbreviation;
    protected String issue;
    protected int pubYear;
    protected String volume;
    @XmlElement(name = "ISSN")
    protected String issn;
    @XmlElement(name = "ESSN")
    protected String essn;
    @XmlElement(name = "ISBN")
    protected String isbn;
    protected String pageInfo;
    protected String publicationTitle;
    protected String publisherLoc;
    protected String publisherName;
    protected String seriesName;
    protected String edition;
    protected String editors;
    protected String doi;
    protected String unstructuredInformation;
    protected String externalLink;
    protected String comments;
    protected int citedOrder;
    protected String match;

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
     * Gets the value of the essn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESSN() {
        return essn;
    }

    /**
     * Sets the value of the essn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESSN(String value) {
        this.essn = value;
    }

    /**
     * Gets the value of the isbn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISBN() {
        return isbn;
    }

    /**
     * Sets the value of the isbn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISBN(String value) {
        this.isbn = value;
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
     * Gets the value of the publicationTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublicationTitle() {
        return publicationTitle;
    }

    /**
     * Sets the value of the publicationTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublicationTitle(String value) {
        this.publicationTitle = value;
    }

    /**
     * Gets the value of the publisherLoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublisherLoc() {
        return publisherLoc;
    }

    /**
     * Sets the value of the publisherLoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublisherLoc(String value) {
        this.publisherLoc = value;
    }

    /**
     * Gets the value of the publisherName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublisherName() {
        return publisherName;
    }

    /**
     * Sets the value of the publisherName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublisherName(String value) {
        this.publisherName = value;
    }

    /**
     * Gets the value of the seriesName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeriesName() {
        return seriesName;
    }

    /**
     * Sets the value of the seriesName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeriesName(String value) {
        this.seriesName = value;
    }

    /**
     * Gets the value of the edition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEdition() {
        return edition;
    }

    /**
     * Sets the value of the edition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEdition(String value) {
        this.edition = value;
    }

    /**
     * Gets the value of the editors property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditors() {
        return editors;
    }

    /**
     * Sets the value of the editors property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditors(String value) {
        this.editors = value;
    }

    /**
     * Gets the value of the doi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoi() {
        return doi;
    }

    /**
     * Sets the value of the doi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoi(String value) {
        this.doi = value;
    }

    /**
     * Gets the value of the unstructuredInformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnstructuredInformation() {
        return unstructuredInformation;
    }

    /**
     * Sets the value of the unstructuredInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnstructuredInformation(String value) {
        this.unstructuredInformation = value;
    }

    /**
     * Gets the value of the externalLink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalLink() {
        return externalLink;
    }

    /**
     * Sets the value of the externalLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalLink(String value) {
        this.externalLink = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

    /**
     * Gets the value of the citedOrder property.
     * 
     */
    public int getCitedOrder() {
        return citedOrder;
    }

    /**
     * Sets the value of the citedOrder property.
     * 
     */
    public void setCitedOrder(int value) {
        this.citedOrder = value;
    }

    /**
     * Gets the value of the match property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatch() {
        return match;
    }

    /**
     * Sets the value of the match property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatch(String value) {
        this.match = value;
    }

}
