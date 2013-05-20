package psidev.psi.mi.jami.exception;

/**
 * Exception thrown when trying to create an Illegal range
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class IllegalRangeException extends Exception {
    public IllegalRangeException() {
        super();
    }

    public IllegalRangeException(String s) {
        super(s);
    }

    public IllegalRangeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public IllegalRangeException(Throwable throwable) {
        super(throwable);
    }
}
