package psidev.psi.mi.jami.utils.checksum;

/**
 * Custom Exception class to handle exceptions from this module
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 */
public class SeguidException extends Exception {

    public SeguidException() {
        super();
    }

    public SeguidException( String message ) {
        super(message);
    }

    public SeguidException( String message, Throwable cause ) {
        super( message,cause );

    }

    public SeguidException(Throwable cause){
        super(cause);
    }

}
