package org.hupo.psi.calimocho.io;

import org.hupo.psi.calimocho.model.Field;

/**
 * Thrown when unexpected fields for Columns are found, or fields
 * with missing key-value pairs.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class IllegalFieldException extends Exception {

    /**
     * String representation of a field available at parse time.
     */
    private String fieldStr;

    /**
     * Field available at formatting time.
     */
    private Field field;

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

    public IllegalFieldException( Field field, String message, Throwable cause ) {
        this( message, cause );
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public void setField( Field field ) {
        this.field = field;
    }
}
