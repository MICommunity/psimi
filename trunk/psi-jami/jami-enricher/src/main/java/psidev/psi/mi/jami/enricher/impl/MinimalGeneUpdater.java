package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.enricher.GeneEnricher;
import psidev.psi.mi.jami.enricher.listener.GeneEnricherListener;
import psidev.psi.mi.jami.model.Gene;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 04/09/13
 */
public class MinimalGeneUpdater extends AbstractInteractorUpdater<Gene> implements GeneEnricher{

    public MinimalGeneUpdater(GeneFetcher fetcher) {
        super(new MinimalGeneEnricher(fetcher));
    }

    protected MinimalGeneUpdater(AbstractInteractorEnricher<Gene> interactorEnricher) {
        super(interactorEnricher);
    }

    public GeneFetcher getGeneFetcher() {
        return ((GeneEnricher)getInteractorEnricher()).getGeneFetcher();
    }

    public void setGeneEnricherListener(GeneEnricherListener listener) {
        ((GeneEnricher)getInteractorEnricher()).setGeneEnricherListener(listener);
    }

    public GeneEnricherListener getGeneEnricherListener() {
        return ((GeneEnricher)getInteractorEnricher()).getGeneEnricherListener();
    }
}
