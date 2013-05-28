package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.alias.AliasesCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.alias.UnambiguousAliasComparator;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;
import psidev.psi.mi.jami.utils.comparator.xref.XrefsCollectionComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Unambiguous interactor comparator
 * It will only compare identifiers if one interactor does have identifiers using UnambiguousIdentifierComparator. Otherwise, it will first compare shortNames (case sensitive)
 * fullnames(case sensitive) and if the shortNames and fullnames are the same, it will compare the aliases using UnambiguousAliasComparator.
 *
 * This comparator will ignore all the other properties of an interactor.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class UnambiguousInteractorBaseComparator extends AbstractInteractorBaseComparator {
    private static UnambiguousInteractorBaseComparator unambiguousInteractorComparator;
    private XrefsCollectionComparator identifierCollectionComparator;
    private AliasesCollectionComparator aliasCollectionComparator;

    public UnambiguousInteractorBaseComparator() {
        super(new UnambiguousExternalIdentifierComparator(), new UnambiguousAliasComparator());
        this.identifierCollectionComparator = new XrefsCollectionComparator(getIdentifierComparator());
        this.aliasCollectionComparator = new AliasesCollectionComparator(getAliasComparator());
    }

    @Override
    public UnambiguousExternalIdentifierComparator getIdentifierComparator() {
        return (UnambiguousExternalIdentifierComparator) this.identifierComparator;
    }

    @Override
    public UnambiguousAliasComparator getAliasComparator() {
        return (UnambiguousAliasComparator) this.aliasComparator;
    }

    public XrefsCollectionComparator getIdentifierCollectionComparator() {
        return identifierCollectionComparator;
    }

    public AliasesCollectionComparator getAliasCollectionComparator() {
        return aliasCollectionComparator;
    }

    @Override
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
        if (!interactor.getIdentifiers().isEmpty()){
            List<Xref> list1 = new ArrayList<Xref>(interactor.getIdentifiers());

            Collections.sort(list1, unambiguousInteractorComparator.getIdentifierCollectionComparator().getObjectComparator());
            for (Xref id : list1){
                hashcode = 31*hashcode + UnambiguousExternalIdentifierComparator.hashCode(id);
            }
        }
        else{
            hashcode = 31*hashcode + interactor.getShortName().hashCode();
            hashcode = 31*hashcode + (interactor.getFullName() != null ? interactor.getFullName().hashCode() : 0);

            List<Alias> list1 = new ArrayList<Alias>(interactor.getAliases());

            Collections.sort(list1, unambiguousInteractorComparator.getAliasCollectionComparator().getObjectComparator());
            for (Alias alias : list1){
                hashcode = 31*hashcode + UnambiguousAliasComparator.hashCode(alias);
            }
        }

        return hashcode;
    }
}
