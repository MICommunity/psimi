package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Polymer;

/**
 * Unambiguous exact polymer comparator.
 * It will first use UnambiguousExactInteractorBaseComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, it will look at sequence/organism.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class UnambiguousExactPolymerComparator extends UnambiguousPolymerComparator {
    private static UnambiguousExactPolymerComparator unambiguousExactPolymerComparator;

    /**
     * Creates a new UnambiguousExactPolymerComparator. It will uses a UnambiguousExactInteractorBaseComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public UnambiguousExactPolymerComparator(){
        super(new UnambiguousExactInteractorBaseComparator());
    }

    @Override
    /**
     * It will first use DefaultExactInteractorBaseComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, it will look at sequence/organism.
     */
    public int compare(Polymer polymer1, Polymer polymer2) {
        return super.compare(polymer1, polymer2);
    }

    @Override
    public UnambiguousExactInteractorBaseComparator getInteractorComparator() {
        return (UnambiguousExactInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use UnambiguousPolymerComparator to know if two polymers are equals.
     * @param polymer1
     * @param polymer2
     * @return true if the two polymers are equal
     */
    public static boolean areEquals(Polymer polymer1, Polymer polymer2){
        if (unambiguousExactPolymerComparator == null){
            unambiguousExactPolymerComparator = new UnambiguousExactPolymerComparator();
        }

        return unambiguousExactPolymerComparator.compare(polymer1, polymer2) == 0;
    }
}
