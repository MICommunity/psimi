package psidev.psi.mi.jami.xml.model.extension;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;

/**
 * Psi-XML source locator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/07/13</pre>
 */

public class PsiXmlLocator extends FileSourceLocator implements Locator{

    private Integer objectId;
    private int characterOffset;

    public PsiXmlLocator(int lineNumber, int charNumber, int offset) {
        super(lineNumber, charNumber);
        this.characterOffset = offset;
    }

    public PsiXmlLocator(int lineNumber, int charNumber, Integer objectId) {
        super(lineNumber, charNumber);
        this.objectId = objectId;
    }

    public Integer getObjectId() {
        return objectId;
    }

    @Override
    public String getPublicId() {
        return null;
    }

    @Override
    public String getSystemId() {
        return null;
    }

    public int getColumnNumber() {
        return getCharNumber();
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public int getCharacterOffset() {
        return characterOffset;
    }

    public void setCharacterOffset(int characterOffset) {
        this.characterOffset = characterOffset;
    }

    public String toString() {
        return super.toString() + (objectId != null ? " Id: "+objectId : "");
    }
}
