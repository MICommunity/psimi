package psidev.psi.mi.jami.enricher.exception;

/**
 * Thrown if no for of identifier can be found
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 14:00
 */
public class MissingIdentifierException extends EnrichmentException{
    public MissingIdentifierException() {
        super();
    }

    public MissingIdentifierException(String s) {
        super(s);
    }

    public MissingIdentifierException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public MissingIdentifierException(Throwable throwable) {
        super(throwable);
    }
}