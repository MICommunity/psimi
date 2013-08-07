
package uk.ac.ebi.cdb.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the uk.ac.ebi.cdb.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ProfilePublications_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "profilePublications");
    private final static QName _GetFulltextXML_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "getFulltextXML");
    private final static QName _GetSupplementaryFiles_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "getSupplementaryFiles");
    private final static QName _GetCitationsResponse_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "getCitationsResponse");
    private final static QName _GetDatabaseLinks_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "getDatabaseLinks");
    private final static QName _GetTextMinedTerms_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "getTextMinedTerms");
    private final static QName _SearchPublications_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "searchPublications");
    private final static QName _GetTextMinedTermsResponse_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "getTextMinedTermsResponse");
    private final static QName _GetLabsLinks_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "getLabsLinks");
    private final static QName _QueryException_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "QueryException");
    private final static QName _ListSearchFields_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "listSearchFields");
    private final static QName _GetSupplementaryFilesResponse_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "getSupplementaryFilesResponse");
    private final static QName _GetReferences_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "getReferences");
    private final static QName _GetFulltextXMLResponse_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "getFulltextXMLResponse");
    private final static QName _SearchPublicationsResponse_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "searchPublicationsResponse");
    private final static QName _ListSearchFieldsResponse_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "listSearchFieldsResponse");
    private final static QName _GetLabsLinksResponse_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "getLabsLinksResponse");
    private final static QName _GetReferencesResponse_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "getReferencesResponse");
    private final static QName _GetDatabaseLinksResponse_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "getDatabaseLinksResponse");
    private final static QName _GetCitations_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "getCitations");
    private final static QName _ProfilePublicationsResponse_QNAME = new QName("http://webservice.cdb.ebi.ac.uk/", "profilePublicationsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: uk.ac.ebi.cdb.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCitations }
     * 
     */
    public GetCitations createGetCitations() {
        return new GetCitations();
    }

    /**
     * Create an instance of {@link GetDatabaseLinksResponse }
     * 
     */
    public GetDatabaseLinksResponse createGetDatabaseLinksResponse() {
        return new GetDatabaseLinksResponse();
    }

    /**
     * Create an instance of {@link GetReferencesResponse }
     * 
     */
    public GetReferencesResponse createGetReferencesResponse() {
        return new GetReferencesResponse();
    }

    /**
     * Create an instance of {@link ProfilePublicationsResponse }
     * 
     */
    public ProfilePublicationsResponse createProfilePublicationsResponse() {
        return new ProfilePublicationsResponse();
    }

    /**
     * Create an instance of {@link GetTextMinedTermsResponse }
     * 
     */
    public GetTextMinedTermsResponse createGetTextMinedTermsResponse() {
        return new GetTextMinedTermsResponse();
    }

    /**
     * Create an instance of {@link GetLabsLinks }
     * 
     */
    public GetLabsLinks createGetLabsLinks() {
        return new GetLabsLinks();
    }

    /**
     * Create an instance of {@link GetTextMinedTerms }
     * 
     */
    public GetTextMinedTerms createGetTextMinedTerms() {
        return new GetTextMinedTerms();
    }

    /**
     * Create an instance of {@link SearchPublications }
     * 
     */
    public SearchPublications createSearchPublications() {
        return new SearchPublications();
    }

    /**
     * Create an instance of {@link GetSupplementaryFiles }
     * 
     */
    public GetSupplementaryFiles createGetSupplementaryFiles() {
        return new GetSupplementaryFiles();
    }

    /**
     * Create an instance of {@link GetCitationsResponse }
     * 
     */
    public GetCitationsResponse createGetCitationsResponse() {
        return new GetCitationsResponse();
    }

    /**
     * Create an instance of {@link GetDatabaseLinks }
     * 
     */
    public GetDatabaseLinks createGetDatabaseLinks() {
        return new GetDatabaseLinks();
    }

    /**
     * Create an instance of {@link ProfilePublications }
     * 
     */
    public ProfilePublications createProfilePublications() {
        return new ProfilePublications();
    }

    /**
     * Create an instance of {@link GetFulltextXML }
     * 
     */
    public GetFulltextXML createGetFulltextXML() {
        return new GetFulltextXML();
    }

    /**
     * Create an instance of {@link ListSearchFieldsResponse }
     * 
     */
    public ListSearchFieldsResponse createListSearchFieldsResponse() {
        return new ListSearchFieldsResponse();
    }

    /**
     * Create an instance of {@link GetLabsLinksResponse }
     * 
     */
    public GetLabsLinksResponse createGetLabsLinksResponse() {
        return new GetLabsLinksResponse();
    }

    /**
     * Create an instance of {@link SearchPublicationsResponse }
     * 
     */
    public SearchPublicationsResponse createSearchPublicationsResponse() {
        return new SearchPublicationsResponse();
    }

    /**
     * Create an instance of {@link GetFulltextXMLResponse }
     * 
     */
    public GetFulltextXMLResponse createGetFulltextXMLResponse() {
        return new GetFulltextXMLResponse();
    }

    /**
     * Create an instance of {@link GetReferences }
     * 
     */
    public GetReferences createGetReferences() {
        return new GetReferences();
    }

    /**
     * Create an instance of {@link GetSupplementaryFilesResponse }
     * 
     */
    public GetSupplementaryFilesResponse createGetSupplementaryFilesResponse() {
        return new GetSupplementaryFilesResponse();
    }

    /**
     * Create an instance of {@link ListSearchFields }
     * 
     */
    public ListSearchFields createListSearchFields() {
        return new ListSearchFields();
    }

    /**
     * Create an instance of {@link QueryException }
     * 
     */
    public QueryException createQueryException() {
        return new QueryException();
    }

    /**
     * Create an instance of {@link PatentPriorityData }
     * 
     */
    public PatentPriorityData createPatentPriorityData() {
        return new PatentPriorityData();
    }

    /**
     * Create an instance of {@link MinedTermsList }
     * 
     */
    public MinedTermsList createMinedTermsList() {
        return new MinedTermsList();
    }

    /**
     * Create an instance of {@link SearchTerm }
     * 
     */
    public SearchTerm createSearchTerm() {
        return new SearchTerm();
    }

    /**
     * Create an instance of {@link ResponseWrapper }
     * 
     */
    public ResponseWrapper createResponseWrapper() {
        return new ResponseWrapper();
    }

    /**
     * Create an instance of {@link ExtCommentBean }
     * 
     */
    public ExtCommentBean createExtCommentBean() {
        return new ExtCommentBean();
    }

    /**
     * Create an instance of {@link PubTypeList }
     * 
     */
    public PubTypeList createPubTypeList() {
        return new PubTypeList();
    }

    /**
     * Create an instance of {@link LabsProvider }
     * 
     */
    public LabsProvider createLabsProvider() {
        return new LabsProvider();
    }

    /**
     * Create an instance of {@link CitationData }
     * 
     */
    public CitationData createCitationData() {
        return new CitationData();
    }

    /**
     * Create an instance of {@link GrantsList }
     * 
     */
    public GrantsList createGrantsList() {
        return new GrantsList();
    }

    /**
     * Create an instance of {@link ChemicalInfo }
     * 
     */
    public ChemicalInfo createChemicalInfo() {
        return new ChemicalInfo();
    }

    /**
     * Create an instance of {@link MinedDbIDs }
     * 
     */
    public MinedDbIDs createMinedDbIDs() {
        return new MinedDbIDs();
    }

    /**
     * Create an instance of {@link Authors }
     * 
     */
    public Authors createAuthors() {
        return new Authors();
    }

    /**
     * Create an instance of {@link FullTextUrlList }
     * 
     */
    public FullTextUrlList createFullTextUrlList() {
        return new FullTextUrlList();
    }

    /**
     * Create an instance of {@link TmAccessionTypeList }
     * 
     */
    public TmAccessionTypeList createTmAccessionTypeList() {
        return new TmAccessionTypeList();
    }

    /**
     * Create an instance of {@link MeshHeadingInfo }
     * 
     */
    public MeshHeadingInfo createMeshHeadingInfo() {
        return new MeshHeadingInfo();
    }

    /**
     * Create an instance of {@link MinedTerms }
     * 
     */
    public MinedTerms createMinedTerms() {
        return new MinedTerms();
    }

    /**
     * Create an instance of {@link Request }
     * 
     */
    public Request createRequest() {
        return new Request();
    }

    /**
     * Create an instance of {@link MeshQualifierInfo }
     * 
     */
    public MeshQualifierInfo createMeshQualifierInfo() {
        return new MeshQualifierInfo();
    }

    /**
     * Create an instance of {@link LabsProviderCountList }
     * 
     */
    public LabsProviderCountList createLabsProviderCountList() {
        return new LabsProviderCountList();
    }

    /**
     * Create an instance of {@link ReferenceInfo }
     * 
     */
    public ReferenceInfo createReferenceInfo() {
        return new ReferenceInfo();
    }

    /**
     * Create an instance of {@link MeshQualifierList }
     * 
     */
    public MeshQualifierList createMeshQualifierList() {
        return new MeshQualifierList();
    }

    /**
     * Create an instance of {@link SubSet }
     * 
     */
    public SubSet createSubSet() {
        return new SubSet();
    }

    /**
     * Create an instance of {@link GrantInfo }
     * 
     */
    public GrantInfo createGrantInfo() {
        return new GrantInfo();
    }

    /**
     * Create an instance of {@link PatentFamilyList }
     * 
     */
    public PatentFamilyList createPatentFamilyList() {
        return new PatentFamilyList();
    }

    /**
     * Create an instance of {@link KeywordList }
     * 
     */
    public KeywordList createKeywordList() {
        return new KeywordList();
    }

    /**
     * Create an instance of {@link BookOrReportDetails }
     * 
     */
    public BookOrReportDetails createBookOrReportDetails() {
        return new BookOrReportDetails();
    }

    /**
     * Create an instance of {@link ProfileListBean }
     * 
     */
    public ProfileListBean createProfileListBean() {
        return new ProfileListBean();
    }

    /**
     * Create an instance of {@link ExtComment }
     * 
     */
    public ExtComment createExtComment() {
        return new ExtComment();
    }

    /**
     * Create an instance of {@link LabsProviderCounts }
     * 
     */
    public LabsProviderCounts createLabsProviderCounts() {
        return new LabsProviderCounts();
    }

    /**
     * Create an instance of {@link InvestigatorList }
     * 
     */
    public InvestigatorList createInvestigatorList() {
        return new InvestigatorList();
    }

    /**
     * Create an instance of {@link PatentApplication }
     * 
     */
    public PatentApplication createPatentApplication() {
        return new PatentApplication();
    }

    /**
     * Create an instance of {@link ReferencesList }
     * 
     */
    public ReferencesList createReferencesList() {
        return new ReferencesList();
    }

    /**
     * Create an instance of {@link PatentClassifierList }
     * 
     */
    public PatentClassifierList createPatentClassifierList() {
        return new PatentClassifierList();
    }

    /**
     * Create an instance of {@link CrossReferencesList }
     * 
     */
    public CrossReferencesList createCrossReferencesList() {
        return new CrossReferencesList();
    }

    /**
     * Create an instance of {@link DbCrossReference }
     * 
     */
    public DbCrossReference createDbCrossReference() {
        return new DbCrossReference();
    }

    /**
     * Create an instance of {@link LabsLinks }
     * 
     */
    public LabsLinks createLabsLinks() {
        return new LabsLinks();
    }

    /**
     * Create an instance of {@link ResultList }
     * 
     */
    public ResultList createResultList() {
        return new ResultList();
    }

    /**
     * Create an instance of {@link SemanticTypeCounts }
     * 
     */
    public SemanticTypeCounts createSemanticTypeCounts() {
        return new SemanticTypeCounts();
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link SearchTerms }
     * 
     */
    public SearchTerms createSearchTerms() {
        return new SearchTerms();
    }

    /**
     * Create an instance of {@link AuthorsList }
     * 
     */
    public AuthorsList createAuthorsList() {
        return new AuthorsList();
    }

    /**
     * Create an instance of {@link MinedAltName }
     * 
     */
    public MinedAltName createMinedAltName() {
        return new MinedAltName();
    }

    /**
     * Create an instance of {@link MinedSummary }
     * 
     */
    public MinedSummary createMinedSummary() {
        return new MinedSummary();
    }

    /**
     * Create an instance of {@link PatentDetailsInfo }
     * 
     */
    public PatentDetailsInfo createPatentDetailsInfo() {
        return new PatentDetailsInfo();
    }

    /**
     * Create an instance of {@link PatentPriorityList }
     * 
     */
    public PatentPriorityList createPatentPriorityList() {
        return new PatentPriorityList();
    }

    /**
     * Create an instance of {@link Journal }
     * 
     */
    public Journal createJournal() {
        return new Journal();
    }

    /**
     * Create an instance of {@link JournalInfo }
     * 
     */
    public JournalInfo createJournalInfo() {
        return new JournalInfo();
    }

    /**
     * Create an instance of {@link FullTextURLInfo }
     * 
     */
    public FullTextURLInfo createFullTextURLInfo() {
        return new FullTextURLInfo();
    }

    /**
     * Create an instance of {@link DbCrossReferenceInfo }
     * 
     */
    public DbCrossReferenceInfo createDbCrossReferenceInfo() {
        return new DbCrossReferenceInfo();
    }

    /**
     * Create an instance of {@link Profile }
     * 
     */
    public Profile createProfile() {
        return new Profile();
    }

    /**
     * Create an instance of {@link CommentCorrection }
     * 
     */
    public CommentCorrection createCommentCorrection() {
        return new CommentCorrection();
    }

    /**
     * Create an instance of {@link PatentFamilyData }
     * 
     */
    public PatentFamilyData createPatentFamilyData() {
        return new PatentFamilyData();
    }

    /**
     * Create an instance of {@link CitationList }
     * 
     */
    public CitationList createCitationList() {
        return new CitationList();
    }

    /**
     * Create an instance of {@link DbCounts }
     * 
     */
    public DbCounts createDbCounts() {
        return new DbCounts();
    }

    /**
     * Create an instance of {@link LabsProviders }
     * 
     */
    public LabsProviders createLabsProviders() {
        return new LabsProviders();
    }

    /**
     * Create an instance of {@link SemanticCounts }
     * 
     */
    public SemanticCounts createSemanticCounts() {
        return new SemanticCounts();
    }

    /**
     * Create an instance of {@link PatentClassifierInfo }
     * 
     */
    public PatentClassifierInfo createPatentClassifierInfo() {
        return new PatentClassifierInfo();
    }

    /**
     * Create an instance of {@link SubSetList }
     * 
     */
    public SubSetList createSubSetList() {
        return new SubSetList();
    }

    /**
     * Create an instance of {@link MeshHeadingsList }
     * 
     */
    public MeshHeadingsList createMeshHeadingsList() {
        return new MeshHeadingsList();
    }

    /**
     * Create an instance of {@link ChemicalList }
     * 
     */
    public ChemicalList createChemicalList() {
        return new ChemicalList();
    }

    /**
     * Create an instance of {@link DbCrossReferenceList }
     * 
     */
    public DbCrossReferenceList createDbCrossReferenceList() {
        return new DbCrossReferenceList();
    }

    /**
     * Create an instance of {@link CommentCorrectionList }
     * 
     */
    public CommentCorrectionList createCommentCorrectionList() {
        return new CommentCorrectionList();
    }

    /**
     * Create an instance of {@link DbCountList }
     * 
     */
    public DbCountList createDbCountList() {
        return new DbCountList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProfilePublications }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "profilePublications")
    public JAXBElement<ProfilePublications> createProfilePublications(ProfilePublications value) {
        return new JAXBElement<ProfilePublications>(_ProfilePublications_QNAME, ProfilePublications.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFulltextXML }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "getFulltextXML")
    public JAXBElement<GetFulltextXML> createGetFulltextXML(GetFulltextXML value) {
        return new JAXBElement<GetFulltextXML>(_GetFulltextXML_QNAME, GetFulltextXML.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSupplementaryFiles }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "getSupplementaryFiles")
    public JAXBElement<GetSupplementaryFiles> createGetSupplementaryFiles(GetSupplementaryFiles value) {
        return new JAXBElement<GetSupplementaryFiles>(_GetSupplementaryFiles_QNAME, GetSupplementaryFiles.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCitationsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "getCitationsResponse")
    public JAXBElement<GetCitationsResponse> createGetCitationsResponse(GetCitationsResponse value) {
        return new JAXBElement<GetCitationsResponse>(_GetCitationsResponse_QNAME, GetCitationsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDatabaseLinks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "getDatabaseLinks")
    public JAXBElement<GetDatabaseLinks> createGetDatabaseLinks(GetDatabaseLinks value) {
        return new JAXBElement<GetDatabaseLinks>(_GetDatabaseLinks_QNAME, GetDatabaseLinks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTextMinedTerms }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "getTextMinedTerms")
    public JAXBElement<GetTextMinedTerms> createGetTextMinedTerms(GetTextMinedTerms value) {
        return new JAXBElement<GetTextMinedTerms>(_GetTextMinedTerms_QNAME, GetTextMinedTerms.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPublications }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "searchPublications")
    public JAXBElement<SearchPublications> createSearchPublications(SearchPublications value) {
        return new JAXBElement<SearchPublications>(_SearchPublications_QNAME, SearchPublications.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTextMinedTermsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "getTextMinedTermsResponse")
    public JAXBElement<GetTextMinedTermsResponse> createGetTextMinedTermsResponse(GetTextMinedTermsResponse value) {
        return new JAXBElement<GetTextMinedTermsResponse>(_GetTextMinedTermsResponse_QNAME, GetTextMinedTermsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLabsLinks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "getLabsLinks")
    public JAXBElement<GetLabsLinks> createGetLabsLinks(GetLabsLinks value) {
        return new JAXBElement<GetLabsLinks>(_GetLabsLinks_QNAME, GetLabsLinks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "QueryException")
    public JAXBElement<QueryException> createQueryException(QueryException value) {
        return new JAXBElement<QueryException>(_QueryException_QNAME, QueryException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListSearchFields }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "listSearchFields")
    public JAXBElement<ListSearchFields> createListSearchFields(ListSearchFields value) {
        return new JAXBElement<ListSearchFields>(_ListSearchFields_QNAME, ListSearchFields.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSupplementaryFilesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "getSupplementaryFilesResponse")
    public JAXBElement<GetSupplementaryFilesResponse> createGetSupplementaryFilesResponse(GetSupplementaryFilesResponse value) {
        return new JAXBElement<GetSupplementaryFilesResponse>(_GetSupplementaryFilesResponse_QNAME, GetSupplementaryFilesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReferences }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "getReferences")
    public JAXBElement<GetReferences> createGetReferences(GetReferences value) {
        return new JAXBElement<GetReferences>(_GetReferences_QNAME, GetReferences.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFulltextXMLResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "getFulltextXMLResponse")
    public JAXBElement<GetFulltextXMLResponse> createGetFulltextXMLResponse(GetFulltextXMLResponse value) {
        return new JAXBElement<GetFulltextXMLResponse>(_GetFulltextXMLResponse_QNAME, GetFulltextXMLResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPublicationsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "searchPublicationsResponse")
    public JAXBElement<SearchPublicationsResponse> createSearchPublicationsResponse(SearchPublicationsResponse value) {
        return new JAXBElement<SearchPublicationsResponse>(_SearchPublicationsResponse_QNAME, SearchPublicationsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListSearchFieldsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "listSearchFieldsResponse")
    public JAXBElement<ListSearchFieldsResponse> createListSearchFieldsResponse(ListSearchFieldsResponse value) {
        return new JAXBElement<ListSearchFieldsResponse>(_ListSearchFieldsResponse_QNAME, ListSearchFieldsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLabsLinksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "getLabsLinksResponse")
    public JAXBElement<GetLabsLinksResponse> createGetLabsLinksResponse(GetLabsLinksResponse value) {
        return new JAXBElement<GetLabsLinksResponse>(_GetLabsLinksResponse_QNAME, GetLabsLinksResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReferencesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "getReferencesResponse")
    public JAXBElement<GetReferencesResponse> createGetReferencesResponse(GetReferencesResponse value) {
        return new JAXBElement<GetReferencesResponse>(_GetReferencesResponse_QNAME, GetReferencesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDatabaseLinksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "getDatabaseLinksResponse")
    public JAXBElement<GetDatabaseLinksResponse> createGetDatabaseLinksResponse(GetDatabaseLinksResponse value) {
        return new JAXBElement<GetDatabaseLinksResponse>(_GetDatabaseLinksResponse_QNAME, GetDatabaseLinksResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCitations }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "getCitations")
    public JAXBElement<GetCitations> createGetCitations(GetCitations value) {
        return new JAXBElement<GetCitations>(_GetCitations_QNAME, GetCitations.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProfilePublicationsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.cdb.ebi.ac.uk/", name = "profilePublicationsResponse")
    public JAXBElement<ProfilePublicationsResponse> createProfilePublicationsResponse(ProfilePublicationsResponse value) {
        return new JAXBElement<ProfilePublicationsResponse>(_ProfilePublicationsResponse_QNAME, ProfilePublicationsResponse.class, null, value);
    }

}
