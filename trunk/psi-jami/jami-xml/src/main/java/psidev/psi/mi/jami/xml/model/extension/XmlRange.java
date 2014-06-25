package psidev.psi.mi.jami.xml.model.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.ResultingSequence;
import psidev.psi.mi.jami.utils.comparator.range.UnambiguousRangeAndResultingSequenceComparator;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.jami.xml.model.extension.xml300.XmlResultingSequence;
import psidev.psi.mi.jami.xml.model.reference.AbstractParticipantRef;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Xml implementation of Range
 *
 * The JAXB binding is designed to be read-only and is not designed for writing
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlRange implements Range, FileSourceContext, Locatable {
    private Position start;
    private Position end;
    private boolean isLink;
    private ResultingSequence resultingSequence;
    private Participant participant;
    private PsiXmlLocator sourceLocator;
    @XmlLocation
    @XmlTransient
    private Locator locator;

    private XmlCvTerm startStatus;
    private XmlCvTerm endStatus;

    public XmlRange(){

    }

    public XmlRange(Position start, Position end){
        setPositions(start, end);
    }

    public XmlRange(Position start, Position end, boolean isLink){
        this(start, end);
        this.isLink = isLink;
    }

    public XmlRange(Position start, Position end, ResultingSequence resultingSequence){
        this(start, end);
        this.resultingSequence = resultingSequence;
    }

    public XmlRange(Position start, Position end, boolean isLink, ResultingSequence resultingSequence){
        this(start, end, isLink);
        this.resultingSequence = resultingSequence;
    }

    public XmlRange(Position start, Position end, Participant participant){
        this(start, end);
        this.participant = participant;
    }

    public XmlRange(Position start, Position end, boolean isLink, Participant participant){
        this(start, end, isLink);
        this.participant = participant;
    }

    public Position getStart() {
        if (start == null){
            if (startStatus != null){
                start = new XmlPosition(startStatus, 0, true);
            }
            else{
                start = new XmlPosition(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI), 0, true);
            }
        }
        return start;
    }

    public Position getEnd() {
        if (end == null){
            if (endStatus != null){
                end = new XmlPosition(endStatus, 0, true);
            }
            else{
                end = new XmlPosition(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI), 0, true);
            }
        }
        return this.end;
    }

    /**
     * Gets the value of the isLink property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public boolean isLink() {
        return false;
    }

    public void setLink(boolean link) {
        this.isLink = link;
    }

    /**
     * Sets the value of the startStatus property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlCvTerm }
     *
     */
    @XmlElement(name = "startStatus", required = true)
    public void setJAXBStartStatus(XmlCvTerm value) {
        this.startStatus = value;
    }

    /**
     * Sets the value of the beginInterval property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlInterval }
     *
     */
    @XmlElement(name = "beginInterval")
    public void setJAXBBeginInterval(XmlInterval value) {
        this.start = value;
        value.setJAXBStatus(this.startStatus);
        if (this.end != null && !this.start.isPositionUndetermined() && !this.end.isPositionUndetermined() &&
                this.start.getEnd() > this.end.getStart()){
            this.start = new XmlPosition(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI), true);
            this.end = new XmlPosition(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI), true);
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onInvalidRange("The range is invalid as the start position is after the end position. It will be loaded as undetermined range.", this);
            }
        }
    }

    /**
     * Sets the value of the begin property.
     *
     * @param value
     *     allowed object is
     *     {@link Position }
     *
     */
    @XmlElement(name = "begin")
    public void setJAXBBeginPosition(XmlPosition value) {
        this.start = value;
        value.setJAXBStatus(this.startStatus);
        if (this.end != null && !this.start.isPositionUndetermined() && !this.end.isPositionUndetermined() &&
                this.start.getEnd() > this.end.getStart()){
            this.start = new XmlPosition(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI), true);
            this.end = new XmlPosition(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI), true);
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onInvalidRange("The range is invalid as the start position is after the end position. It will be loaded as undetermined range.", this);
            }
        }
    }

    /**
     * Sets the value of the endStatus property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlCvTerm }
     *
     */
    @XmlElement(name = "endStatus", required = true)
    public void setJAXBEndStatus(XmlCvTerm value) {
        this.endStatus = value;
    }

    /**
     * Sets the value of the endInterval property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlInterval }
     *
     */
    @XmlElement(name = "endInterval")
    public void setJAXBEndInterval(XmlInterval value) {
        this.end = value;
        value.setJAXBStatus(endStatus);
        if (this.start != null && !this.start.isPositionUndetermined() && !this.end.isPositionUndetermined() &&
                this.start.getEnd() > this.end.getStart()){
            this.start = new XmlPosition(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI), true);
            this.end = new XmlPosition(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI), true);
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onInvalidRange("The range is invalid as the start position is after the end position. It will be loaded as undetermined range.", this);
            }
        }
    }

    /**
     * Sets the value of the end property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlPosition }
     *
     */
    @XmlElement(name = "end")
    public void setJAXBEndPosition(XmlPosition value) {
        this.end = value;
        value.setJAXBStatus(endStatus);
        if (this.start != null && !this.start.isPositionUndetermined() && !this.end.isPositionUndetermined() &&
                this.start.getEnd() > this.end.getStart()){
            this.start = new XmlPosition(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI), true);
            this.end = new XmlPosition(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI), true);
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onInvalidRange("The range is invalid as the start position is after the end position. It will be loaded as undetermined range", this);
            }
        }
    }

    @XmlElement(name = "isLink", defaultValue = "false")
    public void setJAXBLink(boolean link) {
        this.isLink = link;
    }

    public void setPositions(Position start, Position end) {
        if (start == null){
            throw new IllegalArgumentException("The start position is required and cannot be null");
        }
        if (end == null){
            throw new IllegalArgumentException("The end position is required and cannot be null");
        }

        if (start.getEnd() != 0 && end.getStart() != 0 && start.getEnd() > end.getStart()){
            throw new IllegalArgumentException("The start position cannot be ending before the end position");
        }

        this.start = start;
        this.end = end;
    }

    public ResultingSequence getResultingSequence() {
        return this.resultingSequence;
    }

    public void setResultingSequence(ResultingSequence resultingSequence) {
        this.resultingSequence = resultingSequence;
    }

    public Participant getParticipant() {
        return this.participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @XmlElement(name = "resultingSequence", namespace = "http://psi.hupo.org/mi/mif300")
    public void setJAXBResultingSequence(XmlResultingSequence resultingSequence){
         setResultingSequence(resultingSequence);
    }

    public void setJAXBParticipantRef(int id, PsiXmlLocator locator){
        if (this.participant == null){
            this.participant = new ParticipantRef(id, locator);
        }
    }

    @XmlElement(name = "participantRef" , namespace = "http://psi.hupo.org/mi/mif300")
    public void setJAXBParticipantRef(int id){
        this.participant = new ParticipantRef(id, this.sourceLocator);
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        if (sourceLocator == null && locator != null){
            sourceLocator = new PsiXmlLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
        }
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else if (sourceLocator instanceof PsiXmlLocator){
            this.sourceLocator = (PsiXmlLocator)sourceLocator;
        }
        else {
            this.sourceLocator = new PsiXmlLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Range)){
            return false;
        }

        return UnambiguousRangeAndResultingSequenceComparator.areEquals(this, (Range) o);
    }

    @Override
    public int hashCode() {
        return UnambiguousRangeAndResultingSequenceComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return "Feature range: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
    }

    ////// inner classes
    /**
     * participant ref for a composite feature
     */
    private class ParticipantRef extends AbstractParticipantRef{
        private PsiXmlLocator sourceLocator;

        public ParticipantRef(int ref, PsiXmlLocator locator) {
            super(ref);
            this.sourceLocator = locator;
        }

        public boolean resolve(PsiXmlIdCache parsedObjects) {
            if (parsedObjects.containsParticipant(this.ref)){
                participant = parsedObjects.getParticipant(this.ref);
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Feature range participant reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
        }

        @Override
        protected void initialiseParticipantDelegate() {
            XmlModelledParticipant modelled = new XmlModelledParticipant();
            modelled.setId(this.ref);
            setDelegate(modelled);
        }

        public FileSourceLocator getSourceLocator() {
            return this.sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else if (sourceLocator instanceof PsiXmlLocator){
                this.sourceLocator = (PsiXmlLocator)sourceLocator;
            }
            else {
                this.sourceLocator = new PsiXmlLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        public void setSourceLocator(PsiXmlLocator sourceLocator) {
            this.sourceLocator = sourceLocator;
        }
    }
}
