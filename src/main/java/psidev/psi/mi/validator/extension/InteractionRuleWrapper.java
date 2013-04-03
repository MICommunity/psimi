package psidev.psi.mi.validator.extension;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.tools.ontology_manager.OntologyManager;

/**
 * This rule contains all rules that can check interaction objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public class InteractionRuleWrapper extends AbstractRuleWrapper<InteractionEvidence>{

    public InteractionRuleWrapper(OntologyManager ontologyManager) {
        super(ontologyManager, InteractionEvidence.class);
    }

    public String getId() {
        return "Rinteraction";
    }
}