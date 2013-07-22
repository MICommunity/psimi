package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.datasource.FileSourceLocator;

/**
 * Psi-XML source locator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/07/13</pre>
 */

public class PsiXmLocator extends FileSourceLocator {

    private Integer objectId;

    public PsiXmLocator(int lineNumber, int charNumber, Integer objectId) {
        super(lineNumber, charNumber);
    }

    public Integer getObjectId() {
        return objectId;
    }

    public String toString() {
        return super.toString() + (objectId != null ? " Id: "+objectId : "");
    }
}
