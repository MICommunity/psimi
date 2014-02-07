package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.ComplexChangeListener;
import psidev.psi.mi.jami.listener.ModelledInteractionChangeListener;
import psidev.psi.mi.jami.model.*;

import java.util.Date;
import java.util.logging.Logger;

/**
 * This listener will just complex change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class ComplexChangeLogger extends InteractorChangeLogger<Complex> implements ComplexChangeListener {

    private static final Logger complexChangeLogger = Logger.getLogger("ComplexChangeLogger");
    private ModelledInteractionChangeListener<Complex> delegate;

    public ComplexChangeLogger() {
        this.delegate = new ModelledInteractionChangeLogger<Complex>();
    }

    public void onAddedCooperativeEffect(Complex interaction, CooperativeEffect added) {
        this.delegate.onAddedCooperativeEffect(interaction, added);
    }

    public void onRemovedCooperativeEffect(Complex interaction, CooperativeEffect removed) {
        this.delegate.onRemovedCooperativeEffect(interaction, removed);
    }

    public void onAddedInteractionEvidence(Complex interaction, InteractionEvidence added) {
        this.delegate.onAddedInteractionEvidence(interaction, added);
    }

    public void onRemovedInteractionEvidence(Complex interaction, InteractionEvidence removed) {
        this.delegate.onRemovedInteractionEvidence(interaction, removed);
    }

    public void onSourceUpdate(Complex interaction, Source oldSource) {
        this.delegate.onSourceUpdate(interaction, oldSource);
    }

    public void onAddedConfidence(Complex o, Confidence added) {
        this.delegate.onAddedConfidence(o, added);
    }

    public void onRemovedConfidence(Complex o, Confidence removed) {
        this.delegate.onRemovedConfidence(o, removed);
    }

    public void onUpdatedDateUpdate(Complex interaction, Date oldUpdate) {
        this.delegate.onUpdatedDateUpdate(interaction, oldUpdate);
    }

    public void onCreatedDateUpdate(Complex interaction, Date oldCreated) {
        this.delegate.onCreatedDateUpdate(interaction, oldCreated);
    }

    public void onInteractionTypeUpdate(Complex interaction, CvTerm oldType) {
        this.delegate.onInteractionTypeUpdate(interaction, oldType);
    }

    public void onAddedParticipant(Complex interaction, Participant addedParticipant) {
        this.delegate.onAddedParticipant(interaction, addedParticipant);
    }

    public void onRemovedParticipant(Complex interaction, Participant removedParticipant) {
        this.delegate.onRemovedParticipant(interaction, removedParticipant);
    }

    public void onAddedParameter(Complex o, Parameter added) {
        this.delegate.onAddedParameter(o, added);
    }

    public void onRemovedParameter(Complex o, Parameter removed) {
        this.delegate.onRemovedParameter(o, removed);
    }
}
