package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.XmlEntryContext;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Xml implementation of ModelledParameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "",propOrder = {
        "JAXBExperimentRef"
})
public class XmlModelledParameter extends XmlParameter implements ModelledParameter{
    private Map<Integer, Object> mapOfReferencedObjects;
    private Integer experimentRef;
    private Experiment experiment;
    private Collection<Publication> publications;

    public XmlModelledParameter() {
        super();
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlModelledParameter(CvTerm type, ParameterValue value, BigDecimal uncertainty, CvTerm unit) {
        super(type, value, uncertainty, unit);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public Collection<Publication> getPublications() {
        if (publications == null){
            this.publications = new ArrayList<Publication>();
        }
        if (publications.isEmpty() && experimentRef != null){
            resolveExperimentReferences();
        }
        return this.publications;
    }

    /**
     * Gets the value of the experimentRefList property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    @XmlElement(name="experimentRef")
    public Integer getJAXBExperimentRef() {
        return experimentRef;
    }

    /**
     * Sets the value of the experimentRefList property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setJAXBExperimentRef(Integer value) {
        this.experimentRef = value;
    }

    public Experiment getExperiment() {
        if (experiment == null && this.experimentRef != null){
            resolveExperimentReferences();
        }
        return experiment;
    }

    private void resolveExperimentReferences() {
        if (this.mapOfReferencedObjects.containsKey(experimentRef)){
            Object o = this.mapOfReferencedObjects.get(experimentRef);
            if (o instanceof Experiment){
                this.experiment = (Experiment)o;
            }
        }
    }
}
