package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;
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
    private CvTermEnricher<CvTerm> cvEnricher;

    public FullOrganismEnricher(OrganismFetcher organismFetcher) {
        super(organismFetcher);
    }

    public CvTermEnricher<CvTerm> getCvEnricher() {
        return cvEnricher;
    }

    public void setCvEnricher(CvTermEnricher<CvTerm> cvEnricher) {
        this.cvEnricher = cvEnricher;
    }

    @Override
    protected void processOtherProperties(Organism organismToEnrich, Organism organismFetched) throws EnricherException {
        EnricherUtils.mergeAliases(organismToEnrich, organismToEnrich.getAliases(), organismFetched.getAliases(), false, getOrganismEnricherListener());

        processCellType(organismToEnrich, organismFetched);
        processTissue(organismToEnrich, organismFetched);
        processCompartment(organismToEnrich, organismFetched);
    }

    protected void processCellType(Organism entityToEnrich, Organism fetched) throws EnricherException {
        if (entityToEnrich.getCellType() == null && fetched.getCellType() != null){
            entityToEnrich.setCellType(fetched.getCellType());
            if (getOrganismEnricherListener() != null){
                getOrganismEnricherListener().onCellTypeUpdate(entityToEnrich, null);
            }
        }
        if (getCvEnricher() != null && entityToEnrich.getCellType() != null){
            getCvEnricher().enrich(entityToEnrich.getCellType());
        }
    }

    protected void processTissue(Organism entityToEnrich, Organism fetched) throws EnricherException {
        if (entityToEnrich.getTissue() == null && fetched.getTissue() != null){
            entityToEnrich.setTissue(fetched.getTissue());
            if (getOrganismEnricherListener() != null){
                getOrganismEnricherListener().onTissueUpdate(entityToEnrich, null);
            }
        }
        if (getCvEnricher() != null && entityToEnrich.getTissue() != null){
            getCvEnricher().enrich(entityToEnrich.getTissue());
        }
    }

    protected void processCompartment(Organism entityToEnrich, Organism fetched) throws EnricherException {
        if (entityToEnrich.getCompartment() == null && fetched.getCompartment() != null){
            entityToEnrich.setCompartment(fetched.getCompartment());
            if (getOrganismEnricherListener() != null){
                getOrganismEnricherListener().onCompartmentUpdate(entityToEnrich, null);
            }
        }
        if (getCvEnricher() != null && entityToEnrich.getCompartment() != null){
            getCvEnricher().enrich(entityToEnrich.getCompartment());
        }
    }
}
