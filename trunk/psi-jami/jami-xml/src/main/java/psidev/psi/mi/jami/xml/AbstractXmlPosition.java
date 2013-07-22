package psidev.psi.mi.jami.xml;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.utils.comparator.range.UnambiguousPositionComparator;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import java.io.Serializable;

/**
 * Abstract class for XmlPosition
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */

public abstract class AbstractXmlPosition implements Position, FileSourceContext, Serializable {

    private XmlCvTerm status;
    private boolean isPositionUndetermined;

    private org.xml.sax.Locator locator;
    private FileSourceLocator sourceLocator;

    protected AbstractXmlPosition() {
    }

    protected AbstractXmlPosition(XmlCvTerm status, boolean positionUndetermined) {
        this.status = status;
        isPositionUndetermined = positionUndetermined;
    }

    public XmlCvTerm getStatus() {
        if (status == null){
            this.status = new XmlCvTerm(PsiXmlUtils.UNSPECIFIED);
        }
        return this.status;
    }

    public void setStatus(XmlCvTerm status) {
        this.status = status;
    }

    public boolean isPositionUndetermined() {
        return this.isPositionUndetermined;
    }

    public Locator getLocator() {
        return locator;
    }

    public void setLocator(Locator locator) {
        this.locator = locator;
        this.sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Position)){
            return false;
        }

        return UnambiguousPositionComparator.areEquals(this, (Position) o);
    }

    @Override
    public String toString() {
        return getStatus().toString() + ": " + getStart()  +".."+ getEnd();
    }

    @Override
    public int hashCode() {
        return UnambiguousPositionComparator.hashCode(this);
    }
}
