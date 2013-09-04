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

    public Collection<Gene> getGenesByEnsemblIdentifier(String identifier)
            throws BridgeFailedException;

    public Collection<Gene> getGenesByEnsemblGenomesIdentifier(String identifier)
            throws BridgeFailedException;

    /* public Gene getGeneByIdentifier (String identifier)
            throws BridgeFailedException;   */
}
