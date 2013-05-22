package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.utils.comparator.experiment.VariableParameterValueComparator;

/**
 * Default implementation for variableValue.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class DefaultVariableParameterValue implements VariableParameterValue {

    private String value;
    private Integer order;
    private VariableParameter variableParameter;

    public DefaultVariableParameterValue(String value, VariableParameter variableParameter){
        if (value == null){
            throw new IllegalArgumentException("The value of a variableParameterValue cannot be null");
        }
        this.value = value;
        this.variableParameter = variableParameter;
    }

    public DefaultVariableParameterValue(String value, VariableParameter variableParameter, Integer order){
        if (value == null){
            throw new IllegalArgumentException("The value of a variableParameterValue cannot be null");
        }
        this.value = value;
        this.variableParameter = variableParameter;
        this.order = order;
    }

    public String getValue() {
        return value;
    }

    public Integer getOrder() {
        return order;
    }

    public VariableParameter getVariableParameter() {
        return variableParameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof VariableParameterValue)){
            return false;
        }

        return VariableParameterValueComparator.areEquals(this, (VariableParameterValue) o);
    }

    @Override
    public int hashCode() {
        return VariableParameterValueComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : super.toString();
    }
}
