package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Protein;

import java.util.Comparator;

/**
 * Abstract proteins comparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public abstract class AbstractProteinComparator implements Comparator<Protein>{

    protected AbstractPolymerComparator interactorComparator;

    /**
     * It needs a AbstractPolymerComparator to compares interactor properties and it will creates a new OrganismTaxIdComparator
     * @param interactorComparator : comparator for interactor properties. It is required
     */
    public AbstractProteinComparator(AbstractPolymerComparator interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare proteins. It cannot be null");
        }
        this.interactorComparator = interactorComparator;

    }

    /**
     *
     * @param protein1
     * @param protein2
     * @return
     */
    public abstract int compare(Protein protein1, Protein protein2);

    public AbstractPolymerComparator getInteractorComparator() {
        return interactorComparator;
    }
}
