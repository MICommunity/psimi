package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.enricher.listener.GeneEnricherListener;
import psidev.psi.mi.jami.model.Gene;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/09/13
 */
public interface GeneEnricher extends InteractorEnricher<Gene>{

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
