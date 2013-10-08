package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.xml.XmlEntryContext;

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

    private Map<Integer, Object> mapOfReferencedObjects;
    private Collection<Integer> experimentRefList;
    private Collection<Experiment> experiments;

    public HostOrganism() {
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public HostOrganism(int taxId) {
        super(taxId);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public HostOrganism(int taxId, String commonName) {
        super(taxId, commonName);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public HostOrganism(int taxId, String commonName, String scientificName) {
        super(taxId, commonName, scientificName);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public HostOrganism(int taxId, CvTerm cellType, CvTerm tissue, CvTerm compartment) {
        super(taxId, cellType, tissue, compartment);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public HostOrganism(int taxId, String commonName, CvTerm cellType, CvTerm tissue, CvTerm compartment) {
        super(taxId, commonName, cellType, tissue, compartment);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public HostOrganism(int taxId, String commonName, String scientificName, CvTerm cellType, CvTerm tissue, CvTerm compartment) {
        super(taxId, commonName, scientificName, cellType, tissue, compartment);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
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
    public void setJAXBExperimentRefList(Collection<Integer> value) {
        this.experimentRefList = value;
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
            if (this.mapOfReferencedObjects.containsKey(id)){
                Object o = this.mapOfReferencedObjects.get(id);
                if (o instanceof Experiment){
                    this.experiments.add((Experiment)o);
                }
            }
        }
    }
}
