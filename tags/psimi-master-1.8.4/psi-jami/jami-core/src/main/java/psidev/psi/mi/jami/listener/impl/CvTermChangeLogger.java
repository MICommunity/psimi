package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.CvTermChangeListener;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just log cvTerm change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class CvTermChangeLogger implements CvTermChangeListener {

    private static final Logger cvChangeLogger = Logger.getLogger("CvTermChangeLogger");

    public void onShortNameUpdate(CvTerm cv, String oldShortName) {
        cvChangeLogger.log(Level.INFO, "The short name "+oldShortName+" has been updated with " + cv.getShortName() + " in the cv term " + cv.toString());
    }

    public void onFullNameUpdate(CvTerm cv, String oldFullName) {
        if (oldFullName == null){
            cvChangeLogger.log(Level.INFO, "The full name has been initialised for the cv term " + cv.toString());
        }
        else if (cv.getFullName() == null){
            cvChangeLogger.log(Level.INFO, "The full name has been reset for the cv term " + cv.toString());
        }
        else {
            cvChangeLogger.log(Level.INFO, "The full name "+oldFullName+" has been updated with " + cv.getFullName() + " in the cv term " + cv.toString());
        }
    }

    public void onMIIdentifierUpdate(CvTerm cv, String oldMI) {
        if (oldMI == null){
            cvChangeLogger.log(Level.INFO, "The MI identifier " + cv.getMIIdentifier() + " has been added to the cv term " + cv.toString());
        }
        else if (cv.getMIIdentifier() == null){
            cvChangeLogger.log(Level.INFO, "The MI identifier "+ oldMI+ " has been removed from the cv term " + cv.toString());
        }
        else {
            cvChangeLogger.log(Level.INFO, "The MI identifier "+oldMI+" has been updated with " + cv.getMIIdentifier() + " in the cv term " + cv.toString());
        }
    }

    public void onMODIdentifierUpdate(CvTerm cv, String oldMOD) {
        if (oldMOD == null){
            cvChangeLogger.log(Level.INFO, "The MOD identifier " + cv.getMODIdentifier() + " has been added to the cv term " + cv.toString());
        }
        else if (cv.getMIIdentifier() == null){
            cvChangeLogger.log(Level.INFO, "The MOD identifier "+ oldMOD+ " has been removed from the cv term " + cv.toString());
        }
        else {
            cvChangeLogger.log(Level.INFO, "The MOD identifier "+oldMOD+" has been updated with " + cv.getMODIdentifier() + " in the cv term " + cv.toString());
        }
    }

    public void onPARIdentifierUpdate(CvTerm cv, String oldPAR) {
        if (oldPAR == null){
            cvChangeLogger.log(Level.INFO, "The PAR identifier " + cv.getPARIdentifier() + " has been added to the cv term " + cv.toString());
        }
        else if (cv.getMIIdentifier() == null){
            cvChangeLogger.log(Level.INFO, "The PAR identifier "+ oldPAR+ " has been removed from the cv term " + cv.toString());
        }
        else {
            cvChangeLogger.log(Level.INFO, "The PAR identifier "+oldPAR+" has been updated with " + cv.getPARIdentifier() + " in the cv term " + cv.toString());
        }
    }

    public void onAddedIdentifier(CvTerm cv, Xref added) {
        cvChangeLogger.log(Level.INFO, "The identifier " + added.toString() + " has been added to the cv term " + cv.toString());
    }

    public void onRemovedIdentifier(CvTerm cv, Xref removed) {
        cvChangeLogger.log(Level.INFO, "The identifier " + removed.toString() + " has been removed from the cv term " + cv.toString());
    }

    public void onAddedXref(CvTerm cv, Xref added) {
        cvChangeLogger.log(Level.INFO, "The xref " + added.toString() + " has been added to the cv term " + cv.toString());
    }

    public void onRemovedXref(CvTerm cv, Xref removed) {
        cvChangeLogger.log(Level.INFO, "The xref " + removed.toString() + " has been removed from the cv term " + cv.toString());
    }

    public void onAddedSynonym(CvTerm cv, Alias added) {
        cvChangeLogger.log(Level.INFO, "The synonym " + added.toString() + " has been added to the cv term " + cv.toString());
    }

    public void onRemovedSynonym(CvTerm cv, Alias removed) {
        cvChangeLogger.log(Level.INFO, "The synonym " + removed.toString() + " has been removed from the cv term " + cv.toString());
    }
}
