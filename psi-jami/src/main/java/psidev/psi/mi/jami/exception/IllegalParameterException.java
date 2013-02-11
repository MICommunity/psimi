package psidev.psi.mi.jami.exception;

/**
 * Exception to throw if a Parameter is not valid
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class IllegalParameterException extends Exception {

    public IllegalParameterException() {
        super();
    }

    public IllegalParameterException(String s) {
        super(s);
    }

    public IllegalParameterException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public IllegalParameterException(Throwable throwable) {
        super(throwable);
    }
}
