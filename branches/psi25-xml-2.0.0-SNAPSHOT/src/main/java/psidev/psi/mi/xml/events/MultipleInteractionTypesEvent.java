package psidev.psi.mi.xml.events;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.InteractionType;

import java.io.Serializable;
import java.util.Set;

/**
 * This event is fired when having multiple interaction types
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/03/13</pre>
 */

public class MultipleInteractionTypesEvent extends DefaultFileSourceContext implements Serializable {

    private Interaction interaction;
    private Set<InteractionType> types;
    private String message;

    public MultipleInteractionTypesEvent(Interaction interaction, Set<InteractionType> values, String message){
        this.types = values;
        this.message = message;
        this.interaction = interaction;
    }

    public Set<InteractionType> getMultipleTypes() {
        return types;
    }

    public String getMessage() {
        return message;
    }

    public Interaction getInteraction() {
        return interaction;
    }
}