package psidev.psi.mi.xml.events;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Participant;
import psidev.psi.mi.xml.model.ParticipantIdentificationMethod;

import java.io.Serializable;
import java.util.Set;

/**
 * This event is fired when having multiple participant identification methods for one participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/03/13</pre>
 */

public class MultipleParticipantIdentificationMethodsPerParticipant extends DefaultFileSourceContext implements Serializable {

    private Participant participant;
    private Interaction interaction;
    private Set<ParticipantIdentificationMethod> participantIdentificationMethods;
    private String message;

    public MultipleParticipantIdentificationMethodsPerParticipant(Interaction interaction, Participant participant, Set<ParticipantIdentificationMethod> values, String message){
        this.participantIdentificationMethods = values;
        this.message = message;
        this.interaction = interaction;
        this.participant = participant;
    }

    public Set<ParticipantIdentificationMethod> getParticipantIdentificationMethods() {
        return participantIdentificationMethods;
    }

    public String getMessage() {
        return message;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public Participant getParticipant() {
        return participant;
    }
}