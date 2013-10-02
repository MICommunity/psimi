package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.Organism;

/**
 * Provides maximum enrichment of the organism.
 * Will enrich all aspects covered by the minimum enricher as well as enriching the Aliases.
 * As an enricher, no values from the provided organism to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public class FullOrganismEnricher extends MinimalOrganismEnricher {

    public FullOrganismEnricher(OrganismFetcher organismFetcher) {
        super(organismFetcher);
    }

    @Override
    protected void processOtherProperties(Organism organismToEnrich, Organism organismFetched) {
        EnricherUtils.mergeAliases(organismToEnrich, organismToEnrich.getAliases(), organismFetched.getAliases(), false, getOrganismEnricherListener());
    }
}
