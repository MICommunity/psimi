package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.xml.AbstractExperimentRef;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
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
    @XmlElement(name="experimentRef", required = true)
    public JAXBExperimentRefList getJAXBExperimentRefList() {
        return this.jaxbExperimentRefList;
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
    }

    private FileSourceLocator getHostOrganismLocator(){
        return getSourceLocator();
    }

    ////////////////////////////////////////////////////////////////// classes

    /**
     * The experiment ref list used by JAXB to populate experiment refs
     */
    public class JAXBExperimentRefList extends ArrayList<Integer>{

        public JAXBExperimentRefList(){
            experiments = new ArrayList<Experiment>();
        }

        public JAXBExperimentRefList(int initialCapacity) {
            experiments = new ArrayList<Experiment>(initialCapacity);
        }

        public JAXBExperimentRefList(Collection<? extends Integer> c) {
            experiments = new ArrayList<Experiment>(c.size());
            addAll(c);
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
            ((ArrayList<Experiment>)experiments).add(index, new ExperimentRef(val));
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
                    experiments.remove(this);
                    experiments.add((Experiment)obj);
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Experiment reference: "+ref+" in host organism "+(getHostOrganismLocator() != null? getHostOrganismLocator().toString():"") ;
        }

        public FileSourceLocator getSourceLocator() {
            return getHostOrganismLocator();
        }

        public void setSourceLocator(FileSourceLocator locator) {
            throw new UnsupportedOperationException("Cannot set the source locator of an experiment ref");
        }
    }
}
