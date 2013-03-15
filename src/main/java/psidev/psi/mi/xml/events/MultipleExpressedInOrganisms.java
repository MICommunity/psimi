package psidev.psi.mi.xml.events;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.xml.model.HostOrganism;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Participant;

import java.io.Serializable;
import java.util.Set;

/**
 * This event is fired when having multiple expressed in organisms
 * for one participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/03/13</pre>
 */

public class MultipleExpressedInOrganisms extends DefaultFileSourceContext implements Serializable {

    private Participant participant;
    private Interaction interaction;
    private Set<HostOrganism> hostOrganisms;
    private String message;

    public MultipleExpressedInOrganisms(Interaction interaction, Participant participant, Set<HostOrganism> values, String message){
        this.hostOrganisms = values;
        this.message = message;
        this.interaction = interaction;
        this.participant = participant;
    }

    public Set<HostOrganism> getMultipleHostOrganisms() {
        return hostOrganisms;
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