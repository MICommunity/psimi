package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Gene;

import java.util.Collection;

/**
 * A fetcher for genes.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/08/13
 */
public interface GeneFetcher extends InteractorFetcher<Gene>{

    /**
     *
     * @param identifier    The identifier of the gene
     * @param taxID         The organism the gene is from.
     * @return              The genes matching the search terms.
     * @throws BridgeFailedException
     */
    public Collection<Gene> fetchByIdentifier(String identifier, int taxID)
            throws BridgeFailedException;


    /**
     *
     * @param identifiers    The identifiers of the gene
     * @param taxID         The organism the gene is from.
     * @return              The genes matching the search terms.
     * @throws BridgeFailedException
     */
    public Collection<Gene> fetchByIdentifiers(Collection<String> identifiers, int taxID)
            throws BridgeFailedException;
}
