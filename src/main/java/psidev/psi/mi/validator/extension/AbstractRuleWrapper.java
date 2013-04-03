package psidev.psi.mi.validator.extension;

import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Abstract class for RuleWrappers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public abstract class AbstractRuleWrapper<O> extends ObjectRule<O> {

    private Collection<ObjectRule<O>> rules;
    private Class<O> type;

    public AbstractRuleWrapper(OntologyManager ontologyManager, Class<O> type) {
        super(ontologyManager);
        this.rules = new ArrayList<ObjectRule<O>>();
        this.type = type;
    }

    public boolean canCheck( Object t ){
        return t != null && type.isAssignableFrom(t.getClass());
    }

    @Override
    public Collection<ValidatorMessage> check(O o) throws ValidatorException {
        if (rules.isEmpty()){
            return Collections.EMPTY_LIST;
        }

        Iterator<ObjectRule<O>> ruleIterator = rules.iterator();

        Collection<ValidatorMessage> messages = ruleIterator.next().check(o);

        while (ruleIterator.hasNext()){
            messages.addAll(ruleIterator.next().check(o));
        }

        return messages;
    }

    public void addRule(ObjectRule<O> rule){
        if (rule != null){
            this.rules.add(rule);
        }
    }

    public Class<O> getType() {
        return type;
    }
}
