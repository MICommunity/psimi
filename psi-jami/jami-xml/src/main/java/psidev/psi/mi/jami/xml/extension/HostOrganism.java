package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.CvTerm;

import javax.xml.bind.annotation.*;
import java.util.Collection;

/**
 * The experiment/participant host organism
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "hostOrganism", propOrder = {
        "experimentRefList"
})
public class HostOrganism extends XmlOrganism{

    private Collection<Integer> experimentRefList;

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
    @XmlElement(name="experimentRef")
    public Collection<Integer> getExperimentRefList() {
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
    public void setExperimentRefList(Collection<Integer> value) {
        this.experimentRefList = value;
    }
}
