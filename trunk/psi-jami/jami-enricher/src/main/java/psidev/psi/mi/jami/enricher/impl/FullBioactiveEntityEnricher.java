package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * Provides maximum enrichment of the BioactiveEntity.
 * Will enrich all aspects covered by the minimum enricher.
 * As an enricher, no values from the provided BioactiveEntity to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class FullBioactiveEntityEnricher extends MinimalBioactiveEntityEnricher {

    /**
     * A constructor which initiates with a fetcher.
     * @param fetcher   The fetcher to use to gather bioactive entity records.
     */
    public FullBioactiveEntityEnricher(BioactiveEntityFetcher fetcher) {
        super(fetcher);
    }

    /**
     * Strategy for the BioactiveEntity enrichment.
     * This method can be overwritten to change how the BioactiveEntity is enriched.
     * @param bioactiveEntityToEnrich   The BioactiveEntity to be enriched.
     */
    @Override
    protected void processBioactiveEntity(BioactiveEntity bioactiveEntityToEnrich, BioactiveEntity fetched) throws EnricherException {
        processMinimalEnrichment(bioactiveEntityToEnrich, fetched);

        // Xrefs
        processXrefs(bioactiveEntityToEnrich, fetched);

        // Checksums
        processChecksums(bioactiveEntityToEnrich, fetched);
    }

    protected void processChecksums(BioactiveEntity bioactiveEntityToEnrich, BioactiveEntity fetched) throws EnricherException {
        EnricherUtils.mergeChecksums(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getChecksums(), fetched.getChecksums(), true,
                getBioactiveEntityEnricherListener());
    }

    protected void processMinimalEnrichment(BioactiveEntity bioactiveEntityToEnrich, BioactiveEntity fetched) throws EnricherException {
        super.processBioactiveEntity(bioactiveEntityToEnrich, fetched);
    }

    protected void processXrefs(BioactiveEntity bioactiveEntityToEnrich, BioactiveEntity fetched) {
        EnricherUtils.mergeXrefs(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getXrefs(), fetched.getXrefs(), false, true,
                getBioactiveEntityEnricherListener(), getBioactiveEntityEnricherListener());
    }
}
