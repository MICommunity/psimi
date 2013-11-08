package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;

import java.util.Collection;

/**
 * A Data source of interactions.
 * It gives full access to all the interactions using Iterator or the full collection.
 * It can also give information about the size of the datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public interface InteractionSource<T extends Interaction> extends InteractionStream<T> {

    /**
     * The collection of Interactions for this datasource
     * @return collection of Interactions for this datasource
     * @throws psidev.psi.mi.jami.exception.MIIOException
     */
    public Collection<T> getInteractions() throws MIIOException;

    public long getNumberOfInteractions();
}
