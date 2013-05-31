package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;

/**
 * Unambiguous comparator for curated interactions.
 *
 * It will first compare the basic properties of an interaction using UnambiguousInteractionBaseComparator.
 * Then it will compare the created dates (null created dates always come after)
 * Finally it will compare the updated date (null updated date always come after)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class UnambiguousCuratedInteractionBaseComparator extends CuratedInteractionBaseComparator {

    private static UnambiguousCuratedInteractionBaseComparator unambiguousCuratedInteractionBaseComparator;

    public UnambiguousCuratedInteractionBaseComparator() {
        super(new UnambiguousInteractionBaseComparator());
    }

    @Override
    public UnambiguousInteractionBaseComparator getInteractionBaseComparator() {
        return (UnambiguousInteractionBaseComparator) super.getInteractionBaseComparator();
    }

    @Override
    /**
     * It will first compare the basic properties of an interaction using UnambiguousInteractionBaseComparator.
     * Then it will compare the created dates (null created dates always come after)
     * Finally it will compare the updated date (null updated date always come after)
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousCuratedInteractionBaseComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (unambiguousCuratedInteractionBaseComparator == null){
            unambiguousCuratedInteractionBaseComparator = new UnambiguousCuratedInteractionBaseComparator();
        }

        return unambiguousCuratedInteractionBaseComparator.compare(interaction1, interaction2) == 0;
    }

    /**
     *
     * @param interaction
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Interaction interaction){
        if (unambiguousCuratedInteractionBaseComparator == null){
            unambiguousCuratedInteractionBaseComparator = new UnambiguousCuratedInteractionBaseComparator();
        }

        if (interaction == null){
            return 0;
        }

        int hashcode = 31;

        hashcode = 31*hashcode + UnambiguousInteractionBaseComparator.hashCode(interaction);
        hashcode = 31*hashcode + (interaction.getCreatedDate() != null ? interaction.getCreatedDate().hashCode() : 0);
        hashcode = 31*hashcode + (interaction.getUpdatedDate() != null ? interaction.getUpdatedDate().hashCode() : 0);

        return hashcode;
    }
}
