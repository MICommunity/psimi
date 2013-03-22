package psidev.psi.mi.tab.model;

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
    public String getLocationDescription() {
        return "Line: "+getLineNumber() + ", MITAB Column: "+ columnNumber + ", Character number: " + getCharNumber();
    }
}
