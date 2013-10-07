package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.ModelledEntity;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.comparator.participant.CustomizableModelledParticipantComparator;
import psidev.psi.mi.jami.utils.comparator.participant.ParticipantCollectionComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic ModelledInteraction comparator.
 *
 * It will use a Comparator<Interaction> to compare basic interaction properties.
 * Then it will compare the modelledParticipants using CustomizableModelledParticipantComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class ModelledInteractionComparator implements Comparator<ModelledInteraction> {

    protected Comparator<Interaction> interactionBaseComparator;
    protected ParticipantCollectionComparator participantCollectionComparator;

    /**
     *
     * @param participantComparator : required to compare participants
     * @param interactionComparator
     */
    public ModelledInteractionComparator(CustomizableModelledParticipantComparator participantComparator, Comparator<Interaction> interactionComparator){
        if (interactionComparator == null){
            throw new IllegalArgumentException("The Interaction comparator is required to compare basic interaction properties. It cannot be null");
        }
        this.interactionBaseComparator = interactionComparator;
        if (participantComparator == null){
            throw new IllegalArgumentException("The participant comparator is required to compare participants of an interaction. It cannot be null");
        }
        this.participantCollectionComparator = new ParticipantCollectionComparator<ModelledEntity>(participantComparator);

    }

    public ParticipantCollectionComparator getParticipantCollectionComparator() {
        return participantCollectionComparator;
    }

    public Comparator<Interaction> getInteractionBaseComparator() {
        return interactionBaseComparator;
    }

    /**
     * It will use a Comparator<Interaction> to compare basic interaction properties.
     * Then it will compare the modelledParticipants using CustomizableModelledParticipantComparator.
     * @param modelledInteraction1
     * @param modelledInteraction2
     * @return
     */
    public int compare(ModelledInteraction modelledInteraction1, ModelledInteraction modelledInteraction2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (modelledInteraction1 == null && modelledInteraction2 == null){
            return EQUAL;
        }
        else if (modelledInteraction1 == null){
            return AFTER;
        }
        else if (modelledInteraction2 == null){
            return BEFORE;
        }
        else {

            int comp = interactionBaseComparator.compare(modelledInteraction1, modelledInteraction2);
            if (comp != 0){
               return comp;
            }

            // first compares participants of an interaction
            Collection<? extends ModelledParticipant> participants1 = modelledInteraction1.getParticipants();
            Collection<? extends ModelledParticipant> participants2 = modelledInteraction2.getParticipants();

            return participantCollectionComparator.compare(participants1, participants2);
        }
    }
}
