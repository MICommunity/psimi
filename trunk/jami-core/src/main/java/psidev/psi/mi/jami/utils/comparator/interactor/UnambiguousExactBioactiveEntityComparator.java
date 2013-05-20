package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * unambiguous Exact bioactive entity comparator.
 * It will first use UnambiguousExactInteractorBaseComparator to compare the basic interactor properties.
 * If the basic interactor properties are the same, It will look first for CHEBI identifier if both are set. If the CHEBI identifiers are not both set, it will look at the
 * smiles. If at least one smile is not set, it will look at the standard Inchi key. If at least one standard Inchi key is not set, it
 * will look at the standard Inchi.
 * This comparator will ignore all the other properties of an interactor.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExactBioactiveEntityComparator extends BioactiveEntityComparator {
    private static UnambiguousExactBioactiveEntityComparator unambiguousExactBioactiveEntityComparator;

    /**
     * Creates a new UnambiguousExactBioactiveEntityComparator. It will use a UnambiguousExactInteractorBaseComparator.
     */
    public UnambiguousExactBioactiveEntityComparator() {
        super(new UnambiguousExactInteractorBaseComparator());
    }

    @Override
    /**
     * It will first use UnambiguousExactInteractorBaseComparator to compare the basic interactor properties.
     * If the basic interactor properties are the same, It will look first for CHEBI identifier if both are set. If the CHEBI identifiers are not both set, it will look at the
     * smiles. If at least one smile is not set, it will look at the standard Inchi key. If at least one standard Inchi key is not set, it
     * will look at the standard Inchi.
     * This comparator will ignore all the other properties of an interactor.
     */
    public int compare(BioactiveEntity bioactiveEntity1, BioactiveEntity bioactiveEntity2) {
        return super.compare(bioactiveEntity1, bioactiveEntity2);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public UnambiguousExactInteractorBaseComparator getInteractorComparator() {
        return (UnambiguousExactInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use UnambiguousExactBioactiveEntityComparator to know if two bioactive entities are equals.
     * @param entity1
     * @param entity2
     * @return true if the two bioactive entities are equal
     */
    public static boolean areEquals(BioactiveEntity entity1, BioactiveEntity entity2){
        if (unambiguousExactBioactiveEntityComparator == null){
            unambiguousExactBioactiveEntityComparator = new UnambiguousExactBioactiveEntityComparator();
        }

        return unambiguousExactBioactiveEntityComparator.compare(entity1, entity2) == 0;
    }
}
