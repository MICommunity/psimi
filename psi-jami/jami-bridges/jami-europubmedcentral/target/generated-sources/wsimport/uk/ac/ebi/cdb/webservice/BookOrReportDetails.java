
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bookOrReportDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bookOrReportDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="publisher" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dayOfPublication" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/>
 *         &lt;element name="monthOfPublication" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/>
 *         &lt;element name="yearOfPublication" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numberOfPages" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="edition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isbn10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isbn13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="seriesName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="seriesIssn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comprisingTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comprisingTitleNonAscii" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="extraInformation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bookOrReportDetails", propOrder = {
    "publisher",
    "dayOfPublication",
    "monthOfPublication",
    "yearOfPublication",
    "numberOfPages",
    "edition",
    "isbn10",
    "isbn13",
    "seriesName",
    "seriesIssn",
    "comprisingTitle",
    "comprisingTitleNonAscii",
    "extraInformation"
})
public class BookOrReportDetails {

    protected String publisher;
    protected Byte dayOfPublication;
    protected Byte monthOfPublication;
    protected Short yearOfPublication;
    protected String numberOfPages;
    protected String edition;
    protected String isbn10;
    protected String isbn13;
    protected String seriesName;
    protected String seriesIssn;
    protected String comprisingTitle;
    protected String comprisingTitleNonAscii;
    protected String extraInformation;

    /**
     * Gets the value of the publisher property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets the value of the publisher property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublisher(String value) {
        this.publisher = value;
    }

    /**
     * Gets the value of the dayOfPublication property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getDayOfPublication() {
        return dayOfPublication;
    }

    /**
     * Sets the value of the dayOfPublication property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setDayOfPublication(Byte value) {
        this.dayOfPublication = value;
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
     * Gets the value of the numberOfPages property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * Sets the value of the numberOfPages property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfPages(String value) {
        this.numberOfPages = value;
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
     * Gets the value of the isbn10 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsbn10() {
        return isbn10;
    }

    /**
     * Sets the value of the isbn10 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsbn10(String value) {
        this.isbn10 = value;
    }

    /**
     * Gets the value of the isbn13 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsbn13() {
        return isbn13;
    }

    /**
     * Sets the value of the isbn13 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsbn13(String value) {
        this.isbn13 = value;
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
     * Gets the value of the seriesIssn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeriesIssn() {
        return seriesIssn;
    }

    /**
     * Sets the value of the seriesIssn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeriesIssn(String value) {
        this.seriesIssn = value;
    }

    /**
     * Gets the value of the comprisingTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComprisingTitle() {
        return comprisingTitle;
    }

    /**
     * Sets the value of the comprisingTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComprisingTitle(String value) {
        this.comprisingTitle = value;
    }

    /**
     * Gets the value of the comprisingTitleNonAscii property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComprisingTitleNonAscii() {
        return comprisingTitleNonAscii;
    }

    /**
     * Sets the value of the comprisingTitleNonAscii property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComprisingTitleNonAscii(String value) {
        this.comprisingTitleNonAscii = value;
    }

    /**
     * Gets the value of the extraInformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtraInformation() {
        return extraInformation;
    }

    /**
     * Sets the value of the extraInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtraInformation(String value) {
        this.extraInformation = value;
    }

}
