package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceLocator;

/**
 * Psi-XML source locator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/07/13</pre>
 */

public class PsiXmLocator extends FileSourceLocator implements org.xml.sax.Locator {

    private Integer objectId;

    public PsiXmLocator(int lineNumber, int charNumber, Integer objectId) {
        super(lineNumber, charNumber);
    }

    public Integer getObjectId() {
        return objectId;
    }

    public String getPublicId() {
        return null;
    }

    public String getSystemId() {
        return null;
    }

    public int getColumnNumber() {
        return getCharNumber();
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String toString() {
        return super.toString() + (objectId != null ? " Id: "+objectId : "");
    }
}
