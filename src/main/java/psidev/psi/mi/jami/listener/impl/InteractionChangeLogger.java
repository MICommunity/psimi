package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.InteractionChangeListener;
import psidev.psi.mi.jami.model.*;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just interaction change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class InteractionChangeLogger<T extends Interaction> implements InteractionChangeListener<T> {

    private static final Logger interactionChangeLogger = Logger.getLogger("InteractionChangeLogger");

    public void onShortNameUpdate(T interaction, String oldShortName) {
        if (oldShortName == null){
            interactionChangeLogger.log(Level.INFO, "The short name has been initialised for the interaction " + interaction.toString());
        }
        else if (interaction.getShortName() == null){
            interactionChangeLogger.log(Level.INFO, "The short name has been reset for the interaction " + interaction.toString());
        }
        else {
            interactionChangeLogger.log(Level.INFO, "The short name " + oldShortName + " has been updated with " + interaction.getShortName() + " in the interaction " + interaction.toString());
        }
    }

    public void onUpdatedDateUpdate(T interaction, Date oldUpdate) {
        if (oldUpdate == null){
            interactionChangeLogger.log(Level.INFO, "The updated date has been initialised for the interaction " + interaction.toString());
        }
        else if (interaction.getUpdatedDate() == null){
            interactionChangeLogger.log(Level.INFO, "The updated date has been reset for the interaction " + interaction.toString());
        }
        else {
            interactionChangeLogger.log(Level.INFO, "The updated date " + oldUpdate + " has been updated with " + interaction.getUpdatedDate() + " in the interaction " + interaction.toString());
        }
    }

    public void onCreatedDateUpdate(T interaction, Date oldCreated) {
        if (oldCreated == null){
            interactionChangeLogger.log(Level.INFO, "The creation date has been initialised for the interaction " + interaction.toString());
        }
        else if (interaction.getCreatedDate() == null){
            interactionChangeLogger.log(Level.INFO, "The creation date has been reset for the interaction " + interaction.toString());
        }
        else {
            interactionChangeLogger.log(Level.INFO, "The creation date " + oldCreated + " has been updated with " + interaction.getCreatedDate() + " in the interaction " + interaction.toString());
        }
    }

    public void onInteractionTypeUpdate(T interaction, CvTerm old) {
        if (old == null){
            interactionChangeLogger.log(Level.INFO, "The interaction type has been initialised for the interaction " + interaction.toString());
        }
        else if (interaction.getInteractionType() == null){
            interactionChangeLogger.log(Level.INFO, "The interaction type has been reset for the interaction " + interaction.toString());
        }
        else {
            interactionChangeLogger.log(Level.INFO, "The interaction type " + old + " has been updated with " + interaction.getInteractionType() + " in the interaction " + interaction.toString());
        }    }

    public void onAddedParticipant(T interaction, Participant addedParticipant) {
        interactionChangeLogger.log(Level.INFO, "The participant " + addedParticipant.toString() + " has been added to the interaction " + interaction.toString());
    }

    public void onRemovedParticipant(T interaction, Participant removedParticipant) {
        interactionChangeLogger.log(Level.INFO, "The participant " + removedParticipant.toString() + " has been removed from the interaction " + interaction.toString());
    }

    public void onAddedIdentifier(T interaction, Xref added) {
        interactionChangeLogger.log(Level.INFO, "The identifier " + added.toString() + " has been added to the interaction " + interaction.toString());
    }

    public void onRemovedIdentifier(T interaction, Xref removed) {
        interactionChangeLogger.log(Level.INFO, "The identifier " + removed.toString() + " has been removed from the interaction " + interaction.toString());
    }

    public void onAddedXref(T interaction, Xref added) {
        interactionChangeLogger.log(Level.INFO, "The xref " + added.toString() + " has been added to the interaction " + interaction.toString());
    }

    public void onRemovedXref(T interaction, Xref removed) {
        interactionChangeLogger.log(Level.INFO, "The xref " + removed.toString() + " has been removed from the interaction " + interaction.toString());
    }

    public void onAddedChecksum(T interaction, Checksum added) {
        interactionChangeLogger.log(Level.INFO, "The checksum " + added.toString() + " has been added to the interaction " + interaction.toString());
    }

    public void onRemovedChecksum(T interaction, Checksum removed) {
        interactionChangeLogger.log(Level.INFO, "The checksum " + removed.toString() + " has been removed from the interaction " + interaction.toString());
    }

    public void onAddedAnnotation(T interaction, Annotation added) {
        interactionChangeLogger.log(Level.INFO, "The annotation " + added.toString() + " has been added to the interaction " + interaction.toString());
    }

    public void onRemovedAnnotation(T interaction, Annotation removed) {
        interactionChangeLogger.log(Level.INFO, "The annotation " + removed.toString() + " has been removed from the interaction " + interaction.toString());
    }
}
