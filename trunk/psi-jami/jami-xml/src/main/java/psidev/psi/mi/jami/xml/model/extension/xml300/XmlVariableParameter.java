package psidev.psi.mi.jami.xml.model.extension.xml300;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.utils.comparator.experiment.UnambiguousVariableParameterComparator;
import psidev.psi.mi.jami.xml.model.extension.PsiXmLocator;
import psidev.psi.mi.jami.xml.model.extension.XmlCvTerm;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * XML 3.0 implementation of variable parameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/14</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlVariableParameter implements VariableParameter,FileSourceContext,Locatable, Serializable {

    private PsiXmLocator sourceLocator;

    @XmlLocation
    @XmlTransient
    private Locator locator;

    private String description;
    private CvTerm unit;
    private Experiment experiment;

    private JAXBVariableValueWrapper jaxbVariableValueWrapper;

    public XmlVariableParameter(){

    }

    public XmlVariableParameter(String description){
        if (description == null){
            throw new IllegalArgumentException("The description of the variableParameter is required and cannot be null.");
        }
        this.description = description;
    }

    public XmlVariableParameter(String description, Experiment experiment){
        this(description);
        this.experiment = experiment;
    }

    public XmlVariableParameter(String description, CvTerm unit){
        this(description);
        this.unit = unit;
    }

    public XmlVariableParameter(String description, Experiment experiment, CvTerm unit){
        this(description, experiment);
        this.unit = unit;
    }

    protected void initialiseVatiableParameterValues(){
        this.jaxbVariableValueWrapper = new JAXBVariableValueWrapper();
    }

    public String getDescription() {
        if (this.description == null){
            this.description = PsiXmlUtils.UNSPECIFIED;
        }
        return this.description;
    }

    public void setDescription(String description) {
        if (description == null){
            throw new IllegalArgumentException("The description cannot be null");
        }
        this.description = description;
    }

    public CvTerm getUnit() {
        return this.unit;
    }

    public void setUnit(CvTerm unit) {
        this.unit = unit;
    }

    public Collection<VariableParameterValue> getVariableValues() {
        if (jaxbVariableValueWrapper == null){
            initialiseVatiableParameterValues();
        }
        return this.jaxbVariableValueWrapper.variableValues;
    }

    public Experiment getExperiment() {
        return this.experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public void setExperimentAndAddVariableParameter(Experiment experiment) {
        if (this.experiment != null){
            this.experiment.removeVariableParameter(this);
        }
        if (experiment != null){
            experiment.addVariableParameter(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof VariableParameter)){
            return false;
        }

        return UnambiguousVariableParameterComparator.areEquals(this, (VariableParameter) o);
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        if (sourceLocator == null && locator != null){
            sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
        }
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }
    }

    public void setSourceLocation(PsiXmLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public int hashCode() {
        return UnambiguousVariableParameterComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return description.toString() + (unit != null ? "(unit: "+unit.toString()+")":"");
    }

    @XmlElement(name = "description", required = true)
    public void setJAXBDescription(String desc){
        this.description = desc;
    }

    @XmlElement(name = "unit")
    public void setJAXBUnit(XmlCvTerm unit){
        this.unit = unit;
    }

    @XmlElement(name = "variableValueList", required = true)
    public void setJAXBVariableParameterValuesWrapper(JAXBVariableValueWrapper jaxbVariableValueList) {
        this.jaxbVariableValueWrapper = jaxbVariableValueList;
        // initialise all variable values because of back references
        if (this.jaxbVariableValueWrapper != null && !this.jaxbVariableValueWrapper.variableValues.isEmpty()){
            for (VariableParameterValue value : this.jaxbVariableValueWrapper.variableValues){
                ((XmlVariableParameterValue)value).setVariableParameter(this);
            }
        }
    }

    //////////////////////////////////////////////////

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="variableValuesWrapper")
    public static class JAXBVariableValueWrapper implements Locatable, FileSourceContext{
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<VariableParameterValue> variableValues;

        public JAXBVariableValueWrapper(){
            initialiseVariableValues();
        }

        public JAXBVariableValueWrapper(List<VariableParameterValue> values){
            this.variableValues = values;
        }

        @Override
        public Locator sourceLocation() {
            return (Locator)getSourceLocator();
        }

        public FileSourceLocator getSourceLocator() {
            if (sourceLocator == null && locator != null){
                sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
            }
            return sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else{
                this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        protected void initialiseVariableValues(){
            this.variableValues = new ArrayList<VariableParameterValue>();
        }

        @XmlElement(type=XmlVariableParameterValue.class, name="variableValue", required = true)
        public List<VariableParameterValue> getJAXBVariableParameterValues() {
            return this.variableValues;
        }

        @Override
        public String toString() {
            return "Variable parameter values List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

}
