package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.utils.ParameterUtils;
import psidev.psi.mi.jami.utils.comparator.parameter.UnambiguousParameterComparator;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Default implementation for Parameter
 *
 * Notes: The equals and hashcode methods have been overridden to be consistent with UnambiguousParameterComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultParameter implements Parameter, Serializable {

    private CvTerm type;
    private BigDecimal uncertainty;
    private CvTerm unit;
    private ParameterValue value;

    public DefaultParameter(CvTerm type, ParameterValue value){
        if (type == null){
            throw new IllegalArgumentException("The parameter type is required and cannot be null");
        }
        this.type = type;
        if (value == null){
            throw new IllegalArgumentException("The parameter value is required and cannot be null");
        }
        this.value = value;
    }

    public DefaultParameter(CvTerm type, ParameterValue value, CvTerm unit){
        this(type, value);
        this.unit = unit;
    }

    public DefaultParameter(CvTerm type, ParameterValue value, CvTerm unit, BigDecimal uncertainty){
        this(type, value, unit);
        this.uncertainty = uncertainty;
    }

    public DefaultParameter(CvTerm type, ParameterValue value, BigDecimal uncertainty){
        this(type, value);
        this.uncertainty = uncertainty;
    }

    public DefaultParameter(CvTerm type, String value) throws IllegalParameterException {
        if (type == null){
            throw new IllegalArgumentException("The parameter type is required and cannot be null");
        }
        this.type = type;

        Parameter param = ParameterUtils.createParameterFromString(type, value);
        this.value = param.getValue();
        this.uncertainty = param.getUncertainty();
    }

    public DefaultParameter(CvTerm type, String value, CvTerm unit) throws IllegalParameterException {
        this(type, value);
        this.unit = unit;
    }

    public CvTerm getType() {
        return this.type;
    }

    public BigDecimal getUncertainty() {
        return this.uncertainty;
    }

    public CvTerm getUnit() {
        return this.unit;
    }

    public ParameterValue getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Parameter)){
            return false;
        }

        return UnambiguousParameterComparator.areEquals(this, (Parameter) o);
    }

    @Override
    public String toString() {
        return type.toString() + ": " + value  + (uncertainty != null ? " ~" + uncertainty.toString() : "" + (unit != null ? "("+unit.toString()+")" : ""));
    }

    @Override
    public int hashCode() {
        return UnambiguousParameterComparator.hashCode(this);
    }
}
