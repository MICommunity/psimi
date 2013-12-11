package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.tools.ontology_manager.OntologyManager;

/**
 * This rule contains all rules that can check Alias objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public class InteractorRuleWrapper extends AbstractRuleWrapper<Interactor>{

    public InteractorRuleWrapper(OntologyManager ontologyManager) {
        super(ontologyManager, Interactor.class);
    }

    public String getId() {
        return "Rinteractor";
    }
}