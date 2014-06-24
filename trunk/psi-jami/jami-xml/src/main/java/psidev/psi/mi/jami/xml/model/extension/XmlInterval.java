package psidev.psi.mi.jami.xml.model.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.math.BigInteger;

/**
 * Xml implementation of a position which is an interval
 * The JAXB binding is designed to be read-only and is not designed for writing
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlInterval extends AbstractXmlPosition  {
    private long start;
    private long end;
    @XmlLocation
    @XmlTransient
    private Locator locator;

    public XmlInterval() {
    }

    public XmlInterval(CvTerm status, boolean positionUndetermined) {
        super(status, positionUndetermined);
    }

    public XmlInterval(CvTerm status, long start, long end, boolean positionUndetermined) {
        super(status, positionUndetermined);
        this.start = start;
        this.end = end;
    }

    @Override
    public CvTerm getStatus() {
        return super.getStatus();
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    @Override
    public boolean isPositionUndetermined() {
        return super.isPositionUndetermined();
    }

    /**
     * Sets the value of the begin property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    @XmlAttribute(name = "begin", required = true)
    public void setJAXBBeginPosition(long value) {
        this.start = value;
    }

    /**
     * Sets the value of the end property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    @XmlAttribute(name = "end", required = true)
    public void setJAXBEndPosition(long value) {
        this.end = value;
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
        return "Xml Range Interval: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
    }
}
