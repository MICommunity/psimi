package psidev.psi.mi.jami.utils.comparator.organism;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;

import java.util.Comparator;

/**
 * Basic organism comparator.
 * It will first look at the taxids. If taxIds are the same , it will look at the cell types.
 * If the cell types are the same, it will look at the tissues. If the tissues are the same, it will look at the compartments.
 * - Two organisms which are null are equals
 * - The organism which is not null is before null.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class OrganismComparator extends OrganismTaxIdComparator {

    protected Comparator<CvTerm> cvTermComparator;

    /**
     * Creates a new OrganismComparator. It needs a cvtermComparator for comparing the cell types, tissues and compartments
     * @param cvTermComparator : cv term comparator is required for comparing cell types, tissues and compartments
     */
    public OrganismComparator(Comparator<CvTerm> cvTermComparator){
        if (cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required to compare the cell type, tissue and compartment. It cannot be null");
        }
        this.cvTermComparator = cvTermComparator;
    }

    public Comparator<CvTerm> getCvTermComparator() {
        return cvTermComparator;
    }

    @Override
    /**
     * It will first look at the taxids. If taxIds are the same , it will look at the cell types.
     * If the cell types are the same, it will look at the tissues. If the tissues are the same, it will look at the compartments.
     * - Two organisms which are null are equals
     * - The organism which is not null is before null.
     */
    public int compare(Organism organism1, Organism organism2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (organism1 == null && organism2 == null){
            return EQUAL;
        }
        else if (organism1 == null){
            return AFTER;
        }
        else if (organism2 == null){
            return BEFORE;
        }
        else {
            // check tax ids first
            int comp = super.compare(organism1, organism2);
            if (comp != 0){
               return comp;
            }

            // then compares the cell type
            CvTerm cellType1 = organism1.getCellType();
            CvTerm cellType2 = organism2.getCellType();

            comp = cvTermComparator.compare(cellType1, cellType2);
            if (comp != 0){
                return comp;
            }

            // then compares the tissues
            CvTerm tissue1 = organism1.getTissue();
            CvTerm tissue2 = organism2.getTissue();

            comp = cvTermComparator.compare(tissue1, tissue2);
            if (comp != 0){
                return comp;
            }

            // then compares the compartments
            CvTerm compartment1 = organism1.getCompartment();
            CvTerm compartment2 = organism2.getCompartment();

            return cvTermComparator.compare(compartment1, compartment2);
        }
    }
}
