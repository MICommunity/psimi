package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.OntologyTermChangeListener;
import psidev.psi.mi.jami.model.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just log ontologyTerm change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class OntologyTermChangeLogger extends CvTermChangeLogger implements OntologyTermChangeListener {

    private static final Logger ontologyTermChangeLogger = Logger.getLogger("OntologyTermChangeLogger");

    public void onAddedParent(OntologyTerm cv, OntologyTerm added) {
        ontologyTermChangeLogger.log(Level.INFO, "The parent " + added.toString() + " has been added to the cv term " + cv.toString());
    }

    public void onRemovedParent(OntologyTerm cv, OntologyTerm removed) {
        ontologyTermChangeLogger.log(Level.INFO, "The parent " + removed.toString() + " has been removed from the cv term " + cv.toString());
    }

    public void onAddedChild(OntologyTerm cv, OntologyTerm added) {
        ontologyTermChangeLogger.log(Level.INFO, "The child " + added.toString() + " has been added to the cv term " + cv.toString());
    }

    public void onRemovedChild(OntologyTerm cv, OntologyTerm removed) {
        ontologyTermChangeLogger.log(Level.INFO, "The child " + removed.toString() + " has been removed from the cv term " + cv.toString());
    }
}
