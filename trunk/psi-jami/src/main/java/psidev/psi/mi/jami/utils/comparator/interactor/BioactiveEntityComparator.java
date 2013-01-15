package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.Interactor;

import java.util.Comparator;

/**
 * Basic bioactive entity comparator.
 * It will look first for CHEBI identifier if both are set. If the CHEBI identifiers are not both set, it will look at the
 * smiles. If at least one smile is not set, it will look at the standard Inchi key. If at least one standard Inchi key is not set, it
 * will look at the standard Inchi.
 * If the properties of a bioactive entity were not enough to compare the bioactive entities, it will use Comparator<Interactor> to compare the interactor properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/01/13</pre>
 */

public class BioactiveEntityComparator implements Comparator<BioactiveEntity> {

    protected Comparator<Interactor> interactorComparator;

    /**
     * Creates a bew BioactiveEntityComparator. It needs a Comparator<Interactor> to compares interactor properties
     * @param interactorComparator : comparator for interactor properties. It is required
     */
    public BioactiveEntityComparator(Comparator<Interactor> interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare bioactive entities. It cannot be null");
        }
        this.interactorComparator = interactorComparator;
    }

    /**
     * It will look first for CHEBI identifier if both are set. If the CHEBI identifiers are not both set, it will look at the
     * smiles. If at least one smile is not set, it will look at the standard Inchi key. If at least one standard Inchi key is not set, it
     * will look at the standard Inchi.
     * If the properties of a bioactive entity were not enough to compare the bioactive entities, it will use Comparator<Interactor> to compare the interactor properties
     * @param bioactiveEntity1
     * @param bioactiveEntity2
     * @return
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
            // first compares CHEBI identifiers
            String chebi1 = bioactiveEntity1.getChebi();
            String chebi2 = bioactiveEntity2.getChebi();

            if (chebi1 != null && chebi2 != null){
                return chebi1.compareTo(chebi2);
            }

            // compares smile if at least one chebi identifier is not set
            String smile1 = bioactiveEntity1.getSmile();
            String smile2 = bioactiveEntity2.getSmile();

            if (smile1 != null && smile2 != null){
                return smile1.compareTo(smile2);
            }

            // compares standard InChi key if at least one smile is not set
            String inchikey1 = bioactiveEntity1.getStandardInchiKey();
            String inchiKey2 = bioactiveEntity2.getStandardInchiKey();

            if (inchikey1 != null && inchiKey2 != null){
                return inchikey1.compareTo(inchiKey2);
            }

            // compares standard inchi if at least one inchi key is not set
            String inchi1 = bioactiveEntity1.getStandardInchi();
            String inchi2 = bioactiveEntity2.getStandardInchi();

            if (inchi1 != null && inchi2 != null){
                return inchi1.compareTo(inchi2);
            }

            // compares the interactor properties if the bioactive properties are not enough
            return interactorComparator.compare(bioactiveEntity1, bioactiveEntity2);
        }
    }

    public Comparator<Interactor> getInteractorComparator() {
        return interactorComparator;
    }
}
