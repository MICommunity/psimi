package psidev.psi.mi.jami.binary.expansion;

/**
 * Exception for Interactions that cannot be expanded with a specific complex expansion
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/07/14</pre>
 */

public class ComplexExpansionException extends Exception{

    public ComplexExpansionException() {
        super();
    }

    public ComplexExpansionException(String message) {
        super(message);
    }

    public ComplexExpansionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComplexExpansionException(Throwable cause) {
        super(cause);
    }

    protected ComplexExpansionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
