package psidev.psi.mi.tab.model;

/**
 * InteractionDetectionMethod that does not authorize users to modify them.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0-rc1
 */
public class UnmodifiableInteractionDetectionMethod implements InteractionDetectionMethod {

    private InteractionDetectionMethod idm;

    //////////////////
    // Constructor

    private UnmodifiableInteractionDetectionMethod() {
    }

    public UnmodifiableInteractionDetectionMethod( InteractionDetectionMethod cr ) {
        if ( cr == null ) {
            throw new IllegalArgumentException( "You must give a non null InteractionType" );
        }
        this.idm = cr;
    }

    /////////////////
    // Getters

    public String getDatabase() {
        return idm.getDatabase();
    }

    public String getIdentifier() {
        return idm.getIdentifier();
    }

    public String getText() {
        return idm.getText();
    }

    public boolean hasText() {
        return idm.hasText();
    }

    //////////////////////
    // Setters

    public void setDatabase( String database ) {
        throw new ForbiddenOperationException( "If you need to alter Cross Reference, you should disable InteractionDetectionMethod pooling." );
    }

    public void setIdentifier( String identifier ) {
        throw new ForbiddenOperationException( "If you need to alter Cross Reference, you should disable InteractionDetectionMethod pooling." );
    }

    public void setText( String text ) {
        throw new ForbiddenOperationException( "If you need to alter Cross Reference, you should disable InteractionDetectionMethod pooling." );
    }
}