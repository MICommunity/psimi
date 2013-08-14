package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

/**
 * Default Interactor base comparator.
 * It will first compare the interactor types using DefaultCvTermComparator. If both types are equal,
 * it will compare organisms using OrganismTaxIdComparator. If both organisms are equal or not set, it will use a DefaultInteractorBaseComparator to compare basic Interactor properties.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultExactInteractorBaseComparator {

    /**
     * Use DefaulExacttInteractorBaseComparator to know if two interactors are equals.
     * @param interactor1
     * @param interactor2
     * @return true if the two interactors are equal
     */
    public static boolean areEquals(Interactor interactor1, Interactor interactor2){
        if (interactor1 == null && interactor2 == null){
            return true;
        }
        else if (interactor1 == null || interactor2 == null){
            return false;
        }
        else{
            // compares first interactor types
            CvTerm type1 = interactor1.getInteractorType();
            CvTerm type2 = interactor2.getInteractorType();

            if (!DefaultCvTermComparator.areEquals(type1, type2)){
                return false;
            }

            // then compares organism if both are set
            Organism organism1 = interactor1.getOrganism();
            Organism organism2 = interactor2.getOrganism();

            if (organism1 != null && organism2 != null && !OrganismTaxIdComparator.areEquals(organism1, organism2)){
                return false;
            }

            return DefaultInteractorBaseComparator.areEquals(interactor1, interactor2);
        }
    }
}
