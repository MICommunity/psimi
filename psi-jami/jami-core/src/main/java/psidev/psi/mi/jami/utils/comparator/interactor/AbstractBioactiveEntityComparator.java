package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.Interactor;

import java.util.Comparator;

/**
 * Abstract bioactive entity comparator.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/01/13</pre>
 */

public abstract class AbstractBioactiveEntityComparator implements Comparator<BioactiveEntity> {

    protected Comparator<Interactor> interactorComparator;

    /**
     * Creates a bew AbstractBioactiveEntityComparator. It needs a AbstractInteractorBaseComparator to compares interactor properties
     * @param interactorComparator : comparator for interactor properties. It is required
     */
    public AbstractBioactiveEntityComparator(Comparator<Interactor> interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare bioactive entities. It cannot be null");
        }
        this.interactorComparator = interactorComparator;
    }

    /**
     * @param bioactiveEntity1
     * @param bioactiveEntity2
     * @return
     */
    public abstract int compare(BioactiveEntity bioactiveEntity1, BioactiveEntity bioactiveEntity2);

    public Comparator<Interactor> getInteractorComparator() {
        return interactorComparator;
    }
}
