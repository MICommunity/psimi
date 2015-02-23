package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.InteractionEvidenceChangeListener;
import psidev.psi.mi.jami.model.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just interaction evidence change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class InteractionEvidenceChangeLogger extends InteractionChangeLogger<InteractionEvidence> implements InteractionEvidenceChangeListener {

    private static final Logger interactionEvidenceChangeLogger = Logger.getLogger("InteractionEvidenceChangeLogger");

    public void onExperimentUpdate(InteractionEvidence interaction, Experiment oldExperiment) {
        if (oldExperiment == null){
            interactionEvidenceChangeLogger.log(Level.INFO, "The experiment has been initialised for the interaction " + interaction.toString());
        }
        else if (interaction.getExperiment() == null){
            interactionEvidenceChangeLogger.log(Level.INFO, "The experiment has been reset for the interaction " + interaction.toString());
        }
        else {
            interactionEvidenceChangeLogger.log(Level.INFO, "The experiment " + oldExperiment + " has been updated with " + interaction.getExperiment() + " in the interaction " + interaction.toString());
        }
    }

    public void onInferredPropertyUpdate(InteractionEvidence interaction, boolean oldInferred) {
        interactionEvidenceChangeLogger.log(Level.INFO, "The inferred property " + oldInferred + " has been updated with " + interaction.isInferred() + " in the interaction " + interaction.toString());
    }

    public void onNegativePropertyUpdate(InteractionEvidence interaction, boolean oldNegative) {
        interactionEvidenceChangeLogger.log(Level.INFO, "The negative property " + oldNegative + " has been updated with " + interaction.isNegative() + " in the interaction " + interaction.toString());

    }

    public void onAddedVariableParameterValues(InteractionEvidence interaction, VariableParameterValueSet added) {
        interactionEvidenceChangeLogger.log(Level.INFO, "The variable parameters values " + added.toString() + " have been added to the interaction " + interaction.toString());
    }

    public void onRemovedVariableParameterValues(InteractionEvidence interaction, VariableParameterValueSet removed) {
        interactionEvidenceChangeLogger.log(Level.INFO, "The variable parameters values " + removed.toString() + " have been removed from the interaction " + interaction.toString());
    }

    public void onAddedConfidence(InteractionEvidence o, Confidence added) {
        interactionEvidenceChangeLogger.log(Level.INFO, "The confidence " + added.toString() + " has been added to the interaction " + o.toString());
    }

    public void onRemovedConfidence(InteractionEvidence o, Confidence removed) {
        interactionEvidenceChangeLogger.log(Level.INFO, "The confidence " + removed.toString() + " has been removed from the interaction " + o.toString());
    }

    public void onAddedParameter(InteractionEvidence o, Parameter added) {
        interactionEvidenceChangeLogger.log(Level.INFO, "The parameter " + added.toString() + " has been added to the interaction " + o.toString());
    }

    public void onRemovedParameter(InteractionEvidence o, Parameter removed) {
        interactionEvidenceChangeLogger.log(Level.INFO, "The parameter " + removed.toString() + " has been removed from the interaction " + o.toString());

    }
}
