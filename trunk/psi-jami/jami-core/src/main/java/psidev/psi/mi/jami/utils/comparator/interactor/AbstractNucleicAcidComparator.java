package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.NucleicAcid;

import java.util.Comparator;

/**
 * Abstract nucleic acids comparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public abstract class AbstractNucleicAcidComparator implements Comparator<NucleicAcid> {

    protected AbstractPolymerComparator interactorComparator;

    /**
     * Creates a new AbstractNucleicAcidComparator. It needs a AbstractPolymerComparator to compares polymer properties and it will creates a new OrganismTaxIdComparator
     * @param interactorComparator : comparator for interactor properties. It is required
     */
    public AbstractNucleicAcidComparator(AbstractPolymerComparator interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare nucleic acids. It cannot be null");
        }
        this.interactorComparator = interactorComparator;

    }

    /**
     * @param nucleicAcid1
     * @param nucleicAcid2
     * @return
     */
    public abstract int compare(NucleicAcid nucleicAcid1, NucleicAcid nucleicAcid2);

    public AbstractPolymerComparator getInteractorComparator() {
        return this.interactorComparator;
    }
}
