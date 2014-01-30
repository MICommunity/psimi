package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.ProteinChangeListener;
import psidev.psi.mi.jami.model.Protein;

import java.util.logging.Logger;

/**
 * This listener will just protein change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class ProteinChangeLogger extends PolymerChangeLogger<Protein> implements ProteinChangeListener {

    private static final Logger proteinChangeLogger = Logger.getLogger("ProteinChangeLogger");
}
