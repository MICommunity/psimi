package psidev.psi.mi.jami.enricher.listener.experiment;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.model.Experiment;

/**
 * //An extension of the ExperimentChangeListener
 * //with specific methods related to the process of enriching.
 * Each method will be fired after the change has been made to the experiment.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  31/07/13
 */
public interface ExperimentEnricherListener
        extends EnricherListener<Experiment>{
}
