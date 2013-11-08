package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.ModelledConfidence;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.xml.AbstractExperimentRef;
import psidev.psi.mi.jami.xml.PsiXml25IdIndex;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Xml implementation of ModelledConfidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlModelledConfidence extends XmlConfidence implements ModelledConfidence{
    private JAXBExperimentRefWrapper jaxbExperimentRefWrapper;

    public XmlModelledConfidence() {
        super();

    }

    public XmlModelledConfidence(XmlOpenCvTerm type, String value) {
        super(type, value);
    }

    public Collection<Publication> getPublications() {
        if (this.jaxbExperimentRefWrapper == null){
            this.jaxbExperimentRefWrapper = new JAXBExperimentRefWrapper();
        }
        return this.jaxbExperimentRefWrapper.publications;
    }

    public Collection<Experiment> getExperiments() {
        if (this.jaxbExperimentRefWrapper == null){
            this.jaxbExperimentRefWrapper = new JAXBExperimentRefWrapper();
        }
        return this.jaxbExperimentRefWrapper.experiments;
    }

    /**
     * Gets the value of the experimentRefList property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    @XmlElement(name="experimentRefList")
    public void setJAXBExperimentRefWrapper(JAXBExperimentRefWrapper wrapper) {
        this.jaxbExperimentRefWrapper = wrapper;
    }

    ////////////////////////////////////////////////////////////////// classes

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name = "confidenceExperimentRefList")
    public static class JAXBExperimentRefWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private JAXBExperimentRefList jaxbExperimentRefs;
        private List<Experiment> experiments;
        private Collection<Publication> publications;

        public JAXBExperimentRefWrapper(){
            experiments = new ArrayList<Experiment>();
            publications = new ArrayList<Publication>();
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
                ((List<Experiment>)experiments).add(index, new ExperimentRef(val));
                return true;
            }

            @Override
            public String toString() {
                return "Confidence Experiment List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
            }
        }
        /**
         * Experiment ref for experimental interactor
         */
        private class ExperimentRef extends AbstractExperimentRef{
            public ExperimentRef(int ref) {
                super(ref);
            }

            public boolean resolve(PsiXml25IdIndex parsedObjects) {
                if (parsedObjects.contains(this.ref)){
                    Object obj = parsedObjects.get(this.ref);
                    if (obj instanceof Experiment){
                        Experiment exp = (Experiment)obj;
                        experiments.remove(this);
                        experiments.add(exp);
                        if (exp.getPublication() != null){
                            publications.add(exp.getPublication());
                        }
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String toString() {
                return "Confidence Experiment Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
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
