package psidev.psi.mi.jami.utils.comparator.organism;

import psidev.psi.mi.jami.model.CvTerm;
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

public class DefaultOrganismComparator {

    /**
     * Use DefaultOrganismComparator to know if two organism are equals.
     * @param organism1
     * @param organism2
     * @return true if the two organisms are equal
     */
    public static boolean areEquals(Organism organism1, Organism organism2){

        if (organism1 == null && organism2 == null){
            return true;
        }
        else if (organism1 == null || organism2 == null){
            return false;
        }
        else {
            // check tax ids first
            if (!OrganismTaxIdComparator.areEquals(organism1, organism2)){
                return false;
            }

            // then compares the cell type
            CvTerm cellType1 = organism1.getCellType();
            CvTerm cellType2 = organism2.getCellType();

            if (!DefaultCvTermComparator.areEquals(cellType1, cellType2)){
                return false;
            }

            // then compares the tissues
            CvTerm tissue1 = organism1.getTissue();
            CvTerm tissue2 = organism2.getTissue();

            if (!DefaultCvTermComparator.areEquals(tissue1, tissue2)){
                return false;
            }

            // then compares the compartments
            CvTerm compartment1 = organism1.getCompartment();
            CvTerm compartment2 = organism2.getCompartment();

            return DefaultCvTermComparator.areEquals(compartment1, compartment2);
        }
    }
}
