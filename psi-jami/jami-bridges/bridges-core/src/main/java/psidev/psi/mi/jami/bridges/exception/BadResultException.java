package psidev.psi.mi.jami.bridges.exception;

/**
 * An error object to signify that a DEPRECATEDquery to a database has failed.
 * This Exception MUST also pass the original Exception that caused the problem.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 15/04/13
 * Time: 13:21
 */
public class BadResultException extends Exception{

    public BadResultException() {
        super();
    }

    public BadResultException(String s) {
        super(s);
    }

    public BadResultException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BadResultException(Throwable throwable) {
        super(throwable);
    }
}
