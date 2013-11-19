package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25IdCache;
import psidev.psi.mi.jami.xml.reference.AbstractExperimentRef;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Xml implementation of ModelledParameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlModelledParameter extends XmlParameter implements ModelledParameter{
    private Experiment experiment;
    private Collection<Publication> publications;

    public XmlModelledParameter() {
        super();
    }

    public XmlModelledParameter(CvTerm type, ParameterValue value, BigDecimal uncertainty, CvTerm unit) {
        super(type, value, uncertainty, unit);
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
    @XmlElement(name="experimentRef")
    public Integer getJAXBExperimentRef() {
        if (experiment instanceof XmlModelledParameter.ExperimentRef){
            return ((XmlModelledParameter.ExperimentRef) experiment).getRef();
        }
        return null;
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
        if (value != null){
            this.experiment = new ExperimentRef(value);
        }
    }

    public Experiment getExperiment() {
        return experiment;
    }

    private FileSourceLocator getParameterLocator(){
        return getSourceLocator();
    }

    ///////////////////////////////////////////////////////// classes

    /**
     * Experiment ref for experimental interactor
     */
    private class ExperimentRef extends AbstractExperimentRef{
        public ExperimentRef(int ref) {
            super(ref);
        }

        public boolean resolve(PsiXml25IdCache parsedObjects) {
            if (parsedObjects.contains(this.ref)){
                Experiment obj = parsedObjects.getExperiment(this.ref);
                if (obj != null){
                    experiment = obj;
                    if (experiment.getPublication() != null){
                        getPublications().add(experiment.getPublication());
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Parameter Experiment Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
        }

        public FileSourceLocator getSourceLocator() {
            return getParameterLocator();
        }

        public void setSourceLocator(FileSourceLocator locator) {
            throw new UnsupportedOperationException("Cannot set the source locator of an experiment ref");
        }
    }
}
