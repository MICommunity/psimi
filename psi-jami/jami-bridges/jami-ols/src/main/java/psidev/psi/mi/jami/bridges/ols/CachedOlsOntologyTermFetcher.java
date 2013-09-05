package psidev.psi.mi.jami.bridges.ols;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.OntologyTerm;


/**
 *
 *
 * Code for the cache based on the CachedOntologyService at uk.ac.ebi.intact.bridges.olslight;
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public class CachedOlsOntologyTermFetcher extends CachedOlsFetcher<OntologyTerm>{

    public static final String CACHE_NAME = "ontology-cache";


    public CachedOlsOntologyTermFetcher() throws BridgeFailedException {
        super(CACHE_NAME, new OlsOntologyTermFetcher());
    }
}