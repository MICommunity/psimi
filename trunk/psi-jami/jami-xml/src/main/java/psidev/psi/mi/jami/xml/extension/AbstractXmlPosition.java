package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.utils.comparator.range.UnambiguousPositionComparator;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Abstract class for XmlPosition
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlTransient
public abstract class AbstractXmlPosition implements Position, FileSourceContext, Locatable {

    private CvTerm status;
    private boolean isPositionUndetermined;

    private PsiXmLocator sourceLocator;

    protected AbstractXmlPosition() {
    }

    protected AbstractXmlPosition(XmlCvTerm status, boolean positionUndetermined) {
        this.status = status;
        isPositionUndetermined = positionUndetermined;
    }

    public CvTerm getStatus() {
        if (status == null){
            this.status = new XmlCvTerm(PsiXmlUtils.UNSPECIFIED);
        }
        return this.status;
    }

    public void setJAXBStatus(CvTerm status) {
        this.status = status;
    }

    public boolean isPositionUndetermined() {
        return this.isPositionUndetermined;
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = (PsiXmLocator)sourceLocator;
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
