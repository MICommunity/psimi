package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.FeatureChangeListener;
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

public class FeatureChangeLogger<T extends Feature> implements FeatureChangeListener<T> {

    private static final Logger featureChangeLogger = Logger.getLogger("FeatureChangeLogger");

    public void onShortNameUpdate(T protein, String oldShortName) {
        featureChangeLogger.log(Level.INFO, "The short name " + oldShortName + " has been updated with " + protein.getShortName() + " in the feature " + protein.toString());
    }

    public void onFullNameUpdate(T protein, String oldFullName) {
        if (oldFullName == null){
            featureChangeLogger.log(Level.INFO, "The full name has been initialised for the feature " + protein.toString());
        }
        else if (protein.getFullName() == null){
            featureChangeLogger.log(Level.INFO, "The full name has been reset for the feature " + protein.toString());
        }
        else {
            featureChangeLogger.log(Level.INFO, "The full name " + oldFullName + " has been updated with " + protein.getFullName() + " in the feature " + protein.toString());
        }
    }

    public void onInterproUpdate(T feature, String oldInterpro) {
        if (oldInterpro == null){
            featureChangeLogger.log(Level.INFO, "The interpro identifier has been initialised for the feature " + feature.toString());
        }
        else if (feature.getInterpro() == null){
            featureChangeLogger.log(Level.INFO, "The interpro identifier has been reset for the feature " + feature.toString());
        }
        else {
            featureChangeLogger.log(Level.INFO, "The interpro identifier " + oldInterpro.toString() + " has been replaced with " + feature.getInterpro() + " in the feature " + feature.toString());
        }
    }

    public void onTypeUpdate(T protein, CvTerm old) {
        if (old == null){
            featureChangeLogger.log(Level.INFO, "The type has been initialised for the feature " + protein.toString());
        }
        else if (protein.getType() == null){
            featureChangeLogger.log(Level.INFO, "The type has been reset for the feature " + protein.toString());
        }
        else {
            featureChangeLogger.log(Level.INFO, "The type " + old.toString() + " has been replaced with " + protein.getType() + " in the feature " + protein.toString());
        }
    }

    public void onAddedRange(T feature, Range added) {
        featureChangeLogger.log(Level.INFO, "The range " + added.toString() + " has been added to the feature " + feature.toString());
    }

    public void onRemovedRange(T feature, Range removed) {
        featureChangeLogger.log(Level.INFO, "The range " + removed.toString() + " has been removed from the feature " + feature.toString());
    }

    public void onUpdatedRangePositions(T feature, Range range, Position oldStart, Position oldEnd) {
        featureChangeLogger.log(Level.INFO, "The range " + oldStart.toString()+"-"+oldEnd.toString() + " has been replaced with "+range.toString()+" in the feature " + feature.toString());
    }

    public void onInteractionEffectUpdate(T protein, CvTerm old) {
        if (old == null){
            featureChangeLogger.log(Level.INFO, "The interaction effect has been initialised for the feature " + protein.toString());
        }
        else if (protein.getInteractionEffect() == null){
            featureChangeLogger.log(Level.INFO, "The interaction effect has been reset for the feature " + protein.toString());
        }
        else {
            featureChangeLogger.log(Level.INFO, "The interaction effect " + old.toString() + " has been replaced with " + protein.getInteractionEffect() + " in the feature " + protein.toString());
        }
    }

    public void onInteractionDependencyUpdate(T protein, CvTerm old) {
        if (old == null){
            featureChangeLogger.log(Level.INFO, "The interaction dependency has been initialised for the feature " + protein.toString());
        }
        else if (protein.getInteractionDependency() == null){
            featureChangeLogger.log(Level.INFO, "The interaction dependency has been reset for the feature " + protein.toString());
        }
        else {
            featureChangeLogger.log(Level.INFO, "The interaction dependency " + old.toString() + " has been replaced with " + protein.getInteractionDependency() + " in the feature " + protein.toString());
        }
    }

    public void onAddedIdentifier(T protein, Xref added) {
        featureChangeLogger.log(Level.INFO, "The identifier " + added.toString() + " has been added to the feature " + protein.toString());
    }

    public void onRemovedIdentifier(T protein, Xref removed) {
        featureChangeLogger.log(Level.INFO, "The identifier " + removed.toString() + " has been removed from the feature " + protein.toString());
    }

    public void onAddedXref(T protein, Xref added) {
        featureChangeLogger.log(Level.INFO, "The xref " + added.toString() + " has been added to the feature " + protein.toString());
    }

    public void onRemovedXref(T protein, Xref removed) {
        featureChangeLogger.log(Level.INFO, "The xref " + removed.toString() + " has been removed from the feature " + protein.toString());
    }

    public void onAddedAlias(T protein, Alias added) {
        featureChangeLogger.log(Level.INFO, "The alias " + added.toString() + " has been added to the feature " + protein.toString());
    }

    public void onRemovedAlias(T protein, Alias removed) {
        featureChangeLogger.log(Level.INFO, "The alias " + removed.toString() + " has been removed from the feature " + protein.toString());
    }

    public void onAddedAnnotation(T protein, Annotation added) {
        featureChangeLogger.log(Level.INFO, "The annotation " + added.toString() + " has been added to the feature " + protein.toString());
    }

    public void onRemovedAnnotation(T protein, Annotation removed) {
        featureChangeLogger.log(Level.INFO, "The annotation " + removed.toString() + " has been removed from the feature " + protein.toString());
    }
}
