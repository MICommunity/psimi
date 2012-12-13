package psidev.psi.mi.validator.extension.rules.dependencies;

/**
 * Exception thrown by the validator rules
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26-Apr-2010</pre>
 */

public class ValidatorRuleException extends RuntimeException{
    public ValidatorRuleException() {
        super();
    }

    public ValidatorRuleException(String message) {
        super(message);
    }

    public ValidatorRuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatorRuleException(Throwable cause) {
        super(cause);
    }
}
