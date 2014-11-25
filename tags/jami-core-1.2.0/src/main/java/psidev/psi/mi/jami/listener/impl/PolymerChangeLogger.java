package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.PolymerChangeListener;
import psidev.psi.mi.jami.model.Polymer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just protein change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class PolymerChangeLogger<P extends Polymer> extends InteractorChangeLogger<P> implements PolymerChangeListener<P> {

    private static final Logger polymerChangeLogger = Logger.getLogger("PolymerChangeLogger");

    public void onSequenceUpdate(P protein, String oldSequence) {
        if (oldSequence == null){
            polymerChangeLogger.log(Level.INFO, "The sequence has been initialised for the polymer " + protein.toString());
        }
        else if (protein.getSequence() == null){
            polymerChangeLogger.log(Level.INFO, "The sequence has been reset for the polymer " + protein.toString());
        }
        else {
            polymerChangeLogger.log(Level.INFO, "The sequence " + oldSequence + " has been updated with " + protein.getSequence() + " in the polymer " + protein.toString());
        }
    }
}
