package psidev.psi.mi.jami.enricher.impl.full;


import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalOrganismUpdater;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Provides maximum updating of the organism.
 * Will update all aspects covered by the minimum updater as well as updating the Aliases.
 * As an updater, values from the provided CvTerm to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  24/05/13
 */
public class FullOrganismUpdater extends MinimalOrganismUpdater {

    private CvTermEnricher<CvTerm> cvEnricher;

    public FullOrganismUpdater(OrganismFetcher organismFetcher) {
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
        if (!DefaultCvTermComparator.areEquals(entityToEnrich.getCellType(),fetched.getCellType())){
            CvTerm old = entityToEnrich.getCellType();
            entityToEnrich.setCellType(fetched.getCellType());
            if (getOrganismEnricherListener() != null){
                getOrganismEnricherListener().onCellTypeUpdate(entityToEnrich, old);
            }
        }
        else if (getCvEnricher() != null
                && entityToEnrich.getCellType() != fetched.getCellType()){
            getCvEnricher().enrich(entityToEnrich.getCellType(), fetched.getCellType());
        }
        if (getCvEnricher() != null && entityToEnrich.getCellType() != null){
            getCvEnricher().enrich(entityToEnrich.getCellType());
        }
    }

    protected void processTissue(Organism entityToEnrich, Organism fetched) throws EnricherException {
        if (!DefaultCvTermComparator.areEquals(entityToEnrich.getTissue(), fetched.getTissue())){
            CvTerm old = entityToEnrich.getTissue();
            entityToEnrich.setTissue(fetched.getTissue());
            if (getOrganismEnricherListener() != null){
                getOrganismEnricherListener().onTissueUpdate(entityToEnrich, old);
            }
        }
        else if (getCvEnricher() != null
                && entityToEnrich.getTissue() != fetched.getTissue()){
            getCvEnricher().enrich(entityToEnrich.getTissue(), fetched.getTissue());
        }
        if (getCvEnricher() != null && entityToEnrich.getTissue() != null){
            getCvEnricher().enrich(entityToEnrich.getTissue());
        }
    }

    protected void processCompartment(Organism entityToEnrich, Organism fetched) throws EnricherException {
        if (!DefaultCvTermComparator.areEquals(entityToEnrich.getCompartment(),fetched.getCompartment())){
            CvTerm old = entityToEnrich.getCompartment();
            entityToEnrich.setCompartment(fetched.getCompartment());
            if (getOrganismEnricherListener() != null){
                getOrganismEnricherListener().onCompartmentUpdate(entityToEnrich, old);
            }
        }
        else if (getCvEnricher() != null
                && entityToEnrich.getCompartment() != fetched.getCompartment()){
            getCvEnricher().enrich(entityToEnrich.getCompartment(), fetched.getCompartment());
        }
        if (getCvEnricher() != null && entityToEnrich.getCompartment() != null){
            getCvEnricher().enrich(entityToEnrich.getCompartment());
        }
    }
}
