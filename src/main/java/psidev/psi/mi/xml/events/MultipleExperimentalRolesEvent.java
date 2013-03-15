package psidev.psi.mi.xml.events;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.xml.model.ExperimentalRole;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Participant;

import java.io.Serializable;
import java.util.Set;

/**
 * This event is fired when a participant has several roles
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class MultipleExperimentalRolesEvent extends DefaultFileSourceContext implements Serializable {

    private Participant participant;
    private Interaction interaction;
    private Set<ExperimentalRole> experimentalRoles;
    private String message;

    public MultipleExperimentalRolesEvent(Interaction interaction, Participant participant, Set<ExperimentalRole> values, String message){
        this.experimentalRoles = values;
        this.message = message;
        this.interaction = interaction;
        this.participant = participant;
    }

    public Set<ExperimentalRole> getMultipleRoles() {
        return experimentalRoles;
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
