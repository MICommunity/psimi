package psidev.psi.mi.jami.enricher.exception;

/**
 * Thrown if the fetcher can not be found
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 14:05
 */
@Deprecated
public class ConflictException extends EnrichmentException{
    public ConflictException() {
        super();
    }

    public ConflictException(String s) {
        super(s);
    }

    public ConflictException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ConflictException(Throwable throwable) {
        super(throwable);
    }
}
