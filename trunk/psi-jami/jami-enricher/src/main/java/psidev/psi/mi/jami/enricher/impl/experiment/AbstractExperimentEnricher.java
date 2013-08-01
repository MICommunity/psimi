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
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public abstract class AbstractExperimentEnricher
        implements ExperimentEnricher{

    private PublicationEnricher publicationEnricher = null;
    private CvTermEnricher cvTermEnricher = null;
    private OrganismEnricher organismEnricher = null;
    private ExperimentEnricherListener listener = null;

    public AbstractExperimentEnricher(){}

    public void enrichExperiments(Collection<Experiment> experimentsToEnrich) throws EnricherException {
        for(Experiment experimentToEnrich : experimentsToEnrich){
            enrichExperiment(experimentToEnrich);
        }
    }

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

    protected abstract void processExperiment(Experiment experimentToEnrich) throws EnricherException;



    public ExperimentEnricherListener getExperimentEnricherListener() {
        return listener;
    }
    public void setExperimentEnricherListener(ExperimentEnricherListener listener) {
        this.listener = listener;
    }

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher) {
        this.cvTermEnricher = cvTermEnricher;
    }
    public CvTermEnricher getCvTermEnricher() {
        return cvTermEnricher;
    }

    public void getOrganismEnricher(OrganismEnricher organismEnricher) {
        this.organismEnricher = organismEnricher;
    }
    public OrganismEnricher getOrganismEnricher() {
        return organismEnricher;
    }

    public PublicationEnricher getPublicationEnricher() {
        return publicationEnricher;
    }
    public void setPublicationEnricher(PublicationEnricher publicationEnricher) {
        this.publicationEnricher = publicationEnricher;
    }
}
