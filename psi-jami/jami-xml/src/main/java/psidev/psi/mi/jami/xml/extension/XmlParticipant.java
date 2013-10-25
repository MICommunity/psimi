package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.AbstractEntityAttributeList;
import psidev.psi.mi.jami.xml.AbstractEntityFeatureList;

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

    @XmlLocation
    @XmlTransient
    private Locator locator;
    private JAXBAttributeList jaxbAttributeList;
    private JAXBFeatureList jaxbFeatureList;

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

    /**
     * Gets the value of the featureList property.
     *
     * @return
     *     possible object is
     *     {@link AbstractXmlFeature }
     *
     */
    @XmlElementWrapper(name="featureList")
    @XmlElement(type=XmlFeature.class, name="feature", required = true)
    public JAXBFeatureList getJAXBFeatures() {
        return this.jaxbFeatureList;
    }

    /**
     * Sets the value of the featureList property.
     *
     * @param value
     *     allowed object is
     *     {@link AbstractXmlFeature }
     *
     */
    public void setJAXBFeatures(JAXBFeatureList value) {
        this.jaxbFeatureList = value;
        if (value != null){
            this.jaxbFeatureList.setParent(this);
        }
    }

    @Override
    @XmlElement(name = "interactor", type = XmlInteractor.class)
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
    public JAXBAttributeList getJAXBAttributes() {
        return this.jaxbAttributeList;
    }

    /**
     * Sets the value of the jaxbAttributeList property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlAnnotation }
     *
     */
    public void setJAXBAttributes(JAXBAttributeList value) {
        this.jaxbAttributeList = value;
        if (value != null){
            this.jaxbAttributeList.setParent(this);
        }
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
    protected void initialiseAnnotations() {
        if (jaxbAttributeList != null){
            super.initialiseAnnotationsWith(new ArrayList<Annotation>(jaxbAttributeList));
            this.jaxbAttributeList = null;
        }else{
            super.initialiseAnnotations();
        }
    }

    @Override
    protected void initialiseFeatures(){
        if (jaxbFeatureList != null){
            super.initialiseFeaturesWith(new ArrayList<Feature>(jaxbFeatureList));
            this.jaxbFeatureList = null;
        }else{
            super.initialiseFeatures();
        }
    }

    /**
     * The attribute list used by JAXB to populate participant annotations
     */
    public static class JAXBAttributeList extends AbstractEntityAttributeList<XmlParticipant> {

        public JAXBAttributeList(){
            super();
        }
    }

    /**
     * The feature list used by JAXB to populate participant features
     */
    public static class JAXBFeatureList extends AbstractEntityFeatureList<Feature, XmlParticipant>{

        public JAXBFeatureList(){
            super();
        }
    }
}
