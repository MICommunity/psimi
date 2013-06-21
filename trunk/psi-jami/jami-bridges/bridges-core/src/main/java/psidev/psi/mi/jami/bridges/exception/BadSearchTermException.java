package psidev.psi.mi.jami.bridges.exception;

/**
 * This exception is thrown when attempting to query a service using a term which is invalid.
 * For example a null search tem.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 15/04/13
 */
public class BadSearchTermException extends Exception{

    public BadSearchTermException() {
        super();
    }

    public BadSearchTermException(String s) {
        super(s);
    }

    public BadSearchTermException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BadSearchTermException(Throwable throwable) {
        super(throwable);
    }
}
