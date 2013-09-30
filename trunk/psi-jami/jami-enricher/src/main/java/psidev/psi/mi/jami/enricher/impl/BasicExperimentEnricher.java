package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.ExperimentEnricher;
import psidev.psi.mi.jami.enricher.PublicationEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.ExperimentEnricherListener;
import psidev.psi.mi.jami.model.Experiment;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class BasicExperimentEnricher implements ExperimentEnricher{

    private PublicationEnricher publicationEnricher = null;
    private CvTermEnricher cvTermEnricher = null;
    private ExperimentEnricherListener listener = null;

    public BasicExperimentEnricher(){

    }

    /**
     * Enrichment of a single experiment.
     * @param experimentToEnrich    The experiment which is to be enriched.
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrich(Experiment experimentToEnrich) throws EnricherException {
        if( experimentToEnrich == null )
            throw new IllegalArgumentException("Attempted to enrich null experiment.");

        if( getPublicationEnricher() != null
                && experimentToEnrich.getPublication() != null )
            getPublicationEnricher().enrich(experimentToEnrich.getPublication());

        if( getCvTermEnricher() != null
                && experimentToEnrich.getInteractionDetectionMethod() != null )
            getCvTermEnricher().enrich(experimentToEnrich.getInteractionDetectionMethod());

        if( getExperimentEnricherListener() != null )
            getExperimentEnricherListener().onEnrichmentComplete(experimentToEnrich , EnrichmentStatus.SUCCESS , "The experiment has been successfully enriched.");
    }

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
