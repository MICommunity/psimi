package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.ModelledConfidence;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.xml.AbstractExperimentRef;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Xml implementation of ModelledConfidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "modelledConfidence",propOrder = {
        "JAXBExperimentRefList"
})
public class XmlModelledConfidence extends XmlConfidence implements ModelledConfidence{
    private Collection<Experiment> experiments;
    private Collection<Publication> publications;
    private JAXBExperimentRefList jaxbExperimentRefList;

    public XmlModelledConfidence() {
        super();

    }

    public XmlModelledConfidence(XmlOpenCvTerm type, String value) {
        super(type, value);
    }

    public Collection<Publication> getPublications() {
        if (publications == null){
            this.publications = new ArrayList<Publication>();
        }
        return this.publications;
    }

    public Collection<Experiment> getExperiments() {
        if (experiments == null){
            experiments = new ArrayList<Experiment>();
        }
        return experiments;
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
    @XmlElements({@XmlElement(name="experimentRef", required = true)})
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

    private FileSourceLocator getConfidenceLocator(){
        return getSourceLocator();
    }

    /**
     * The experiment ref list used by JAXB to populate experiment refs
     */
    public static class JAXBExperimentRefList extends ArrayList<Integer>{

        private XmlModelledConfidence parent;

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
                        Experiment exp = (Experiment)obj;
                        parent.experiments.remove(this);
                        parent.experiments.add(exp);
                        if (exp.getPublication() != null){
                            parent.publications.add(exp.getPublication());
                        }
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String toString() {
                return "Experiment reference: "+ref+" in confidence "+(parent.getConfidenceLocator() != null? parent.getConfidenceLocator().toString():"") ;
            }

            public FileSourceLocator getSourceLocator() {
                return parent.getConfidenceLocator();
            }

            public void setSourceLocator(FileSourceLocator locator) {
                throw new UnsupportedOperationException("Cannot set the source locator of an experiment ref");
            }
        }
    }
}
