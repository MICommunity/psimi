package psidev.psi.mi.jami.bridges.exception;

/**
 * An error object to signify that a query to a database has failed.
 * This Exception MUST also pass the original Exception that caused the problem.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 15/04/13
 * Time: 13:21
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
