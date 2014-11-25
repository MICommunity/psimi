package psidev.psi.mi.jami.bridges.europubmedcentral;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import uk.ac.ebi.cdb.webservice.*;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Uses the EuroPubmedCentral WSDL SOAP service to fetch publication entries.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 30/07/13
 */
public class EuroPubmedCentralFetcher
        implements PublicationFetcher {

    protected static final Logger log = LoggerFactory.getLogger(EuroPubmedCentralFetcher.class.getName());

    private static final String IDENTIFIER_TYPE = "med";
    private static final String DATA_SET = "metadata";
    private static final String RESULT_TYPE = "core"; // "lite";
    private static final String EMAIL = "psi-dev@gmail.com";

    private static final String WSDL_URL="http://www.ebi.ac.uk/webservices/citexplore/v3.0.1/service?wsdl";
    private static final String WS_NAMESPACE="http://webservice.cdb.ebi.ac.uk/";
    private final static String WS_SERVICE_NAME="WSCitationImplService";

    private WSCitationImplService service;

    /**
     * Initiates the EuroPubmedCentral fetcher
     * @throws BridgeFailedException
     */
    public EuroPubmedCentralFetcher() throws BridgeFailedException {
        this(WSDL_URL);
    }

    /**
     * Uses the current WSDL URL to initiate the service.
     * @param wsdlUrl   The URL of the current version of WSDL in use.
     * @throws BridgeFailedException
     */
    private EuroPubmedCentralFetcher(String wsdlUrl) throws BridgeFailedException {
        try {
            service = new WSCitationImplService(new URL(wsdlUrl), new QName(WS_NAMESPACE, WS_SERVICE_NAME));
        } catch (MalformedURLException e) {
            throw new BridgeFailedException("Unable to initiate the publication fetcher.", e );
        }
    }

    private WSCitationImpl getPort() {
        return service.getWSCitationImplPort();
    }


    /**
     * Queries the EuroPubmedCentral WSDL service for the meta data.
     * A second query is made to gather Xrefs if the meta data shows they exist.
     * @param id    the pubmedID of the publication
     * @return      a completed publication record.
     * @throws BridgeFailedException
     */
    public Publication fetchByIdentifier(String id, String source) throws BridgeFailedException{
        if(id == null)
            throw new IllegalArgumentException("Cannot fetch null identifier");
        if(source == null)
            throw new IllegalArgumentException("Cannot fetch null source. Cane be DOI or PUBMED");

        Collection<Result> results = Collections.EMPTY_LIST;
        String query;
        if(source.equalsIgnoreCase(Xref.DOI)){
            query = "DOI:" + id + " SRC:"+IDENTIFIER_TYPE;
        }
        // if not recognized source or PUBMED, still uses EXT_ID
        else {
            query = "EXT_ID:" + id + " SRC:"+IDENTIFIER_TYPE;
        }

        try {
            ResponseWrapper wrapper = getPort().searchPublications(query, DATA_SET , RESULT_TYPE, 0, false, EMAIL);
            if(wrapper.getResultList() != null)
                results = wrapper.getResultList().getResult();
        } catch (QueryException_Exception e) {
            throw new BridgeFailedException("Problem fetching query: "+query, e);
        }

        Publication publication = null;
        if (!results.isEmpty()) {
            Result entry = results.iterator().next();

            publication = createPublicationFrom(entry);
        }
        return publication;
    }


    /**
     * Uses the PubMed identifiers to search for publications and return completed records.
     * @param identifiers   The identifiers of the publications to search for.
     * @return              Completed records for the publications.
     * @throws BridgeFailedException
     */
    public Collection<Publication> fetchByIdentifiers(Map<String, Collection<String>> identifiers) throws BridgeFailedException {
        if (identifiers == null){
           throw new IllegalArgumentException("The map of identifiers cannot be null");
        }
        Collection<Publication> results = new ArrayList<Publication>();
        for (Map.Entry<String, Collection<String>> identifierSets : identifiers.entrySet()) {
            String source = identifierSets.getKey();
            for (String identifier : identifierSets.getValue()) {
                Publication pub = fetchByIdentifier(identifier, source);
                if (pub != null){
                    results.add(pub);
                }
            }
        }
        return results;
    }

    private Publication createPublicationFrom(Result result){
        Publication publication = new DefaultPublication();
        // PubMed ID
        publication.setPubmedId(result.getPmid());

        // DOI number
        publication.setDoi(result.getDOI());

        // Publication Title
        publication.setTitle(result.getTitle());

        // Journal Name
        if(result.getJournalInfo() != null ){
            if(result.getJournalInfo().getJournal() != null){
                publication.setJournal(result.getJournalInfo().getJournal().getTitle());
            }

            // Publication Date
            Calendar date = new GregorianCalendar(result.getJournalInfo().getYearOfPublication() ,
                    result.getJournalInfo().getMonthOfPublication()-1 , 1); // Dates begin from month 0; Days start from 1
            publication.setPublicationDate(date.getTime());
        }

        // Publication Authors
        for(Authors authors : result.getAuthorList().getAuthor()){
            publication.getAuthors().add(authors.getLastName()+' '+authors.getInitials()+'.');
        }

        return publication;
    }
}
