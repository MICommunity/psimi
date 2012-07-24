package psidev.psi.mi.tab.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 13/06/2012
 * Time: 14:13
 * To change this template use File | Settings | File Templates.
 */
public class FeatureImpl implements Feature {
    /**
     * Generated with IntelliJ plugin generateSerialVersionUID.
     * To keep things consistent, please use the same thing.
     */
    private static final long serialVersionUID = -7190299784909995251L;

    /////////////////////////
    // Instance variables

    /**
     * Type of the feature.
     */
    private String featureType;

    /**
     * List of Range where appears of the feature.
     */
    private List<String> range;

    /**
     * Optional text, features type names, interpro cross references, etc.
     */
    private String text;

    /**
     * Construct a FeatureImpl object
     *
     * @param featureType String with the PSI-MI name of the CVTerm for the feature
     * @param range range where appears the feature. See more information in:
     * @link https://docs.google.com/spreadsheet/ccc?key=0AhnBwV7LOdY_dE1xbDVSYkpoa3ptZ3NRbzNmdWpySXc&hl=en_GB#gid=0
     */
    public FeatureImpl(String featureType, List<String> range) {
       this(featureType,range,null);
    }


    //////////////////////
    // Constructors

    /**
     * Construct a FeatureImpl object
     *
     * @param featureType String with the PSI-MI name of the CVTerm for the feature
     * @param range Range where appears of the feature. See more information in:
     * @link https://docs.google.com/spreadsheet/ccc?key=0AhnBwV7LOdY_dE1xbDVSYkpoa3ptZ3NRbzNmdWpySXc&hl=en_GB#gid=0
     * @param text optional information like features type names, interpro cross references, etc.
     */
    public FeatureImpl(String featureType, List<String> range, String text) {
        setFeatureType(featureType);
        setRange(range);
        setText(text);
    }
    /**
     * {@inheritDoc}
     */
    public String getFeatureType() {
        return featureType;
    }

    /**
     * {@inheritDoc}
     */
    public void setFeatureType(String featureType) {
        if ( featureType == null ) {
            throw new IllegalArgumentException( "You must give a non null feature type." );
        }
        featureType = featureType.trim();
        if ( featureType.length() == 0 ) {
            throw new IllegalArgumentException( "You must give a non empty feature type." );
        }

        this.featureType = featureType;
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getRanges() {
        return range;
    }

    /**
     * {@inheritDoc}
     */
    public void setRange(List<String> ranges) {
        if ( ranges != null ) {

            if ( ranges.isEmpty() ) {
                text = null;
            }
        }

        this.range = ranges;
    }

    /**
     * {@inheritDoc}
     */
    public String getText() {
        return text;
    }

    /**
     * {@inheritDoc}
     */
    public void setText(String text) {
        if ( text != null ) {
            // ignore empty string
            text = text.trim();
            if ( text.length() == 0 ) {
                text = null;
            }
        }
        this.text = text;
    }

    //////////////////////////
    // Object's override

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Feature" );
        sb.append( "{featureType='" ).append( featureType ).append( '\'' );
        sb.append( ", range='" ).append( range ).append( '\'' );
        if ( text != null ) {
            sb.append( ", text='" ).append( text ).append( '\'' );
        }
        sb.append( '}' );
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        final FeatureImpl that = ( FeatureImpl ) o;

        if ( !featureType.equals( that.featureType ) ) {
            return false;
        }
        if ( !range.equals( that.range ) ) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result;
        result = featureType.hashCode();
        result = 29 * result + range.hashCode();
        return result;
    }
}
