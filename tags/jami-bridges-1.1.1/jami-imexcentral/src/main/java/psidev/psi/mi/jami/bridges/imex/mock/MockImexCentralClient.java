package psidev.psi.mi.jami.bridges.imex.mock;

import edu.ucla.mbi.imex.central.ws.v20.IcentralFault;
import edu.ucla.mbi.imex.central.ws.v20.Identifier;
import edu.ucla.mbi.imex.central.ws.v20.ImexCentralFault;
import edu.ucla.mbi.imex.central.ws.v20.Publication;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.imex.ImexCentralClient;
import psidev.psi.mi.jami.bridges.imex.Operation;
import psidev.psi.mi.jami.bridges.imex.PublicationStatus;
import psidev.psi.mi.jami.bridges.imex.extension.ImexPublication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Dummy service that one can use to write test against.
 *
 */
public class MockImexCentralClient implements ImexCentralClient {

    public MockImexCentralClient() {
        allPublications = new ArrayList<psidev.psi.mi.jami.model.Publication>( );
    }

    private int imexIdSequence = 1;
    List<psidev.psi.mi.jami.model.Publication> allPublications;
    private static Pattern PUBMED_REGEXP = Pattern.compile("\\d+");

    private String INTACT_GROUP = "INTACT";
    private String MATRIXDB_GROUP = "MATRIXDB";
    private String intact_user = "intact";
    private String phantom_user = "phantom";

    private static Pattern pubmed_regexp = Pattern.compile("\\d+");

    /////////////////////////////
    // Service initialization

    public void initImexSequence( int sequence ) {
        if( sequence < 1 ) {
            throw new IllegalArgumentException( "You must give a positive sequence: " + sequence );
        }
        imexIdSequence = sequence;
    }

    public void initPublications( List<psidev.psi.mi.jami.model.Publication> allPublications ) {
        if ( allPublications == null ) {
            throw new IllegalArgumentException( "You must give a non null allPublications" );
        }
        this.allPublications = allPublications;
    }

    public int getNextSequenceValue() {
        return imexIdSequence;
    }

    public void addPublication( psidev.psi.mi.jami.model.Publication p ) {
        if ( p == null ) {
            throw new IllegalArgumentException( "You must give a non null publication" );
        }
        allPublications.add( p );
    }

    public void addPublication( String identifier, String imexAccession, String status, String owner ) {
        Publication p = new Publication();
        final Identifier id = new Identifier();
        id.setAc( identifier );
        p.getIdentifier().add(id);
        p.setImexAccession( ( imexAccession == null ? "N/A" : imexAccession ) );
        p.setStatus( status );
        p.setOwner( owner );
        allPublications.add( new ImexPublication(p));
    }

    ///////////////////////
    // ImexCentralClient

    public String getEndpoint() {
        return null;
    }

    public psidev.psi.mi.jami.model.Publication fetchByIdentifier( String identifier, String source ) throws BridgeFailedException {

        for ( psidev.psi.mi.jami.model.Publication p : allPublications ) {
            for (Xref i : p.getIdentifiers()){
                if( XrefUtils.doesXrefHaveDatabaseAndId(i, null, source, identifier) ) {
                    return p;
                }
            }

            if (p.getImexId() != null && p.getImexId().equalsIgnoreCase(identifier)){
                return p;
            }
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

    public List<psidev.psi.mi.jami.model.Publication> fetchPublicationsByOwner( String owner, int first, int max) throws BridgeFailedException {
        List<psidev.psi.mi.jami.model.Publication> publications = new ArrayList<psidev.psi.mi.jami.model.Publication>( );
        for ( int i = first; i <= max; i++ ) {
            psidev.psi.mi.jami.model.Publication p = allPublications.get(i);
            if( p instanceof  ImexPublication && owner.equals(((ImexPublication)p).getOwner()) ) {
                publications.add( p );
            }
        }
        return publications;
    }

    public List<psidev.psi.mi.jami.model.Publication> fetchPublicationsByStatus( String status, int first, int max) throws BridgeFailedException{
        List<psidev.psi.mi.jami.model.Publication> publications = new ArrayList<psidev.psi.mi.jami.model.Publication>( );
        for ( int j = first; j <= max; j++ ) {
            psidev.psi.mi.jami.model.Publication p = allPublications.get(j);

            if( p instanceof  ImexPublication && status.equals(((ImexPublication)p).getStatus()) ) {
                publications.add( p );
            }
        }
        return publications;
    }

    public psidev.psi.mi.jami.model.Publication updatePublicationStatus(String identifier, String source, PublicationStatus status) throws BridgeFailedException {
        psidev.psi.mi.jami.model.Publication p = fetchByIdentifier(identifier, source);

        if (p != null && p instanceof ImexPublication){
            ((ImexPublication) p).setStatus(status);
        }
        else {
            ImexCentralFault imexFault = new ImexCentralFault();
            imexFault.setFaultCode(6);

            IcentralFault fault = new IcentralFault("No publication record found", imexFault);
            throw new BridgeFailedException(fault);
        }

        return p;
    }

    public psidev.psi.mi.jami.model.Publication updatePublicationAdminGroup( String identifier, String source, Operation operation, String group ) throws BridgeFailedException {
        psidev.psi.mi.jami.model.Publication p = fetchByIdentifier(identifier, source);

        if (p != null && p instanceof ImexPublication){
            if (group != null && (group.equalsIgnoreCase(INTACT_GROUP) || group.equalsIgnoreCase(MATRIXDB_GROUP))){
                ((ImexPublication) p).getSources().add(new DefaultSource(group));
            }
            // group not recognized, throw exception as in the real webservice
            else {
                ImexCentralFault imexFault = new ImexCentralFault();
                imexFault.setFaultCode(11);

                IcentralFault fault = new IcentralFault("Group not recognized", imexFault);
                throw new BridgeFailedException(fault);
            }
        }
        else if (p != null){
            p.setSource(new DefaultSource(group));
        }
        else {
            ImexCentralFault imexFault = new ImexCentralFault();
            imexFault.setFaultCode(6);

            IcentralFault fault = new IcentralFault("No publication record found", imexFault);
            throw new BridgeFailedException(fault);
        }

        return p;
    }

    public psidev.psi.mi.jami.model.Publication updatePublicationAdminUser( String identifier, String source, Operation operation, String user ) throws BridgeFailedException {
        psidev.psi.mi.jami.model.Publication p = fetchByIdentifier(identifier, source);

        if (p != null && p instanceof ImexPublication){
            if (user != null && (user.equalsIgnoreCase(intact_user) || user.equalsIgnoreCase(phantom_user))){
                ((ImexPublication) p).getCurators().add(user.toLowerCase());
            }
            // user not recognized, throw exception as in the real webservice
            else {
                ImexCentralFault imexFault = new ImexCentralFault();
                imexFault.setFaultCode(10);

                IcentralFault fault = new IcentralFault("User not recognized", imexFault);
                throw new BridgeFailedException(fault);
            }
        }
        else {
            ImexCentralFault imexFault = new ImexCentralFault();
            imexFault.setFaultCode(6);

            IcentralFault fault = new IcentralFault("No publication record found", imexFault);
            throw new BridgeFailedException(fault);
        }
        
        return p;
    }

    public psidev.psi.mi.jami.model.Publication updatePublicationIdentifier(String oldIdentifier, String oldSource, String newIdentifier, String source) throws BridgeFailedException {
        psidev.psi.mi.jami.model.Publication existingPub = fetchByIdentifier(newIdentifier, source);

        // if the new identifier is already in IMEx central, we don't update anything
        if (existingPub != null){
            ImexCentralFault imexFault = new ImexCentralFault();
            imexFault.setFaultCode(3);

            IcentralFault fault = new IcentralFault("New publication identifier " + newIdentifier + "already used in IMEx central.", imexFault);
            throw new BridgeFailedException( "Impossible to update the identifier of " + oldIdentifier, fault );
        }
        
        final psidev.psi.mi.jami.model.Publication p = fetchByIdentifier(oldIdentifier, oldSource);
        if( p != null ) {
            Xref newId = XrefUtils.createIdentityXref(source, newIdentifier);

            Iterator<Xref> refIterator = p.getIdentifiers().iterator();
            for (Xref id : p.getIdentifiers()){
                // update the proper identifier in IMEx central
                if (DefaultCvTermComparator.areEquals(id.getDatabase(), newId.getDatabase())){
                    refIterator.remove();
                }
            }

            p.getIdentifiers().add(newId);
        }
        else {
            ImexCentralFault imexFault = new ImexCentralFault();
            imexFault.setFaultCode(6);

            IcentralFault fault = new IcentralFault("No publication record found", imexFault);
            throw new BridgeFailedException(fault);
        }
        
        return p;
    }

    public void createPublication( psidev.psi.mi.jami.model.Publication publication ) throws BridgeFailedException {
        psidev.psi.mi.jami.model.Publication existingP = null;

        for ( psidev.psi.mi.jami.model.Publication p : allPublications ) {
            for (Xref i : p.getIdentifiers()){
                if( !publication.getIdentifiers().isEmpty() && publication.getIdentifiers().iterator().next().getId().equals( i.getId() ) ) {
                    existingP = p;
                    break;
                }
            }
        }

        if (existingP == null){
            allPublications.add(publication);
        }
        else {
            allPublications.remove(existingP);
            allPublications.add(publication);
        }
    }

    public psidev.psi.mi.jami.model.Publication createPublicationById( String identifier, String source ) throws BridgeFailedException {

        psidev.psi.mi.jami.model.Publication p = new ImexPublication(new Publication());
        p.getIdentifiers().add(XrefUtils.createIdentityXref(source, identifier));
        allPublications.add( p );
        return p;
    }

    public psidev.psi.mi.jami.model.Publication fetchPublicationImexAccession( String identifier, String source, boolean create ) throws BridgeFailedException {
        final psidev.psi.mi.jami.model.Publication p = fetchByIdentifier(identifier, source);
        
        if (p != null){
            if( create ) {
                if( p.getImexId() != null) {
                    throw new IllegalStateException( "Publication already has an IMEx id: " + p.getImexId() );
                }

                // assigning new IMEx ID
                p.assignImexId("IM-" + imexIdSequence);
                imexIdSequence++;
            }
        }
        else {
            ImexCentralFault imexFault = new ImexCentralFault();
            imexFault.setFaultCode(6);

            IcentralFault fault = new IcentralFault("No publication record found", imexFault);
            throw new BridgeFailedException(fault);
        }

        return p;
    }
}
