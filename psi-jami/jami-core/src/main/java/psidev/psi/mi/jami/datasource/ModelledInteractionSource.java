package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.ModelledInteraction;

import java.util.Iterator;

/**
 * A MI data source for ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public interface ModelledInteractionSource extends InteractionSource {
    public Iterator<ModelledInteraction> getInteractionsIterator() throws MIIOException;
}
