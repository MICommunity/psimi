package psidev.psi.mi.jami.utils.comparator.organism;

import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Default organism comparator.
 * It will first look at the taxids. If taxIds are the same , it will look at the cell types using DefaultOrganismComparator.
 * If the cell types are the same, it will look at the tissues using DefaultOrganismComparator. If the tissues are the same,
 * it will look at the compartments using DefaultOrganismComparator.
 * - Two organisms which are null are equals
 * - The organism which is not null is before null.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class DefaultOrganismComparator extends OrganismComparator{

    private static DefaultOrganismComparator defaultOrganismComparator;

    /**
     * Creates a new DefaultOrganismComparator. It will use a DefaultCvTermComparator to compare tissues, cell types and compartments.
     */
    public DefaultOrganismComparator() {
        super(new DefaultCvTermComparator());
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) this.cvTermComparator;
    }

    @Override
    /**
     * It will first look at the taxids. If taxIds are the same , it will look at the cell types using DefaultOrganismComparator.
     * If the cell types are the same, it will look at the tissues using DefaultOrganismComparator. If the tissues are the same,
     * it will look at the compartments using DefaultOrganismComparator.
     * - Two organisms which are null are equals
     * - The organism which is not null is before null.
     */
    public int compare(Organism organism1, Organism organism2) {
        return super.compare(organism1, organism2);
    }

    /**
     * Use DefaultOrganismComparator to know if two organism are equals.
     * @param organism1
     * @param organism2
     * @return true if the two organisms are equal
     */
    public static boolean areEquals(Organism organism1, Organism organism2){
        if (defaultOrganismComparator == null){
            defaultOrganismComparator = new DefaultOrganismComparator();
        }

        return defaultOrganismComparator.compare(organism1, organism2) == 0;
    }
}
