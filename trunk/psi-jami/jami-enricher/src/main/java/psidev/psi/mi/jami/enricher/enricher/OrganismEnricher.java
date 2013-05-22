package psidev.psi.mi.jami.enricher.enricher;

import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.listener.EnricherEventProcessor;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/05/13
 * Time: 10:06
 */
public interface OrganismEnricher extends EnricherEventProcessor {
    public void enrichOrganism(Organism organismMaster)
            throws EnrichmentException;

    public void setFetcher(OrganismFetcher fetcher);
    public OrganismFetcher getFetcher();
}
