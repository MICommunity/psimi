package org.hupo.psi.calimocho.io;

import org.hupo.psi.calimocho.model.ColumnDefinition;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class IllegalRowException extends Exception {

    private Integer lineNumber;
    private String line;
    private ColumnDefinition columnDefinition;

    public IllegalRowException() {
    }

    public IllegalRowException( String message ) {
        super( message );
    }

    public IllegalRowException( String message, Throwable cause ) {
        super( message, cause );
    }

    public IllegalRowException( String message, String line, int lineNumber, Throwable cause ) {
        super( message, cause );
        this.line = line;
        this.lineNumber = lineNumber;
    }

    public String getLine() {
        return line;
    }

    public void setLine( String line ) {
        this.line = line;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber( Integer lineNumber ) {
        this.lineNumber = lineNumber;
    }

    public ColumnDefinition getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition( ColumnDefinition columnDefinition ) {
        this.columnDefinition = columnDefinition;
    }
}
