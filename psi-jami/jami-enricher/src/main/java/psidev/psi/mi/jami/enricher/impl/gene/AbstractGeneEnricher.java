package psidev.psi.mi.jami.enricher.impl.gene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mock.MockOrganismFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.GeneEnricher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.gene.GeneEnricherListener;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.Collection;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/09/13
 */
public abstract class AbstractGeneEnricher
        implements GeneEnricher{

    protected static final Logger log = LoggerFactory.getLogger(AbstractGeneEnricher.class.getName());

    public static final int RETRY_COUNT = 5;

    private GeneFetcher fetcher = null;
    private GeneEnricherListener listener = null;

    private MockOrganismFetcher mockFetcher = new MockOrganismFetcher();
    private CvTermEnricher cvTermEnricher = null;
    private OrganismEnricher organismEnricher = null;

    protected Gene geneFetched = null;

    public AbstractGeneEnricher(GeneFetcher fetcher){
        setGeneFetcher(fetcher);
    }

    public void enrichGene(Gene geneToEnrich) throws EnricherException {
        if(geneToEnrich == null)
            throw new IllegalArgumentException("Can not enrich null Gene.");

        geneFetched = fetchGene(geneToEnrich);
        if(geneFetched == null){
            if(getGeneEnricherListener() != null)
                getGeneEnricherListener().onEnrichmentComplete(geneToEnrich , EnrichmentStatus.FAILED , "Could not fetch a gene.");
            return;
        }

        // == Check For Conflicts ======================================================
        if (! isGeneConflictFree(geneToEnrich) ) return;

        // == ORGANISM =================================================================
        if(getOrganismEnricher() != null ){
            if(geneFetched.getOrganism() != null){
                OrganismFetcher originalOrganismFetcher = getOrganismEnricher().getOrganismFetcher();
                getOrganismEnricher().setOrganismFetcher(mockFetcher);
                mockFetcher.addEntry(
                        Integer.toString(geneToEnrich.getOrganism().getTaxId()),
                        geneFetched.getOrganism());
                getOrganismEnricher().enrichOrganism(geneToEnrich.getOrganism());
                getOrganismEnricher().setOrganismFetcher(originalOrganismFetcher);
            } else {
                getOrganismEnricher().enrichOrganism(geneToEnrich.getOrganism());
            }
        }

        // == InteractorType =================================================
        if(!geneToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(Gene.GENE_MI)){
            if(geneToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(
                    Interactor.UNKNOWN_INTERACTOR_MI)){
                geneToEnrich.setInteractorType(CvTermUtils.createProteinInteractorType());
                if(getGeneEnricherListener() != null)
                    getGeneEnricherListener().onAddedInteractorType(geneToEnrich);
            }
        }

        if(geneToEnrich.getInteractorType() != null){
            getCvTermEnricher().enrichCvTerm(geneToEnrich.getInteractorType());
        }

        // == ENRICH THE GENE ===================

        processGene(geneToEnrich);

        if(getGeneEnricherListener() != null)
            getGeneEnricherListener().onEnrichmentComplete(
                    geneToEnrich , EnrichmentStatus.SUCCESS , null);

    }

    public void enrichGenes(Collection<Gene> genesToEnrich) throws EnricherException {
        for(Gene geneToEnrich : genesToEnrich){
            enrichGene(geneToEnrich);
        }
    }



    /**
     * Checks for fatal conflicts between the ToEnrich and the fetched form.
     * The interactor type is the first comparison.
     * It may either be the psi-mi term for 'gene' or 'unknown interactor'.
     * If anything other than these terms are found, the enrichment stops and returns false.
     * <p>
     * The organisms are then compared. If any are null, they are marked as unknown.
     * If the ToEnrich has a null organism, a new, 'unknown' organism is added.
     * Otherwise the organisms taxids' are compared, returning false if they do not match.
     * <p>
     * In both cases, if false is returned, an onProteinEnriched event is fired with a 'failed' status.
     *
     * @param geneToEnrich   The gene which is being enriched.
     * @return                  Whether there were any conflicts which would halt enrichment
     */
    protected boolean isGeneConflictFree(Gene geneToEnrich){
        boolean conflictFree = true;
        String existMsg = "";

        //InteractorType
        if(! CvTermUtils.isCvTerm(geneToEnrich.getInteractorType() , Gene.GENE_MI , null)
                && ! CvTermUtils.isCvTerm(
                    geneToEnrich.getInteractorType() , Interactor.UNKNOWN_INTERACTOR_MI , null)){

            existMsg = "Conflict in interactorType. " +
                    "Found " + geneToEnrich.getInteractorType().getShortName() + " " +
                    "(psi-mi id:" + geneToEnrich.getInteractorType().getMIIdentifier() + "). ";
            conflictFree = false;
        }

        //Organism
        if(geneFetched.getOrganism() == null) geneFetched.setOrganism(new DefaultOrganism(-3));
        if(geneToEnrich.getOrganism() == null) geneToEnrich.setOrganism(new DefaultOrganism(-3));

        if(geneToEnrich.getOrganism().getTaxId() > 0
                && geneToEnrich.getOrganism().getTaxId() != geneFetched.getOrganism().getTaxId()){
            existMsg = existMsg +
                    "Conflict in organism. " +
                    "Fetched taxid " + geneFetched.getOrganism().getTaxId() + " " +
                    "but was enriching taxid " + geneToEnrich.getOrganism().getTaxId() + ".";
            conflictFree = false;
        }

        if(! conflictFree)
            if(getGeneEnricherListener() != null) getGeneEnricherListener().onEnrichmentComplete(
                    geneToEnrich , EnrichmentStatus.FAILED ,  existMsg);

        return conflictFree;
    }






    abstract void processGene(Gene geneToEnrich);

    private Gene fetchGene(Gene geneToEnrich) throws EnricherException {
        if(getGeneFetcher() == null)
            throw new IllegalStateException("Can not fetch with null gene fetcher.");

        Collection<Gene> results = Collections.emptyList();

        if(geneToEnrich.getEnsembl() != null){
            try {
                results = getGeneFetcher().getGenesByEnsemblIdentifier(geneToEnrich.getEnsembl());
                if(!results.isEmpty())
                    if(results.size() == 1)
                        return results.iterator().next();
            } catch (BridgeFailedException e) {
                int index = 0;
                while(index < RETRY_COUNT){
                    try {
                        results = getGeneFetcher().getGenesByEnsemblIdentifier(geneToEnrich.getEnsembl());
                        if(!results.isEmpty())
                            if(results.size() == 1)
                                return results.iterator().next();
                    } catch (BridgeFailedException ee) {
                        ee.printStackTrace();
                    }
                    index++;
                }
                throw new EnricherException("Retried "+RETRY_COUNT+" times", e);
            }
        }

        /*
        if(geneToEnrich.getRefseq() != null){
            try {
                results = Collections.emptyList();
                if(!results.isEmpty())
                    if(results.size() == 1)
                        return results.iterator().next();
            } catch (BridgeFailedException e) {
                int index = 0;
                while(index < RETRY_COUNT){
                    try {
                        results = Collections.emptyList();
                        if(!results.isEmpty())
                            if(results.size() == 1)
                                return results.iterator().next();
                    } catch (BridgeFailedException ee) {
                        ee.printStackTrace();
                    }
                    index++;
                }
                throw new EnricherException("Retried "+RETRY_COUNT+" times", e);
            }
        } */

        return null;
    }


    public void setGeneFetcher(GeneFetcher fetcher) {
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
}
