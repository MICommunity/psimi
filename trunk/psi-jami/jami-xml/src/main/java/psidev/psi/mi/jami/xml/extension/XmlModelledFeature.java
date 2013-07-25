package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The xml implementation of a modelledFeature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "feature", propOrder = {
        "names",
        "xref",
        "type",
        "featureRanges",
        "attributes"
})
public class XmlModelledFeature extends AbstractXmlFeature<ModelledParticipant, ModelledFeature> implements ModelledFeature {

    public XmlModelledFeature() {
    }

    public XmlModelledFeature(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public XmlModelledFeature(CvTerm type) {
        super(type);
    }

    public XmlModelledFeature(String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
    }

    public XmlModelledFeature(String shortName, String fullName, String interpro) {
        super(shortName, fullName, interpro);
    }

    public XmlModelledFeature(CvTerm type, String interpro) {
        super(type, interpro);
    }

    public XmlModelledFeature(String shortName, String fullName, CvTerm type, String interpro) {
        super(shortName, fullName, type, interpro);
    }

    /**
     * Gets the value of the names property.
     *
     * @return
     *     possible object is
     *     {@link NamesContainer }
     *
     */
    @Override
    @XmlElement(name = "names")
    public NamesContainer getNames() {
        return super.getNames();
    }

    @Override
    @XmlTransient
    public String getShortName() {
        return super.getShortName();
    }

    @Override
    @XmlTransient
    public String getFullName() {
        return super.getFullName();
    }

    /**
     * Gets the value of the xref property.
     *
     * @return
     *     possible object is
     *     {@link psidev.psi.mi.jami.model.Xref }
     *
     */
    @Override
    @XmlElement(name = "xref")
    public FeatureXrefContainer getXref() {
        return super.getXref();
    }

    @Override
    @XmlTransient
    public String getInterpro() {
        return super.getInterpro();
    }

    @Override
    @XmlTransient
    public Collection<Xref> getIdentifiers() {
        return super.getIdentifiers();
    }

    @Override
    @XmlTransient
    public Collection<Xref> getXrefs() {
        return super.getXrefs();
    }

    @Override
    @XmlElement(name = "featureType", type = XmlCvTerm.class)
    public CvTerm getType() {
        return super.getType();
    }

    @Override
    @XmlTransient
    public Collection<Annotation> getAnnotations() {
        return super.getAnnotations();
    }

    @Override
    @XmlTransient
    public Collection<Range> getRanges() {
        return super.getRanges();
    }

    @Override
    @XmlTransient
    public CvTerm getInteractionEffect() {
        return super.getInteractionEffect();
    }

    @Override
    @XmlTransient
    public CvTerm getInteractionDependency() {
        return super.getInteractionDependency();
    }

    @Override
    @XmlTransient
    public ModelledParticipant getParticipant() {
        return super.getParticipant();
    }

    @Override
    @XmlTransient
    public Collection<ModelledFeature> getLinkedFeatures() {
        return super.getLinkedFeatures();
    }

    /**
     * Gets the value of the featureRangeList property.
     *
     * @return
     *     possible object is
     *     {@link XmlRange }
     *
     */
    @XmlElementWrapper(name="featureRangeList", required = true)
    @XmlElement(name="featureRange", required = true)
    @XmlElementRefs({@XmlElementRef(type=XmlRange.class)})
    @Override
    public ArrayList<Range> getFeatureRanges() {
        return super.getFeatureRanges();
    }

    /**
     * Gets the value of the attributeList property.
     *
     * @return
     *     possible object is
     *     {@link XmlAnnotation }
     *
     */
    @XmlElementWrapper(name="attributeList")
    @XmlElement(name="attribute", required = true)
    @XmlElementRefs({@XmlElementRef(type=XmlAnnotation.class)})
    @Override
    public ArrayList<Annotation> getAttributes() {
        return super.getAttributes();
    }

    /**
     * Gets the value of the id property.
     *
     */
    @Override
    @XmlAttribute(name = "id", required = true)
    public int getId() {
        return super.getId();
    }

    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return super.getSaxLocator();
    }

    public void setSaxLocator(Locator sourceLocator) {
        super.setSaxLocator(sourceLocator);
    }

    @Override
    @XmlTransient
    public FileSourceLocator getSourceLocator() {
        return super.getSourceLocator();
    }
}
