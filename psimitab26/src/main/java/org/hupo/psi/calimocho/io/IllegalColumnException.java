package org.hupo.psi.calimocho.io;

import org.hupo.psi.calimocho.model.ColumnDefinition;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class IllegalColumnException extends Exception {

    private String column;
    private ColumnDefinition columnDefinition;

    public IllegalColumnException() {
    }

    public IllegalColumnException( String message ) {
        super( message );
    }

    public IllegalColumnException( String message, Throwable cause ) {
        super( message, cause );
    }

    public IllegalColumnException( String message, String column, ColumnDefinition columnDefinition, Throwable cause ) {
        super( message, cause );
        this.column = column;
        this.columnDefinition = columnDefinition;
    }

    public IllegalColumnException( String message, String column, ColumnDefinition columnDefinition ) {
        this(message, column, columnDefinition, null);
    }

    public String getColumn() {
        return column;
    }

    public void setColumn( String column ) {
        this.column = column;
    }

    public ColumnDefinition getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition( ColumnDefinition columnDefinition ) {
        this.columnDefinition = columnDefinition;
    }
}
