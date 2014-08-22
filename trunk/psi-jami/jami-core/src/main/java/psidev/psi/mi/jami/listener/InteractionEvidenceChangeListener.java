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

    /**
     *
     * @param interaction : updated interaction
     * @param oldExperiment : old experiment
     */
    public void onExperimentUpdate(InteractionEvidence interaction, Experiment oldExperiment);

    /**
     *
     * @param interaction : updated interaction
     * @param added : added variable parameter value
     */
    public void onAddedVariableParameterValues(InteractionEvidence interaction, VariableParameterValueSet added);

    /**
     *
     * @param interaction : updated interaction
     * @param removed : removed parameter value
     */
    public void onRemovedVariableParameterValues(InteractionEvidence interaction, VariableParameterValueSet removed);

    /**
     *
     * @param interaction : updated interaction
     * @param oldInferred : old inferred
     */
    public void onInferredPropertyUpdate(InteractionEvidence interaction, boolean oldInferred);

    /**
     *
     * @param interaction : updated interaction
     * @param negative  : old negative
     */
    public void onNegativePropertyUpdate(InteractionEvidence interaction, boolean negative);
}
