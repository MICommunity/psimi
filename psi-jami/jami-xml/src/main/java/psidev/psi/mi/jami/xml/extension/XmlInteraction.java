package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * Xml implementation of interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "interaction", propOrder = {
        "JAXBNames",
        "JAXBXref",
        "JAXBParticipants",
        "JAXBInferredInteractions",
        "JAXBInteractionTypes",
        "JAXBIntraMolecular",
        "JAXBAttributes"
})
public class XmlInteraction extends AbstractXmlInteraction<Participant>{

    public XmlInteraction() {
        super();
    }

    public XmlInteraction(String shortName) {
        super(shortName);
    }

    public XmlInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }

    @Override
    @XmlAttribute(name = "names")
    public NamesContainer getJAXBNames() {
        return super.getJAXBNames();
    }

    @Override
    @XmlAttribute(name = "xref")
    public InteractionXrefContainer getJAXBXref() {
        return super.getJAXBXref();
    }

    @Override
    @XmlAttribute(name = "id", required = true)
    public int getJAXBId() {
        return super.getJAXBId();
    }

    @Override
    @XmlElementWrapper(name="attributeList")
    @XmlElement(name="attribute", required = true)
    @XmlElementRefs({ @XmlElementRef(type=XmlAnnotation.class)})
    public ArrayList<Annotation> getJAXBAttributes() {
        return super.getJAXBAttributes();
    }

    @Override
    @XmlElement(name = "intraMolecular", defaultValue = "false")
    public Boolean getJAXBIntraMolecular() {
        return super.getJAXBIntraMolecular();
    }

    @Override
    @XmlElementWrapper(name="participantList")
    @XmlElement(name="participant", required = true)
    @XmlElementRefs({ @XmlElementRef(type=XmlParticipant.class)})
    public ArrayList<Participant> getJAXBParticipants() {
        return super.getJAXBParticipants();
    }

    @Override
    @XmlElementWrapper(name="inferredInteractionList")
    @XmlElement(name="inferredInteraction", required = true)
    public ArrayList<InferredInteraction> getJAXBInferredInteractions() {
        return super.getJAXBInferredInteractions();
    }

    @Override
    @XmlElement(name="interactionType")
    public ArrayList<CvTerm> getJAXBInteractionTypes() {
        return super.getJAXBInteractionTypes();
    }

    @Override
    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return super.getSaxLocator();
    }
}
