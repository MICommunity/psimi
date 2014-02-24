package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;

import java.util.Iterator;

/**
 * An interactor data source allows to stream the interactors of a given datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public interface InteractorStream<T extends Interactor> extends MIDataSource {

    /**
     * The interactors iterator for this datasource.
     * @return iterator of interactors for a given datasource
     */
    public Iterator<T> getInteractorsIterator() throws MIIOException;
}
