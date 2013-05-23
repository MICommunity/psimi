package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.interactor.InteractorBaseComparator;
import psidev.psi.mi.jami.utils.comparator.participant.ParticipantCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.participant.ParticipantInteractorComparator;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Simple interaction comparator only based on the interactors of an interaction.
 *
 * It will use a InteractorComparator to compare the interactors involved in the interaction.
 *
 * It will ignore all other properties of an Interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class InteractionInteractorComparator implements Comparator<Interaction> {

    protected ParticipantCollectionComparator participantCollectionComparator;

    /**
     * Creates a new InteractionInteractorComparator
     * @param interactorComparator : interactor comparator required for comparing interactors involved in the interaction
     */
    public InteractionInteractorComparator(InteractorBaseComparator interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The Interactor comparator is required to compare interactors of an interaction. It cannot be null");
        }
        this.participantCollectionComparator = new ParticipantCollectionComparator(new ParticipantInteractorComparator(interactorComparator));
    }

    public ParticipantCollectionComparator getParticipantCollectionComparator() {
        return participantCollectionComparator;
    }

    /**
     * It will use a ParticipantInteractorComparator comparator for comparing the participants involved in the interaction.
     *
     * It will ignore all other properties of an Interaction
     *
     * @param interaction1
     * @param interaction2
     * @return
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

            // first compares participants of an interaction
            Collection<? extends Participant> participants1 = Collections.EMPTY_LIST;
            Collection<? extends Participant> participants2 = Collections.EMPTY_LIST;

            if (interaction1 instanceof InteractionEvidence){
                 participants1 = ((InteractionEvidence)interaction1).getParticipantEvidences();
            }
            else if (interaction1 instanceof ModelledInteraction){
                participants1 = ((ModelledInteraction)interaction1).getModelledParticipants();
            }

            if (interaction2 instanceof InteractionEvidence){
                participants2 = ((InteractionEvidence)interaction2).getParticipantEvidences();
            }
            else if (interaction2 instanceof ModelledInteraction){
                participants2 = ((ModelledInteraction)interaction2).getModelledParticipants();
            }

            return participantCollectionComparator.compare(participants1, participants2);
        }
    }
}
