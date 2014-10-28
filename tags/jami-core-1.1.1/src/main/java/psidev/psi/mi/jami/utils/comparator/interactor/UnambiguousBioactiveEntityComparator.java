package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * Unambiguous bioactive entity comparator.
 * It will first use UnambiguousInteractorBaseComparator to compare the basic interactor properties.
 * If the basic interactor properties are the same, It will look first for CHEBI identifier (the interactor with a non null CHEBI identifier will always come first). If the CHEBI identifiers are not set, it will look at the
 * standard inchi key (the interactor with a non null standard inchi key will always come first). If the standard inchi keys are identical, it will look at the smile (the interactor with a non null smile will always come first). If the smiles are identical, it
 * will look at the standard Inchi (the interactor with a non null standard inchi will always come first).
 * This comparator will ignore all the other properties of an interactor.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class UnambiguousBioactiveEntityComparator extends BioactiveEntityComparator{

    private static UnambiguousBioactiveEntityComparator unambiguousBioactiveEntityComparator;

    /**
     * Creates a new UnambiguousBioactiveEntityComparator. It will use an UnambiguousInteractorBaseComparator
     */
    public UnambiguousBioactiveEntityComparator() {
        super(new UnambiguousInteractorBaseComparator());
    }

    public UnambiguousInteractorBaseComparator getInteractorComparator() {
        return (UnambiguousInteractorBaseComparator)super.getInteractorComparator();
    }

    /**
     * Use UnambiguousBioactiveEntityComparator to know if two bioactive entities are equals.
     * @param entity1
     * @param entity2
     * @return true if the two bioactive entities are equal
     */
    public static boolean areEquals(BioactiveEntity entity1, BioactiveEntity entity2){
        if (unambiguousBioactiveEntityComparator == null){
            unambiguousBioactiveEntityComparator = new UnambiguousBioactiveEntityComparator();
        }

        return unambiguousBioactiveEntityComparator.compare(entity1, entity2) == 0;
    }
}
