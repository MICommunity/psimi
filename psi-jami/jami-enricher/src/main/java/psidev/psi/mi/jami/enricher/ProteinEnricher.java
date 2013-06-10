package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.listener.EnricherEventProcessor;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 16/05/13
 * Time: 13:03
 */
public interface ProteinEnricher {//extends EnricherEventProcessor {

    public void enrichProtein(Protein proteinToEnrich)
            throws EnrichmentException;

    public void setFetcher(ProteinFetcher fetcher);
    public ProteinFetcher getFetcher();
}
