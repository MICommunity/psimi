package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.gene.GeneEnricherListener;
import psidev.psi.mi.jami.model.Gene;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/09/13
 */
public interface GeneEnricher {

    /**
     * Enriches a single gene.
     * @param geneToEnrich   The entity to be enriched.
     * @throws EnricherException        Thrown if problems are encountered in the fetcher
     */
    public void enrichGene(Gene geneToEnrich)
            throws EnricherException;

    /**
     * Enriches a collection of genes.
     * @param genesToEnrich       The entities to be enriched
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichGenes(Collection<Gene> genesToEnrich)
            throws EnricherException;

    /**
     * Sets the gene fetcher to be used for enrichment.
     * If the fetcher is null, an illegal state exception will be thrown at the the next enrichment.
     * @param fetcher   The fetcher to be used to gather data for enrichment
     */
    public void setGeneFetcher(GeneFetcher fetcher);

    /**
     * Returns the current fetcher which is being used to collect information about entities for enrichment.
     * @return  The current fetcher.
     */
    public GeneFetcher getGeneFetcher();

    /**
     * Sets the listener to use when the gene has been changed
     * @param listener  The new listener. Can be null.
     */
    public void setGeneEnricherListener(GeneEnricherListener listener);

    /**
     * The current listener of changes to the genes.
     * @return  The current listener. Can be null.
     */
    public GeneEnricherListener getGeneEnricherListener();
}
