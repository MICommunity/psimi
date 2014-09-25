package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.tools.ontology_manager.OntologyManager;

/**
 * This rule contains all rules that can check Alias objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public class CvRuleWrapper extends AbstractRuleWrapper<CvTerm>{

    public CvRuleWrapper(OntologyManager ontologyManager) {
        super(ontologyManager, CvTerm.class);
    }

    public String getId() {
        return "Rcv";
    }
}