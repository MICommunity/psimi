package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.*;

/**
 * Listener for changes in an experimental entity
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public interface ParticipantEvidenceChangeListener<F extends ParticipantEvidence> extends ParticipantChangeListener<F>, ParametersChangeListener<F>, ConfidencesChangeListener<F> {

    public void onExperimentalRoleUpdate(F participant, CvTerm oldType);

    public void onExpressedInUpdate(F participant, Organism oldOrganism);

    public void onAddedIdentificationMethod(F participant, CvTerm added);

    public void onRemovedIdentificationMethod(F participant, CvTerm removed);

    public void onAddedExperimentalPreparation(F participant, CvTerm added);

    public void onRemovedExperimentalPreparation(F participant, CvTerm removed);
}
