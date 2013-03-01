package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.model.Organism;

import java.util.Iterator;

/**
 * An organism data source allows to stream the organisms of a given datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public interface StreamingOrganismSource extends MolecularInteractionDataSource{

    /**
     * The organisms iterator for this datasource.
     * @return iterator of organisms for a given datasource
     */
    public Iterator<Organism> getOrganismsIterator();
}
