package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.xml.XmlEntryContext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
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
public class InferredInteractionParticipant
{

    private Integer participantFeatureRef;
    private Integer participantRef;

    private Feature feature;
    private Entity participant;

    private Map<Integer, Object> mapOfReferencedObjects;

    public InferredInteractionParticipant(){
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
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
        return participantFeatureRef;
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
        this.participantFeatureRef = value;
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
        return participantRef;
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
        this.participantRef = value;
    }

    public Feature getFeature() {
        if (feature == null && participantFeatureRef != null){
            resolveFeatureReferences();
        }
        return feature;
    }

    public Entity getParticipant() {
        if (participant == null && participantRef != null){
            resolveParticipantReferences();
        }
        return participant;
    }

    private void resolveFeatureReferences() {
        if (this.mapOfReferencedObjects.containsKey(participantFeatureRef)){
            Object o = this.mapOfReferencedObjects.get(participantFeatureRef);
            if (o instanceof Feature){
                this.feature = (Feature)o;
            }
        }
    }
    private void resolveParticipantReferences() {
        if (this.mapOfReferencedObjects.containsKey(participantRef)){
            Object o = this.mapOfReferencedObjects.get(participantRef);
            if (o instanceof Entity){
                this.participant = (Entity)o;
            }
        }
    }
}
