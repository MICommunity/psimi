package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * XML implementation of ModelledParticipant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "modelledParticipant", propOrder = {
        "JAXBNames",
        "JAXBXref",
        "JAXBInteractionRef",
        "JAXBInteractor",
        "JAXBInteractorRef",
        "JAXBBiologicalRole",
        "JAXBFeatures",
        "JAXBAttributes"
})
public class XmlModelledParticipant extends AbstractXmlParticipant<ModelledInteraction, ModelledFeature> implements ModelledParticipant{

    @XmlLocation
    @XmlTransient
    protected Locator locator;

    public XmlModelledParticipant() {
    }

    public XmlModelledParticipant(Interactor interactor) {
        super(interactor);
    }

    public XmlModelledParticipant(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public XmlModelledParticipant(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }

    public XmlModelledParticipant(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    @Override
    @XmlElement(name = "names")
    public NamesContainer getJAXBNames() {
        return super.getJAXBNames();
    }

    @Override
    @XmlElement(name = "xref")
    public XrefContainer getJAXBXref() {
        return super.getJAXBXref();
    }

    @Override
    @XmlElement(name = "interactionRef")
    public Integer getJAXBInteractionRef() {
        return super.getJAXBInteractionRef();
    }

    @Override
    @XmlElement(name = "interactorRef")
    public Integer getJAXBInteractorRef() {
        return super.getJAXBInteractorRef();
    }

    @Override
    @XmlElement(name = "biologicalRole")
    public CvTerm getJAXBBiologicalRole() {
        return super.getJAXBBiologicalRole();
    }

    @Override
    @XmlElement(name = "interactor")
    public XmlInteractor getJAXBInteractor() {
        return super.getJAXBInteractor();
    }

    @Override
    @XmlAttribute(name = "id", required = true)
    public int getJAXBId() {
        return super.getJAXBId();
    }

    /**
     * Gets the value of the jaxbAttributeList property.
     *
     * @return
     *     possible object is
     *     {@link XmlAnnotation }
     *
     */
    @XmlElementWrapper(name="attributeList")
    @XmlElement(type=XmlAnnotation.class, name="attribute", required = true)
    public List<Annotation> getJAXBAttributes() {
        return super.getJAXBAttributes();
    }

    /**
     * Gets the value of the featureList property.
     *
     * @return
     *     possible object is
     *     {@link AbstractXmlFeature }
     *
     */
    @XmlElementWrapper(name = "featureList")
    @XmlElement(type=XmlModelledFeature.class, name="feature", required = true)
    public List<ModelledFeature> getJAXBFeatures() {
        return super.getJAXBFeatures();
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        if (super.getSourceLocator() == null && locator != null){
            super.setSourceLocator(new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), getJAXBId()));
        }
        return super.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            super.setSourceLocator(null);
        }
        else{
            super.setSourceLocator(new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), getJAXBId()));
        }
    }
}
