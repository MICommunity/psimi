package psidev.psi.mi.jami.enricher.exception;

/**
 * Created with IntelliJ IDEA.
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

