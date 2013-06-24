package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * default Exact bioactive entity comparator.
 * It will first use DefaultExactInteractorBaseComparator to compare the basic interactor properties.
 * If the basic interactor properties are the same, It will look first for CHEBI identifier if both are set. If the CHEBI identifiers are not both set, it will look at the
 * standard inchi key. If at least one standard inchi key is not set or both inchi key are identical, it will look at the smile. If at least one smile is not set or both smiles are identical, it
 * will look at the standard Inchi.
 *
 * This comparator will ignore all the other properties of an interactor.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultExactBioactiveEntityComparator {

    /**
     * Use DefaultBioactiveEntityComparator to know if two bioactive entities are equals.
     * @param bioactiveEntity1
     * @param bioactiveEntity2
     * @return true if the two bioactive entities are equal
     */
    public static boolean areEquals(BioactiveEntity bioactiveEntity1, BioactiveEntity bioactiveEntity2){
        if (bioactiveEntity1 == null && bioactiveEntity2 == null){
            return true;
        }
        else if (bioactiveEntity1 == null || bioactiveEntity2 == null){
            return false;
        }
        else {

            // First compares the basic interactor properties
            if (!DefaultExactInteractorBaseComparator.areEquals(bioactiveEntity1, bioactiveEntity2)){
                return false;
            }

            // then compares CHEBI identifiers
            String chebi1 = bioactiveEntity1.getChebi();
            String chebi2 = bioactiveEntity2.getChebi();

            if (chebi1 != null && chebi2 != null){
                return chebi1.equals(chebi2);
            }

            // compares standard InChi key
            String inchikey1 = bioactiveEntity1.getStandardInchiKey();
            String inchiKey2 = bioactiveEntity2.getStandardInchiKey();
            if (inchikey1 != null && inchiKey2 != null){
                if (!inchikey1.equals(inchiKey2)){
                    return false;
                }
            }

            // compares smile if at least one inchikey is not set
            String smile1 = bioactiveEntity1.getSmile();
            String smile2 = bioactiveEntity2.getSmile();

            if (smile1 != null && smile2 != null){
                if (!smile1.equals(smile2)){
                    return false;
                }
            }

            // compares standard inchi if at least one inchi key is not set
            String inchi1 = bioactiveEntity1.getStandardInchi();
            String inchi2 = bioactiveEntity2.getStandardInchi();

            if (inchi1 != null && inchi2 != null){
                return inchi1.equals(inchi2);
            }

            return true;
        }
    }
}
