package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Xml implementation of a Feature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "feature", propOrder = {
        "JAXBNames",
        "JAXBXref",
        "JAXBType",
        "JAXBFeatureDetectionMethod",
        "JAXBExperimentRefList",
        "JAXBRanges",
        "JAXBAttributes"
})
public class XmlFeatureEvidence extends AbstractXmlFeature<ExperimentalEntity, FeatureEvidence> implements FeatureEvidence{

    private List<CvTerm> featureDetectionMethods;
    private ArrayList<Integer> experimentRefList;
    private Collection<Experiment> experiments;

    public XmlFeatureEvidence() {
    }

    public XmlFeatureEvidence(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public XmlFeatureEvidence(CvTerm type) {
        super(type);
    }

    public XmlFeatureEvidence(String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
    }

    public XmlFeatureEvidence(String shortName, String fullName, String interpro) {
        super(shortName, fullName, interpro);
    }

    public XmlFeatureEvidence(CvTerm type, String interpro) {
        super(type, interpro);
    }

    public XmlFeatureEvidence(String shortName, String fullName, CvTerm type, String interpro) {
        super(shortName, fullName, type, interpro);
    }

    public Collection<CvTerm> getDetectionMethods() {
        return featureDetectionMethods;
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
    public NamesContainer getJAXBNames() {
        return super.getJAXBNames();
    }

    /**
     * Gets the value of the xref property.
     *
     * @return
     *     possible object is
     *     {@link Xref }
     *
     */
    @Override
    @XmlElement(name = "xref")
    public FeatureXrefContainer getJAXBXref() {
        return super.getJAXBXref();
    }

    @Override
    @XmlElement(name = "featureType", type = XmlCvTerm.class)
    public CvTerm getJAXBType() {
        return super.getType();
    }

    /**
     * Gets the value of the featureDetectionMethod property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    @XmlElement(name = "featureDetectionMethod", type = XmlCvTerm.class)
    public CvTerm getJAXBFeatureDetectionMethod() {
        if (featureDetectionMethods == null){
            return null;
        }
        return featureDetectionMethods.iterator().next();
    }

    /**
     * Sets the value of the featureDetectionMethod property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlCvTerm }
     *
     */
    public void setJAXBFeatureDetectionMethod(XmlCvTerm value) {
        if (featureDetectionMethods == null){
            this.featureDetectionMethods = new ArrayList<CvTerm>();
        }
        if (!featureDetectionMethods.isEmpty()){
            featureDetectionMethods.remove(0);
        }
        if (value != null){
            this.featureDetectionMethods.add(0, value);
        }
    }

    /**
     * Gets the value of the experimentRefList property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    @XmlElementWrapper(name="experimentRefList")
    @XmlElement(name="experimentRef")
    public ArrayList<Integer> getJAXBExperimentRefList() {
        return experimentRefList;
    }

    /**
     * Sets the value of the experimentRefList property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setJAXBExperimentRefList(ArrayList<Integer> value) {
        this.experimentRefList = value;
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
    public ArrayList<Range> getJAXBRanges() {
        return super.getJAXBRanges();
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
    public ArrayList<Annotation> getJAXBAttributes() {
        return super.getJAXBAttributes();
    }

    /**
     * Gets the value of the id property.
     *
     */
    @Override
    @XmlAttribute(name = "id", required = true)
    public int getJAXBId() {
        return super.getJAXBId();
    }

    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return super.getSaxLocator();
    }

    public void setSaxLocator(Locator sourceLocator) {
        super.setSaxLocator(sourceLocator);
    }

    public Collection<Experiment> getExperiments() {
        if (experiments == null){
            experiments = new ArrayList<Experiment>();
        }
        if (experiments.isEmpty() && this.experimentRefList != null && !this.experimentRefList.isEmpty()){
            resolveExperimentReferences();
        }
        return experiments;
    }

    private void resolveExperimentReferences() {
        for (Integer id : this.experimentRefList){
            if (getMapOfReferencedObjects().containsKey(id)){
                Object o = getMapOfReferencedObjects().get(id);
                if (o instanceof Experiment){
                    this.experiments.add((Experiment)o);
                }
            }
        }
    }
}
