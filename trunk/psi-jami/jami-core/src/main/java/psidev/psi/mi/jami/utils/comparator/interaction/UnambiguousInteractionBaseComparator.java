package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;
import psidev.psi.mi.jami.utils.comparator.xref.XrefsCollectionComparator;

import java.util.*;

/**
 * Unambiguous Interaction comparator.
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

public class UnambiguousInteractionBaseComparator implements Comparator<Interaction> {

    private static UnambiguousInteractionBaseComparator unambiguousInteractionComparator;

    private XrefsCollectionComparator identifierCollectionComparator;
    private UnambiguousCvTermComparator cvTermComparator;
    private UnambiguousExternalIdentifierComparator identifierComparator;

    /**
     * Creates a new UnambiguousInteractionBaseComparator. It will use a UnambiguousParticipantBaseComparator to
     * compare participants and UnambiguousCvTermcomparator to compare interaction types
     */
    public UnambiguousInteractionBaseComparator() {
        this.identifierComparator = new UnambiguousExternalIdentifierComparator();
        this.cvTermComparator = new UnambiguousCvTermComparator();
        this.identifierCollectionComparator = new XrefsCollectionComparator(getIdentifierComparator());
    }

    public UnambiguousCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }

    public XrefsCollectionComparator getIdentifierCollectionComparator() {
        return identifierCollectionComparator;
    }

    public UnambiguousExternalIdentifierComparator getIdentifierComparator() {
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

        if (interaction1 == null && interaction2 == null){
            return EQUAL;
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

    /**
     * Use UnambiguousInteractionBaseComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (unambiguousInteractionComparator == null){
            unambiguousInteractionComparator = new UnambiguousInteractionBaseComparator();
        }

        return unambiguousInteractionComparator.compare(interaction1, interaction2) == 0;
    }

    /**
     *
     * @param interaction
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Interaction interaction){
        if (unambiguousInteractionComparator == null){
            unambiguousInteractionComparator = new UnambiguousInteractionBaseComparator();
        }

        if (interaction == null){
            return 0;
        }

        int hashcode = 31;

        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(interaction.getInteractionType());

        String rigid = interaction.getRigid();
        hashcode = 31*hashcode + (rigid != null ? rigid.hashCode() : 0);
        if (!interaction.getIdentifiers().isEmpty()){
            List<Xref> list1 = new ArrayList<Xref>(interaction.getIdentifiers());

            Collections.sort(list1, unambiguousInteractionComparator.getIdentifierCollectionComparator().getObjectComparator());
            for (Xref id : list1){
                hashcode = 31*hashcode + UnambiguousExternalIdentifierComparator.hashCode(id);
            }
        }
        else {
            hashcode = 31*hashcode + (interaction.getShortName() != null ? interaction.getShortName().hashCode() : 0);
        }

        return hashcode;
    }
}
