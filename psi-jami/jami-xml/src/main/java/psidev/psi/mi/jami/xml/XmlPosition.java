package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.CvTerm;

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

    public XmlPosition(CvTerm status, boolean positionUndetermined) {
        super(status, positionUndetermined);
    }

    public XmlPosition(CvTerm status, BigInteger pos, boolean positionUndetermined) {
        super(status, positionUndetermined);
        this.pos = pos;
    }

    @Override
    @XmlTransient
    public CvTerm getStatus() {
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
}
