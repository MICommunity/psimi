package psidev.psi.mi.tab.model.builder;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 13/07/2012
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 */
public enum PsimiTabVersion {

    v2_5(15),
    v2_6(36),
    v2_7(42);


    /////////////////////////////////
    // Constructor

    private final int numberOfColumns;

    private PsimiTabVersion(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }
}
