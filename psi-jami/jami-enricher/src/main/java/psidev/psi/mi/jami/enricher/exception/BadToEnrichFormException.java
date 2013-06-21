package psidev.psi.mi.jami.enricher.exception;

/**
 * This exception is thrown when an object being enriched has a value which is unexpected.
 * Examples may include: empty fields which should contain data, data in an unexpected format.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 11/06/13
 */
public class BadToEnrichFormException extends Exception {
    public BadToEnrichFormException() {
        super();
    }

    public BadToEnrichFormException(String s) {
        super(s);
    }

    public BadToEnrichFormException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BadToEnrichFormException(Throwable throwable) {
        super(throwable);
    }
}

