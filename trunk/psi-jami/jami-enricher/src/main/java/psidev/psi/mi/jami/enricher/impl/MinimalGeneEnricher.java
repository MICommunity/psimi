package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mock.MockOrganismFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.GeneEnricher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.GeneEnricherListener;
import psidev.psi.mi.jami.listener.InteractorChangeListener;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

import java.util.Collection;

/**
 * Minimal gene enricher
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 04/09/13
 */
public class MinimalGeneEnricher extends AbstractInteractorEnricher<Gene> implements GeneEnricher {

    private GeneFetcher fetcher = null;
    private GeneEnricherListener listener = null;

    private CvTermEnricher cvTermEnricher = null;
    private OrganismEnricher organismEnricher = null;
    private boolean hasMockOrganismFetcher = false;

    public MinimalGeneEnricher(GeneFetcher fetcher) {
        if (fetcher == null){
            throw new IllegalArgumentException("The fetcher is required and cannot be null");
        }
        this.fetcher = fetcher;
    }

    public GeneFetcher getGeneFetcher() {
        return fetcher;
    }

    public void setGeneEnricherListener(GeneEnricherListener listener) {
        this.listener = listener;
    }

    public GeneEnricherListener getGeneEnricherListener() {
        return listener;
    }

    /**
     * The organism enricher which will be used to collect data about the organisms.
     * @param organismEnricher  The organism enricher to be used.
     */
    public void setOrganismEnricher(OrganismEnricher organismEnricher) {
        this.organismEnricher = organismEnricher;
        // special treatment for Mock organism fetcher
        if (this.organismEnricher.getOrganismFetcher() instanceof MockOrganismFetcher){
            hasMockOrganismFetcher = true;
        }
    }
    /**
     * The Enricher to use on the protein's organism.
     * @return  The current organism enricher.
     */
    public OrganismEnricher getOrganismEnricher(){
        return organismEnricher;
    }


    public void setCvTermEnricher(CvTermEnricher cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    public CvTermEnricher getCvTermEnricher(){
        return cvTermEnricher;
    }

    @Override
    protected InteractorChangeListener<Gene> getListener() {
        return this.listener;
    }

    @Override
    protected Gene fetchEnrichedVersionFrom(Gene geneToEnrich) throws EnricherException {
        int taxid = -3;
        if(geneToEnrich.getOrganism() != null) {
            taxid = geneToEnrich.getOrganism().getTaxId();
        }

        Gene geneFetched = null;

        if(geneToEnrich.getEnsembl() != null)
            geneFetched = fetchGeneByIdentifier(geneToEnrich, geneToEnrich.getEnsembl() , taxid);

        if(geneFetched == null && geneToEnrich.getRefseq() != null)
            geneFetched = fetchGeneByIdentifier(geneToEnrich, geneToEnrich.getRefseq() , taxid);

        if(geneFetched == null && geneToEnrich.getEnsemblGenome() != null)
            geneFetched = fetchGeneByIdentifier(geneToEnrich, geneToEnrich.getEnsemblGenome() , taxid);

        // special treatment for organism enricher based with a Mock fetcher
        if (hasMockOrganismFetcher && geneFetched != null && geneFetched.getOrganism() != null){
            MockOrganismFetcher organismFetcher = (MockOrganismFetcher) getOrganismEnricher().getOrganismFetcher();
            try {
                if (organismFetcher.fetchByTaxID(geneFetched.getOrganism().getTaxId()) == null){
                    organismFetcher.addEntry(Integer.toString(geneFetched.getOrganism().getTaxId()), geneFetched.getOrganism());
                }
            } catch (BridgeFailedException e) {
                throw new EnricherException("Cannot add the organism " + geneFetched.getOrganism().getTaxId() +" to the organism mock fetcher", e);
            }
        }

        return geneFetched;
    }

    @Override
    protected void onEnrichedVersionNotFound(Gene objectToEnrich) throws EnricherException {
        getGeneEnricherListener().onEnrichmentComplete(
                objectToEnrich , EnrichmentStatus.FAILED ,
                "Could not fetch a gene with the provided identifier.");
    }

    @Override
    protected boolean isFullEnrichment() {
        return false;
    }

    @Override
    protected void onCompletedEnrichment(Gene objectToEnrich) {
        if(getGeneEnricherListener() != null)
            getGeneEnricherListener().onEnrichmentComplete(
                    objectToEnrich , EnrichmentStatus.SUCCESS , "The gene has been successfully enriched.");
    }

    @Override
    protected void onInteractorCheckFailure(Gene objectToEnrich, Gene fetchedObject) {
        if(getGeneEnricherListener() != null)
            getGeneEnricherListener().onEnrichmentComplete(
                    objectToEnrich , EnrichmentStatus.FAILED , "Cannot enrich the gene because the interactor type is not a gene type or we have a mismatch between the gene taxid to enrich and the fetched gene taxid.");
    }

    @Override
    protected boolean canEnrichInteractor(Gene entityToEnrich, Gene fetchedEntity) {
        // if the interactor type is not a valid bioactive entity interactor type, we cannot enrich
        if (entityToEnrich.getInteractorType() != null &&
                !CvTermUtils.isCvTerm(entityToEnrich.getInteractorType(), Gene.GENE_MI, Gene.GENE)
                && !CvTermUtils.isCvTerm(entityToEnrich.getInteractorType(), Interactor.UNKNOWN_INTERACTOR_MI, Interactor.UNKNOWN_INTERACTOR)){
            return false;
        }

        // if the organism is different, we cannot enrich
        if (entityToEnrich.getOrganism() != null && fetchedEntity.getOrganism() != null &&
                !OrganismTaxIdComparator.areEquals(entityToEnrich.getOrganism(), fetchedEntity.getOrganism())){
            return false;
        }

        return true;
    }

    private Gene fetchGeneByIdentifier(Gene gene, String identifier , int taxid) throws EnricherException {
        Collection<Gene> results;
        try {
            results = getGeneFetcher().fetchByIdentifier(identifier, taxid);
            if(results.size() == 1){
                return results.iterator().next();
            }
            else if (!results.isEmpty()){
                if (getGeneEnricherListener() != null){
                    getGeneEnricherListener().onEnrichmentError(gene, "The identifier " + identifier + " and taxid " + taxid + " can match " +
                    results.size() + " genes and it is not possible to enrich with multiple entries", new EnricherException("Multiple gene entries found for " + identifier + " and taxid " + taxid));
                }
                return null;
            }
            else{
                return null;
            }
        } catch (BridgeFailedException e) {
            int index = 0;
            while(index < getRetryCount()){
                try {
                    results = getGeneFetcher().fetchByIdentifier(identifier, taxid);
                    if(results.size() == 1){
                        return results.iterator().next();
                    }
                    else if (!results.isEmpty()){
                        if (getGeneEnricherListener() != null){
                            getGeneEnricherListener().onEnrichmentError(gene, "The identifier " + identifier + " and taxid " + taxid + " can match " +
                                    results.size() + " genes and it is not possible to enrich with multiple entries", new EnricherException("Multiple gene entries found for " + identifier + " and taxid " + taxid));
                        }
                        return null;
                    }
                    else{
                        return null;
                    }
                } catch (BridgeFailedException ee) {
                    ee.printStackTrace();
                    index++;
                }
            }
            throw new EnricherException("Retried "+getRetryCount()+" times to connect to the gene fetcher", e);
        }
    }
}
