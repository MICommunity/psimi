package psidev.psi.mi.jami.xml.model.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.model.reference.AbstractFeatureRef;
import psidev.psi.mi.jami.xml.model.reference.AbstractParticipantRef;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Participant of the inferred interaction.
 *
 * <p>Java class for inferredInteractionParticipant complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="inferredInteractionParticipant">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;elements name="participantRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;elements name="participantFeatureRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class InferredInteractionParticipant implements FileSourceContext, Locatable
{

    private Feature feature;
    private Participant participant;
    private PsiXmlLocator sourceLocator;
    @XmlLocation
    @XmlTransient
    private Locator locator;

    public InferredInteractionParticipant(){
    }

    public Feature getFeature() {
        return feature;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
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

    /**
     * Sets the value of the participantFeatureRef property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    @XmlElement(name = "participantFeatureRef")
    public void setJAXBParticipantFeatureRef(Integer value) {
        if (value != null){
            this.feature = new FeatureRef(value);
        }
    }

    /**
     * Sets the value of the participantRef property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    @XmlElement(name = "participantRef")
    public void setJAXBParticipantRef(Integer value) {
        if (value != null){
            this.participant = new ParticipantRef(value);
        }
    }

    private FileSourceLocator getInferredParticipantLocator(){
        return sourceLocator;
    }

    @Override
    public String toString() {
        return "Inferred participant: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
    }

    ////////////////////////////////////////////////////////////////// classes

    private class ParticipantRef extends AbstractParticipantRef{
        public ParticipantRef(int ref) {
            super(ref);
        }

        public boolean resolve(PsiXmlIdCache parsedObjects) {
            if (parsedObjects.containsParticipant(this.ref)){
                Participant p = parsedObjects.getParticipant(this.ref);
                if (p != null){
                    participant = p;
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Participant Reference in inferred Participant: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
        }

        @Override
        protected void initialiseParticipantDelegate() {
            XmlModelledParticipant modelled = new XmlModelledParticipant();
            modelled.setId(this.ref);
            setDelegate(modelled);
        }

        public FileSourceLocator getSourceLocator() {
            return getInferredParticipantLocator();
        }

        public void setSourceLocator(FileSourceLocator locator) {
            throw new UnsupportedOperationException("Cannot set the source locator of a participant ref");
        }
    }

    private class FeatureRef extends AbstractFeatureRef{
        public FeatureRef(int ref) {
            super(ref);
        }

        public boolean resolve(PsiXmlIdCache parsedObjects) {
            if (parsedObjects.containsFeature(this.ref)){
                Feature f = parsedObjects.getFeature(this.ref);
                if (f != null){
                    feature = f;
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Feature Reference in inferred participant: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
        }

        @Override
        protected void initialiseFeatureDelegate() {
            XmlFeature modelled = new XmlFeature();
            modelled.setId(this.ref);
            setDelegate(modelled);
        }

        public FileSourceLocator getSourceLocator() {
            return getInferredParticipantLocator();
        }

        public void setSourceLocator(FileSourceLocator locator) {
            throw new UnsupportedOperationException("Cannot set the source locator of a feature ref");
        }
    }
}
