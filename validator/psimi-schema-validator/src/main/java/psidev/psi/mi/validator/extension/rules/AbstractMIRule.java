package psidev.psi.mi.validator.extension.rules;

import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.Collection;

/**
 * Abstract class for MI rules
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public abstract class AbstractMIRule<O> extends ObjectRule<O> {

    private Class<O> type;

    public AbstractMIRule(OntologyManager ontologyManager, Class<O> type) {
        super(ontologyManager);
        this.type = type;
    }

    public boolean canCheck( Object t ){
        return t != null && type.isAssignableFrom(t.getClass());
    }

    @Override
    public abstract Collection<ValidatorMessage> check(O o) throws ValidatorException;

    public Class<O> getType() {
        return type;
    }
}
