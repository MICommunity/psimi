package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.model.*;

import java.util.Iterator;

/**
 * An interactor data source allows to stream the interactors of a given datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public interface StreamingInteractorSource {

    /**
     * The interactors iterator for this datasource.
     * @return iterator of interactors for a given datasource
     */
    public Iterator<Interactor> getInteractorsIterator();

    public Iterator<Protein> getProteinsIterator();
    public Iterator<NucleicAcid> getNucleicAcidsIterator();
    public Iterator<Gene> getGenesIterator();
    public Iterator<BioactiveEntity> getBioactiveEntitiesIterator();
    public Iterator<Complex> getComplexesIterator();
}
