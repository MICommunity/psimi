package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.ModelledConfidence;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.xml.AbstractExperimentRef;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    @XmlElement(name="experimentRef", required = true, type = Integer.class)
    public List<Integer> getJAXBExperimentRefList() {
        if (this.jaxbExperimentRefList == null){
           this.jaxbExperimentRefList = new JAXBExperimentRefList();
        }
        return jaxbExperimentRefList;
    }

    /**
     * The experiment ref list used by JAXB to populate experiment refs
     */
    private class JAXBExperimentRefList extends ArrayList<Integer>{

        public JAXBExperimentRefList(){
            super();
            experiments = new ArrayList<Experiment>();
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
            return "Experiment reference: "+ref+" in confidence "+(XmlModelledConfidence.this.getSourceLocator() != null? XmlModelledConfidence.this.getSourceLocator().toString():"") ;
        }

        public FileSourceLocator getSourceLocator() {
            return XmlModelledConfidence.this.getSourceLocator();
        }

        public void setSourceLocator(FileSourceLocator locator) {
            throw new UnsupportedOperationException("Cannot set the source locator of an experiment ref");
        }
    }
}
