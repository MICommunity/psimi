package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.xml.AbstractExperimentRef;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The experiment/participant host organism
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "hostOrganism", propOrder = {
        "JAXBExperimentRefList"
})
public class HostOrganism extends XmlOrganism{

    private Collection<Experiment> experiments;
    private JAXBExperimentRefList jaxbExperimentRefList;

    public HostOrganism() {
    }

    public HostOrganism(int taxId) {
        super(taxId);
    }

    public HostOrganism(int taxId, String commonName) {
        super(taxId, commonName);
    }

    public HostOrganism(int taxId, String commonName, String scientificName) {
        super(taxId, commonName, scientificName);
    }

    public HostOrganism(int taxId, CvTerm cellType, CvTerm tissue, CvTerm compartment) {
        super(taxId, cellType, tissue, compartment);
    }

    public HostOrganism(int taxId, String commonName, CvTerm cellType, CvTerm tissue, CvTerm compartment) {
        super(taxId, commonName, cellType, tissue, compartment);
    }

    public HostOrganism(int taxId, String commonName, String scientificName, CvTerm cellType, CvTerm tissue, CvTerm compartment) {
        super(taxId, commonName, scientificName, cellType, tissue, compartment);
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
    @XmlElement(name = "experimentRef", type = Integer.class, required = true)
    public List<Integer> getJAXBExperimentRefList() {
        if (this.jaxbExperimentRefList == null){
           this.jaxbExperimentRefList = new JAXBExperimentRefList();
        }
        return this.jaxbExperimentRefList;
    }

    ////////////////////////////////////////////////////////////////// classes

    //////////////////////////////////////////////////////////////
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

    ////////////////////////////////////////////////////// classes
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
                    experiments.remove(this);
                    experiments.add((Experiment)obj);
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Experiment reference: "+ref+" in host organism "+(HostOrganism.this.getSourceLocator() != null? HostOrganism.this.getSourceLocator().toString():"") ;
        }

        public FileSourceLocator getSourceLocator() {
            return HostOrganism.this.getSourceLocator();
        }

        public void setSourceLocator(FileSourceLocator locator) {
            throw new UnsupportedOperationException("Cannot set the source locator of an experiment ref");
        }
    }
}
