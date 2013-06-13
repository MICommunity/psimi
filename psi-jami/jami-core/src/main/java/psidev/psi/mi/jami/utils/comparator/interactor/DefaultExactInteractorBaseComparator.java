package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

import java.util.Comparator;

/**
 * Default Interactor base comparator.
 * It will first compare the interactor types using DefaultCvTermComparator. If both types are equal,
 * it will compare organisms using OrganismTaxIdComparator. If both organisms are equal or not set, it will use a DefaultInteractorBaseComparator to compare basic Interactor properties.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultExactInteractorBaseComparator implements Comparator<Interactor> {

    private static DefaultExactInteractorBaseComparator defaultExactInteractorComparator;
    private OrganismTaxIdComparator organismComparator;
    private DefaultCvTermComparator typeComparator;
    private DefaultInteractorBaseComparator interactorBaseComparator;

    /**
     * Creates a new DefaultExactInteractorBaseComparator.
     * It will use a DefaultInteractorBaseComparator to compare basic interactor properties, a OrganismTaxIdComparator to compare
     * organisms and a DefaultCvTermComparator to compare checksum types and interactor types
     */
    public DefaultExactInteractorBaseComparator() {
        this.interactorBaseComparator = new DefaultInteractorBaseComparator();
        this.organismComparator = new OrganismTaxIdComparator();
        this.typeComparator = new DefaultCvTermComparator();
    }

    public DefaultInteractorBaseComparator getInteractorBaseComparator() {
        return this.interactorBaseComparator;
    }

    public DefaultCvTermComparator getTypeComparator() {
        return this.typeComparator;
    }

    public OrganismTaxIdComparator getOrganismComparator() {
        return organismComparator;
    }

    /**
     * It will first compare the interactor types using DefaultCvTermComparator. If both types are equal,
     * it will compare organisms using OrganismTaxIdComparator. If both organisms are equal or not set, it will use a DefaultInteractorBaseComparator to compare basic Interactor properties.
     */
    public int compare(Interactor interactor1, Interactor interactor2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (interactor1 == null && interactor2 == null){
            return EQUAL;
        }
        else if (interactor1 == null){
            return AFTER;
        }
        else if (interactor2 == null){
            return BEFORE;
        }
        else{
            // compares first interactor types
            CvTerm type1 = interactor1.getInteractorType();
            CvTerm type2 = interactor2.getInteractorType();

            int comp = typeComparator.compare(type1, type2);

            if (comp != 0){
                return comp;
            }

            // then compares organism if both are set
            Organism organism1 = interactor1.getOrganism();
            Organism organism2 = interactor2.getOrganism();

            if (organism1 != null && organism2 != null){
                comp = organismComparator.compare(organism1, organism2);
                if (comp != 0){
                    return comp;
                }
            }

            return interactorBaseComparator.compare(interactor1, interactor2);
        }
    }

    /**
     * Use DefaulExacttInteractorBaseComparator to know if two interactors are equals.
     * @param interactor1
     * @param interactor2
     * @return true if the two interactors are equal
     */
    public static boolean areEquals(Interactor interactor1, Interactor interactor2){
        if (defaultExactInteractorComparator == null){
            defaultExactInteractorComparator = new DefaultExactInteractorBaseComparator();
        }

        return defaultExactInteractorComparator.compare(interactor1, interactor2) == 0;
    }
}
