package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

/**
 * Unambiguous polymer comparator.
 * It will first use UnambiguousInteractorBaseComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, it will look at sequence/organism.
 * *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class UnambiguousPolymerComparator extends PolymerComparator{

    private static UnambiguousPolymerComparator unambiguousPolymerComparator;

    /**
     * Creates a new UnambiguousPolymerComparator. It will uses a UnambiguousInteractorBaseComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public UnambiguousPolymerComparator(){
        super(new UnambiguousInteractorBaseComparator(), new OrganismTaxIdComparator());
    }

    public UnambiguousInteractorBaseComparator getInteractorComparator() {
        return (UnambiguousInteractorBaseComparator)super.getInteractorComparator();
    }

    /**
     * Use UnambiguousPolymerComparator to know if two polymers are equals.
     * @param polymer1
     * @param polymer2
     * @return true if the two polymers are equal
     */
    public static boolean areEquals(Polymer polymer1, Polymer polymer2){
        if (unambiguousPolymerComparator == null){
            unambiguousPolymerComparator = new UnambiguousPolymerComparator();
        }

        return unambiguousPolymerComparator.compare(polymer1, polymer2) == 0;
    }
}
