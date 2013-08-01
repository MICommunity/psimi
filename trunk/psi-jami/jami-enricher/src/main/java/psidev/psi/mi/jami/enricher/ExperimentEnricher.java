package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.experiment.listener.ExperimentEnricherListener;
import psidev.psi.mi.jami.model.Experiment;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public interface ExperimentEnricher {

    public void enrichExperiment(Experiment experimentToEnrich) throws EnricherException;
    public void enrichExperiments(Collection<Experiment> experimentsToEnrich) throws EnricherException;

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher);
    public CvTermEnricher getCvTermEnricher();

    public void getOrganismEnricher(OrganismEnricher organismEnricher);
    public OrganismEnricher getOrganismEnricher();

    public PublicationEnricher getPublicationEnricher();
    public void setPublicationEnricher(PublicationEnricher publicationEnricher);

    public ExperimentEnricherListener getExperimentEnricherListener();
    public void setExperimentEnricherListener(ExperimentEnricherListener listener);
}
