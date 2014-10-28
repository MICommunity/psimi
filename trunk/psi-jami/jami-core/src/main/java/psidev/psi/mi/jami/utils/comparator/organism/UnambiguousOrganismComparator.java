package psidev.psi.mi.jami.utils.comparator.organism;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;

/**
 * Unambiguous organism comparator.
 * It will first look at the taxids. If taxIds are the same , it will look at the cell types using UnambiguousOrganismComparator.
 * If the cell types are the same, it will look at the tissues using UnambiguousOrganismComparator. If the tissues are the same,
 * it will look at the compartments using UnambiguousOrganismComparator.
 * - Two organisms which are null are equals
 * - The organism which is not null is before null.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class UnambiguousOrganismComparator extends OrganismComparator {

    private static UnambiguousOrganismComparator unambiguousOrganismComparator;

    /**
     * Creates a new UnambiguousOrganismComparator. It will use a UnambiguousCvTermComparator to compare tissues, cell types and compartments.
     */
    public UnambiguousOrganismComparator() {
        super(new UnambiguousCvTermComparator());
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) super.getCvTermComparator();
    }

    @Override
    /**
     * It will first look at the taxids. If taxIds are the same , it will look at the cell types using UnambiguousOrganismComparator.
     * If the cell types are the same, it will look at the tissues using UnambiguousOrganismComparator. If the tissues are the same,
     * it will look at the compartments using UnambiguousOrganismComparator.
     * - Two organisms which are null are equals
     * - The organism which is not null is before null.
     */
    public int compare(Organism organism1, Organism organism2) {
        return super.compare(organism1, organism2);
    }

    /**
     * Use UnambiguousOrganismComparator to know if two organism are equals.
     * @param organism1
     * @param organism2
     * @return true if the two organisms are equal
     */
    public static boolean areEquals(Organism organism1, Organism organism2){
        if (unambiguousOrganismComparator == null){
            unambiguousOrganismComparator = new UnambiguousOrganismComparator();
        }

        return unambiguousOrganismComparator.compare(organism1, organism2) == 0;
    }

    /**
     *
     * @param organism
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Organism organism){
        if (unambiguousOrganismComparator == null){
            unambiguousOrganismComparator = new UnambiguousOrganismComparator();
        }

        if (organism == null){
            return 0;
        }

        int hashcode = 31;
        hashcode = 31*hashcode + organism.getTaxId();

        CvTerm cellType = organism.getCellType();
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(cellType);

        CvTerm tissue = organism.getTissue();
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(tissue);

        CvTerm compartment = organism.getCompartment();
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(compartment);

        return hashcode;
    }
}
