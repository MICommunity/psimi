package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;

/**
 * Default interactor comparator.
 * It will look first for unique identifier if both are set using DefaultIdentifierComparator. If the unique identifiers are not both set or equals, it will look for at least one
 * same alternative identifier. If no alternative identifiers are equal, it will look at the short names (case sensitive).
 * If the shortnames do not match, it will look for at least one common alias using DefaultAliasComparator.
 *
 * This comparator will ignore all the other properties of an interactor.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class DefaultInteractorComparator extends InteractorComparator {

    private static DefaultInteractorComparator defaultInteractorComparator;

    public DefaultInteractorComparator() {
        super(new DefaultExternalIdentifierComparator(), new DefaultAliasComparator());
    }

    @Override
    public DefaultExternalIdentifierComparator getIdentifierComparator() {
        return (DefaultExternalIdentifierComparator) this.identifierComparator;
    }

    @Override
    public DefaultAliasComparator getAliasComparator() {
        return (DefaultAliasComparator) this.aliasComparator;
    }

    @Override
    /**
     *  It will look first for unique identifier if both are set using DefaultIdentifierComparator. If the unique identifiers are not both set or equals, it will look for at least one
     * same alternative identifier. If no alternative identifiers are equal, it will look at the short names (case sensitive).
     * If the shortnames do not match, it will look for at least one common alias using DefaultAliasComparator.
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
        if (defaultInteractorComparator == null){
            defaultInteractorComparator = new DefaultInteractorComparator();
        }

        return defaultInteractorComparator.compare(interactor1, interactor2) == 0;
    }
}
