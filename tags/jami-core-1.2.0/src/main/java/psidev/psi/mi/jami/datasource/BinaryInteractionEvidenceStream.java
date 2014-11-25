package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;

/**
 * A Data source of binary interaction evidences giving only a stream of interactions.
 * It is not possible to get a full collection of interactions.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public interface BinaryInteractionEvidenceStream extends InteractionEvidenceStream<BinaryInteractionEvidence> {
}
