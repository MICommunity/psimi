package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

import java.util.Comparator;

/**
 * Abstract Exact Interactor base comparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public abstract class AbstractExactInteractorBaseComparator implements Comparator<Interactor> {

    protected OrganismTaxIdComparator organismComparator;
    protected AbstractCvTermComparator typeComparator;
    protected AbstractInteractorBaseComparator interactorBaseComparator;

    /**
     * @param interactorBaseComparator : the basic comparator for interactor properties (look only at identifiers, shortname, fullname and aliases)
     * @param organismComparator : the comparator for organisms. if null will be OrganismTaxIdComparator
     * @param typeComparator : the interactor type comparator. It is required
     */
    public AbstractExactInteractorBaseComparator(AbstractInteractorBaseComparator interactorBaseComparator,
                                                 OrganismTaxIdComparator organismComparator,
                                                 AbstractCvTermComparator typeComparator){

        if (interactorBaseComparator == null){
            throw new IllegalArgumentException("The interactor base comparator is required to compare basic interactor properties such as identifiers and names. It cannot be null");
        }
        this.interactorBaseComparator = interactorBaseComparator;
        if (organismComparator == null){
            this.organismComparator = new OrganismTaxIdComparator();
        }
        else {
            this.organismComparator = organismComparator;
        }
        if (typeComparator == null){
            throw new IllegalArgumentException("The interactor type comparator is required to compares interactor types. It cannot be null");
        }
        this.typeComparator = typeComparator;
    }

    public OrganismTaxIdComparator getOrganismComparator() {
        return organismComparator;
    }

    public AbstractCvTermComparator getTypeComparator() {
        return typeComparator;
    }

    public AbstractInteractorBaseComparator getInteractorBaseComparator() {
        return interactorBaseComparator;
    }

    /**
     * @param interactor1
     * @param interactor2
     * @return
     */
    public abstract int compare(Interactor interactor1, Interactor interactor2);
}
