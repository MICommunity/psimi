package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExternalIdentifier;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.comparator.alias.UnambiguousAliasComparator;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;

/**
 * Unambiguous Interactor base comparator.
 * It will first compare the interactor types using UnambiguousCvTermComparator. If both types are equal,
 * it will compare organisms using OrganismTaxIdComparator. If both organisms are equal, it will use a UnambiguousInteractorBaseComparator to compare basic Interactor properties.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExactInteractorBaseComparator extends ExactInteractorBaseComparator{

    private static UnambiguousExactInteractorBaseComparator unambiguousExactInteractorComparator;

    /**
     * Creates a new UnambiguousExactInteractorBaseComparator.
     * It will use a UnambiguousInteractorBaseComparator to compare basic interactor properties, a OrganismTaxIdComparator to compare
     * organisms and a UnambiguousCvTermComparator to compare checksum types and interactor types
     */
    public UnambiguousExactInteractorBaseComparator() {
        super(new UnambiguousExternalIdentifierComparator(), new UnambiguousAliasComparator(), new OrganismTaxIdComparator(), new UnambiguousCvTermComparator());
    }

    @Override
    public UnambiguousExternalIdentifierComparator getIdentifierComparator() {
        return (UnambiguousExternalIdentifierComparator) this.identifierComparator;
    }

    @Override
    public UnambiguousAliasComparator getAliasComparator() {
        return (UnambiguousAliasComparator) this.aliasComparator;
    }

    @Override
    public UnambiguousCvTermComparator getTypeComparator() {
        return (UnambiguousCvTermComparator) this.typeComparator;
    }

    @Override
    /**
     * It will first compare the interactor types using UnambiguousCvTermComparator. If both types are equal,
     * it will compare organisms using OrganismTaxIdComparator. If both organisms are equal, it will use a UnambiguousInteractorBaseComparator to compare basic Interactor properties.
     *
     */
    public int compare(Interactor interactor1, Interactor interactor2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (interactor1 == null && interactor2 == null){
            return EQUAL;
        }
        else if (interactor1 == null){
            return AFTER;
        }
        else if (interactor2 == null){
            return BEFORE;
        }
        else{
            // compares first interactor types
            CvTerm type1 = interactor1.getType();
            CvTerm type2 = interactor2.getType();

            int comp=EQUAL;
            if (type1 != null || type2 != null){
                comp = typeComparator.compare(type1, type2);
            }

            if (comp != 0){
                return comp;
            }

            // then compares organism
            Organism organism1 = interactor1.getOrganism();
            Organism organism2 = interactor2.getOrganism();

            if (organism1 != null || organism2 != null){
                comp = organismComparator.compare(organism1, organism2);
            }

            if (comp != 0){
                return comp;
            }

            // then compare unique identifier
            ExternalIdentifier uniqueId1 = interactor1.getUniqueIdentifier();
            ExternalIdentifier uniqueId2 = interactor2.getUniqueIdentifier();

            if (uniqueId1 != null || uniqueId2 != null){
                return identifierComparator.compare(uniqueId1, uniqueId2);
            }

            // then compares the short name (case sensitive)
            String shortName1 = interactor1.getShortName();
            String shortName2 = interactor2.getShortName();
            return shortName1.compareTo(shortName2);
        }
    }

    /**
     * Use UnambiguousExacttInteractorBaseComparator to know if two interactors are equals.
     * @param interactor1
     * @param interactor2
     * @return true if the two interactors are equal
     */
    public static boolean areEquals(Interactor interactor1, Interactor interactor2){
        if (unambiguousExactInteractorComparator == null){
            unambiguousExactInteractorComparator = new UnambiguousExactInteractorBaseComparator();
        }

        return unambiguousExactInteractorComparator.compare(interactor1, interactor2) == 0;
    }

    /**
     *
     * @param interactor
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Interactor interactor){
        if (unambiguousExactInteractorComparator == null){
            unambiguousExactInteractorComparator = new UnambiguousExactInteractorBaseComparator();
        }

        if (interactor == null){
            return 0;
        }

        int hashcode = 31;
        hashcode = 31*hashcode + unambiguousExactInteractorComparator.getTypeComparator().hashCode(interactor.getType());
        hashcode = 31*hashcode + unambiguousExactInteractorComparator.getOrganismComparator().hashCode(interactor.getOrganism());

        ExternalIdentifier uniqueId = interactor.getUniqueIdentifier();
        if (uniqueId != null){
            hashcode = 31*hashcode + unambiguousExactInteractorComparator.getIdentifierComparator().hashCode(uniqueId);
        }
        else {
            hashcode = 31*hashcode + interactor.getShortName().hashCode();
        }

        return hashcode;
    }
}
