package psidev.psi.mi.validator.extension.rules.dependencies;

/**
 * Represents the second term in a dependency.
 * @author Marine Dumousseau
 */
public class AssociatedTerm {

    /**
     * second term of the dependency
     */
    private Term secondTermOfTheDependency;

    /**
     * level of the message when a dependency involving this second term is found.
     */
    private String level;

    //////////////////
    // Constructors

    /**
     *
     * @param secondTermOfTheDependency
     * @param level
     */
    public AssociatedTerm( Term secondTermOfTheDependency, String level ) {
        this( secondTermOfTheDependency );
        if (level != null){

            if (!level.equals("NONE")){
                this.level = level;
            }
            else {
                this.level = null;
            }

        }
        else{
            this.level = null;
        }
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
    public String getLevel() {
        return level;
    }

    /**
     *
     * @return the associated term as a String
     */
    @Override
    public String toString() {
        return "Associated term in the dependency{" +
                ", second Term Of The Dependency=" + secondTermOfTheDependency +
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
