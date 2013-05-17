package psidev.psi.mi.enricher.proteinenricher;


import psidev.psi.mi.enricher.exception.EnrichmentException;
import psidev.psi.mi.enricherlistener.EnricherEventProcessor;
import psidev.psi.mi.fetcher.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 16/05/13
 * Time: 13:03
 */
public interface ProteinEnricher extends EnricherEventProcessor {
    public void enrichProtein(Protein proteinMaster) throws BridgeFailedException, EnrichmentException;
}
