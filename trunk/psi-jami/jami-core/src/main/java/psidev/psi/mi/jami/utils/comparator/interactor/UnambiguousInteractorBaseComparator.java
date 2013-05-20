package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.comparator.alias.UnambiguousAliasComparator;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;

/**
 * Unambiguous interactor comparator
 * It will only compare the short names (case sensitive).
 *
 * This comparator will ignore all the other properties of an interactor.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class UnambiguousInteractorBaseComparator extends InteractorBaseComparator {
    private static UnambiguousInteractorBaseComparator unambiguousInteractorComparator;

    public UnambiguousInteractorBaseComparator() {
        super(new UnambiguousExternalIdentifierComparator(), new UnambiguousAliasComparator());
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
    /**
     * It will only compare the short names (case sensitive).
     *
     * This comparator will ignore all the other properties of an interactor.
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
        else {

            // compares the short name (case sensitive)
            String shortName1 = interactor1.getShortName();
            String shortName2 = interactor2.getShortName();
            return shortName1.compareTo(shortName2);
        }
    }

    /**
     * Use DefaultConfidenceComparator to know if two confidences are equals.
     * @param interactor1
     * @param interactor2
     * @return true if the two confidences are equal
     */
    public static boolean areEquals(Interactor interactor1, Interactor interactor2){
        if (unambiguousInteractorComparator == null){
            unambiguousInteractorComparator = new UnambiguousInteractorBaseComparator();
        }

        return unambiguousInteractorComparator.compare(interactor1, interactor2) == 0;
    }

    /**
     *
     * @param interactor
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Interactor interactor){
        if (unambiguousInteractorComparator == null){
            unambiguousInteractorComparator = new UnambiguousInteractorBaseComparator();
        }

        if (interactor == null){
            return 0;
        }

        int hashcode = 31;
        hashcode = 31*hashcode + interactor.getShortName().hashCode();

        return hashcode;
    }
}
