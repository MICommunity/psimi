package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.ProteinChangeListener;
import psidev.psi.mi.jami.model.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just protein change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class ProteinChangeLogger implements ProteinChangeListener {

    private static final Logger proteinChangeLogger = Logger.getLogger("ProteinChangeLogger");

    public void onSequenceUpdate(Protein protein, String oldSequence) {
        if (oldSequence == null){
            proteinChangeLogger.log(Level.INFO, "The sequence has been initialised for the protein " + protein.toString());
        }
        else if (protein.getSequence() == null){
            proteinChangeLogger.log(Level.INFO, "The sequence has been reset for the protein " + protein.toString());
        }
        else {
            proteinChangeLogger.log(Level.INFO, "The sequence "+oldSequence+" has been updated with " + protein.getSequence() + " in the protein " + protein.toString());
        }
    }

    public void onShortNameUpdate(Protein protein, String oldShortName) {
        proteinChangeLogger.log(Level.INFO, "The short name "+oldShortName+" has been updated with " + protein.getShortName() + " in the protein " + protein.toString());
    }

    public void onFullNameUpdate(Protein protein, String oldFullName) {
        if (oldFullName == null){
            proteinChangeLogger.log(Level.INFO, "The full name has been initialised for the protein " + protein.toString());
        }
        else if (protein.getFullName() == null){
            proteinChangeLogger.log(Level.INFO, "The full name has been reset for the protein " + protein.toString());
        }
        else {
            proteinChangeLogger.log(Level.INFO, "The full name "+oldFullName+" has been updated with " + protein.getFullName() + " in the protein " + protein.toString());
        }
    }

    public void onAddedInteractorType(Protein protein) {
        proteinChangeLogger.log(Level.INFO, "The interactor type " + protein.getInteractorType().toString() + " has been added to the protein " + protein.toString());
    }

    public void onAddedOrganism(Protein protein) {
        proteinChangeLogger.log(Level.INFO, "The organism " + protein.getOrganism().getTaxId() + " has been added to the protein " + protein.toString());
    }

    public void onAddedIdentifier(Protein protein, Xref added) {
        proteinChangeLogger.log(Level.INFO, "The identifier " + added.toString() + " has been added to the protein " + protein.toString());
    }

    public void onRemovedIdentifier(Protein protein, Xref removed) {
        proteinChangeLogger.log(Level.INFO, "The identifier " + removed.toString() + " has been removed from the protein " + protein.toString());
    }

    public void onAddedXref(Protein protein, Xref added) {
        proteinChangeLogger.log(Level.INFO, "The xref " + added.toString() + " has been added to the protein " + protein.toString());
    }

    public void onRemovedXref(Protein protein, Xref removed) {
        proteinChangeLogger.log(Level.INFO, "The xref " + removed.toString() + " has been removed from the protein " + protein.toString());
    }

    public void onAddedAlias(Protein protein, Alias added) {
        proteinChangeLogger.log(Level.INFO, "The alias " + added.toString() + " has been added to the protein " + protein.toString());
    }

    public void onRemovedAlias(Protein protein, Alias removed) {
        proteinChangeLogger.log(Level.INFO, "The alias " + removed.toString() + " has been removed from the protein " + protein.toString());
    }

    public void onAddedChecksum(Protein protein, Checksum added) {
        proteinChangeLogger.log(Level.INFO, "The checksum " + added.toString() + " has been added to the protein " + protein.toString());
    }

    public void onRemovedChecksum(Protein protein, Checksum removed) {
        proteinChangeLogger.log(Level.INFO, "The checksum " + removed.toString() + " has been removed from the protein " + protein.toString());
    }

    public void onAddedAnnotation(Protein protein, Annotation added) {
        proteinChangeLogger.log(Level.INFO, "The annotation " + added.toString() + " has been added to the protein " + protein.toString());
    }

    public void onRemovedAnnotation(Protein protein, Annotation removed) {
        proteinChangeLogger.log(Level.INFO, "The annotation " + removed.toString() + " has been removed from the protein " + protein.toString());
    }
}
