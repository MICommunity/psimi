package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.model.Publication;

import java.util.Iterator;

/**
 * A publication data source allows to stream the publications of a given datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public interface StreamingPublicationSource extends MIDataSource {

    /**
     * The publications iterator for this datasource.
     * @return iterator of publications for a given datasource
     */
     public Iterator<? extends Publication> getPublicationsIterator();
}
