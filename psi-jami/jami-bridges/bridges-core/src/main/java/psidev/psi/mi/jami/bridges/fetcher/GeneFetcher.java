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
     * @param source        The database that the identifier is from.
     * @return              The genes matching the search terms.
     * @throws BridgeFailedException
     */
    public Collection<Gene> getGenesByIdentifier(String identifier , GeneIdentifierSource source)
            throws BridgeFailedException;

    /**
     *
     * @param identifier    The identifier of the gene
     * @param source        The database that the identifier is from.
     * @param taxID         The organism the gene is from.
     * @return              The genes matching the search terms.
     * @throws BridgeFailedException
     */
    public Collection<Gene> getGenesByIdentifier(String identifier , GeneIdentifierSource source, int taxID)
            throws BridgeFailedException;


}
