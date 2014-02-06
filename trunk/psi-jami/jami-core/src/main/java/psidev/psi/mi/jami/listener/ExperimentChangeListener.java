package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.*;

/**
 * Listener for experiment changes
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public interface ExperimentChangeListener extends XrefsChangeListener<Experiment>, AnnotationsChangeListener<Experiment>, ConfidencesChangeListener<Experiment> {

    public void onPublicationUpdate(Experiment experiment, Publication oldPublication);

    public void onInteractionDetectionMethodUpdate(Experiment experiment, CvTerm oldCv);

    public void onHostOrganismUpdate(Experiment experiment, Organism oldOrganism);

    /**
     * Listen to the event where a parameter has been added to the object parameters.
     * @param o        The object which has changed.
     * @param added             The added parameter.
     */
    public void onAddedVariableParameter(Experiment o, VariableParameter added);

    /**
     * Listen to the event where a parameter has been removed from the object parameters.
     * @param o        The object which has changed.
     * @param removed           The removed parameter.
     */
    public void onRemovedVariableParameter(Experiment o, VariableParameter removed);
}
