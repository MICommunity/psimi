package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Default interactor comparator.
 * If both interactors have identifier(s), it will look for at least one identical identifier using DefaultIdentifierComparator. If at least one interactor does not have any identifiers, it will look at
 * the short names (case sensitive).
 * If the shortnames do not match, it will look at the fullNames (case sensitive) if both fullnames are set and if the fullnames are not set or do not match,
 * it will look for at least one common alias using DefaultAliasComparator.
 *
 * This comparator will ignore all the other properties of an interactor.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class DefaultInteractorBaseComparator extends AbstractInteractorBaseComparator {

    private static DefaultInteractorBaseComparator defaultInteractorComparator;

    public DefaultInteractorBaseComparator() {
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
     * If both interactors have identifier(s), it will look for at least one identical identifier using DefaultIdentifierComparator. If at least one interactor does not have any identifiers, it will look at
     * the short names (case sensitive).
     * If the shortnames do not match, it will look at the fullNames (case sensitive) if both fullnames are set and if the fullnames are not set or do not match,
     * it will look for at least one common alias using DefaultAliasComparator.
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

            int comp = EQUAL;
            // first compares identifiers, at least one matching identifier
            if (!interactor1.getIdentifiers().isEmpty() && !interactor2.getIdentifiers().isEmpty()){
                List<Xref> ids1 = new ArrayList<Xref>(interactor1.getIdentifiers());
                List<Xref> ids2 = new ArrayList<Xref>(interactor2.getIdentifiers());
                // sort the collections first
                Collections.sort(ids1, identifierComparator);
                Collections.sort(ids2, identifierComparator);
                // get an iterator
                Iterator<Xref> iterator1 = ids1.iterator();
                Iterator<Xref> iterator2 = ids2.iterator();

                // at least one external identifier must match
                Xref altid1 = iterator1.next();
                Xref altid2 = iterator2.next();
                comp = identifierComparator.compare(altid1, altid2);
                while (comp != 0 && altid1 != null && altid2 != null){
                    // altid1 is before altid2
                    if (comp < 0){
                        // we need to get the next element from ids1
                        if (iterator1.hasNext()){
                            altid1 = iterator1.next();
                            comp = identifierComparator.compare(altid1, altid2);
                        }
                        // ids 1 is empty, we can stop here
                        else {
                            altid1 = null;
                        }
                    }
                    // altid2 is before altid1
                    else {
                        // we need to get the next element from ids2
                        if (iterator2.hasNext()){
                            altid2 = iterator2.next();
                            comp = identifierComparator.compare(altid1, altid2);
                        }
                        // ids 2 is empty, we can stop here
                        else {
                            altid2 = null;
                        }
                    }
                }

                return comp;
            }

            // then compares the short name (case sensitive)
            String shortName1 = interactor1.getShortName();
            String shortName2 = interactor2.getShortName();
            comp = shortName1.compareTo(shortName2);
            if (comp == 0){
                return comp;
            }

            // then compares the full name (case sensitive)
            String fullName1 = interactor1.getFullName();
            String fullName2 = interactor2.getFullName();
            if (fullName1 != null && fullName2 != null){
                comp = fullName1.compareTo(fullName2);
                if (comp == 0){
                    return comp;
                }
            }

            // finally compares aliases
            if (!interactor1.getAliases().isEmpty() && !interactor2.getAliases().isEmpty()){
                List<Alias> aliases1 = new ArrayList<Alias>(interactor1.getAliases());
                List<Alias> aliases2 = new ArrayList<Alias>(interactor2.getAliases());
                // sort the collections first
                Collections.sort(aliases1, aliasComparator);
                Collections.sort(aliases2, aliasComparator);
                // get an iterator
                Iterator<Alias> iterator1 = aliases1.iterator();
                Iterator<Alias> iterator2 = aliases2.iterator();

                // at least one alias must match
                Alias alias1 = iterator1.next();
                Alias alias2 = iterator2.next();
                comp = aliasComparator.compare(alias1, alias2);
                while (comp != 0 && alias1 != null && alias2 != null){
                    // alias1 is before alias2
                    if (comp < 0){
                        // we need to get the next element from aliases1
                        if (iterator1.hasNext()){
                            alias1 = iterator1.next();
                            comp = aliasComparator.compare(alias1, alias2);
                        }
                        // ids 1 is empty, we can stop here
                        else {
                            alias1 = null;
                        }
                    }
                    // alias2 is before alias1
                    else {
                        // we need to get the next element from aliases2
                        if (iterator2.hasNext()){
                            alias2 = iterator2.next();
                            comp = aliasComparator.compare(alias1, alias2);
                        }
                        // aliases 2 is empty, we can stop here
                        else {
                            alias2 = null;
                        }
                    }
                }
            }

            return comp;
        }
    }

    /**
     * Use DefaultInteractorBaseComparator to know if two interactors are equals.
     * @param interactor1
     * @param interactor2
     * @return true if the two interactors are equal
     */
    public static boolean areEquals(Interactor interactor1, Interactor interactor2){
        if (defaultInteractorComparator == null){
            defaultInteractorComparator = new DefaultInteractorBaseComparator();
        }

        return defaultInteractorComparator.compare(interactor1, interactor2) == 0;
    }
}
