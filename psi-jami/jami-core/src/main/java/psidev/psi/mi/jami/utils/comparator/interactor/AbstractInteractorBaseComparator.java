package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.alias.AliasComparator;

import java.util.Comparator;

/**
 * Abstract interactor comparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public abstract class AbstractInteractorBaseComparator implements Comparator<Interactor> {

    protected Comparator<Xref> identifierComparator;
    protected AliasComparator aliasComparator;

    /**
     * @param identifierComparator : the identifier comparator. It is required
     * @param aliasComparator : the comparator for aliases. it is required
     */
    public AbstractInteractorBaseComparator(Comparator<Xref> identifierComparator, AliasComparator aliasComparator){

        if (identifierComparator == null){
            throw new IllegalArgumentException("The external identifier comparator is required to compares identifiers. It cannot be null");
        }
        this.identifierComparator = identifierComparator;
        if (aliasComparator == null){
            throw new IllegalArgumentException("The alias comparator is required to compares aliases. It cannot be null");
        }
        this.aliasComparator = aliasComparator;
    }

    public Comparator<Xref> getIdentifierComparator() {
        return identifierComparator;
    }

    public AliasComparator getAliasComparator() {
        return aliasComparator;
    }

    /**
     * Basic interactor comparator.
     * @param interactor1
     * @param interactor2
     * @return
     */
    public abstract int compare(Interactor interactor1, Interactor interactor2);
}
