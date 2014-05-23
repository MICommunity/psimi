package psidev.psi.mi.jami.xml.model.extension.xml300;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.model.extension.PsiXmLocator;
import psidev.psi.mi.jami.xml.model.extension.XmlModelledFeature;
import psidev.psi.mi.jami.xml.model.reference.AbstractFeatureRef;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class that wraps a list of linkedFeatures which all bind together
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/14</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class BindingFeatures implements FileSourceContext, Locatable {

    private JAXBBindingFeatureRefWrapper jaxbBindingFeatureRefWrapper;
    private PsiXmLocator sourceLocator;
    @XmlLocation
    @XmlTransient
    private Locator locator;

    public BindingFeatures() {
        XmlEntryContext.getInstance().registerBindingFeature(this);
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

    public Collection<ModelledFeature> getLinkedFeatures() {
        if (jaxbBindingFeatureRefWrapper == null){
            jaxbBindingFeatureRefWrapper = new JAXBBindingFeatureRefWrapper();
        }
        return jaxbBindingFeatureRefWrapper.linkedFeatures;
    }

    /**
     * Gets the value of the experimentRefList property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    @XmlElement(name="bindingFeatures")
    public void setJAXBBindingFeaturesRefWrapper(JAXBBindingFeatureRefWrapper wrapper) {
        this.jaxbBindingFeatureRefWrapper = wrapper;
    }

    ////////////////////////////////////////////////////////////////// classes

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name = "bindingFeatureRefList")
    public static class JAXBBindingFeatureRefWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private JAXBFeatureRefList jaxbFeatureRefs;
        private List<ModelledFeature> linkedFeatures;

        public JAXBBindingFeatureRefWrapper(){
            linkedFeatures = new ArrayList<ModelledFeature>();
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

        @XmlElement(name = "participantFeatureRef", type = Integer.class, required = true)
        public List<Integer> getJAXBFeatureRefs() {
            if (this.jaxbFeatureRefs == null){
                this.jaxbFeatureRefs = new JAXBFeatureRefList();
            }
            return jaxbFeatureRefs;
        }

        @Override
        public String toString() {
            return "Binding features: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }

        /**
         * The participant feature ref list used by JAXB to populate feature refs
         */
        private class JAXBFeatureRefList extends ArrayList<Integer>{

            public JAXBFeatureRefList(){
                super();
                linkedFeatures = new ArrayList<ModelledFeature>();
            }

            @Override
            public boolean add(Integer val) {
                if (val == null){
                    return false;
                }
                return linkedFeatures.add(new ParticipantFeatureRef(val));
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
                ((ArrayList<ModelledFeature>) linkedFeatures).add(index, new ParticipantFeatureRef(val));
                return true;
            }

            @Override
            public String toString() {
                return "Inferred Interaction Experiment List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
            }
        }

        ///////////////////////////////////////////
        /**
         * Experiment ref for experimental interactor
         */
        private class ParticipantFeatureRef extends AbstractFeatureRef<ModelledParticipant, ModelledFeature> implements ModelledFeature{
            public ParticipantFeatureRef(int ref) {
                super(ref);
            }

            public boolean resolve(PsiXmlIdCache parsedObjects) {
                if (parsedObjects.contains(this.ref)){
                    Feature obj = parsedObjects.getFeature(this.ref);
                    if (obj instanceof ModelledFeature){
                        linkedFeatures.remove(this);
                        linkedFeatures.add((ModelledFeature)obj);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String toString() {
                return "Binding feature Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
            }

            @Override
            protected void initialiseFeatureDelegate() {
                XmlModelledFeature modelled = new XmlModelledFeature();
                modelled.setId(this.ref);
                setDelegate(modelled);
            }

            public FileSourceLocator getSourceLocator() {
                return sourceLocator;
            }

            public void setSourceLocator(FileSourceLocator locator) {
                throw new UnsupportedOperationException("Cannot set the source locator of a binding feature ref");
            }
        }
    }
}
