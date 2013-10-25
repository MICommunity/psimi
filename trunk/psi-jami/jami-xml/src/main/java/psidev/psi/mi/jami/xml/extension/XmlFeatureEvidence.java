package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.AbstractExperimentRef;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 * Xml implementation of a Feature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "featureEvidence", propOrder = {
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
    private Collection<Experiment> experiments;
    private boolean initialisedMethods = false;
    private XmlParticipantEvidence originalParticipant;
    @XmlLocation
    @XmlTransient
    private Locator locator;
    private JAXBExperimentRefList jaxbExperimentRefList;

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
        if (!initialisedMethods){
            initialiseDetectionMethods();
        }
        return featureDetectionMethods;
    }

    public Collection<Experiment> getExperiments() {
        if (experiments == null){
            experiments = new ArrayList<Experiment>();
        }
        return experiments;
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
    public void setJAXBFeatureDetectionMethod(CvTerm value) {
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
    @XmlElement(name="experimentRef", required = true)
    public JAXBExperimentRefList getJAXBExperimentRefList() {
        return jaxbExperimentRefList;
    }

    /**
     * Sets the value of the experimentRefList property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setJAXBExperimentRefList(JAXBExperimentRefList value) {
        this.jaxbExperimentRefList = value;
        if (value != null){
            experiments = new ArrayList<Experiment>();
            this.jaxbExperimentRefList.parent = this;
        }
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
    @XmlElements({@XmlElement(type=XmlRange.class, name="featureRange", required = true)})
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
    @XmlElements({@XmlElement(type=XmlAnnotation.class, name="attribute", required = true)})
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

    protected void initialiseDetectionMethods(){

        if (this.featureDetectionMethods == null){
            this.featureDetectionMethods = new ArrayList<CvTerm>();
        }
        else if (!this.featureDetectionMethods.isEmpty()){
            return;
        }

        if (originalParticipant != null){
            XmlInteractionEvidence interaction = originalParticipant.getOriginalInteraction();
            if (interaction != null){
                List<XmlExperiment> originalExperiments = interaction.getOriginalExperiments();
                if (originalExperiments != null && !originalExperiments.isEmpty()){
                    for (XmlExperiment exp : originalExperiments){
                        if (exp.getFeatureDetectionMethod() != null){
                            this.featureDetectionMethods.add(exp.getFeatureDetectionMethod());
                        }
                    }
                }
            }
            originalParticipant = null;
        }

        initialisedMethods = true;
    }

    protected void setOriginalParticipant(XmlParticipantEvidence p){
        this.originalParticipant = p;
        setParticipant(p);
    }

    private FileSourceLocator getFeatureLocator(){
        return getSourceLocator();
    }

    ////////////////////////////////////////////////////////////////// classes

    /**
     * The experiment ref list used by JAXB to populate experiment refs
     */
    public static class JAXBExperimentRefList extends ArrayList<Integer>{

        private XmlFeatureEvidence parent;

        public JAXBExperimentRefList(){
        }

        public JAXBExperimentRefList(int initialCapacity) {
        }

        public JAXBExperimentRefList(Collection<? extends Integer> c) {
            addAll(c);
        }

        @Override
        public boolean add(Integer val) {
            if (val == null){
                return false;
            }
            return parent.experiments.add(new ExperimentRef(val));
        }

        @Override
        public boolean addAll(Collection<? extends Integer> c) {
            if (c == null){
                return false;
            }
            boolean added = false;

            for (Integer a : c){
                if (add(a)){
                    added = true;
                }
            }
            return added;
        }

        @Override
        public void add(int index, Integer element) {
            addToSpecificIndex(index, element);
        }

        @Override
        public boolean addAll(int index, Collection<? extends Integer> c) {
            int newIndex = index;
            if (c == null){
                return false;
            }
            boolean add = false;
            for (Integer a : c){
                if (addToSpecificIndex(newIndex, a)){
                    newIndex++;
                    add = true;
                }
            }
            return add;
        }

        private boolean addToSpecificIndex(int index, Integer val) {
            if (val == null){
                return false;
            }
            ((ArrayList<Experiment>)parent.experiments).add(index, new ExperimentRef(val));
            return true;
        }
        ////////////////////////////////////////////////// classes

        /**
         * Experiment ref for experimental interactor
         */
        private class ExperimentRef extends AbstractExperimentRef{
            public ExperimentRef(int ref) {
                super(ref);
            }

            public boolean resolve(Map<Integer, Object> parsedObjects) {
                if (parsedObjects.containsKey(this.ref)){
                    Object obj = parsedObjects.get(this.ref);
                    if (obj instanceof Experiment){
                        parent.experiments.remove(this);
                        parent.experiments.add((Experiment)obj);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String toString() {
                return "Experiment reference: "+ref+" in feature "+(parent.getFeatureLocator() != null? parent.getFeatureLocator().toString():"") ;
            }

            public FileSourceLocator getSourceLocator() {
                return parent.getFeatureLocator();
            }

            public void setSourceLocator(FileSourceLocator locator) {
                throw new UnsupportedOperationException("Cannot set the source locator of an experiment ref");
            }
        }
    }
}
