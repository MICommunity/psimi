package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.Interactor;

import java.util.Comparator;

/**
 * Default bioactive entity comparator.
 * It will first use DefaultInteractorBaseComparator to compare the basic interactor properties.
 * If the basic interactor properties are the same, It will look first for CHEBI identifier if both are set. If the CHEBI identifiers are not both set, it will look at the
 * standard inchi key. If at least one standard inchi key is not set or both inchi key are identical, it will look at the smile. If at least one smile is not set or both smiles are identical, it
 * will look at the standard Inchi.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/01/13</pre>
 */

public class DefaultBioactiveEntityComparator extends AbstractBioactiveEntityComparator {

    private static DefaultBioactiveEntityComparator defaultBioactiveEntityComparator;

    /**
     * Creates a new DefaultBioactiveComparator. It will use a DefaultInteractorBaseComparator.
     */
    public DefaultBioactiveEntityComparator() {
        super(new DefaultInteractorBaseComparator());
    }

    public DefaultBioactiveEntityComparator(Comparator<Interactor> interactorBaseComparator) {
        super(interactorBaseComparator != null ? interactorBaseComparator : new DefaultInteractorBaseComparator());
    }

    @Override
    /**
     * It will first use DefaultInteractorBaseComparator to compare the basic interactor properties.
     * If the basic interactor properties are the same, It will look first for CHEBI identifier if both are set. If the CHEBI identifiers are not both set, it will look at the
     * standard inchi key. If at least one standard inchi key is not set or both inchi key are identical, it will look at the smile. If at least one smile is not set or both smiles are identical, it
     * will look at the standard Inchi.
     */
    public int compare(BioactiveEntity bioactiveEntity1, BioactiveEntity bioactiveEntity2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (bioactiveEntity1 == null && bioactiveEntity2 == null){
            return EQUAL;
        }
        else if (bioactiveEntity1 == null){
            return AFTER;
        }
        else if (bioactiveEntity2 == null){
            return BEFORE;
        }
        else {

            // First compares the basic interactor properties
            int comp = interactorComparator.compare(bioactiveEntity1, bioactiveEntity2);
            if (comp != 0){
                return comp;
            }

            // then compares CHEBI identifiers
            String chebi1 = bioactiveEntity1.getChebi();
            String chebi2 = bioactiveEntity2.getChebi();

            if (chebi1 != null && chebi2 != null){
                return chebi1.compareTo(chebi2);
            }

            // compares standard InChi key
            String inchikey1 = bioactiveEntity1.getStandardInchiKey();
            String inchiKey2 = bioactiveEntity2.getStandardInchiKey();
            if (inchikey1 != null && inchiKey2 != null){
                comp = inchikey1.compareTo(inchiKey2);
                if (comp != 0){
                    return comp;
                }
            }

            // compares smile if at least one inchikey is not set
            String smile1 = bioactiveEntity1.getSmile();
            String smile2 = bioactiveEntity2.getSmile();

            if (smile1 != null && smile2 != null){
                comp = smile1.compareTo(smile2);
                if (comp != 0){
                    return comp;
                }
            }

            // compares standard inchi if at least one inchi key is not set
            String inchi1 = bioactiveEntity1.getStandardInchi();
            String inchi2 = bioactiveEntity2.getStandardInchi();

            if (inchi1 != null && inchi2 != null){
                comp = inchi1.compareTo(inchi2);
            }

            return comp;
        }
    }

    /**
     * Use DefaultBioactiveEntityComparator to know if two bioactive entities are equals.
     * @param entity1
     * @param entity2
     * @return true if the two bioactive entities are equal
     */
    public static boolean areEquals(BioactiveEntity entity1, BioactiveEntity entity2){
        if (defaultBioactiveEntityComparator == null){
            defaultBioactiveEntityComparator = new DefaultBioactiveEntityComparator();
        }

        return defaultBioactiveEntityComparator.compare(entity1, entity2) == 0;
    }
}
