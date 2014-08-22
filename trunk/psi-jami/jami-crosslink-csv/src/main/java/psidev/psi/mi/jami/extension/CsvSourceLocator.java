package psidev.psi.mi.jami.extension;

import psidev.psi.mi.jami.datasource.FileSourceLocator;

/**
 * A Crosslink CSV file source context
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class CsvSourceLocator extends FileSourceLocator {

    private int columnNumber;

    public CsvSourceLocator(int lineNumber, int charNumber, int columnNumber) {
        super(lineNumber, charNumber);
        this.columnNumber = columnNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    @Override
    public String toString() {
        return "Line: "+getLineNumber() + ", CSV Column: "+ columnNumber + ", Character number: " + getCharNumber();
    }
}
