package psidev.psi.mi.jami.enricher.impl.experiment.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Experiment;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public interface ExperimentEnricherListener
    extends EnricherListener{

    public void onExperimentEnriched(Experiment experiment , EnrichmentStatus status , String message);
}
