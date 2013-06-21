package psidev.psi.mi.jami.enricher.exception;

/**
 * This exception is thrown when an object being used to enrich has a value which is unexpected.
 * Examples may include: empty fields which should contain data, data in an unexpected format.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 11/06/13
 */
public class BadEnrichedFormException extends Exception {
    public BadEnrichedFormException() {
        super();
    }

    public BadEnrichedFormException(String s) {
        super(s);
    }

    public BadEnrichedFormException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BadEnrichedFormException(Throwable throwable) {
        super(throwable);
    }
}
