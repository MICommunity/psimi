package org.hupo.psi.calimocho.io;

/**
 * Thrown when unexpected fields for Columns are found, or fields
 * with missing key-value pairs.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class IllegalFieldException extends Exception {

    public IllegalFieldException() {
        super();
    }

    public IllegalFieldException( String message ) {
        super( message );
    }

    public IllegalFieldException( String message, Throwable cause ) {
        super( message, cause );
    }

    public IllegalFieldException( Throwable cause ) {
        super( cause );
    }
}
