package psidev.psi.mi.xml.xmlindex;

import psidev.psi.tools.xxindex.index.IndexElement;

/**
 * TODO comment that class header
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since TODO specify the maven artifact version
 */
public class InputStreamRange {

    long fromPosition;
    long toPosition;
    long lineNumber = IndexElement.NO_LINE_NUMBER;

    //////////////////
    // Constructors

    public InputStreamRange() {
    }

    ///////////////////////////
    // Getters and Setters

    public long getFromPosition() {
        return fromPosition;
    }

    public void setFromPosition( long fromPosition ) {
        this.fromPosition = fromPosition;
    }

    public long getToPosition() {
        return toPosition;
    }

    public void setToPosition( long toPosition ) {
        this.toPosition = toPosition;
    }

    public boolean hasLineNumber() {
        return getLineNumber() != IndexElement.NO_LINE_NUMBER;
    }

    public long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber( long lineNumber ) {
        this.lineNumber = lineNumber;
    }

    //////////////////////////
    // Object's override


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "InputStreamRange" );
        sb.append( '[' ).append( fromPosition );
        sb.append( ".." ).append( toPosition );
        if( hasLineNumber() ) {
            sb.append( " line:" ).append( lineNumber );
        }
        sb.append( ']' );
        return sb.toString();
    }
}
