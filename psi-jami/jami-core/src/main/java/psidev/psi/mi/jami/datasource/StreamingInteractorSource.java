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

public interface StreamingInteractorSource extends MIDataSource {

    /**
     * The interactors iterator for this datasource.
     * @return iterator of interactors for a given datasource
     */
    public Iterator<? extends Interactor> getInteractorsIterator() throws MIIOException;

    public Iterator<? extends Protein> getProteinsIterator() throws MIIOException;
    public Iterator<? extends NucleicAcid> getNucleicAcidsIterator() throws MIIOException;
    public Iterator<? extends Gene> getGenesIterator() throws MIIOException;
    public Iterator<? extends BioactiveEntity> getBioactiveEntitiesIterator() throws MIIOException;
    public Iterator<? extends Complex> getComplexesIterator() throws MIIOException;
}
