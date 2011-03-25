package org.hupo.psi.calimocho.io;


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
}
