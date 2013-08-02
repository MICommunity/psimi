package psidev.psi.mi.jami.enricher.impl.experiment;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.ExperimentEnricher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.PublicationEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.experiment.listener.ExperimentEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Experiment;

import java.util.Collection;

/**
 * The experiment enricher has no fetcher and can enrich either a single experiment of a collection.
 * It has subEnrichers for CvTerms, Organisms, and publications.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  31/07/13
 */
public abstract class AbstractExperimentEnricher
        implements ExperimentEnricher{

    private PublicationEnricher publicationEnricher = null;
    private CvTermEnricher cvTermEnricher = null;
    private OrganismEnricher organismEnricher = null;
    private ExperimentEnricherListener listener = null;

    public AbstractExperimentEnricher(){}

    /**
     * Enriches a collection of experiments.
     * @param experimentsToEnrich   The experiments which are to be enriched.
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichExperiments(Collection<Experiment> experimentsToEnrich) throws EnricherException {
        for(Experiment experimentToEnrich : experimentsToEnrich){
            enrichExperiment(experimentToEnrich);
        }
    }

    /**
     * Enrichment of a single experiment.
     * @param experimentToEnrich    The experiment which is to be enriched.
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichExperiment(Experiment experimentToEnrich) throws EnricherException {
        if( experimentToEnrich == null )
            throw new IllegalArgumentException("Attempted to enrich null experiment.");

        if( getOrganismEnricher() != null
                && experimentToEnrich.getHostOrganism() != null)
            getOrganismEnricher().enrichOrganism(experimentToEnrich.getHostOrganism());

        if( getPublicationEnricher() != null
                && experimentToEnrich.getPublication() != null )
            getPublicationEnricher().enrichPublication(experimentToEnrich.getPublication());

        if( getCvTermEnricher() != null
                && experimentToEnrich.getInteractionDetectionMethod() != null )
            getCvTermEnricher().enrichCvTerm(experimentToEnrich.getInteractionDetectionMethod());

        processExperiment(experimentToEnrich);

        if( getExperimentEnricherListener() != null )
            getExperimentEnricherListener().onExperimentEnriched(experimentToEnrich , EnrichmentStatus.SUCCESS , null);
    }

    /**
     * Processes the specific details of the experiment which are delegated to a subEnricher.
     * @param experimentToEnrich    The experiment which is to be enriched
     * @throws EnricherException    Thrown if problems are encountered in a fetcher.
     */
    protected abstract void processExperiment(Experiment experimentToEnrich) throws EnricherException;

    /**
     * Sets the subEnricher for CvTerms. Can be null.
     * @param cvTermEnricher    The CvTerm enricher to be used
     */
    public void setCvTermEnricher(CvTermEnricher cvTermEnricher) {
        this.cvTermEnricher = cvTermEnricher;
    }
    /**
     * Gets the subEnricher for CvTerms. Can be null.
     * @return  The CvTerm enricher which is being used.
     */
    public CvTermEnricher getCvTermEnricher() {
        return cvTermEnricher;
    }

    /**
     * Sets the subEnricher for organisms. Can be null.
     * @param organismEnricher The Organism enricher which is to be used.
     */
    public void setOrganismEnricher(OrganismEnricher organismEnricher) {
        this.organismEnricher = organismEnricher;
    }
    /**
     * Gets the subEnricher for organisms. Can be null.
     * @return  The organism enricher currently being used.
     */
    public OrganismEnricher getOrganismEnricher() {
        return organismEnricher;
    }

    /**
     * Sets the subEnricher for publications. Can be null.
     * @return  The publications enricher which is being used.
     */
    public PublicationEnricher getPublicationEnricher() {
        return publicationEnricher;
    }
    /**
     * Gets the publications for organisms. Can be null.
     * @param publicationEnricher   The organism enricher is being used.
     */
    public void setPublicationEnricher(PublicationEnricher publicationEnricher) {
        this.publicationEnricher = publicationEnricher;
    }

    /**
     * Gets current ExperimentEnricherListener. Can be null.
     * @return      The listener which is currently beign used.
     */
    public ExperimentEnricherListener getExperimentEnricherListener() {
        return listener;
    }

    /**
     * Sets the ExperimentEnricherListener. Can be null.
     * @param listener  The listener to be used.
     */
    public void setExperimentEnricherListener(ExperimentEnricherListener listener) {
        this.listener = listener;
    }
}
