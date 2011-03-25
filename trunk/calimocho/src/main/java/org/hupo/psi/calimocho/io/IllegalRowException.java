package org.hupo.psi.calimocho.io;


import org.hupo.psi.calimocho.model.Row;

/**
 * Thrown when unexpected formats for Rows are found.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class IllegalRowException extends Exception {

    private Integer lineNumber;
    private String line;
    private Row row;

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

    public IllegalRowException( String message, Row row, Throwable cause ) {
        super( message, cause );
        this.row = row;
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

    public Row getRow() {
        return row;
    }

    public void setRow( Row row ) {
        this.row = row;
    }
}
