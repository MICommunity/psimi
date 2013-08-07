
package uk.ac.ebi.cdb.webservice;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for result complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="result">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="request" type="{http://webservice.cdb.ebi.ac.uk/}request" minOccurs="0"/>
 *         &lt;element name="citationId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pmid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pmcid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DOI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authorString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authorList" type="{http://webservice.cdb.ebi.ac.uk/}authorsList" minOccurs="0"/>
 *         &lt;element name="investigatorList" type="{http://webservice.cdb.ebi.ac.uk/}investigatorList" minOccurs="0"/>
 *         &lt;element name="journalInfo" type="{http://webservice.cdb.ebi.ac.uk/}journalInfo" minOccurs="0"/>
 *         &lt;element name="journalTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="issue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="journalVolume" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pubYear" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="journalIssn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pageInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="abstractText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="affiliation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pubModel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pubType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pubTypeList" type="{http://webservice.cdb.ebi.ac.uk/}pubTypeList" minOccurs="0"/>
 *         &lt;element name="brSummary" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bookOrReportDetails" type="{http://webservice.cdb.ebi.ac.uk/}bookOrReportDetails" minOccurs="0"/>
 *         &lt;element name="patentDetails" type="{http://webservice.cdb.ebi.ac.uk/}patentDetailsInfo" minOccurs="0"/>
 *         &lt;element name="grantsList" type="{http://webservice.cdb.ebi.ac.uk/}grantsList" minOccurs="0"/>
 *         &lt;element name="meshHeadingList" type="{http://webservice.cdb.ebi.ac.uk/}meshHeadingsList" minOccurs="0"/>
 *         &lt;element name="keywordList" type="{http://webservice.cdb.ebi.ac.uk/}keywordList" minOccurs="0"/>
 *         &lt;element name="chemicalList" type="{http://webservice.cdb.ebi.ac.uk/}chemicalList" minOccurs="0"/>
 *         &lt;element name="subsetList" type="{http://webservice.cdb.ebi.ac.uk/}subSetList" minOccurs="0"/>
 *         &lt;element name="fullTextUrlList" type="{http://webservice.cdb.ebi.ac.uk/}fullTextUrlList" minOccurs="0"/>
 *         &lt;element name="commentCorrectionList" type="{http://webservice.cdb.ebi.ac.uk/}commentCorrectionList" minOccurs="0"/>
 *         &lt;element name="extCommentList" type="{http://webservice.cdb.ebi.ac.uk/}extCommentBean" minOccurs="0"/>
 *         &lt;element name="isOpenAccess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inUKPMC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inPMC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="citedByCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="hasReferences" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hasTextMinedTerms" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hasDbCrossReferences" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dbCrossReferenceList" type="{http://webservice.cdb.ebi.ac.uk/}crossReferencesList" minOccurs="0"/>
 *         &lt;element name="hasSupplementary" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hasFullTextXML" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hasPDF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hasLabsLinks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hasTMAccessionNumbers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tmAccessionTypeList" type="{http://webservice.cdb.ebi.ac.uk/}tmAccessionTypeList" minOccurs="0"/>
 *         &lt;element name="dateOfCompletion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dateOfCreation" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dateOfRevision" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fullText" type="{http://ws-i.org/profiles/basic/1.1/xsd}swaRef" minOccurs="0"/>
 *         &lt;element name="supplementaryFiles" type="{http://ws-i.org/profiles/basic/1.1/xsd}swaRef" minOccurs="0"/>
 *         &lt;element name="luceneScore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "result", propOrder = {
    "request",
    "citationId",
    "id",
    "source",
    "pmid",
    "pmcid",
    "doi",
    "title",
    "authorString",
    "authorList",
    "investigatorList",
    "journalInfo",
    "journalTitle",
    "issue",
    "journalVolume",
    "pubYear",
    "journalIssn",
    "pageInfo",
    "abstractText",
    "affiliation",
    "language",
    "pubModel",
    "pubType",
    "pubTypeList",
    "brSummary",
    "bookOrReportDetails",
    "patentDetails",
    "grantsList",
    "meshHeadingList",
    "keywordList",
    "chemicalList",
    "subsetList",
    "fullTextUrlList",
    "commentCorrectionList",
    "extCommentList",
    "isOpenAccess",
    "inUKPMC",
    "inPMC",
    "citedByCount",
    "hasReferences",
    "hasTextMinedTerms",
    "hasDbCrossReferences",
    "dbCrossReferenceList",
    "hasSupplementary",
    "hasFullTextXML",
    "hasPDF",
    "hasLabsLinks",
    "hasTMAccessionNumbers",
    "tmAccessionTypeList",
    "dateOfCompletion",
    "dateOfCreation",
    "dateOfRevision",
    "fullText",
    "supplementaryFiles",
    "luceneScore"
})
public class Result {

    protected Request request;
    protected Integer citationId;
    protected String id;
    protected String source;
    protected String pmid;
    protected String pmcid;
    @XmlElement(name = "DOI")
    protected String doi;
    protected String title;
    protected String authorString;
    protected AuthorsList authorList;
    protected InvestigatorList investigatorList;
    protected JournalInfo journalInfo;
    protected String journalTitle;
    protected String issue;
    protected String journalVolume;
    protected String pubYear;
    protected String journalIssn;
    protected String pageInfo;
    protected String abstractText;
    protected String affiliation;
    protected String language;
    protected String pubModel;
    protected String pubType;
    protected PubTypeList pubTypeList;
    protected String brSummary;
    protected BookOrReportDetails bookOrReportDetails;
    protected PatentDetailsInfo patentDetails;
    protected GrantsList grantsList;
    protected MeshHeadingsList meshHeadingList;
    protected KeywordList keywordList;
    protected ChemicalList chemicalList;
    protected SubSetList subsetList;
    protected FullTextUrlList fullTextUrlList;
    protected CommentCorrectionList commentCorrectionList;
    protected ExtCommentBean extCommentList;
    protected String isOpenAccess;
    protected String inUKPMC;
    protected String inPMC;
    protected Integer citedByCount;
    protected String hasReferences;
    protected String hasTextMinedTerms;
    protected String hasDbCrossReferences;
    protected CrossReferencesList dbCrossReferenceList;
    protected String hasSupplementary;
    protected String hasFullTextXML;
    protected String hasPDF;
    protected String hasLabsLinks;
    protected String hasTMAccessionNumbers;
    protected TmAccessionTypeList tmAccessionTypeList;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfCompletion;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfCreation;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfRevision;
    @XmlElement(type = String.class)
    @XmlAttachmentRef
    protected DataHandler fullText;
    @XmlElement(type = String.class)
    @XmlAttachmentRef
    protected DataHandler supplementaryFiles;
    protected String luceneScore;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link Request }
     *     
     */
    public Request getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link Request }
     *     
     */
    public void setRequest(Request value) {
        this.request = value;
    }

    /**
     * Gets the value of the citationId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCitationId() {
        return citationId;
    }

    /**
     * Sets the value of the citationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCitationId(Integer value) {
        this.citationId = value;
    }

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
     * Gets the value of the pmid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPmid() {
        return pmid;
    }

    /**
     * Sets the value of the pmid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPmid(String value) {
        this.pmid = value;
    }

    /**
     * Gets the value of the pmcid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPmcid() {
        return pmcid;
    }

    /**
     * Sets the value of the pmcid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPmcid(String value) {
        this.pmcid = value;
    }

    /**
     * Gets the value of the doi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDOI() {
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
    public void setDOI(String value) {
        this.doi = value;
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
     * Gets the value of the authorList property.
     * 
     * @return
     *     possible object is
     *     {@link AuthorsList }
     *     
     */
    public AuthorsList getAuthorList() {
        return authorList;
    }

    /**
     * Sets the value of the authorList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthorsList }
     *     
     */
    public void setAuthorList(AuthorsList value) {
        this.authorList = value;
    }

    /**
     * Gets the value of the investigatorList property.
     * 
     * @return
     *     possible object is
     *     {@link InvestigatorList }
     *     
     */
    public InvestigatorList getInvestigatorList() {
        return investigatorList;
    }

    /**
     * Sets the value of the investigatorList property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvestigatorList }
     *     
     */
    public void setInvestigatorList(InvestigatorList value) {
        this.investigatorList = value;
    }

    /**
     * Gets the value of the journalInfo property.
     * 
     * @return
     *     possible object is
     *     {@link JournalInfo }
     *     
     */
    public JournalInfo getJournalInfo() {
        return journalInfo;
    }

    /**
     * Sets the value of the journalInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JournalInfo }
     *     
     */
    public void setJournalInfo(JournalInfo value) {
        this.journalInfo = value;
    }

    /**
     * Gets the value of the journalTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJournalTitle() {
        return journalTitle;
    }

    /**
     * Sets the value of the journalTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJournalTitle(String value) {
        this.journalTitle = value;
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
     * Gets the value of the journalVolume property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJournalVolume() {
        return journalVolume;
    }

    /**
     * Sets the value of the journalVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJournalVolume(String value) {
        this.journalVolume = value;
    }

    /**
     * Gets the value of the pubYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPubYear() {
        return pubYear;
    }

    /**
     * Sets the value of the pubYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPubYear(String value) {
        this.pubYear = value;
    }

    /**
     * Gets the value of the journalIssn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJournalIssn() {
        return journalIssn;
    }

    /**
     * Sets the value of the journalIssn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJournalIssn(String value) {
        this.journalIssn = value;
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
     * Gets the value of the abstractText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbstractText() {
        return abstractText;
    }

    /**
     * Sets the value of the abstractText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbstractText(String value) {
        this.abstractText = value;
    }

    /**
     * Gets the value of the affiliation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAffiliation() {
        return affiliation;
    }

    /**
     * Sets the value of the affiliation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAffiliation(String value) {
        this.affiliation = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the pubModel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPubModel() {
        return pubModel;
    }

    /**
     * Sets the value of the pubModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPubModel(String value) {
        this.pubModel = value;
    }

    /**
     * Gets the value of the pubType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPubType() {
        return pubType;
    }

    /**
     * Sets the value of the pubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPubType(String value) {
        this.pubType = value;
    }

    /**
     * Gets the value of the pubTypeList property.
     * 
     * @return
     *     possible object is
     *     {@link PubTypeList }
     *     
     */
    public PubTypeList getPubTypeList() {
        return pubTypeList;
    }

    /**
     * Sets the value of the pubTypeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PubTypeList }
     *     
     */
    public void setPubTypeList(PubTypeList value) {
        this.pubTypeList = value;
    }

    /**
     * Gets the value of the brSummary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrSummary() {
        return brSummary;
    }

    /**
     * Sets the value of the brSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrSummary(String value) {
        this.brSummary = value;
    }

    /**
     * Gets the value of the bookOrReportDetails property.
     * 
     * @return
     *     possible object is
     *     {@link BookOrReportDetails }
     *     
     */
    public BookOrReportDetails getBookOrReportDetails() {
        return bookOrReportDetails;
    }

    /**
     * Sets the value of the bookOrReportDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookOrReportDetails }
     *     
     */
    public void setBookOrReportDetails(BookOrReportDetails value) {
        this.bookOrReportDetails = value;
    }

    /**
     * Gets the value of the patentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link PatentDetailsInfo }
     *     
     */
    public PatentDetailsInfo getPatentDetails() {
        return patentDetails;
    }

    /**
     * Sets the value of the patentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatentDetailsInfo }
     *     
     */
    public void setPatentDetails(PatentDetailsInfo value) {
        this.patentDetails = value;
    }

    /**
     * Gets the value of the grantsList property.
     * 
     * @return
     *     possible object is
     *     {@link GrantsList }
     *     
     */
    public GrantsList getGrantsList() {
        return grantsList;
    }

    /**
     * Sets the value of the grantsList property.
     * 
     * @param value
     *     allowed object is
     *     {@link GrantsList }
     *     
     */
    public void setGrantsList(GrantsList value) {
        this.grantsList = value;
    }

    /**
     * Gets the value of the meshHeadingList property.
     * 
     * @return
     *     possible object is
     *     {@link MeshHeadingsList }
     *     
     */
    public MeshHeadingsList getMeshHeadingList() {
        return meshHeadingList;
    }

    /**
     * Sets the value of the meshHeadingList property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeshHeadingsList }
     *     
     */
    public void setMeshHeadingList(MeshHeadingsList value) {
        this.meshHeadingList = value;
    }

    /**
     * Gets the value of the keywordList property.
     * 
     * @return
     *     possible object is
     *     {@link KeywordList }
     *     
     */
    public KeywordList getKeywordList() {
        return keywordList;
    }

    /**
     * Sets the value of the keywordList property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeywordList }
     *     
     */
    public void setKeywordList(KeywordList value) {
        this.keywordList = value;
    }

    /**
     * Gets the value of the chemicalList property.
     * 
     * @return
     *     possible object is
     *     {@link ChemicalList }
     *     
     */
    public ChemicalList getChemicalList() {
        return chemicalList;
    }

    /**
     * Sets the value of the chemicalList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChemicalList }
     *     
     */
    public void setChemicalList(ChemicalList value) {
        this.chemicalList = value;
    }

    /**
     * Gets the value of the subsetList property.
     * 
     * @return
     *     possible object is
     *     {@link SubSetList }
     *     
     */
    public SubSetList getSubsetList() {
        return subsetList;
    }

    /**
     * Sets the value of the subsetList property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubSetList }
     *     
     */
    public void setSubsetList(SubSetList value) {
        this.subsetList = value;
    }

    /**
     * Gets the value of the fullTextUrlList property.
     * 
     * @return
     *     possible object is
     *     {@link FullTextUrlList }
     *     
     */
    public FullTextUrlList getFullTextUrlList() {
        return fullTextUrlList;
    }

    /**
     * Sets the value of the fullTextUrlList property.
     * 
     * @param value
     *     allowed object is
     *     {@link FullTextUrlList }
     *     
     */
    public void setFullTextUrlList(FullTextUrlList value) {
        this.fullTextUrlList = value;
    }

    /**
     * Gets the value of the commentCorrectionList property.
     * 
     * @return
     *     possible object is
     *     {@link CommentCorrectionList }
     *     
     */
    public CommentCorrectionList getCommentCorrectionList() {
        return commentCorrectionList;
    }

    /**
     * Sets the value of the commentCorrectionList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommentCorrectionList }
     *     
     */
    public void setCommentCorrectionList(CommentCorrectionList value) {
        this.commentCorrectionList = value;
    }

    /**
     * Gets the value of the extCommentList property.
     * 
     * @return
     *     possible object is
     *     {@link ExtCommentBean }
     *     
     */
    public ExtCommentBean getExtCommentList() {
        return extCommentList;
    }

    /**
     * Sets the value of the extCommentList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtCommentBean }
     *     
     */
    public void setExtCommentList(ExtCommentBean value) {
        this.extCommentList = value;
    }

    /**
     * Gets the value of the isOpenAccess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsOpenAccess() {
        return isOpenAccess;
    }

    /**
     * Sets the value of the isOpenAccess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsOpenAccess(String value) {
        this.isOpenAccess = value;
    }

    /**
     * Gets the value of the inUKPMC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInUKPMC() {
        return inUKPMC;
    }

    /**
     * Sets the value of the inUKPMC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInUKPMC(String value) {
        this.inUKPMC = value;
    }

    /**
     * Gets the value of the inPMC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInPMC() {
        return inPMC;
    }

    /**
     * Sets the value of the inPMC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInPMC(String value) {
        this.inPMC = value;
    }

    /**
     * Gets the value of the citedByCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCitedByCount() {
        return citedByCount;
    }

    /**
     * Sets the value of the citedByCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCitedByCount(Integer value) {
        this.citedByCount = value;
    }

    /**
     * Gets the value of the hasReferences property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasReferences() {
        return hasReferences;
    }

    /**
     * Sets the value of the hasReferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasReferences(String value) {
        this.hasReferences = value;
    }

    /**
     * Gets the value of the hasTextMinedTerms property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasTextMinedTerms() {
        return hasTextMinedTerms;
    }

    /**
     * Sets the value of the hasTextMinedTerms property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasTextMinedTerms(String value) {
        this.hasTextMinedTerms = value;
    }

    /**
     * Gets the value of the hasDbCrossReferences property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasDbCrossReferences() {
        return hasDbCrossReferences;
    }

    /**
     * Sets the value of the hasDbCrossReferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasDbCrossReferences(String value) {
        this.hasDbCrossReferences = value;
    }

    /**
     * Gets the value of the dbCrossReferenceList property.
     * 
     * @return
     *     possible object is
     *     {@link CrossReferencesList }
     *     
     */
    public CrossReferencesList getDbCrossReferenceList() {
        return dbCrossReferenceList;
    }

    /**
     * Sets the value of the dbCrossReferenceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CrossReferencesList }
     *     
     */
    public void setDbCrossReferenceList(CrossReferencesList value) {
        this.dbCrossReferenceList = value;
    }

    /**
     * Gets the value of the hasSupplementary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasSupplementary() {
        return hasSupplementary;
    }

    /**
     * Sets the value of the hasSupplementary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasSupplementary(String value) {
        this.hasSupplementary = value;
    }

    /**
     * Gets the value of the hasFullTextXML property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasFullTextXML() {
        return hasFullTextXML;
    }

    /**
     * Sets the value of the hasFullTextXML property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasFullTextXML(String value) {
        this.hasFullTextXML = value;
    }

    /**
     * Gets the value of the hasPDF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasPDF() {
        return hasPDF;
    }

    /**
     * Sets the value of the hasPDF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasPDF(String value) {
        this.hasPDF = value;
    }

    /**
     * Gets the value of the hasLabsLinks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasLabsLinks() {
        return hasLabsLinks;
    }

    /**
     * Sets the value of the hasLabsLinks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasLabsLinks(String value) {
        this.hasLabsLinks = value;
    }

    /**
     * Gets the value of the hasTMAccessionNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasTMAccessionNumbers() {
        return hasTMAccessionNumbers;
    }

    /**
     * Sets the value of the hasTMAccessionNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasTMAccessionNumbers(String value) {
        this.hasTMAccessionNumbers = value;
    }

    /**
     * Gets the value of the tmAccessionTypeList property.
     * 
     * @return
     *     possible object is
     *     {@link TmAccessionTypeList }
     *     
     */
    public TmAccessionTypeList getTmAccessionTypeList() {
        return tmAccessionTypeList;
    }

    /**
     * Sets the value of the tmAccessionTypeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TmAccessionTypeList }
     *     
     */
    public void setTmAccessionTypeList(TmAccessionTypeList value) {
        this.tmAccessionTypeList = value;
    }

    /**
     * Gets the value of the dateOfCompletion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfCompletion() {
        return dateOfCompletion;
    }

    /**
     * Sets the value of the dateOfCompletion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfCompletion(XMLGregorianCalendar value) {
        this.dateOfCompletion = value;
    }

    /**
     * Gets the value of the dateOfCreation property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfCreation() {
        return dateOfCreation;
    }

    /**
     * Sets the value of the dateOfCreation property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfCreation(XMLGregorianCalendar value) {
        this.dateOfCreation = value;
    }

    /**
     * Gets the value of the dateOfRevision property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfRevision() {
        return dateOfRevision;
    }

    /**
     * Sets the value of the dateOfRevision property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfRevision(XMLGregorianCalendar value) {
        this.dateOfRevision = value;
    }

    /**
     * Gets the value of the fullText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public DataHandler getFullText() {
        return fullText;
    }

    /**
     * Sets the value of the fullText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullText(DataHandler value) {
        this.fullText = value;
    }

    /**
     * Gets the value of the supplementaryFiles property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public DataHandler getSupplementaryFiles() {
        return supplementaryFiles;
    }

    /**
     * Sets the value of the supplementaryFiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplementaryFiles(DataHandler value) {
        this.supplementaryFiles = value;
    }

    /**
     * Gets the value of the luceneScore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLuceneScore() {
        return luceneScore;
    }

    /**
     * Sets the value of the luceneScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLuceneScore(String value) {
        this.luceneScore = value;
    }

}
