package psidev.psi.mi.tab.model;

/**
 * InteractionType that does not authorize users to modify them.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0-rc1
 */
public class UnmodifiableInteractionType implements InteractionType {

    private InteractionType it;

    //////////////////
    // Constructor

    private UnmodifiableInteractionType() {
    }

    public UnmodifiableInteractionType( InteractionType cr ) {
        if ( cr == null ) {
            throw new IllegalArgumentException( "You must give a non null InteractionType" );
        }
        this.it = cr;
    }

    /////////////////
    // Getters

    public String getDatabase() {
        return it.getDatabase();
    }

    public String getIdentifier() {
        return it.getIdentifier();
    }

    public String getText() {
        return it.getText();
    }

    public boolean hasText() {
        return it.hasText();
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