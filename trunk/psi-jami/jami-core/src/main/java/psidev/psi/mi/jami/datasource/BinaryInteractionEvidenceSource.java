package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.exception.MIIOException;

import java.util.Iterator;

/**
 * A MI data source for Binary interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public interface BinaryInteractionEvidenceSource extends InteractionSource {

    public Iterator<BinaryInteractionEvidence> getInteractionsIterator() throws MIIOException;
}
