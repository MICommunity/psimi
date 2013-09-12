package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * Provides minimum updating of the bioactiveEntity.
 * As an updater, values from the provided bioactiveEntity to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class MinimalBioactiveEntityUpdater
        extends AbstractBioactiveEntityEnricher{

    /**
     * A constructor which initiates with a fetcher.
     * @param fetcher   The fetcher to use to gather bioactive entity records.
     */
    public MinimalBioactiveEntityUpdater(BioactiveEntityFetcher fetcher) {
        super(fetcher);
    }

    /**
     * Strategy for the BioactiveEntity enrichment.
     * This method can be overwritten to change how the BioactiveEntity is enriched.
     * @param bioactiveEntityToEnrich   The BioactiveEntity to be enriched.
     */
    @Override
    protected void processBioactiveEntity(BioactiveEntity bioactiveEntityToEnrich) {

        // bioactiveEntityToEnrich.getInteractorType();
        // bioactiveEntityToEnrich.getOrganism();
        // bioactiveEntityToEnrich.getAliases();
        // bioactiveEntityToEnrich.getXrefs();


        // SHORT NAME
        if(bioactiveEntityFetched.getShortName() != null
                && ! bioactiveEntityFetched.getShortName().equalsIgnoreCase(bioactiveEntityToEnrich.getShortName())){
            String oldValue = bioactiveEntityToEnrich.getShortName();
            bioactiveEntityToEnrich.setShortName(bioactiveEntityFetched.getShortName());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onShortNameUpdate(bioactiveEntityToEnrich , oldValue);
        }

        // FULL NAME
        if(bioactiveEntityFetched.getFullName() != null
                && ! bioactiveEntityFetched.getFullName().equalsIgnoreCase(bioactiveEntityToEnrich.getFullName())){
            String oldValue = bioactiveEntityToEnrich.getFullName();
            bioactiveEntityToEnrich.setFullName(bioactiveEntityFetched.getFullName());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onFullNameUpdate(bioactiveEntityToEnrich , oldValue);
        }

        // CHEBI IDENTIFIER
        if(bioactiveEntityFetched.getChebi() != null
                && ! bioactiveEntityFetched.getChebi().equalsIgnoreCase(bioactiveEntityToEnrich.getChebi())){
            String oldValue = bioactiveEntityToEnrich.getChebi();
            bioactiveEntityToEnrich.setChebi(bioactiveEntityFetched.getChebi());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onChebiUpdate(bioactiveEntityToEnrich , oldValue);
        }

        // INCHI Code & KEY
        if( bioactiveEntityFetched.getStandardInchi() != null
                && ! bioactiveEntityFetched.getStandardInchi().equalsIgnoreCase(bioactiveEntityToEnrich.getStandardInchi())
                && bioactiveEntityFetched.getStandardInchiKey() != null
                && ! bioactiveEntityFetched.getStandardInchiKey().equalsIgnoreCase(bioactiveEntityToEnrich.getStandardInchiKey())){

            String oldValue = bioactiveEntityToEnrich.getStandardInchi();
            bioactiveEntityToEnrich.setStandardInchi(bioactiveEntityFetched.getStandardInchi());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onStandardInchiUpdate(bioactiveEntityToEnrich , oldValue);

            oldValue = bioactiveEntityToEnrich.getStandardInchiKey();
            bioactiveEntityToEnrich.setStandardInchiKey(bioactiveEntityFetched.getStandardInchiKey());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onStandardInchiKeyUpdate(bioactiveEntityToEnrich , oldValue);
        }

        // SMILE
        if(bioactiveEntityFetched.getSmile() != null
                && ! bioactiveEntityFetched.getSmile().equalsIgnoreCase(bioactiveEntityToEnrich.getSmile())){
            String oldValue = bioactiveEntityToEnrich.getSmile();
            bioactiveEntityToEnrich.setSmile(bioactiveEntityFetched.getSmile());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onSmileUpdate(bioactiveEntityToEnrich , oldValue);
        }
    }
}
