package psidev.psi.mi.jami.binary.impl;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * A wrapper for ModelledInteraction which contains two participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public class ModelledBinaryInteractionWrapper extends AbstractBinaryInteractionWrapper<ModelledInteraction, ModelledParticipant> implements ModelledBinaryInteraction{

    private ModelledInteraction modelledInteraction;

    public ModelledBinaryInteractionWrapper(ModelledInteraction interaction) {
        super(interaction);
    }

    public ModelledBinaryInteractionWrapper(ModelledInteraction interaction, CvTerm complexExpansion) {
        super(interaction, complexExpansion);
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        return getWrappedInteraction().getInteractionEvidences();
    }

    public Source getSource() {
        return getWrappedInteraction().getSource();
    }

    public void setSource(Source source) {
        getWrappedInteraction().setSource(source);
    }

    public Collection<ModelledConfidence> getModelledConfidences() {
        return getWrappedInteraction().getModelledConfidences();
    }

    public Collection<ModelledParameter> getModelledParameters() {
        return getWrappedInteraction().getModelledParameters();
    }

    public Collection<CooperativeEffect> getCooperativeEffects() {
        return getWrappedInteraction().getCooperativeEffects();
    }
}
