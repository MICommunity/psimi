package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.ExperimentEnricherListener;
import psidev.psi.mi.jami.model.Experiment;

import java.util.Collection;

/**
 * The experimentEnricher can enrich either a single experiment or a collection.
 * It has no fetcher and only enrich through subEnrichers.
 * Sub enrichers: CvTerm, Organism, Publication.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  31/07/13
 */
public interface ExperimentEnricher {

    /**
     * Enriches a collection of experiments.
     * @param experimentsToEnrich   The experiments which are to be enriched.
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichExperiments(Collection<Experiment> experimentsToEnrich) throws EnricherException;

    /**
     * Enrichment of a single experiment.
     * @param experimentToEnrich    The experiment which is to be enriched.
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichExperiment(Experiment experimentToEnrich) throws EnricherException;

    /**
     * Sets the subEnricher for CvTerms. Can be null.
     * @param cvTermEnricher    The CvTerm enricher to be used
     */
    public void setCvTermEnricher(CvTermEnricher cvTermEnricher);

    /**
     * Gets the subEnricher for CvTerms. Can be null.
     * @return  The CvTerm enricher which is being used.
     */
    public CvTermEnricher getCvTermEnricher();


    /**
     * Sets the subEnricher for organisms. Can be null.
     * @param organismEnricher The Organism enricher which is to be used.
     */
    public void setOrganismEnricher(OrganismEnricher organismEnricher);

    /**
     * Gets the subEnricher for organisms. Can be null.
     * @return  The organism enricher currently being used.
     */
    public OrganismEnricher getOrganismEnricher();

    /**
     * Gets the publications for organisms. Can be null.
     * @param publicationEnricher   The organism enricher is being used.
     */
    public void setPublicationEnricher(PublicationEnricher publicationEnricher);

    /**
     * Sets the subEnricher for publications. Can be null.
     * @return  The publications enricher which is being used.
     */
    public PublicationEnricher getPublicationEnricher();


    /**
     * Gets current ExperimentEnricherListener. Can be null.
     * @return      The listener which is currently being used.
     */
    public ExperimentEnricherListener getExperimentEnricherListener();

    /**
     * Sets the ExperimentEnricherListener. Can be null.
     * @param listener  The listener to be used.
     */
    public void setExperimentEnricherListener(ExperimentEnricherListener listener);
}
