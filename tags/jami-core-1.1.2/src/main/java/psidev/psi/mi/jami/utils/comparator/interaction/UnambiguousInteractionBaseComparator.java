package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

public class UnambiguousInteractionBaseComparator extends InteractionBaseComparator {

    private static UnambiguousInteractionBaseComparator unambiguousInteractionComparator;

    /**
     * Creates a new UnambiguousInteractionBaseComparator. It will use a UnambiguousParticipantBaseComparator to
     * compare participants and UnambiguousCvTermcomparator to compare interaction types
     */
    public UnambiguousInteractionBaseComparator() {
        super( new UnambiguousExternalIdentifierComparator(),new UnambiguousCvTermComparator());
    }

    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) super.getCvTermComparator();
    }

    public UnambiguousExternalIdentifierComparator getIdentifierComparator() {
        return (UnambiguousExternalIdentifierComparator)super.getIdentifierComparator();
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
