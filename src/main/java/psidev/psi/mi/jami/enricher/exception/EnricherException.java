package psidev.psi.mi.jami.enricher.exception;

/**
 * This exception is thrown when exceptions are induced in the enricher.
 * This includes re-wrapping the BridgeFailedException.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 11/06/13
 */
public class EnricherException extends Exception {
    public EnricherException() {
        super();
    }

    public EnricherException(String s) {
        super(s);
    }

    public EnricherException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public EnricherException(Throwable throwable) {
        super(throwable);
    }
}
