package psidev.psi.mi.jami.enricher.exception;

/**
 * Thrown when a required service is missing.
 * Examples of services which may be missing include fetcher, enricher or remapper.
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
