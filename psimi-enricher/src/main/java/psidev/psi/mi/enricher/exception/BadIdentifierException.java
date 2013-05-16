package psidev.psi.mi.enricher.exception;

/**
 * Thrown if the identifier returned no result
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 14:05
 */
public class BadIdentifierException extends EnrichmentException{
    public BadIdentifierException() {
        super();
    }

    public BadIdentifierException(String s) {
        super(s);
    }

    public BadIdentifierException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BadIdentifierException(Throwable throwable) {
        super(throwable);
    }
}
