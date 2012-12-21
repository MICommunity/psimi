package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.ExternalIdentifier;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.comparator.alias.AliasComparator;

import java.util.*;

/**
 * Basic interactor comparator.
 * It will look first for unique identifier if both are set. If the unique identifiers are not both set or equals, it will look for at least one
 * same alternative identifier. If no alternative identifiers are equal, it will look at the short names (case sensitive).
 * If the shortnames do not match, it will look for at least one common alias.
 *
 * This comparator will ignore all the other properties of an interactor.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class InteractorComparator implements Comparator<Interactor> {

    protected Comparator<ExternalIdentifier> identifierComparator;
    protected AliasComparator aliasComparator;

    /**
     * Creates a new InteractorComparator.
     * @param identifierComparator : the identifier comparator. It is required
     * @param aliasComparator : the comparator for aliases. it is required
     */
    public InteractorComparator(Comparator<ExternalIdentifier> identifierComparator, AliasComparator aliasComparator){

        if (identifierComparator == null){
            throw new IllegalArgumentException("The external identifier comparator is required to compares identifiers. It cannot be null");
        }
        this.identifierComparator = identifierComparator;
        if (aliasComparator == null){
            throw new IllegalArgumentException("The alias comparator is required to compares aliases. It cannot be null");
        }
        this.aliasComparator = aliasComparator;
    }

    public Comparator<ExternalIdentifier> getIdentifierComparator() {
        return identifierComparator;
    }

    public AliasComparator getAliasComparator() {
        return aliasComparator;
    }

    /**
     * Basic interactor comparator.
     * It will look first for unique identifier if both are set. If the unique identifiers are not both set or equals, it will look for at least one
     * same alternative identifier. If no alternative identifiers are equal, it will look at the short names (case sensitive).
     * If the shortnames do not match, it will look for at least one common alias.
     *
     * This comparator will ignore all the other properties of an interactor.
     * @param interactor1
     * @param interactor2
     * @return
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
            // first compare unique identifier
            ExternalIdentifier uniqueId1 = interactor1.getUniqueIdentifier();
            ExternalIdentifier uniqueId2 = interactor2.getUniqueIdentifier();

            int comp = identifierComparator.compare(uniqueId1, uniqueId2);
            if (comp == EQUAL && uniqueId1 != null && uniqueId2 != null){
                return comp;
            }

            int comp2 = EQUAL;
            // then compares alternative identifiers if first identifier is not enough
            if (!interactor1.getAlternativeIdentifiers().isEmpty() && !interactor2.getAlternativeIdentifiers().isEmpty()){
                List<ExternalIdentifier> ids1 = new ArrayList<ExternalIdentifier>(interactor1.getAlternativeIdentifiers());
                List<ExternalIdentifier> ids2 = new ArrayList<ExternalIdentifier>(interactor2.getAlternativeIdentifiers());
                // sort the collections first
                Collections.sort(ids1, identifierComparator);
                Collections.sort(ids2, identifierComparator);
                // get an iterator
                Iterator<ExternalIdentifier> iterator1 = ids1.iterator();
                Iterator<ExternalIdentifier> iterator2 = ids2.iterator();

                // at least one external identifier must match
                ExternalIdentifier altid1 = iterator1.next();
                ExternalIdentifier altid2 = iterator2.next();
                comp2 = identifierComparator.compare(altid1, altid2);
                while (comp2 != 0 && altid1 != null && altid2 != null){
                    // altid1 is before altid2
                    if (comp2 < 0){
                        // we need to get the next element from ids1
                        if (iterator1.hasNext()){
                            altid1 = iterator1.next();
                            comp2 = identifierComparator.compare(altid1, altid2);
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
                            comp2 = identifierComparator.compare(altid1, altid2);
                        }
                        // ids 2 is empty, we can stop here
                        else {
                            altid2 = null;
                        }
                    }
                }

                if (comp2 == 0){
                    return comp2;
                }
            }

            // then compares the short name (case sensitive)
            String shortName1 = interactor1.getShortName();
            String shortName2 = interactor2.getShortName();
            int comp3 = shortName1.compareTo(shortName2);
            if (comp3 == 0){
                return comp3;
            }

            // finally compares aliases
            int comp4 = EQUAL;
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
                comp4 = aliasComparator.compare(alias1, alias2);
                while (comp4 != 0 && alias1 != null && alias2 != null){
                    // alias1 is before alias2
                    if (comp4 < 0){
                        // we need to get the next element from aliases1
                        if (iterator1.hasNext()){
                            alias1 = iterator1.next();
                            comp4 = aliasComparator.compare(alias1, alias2);
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
                            comp4 = aliasComparator.compare(alias1, alias2);
                        }
                        // aliases 2 is empty, we can stop here
                        else {
                            alias2 = null;
                        }
                    }
                }

                if (comp4 == 0){
                    return comp4;
                }
            }

            if (comp != 0){
                return comp;
            }
            else if (comp2 != 0) {
                return comp2;
            }
            else if (comp3 != 0) {
                return comp3;
            }
            else if (comp4 != 0) {
                return comp4;
            }
            else {
                return EQUAL;
            }
        }
    }
}
