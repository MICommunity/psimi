package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.VariableParameterValueSet;

/**
 * Listener for changes in interaction evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public interface InteractionEvidenceChangeListener extends InteractionChangeListener<InteractionEvidence>, ParametersChangeListener<InteractionEvidence>,
        ConfidencesChangeListener<InteractionEvidence> {

    public void onExperimentUpdate(InteractionEvidence interaction, Experiment oldExperiment);

    public void onAddedVariableParameterValues(InteractionEvidence interaction, VariableParameterValueSet added);

    public void onRemovedVariableParameterValues(InteractionEvidence interaction, VariableParameterValueSet removed);

    public void onInferredPropertyUpdate(InteractionEvidence interaction, boolean oldInferred);

    public void onNegativePropertyUpdate(InteractionEvidence interaction, boolean negative);
}
