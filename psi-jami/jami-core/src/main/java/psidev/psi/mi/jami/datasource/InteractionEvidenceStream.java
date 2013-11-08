package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.model.InteractionEvidence;

/**
 * A Data source of interaction evidences giving only a stream of interactions.
 * It is not possible to get a full collection of interactions.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public interface InteractionEvidenceStream<T extends InteractionEvidence> extends InteractionStream<T> {
}
