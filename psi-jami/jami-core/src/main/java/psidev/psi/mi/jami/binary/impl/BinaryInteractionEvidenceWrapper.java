package psidev.psi.mi.jami.binary.impl;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * A wrapper for InteractionEvidence which contains two participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public class BinaryInteractionEvidenceWrapper extends AbstractBinaryInteractionWrapper<InteractionEvidence, ParticipantEvidence> implements BinaryInteractionEvidence {

    public BinaryInteractionEvidenceWrapper(InteractionEvidence interaction) {
        super(interaction);
    }

    public BinaryInteractionEvidenceWrapper(InteractionEvidence interaction, CvTerm complexExpansion) {
        super(interaction, complexExpansion);
    }

    public String getImexId() {
        return getWrappedInteraction().getImexId();
    }

    public void assignImexId(String identifier) {
        getWrappedInteraction().assignImexId(identifier);
    }

    public Experiment getExperiment() {
        return getWrappedInteraction().getExperiment();
    }

    public void setExperiment(Experiment experiment) {
        getWrappedInteraction().setExperiment(experiment);
    }

    public void setExperimentAndAddInteractionEvidence(Experiment experiment) {
        getWrappedInteraction().setExperimentAndAddInteractionEvidence(experiment);
    }

    public Collection<VariableParameterValueSet> getVariableParameterValues() {
        return getWrappedInteraction().getVariableParameterValues();
    }

    public String getAvailability() {
        return getWrappedInteraction().getAvailability();
    }

    public void setAvailability(String availability) {
        getWrappedInteraction().setAvailability(availability);
    }

    public Collection<Parameter> getParameters() {
        return getWrappedInteraction().getParameters();
    }

    public boolean isInferred() {
        return getWrappedInteraction().isInferred();
    }

    public void setInferred(boolean inferred) {
        getWrappedInteraction().setInferred(inferred);
    }

    public Collection<Confidence> getConfidences() {
        return getWrappedInteraction().getConfidences();
    }

    public boolean isNegative() {
        return getWrappedInteraction().isNegative();
    }

    public void setNegative(boolean negative) {
        getWrappedInteraction().setNegative(negative);
    }
}
