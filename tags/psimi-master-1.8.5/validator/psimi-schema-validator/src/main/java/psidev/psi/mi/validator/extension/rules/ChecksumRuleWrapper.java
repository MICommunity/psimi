package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.tools.ontology_manager.OntologyManager;

/**
 * This rule contains all rules that can check checksum objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public class ChecksumRuleWrapper extends AbstractRuleWrapper<Checksum>{

    public ChecksumRuleWrapper(OntologyManager ontologyManager) {
        super(ontologyManager, Checksum.class);
    }

    public String getId() {
        return "Rchecksum";
    }
}