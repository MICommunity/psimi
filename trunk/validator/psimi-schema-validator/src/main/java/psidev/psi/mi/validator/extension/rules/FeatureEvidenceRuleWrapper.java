package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.tools.ontology_manager.OntologyManager;

/**
 * This rule contains all rules that can check Alias objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public class FeatureEvidenceRuleWrapper extends AbstractRuleWrapper<FeatureEvidence>{

    public FeatureEvidenceRuleWrapper(OntologyManager ontologyManager) {
        super(ontologyManager, FeatureEvidence.class);
    }

    public String getId() {
        return "Rfeature";
    }
}