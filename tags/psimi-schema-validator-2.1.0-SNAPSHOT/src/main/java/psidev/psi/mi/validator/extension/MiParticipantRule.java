package psidev.psi.mi.validator.extension;

import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

/**
 * Abstract rule for Participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public abstract class MiParticipantRule extends ObjectRule<ParticipantEvidence> {

    public MiParticipantRule(OntologyManager ontologyManager) {
        super(ontologyManager);
    }

    @Override
    public boolean canCheck(Object t) {
        return t instanceof ParticipantEvidence;
    }
}