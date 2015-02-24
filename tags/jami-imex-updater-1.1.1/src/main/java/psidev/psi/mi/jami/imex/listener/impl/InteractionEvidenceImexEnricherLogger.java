package psidev.psi.mi.jami.imex.listener.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.impl.log.InteractionEvidenceEnricherLogger;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.imex.listener.InteractionImexEnricherListener;

import java.util.Collection;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/07/13
 */
public class InteractionEvidenceImexEnricherLogger
        extends InteractionEvidenceEnricherLogger implements InteractionImexEnricherListener {

    private static final Logger log = LoggerFactory.getLogger(InteractionEvidenceImexEnricherLogger.class.getName());

    public void onImexIdConflicts(InteractionEvidence interactionEvidence, Collection<Xref> conflictingXrefs) {
        log.error("The interaction " + interactionEvidence + " has " + conflictingXrefs.size() + " IMEx primary references and only one" +
                "is allowed");
    }

    public void onImexIdAssigned(InteractionEvidence interaction, String imex) {
        log.info("The IMEx primary reference " + imex + " has been added to the interaction " + interaction);
    }
}
