package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.tools.ontology_manager.OntologyManager;

/**
 * This rule contains all rules that can check participant evidence objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public class ParticipantEvidenceRuleWrapper extends AbstractRuleWrapper<ParticipantEvidence>{

    public ParticipantEvidenceRuleWrapper(OntologyManager ontologyManager) {
        super(ontologyManager, ParticipantEvidence.class);
    }

    public String getId() {
        return "RparticipantEvidence";
    }
}