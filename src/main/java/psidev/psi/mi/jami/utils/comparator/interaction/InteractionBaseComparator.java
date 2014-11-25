package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;
import psidev.psi.mi.jami.utils.comparator.xref.XrefsCollectionComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 *Interaction comparator.
 *
 * It will first compare the interaction types using UnambiguousCvTermComparator.
 * Then it will compare the rigids (case sensitive, null rigids always come after).
 * Then it will compare the identifiers using UnambiguousExternalIdentifierComparator.
 * If the interactions do not have any identifiers, it will compare the shortnames (case sensitive, shortname null comes always after)
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class InteractionBaseComparator implements Comparator<Interaction> {

    private CollectionComparator<Xref> identifierCollectionComparator;
    private Comparator<CvTerm> cvTermComparator;
    private Comparator<Xref> identifierComparator;

    public InteractionBaseComparator(Comparator<Xref> identifierComparator, Comparator<CvTerm> cvTermComparator) {
        if (identifierComparator == null){
           throw new IllegalArgumentException("The identifier comparator cannot be null");
        }
        this.identifierComparator = identifierComparator;
        if (cvTermComparator == null){
            throw new IllegalArgumentException("The Cv Term comparator cannot be null");
        }
        this.cvTermComparator = cvTermComparator;
        this.identifierCollectionComparator = new XrefsCollectionComparator(getIdentifierComparator());
    }

    public InteractionBaseComparator(CollectionComparator<Xref> identifierComparator,  Comparator<CvTerm> cvTermComparator) {
        if (identifierComparator == null){
            throw new IllegalArgumentException("The identifier comparator cannot be null");
        }
        this.identifierComparator = identifierComparator.getObjectComparator();
        if (cvTermComparator == null){
            throw new IllegalArgumentException("The Cv Term comparator cannot be null");
        }
        this.cvTermComparator = cvTermComparator;
        this.identifierCollectionComparator = identifierComparator;
    }

    public Comparator<CvTerm> getCvTermComparator() {
        return cvTermComparator;
    }

    public CollectionComparator<Xref> getIdentifierCollectionComparator() {
        return identifierCollectionComparator;
    }

    public Comparator<Xref> getIdentifierComparator() {
        return this.identifierComparator;
    }

    /**
     * It will first compare the interaction types using UnambiguousCvTermComparator.
     * Then it will compare the rigids (case sensitive, null rigids always come after).
     * Then it will compare the identifiers using UnambiguousExternalIdentifierComparator.
     * If the interactions do not have any identifiers, it will compare the shortnames (case sensitive, shortname null comes always after)
     *
     *
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (interaction1 == interaction2){
            return 0;
        }
        else if (interaction1 == null){
            return AFTER;
        }
        else if (interaction2 == null){
            return BEFORE;
        }
        else {

            // First compares interaction type
            CvTerm type1 = interaction1.getInteractionType();
            CvTerm type2 = interaction2.getInteractionType();

            int comp = cvTermComparator.compare(type1, type2);
            if (comp != 0){
                return comp;
            }

            // then compares rigid if both are set
            String rigid1 = interaction1.getRigid();
            String rigid2 = interaction2.getRigid();
            if (rigid1 != null && rigid2 != null){
                comp = rigid1.compareTo(rigid2);
                if (comp != 0){
                    return comp;
                }
            }
            else if (rigid1 != null){
                return BEFORE;
            }
            else if (rigid2 != null){
                return AFTER;
            }

            // then compares identifiers if both are set
            if (!interaction1.getIdentifiers().isEmpty() || !interaction2.getIdentifiers().isEmpty()){
               Collection<Xref> identifiers1 = interaction1.getIdentifiers();
               Collection<Xref> identifiers2 = interaction2.getIdentifiers();

                return identifierCollectionComparator.compare(identifiers1, identifiers2);
            }
            else {
                // then compares shortnames if both are set
                String shortname1 = interaction1.getShortName();
                String shortname2 = interaction2.getShortName();
                if (shortname1 != null && shortname2 != null){
                    return shortname1.compareTo(shortname2);
                }
                else if (shortname1 != null) {
                    return BEFORE;
                }
                else if (shortname2 != null){
                    return AFTER;
                }
            }

            return comp;
        }
    }
}
