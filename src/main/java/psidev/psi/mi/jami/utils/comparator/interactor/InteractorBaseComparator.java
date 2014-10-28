package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;
import psidev.psi.mi.jami.utils.comparator.alias.AliasComparator;
import psidev.psi.mi.jami.utils.comparator.alias.AliasesCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.xref.XrefsCollectionComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * nteractor comparator
 * It will only compare identifiers if one interactor does have identifiers using UnambiguousIdentifierComparator. Otherwise, it will first compare shortNames (case sensitive)
 * fullnames(case sensitive) and if the shortNames and fullnames are the same, it will compare the aliases using UnambiguousAliasComparator.
 *
 * This comparator will ignore all the other properties of an interactor.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class InteractorBaseComparator implements Comparator<Interactor> {
    private CollectionComparator<Xref> identifierCollectionComparator;
    private CollectionComparator<Alias> aliasCollectionComparator;
    private Comparator<Xref> identifierComparator;
    private Comparator<Alias> aliasComparator;

    public InteractorBaseComparator(Comparator<Xref> identifierComparator, AliasComparator aliasComparator) {
        if (identifierComparator == null){
            throw new IllegalArgumentException("the identifier comparator cannot be null");
        }
        this.identifierComparator = identifierComparator;
        if (aliasComparator == null){
            throw new IllegalArgumentException("the alias comparator cannot be null");
        }
        this.aliasComparator = aliasComparator;
        this.identifierCollectionComparator = new XrefsCollectionComparator(identifierComparator);
        this.aliasCollectionComparator = new AliasesCollectionComparator(aliasComparator);
    }

    public InteractorBaseComparator(CollectionComparator<Xref> identifierComparator, CollectionComparator<Alias> aliasComparator) {
        if (identifierComparator == null){
            throw new IllegalArgumentException("the identifier comparator cannot be null");
        }
        this.identifierComparator = identifierComparator.getObjectComparator();
        if (aliasComparator == null){
            throw new IllegalArgumentException("the alias comparator cannot be null");
        }
        this.aliasComparator = aliasComparator.getObjectComparator();
        this.identifierCollectionComparator = identifierComparator;
        this.aliasCollectionComparator = aliasComparator;
    }

    public Comparator<Xref> getIdentifierComparator() {
        return this.identifierComparator;
    }

    public Comparator<Alias> getAliasComparator() {
        return this.aliasComparator;
    }

    public CollectionComparator<Xref> getIdentifierCollectionComparator() {
        return identifierCollectionComparator;
    }

    public CollectionComparator<Alias> getAliasCollectionComparator() {
        return aliasCollectionComparator;
    }

    /**
     * It will only compare identifiers if one interactor does have identifiers using UnambiguousIdentifierComparator. Otherwise, it will first compare shortNames (case sensitive)
     * fullnames(case sensitive) and if the shortNames and fullnames are the same, it will compare the aliases using UnambiguousAliasComparator.
     *
     *
     * This comparator will ignore all the other properties of an interactor.
     */
    public int compare(Interactor interactor1, Interactor interactor2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (interactor1 == interactor2){
            return EQUAL;
        }
        else if (interactor1 == null){
            return AFTER;
        }
        else if (interactor2 == null){
            return BEFORE;
        }
        else {
            if (!interactor1.getIdentifiers().isEmpty() || !interactor2.getIdentifiers().isEmpty()){
                Collection<Xref> identifiers1 = interactor1.getIdentifiers();
                Collection<Xref> identifiers2 = interactor2.getIdentifiers();
                return identifierCollectionComparator.compare(identifiers1, identifiers2);
            }

            // compares the short name (case sensitive)
            String shortName1 = interactor1.getShortName();
            String shortName2 = interactor2.getShortName();
            int comp = shortName1.compareTo(shortName2);

            if (comp != 0){
                return comp;
            }

            // compares the full name (case sensitive)
            String fullName1 = interactor1.getFullName();
            String fullName2 = interactor2.getFullName();
            if (fullName1 == null && fullName2 == null){
               comp = 0;
            }
            else if (fullName1 == null){
               return AFTER;
            }
            else if (fullName2 == null){
                return BEFORE;
            }
            else{
                comp = fullName1.compareTo(fullName2);
            }

            if (comp != 0){
                return comp;
            }

            // compares the aliases
            Collection<Alias> aliases1 = interactor1.getAliases();
            Collection<Alias> aliases2 = interactor2.getAliases();

            return aliasCollectionComparator.compare(aliases1, aliases2);
        }
    }

}
