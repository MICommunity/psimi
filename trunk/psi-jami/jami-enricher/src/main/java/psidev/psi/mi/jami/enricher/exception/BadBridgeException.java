package psidev.psi.mi.jami.enricher.exception;

/**
 * Thrown if the identifier returned no result
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 14:05
 */
public class BadBridgeException extends EnrichmentException{
    public BadBridgeException() {
        super();
    }

    public BadBridgeException(String s) {
        super(s);
    }

    public BadBridgeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BadBridgeException(Throwable throwable) {
        super(throwable);
    }
}
