package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.ModelledConfidence;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.xml.XmlEntryContext;

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
@XmlType(name = "",propOrder = {
        "JAXBExperimentRefList"
})
public class XmlModelledConfidence extends XmlConfidence implements ModelledConfidence{
    private Map<Integer, Object> mapOfReferencedObjects;
    private ArrayList<Integer> experimentRefList;
    private Collection<Experiment> experiments;
    private Collection<Publication> publications;

    public XmlModelledConfidence() {
        super();
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();

    }

    public XmlModelledConfidence(XmlOpenCvTerm type, String value) {
        super(type, value);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public Collection<Publication> getPublications() {
        if (publications == null){
            this.publications = new ArrayList<Publication>();
        }
        if (publications.isEmpty() && experimentRefList != null && !experimentRefList.isEmpty()){
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
    @XmlElementWrapper(name="experimentRefList")
    @XmlElement(name="experimentRef", required = true)
    public ArrayList<Integer> getJAXBExperimentRefList() {
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
    public void setJAXBExperimentRefList(ArrayList<Integer> value) {
        this.experimentRefList = value;
    }

    @XmlTransient
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
                    Experiment exp = (Experiment)o;
                    this.experiments.add(exp);
                    if (exp.getPublication() != null){
                        this.publications.add(exp.getPublication());
                    }
                }
            }
        }
    }
}
