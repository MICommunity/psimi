package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.jami.model.Publication;
import psidev.psi.tools.ontology_manager.OntologyManager;

/**
 * This rule contains all rules that can check publication objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public class PublicationRuleWrapper extends AbstractRuleWrapper<Publication>{

    public PublicationRuleWrapper(OntologyManager ontologyManager) {
        super(ontologyManager, Publication.class);
    }

    public String getId() {
        return "Rpublication";
    }
}