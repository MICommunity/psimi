package psidev.psi.mi.validator.extension;

import psidev.psi.mi.jami.datasource.MolecularInteractionFileDataSource;
import psidev.psi.tools.ontology_manager.OntologyManager;

/**
 * This rule contains all rules that can check molecular interaction file data sources objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public class MolecularInteractionFileDataSourceRuleWrapper extends AbstractRuleWrapper<MolecularInteractionFileDataSource> {

    public MolecularInteractionFileDataSourceRuleWrapper(OntologyManager ontologyManager) {
        super(ontologyManager, MolecularInteractionFileDataSource.class);
    }

    public String getId() {
        return "RfileDataSource";
    }
}