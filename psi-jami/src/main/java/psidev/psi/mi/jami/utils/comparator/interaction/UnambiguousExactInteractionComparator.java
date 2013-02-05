package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousParticipantBaseComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unambiguous exact Generic interaction comparator.
 * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
 * - It uses UnambiguousExactExperimentalInteractionComparator to compare experimental interactions
 * - It uses UnambiguousExactModelledInteractionComparator to compare modelled interactions
 * - It uses UnambiguousExactCooperativeInteractionComparator to compare cooperative interactions
 * - It uses UnambiguousExactAllostericInteractionComparator to compare allosteric interactions
 * - It uses UnambiguousExactInteractionBaseComparator to compare basic interaction properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class UnambiguousExactInteractionComparator extends InteractionComparator {

    private static UnambiguousExactInteractionComparator unambiguousExactInteractionComparator;

    /**
     * Creates a new UnambiguousExactInteractionComparator.
     */
    public UnambiguousExactInteractionComparator() {
        super(new UnambiguousExactInteractionBaseComparator(), new UnambiguousExactExperimentalInteractionComparator(), new UnambiguousExactCooperativeInteractionComparator(), new UnambiguousExactAllostericInteractionComparator());
    }

    @Override
    public UnambiguousExactInteractionBaseComparator getInteractionBaseComparator() {
        return (UnambiguousExactInteractionBaseComparator) this.interactionBaseComparator;
    }

    @Override
    public UnambiguousExactExperimentalInteractionComparator getExperimentalInteractionComparator() {
        return (UnambiguousExactExperimentalInteractionComparator) this.experimentalInteractionComparator;
    }

    @Override
    public UnambiguousExactCooperativeInteractionComparator getCooperativeInteractionComparator() {
        return (UnambiguousExactCooperativeInteractionComparator) this.cooperativeInteractionComparator;
    }

    @Override
    public UnambiguousExactAllostericInteractionComparator getAllostericInteractionComparator() {
        return (UnambiguousExactAllostericInteractionComparator) allostericInteractionComparator;
    }

    @Override
    /**
     * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
     * - It uses UnambiguousExactExperimentalInteractionComparator to compare experimental interactions
     * - It uses UnambiguousExactModelledInteractionComparator to compare modelled interactions
     * - It uses UnambiguousExactCooperativeInteractionComparator to compare cooperative interactions
     * - It uses UnambiguousExactAllostericInteractionComparator to compare allosteric interactions
     * - It uses UnambiguousExactInteractionBaseComparator to compare basic interaction properties
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        return super.compare(interaction1, interaction2);
    }

    /**
     * Use UnambiguousExactInteractionComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (unambiguousExactInteractionComparator == null){
            unambiguousExactInteractionComparator = new UnambiguousExactInteractionComparator();
        }

        return unambiguousExactInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
