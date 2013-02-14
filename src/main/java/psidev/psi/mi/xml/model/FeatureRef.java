package psidev.psi.mi.xml.model;

/**
 * Reference of a feature object.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0-beta4
 */
public class FeatureRef {

    private int ref;

    //////////////////
    // Constructors

    public FeatureRef( int ref ) {
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

        FeatureRef that = ( FeatureRef ) o;

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
        sb.append( "FeatureRef" );
        sb.append( "{ref=" ).append( ref );
        sb.append( '}' );
        return sb.toString();
    }
}