package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.listener.ExperimentEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;

/**
 * The experimentEnricher can enrich either a single experiment or a collection.
 * It has no fetcher and only enrich through subEnrichers.
 * Sub enrichers: CvTerm, Organism, Publication.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  31/07/13
 */
public interface ExperimentEnricher extends MIEnricher<Experiment>{

    /**
     * Gets the subEnricher for Organisms. Can be null.
     * @return  The Organism enricher which is being used.
     */
    public OrganismEnricher getOrganismEnricher();

    /**
     * Gets the subEnricher for CvTerms. Can be null.
     * @return  The CvTerm enricher which is being used.
     */
    public CvTermEnricher<CvTerm> getCvTermEnricher();

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

    public void setOrganismEnricher(OrganismEnricher organismEnricher);

    public void setCvTermEnricher(CvTermEnricher<CvTerm> cvEnricher);

    public void setPublicationEnricher(PublicationEnricher publicationEnricher);

    public void setExperimentEnricherListener(ExperimentEnricherListener listener);
}
