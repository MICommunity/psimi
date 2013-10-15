package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.xml.AbstractFeatureRef;
import psidev.psi.mi.jami.xml.AbstractParticipantRef;

import javax.xml.bind.annotation.*;
import java.util.Map;

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
 *         &lt;element name="participantRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="participantFeatureRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "inferredInteractionParticipant", propOrder = {
        "JAXBParticipantFeatureRef",
        "JAXBParticipantRef"
})
public class InferredInteractionParticipant implements FileSourceContext
{

    private Feature feature;
    private Entity participant;

    private PsiXmLocator sourceLocator;

    public InferredInteractionParticipant(){
    }

    /**
     * Gets the value of the participantFeatureRef property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    @XmlElement(name = "participantFeatureRef")
    public Integer getJAXBParticipantFeatureRef() {
        if (feature instanceof AbstractXmlFeature){
            return ((AbstractXmlFeature) feature).getJAXBId();
        }
        return null;
    }

    /**
     * Sets the value of the participantFeatureRef property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setJAXBParticipantFeatureRef(Integer value) {
        if (value != null){
           this.feature = new AbstractFeatureRef(value) {
               public boolean resolve(Map<Integer, Object> parsedObjects) {
                   if (parsedObjects.containsKey(this.ref)){
                       Object obj = parsedObjects.get(this.ref);
                       if (obj instanceof Feature){
                           feature = (Feature)obj;
                           return true;
                       }
                   }
                   return false;
               }
           };
        }
    }

    /**
     * Gets the value of the participantRef property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    @XmlElement(name = "participantRef")
    public Integer getJAXBParticipantRef() {
        if (participant instanceof AbstractXmlEntity){
            return ((AbstractXmlEntity) participant).getJAXBId();
        }
        return null;
    }

    /**
     * Sets the value of the participantRef property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setJAXBParticipantRef(Integer value) {
        if (value != null){
            this.participant = new AbstractParticipantRef(value) {
                public boolean resolve(Map<Integer, Object> parsedObjects) {
                    if (parsedObjects.containsKey(this.ref)){
                        Object obj = parsedObjects.get(this.ref);
                        if (obj instanceof Entity){
                            participant = (Entity)obj;
                            return true;
                        }
                    }
                    return false;
                }
            };
        }
    }

    public Feature getFeature() {
        return feature;
    }

    public Entity getParticipant() {
        return participant;
    }

    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return sourceLocator;
    }

    public void setSaxLocator(Locator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), null);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
    }
}
