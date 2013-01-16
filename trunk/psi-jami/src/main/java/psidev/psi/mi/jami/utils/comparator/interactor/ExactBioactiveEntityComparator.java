package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * Exact bioactive entity comparator.
 * It will look first for CHEBI identifier if both are set. If the CHEBI identifiers are not both set, it will look at the
 * smiles. If at least one smile is not set, it will look at the standard Inchi key. If at least one standard Inchi key is not set, it
 * will look at the standard Inchi.
 * If the properties of a bioactive entity were not enough to compare the bioactive entities, it will use ExactInteractorBaseComparator to compare the interactor properties
 * This comparator will ignore all the other properties of an interactor.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class ExactBioactiveEntityComparator extends BioactiveEntityComparator {

    /**
     * Creates a new ExactBioactiveEntityComparator which will use an ExactInteractorBaseComparator
     * @param interactorComparator
     */
    public ExactBioactiveEntityComparator(ExactInteractorBaseComparator interactorComparator) {
        super(interactorComparator);
    }

    @Override
    public ExactInteractorBaseComparator getInteractorComparator() {
        return (ExactInteractorBaseComparator) this.interactorComparator;
    }

    @Override
    /**
     * It will look first for CHEBI identifier if both are set. If the CHEBI identifiers are not both set, it will look at the
     * smiles. If at least one smile is not set, it will look at the standard Inchi key. If at least one standard Inchi key is not set, it
     * will look at the standard Inchi.
     * If the properties of a bioactive entity were not enough to compare the bioactive entities, it will use ExactInteractorBaseComparator to compare the interactor properties
     * This comparator will ignore all the other properties of an interactor.
     */
    public int compare(BioactiveEntity bioactiveEntity1, BioactiveEntity bioactiveEntity2) {
        return super.compare(bioactiveEntity1, bioactiveEntity2);
    }
}
