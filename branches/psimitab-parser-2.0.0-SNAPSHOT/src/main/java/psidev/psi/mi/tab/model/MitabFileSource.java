package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.datasource.FileSourceContext;

/**
 * A MITAB file source context
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class MitabFileSource implements FileSourceContext {

    private int lineNumber;
    private int columnNumber;

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public int getId() {
        return 0;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }
}
