package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.exception.MIIOException;

import java.util.Iterator;

/**
 * A MI datasource for Modelled binary interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public interface ModelledBinaryInteractionSource extends InteractionSource {
    public Iterator<ModelledBinaryInteraction> getInteractionsIterator() throws MIIOException;
}