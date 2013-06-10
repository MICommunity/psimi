package psidev.psi.mi.jami.mitab;

/**
 * The mitab versions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/06/13</pre>
 */

public enum MitabVersion {

    v2_5(15),
    v2_6(36),
    v2_7(42);


    /////////////////////////////////
    // Constructor

    private final int numberOfColumns;

    private MitabVersion(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }
}
