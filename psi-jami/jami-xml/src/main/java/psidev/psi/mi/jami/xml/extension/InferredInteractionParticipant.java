package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.xml.cache.PsiXml25IdCache;
import psidev.psi.mi.jami.xml.reference.AbstractFeatureRef;
import psidev.psi.mi.jami.xml.reference.AbstractParticipantRef;

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
    private Entity participant;
    private PsiXmLocator sourceLocator;
    @XmlLocation
    @XmlTransient
    private Locator locator;

    public InferredInteractionParticipant(){
    }

    public Feature getFeature() {
        return feature;
    }

    public Entity getParticipant() {
        return participant;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public void setParticipant(Entity participant) {
        this.participant = participant;
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        if (sourceLocator == null && locator != null){
            sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
        }
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
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

        public boolean resolve(PsiXml25IdCache parsedObjects) {
            if (parsedObjects.contains(this.ref)){
                Entity obj = parsedObjects.getParticipant(this.ref);
                if (obj != null){
                    participant = obj;
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Participant Reference in inferred Participant: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
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

        public boolean resolve(PsiXml25IdCache parsedObjects) {
            if (parsedObjects.contains(this.ref)){
                Feature obj = parsedObjects.getFeature(this.ref);
                if (obj != null){
                    feature = obj;
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Feature Reference in inferred participant: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
        }

        public FileSourceLocator getSourceLocator() {
            return getInferredParticipantLocator();
        }

        public void setSourceLocator(FileSourceLocator locator) {
            throw new UnsupportedOperationException("Cannot set the source locator of a feature ref");
        }
    }
}
