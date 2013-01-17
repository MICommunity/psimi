package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * default Exact bioactive entity comparator.
 * It will look first for CHEBI identifier if both are set. If the CHEBI identifiers are not both set, it will look at the
 * smiles. If at least one smile is not set, it will look at the standard Inchi key. If at least one standard Inchi key is not set, it
 * will look at the standard Inchi.
 * If the properties of a bioactive entity were not enough to compare the bioactive entities, it will use DefaultExactInteractorBaseComparator to compare the interactor properties
 * This comparator will ignore all the other properties of an interactor.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultExactBioactiveEntityComparator extends BioactiveEntityComparator {

    private static DefaultExactBioactiveEntityComparator defaultExactBioactiveEntityComparator;

    /**
     * Creates a new DefaultExactBioactiveEntityComparator. It will use a DefaultExactInteractorBaseComparator.
     */
    public DefaultExactBioactiveEntityComparator() {
        super(new DefaultExactInteractorBaseComparator());
    }

    @Override
    /**
     * It will look first for CHEBI identifier if both are set. If the CHEBI identifiers are not both set, it will look at the
     * smiles. If at least one smile is not set, it will look at the standard Inchi key. If at least one standard Inchi key is not set, it
     * will look at the standard Inchi.
     * If the properties of a bioactive entity were not enough to compare the bioactive entities, it will use DefaultExactInteractorBaseComparator to compare the interactor properties
     *
     */
    public int compare(BioactiveEntity bioactiveEntity1, BioactiveEntity bioactiveEntity2) {
        return super.compare(bioactiveEntity1, bioactiveEntity2);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public DefaultExactInteractorBaseComparator getInteractorComparator() {
        return (DefaultExactInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use DefaultBioactiveEntityComparator to know if two bioactive entities are equals.
     * @param entity1
     * @param entity2
     * @return true if the two bioactive entities are equal
     */
    public static boolean areEquals(BioactiveEntity entity1, BioactiveEntity entity2){
        if (defaultExactBioactiveEntityComparator == null){
            defaultExactBioactiveEntityComparator = new DefaultExactBioactiveEntityComparator();
        }

        return defaultExactBioactiveEntityComparator.compare(entity1, entity2) == 0;
    }
}
