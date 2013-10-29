package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Default XML implementation for Entity set
 * Notes: The equals and hashcode methods have NOT been overridden because the XmlEntitySet object is a complex object.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlEntitySet extends AbstractXmlEntitySet<Interaction,Feature,Entity> {

    @XmlLocation
    @XmlTransient
    private Locator locator;

    public XmlEntitySet() {
        super();
    }

    public XmlEntitySet(String interactorSetName) {
        super(new XmlInteractorSet(interactorSetName));
    }

    public XmlEntitySet(String interactorSetName, CvTerm bioRole) {
        super(new XmlInteractorSet(interactorSetName), bioRole);
    }

    public XmlEntitySet(String interactorSetName, Stoichiometry stoichiometry) {
        super(new XmlInteractorSet(interactorSetName), stoichiometry);
    }

    public XmlEntitySet(String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(new XmlInteractorSet(interactorSetName), bioRole, stoichiometry);
    }

    @Override
    @XmlElement(name = "names")
    public void setJAXBNames(NamesContainer value) {
        super.setJAXBNames(value);
    }

    @Override
    @XmlElement(name = "xref")
    public void setJAXBXref(XrefContainer value) {
        super.setJAXBXref(value);
    }

    @Override
    @XmlElement(name = "interactor")
    public void setJAXBInteractor(XmlInteractor interactor) {
        super.setJAXBInteractor(interactor);
    }

    @Override
    @XmlElement(name = "interactionRef")
    public void setJAXBInteractionRef(Integer value) {
        super.setJAXBInteractionRef(value);
    }

    @Override
    @XmlElement(name = "interactorRef")
    public void setJAXBInteractorRef(Integer value) {
        super.setJAXBInteractorRef(value);
    }

    @Override
    @XmlElement(name = "biologicalRole", type = XmlCvTerm.class)
    public void setJAXBBiologicalRole(CvTerm bioRole) {
        super.setJAXBBiologicalRole(bioRole);
    }

    @Override
    @XmlAttribute(name = "id", required = true)
    public void setJAXBId(int value) {
        super.setJAXBId(value);
    }

    @Override
    @XmlElement(name="attributeList")
    public void setJAXBAttributeWrapper(JAXBAttributeWrapper jaxbAttributeWrapper) {
        super.setJAXBAttributeWrapper(jaxbAttributeWrapper);
    }

    @XmlElement(name = "featureList")
    public void setJAXBFeatureWrapper(JAXBFeatureWrapper jaxbFeatureWrapper) {
        super.setFeatureWrapper(jaxbFeatureWrapper);
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

    @Override
    protected void initialiseFeatureWrapper() {
        super.setFeatureWrapper(new JAXBFeatureWrapper());
    }

    ////////////////////////////////////////////////////// classes
    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="entitySetFeatureWrapper")
    public static class JAXBFeatureWrapper extends AbstractXmlEntity.JAXBFeatureWrapper<Feature> {

        public JAXBFeatureWrapper(){
            super();
        }

        @XmlElement(type=XmlFeature.class, name="feature", required = true)
        public List<Feature> getJAXBFeatures() {
            return super.getJAXBFeatures();
        }
    }
}
