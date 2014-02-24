package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Iterator;

/**
 * A CV term data source allows to stream the controlled vocabulary terms of a given datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public interface CvTermStream extends MIDataSource {

    /**
     * The CV terms iterator for this datasource.
     * @return iterator of CV terms for a given datasource
     */
    public Iterator<CvTerm> getCvTermsIterator() throws MIIOException;
}
