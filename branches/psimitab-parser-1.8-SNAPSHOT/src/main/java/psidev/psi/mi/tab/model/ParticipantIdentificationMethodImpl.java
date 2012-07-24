package psidev.psi.mi.tab.model;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 22/06/2012
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */
public class ParticipantIdentificationMethodImpl extends  CrossReferenceImpl implements ParticipantIdentificationMethod {

    //////////////////////
    // Constructors

    /**
     * {@inheritDoc}
     */
    public ParticipantIdentificationMethodImpl() {
        super();
    }
    /**
     * {@inheritDoc}
     */
    public ParticipantIdentificationMethodImpl( String databaseName, String identifier ) {
        super( databaseName, identifier );
    }
    /**
     * {@inheritDoc}
     */
    public ParticipantIdentificationMethodImpl( String database, String identifier, String text ) {
        super( database, identifier, text );
    }
}
