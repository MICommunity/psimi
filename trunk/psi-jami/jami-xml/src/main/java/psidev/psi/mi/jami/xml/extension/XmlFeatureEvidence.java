package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.ExperimentalEntity;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.xml.cache.PsiXml25IdCache;
import psidev.psi.mi.jami.xml.reference.AbstractExperimentRef;

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
public class XmlFeatureEvidence extends AbstractXmlFeature<ExperimentalEntity, FeatureEvidence> implements ExtendedPsi25FeatureEvidence{

    private List<CvTerm> featureDetectionMethods;
    private boolean initialisedMethods = false;
    private XmlParticipantEvidence originalParticipant;
    @XmlLocation
    @XmlTransient
    private Locator locator;
    private JAXBExperimentRefWrapper jaxbExperimentRefWrapper;

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

    public List<Experiment> getExperiments() {
        if (jaxbExperimentRefWrapper == null){
            jaxbExperimentRefWrapper = new JAXBExperimentRefWrapper();
        }
        return jaxbExperimentRefWrapper.experiments;
    }

    @Override
    @XmlElement(name = "names")
    public void setJAXBNames(NamesContainer value) {
        super.setJAXBNames(value);
    }

    @Override
    @XmlElement(name = "xref")
    public void setJAXBXref(FeatureXrefContainer value) {
        super.setJAXBXref(value);
    }

    @Override
    @XmlElement(name = "featureType", type = XmlCvTerm.class)
    public void setJAXBType(CvTerm type) {
        super.setJAXBType(type);
    }

    @Override
    @XmlElement(name="attributeList")
    public void setJAXBAttributeWrapper(JAXBAttributeWrapper jaxbAttributeWrapper) {
        super.setJAXBAttributeWrapper(jaxbAttributeWrapper);
    }

    @Override
    @XmlElement(name="featureRangeList", required = true)
    public void setJAXBRangeWrapper(JAXBRangeWrapper jaxbRangeWrapper) {
        super.setJAXBRangeWrapper(jaxbRangeWrapper);
    }

    @XmlAttribute(name = "id", required = true)
    public void setJAXBId(int id) {
        super.setId(id);
    }

    /**
     * Sets the value of the featureDetectionMethod property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlCvTerm }
     *
     */
    @XmlElement(name = "featureDetectionMethod", type = XmlCvTerm.class)
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

    @XmlElement(name="experimentRefList")
    public void setJAXBExperimentRefWrapper(JAXBExperimentRefWrapper wrapper) {
        this.jaxbExperimentRefWrapper = wrapper;
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        if (super.getSourceLocator() == null && locator != null){
            super.setSourceLocator(new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), getId()));
        }
        return super.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            super.setSourceLocator(null);
        }
        else{
            super.setSourceLocator(new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), getId()));
        }
    }

    protected void initialiseDetectionMethods(){

        if (this.featureDetectionMethods == null){
            this.featureDetectionMethods = new ArrayList<CvTerm>();
        }
        else if (!this.featureDetectionMethods.isEmpty()){
            initialisedMethods = true;
            return;
        }

        if (originalParticipant != null){
            AbstractXmlInteractionEvidence interaction = originalParticipant.getOriginalInteraction();
            if (interaction != null){
                List<ExtendedPsi25Experiment> originalExperiments = interaction.getOriginalExperiments();
                if (originalExperiments != null && !originalExperiments.isEmpty()){
                    for (ExtendedPsi25Experiment exp : originalExperiments){
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

    ////////////////////////////////////////////////////////////////// classes

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name = "featureExperimentRefList")
    public static class JAXBExperimentRefWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private JAXBExperimentRefList jaxbExperimentRefs;
        private List<Experiment> experiments;

        public JAXBExperimentRefWrapper(){
            experiments = new ArrayList<Experiment>();
        }

        @Override
        public Locator sourceLocation() {
            return (Locator)getSourceLocator();
        }

        public FileSourceLocator getSourceLocator() {
            if (sourceLocator == null && locator != null){
                sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
            }
            return sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else{
                this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        @XmlElement(name = "experimentRef", type = Integer.class, required = true)
        public List<Integer> getJAXBExperimentRefs() {
            if (this.jaxbExperimentRefs == null){
                this.jaxbExperimentRefs = new JAXBExperimentRefList();
            }
            return jaxbExperimentRefs;
        }

        //////////////////////////////////////////////////////////////
        /**
         * The experiment ref list used by JAXB to populate experiment refs
         */
        private class JAXBExperimentRefList extends ArrayList<Integer>{

            public JAXBExperimentRefList(){
                super();
            }

            @Override
            public boolean add(Integer val) {
                if (val == null){
                    return false;
                }
                return experiments.add(new ExperimentRef(val));
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
                experiments.add(index, new ExperimentRef(val));
                return true;
            }
        }

        ////////////////////////////////////////////////// classes

        /**
         * Experiment ref
         */
        private class ExperimentRef extends AbstractExperimentRef{
            public ExperimentRef(int ref) {
                super(ref);
            }

            public boolean resolve(PsiXml25IdCache parsedObjects) {
                if (parsedObjects.contains(this.ref)){
                    Experiment obj = parsedObjects.getExperiment(this.ref);
                    if (obj != null){
                        experiments.remove(this);
                        experiments.add(obj);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String toString() {
                return "Experiment reference: "+ref+" in feature "+(getSourceLocator() != null? getSourceLocator().toString():"") ;
            }

            public FileSourceLocator getSourceLocator() {
                return sourceLocator;
            }

            public void setSourceLocator(FileSourceLocator locator) {
                throw new UnsupportedOperationException("Cannot set the source locator of an experiment ref");
            }
        }
    }
}
