package psidev.psi.mi.jami.bridges.picr.io;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25-Mar-2010</pre>
 */

public class PicrParsingException extends Exception{

    public PicrParsingException() {
        super();   
    }

    public PicrParsingException(String message) {
        super(message);
    }

    public PicrParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PicrParsingException(Throwable cause) {
        super(cause);
    }
}
