package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.jami.model.Xref;
import psidev.psi.tools.ontology_manager.OntologyManager;

/**
 * This rule contains all rules that can check xref objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public class XrefRuleWrapper extends AbstractRuleWrapper<Xref>{

    public XrefRuleWrapper(OntologyManager ontologyManager) {
        super(ontologyManager, Xref.class);
    }

    public String getId() {
        return "Rxref";
    }
}