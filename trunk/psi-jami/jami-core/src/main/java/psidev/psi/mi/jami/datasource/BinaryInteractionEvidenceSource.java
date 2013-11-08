package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.exception.MIIOException;

import java.util.Collection;
import java.util.Iterator;

/**
 * A datasource of binary interaction evidences
 *
 * It gives full access to all the interactions using Iterator or the full collection.
 * It can also give information about the size of the datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public interface BinaryInteractionEvidenceSource extends BinaryInteractionEvidenceStream, InteractionEvidenceSource<BinaryInteractionEvidence> {
}
