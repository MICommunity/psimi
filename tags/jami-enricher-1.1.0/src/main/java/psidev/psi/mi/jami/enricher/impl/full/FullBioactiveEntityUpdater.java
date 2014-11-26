package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalBioactiveEntityUpdater;

/**
 * A full updater for bioactive entities.
 *
 * See description of full update in AbstractInteractorUpdater.
 *
 * The bioactive entities fetcher is required for enriching bioactive entities.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class FullBioactiveEntityUpdater
        extends MinimalBioactiveEntityUpdater {

    /**
     * A constructor which initiates with a fetcher.
     * @param fetcher   The fetcher to use to gather bioactive Participant records.
     */
    public FullBioactiveEntityUpdater(BioactiveEntityFetcher fetcher) {
        super(fetcher);
    }

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }
}
