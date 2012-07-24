package psidev.psi.mi.tab.model;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 22/06/2012
 * Time: 13:50
 * To change this template use File | Settings | File Templates.
 */
public class ComplexExpansionImpl implements ComplexExpansion {

    /**
     * Generated with IntelliJ plugin generateSerialVersionUID.
     * To keep things consistent, please use the same thing.
     */
    private static final long serialVersionUID = -3808357798606331452L;

    /////////////////////////
    // Instance variables

    /**
     *  Database name.
     */
    private String databaseName;

    /**
     * Identifier
     */
    private String identifier;

    /**
     *  Optional text
     */
    private String expansionName;


    //////////////////////
    // Constructors

    /**
     * Create a new ComplexExpansionImpl object.
     *
     * @param databaseName String with the PSI-MI database name of the CVTerm psi-mi".
     * @param identifier String with the PSI-MI identifier in the previous database.
     */
    public ComplexExpansionImpl(String databaseName, String identifier) {
        this(databaseName, identifier, null);
    }

    /**
     * Create a new ComplexExpansionImpl object.
     *
     * @param databaseName String with the PSI-MI database name of the CVTerm "psi-mi".
     * @param identifier String with the PSI-MI identifier in the previous database .
     * @param expansionName String with the PSI-MI name of the previous CVTerm.
     */
    public ComplexExpansionImpl(String databaseName, String identifier, String expansionName) {
        setDatabaseName(databaseName);
        setIdentifier(identifier);
        setExpansionName(expansionName);
    }

    /**
     * {@inheritDoc}
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * {@inheritDoc}
     */
    public void setDatabaseName(String databaseName) {
        if ( databaseName == null ) {
            throw new IllegalArgumentException( "You must give a non null database." );
        }
        databaseName = databaseName.trim();
        if ( databaseName.length() == 0 ) {
            throw new IllegalArgumentException( "You must give a non empty database." );
        }

        this.databaseName = databaseName;
    }

    /**
     * {@inheritDoc}
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * {@inheritDoc}
     */
    public void setIdentifier(String identifier) {
        if ( identifier == null ) {
            throw new IllegalArgumentException( "You must give a non null identifier." );
        }
        identifier = identifier.trim();
        if ( identifier.length() == 0 ) {
            throw new IllegalArgumentException( "You must give a non empty identifier." );
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getExpansionName() {
        return expansionName;
    }

    /**
     * {@inheritDoc}
     */
    public void setExpansionName(String expansionName) {
        if ( expansionName != null ) {
            // ignore empty string
            expansionName = expansionName.trim();
            if ( expansionName.length() == 0 ) {
                expansionName = null;
            }
        }
        this.expansionName = expansionName;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasExpansionName() {
        return ( expansionName != null && expansionName.trim().length() > 0 );
    }

    //////////////////////////
    // Object's override

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "ComplexExpansion" );
        sb.append( "{database='" ).append( databaseName ).append( '\'' );
        sb.append( ", identifier='" ).append( identifier ).append( '\'' );
        if ( expansionName != null ) {
            sb.append( ", text='" ).append( expansionName ).append( '\'' );
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

        final ComplexExpansionImpl that = ( ComplexExpansionImpl ) o;

        if ( !databaseName.equals( that.databaseName ) ) {
            return false;
        }
        if ( !identifier.equals( that.identifier ) ) {
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
        result = databaseName.hashCode();
        result = 29 * result + identifier.hashCode();
        return result;
    }
}
