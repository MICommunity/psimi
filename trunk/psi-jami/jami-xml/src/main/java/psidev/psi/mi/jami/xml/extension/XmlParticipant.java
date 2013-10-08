package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * Xml implementation of a simple participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "participant", propOrder = {
        "JAXBNames",
        "JAXBXref",
        "JAXBInteractionRef",
        "JAXBInteractor",
        "JAXBInteractorRef",
        "JAXBBiologicalRole",
        "JAXBFeatures",
        "JAXBAttributes"
})
public class XmlParticipant extends AbstractXmlParticipant<Interaction,Feature> {

    public XmlParticipant() {
    }

    public XmlParticipant(Interactor interactor) {
        super(interactor);
    }

    public XmlParticipant(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public XmlParticipant(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public XmlParticipant(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }

    @Override
    @XmlAttribute(name = "names")
    public NamesContainer getJAXBNames() {
        return super.getJAXBNames();
    }

    @Override
    @XmlAttribute(name = "xref")
    public XrefContainer getJAXBXref() {
        return super.getJAXBXref();
    }

    @Override
    @XmlAttribute(name = "interactionRef")
    public Integer getJAXBInteractionRef() {
        return super.getJAXBInteractionRef();
    }

    @Override
    @XmlAttribute(name = "interactorRef")
    public Integer getJAXBInteractorRef() {
        return super.getJAXBInteractorRef();
    }

    @Override
    @XmlAttribute(name = "biologicalRole")
    public CvTerm getJAXBBiologicalRole() {
        return super.getJAXBBiologicalRole();
    }

    @Override
    @XmlElementWrapper(name="featureList")
    @XmlElement(name="feature", required = true)
    @XmlElementRefs({ @XmlElementRef(type=XmlFeature.class)})
    public ArrayList<Feature> getJAXBFeatures() {
        return super.getJAXBFeatures();
    }

    @Override
    @XmlAttribute(name = "interactor")
    public XmlInteractor getJAXBInteractor() {
        return super.getJAXBInteractor();
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
}
