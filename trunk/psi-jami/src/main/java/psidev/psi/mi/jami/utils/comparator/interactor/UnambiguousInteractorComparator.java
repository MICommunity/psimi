package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.comparator.alias.UnambiguousAliasComparator;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;

/**
 * Unambiguous interactor comparator
 * It will look first for unique identifier if both are set using UnambiguousIdentifierComparator. If the unique identifiers are not both set or equals, it will look for at least one
 * same alternative identifier. If no alternative identifiers are equal, it will look at the short names (case sensitive).
 * If the shortnames do not match, it will look for at least one common alias using UnambiguousAliasComparator.
 *
 * This comparator will ignore all the other properties of an interactor.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class UnambiguousInteractorComparator extends InteractorComparator{
    private static UnambiguousInteractorComparator unambiguousInteractorComparator;

    public UnambiguousInteractorComparator() {
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
     * It will look first for unique identifier if both are set using UnambiguousIdentifierComparator. If the unique identifiers are not both set or equals, it will look for at least one
     * same alternative identifier. If no alternative identifiers are equal, it will look at the short names (case sensitive).
     * If the shortnames do not match, it will look for at least one common alias using UnambiguousAliasComparator.
     *
     * This comparator will ignore all the other properties of an interactor.
     */
    public int compare(Interactor interactor1, Interactor interactor2) {
        return super.compare(interactor1, interactor2);
    }

    /**
     * Use DefaultConfidenceComparator to know if two confidences are equals.
     * @param interactor1
     * @param interactor2
     * @return true if the two confidences are equal
     */
    public static boolean areEquals(Interactor interactor1, Interactor interactor2){
        if (unambiguousInteractorComparator == null){
            unambiguousInteractorComparator = new UnambiguousInteractorComparator();
        }

        return unambiguousInteractorComparator.compare(interactor1, interactor2) == 0;
    }
}
