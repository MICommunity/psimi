package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalOrganismEnricher;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;

/**
 * Provides full enrichment of a Organism.
 *
 * - enrich minimal properties of organism. See details in MinimalOrganismEnricher
 * - if cvTermEnricher is not null, will enrich cellType, tissue and compartment but does not override any
 * celltype, tissue, compartment with the one loaded with the fetched organism
 *
 * The organism fetcher is required for enriching organism
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public class FullOrganismEnricher extends MinimalOrganismEnricher {
    private CvTermEnricher<CvTerm> cvEnricher;

    public FullOrganismEnricher(OrganismFetcher organismFetcher) {
        super(organismFetcher);
    }

    @Override
    public CvTermEnricher<CvTerm> getCvTermEnricher() {
        return cvEnricher;
    }

    public void setCvTermEnricher(CvTermEnricher<CvTerm> cvEnricher) {
        this.cvEnricher = cvEnricher;
    }

    @Override
    protected void processOtherProperties(Organism organismToEnrich, Organism organismFetched) throws EnricherException {
        processAliases(organismToEnrich, organismFetched);

        processCellType(organismToEnrich, organismFetched);
        processTissue(organismToEnrich, organismFetched);
        processCompartment(organismToEnrich, organismFetched);
    }

    protected void processAliases(Organism organismToEnrich, Organism organismFetched) {
        EnricherUtils.mergeAliases(organismToEnrich, organismToEnrich.getAliases(), organismFetched.getAliases(), false, getOrganismEnricherListener());
    }

    protected void processCellType(Organism entityToEnrich, Organism fetched) throws EnricherException {
        if (entityToEnrich.getCellType() == null && fetched.getCellType() != null){
            entityToEnrich.setCellType(fetched.getCellType());
            if (getOrganismEnricherListener() != null){
                getOrganismEnricherListener().onCellTypeUpdate(entityToEnrich, null);
            }
        }
        if (getCvTermEnricher() != null && entityToEnrich.getCellType() != null){
            getCvTermEnricher().enrich(entityToEnrich.getCellType());
        }
    }

    protected void processTissue(Organism entityToEnrich, Organism fetched) throws EnricherException {
        if (entityToEnrich.getTissue() == null && fetched.getTissue() != null){
            entityToEnrich.setTissue(fetched.getTissue());
            if (getOrganismEnricherListener() != null){
                getOrganismEnricherListener().onTissueUpdate(entityToEnrich, null);
            }
        }
        if (getCvTermEnricher() != null && entityToEnrich.getTissue() != null){
            getCvTermEnricher().enrich(entityToEnrich.getTissue());
        }
    }

    protected void processCompartment(Organism entityToEnrich, Organism fetched) throws EnricherException {
        if (entityToEnrich.getCompartment() == null && fetched.getCompartment() != null){
            entityToEnrich.setCompartment(fetched.getCompartment());
            if (getOrganismEnricherListener() != null){
                getOrganismEnricherListener().onCompartmentUpdate(entityToEnrich, null);
            }
        }
        if (getCvTermEnricher() != null && entityToEnrich.getCompartment() != null){
            getCvTermEnricher().enrich(entityToEnrich.getCompartment());
        }
    }
}
