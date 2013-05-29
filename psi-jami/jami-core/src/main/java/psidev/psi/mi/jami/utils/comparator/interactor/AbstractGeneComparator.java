package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Interactor;

import java.util.Comparator;

/**
 * Abstract genes comparator.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public abstract class AbstractGeneComparator implements Comparator<Gene> {

    protected Comparator<Interactor> interactorComparator;

    public AbstractGeneComparator(Comparator<Interactor> interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare genes. It cannot be null");
        }
        this.interactorComparator = interactorComparator;
    }

    /**
     * @param gene1
     * @param gene2
     * @return
     */
    public abstract int compare(Gene gene1, Gene gene2);

    public Comparator<Interactor> getInteractorComparator() {
        return interactorComparator;
    }
}
