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
public interface GeneFetcher {

    /**
     *
     * @param identifier    The identifier of the gene
     * @return              The genes matching the search terms.
     * @throws BridgeFailedException
     */
    public Collection<Gene> fetchGenesByIdentifier(String identifier)
            throws BridgeFailedException;

    /**
     *
     * @param identifier    The identifier of the gene
     * @param taxID         The organism the gene is from.
     * @return              The genes matching the search terms.
     * @throws BridgeFailedException
     */
    public Collection<Gene> fetchGenesByIdentifier(String identifier, int taxID)
            throws BridgeFailedException;


    /**
     *
     * @param identifiers    The identifiers of the gene
     * @return              The genes matching the search terms.
     * @throws BridgeFailedException
     */
    public Collection<Gene> fetchGenesByIdentifiers(Collection<String> identifiers)
            throws BridgeFailedException;

    /**
     *
     * @param identifiers    The identifiers of the gene
     * @param taxID         The organism the gene is from.
     * @return              The genes matching the search terms.
     * @throws BridgeFailedException
     */
    public Collection<Gene> fetchGenesByIdentifiers(Collection<String> identifiers, int taxID)
            throws BridgeFailedException;
}
