package psidev.psi.mi.jami.bridges.exception;

/**
 * An error object to signify that a query to a database has failed.
 * This Exception MUST also pass the original Exception that caused the problem.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 15/04/13
 * Time: 13:21
 */
public class NullSearchException extends FetcherException{

    public NullSearchException() {
        super();
    }

    public NullSearchException(String s) {
        super(s);
    }

    public NullSearchException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NullSearchException(Throwable throwable) {
        super(throwable);
    }
}
