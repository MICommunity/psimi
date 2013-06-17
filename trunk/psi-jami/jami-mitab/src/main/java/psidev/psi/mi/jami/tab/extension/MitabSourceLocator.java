package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceLocator;

/**
 * A MITAB file source context
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class MitabSourceLocator extends FileSourceLocator {

    private int columnNumber;

    public MitabSourceLocator(int lineNumber, int charNumber, int columnNumber) {
        super(lineNumber, charNumber);
        this.columnNumber = columnNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    @Override
    public String toString() {
        return "Line: "+getLineNumber() + ", MITAB Column: "+ columnNumber + ", Character number: " + getCharNumber();
    }
}
