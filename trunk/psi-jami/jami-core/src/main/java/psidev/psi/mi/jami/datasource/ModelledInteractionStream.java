package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.model.ModelledInteraction;

/**
 * A Data source of modelled interaction giving only a stream of interactions.
 * It is not possible to get a full collection of interactions.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public interface ModelledInteractionStream<T extends ModelledInteraction> extends InteractionStream<T> {
}
