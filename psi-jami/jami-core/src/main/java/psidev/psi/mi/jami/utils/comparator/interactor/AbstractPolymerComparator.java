package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

import java.util.Comparator;

/**
 * Abstract polymer comparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public abstract class AbstractPolymerComparator implements Comparator<Polymer> {

    protected Comparator<Interactor> interactorComparator;
    protected OrganismTaxIdComparator organismComparator;

    /**
     *  It needs a Comparator<Interactor> to compares interactor properties and it will creates a new OrganismTaxIdComparator
     * @param interactorComparator : comparator for interactor properties. It is required
     */
    public AbstractPolymerComparator(Comparator<Interactor> interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare polymers. It cannot be null");
        }
        this.interactorComparator = interactorComparator;
        this.organismComparator = new OrganismTaxIdComparator();

    }

    /**
     * Creates a new AbstractPolymerComparator. It needs a Comparator<Interactor> to compares interactor properties and a OrganismComparator
     * to compare the sequence and organism. If the organism comparator is null,it will creates a new OrganismTaxIdComparator
     * @param interactorComparator : comparator for interactor properties. It is required
     * @param organismComparator : comparator for organism
     */
    public AbstractPolymerComparator(Comparator<Interactor> interactorComparator, OrganismTaxIdComparator organismComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare polymers. It cannot be null");
        }
        this.interactorComparator = interactorComparator;
        if (organismComparator == null){
            this.organismComparator = new OrganismTaxIdComparator();
        }
        else {
            this.organismComparator = organismComparator;
        }
    }

    /**
     *
     * @param polymer1
     * @param polymer2
     * @return
     */
    public abstract int compare(Polymer polymer1, Polymer polymer2);

    public Comparator<Interactor> getInteractorComparator() {
        return interactorComparator;
    }
}
