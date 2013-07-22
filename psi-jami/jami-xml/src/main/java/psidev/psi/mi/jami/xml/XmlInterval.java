package psidev.psi.mi.jami.xml;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;

/**
 * Xml implementation of a position which is an interval
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "interval")
public class XmlInterval extends AbstractXmlPosition{
    private BigInteger start;
    private BigInteger end;

    public XmlInterval() {
    }

    public XmlInterval(XmlCvTerm status, boolean positionUndetermined) {
        super(status, positionUndetermined);
    }

    public XmlInterval(XmlCvTerm status, BigInteger start, BigInteger end, boolean positionUndetermined) {
        super(status, positionUndetermined);
        this.start = start;
        this.end = end;
    }

    public XmlInterval(XmlCvTerm status, long start, long end, boolean positionUndetermined) {
        super(status, positionUndetermined);
        this.start = new BigInteger(Long.toString(start));
        this.end = new BigInteger(Long.toString(end));
    }

    @Override
    @XmlTransient
    public XmlCvTerm getStatus() {
        return super.getStatus();
    }

    @XmlTransient
    public long getStart() {
        return start != null ? start.longValue() : 0;
    }

    @XmlTransient
    public long getEnd() {
        return end != null ? end.longValue() : 0;
    }

    @Override
    @XmlTransient
    public boolean isPositionUndetermined() {
        return super.isPositionUndetermined();
    }

    /**
     * Gets the value of the begin property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    @XmlAttribute(name = "begin", required = true)
    public BigInteger getBegin() {
        return start;
    }

    /**
     * Sets the value of the begin property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setBegin(BigInteger value) {
        this.start = value;
    }

    /**
     * Gets the value of the end property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    @XmlAttribute(name = "end", required = true)
    public BigInteger getJAXBEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setJAXBEnd(BigInteger value) {
        this.end = value;
    }
}
