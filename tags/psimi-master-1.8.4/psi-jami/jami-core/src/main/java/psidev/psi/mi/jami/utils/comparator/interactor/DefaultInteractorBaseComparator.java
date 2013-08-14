package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.ComparatorUtils;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;

import java.util.Iterator;

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

public class DefaultInteractorBaseComparator {

    /**
     * Use DefaultInteractorBaseComparator to know if two interactors are equals.
     * @param interactor1
     * @param interactor2
     * @return true if the two interactors are equal
     */
    public static boolean areEquals(Interactor interactor1, Interactor interactor2){
        if (interactor1 == null && interactor2 == null){
            return true;
        }
        else if (interactor1 == null || interactor2 == null){
            return false;
        }
        else {

            // first compares identifiers, at least one matching identifier
            if (!interactor1.getIdentifiers().isEmpty() && !interactor2.getIdentifiers().isEmpty()){
                // get an iterator
                Iterator<Xref> iterator1 = interactor1.getIdentifiers().iterator();

                // at least one external identifier must match
                boolean comp = false;
                while (!comp && iterator1.hasNext()){
                    Xref altid1 = iterator1.next();

                    for (Xref altid2 : interactor2.getIdentifiers()){
                        if (DefaultExternalIdentifierComparator.areEquals(altid1, altid2)){
                            return true;
                        }
                    }
                }

                return false;
            }

            // then compares the short name (case sensitive)
            String shortName1 = interactor1.getShortName();
            String shortName2 = interactor2.getShortName();
            if (shortName1.equals(shortName2)){
                return true;
            }

            // then compares the full name (case sensitive)
            String fullName1 = interactor1.getFullName();
            String fullName2 = interactor2.getFullName();
            if (fullName1 != null && fullName2 != null){
                if(fullName1.equals(fullName2)){
                    return true;
                }
            }

            // finally compares aliases
            if (!interactor1.getAliases().isEmpty() && !interactor2.getAliases().isEmpty()){
                return ComparatorUtils.findAtLeastOneMatchingAlias(interactor1.getAliases(), interactor2.getAliases());
            }

            return false;
        }
    }
}
