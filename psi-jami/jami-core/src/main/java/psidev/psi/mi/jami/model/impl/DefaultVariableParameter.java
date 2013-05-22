package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.utils.comparator.experiment.UnambiguousVariableParameterComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for variableParameters
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class DefaultVariableParameter implements VariableParameter {

    private String description;
    private CvTerm unit;
    private Collection<VariableParameterValue> variableValues;

    private Experiment experiment;

    public DefaultVariableParameter(String description){
        if (description == null){
            throw new IllegalArgumentException("The description of the variableParameter is required and cannot be null.");
        }
        this.description = description;
    }

    public DefaultVariableParameter(String description, Experiment experiment){
        this(description);
        this.experiment = experiment;
    }

    public DefaultVariableParameter(String description, CvTerm unit){
        this(description);
        this.unit = unit;
    }

    public DefaultVariableParameter(String description, Experiment experiment, CvTerm unit){
        this(description, experiment);
        this.unit = unit;
    }

    protected void initialiseVatiableParameterValues(){
        this.variableValues = new ArrayList<VariableParameterValue>();
    }

    protected void initialiseVatiableParameterValuesWith(Collection<VariableParameterValue> paramValues){
        if (paramValues == null){
            this.variableValues = Collections.EMPTY_SET;
        }
        else {
            this.variableValues = paramValues;
        }
    }

    public String getDescription() {
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
        if (variableValues == null){
            initialiseVatiableParameterValues();
        }
        return this.variableValues;
    }

    public Experiment getExperiment() {
        return this.experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public void setExperimentAndAddVariableParameter(Experiment experiment) {
        this.experiment = experiment;
        experiment.getVariableParameters().add(this);
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
    public int hashCode() {
        return UnambiguousVariableParameterComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return description.toString() + (unit != null ? "(unit: "+unit.toString()+")":"");
    }
}
