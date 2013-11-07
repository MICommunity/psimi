package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;

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
public class XmlInterval extends AbstractXmlPosition{
    private BigInteger start;
    private BigInteger end;
    @XmlLocation
    @XmlTransient
    private Locator locator;

    public XmlInterval() {
    }

    public XmlInterval(CvTerm status, boolean positionUndetermined) {
        super(status, positionUndetermined);
    }

    public XmlInterval(CvTerm status, BigInteger start, BigInteger end, boolean positionUndetermined) {
        super(status, positionUndetermined);
        this.start = start;
        this.end = end;
    }

    public XmlInterval(CvTerm status, long start, long end, boolean positionUndetermined) {
        super(status, positionUndetermined);
        this.start = new BigInteger(Long.toString(start));
        this.end = new BigInteger(Long.toString(end));
    }

    @Override
    public CvTerm getStatus() {
        return super.getStatus();
    }

    public long getStart() {
        return start != null ? start.longValue() : 0;
    }

    public long getEnd() {
        return end != null ? end.longValue() : 0;
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
    public void setJAXBBeginPosition(BigInteger value) {
        this.start = value;
        if (this.start == null){
            this.start = new BigInteger(String.valueOf(0));
            this.setJAXBStatus(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI));
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onInvalidPosition("The Xml start position is not provided. It will be loaded as undetermined position", this);
            }
        }
        else if (end != null && end.intValue() > start.intValue()){
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onInvalidPosition("The Xml Interval is invalid as the start position "+start+" is after the end position "+end, this);
            }
            this.start = new BigInteger(String.valueOf(0));
            this.end = new BigInteger(String.valueOf(0));
            this.setJAXBStatus(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI));
        }
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
    public void setJAXBEndPosition(BigInteger value) {
        this.end = value;
        if (this.end == null){
            this.end = new BigInteger(String.valueOf(0));
            this.setJAXBStatus(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI));
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onInvalidPosition("The Xml end Position is not provided. It will be loaded as undetermined position", this);
            }
        }
        else if (start != null && end.intValue() > start.intValue()){
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onInvalidPosition("The Xml Interval is invalid as the start position "+start+" is after the end position "+end, this);
            }
            this.start = new BigInteger(String.valueOf(0));
            this.end = new BigInteger(String.valueOf(0));
            this.setJAXBStatus(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI));
        }
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
        return "Xml Range Interval: "+getSourceLocator() != null ? getSourceLocator().toString():super.toString();
    }
}
