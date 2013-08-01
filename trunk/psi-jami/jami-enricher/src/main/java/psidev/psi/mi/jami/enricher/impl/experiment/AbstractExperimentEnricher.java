package psidev.psi.mi.jami.enricher.impl.experiment;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.ExperimentEnricher;
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
public class AbstractExperimentEnricher
        implements ExperimentEnricher{

    private PublicationEnricher publicationEnricher = null;
    private CvTermEnricher cvTermEnricher = null;
    private ExperimentEnricherListener listener = null;


    public void enrichExperiment(Experiment experimentToEnrich) throws EnricherException {
        if( experimentToEnrich == null )
            throw new IllegalArgumentException("Null experiment can not be enriched.");

        processExperiment(experimentToEnrich);

        if( getPublicationEnricher() != null
                && experimentToEnrich.getPublication() != null )
            getPublicationEnricher().enrichPublication(experimentToEnrich.getPublication());

        if( getCvTermEnricher() != null &&
                experimentToEnrich.getInteractionDetectionMethod() != null )
            getCvTermEnricher().enrichCvTerm(experimentToEnrich.getInteractionDetectionMethod());

        if( getExperimentListener() != null )
            getExperimentListener().onExperimentEnriched(experimentToEnrich , EnrichmentStatus.SUCCESS , null);
    }

    public void processExperiment(Experiment experimentToEnrich){
    }

    public void enrichExperiments(Collection<Experiment> experimentsToEnrich) throws EnricherException {
        for(Experiment experimentToEnrich : experimentsToEnrich){
            enrichExperiment(experimentToEnrich);
        }
    }

    public ExperimentEnricherListener getExperimentListener() {
        return listener;
    }
    public void getExperimentListener(ExperimentEnricherListener listener) {
        this.listener = listener;
    }

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher) {
        this.cvTermEnricher = cvTermEnricher;
    }
    public CvTermEnricher getCvTermEnricher() {
        return cvTermEnricher;
    }

    public PublicationEnricher getPublicationEnricher() {
        return publicationEnricher;
    }
    public void setPublicationEnricher(PublicationEnricher publicationEnricher) {
        this.publicationEnricher = publicationEnricher;
    }
}
