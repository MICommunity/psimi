package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Experiment;

import java.util.Iterator;

/**
 * An experiment data source allows to stream the experiments of a given datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public interface ExperimentStream extends MIDataSource {

    /**
     * The experiments iterator for this datasource.
     * @return iterator of experiments for a given datasource
     */
    public Iterator<Experiment> getExperimentsIterator() throws MIIOException;
}
