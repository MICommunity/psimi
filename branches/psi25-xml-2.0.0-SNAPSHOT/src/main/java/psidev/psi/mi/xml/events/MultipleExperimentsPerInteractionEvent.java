package psidev.psi.mi.xml.events;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.Interaction;

import java.io.Serializable;
import java.util.Set;

/**
 * This event is fired when an interaction has several experiments.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/03/13</pre>
 */

public class MultipleExperimentsPerInteractionEvent extends DefaultFileSourceContext implements Serializable {

    private Interaction interaction;
    private Set<ExperimentDescription> experimentDescriptions;
    private String message;

    public MultipleExperimentsPerInteractionEvent(Interaction interaction, Set<ExperimentDescription> values, String message){
        this.experimentDescriptions = values;
        this.message = message;
        this.interaction = interaction;
    }

    public Set<ExperimentDescription> getMultipleExperiments() {
        return experimentDescriptions;
    }

    public String getMessage() {
        return message;
    }

    public Interaction getInteraction() {
        return interaction;
    }
}