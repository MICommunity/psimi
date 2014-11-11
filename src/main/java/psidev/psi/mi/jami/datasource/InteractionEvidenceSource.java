package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.model.InteractionEvidence;

/**
 * A Data source of interaction evidences.
 * It gives full access to all the interactions using Iterator or the full collection.
 * It can also give information about the size of the dataSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public interface InteractionEvidenceSource<T extends InteractionEvidence> extends InteractionEvidenceStream<T>, InteractionSource<T> {
}
