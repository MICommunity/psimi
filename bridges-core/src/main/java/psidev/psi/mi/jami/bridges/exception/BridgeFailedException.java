package psidev.psi.mi.jami.bridges.exception;

/**
 * An error object to signify that a query to an external service has failed.
 * This exception would usually be expected pass the original Exception that caused the problem.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  15/04/13
 */
public class BridgeFailedException extends Exception{

    public BridgeFailedException() {
        super();
    }

    public BridgeFailedException(String s) {
        super(s);
    }

    public BridgeFailedException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BridgeFailedException(Throwable throwable) {
        super(throwable);
    }
}
