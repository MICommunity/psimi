package psidev.psi.mi.validator.extension;

import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.tools.ontology_manager.OntologyManager;

/**
 * This rule contains all rules that can check Polymer objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public class PolymerRuleWrapper extends AbstractRuleWrapper<Polymer>{

    public PolymerRuleWrapper(OntologyManager ontologyManager) {
        super(ontologyManager, Polymer.class);
    }

    public String getId() {
        return "Rpolymer";
    }
}
