package psidev.psi.mi.validator.extension.rules.dependencies;

/**
 * Represents the second term in a dependency.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: AssociatedTerm.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class AssociatedTerm {

    /**
     * second term of the dependency
     */
    private Term secondTermOfTheDependency;

    /**
     * level of the message when a dependency involving this second term is found.
     */
    private DependencyLevel level;

    //////////////////
    // Constructors

    /**
     *
     * @param secondTermOfTheDependency
     * @param level
     */
    public AssociatedTerm( Term secondTermOfTheDependency, String level ) {
        this( secondTermOfTheDependency );

        if ("required".equalsIgnoreCase(level)){
            this.level = DependencyLevel.REQUIRED;
        }
        else if ("should".equalsIgnoreCase(level)) {
            this.level = DependencyLevel.SHOULD;
        }
        else if ("error".equalsIgnoreCase(level)) {
            this.level = DependencyLevel.ERROR;
        }
        else {
            throw new ValidatorRuleException("The level " + level + " is not valid. The level of a dependency can be REQUIRED (at least one of the dependency with the level REQUIRED should be found otherwise an error is thrown)," +
                    " SHOULD (at least one of the dependency with the level SHOULD should be found otherwise a warning is thrown) or ERROR (if one of the dependency with the level ERROR has been found, an error is thrown).");
        }
    }

    public AssociatedTerm( Term secondTermOfTheDependency, DependencyLevel level ) {
        this( secondTermOfTheDependency );

        this.level = level;
    }

    /**
     *
     * @param secondTermOfTheDependency
     */
    public AssociatedTerm( Term secondTermOfTheDependency ) {

        if ( secondTermOfTheDependency == null ) {
            throw new IllegalArgumentException( "You must give a non null second Term Of The Dependency" );
        }
        this.secondTermOfTheDependency = secondTermOfTheDependency;
    }

    /**
     *
     * @return the second term of the dependency
     */
    public Term getSecondTermOfTheDependency() {
        return secondTermOfTheDependency;
    }

    /**
     *
     * @return  the message level for this dependency
     */
    public DependencyLevel getLevel() {
        return level;
    }

    /**
     *
     * @return the associated term as a String
     */
    @Override
    public String toString() {
        return "Second term of the dependency={" + secondTermOfTheDependency +
                ", level=" + level +
                '}';
    }

    /**
     *
     * @param o
     * @return true if a o is equal to this term
     */
    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        AssociatedTerm secondTerm = (AssociatedTerm) o;

        if ( !secondTermOfTheDependency.equals( secondTerm.getSecondTermOfTheDependency()) ) return false;

        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = secondTermOfTheDependency.hashCode();
        return result;
    }
}
