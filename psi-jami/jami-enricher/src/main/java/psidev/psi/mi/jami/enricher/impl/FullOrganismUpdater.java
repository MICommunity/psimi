package psidev.psi.mi.jami.enricher.impl;


import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.Organism;

/**
 * Provides maximum updating of the organism.
 * Will update all aspects covered by the minimum updater as well as updating the Aliases.
 * As an updater, values from the provided CvTerm to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  24/05/13
 */
public class FullOrganismUpdater extends MinimalOrganismUpdater{

    public FullOrganismUpdater(OrganismFetcher organismFetcher) {
        super(organismFetcher);
    }

    @Override
    protected void processOtherProperties(Organism organismToEnrich, Organism organismFetched) {
        EnricherUtils.mergeAliases(organismToEnrich, organismToEnrich.getAliases(), organismFetched.getAliases(), true, getOrganismEnricherListener());
    }
}
