package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.exception.MIIOException;

import java.util.Iterator;

/**
 * A MI datasource providing BinaryInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public interface BinaryInteractionSource extends InteractionSource {
    public Iterator<BinaryInteraction> getInteractionsIterator() throws MIIOException;
}
