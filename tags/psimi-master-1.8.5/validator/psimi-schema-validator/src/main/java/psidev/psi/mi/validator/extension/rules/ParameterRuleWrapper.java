package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.tools.ontology_manager.OntologyManager;

/**
 * This rule contains all rules that can check Alias objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public class ParameterRuleWrapper extends AbstractRuleWrapper<Parameter>{

    public ParameterRuleWrapper(OntologyManager ontologyManager) {
        super(ontologyManager, Parameter.class);
    }

    public String getId() {
        return "Rparameter";
    }
}