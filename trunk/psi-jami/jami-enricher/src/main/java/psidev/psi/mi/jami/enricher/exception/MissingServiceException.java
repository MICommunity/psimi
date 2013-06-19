package psidev.psi.mi.jami.enricher.exception;

/**
 * Thrown if the fetcher can not be found
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 */
public class MissingServiceException extends Exception{
    public MissingServiceException() {
        super();
    }

    public MissingServiceException(String s) {
        super(s);
    }

    public MissingServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public MissingServiceException(Throwable throwable) {
        super(throwable);
    }
}
