package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.ModelledInteractionChangeListener;
import psidev.psi.mi.jami.model.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just modelledInteraction change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class ModelledInteractionChangeLogger<I extends ModelledInteraction> extends InteractionChangeLogger<I> implements ModelledInteractionChangeListener<I> {

    private static final Logger interactionChangeLogger = Logger.getLogger("InteractionEvidenceChangeLogger");

    public void onSourceUpdate(I interaction, Source oldSource) {
        if (oldSource == null){
            interactionChangeLogger.log(Level.INFO, "The source has been initialised for the interaction " + interaction.toString());
        }
        else if (interaction.getSource() == null){
            interactionChangeLogger.log(Level.INFO, "The source has been reset for the interaction " + interaction.toString());
        }
        else {
            interactionChangeLogger.log(Level.INFO, "The source " + oldSource + " has been updated with " + interaction.getSource() + " in the interaction " + interaction.toString());
        }
    }

    public void onEvidenceTypeUpdate(I interaction, CvTerm oldType) {
        if (oldType == null){
            interactionChangeLogger.log(Level.INFO, "The evidence type has been initialised for the interaction " + interaction.toString());
        }
        else if (interaction.getEvidenceType() == null){
            interactionChangeLogger.log(Level.INFO, "The evidence type has been reset for the interaction " + interaction.toString());
        }
        else {
            interactionChangeLogger.log(Level.INFO, "The evidence type " + oldType + " has been updated with " + interaction.getEvidenceType() + " in the interaction " + interaction.toString());
        }
    }

    public void onAddedInteractionEvidence(I interaction, InteractionEvidence added) {
        interactionChangeLogger.log(Level.INFO, "The interaction evidence " + added.toString() + " have been added to the interaction " + interaction.toString());
    }

    public void onRemovedInteractionEvidence(I interaction, InteractionEvidence removed) {
        interactionChangeLogger.log(Level.INFO, "The interaction evidence " + removed.toString() + " have been removed from the interaction " + interaction.toString());
    }

    public void onAddedConfidence(I o, Confidence added) {
        interactionChangeLogger.log(Level.INFO, "The confidence " + added.toString() + " has been added to the interaction " + o.toString());
    }

    public void onRemovedConfidence(I o, Confidence removed) {
        interactionChangeLogger.log(Level.INFO, "The confidence " + removed.toString() + " has been removed from the interaction " + o.toString());
    }

    public void onAddedParameter(I o, Parameter added) {
        interactionChangeLogger.log(Level.INFO, "The parameter " + added.toString() + " has been added to the interaction " + o.toString());
    }

    public void onRemovedParameter(I o, Parameter removed) {
        interactionChangeLogger.log(Level.INFO, "The parameter " + removed.toString() + " has been removed from the interaction " + o.toString());

    }

    public void onAddedCooperativeEffect(I o, CooperativeEffect added) {
        interactionChangeLogger.log(Level.INFO, "The cooperative effect " + added.toString() + " has been added to the interaction " + o.toString());
    }

    public void onRemovedCooperativeEffect(I o, CooperativeEffect removed) {
        interactionChangeLogger.log(Level.INFO, "The cooperative effect " + removed.toString() + " has been removed from the interaction " + o.toString());

    }
}
