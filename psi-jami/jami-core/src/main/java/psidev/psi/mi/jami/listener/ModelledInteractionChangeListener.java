package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.*;

/**
 * Listener for changes in modelled interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public interface ModelledInteractionChangeListener<I extends ModelledInteraction> extends InteractionChangeListener<I>, ParametersChangeListener<I>,
        ConfidencesChangeListener<I> {

    public void onAddedCooperativeEffect(I interaction, CooperativeEffect added);

    public void onRemovedCooperativeEffect(I interaction, CooperativeEffect removed);

    public void onAddedInteractionEvidence(I interaction, InteractionEvidence added);

    public void onRemovedInteractionEvidence(I interaction, InteractionEvidence removed);

    public void onSourceUpdate(I interaction, Source oldSource);

    public void onEvidenceTypeUpdate(I interaction, CvTerm oldType);
}
