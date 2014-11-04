package psidev.psi.mi.jami.imex.listener.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.impl.log.ExperimentEnricherLogger;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.imex.listener.ExperimentImexEnricherListener;

import java.util.Collection;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 */
public class ExperimentImexEnricherLogger extends ExperimentEnricherLogger
        implements ExperimentImexEnricherListener {

    private static final Logger log = LoggerFactory.getLogger(ExperimentImexEnricherLogger.class.getName());

    public void onImexIdConflicts(Experiment originalExperiment, Collection<Xref> conflictingXrefs) {
        log.error("The experiment "+originalExperiment+" has "+conflictingXrefs.size()+" IMEx primary references and only one" +
                "is allowed");
    }

    public void onImexIdAssigned(Experiment experiment, String imex) {
        log.info("The IMEx primary reference "+imex+" has been added to the experiment "+experiment);
    }
}
