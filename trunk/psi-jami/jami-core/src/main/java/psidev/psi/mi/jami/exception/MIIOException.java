package psidev.psi.mi.jami.exception;

/**
 * Exception thrown by a datasource or a writer when it cannot read/write MI data
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/07/13</pre>
 */

public class MIIOException extends RuntimeException{

    public MIIOException() {
        super();
    }

    public MIIOException(String s) {
        super(s);
    }

    public MIIOException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public MIIOException(Throwable throwable) {
        super(throwable);
    }
}
