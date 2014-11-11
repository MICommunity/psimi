package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.InteractorChangeListener;
import psidev.psi.mi.jami.model.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just interactor change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class InteractorChangeLogger<T extends Interactor> implements InteractorChangeListener<T> {

    private static final Logger interactorChangeLogger = Logger.getLogger("InteractorChangeLogger");

    public void onShortNameUpdate(T protein, String oldShortName) {
        interactorChangeLogger.log(Level.INFO, "The short name " + oldShortName + " has been updated with " + protein.getShortName() + " in the interactor " + protein.toString());
    }

    public void onFullNameUpdate(T protein, String oldFullName) {
        if (oldFullName == null){
            interactorChangeLogger.log(Level.INFO, "The full name has been initialised for the interactor " + protein.toString());
        }
        else if (protein.getFullName() == null){
            interactorChangeLogger.log(Level.INFO, "The full name has been reset for the interactor " + protein.toString());
        }
        else {
            interactorChangeLogger.log(Level.INFO, "The full name " + oldFullName + " has been updated with " + protein.getFullName() + " in the interactor " + protein.toString());
        }
    }

    public void onInteractorTypeUpdate(T protein, CvTerm old) {
        interactorChangeLogger.log(Level.INFO, "The interactor type" + old.toString() + " has been replaced with " + protein.getInteractorType().toString()+ " in the interactor " + protein.toString());
    }

    public void onOrganismUpdate(T protein, Organism old) {
        if (old == null){
            interactorChangeLogger.log(Level.INFO, "The organism has been initialised for the interactor " + protein.toString());
        }
        else if (protein.getOrganism() == null){
            interactorChangeLogger.log(Level.INFO, "The organism has been reset for the interactor " + protein.toString());
        }
        else {
            interactorChangeLogger.log(Level.INFO, "The organism " + old.toString() + " has been replaced with " + protein.getOrganism() + " in the interactor " + protein.toString());
        }
    }

    public void onAddedIdentifier(T protein, Xref added) {
        interactorChangeLogger.log(Level.INFO, "The identifier " + added.toString() + " has been added to the interactor " + protein.toString());
    }

    public void onRemovedIdentifier(T protein, Xref removed) {
        interactorChangeLogger.log(Level.INFO, "The identifier " + removed.toString() + " has been removed from the interactor " + protein.toString());
    }

    public void onAddedXref(T protein, Xref added) {
        interactorChangeLogger.log(Level.INFO, "The xref " + added.toString() + " has been added to the interactor " + protein.toString());
    }

    public void onRemovedXref(T protein, Xref removed) {
        interactorChangeLogger.log(Level.INFO, "The xref " + removed.toString() + " has been removed from the interactor " + protein.toString());
    }

    public void onAddedAlias(T protein, Alias added) {
        interactorChangeLogger.log(Level.INFO, "The alias " + added.toString() + " has been added to the interactor " + protein.toString());
    }

    public void onRemovedAlias(T protein, Alias removed) {
        interactorChangeLogger.log(Level.INFO, "The alias " + removed.toString() + " has been removed from the interactor " + protein.toString());
    }

    public void onAddedChecksum(T protein, Checksum added) {
        interactorChangeLogger.log(Level.INFO, "The checksum " + added.toString() + " has been added to the interactor " + protein.toString());
    }

    public void onRemovedChecksum(T protein, Checksum removed) {
        interactorChangeLogger.log(Level.INFO, "The checksum " + removed.toString() + " has been removed from the interactor " + protein.toString());
    }

    public void onAddedAnnotation(T protein, Annotation added) {
        interactorChangeLogger.log(Level.INFO, "The annotation " + added.toString() + " has been added to the interactor " + protein.toString());
    }

    public void onRemovedAnnotation(T protein, Annotation removed) {
        interactorChangeLogger.log(Level.INFO, "The annotation " + removed.toString() + " has been removed from the interactor " + protein.toString());
    }
}
