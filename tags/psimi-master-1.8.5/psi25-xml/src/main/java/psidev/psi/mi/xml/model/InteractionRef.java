package psidev.psi.mi.xml.model;

/**
 * Reference of an interaction object.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0-beta4
 */
public class InteractionRef {

    private int ref;

    //////////////////
    // Constructors

    public InteractionRef( int ref ) {
        this.ref = ref;
    }

    ///////////////////////////
    // Getters and Setters

    public int getRef() {
        return ref;
    }

    public void setRef( int ref ) {
        this.ref = ref;
    }

    //////////////////////////
    // Object's override

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        InteractionRef that = ( InteractionRef ) o;

        if ( ref != that.ref ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return ref;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "InteractionRef" );
        sb.append( "{ref=" ).append( ref );
        sb.append( '}' );
        return sb.toString();
    }
}
