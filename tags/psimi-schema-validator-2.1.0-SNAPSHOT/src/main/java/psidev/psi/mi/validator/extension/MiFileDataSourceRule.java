package psidev.psi.mi.validator.extension;

import psidev.psi.mi.jami.datasource.MolecularInteractionFileDataSource;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

/**
 * Abstract class for file data sources rules
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public abstract class MiFileDataSourceRule extends ObjectRule<MolecularInteractionFileDataSource> {

    public MiFileDataSourceRule(OntologyManager ontologyManager) {
        super(ontologyManager);
    }

    @Override
    public boolean canCheck(Object t) {
        return t instanceof MolecularInteractionFileDataSource;
    }
}