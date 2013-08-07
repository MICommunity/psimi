
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for journalInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="journalInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="issue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="volume" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="journalIssueId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dateOfPublication" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monthOfPublication" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/>
 *         &lt;element name="yearOfPublication" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="journal" type="{http://webservice.cdb.ebi.ac.uk/}journal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "journalInfo", propOrder = {
    "issue",
    "volume",
    "journalIssueId",
    "dateOfPublication",
    "monthOfPublication",
    "yearOfPublication",
    "journal"
})
public class JournalInfo {

    protected String issue;
    protected String volume;
    protected Integer journalIssueId;
    protected String dateOfPublication;
    protected Byte monthOfPublication;
    protected Short yearOfPublication;
    protected Journal journal;

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
     * Gets the value of the journalIssueId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getJournalIssueId() {
        return journalIssueId;
    }

    /**
     * Sets the value of the journalIssueId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setJournalIssueId(Integer value) {
        this.journalIssueId = value;
    }

    /**
     * Gets the value of the dateOfPublication property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateOfPublication() {
        return dateOfPublication;
    }

    /**
     * Sets the value of the dateOfPublication property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfPublication(String value) {
        this.dateOfPublication = value;
    }

    /**
     * Gets the value of the monthOfPublication property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getMonthOfPublication() {
        return monthOfPublication;
    }

    /**
     * Sets the value of the monthOfPublication property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setMonthOfPublication(Byte value) {
        this.monthOfPublication = value;
    }

    /**
     * Gets the value of the yearOfPublication property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getYearOfPublication() {
        return yearOfPublication;
    }

    /**
     * Sets the value of the yearOfPublication property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setYearOfPublication(Short value) {
        this.yearOfPublication = value;
    }

    /**
     * Gets the value of the journal property.
     * 
     * @return
     *     possible object is
     *     {@link Journal }
     *     
     */
    public Journal getJournal() {
        return journal;
    }

    /**
     * Sets the value of the journal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Journal }
     *     
     */
    public void setJournal(Journal value) {
        this.journal = value;
    }

}
