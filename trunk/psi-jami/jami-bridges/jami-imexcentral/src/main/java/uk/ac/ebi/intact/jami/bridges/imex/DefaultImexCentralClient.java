package uk.ac.ebi.intact.jami.bridges.imex;

import edu.ucla.mbi.imex.central.ws.v20.*;
import edu.ucla.mbi.imex.central.ws.v20.Publication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.*;
import uk.ac.ebi.intact.jami.bridges.imex.extension.ImexPublication;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Default IMEx Central Client.
 *
 */
public class DefaultImexCentralClient implements ImexCentralClient {

    private static final Log log = LogFactory.getLog( DefaultImexCentralClient.class );

    public static final String IC_TEST = "https://imexcentral.org/icentraltest/ws-v20";
    public static final String IC_BETA = "https://imexcentral.org/icentralbeta/ws-v20";
    public static final String IC_PROD = "https://imexcentral.org/icentral/ws-v20";

    private IcentralService service;
    private IcentralPort port;
    private String endPoint;

    private static Pattern pubmed_regexp = Pattern.compile("\\d+");

    public final static int USER_NOT_AUTHORIZED = 2;
    public final static int OPERATION_NOT_VALID = 3;
    public final static int IDENTIFIER_MISSING = 4;
    public final static int IDENTIFIER_UNKNOWN = 5;
    public final static int NO_RECORD = 6;
    public final static int NO_RECORD_CREATED = 7;
    public final static int STATUS_UNKNOWN = 8;
    public final static int NO_IMEX_ID = 9;
    public final static int UNKNOWN_USER = 10;
    public final static int UNKNOWN_GROUP = 11;
    public final static int OPERATION_NOT_SUPPORTED = 98;
    public final static int INTERNAL_SERVER_ERROR = 99;

    public DefaultImexCentralClient( String username, String password, String endPoint ) throws BridgeFailedException {

        this.endPoint = endPoint;

        try {
            URL url = new URL( endPoint + "?wsdl" );
            log.debug( "WSDL: " + endPoint + "?wsdl" );
            QName qn = new QName( "http://imex.mbi.ucla.edu/icentral/ws", "ics20" );
            service = new IcentralService( url, qn );
            port = service.getIcp20();

            ( ( BindingProvider ) port ).getRequestContext().put( BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint );
            ( ( BindingProvider ) port ).getRequestContext().put( BindingProvider.USERNAME_PROPERTY, username );
            ( ( BindingProvider ) port ).getRequestContext().put( BindingProvider.PASSWORD_PROPERTY, password );
        } catch ( MalformedURLException e ) {
            throw new BridgeFailedException( "Error while initializing IMEx Central Client", e );
        }
    }

    ///////////////////////
    // IMEx Central

    public String getEndpoint() {
        return endPoint;
    }

    public List<psidev.psi.mi.jami.model.Publication> fetchPublicationsByOwner(String owner, int first, int max) throws BridgeFailedException {
        try {
            // create holders for publication and last record
            Holder<PublicationList> pubList = new Holder<PublicationList>();
            Holder<Long> number = new Holder<Long>();

            port.getPublicationByOwner( owner, first, max, pubList, number );
            if( pubList.value != null) {
                List<Publication> pubs = pubList.value.getPublication();
                List<psidev.psi.mi.jami.model.Publication> publications = new ArrayList<psidev.psi.mi.jami.model.Publication>(pubs.size());
                for (Publication pub : pubs){
                    if (pub != null){
                        publications.add(new ImexPublication(pub));
                    }
                }
                return publications;
            }
            return Collections.EMPTY_LIST;
        } catch ( IcentralFault f ) {
            switch( f.getFaultInfo().getFaultCode() ) {
                case NO_RECORD:
                    // simply no data found, return empty list
                    return Collections.EMPTY_LIST;
            }

            throw new BridgeFailedException( "Error while getting publications by owner: " + owner, f );
        }
    }

    public List<psidev.psi.mi.jami.model.Publication> fetchPublicationsByStatus(String status, int first, int max) throws BridgeFailedException {

        try {
            // create holders for publication and last record
            Holder<PublicationList> pubList = new Holder<PublicationList>();
            Holder<Long> number = new Holder<Long>();

            port.getPublicationByStatus( status, first, max, pubList, number );

            if( pubList.value != null) {
                List<Publication> pubs = pubList.value.getPublication();
                List<psidev.psi.mi.jami.model.Publication> publications = new ArrayList<psidev.psi.mi.jami.model.Publication>(pubs.size());
                for (Publication pub : pubs){
                    if (pub != null){
                        publications.add(new ImexPublication(pub));
                    }
                }
                return publications;
            }
            return Collections.EMPTY_LIST;

        } catch ( IcentralFault f ) {
            switch( f.getFaultInfo().getFaultCode() ) {
                case NO_RECORD:
                    // simply no data found, return empty list
                    return Collections.EMPTY_LIST;
            }

            throw new BridgeFailedException( "Error while getting publications by status: " + status, f );
        }
    }

    /**
     *
     * @param identifier
     * @param status
     * @return an updated publication, null if not found.
     * @throws psidev.psi.mi.jami.bridges.exception.BridgeFailedException
     */
    public psidev.psi.mi.jami.model.Publication updatePublicationStatus( String identifier, String source, PublicationStatus status) throws BridgeFailedException {
        try {
            Publication pub = port.updatePublicationStatus( buildIdentifier( identifier, source ), status.toString(), null );
            if (pub != null){
               return new ImexPublication(pub);
            }
        } catch ( IcentralFault f ) {

            throw new BridgeFailedException( "Error while attempting to update a publication status: " +
                    identifier + "/" + status, f );
        }
        return null;
    }

    public psidev.psi.mi.jami.model.Publication updatePublicationAdminGroup( String identifier, String source, Operation operation, String group ) throws BridgeFailedException {
        try {
            Publication pub = port.updatePublicationAdminGroup( buildIdentifier(identifier, source ), operation.toString(), group );
            if (pub != null){
                return new ImexPublication(pub);
            }
        } catch ( IcentralFault f ) {

            throw new BridgeFailedException( "Error while attempting to upate the admin group to '"+
                    group +"' for publication " + identifier, f );
        }
        return null;
    }

    public psidev.psi.mi.jami.model.Publication updatePublicationAdminUser( String identifier, String source, Operation operation, String user ) throws BridgeFailedException {
        try {
            Publication pub = port.updatePublicationAdminUser( buildIdentifier(identifier, source ), operation.toString(), user );
            if (pub != null){
                return new ImexPublication(pub);
            }
        } catch ( IcentralFault f ) {

            throw new BridgeFailedException( "Error while attempting to upate the admin user to '"+
                    user +"' for publication " + identifier, f );
        }
        return null;
    }

    public psidev.psi.mi.jami.model.Publication updatePublicationIdentifier(String oldIdentifier, String newIdentifier, String source) throws BridgeFailedException {

        psidev.psi.mi.jami.model.Publication existingPub = fetchByIdentifier(newIdentifier, source);

        // if the new identifier is already in IMEx central, we don't update anything
        if (existingPub != null){
            ImexCentralFault imexFault = new ImexCentralFault();
            imexFault.setFaultCode(3);

            IcentralFault fault = new IcentralFault("New publication identifier " + newIdentifier + "already used in IMEx central.", imexFault);
            throw new BridgeFailedException( "Impossible to update the identifier of " + oldIdentifier, fault );
        }

        try {
            Publication pub = port.updatePublicationIdentifier( buildIdentifier(oldIdentifier, source ), buildIdentifier(newIdentifier, source) );
            if (pub != null){
                return new ImexPublication(pub);
            }
        } catch ( IcentralFault f ) {

            throw new BridgeFailedException( "Error while attempting to upate the identifier to '"+
                    newIdentifier +"' for publication " + oldIdentifier, f );
        }
        return null;
    }

    public void createPublication( psidev.psi.mi.jami.model.Publication publication ) throws BridgeFailedException {
        if (publication != null){
            try {
                Publication pub = new Publication();
                pub.setTitle(publication.getTitle());
                pub.setImexAccession(publication.getImexId());
                for (Xref id : publication.getIdentifiers()){
                    Identifier identifier = buildIdentifier(id.getId(), id.getDatabase().getShortName());
                    if (identifier != null){
                        pub.getIdentifier().add(identifier);
                    }
                }

                if (publication.getPublicationDate() != null){
                    GregorianCalendar c = new GregorianCalendar();
                    c.setTime(publication.getPublicationDate());
                    try {
                        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                        pub.setPublicationDate(date2);
                    } catch (DatatypeConfigurationException e) {
                        throw new BridgeFailedException( "Error while creating a publication: " + publication, e);
                    }

                }

                if (publication.getReleasedDate() != null){
                    GregorianCalendar c = new GregorianCalendar();
                    c.setTime(publication.getReleasedDate());
                    try {
                        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                        pub.setReleaseDate(date2);
                    } catch (DatatypeConfigurationException e) {
                        throw new BridgeFailedException( "Error while creating a publication: " + publication, e);
                    }

                }

                if (!publication.getAuthors().isEmpty()){
                    StringBuffer buffer = new StringBuffer();
                    Iterator<String> authorIterator = publication.getAuthors().iterator();
                    while (authorIterator.hasNext()){
                         buffer.append(authorIterator.next());
                        if (authorIterator.hasNext()){
                           buffer.append(",");
                        }
                    }
                    pub.setAuthor(buffer.toString());
                }

                if (publication.getSource() != null){
                    pub.setAdminGroupList(new Publication.AdminGroupList());
                    pub.getAdminGroupList().getGroup().add(publication.getSource().getShortName().toUpperCase());
                }

                port.createPublication( new Holder( publication ) );
            } catch ( IcentralFault f ) {
                throw new BridgeFailedException( "Error while creating a publication: " + publication, f);
            }
        }
    }

    public psidev.psi.mi.jami.model.Publication createPublicationById( String identifier, String source ) throws BridgeFailedException {
        try {
            Publication pub = port.createPublicationById( buildIdentifier( identifier, source ) );
            if (pub != null){
                return new ImexPublication(pub);
            }
        } catch ( IcentralFault f ) {
            throw new BridgeFailedException( "Error while creating a publication by id: " + identifier, f );
        }
        return null;
    }

    public psidev.psi.mi.jami.model.Publication fetchPublicationImexAccession(String identifier, String source, boolean aBoolean) throws BridgeFailedException {
        try {
            Publication pub = port.getPublicationImexAccession( buildIdentifier( identifier, source ), aBoolean );
            if (pub != null){
                return new ImexPublication(pub);
            }
        } catch ( IcentralFault f ) {
            throw new BridgeFailedException("Error while getting an IMEx id by publication by id: " + identifier, f);
        }
        return null;
    }

    public psidev.psi.mi.jami.model.Publication fetchByIdentifier(String identifier, String source) throws BridgeFailedException {
        if(identifier == null)
            throw new IllegalArgumentException("Cannot fetch null identifier");
        if(source == null)
            throw new IllegalArgumentException("Cannot fetch null source. Cane be IMEX, DOI or PUBMED");
        try {
            Publication pub = port.getPublicationById(buildIdentifier(identifier, source));
            if (pub != null){
                return new ImexPublication(pub);
            }
        } catch ( IcentralFault f ) {
            switch( f.getFaultInfo().getFaultCode() ) {
                case NO_RECORD:
                    // simply no data found, return null
                    return null;
            }

            throw new BridgeFailedException("Impossible to find the publication " + identifier, f);
        }
        return null;
    }

    public Collection<psidev.psi.mi.jami.model.Publication> fetchByIdentifiers(Map<String, Collection<String>> identifiers) throws BridgeFailedException {
        if(identifiers == null)
            throw new IllegalArgumentException("The map of identifiers cannot be null");
        Collection<psidev.psi.mi.jami.model.Publication> results = new ArrayList<psidev.psi.mi.jami.model.Publication>();
        for (Map.Entry<String, Collection<String>> identifierSets : identifiers.entrySet()) {
            String source = identifierSets.getKey();
            for (String identifier : identifierSets.getValue()) {
                psidev.psi.mi.jami.model.Publication pub = fetchByIdentifier(identifier, source);
                if (pub != null){
                    results.add(pub);
                }
            }
        }
        return results;
    }

    private Identifier buildIdentifier( String identifier, String source ) {
        final Identifier id = new ObjectFactory().createIdentifier();
        id.setAc( identifier );
        // IMEx identifier
        if( source.equals("imex") && identifier.startsWith( "IM-" ) ) {
            // this will enable searching for publication by IMEx id ... not obvious but it works ...
            id.setNs( "imex" );
        }
        // valid pubmed identifier
        else if (source.equals("pubmed") && Pattern.matches(pubmed_regexp.toString(), identifier)) {
            // fallback namespace
            id.setNs( "pmid" );
        }
        // unassigned publication = internal identifier or internal identifiers
        else if (source.equals("pubmed")){
            id.setNs( "jint" );
        }
        // doi number
        else if (source.equals("doi")){
            id.setNs("doi");
        }
        else{
            id.setNs( "jint" );
        }
        return id;
    }
}
