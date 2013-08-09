package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.impl.DefaultGene;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/08/13
 */
public interface GeneFetcher {

    public Gene getGeneByEnsemblIdentifier(String identifier)
            throws BridgeFailedException;

    public Gene getGeneByEnsemblGenomesIdentifier(String identifier)
            throws BridgeFailedException;

    /* public Gene getGeneByIdentifier (String identifier)
            throws BridgeFailedException;   */
}
