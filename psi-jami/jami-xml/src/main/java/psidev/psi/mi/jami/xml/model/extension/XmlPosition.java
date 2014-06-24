package psidev.psi.mi.jami.xml.model.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Xml implementation of a simple Position
 *
 * The JAXB binding is designed to be read-only and is not designed for writing
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlPosition extends AbstractXmlPosition implements Serializable {

    private long pos;
    @XmlLocation
    @XmlTransient
    private Locator locator;

    public XmlPosition() {
    }

    public XmlPosition(CvTerm status, boolean positionUndetermined) {
        super(status, positionUndetermined);
    }

    public XmlPosition(CvTerm status, long pos, boolean positionUndetermined) {
        super(status, positionUndetermined);
        this.pos = pos;
    }

    @Override
    public CvTerm getStatus() {
        return super.getStatus();
    }

    public long getStart() {
        return pos;
    }

    public long getEnd() {
        return pos;
    }

    @Override
    public boolean isPositionUndetermined() {
        return super.isPositionUndetermined();
    }

    /**
     * Sets the value of the position property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    @XmlAttribute(name = "position", required = true)
    public void setJAXBPosition(long value) {
        this.pos = value;
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        if (super.getSourceLocator() == null && locator != null){
            super.setSourceLocator(new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null));
        }
        return super.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            super.setSourceLocator(null);
        }
        else{
            super.setSourceLocator(new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null));
        }
    }

    @Override
    public String toString() {
        return "Range Position: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
    }
}
