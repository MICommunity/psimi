package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.enricher.impl.AbstractInteractorEnricher;
import psidev.psi.mi.jami.enricher.impl.AbstractInteractorUpdater;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * A basic minimal updater for bioactive entities.
 *
 * See description of minimal update in AbstractInteractorUpdater.
 *
 * The bioactive entities fetcher is required for enriching bioactive entities.
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class MinimalBioactiveEntityUpdater extends AbstractInteractorUpdater<BioactiveEntity> {

    /**
     * A constructor which initiates with a fetcher.
     * @param fetcher   The fetcher to use to gather bioactive Participant records.
     */
    public MinimalBioactiveEntityUpdater(BioactiveEntityFetcher fetcher) {
        super(new MinimalBioactiveEntityEnricher(fetcher));
    }

    protected MinimalBioactiveEntityUpdater(AbstractInteractorEnricher<BioactiveEntity> interactorEnricher) {
        super(interactorEnricher);
    }
}
