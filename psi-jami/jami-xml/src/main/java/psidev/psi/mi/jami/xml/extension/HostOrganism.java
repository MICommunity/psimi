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
    public Collection<Integer> getJAXBExperimentRefList() {
        if (experiments == null || experiments.isEmpty()){
            return null;
        }
        ArrayList<Integer> references = new ArrayList<Integer>(experiments.size());
        for (Experiment exp : experiments){
            if (exp instanceof XmlExperiment){
                references.add(((XmlExperiment) exp).getId());
            }
        }
        if (references.isEmpty()){
            return null;
        }
        return references;
    }

    /**
     * Sets the value of the experimentRefList property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setJAXBExperimentRefList(Collection<Integer> value) {
        if (value != null){
            for (Integer val : value){
                getExperiments().add(new AbstractExperimentRef(val) {
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
                });
            }
        }
    }

    private FileSourceLocator getHostOrganismLocator(){
        return getSourceLocator();
    }

    public Collection<Experiment> getExperiments() {
        if (experiments == null){
            experiments = new ArrayList<Experiment>();
        }
        return experiments;
    }
}
