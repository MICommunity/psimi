package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is for displaying a validator FATAL message when an exception is thrown by the psi xml parser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23-Sep-2010</pre>
 */

public class PsimiXmlSchemaRule extends ObjectRule<Object>{
    public PsimiXmlSchemaRule(OntologyManager ontologyManager) {
        super(ontologyManager);
    }

    @Override
    public boolean canCheck(Object t) {
        return true;
    }

    @Override
    public Collection<ValidatorMessage> check(Object o) throws ValidatorException {
        return new ArrayList<ValidatorMessage>();
    }

    public Collection<ValidatorMessage> createMessageFromException(Exception e) throws ValidatorException {
        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        Mi25Context context = new Mi25Context();

        if (e.getMessage() != null){
            messages.add( new ValidatorMessage( e.getMessage(),
                    MessageLevel.FATAL,
                    context,
                    this ) );
        }

        Throwable cause = e.getCause();

        while (cause != null){
            messages.add( new ValidatorMessage( cause.getMessage(),
                    MessageLevel.FATAL,
                    context,
                    this ) );
            cause = cause.getCause();
        }

        return messages;
    }
}
