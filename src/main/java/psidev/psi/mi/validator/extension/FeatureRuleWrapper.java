package psidev.psi.mi.validator.extension;

import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.tools.ontology_manager.OntologyManager;

/**
 * This rule contains all rules that can check Feature objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public class FeatureRuleWrapper extends AbstractRuleWrapper<FeatureEvidence>{

    public FeatureRuleWrapper(OntologyManager ontologyManager) {
        super(ontologyManager, FeatureEvidence.class);
    }

    public String getId() {
        return "Rfeature";
    }
}
