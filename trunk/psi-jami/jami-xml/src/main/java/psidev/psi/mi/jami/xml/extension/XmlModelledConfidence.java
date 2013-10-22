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
@XmlType(name = "",propOrder = {
        "JAXBExperimentRefList"
})
public class XmlModelledConfidence extends XmlConfidence implements ModelledConfidence{
    private Collection<Experiment> experiments;
    private Collection<Publication> publications;

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
        if (experiments == null || experiments.isEmpty()){
            return null;
        }
        ArrayList<Integer> references = new ArrayList<Integer>(experiments.size());
        for (Experiment exp : experiments){
            if (exp instanceof XmlExperiment){
                references.add(((XmlExperiment) exp).getJAXBId());
            }
        }
        if (references.isEmpty()){
            return null;
        }
        return references;    }

    /**
     * Sets the value of the experimentRefList property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setJAXBExperimentRefList(ArrayList<Integer> value) {
        if (value != null){
            for (Integer val : value){
                getExperiments().add(new AbstractExperimentRef(val) {
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
                        return "Experiment reference: "+ref+" in confidence "+(getConfidenceLocator() != null? getConfidenceLocator().toString():"") ;
                    }

                    public FileSourceLocator getSourceLocator() {
                        return getConfidenceLocator();
                    }

                    public void setSourceLocator(FileSourceLocator locator) {
                        throw new UnsupportedOperationException("Cannot set the source locator of an experiment ref");
                    }
                });
            }
        }
    }

    @XmlTransient
    public Collection<Experiment> getExperiments() {
        if (experiments == null){
            experiments = new ArrayList<Experiment>();
        }
        return experiments;
    }

    private FileSourceLocator getConfidenceLocator(){
        return getSourceLocator();
    }
}
