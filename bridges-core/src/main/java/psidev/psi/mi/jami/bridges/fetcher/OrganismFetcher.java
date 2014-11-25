package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Organism;

import java.util.Collection;

/**
 * Interface for fetching an organism from an external service.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  22/05/13
 */
public interface OrganismFetcher{

    /**
     * Finds the complete organism record matching the provided TaxID number.
     *
     * @param taxID     The taxID to search for
     * @return          A matching organism, or null if one could not be found.
     * @throws BridgeFailedException
     */
    public Organism fetchByTaxID(int taxID)
            throws BridgeFailedException;

    /**
     * Finds the complete organism records matching the provided TaxID numbers.
     *
     * @param taxIDs    A collection of taxIDs to search for.
     * @return          A collection of the matching organisms.
     * @throws BridgeFailedException
     * @throws IllegalArgumentException if taxIds is null
     */
    public Collection<Organism> fetchByTaxIDs(Collection<Integer> taxIDs)
            throws BridgeFailedException;
}
