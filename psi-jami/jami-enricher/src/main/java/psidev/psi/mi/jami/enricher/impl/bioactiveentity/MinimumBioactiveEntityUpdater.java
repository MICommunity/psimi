package psidev.psi.mi.jami.enricher.impl.bioactiveentity;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * Provides minimum updating of the bioactiveEntity.
 * As an updater, values from the provided bioactiveEntity to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class MinimumBioactiveEntityUpdater
        extends AbstractBioactiveEntityEnricher{

    /**
     * A constructor which initiates with a fetcher.
     * @param fetcher   The fetcher to use to gather bioactive entity records.
     */
    public MinimumBioactiveEntityUpdater(BioactiveEntityFetcher fetcher) {
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
            bioactiveEntityToEnrich.setShortName(bioactiveEntityFetched.getShortName());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onFullNameUpdate(bioactiveEntityToEnrich , null);
        }

        // FULL NAME
        if(bioactiveEntityFetched.getFullName() != null
                && ! bioactiveEntityFetched.getFullName().equalsIgnoreCase(bioactiveEntityToEnrich.getFullName())){
            bioactiveEntityToEnrich.setFullName(bioactiveEntityFetched.getFullName());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onFullNameUpdate(bioactiveEntityToEnrich , null);
        }

        // CHEBI IDENTIFIER
        if(bioactiveEntityFetched.getChebi() != null
                && ! bioactiveEntityFetched.getChebi().equalsIgnoreCase(bioactiveEntityToEnrich.getChebi())){
            bioactiveEntityToEnrich.setChebi(bioactiveEntityFetched.getChebi());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onChebiUpdate(bioactiveEntityToEnrich , null);
        }

        // INCHI Code
        if( bioactiveEntityFetched.getStandardInchi() != null
                && ! bioactiveEntityFetched.getStandardInchi().equalsIgnoreCase(bioactiveEntityToEnrich.getStandardInchi())){

            bioactiveEntityToEnrich.setStandardInchi(bioactiveEntityFetched.getStandardInchi());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onStandardInchiUpdate(bioactiveEntityToEnrich , null);
        }

        // INCHI KEY
        if(bioactiveEntityFetched.getStandardInchiKey() != null
                && ! bioactiveEntityFetched.getStandardInchiKey().equalsIgnoreCase(bioactiveEntityToEnrich.getStandardInchiKey())){
            bioactiveEntityToEnrich.setStandardInchiKey(bioactiveEntityFetched.getStandardInchiKey());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onStandardInchiKeyUpdate(bioactiveEntityToEnrich , null);
        }

        // SMILE
        if(bioactiveEntityFetched.getSmile() != null
                && ! bioactiveEntityFetched.getSmile().equalsIgnoreCase(bioactiveEntityToEnrich.getSmile())){
            bioactiveEntityToEnrich.setSmile(bioactiveEntityFetched.getSmile());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onSmileUpdate(bioactiveEntityToEnrich , null);
        }
    }
}
