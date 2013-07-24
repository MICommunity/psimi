package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;

/**
 * Xml implementation of a simple Position
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "position")
public class XmlPosition extends AbstractXmlPosition{

    private BigInteger pos;

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
    @XmlTransient
    public XmlCvTerm getStatus() {
        return super.getStatus();
    }

    @XmlTransient
    public long getStart() {
        return pos != null ? pos.longValue() : 0;
    }

    @XmlTransient
    public long getEnd() {
        return pos != null ? pos.longValue() : 0;
    }

    @Override
    @XmlTransient
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
    public BigInteger getPosition() {
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
    public void setPosition(BigInteger value) {
        this.pos = value;
    }

    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return super.getSaxLocator();
    }

    public void setSaxLocator(Locator sourceLocator) {
        super.setSaxLocator(sourceLocator);
    }

    @Override
    @XmlTransient
    public FileSourceLocator getSourceLocator() {
        return super.getSourceLocator();
    }
}
