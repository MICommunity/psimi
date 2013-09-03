package psidev.psi.mi.jami.enricher.impl.gene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.enricher.GeneEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.gene.GeneEnricherListener;
import psidev.psi.mi.jami.model.Gene;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/09/13
 */
public abstract class AbstractGeneEnricher
        implements GeneEnricher{

    protected static final Logger log = LoggerFactory.getLogger(AbstractGeneEnricher.class.getName());

    public static final int RETRY_COUNT = 5;

    private GeneFetcher fetcher = null;
    private GeneEnricherListener listener = null;
    protected Gene geneFetched = null;

    public AbstractGeneEnricher(GeneFetcher fetcher){
        setGeneFetcher(fetcher);
    }

    public void enrichGene(Gene geneToEnrich) throws EnricherException {
        if(geneToEnrich == null)
            throw new IllegalArgumentException("Can not enrich null Gene.");

        geneFetched = fetchGene(geneToEnrich);
        if(geneFetched == null){
            if(getGeneEnricherListener() != null)
                getGeneEnricherListener().onEnrichmentComplete(geneToEnrich , EnrichmentStatus.FAILED , "Could not fetch a gene.");
            return;
        }

        processGene(geneToEnrich);

        if(getGeneEnricherListener() != null)
            getGeneEnricherListener().onEnrichmentComplete(
                    geneToEnrich , EnrichmentStatus.SUCCESS , null);

    }

    public void enrichGenes(Collection<Gene> genesToEnrich) throws EnricherException {
        for(Gene geneToEnrich : genesToEnrich){
            enrichGene(geneToEnrich);
        }
    }

    abstract void processGene(Gene geneToEnrich);

    private Gene fetchGene(Gene geneToEnrich){
        if(getGeneFetcher() == null)
            throw new IllegalStateException("Can not fetch with null gene fetcher.");
        return null;
    }


    public void setGeneFetcher(GeneFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public GeneFetcher getGeneFetcher() {
        return fetcher;
    }

    public void setGeneEnricherListener(GeneEnricherListener listener) {
        this.listener = listener;
    }

    public GeneEnricherListener getGeneEnricherListener() {
        return listener;
    }
}
