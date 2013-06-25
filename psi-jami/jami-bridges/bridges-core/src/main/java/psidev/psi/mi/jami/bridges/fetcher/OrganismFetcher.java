package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Organism;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/05/13
 */
public interface OrganismFetcher{
    public Organism getOrganismByTaxID(int taxID)
            throws BridgeFailedException;

    public Collection<Organism> getOrganismsByTaxIDs(Collection<Integer> taxIDs)
            throws BridgeFailedException;

}
