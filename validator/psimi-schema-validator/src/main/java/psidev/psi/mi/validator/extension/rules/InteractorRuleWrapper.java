package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * This rule contains all rules that can check Alias objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public class InteractorRuleWrapper extends AbstractRuleWrapper<Interactor>{

    public InteractorRuleWrapper(OntologyManager ontologyManager) {
        super(ontologyManager, Interactor.class);
    }

    @Override
    public Collection<ValidatorMessage> check(Interactor o) throws ValidatorException {
        if (getRules().isEmpty()){
            return Collections.EMPTY_LIST;
        }

        Iterator<ObjectRule<Interactor>> ruleIterator = getRules().iterator();

        if (ruleIterator.hasNext()){
            Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>(getRules().size());

            while (ruleIterator.hasNext()){
                ObjectRule<Interactor> interactorRule = ruleIterator.next();
                if (interactorRule.canCheck(o)){
                    messages.addAll(interactorRule.check(o));
                }
            }

            return messages;
        }
        return Collections.EMPTY_LIST;
    }

    public String getId() {
        return "Rinteractor";
    }
}