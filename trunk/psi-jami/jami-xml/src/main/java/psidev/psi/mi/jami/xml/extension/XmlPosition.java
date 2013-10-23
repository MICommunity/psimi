package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;

import javax.xml.bind.annotation.*;
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
@XmlType(name = "position")
public class XmlPosition extends AbstractXmlPosition{

    private BigInteger pos;
    @XmlLocation
    @XmlTransient
    protected Locator locator;

    public XmlPosition() {
    }

    public XmlPosition(XmlCvTerm status, boolean positionUndetermined) {
        super(status, positionUndetermined);
    }

    public XmlPosition(XmlCvTerm status, BigInteger pos, boolean positionUndetermined) {
        super(status, positionUndetermined);
        this.pos = pos;
    }

    public XmlPosition(XmlCvTerm status, long pos, boolean positionUndetermined) {
        super(status, positionUndetermined);
        this.pos = new BigInteger(Long.toString(pos));
    }

    @Override
    public XmlCvTerm getStatus() {
        return super.getStatus();
    }

    public long getStart() {
        return pos != null ? pos.longValue() : 0;
    }

    public long getEnd() {
        return pos != null ? pos.longValue() : 0;
    }

    @Override
    public boolean isPositionUndetermined() {
        return super.isPositionUndetermined();
    }

    /**
     * Gets the value of the position property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    @XmlAttribute(name = "position", required = true)
    public BigInteger getJAXBPosition() {
        return pos;
    }

    /**
     * Sets the value of the position property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setJAXBPosition(BigInteger value) {
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
}
