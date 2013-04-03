package psidev.psi.mi.validator.extension;

import psidev.psi.mi.jami.model.Organism;
import psidev.psi.tools.ontology_manager.OntologyManager;

/**
 * This rule contains all rules that can check Organism objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public class OrganismRuleWrapper extends AbstractRuleWrapper<Organism>{

    public OrganismRuleWrapper(OntologyManager ontologyManager) {
        super(ontologyManager, Organism.class);
    }

    public String getId() {
        return "Rorganism";
    }
}