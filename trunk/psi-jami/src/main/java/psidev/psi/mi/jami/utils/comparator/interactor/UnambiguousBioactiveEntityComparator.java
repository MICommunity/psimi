package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * Unambiguous bioactive entity comparator.
 * It will look first for CHEBI identifier if both are set. If the CHEBI identifiers are not both set, it will look at the
 * smiles. If at least one smile is not set, it will look at the standard Inchi key. If at least one standard Inchi key is not set, it
 * will look at the standard Inchi.
 * If the properties of a bioactive entity were not enough to compare the bioactive entities, it will use UnambiguousInteractorComparator to compare the interactor properties
 * This comparator will ignore all the other properties of an interactor.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class UnambiguousBioactiveEntityComparator extends BioactiveEntityComparator {

    private static UnambiguousBioactiveEntityComparator unambiguousBioactiveEntityComparator;

    /**
     * Creates a new UnambiguousBioactiveEntityComparator. It will use an UnambiguousInteractorComparator
     */
    public UnambiguousBioactiveEntityComparator() {
        super(new UnambiguousInteractorComparator());
    }

    @Override
    /**
     * It will look first for CHEBI identifier if both are set. If the CHEBI identifiers are not both set, it will look at the
     * smiles. If at least one smile is not set, it will look at the standard Inchi key. If at least one standard Inchi key is not set, it
     * will look at the standard Inchi.
     * If the properties of a bioactive entity were not enough to compare the bioactive entities, it will use UnambiguousInteractorComparator to compare the interactor properties
     * This comparator will ignore all the other properties of an interactor.
     */
    public int compare(BioactiveEntity bioactiveEntity1, BioactiveEntity bioactiveEntity2) {
        return super.compare(bioactiveEntity1, bioactiveEntity2);
    }

    @Override
    public UnambiguousInteractorComparator getInteractorComparator() {
        return (UnambiguousInteractorComparator) this.interactorComparator;
    }

    /**
     * Use UnambiguousBioactiveEntityComparator to know if two bioactive entities are equals.
     * @param entity1
     * @param entity2
     * @return true if the two confidences are equal
     */
    public static boolean areEquals(BioactiveEntity entity1, BioactiveEntity entity2){
        if (unambiguousBioactiveEntityComparator == null){
            unambiguousBioactiveEntityComparator = new UnambiguousBioactiveEntityComparator();
        }

        return unambiguousBioactiveEntityComparator.compare(entity1, entity2) == 0;
    }
}
