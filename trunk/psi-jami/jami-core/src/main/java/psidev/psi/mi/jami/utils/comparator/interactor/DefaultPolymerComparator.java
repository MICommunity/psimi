package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

/**
 * Default polymer comparator.
 * It will first use DefaultInteractorBaseComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, it will look at sequence/organism.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class DefaultPolymerComparator extends PolymerComparator {

    private static DefaultPolymerComparator defaultPolymerComparator;

    /**
     * Creates a new DefaultPolymerComparator. It will uses a DefaultInteractorBaseComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public DefaultPolymerComparator(){
        super(new DefaultInteractorBaseComparator(), new OrganismTaxIdComparator());
    }

    @Override
    /**
     * It will first use DefaultInteractorBaseComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, it will look at sequence/organism.
     */
    public int compare(Polymer polymer1, Polymer polymer2) {
        return super.compare(polymer1, polymer2);
    }

    @Override
    public DefaultInteractorBaseComparator getInteractorComparator() {
        return (DefaultInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use DefaultPolymerComparator to know if two proteins are equals.
     * @param polymer1
     * @param polymer2
     * @return true if the two polymers are equal
     */
    public static boolean areEquals(Polymer polymer1, Polymer polymer2){
        if (defaultPolymerComparator == null){
            defaultPolymerComparator = new DefaultPolymerComparator();
        }

        return defaultPolymerComparator.compare(polymer1, polymer2) == 0;
    }
}
