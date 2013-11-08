package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;

import java.util.Iterator;

/**
 * A Data source of interactions giving only a stream of interactions.
 * It is not possible to get a full collection of interactions.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public interface InteractionStream<T extends Interaction> extends MIDataSource {

    /**
     * The iterator of Interactions for this datasource
     * @return iterator of Interactions for this datasource
     * @throws MIIOException
     */
    public Iterator<T> getInteractionsIterator() throws MIIOException;
}
