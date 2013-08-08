package psidev.psi.mi.jami.enricher.impl.bioactiveentity;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class MinimumBioactiveEntityEnricher
        extends AbstractBioactiveEntityEnricher{

    /**
     * A constructor which initiates with a fetcher.
     * @param fetcher   The fetcher to use to gather bioactive entity records.
     */
    public MinimumBioactiveEntityEnricher(BioactiveEntityFetcher fetcher) {
        super(fetcher);
    }


    @Override
    protected void processBioactiveEntity(BioactiveEntity bioactiveEntityToEnrich) {

    }
}
