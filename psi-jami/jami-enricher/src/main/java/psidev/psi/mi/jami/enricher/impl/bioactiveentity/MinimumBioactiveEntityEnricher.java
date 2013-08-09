package psidev.psi.mi.jami.enricher.impl.bioactiveentity;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * Provides minimum enrichment of the bioactiveEntity.
 * As an enricher, no values from the provided bioactiveEntity to enrich will be changed.
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

    /**
     * Strategy for the BioactiveEntity enrichment.
     * This method can be overwritten to change how the BioactiveEntity is enriched.
     * @param bioactiveEntityToEnrich   The BioactiveEntity to be enriched.
     */
    @Override
    protected void processBioactiveEntity(BioactiveEntity bioactiveEntityToEnrich) {

        // bioactiveEntityToEnrich.getShortName();
        //bioactiveEntityToEnrich.getInteractorType();
        //bioactiveEntityToEnrich.getOrganism();
        //bioactiveEntityToEnrich.getAliases();
        //bioactiveEntityToEnrich.getXrefs();

        // FULL NAME
        if(bioactiveEntityToEnrich.getFullName() == null
                && bioactiveEntityFetched.getFullName() != null){
            bioactiveEntityToEnrich.setFullName(bioactiveEntityFetched.getFullName());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onFullNameUpdate(bioactiveEntityToEnrich , null);
        }

        // CHEBI IDENTIFIER
        if(bioactiveEntityToEnrich.getChebi() == null
                && bioactiveEntityFetched.getChebi() != null){
            bioactiveEntityToEnrich.setChebi(bioactiveEntityFetched.getChebi());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onChebiUpdate(bioactiveEntityToEnrich , null);
        }

        // INCHI Code
        if(bioactiveEntityToEnrich.getStandardInchi() == null
                && bioactiveEntityFetched.getStandardInchi() != null){
            bioactiveEntityToEnrich.setStandardInchi(bioactiveEntityFetched.getStandardInchi());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onStandardInchiUpdate(bioactiveEntityToEnrich , null);
        }

        // INCHI KEY
        if(bioactiveEntityToEnrich.getStandardInchiKey() == null
                && bioactiveEntityFetched.getStandardInchiKey() != null){
            bioactiveEntityToEnrich.setStandardInchiKey(bioactiveEntityFetched.getStandardInchiKey());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onStandardInchiKeyUpdate(bioactiveEntityToEnrich , null);
        }

        // SMILE
        if(bioactiveEntityToEnrich.getSmile() == null
                && bioactiveEntityFetched.getSmile() != null){
            bioactiveEntityToEnrich.setSmile(bioactiveEntityFetched.getSmile());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onSmileUpdate(bioactiveEntityToEnrich , null);
        }


    }
}
