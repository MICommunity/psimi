package psidev.psi.mi.tab.model;

/**
 * CrossReference that does not authorize users to modify them.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0-rc1
 */
public class UnmodifiableCrossReference implements CrossReference {

    private CrossReference cr;

    //////////////////
    // Constructor

    private UnmodifiableCrossReference() {
    }

    public UnmodifiableCrossReference( CrossReference cr ) {
        if ( cr == null ) {
            throw new IllegalArgumentException( "You must give a non null CrossReference" );
        }
        this.cr = cr;
    }

    /////////////////
    // Getters

    public String getDatabase() {
        return cr.getDatabase();
    }

    public String getIdentifier() {
        return cr.getIdentifier();
    }

    public String getText() {
        return cr.getText();
    }

    public boolean hasText() {
        return cr.hasText();
    }

    //////////////////////
    // Setters

    public void setDatabase( String database ) {
        throw new ForbiddenOperationException( "If you need to alter Cross Reference, you should disable CrossReference pooling." );
    }

    public void setIdentifier( String identifier ) {
        throw new ForbiddenOperationException( "If you need to alter Cross Reference, you should disable CrossReference pooling." );
    }

    public void setText( String text ) {
        throw new ForbiddenOperationException( "If you need to alter Cross Reference, you should disable CrossReference pooling." );
    }
}