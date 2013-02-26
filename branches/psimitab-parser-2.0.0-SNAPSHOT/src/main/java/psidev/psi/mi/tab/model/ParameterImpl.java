package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.ParameterUtils;
import psidev.psi.mi.jami.utils.factory.ParameterFactory;

import java.math.BigDecimal;


/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 13/06/2012
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
public class ParameterImpl implements Parameter, psidev.psi.mi.jami.model.Parameter {
    /**
     * Generated with IntelliJ plugin generateSerialVersionUID.
     * To keep things consistent, please use the same thing.
     */
    private static final long serialVersionUID = 8967236664673865804L;

    private CvTerm type;
    private BigDecimal uncertainty;
    private CvTerm unit;
    private ParameterValue value;

    //////////////////////
    // Constructors

    /**
     * Builds a parameter.
     *
     * @param type  the type of the parameter.
     * @param value double value.
     */
    public ParameterImpl(String type, String value) throws IllegalParameterException {
        this.type = new DefaultCvTerm(type != null ? type : "unknown");

        if (value == null){
           this.value = new ParameterValue(new BigDecimal("0"));
        }
        else {
            psidev.psi.mi.jami.model.Parameter param = ParameterFactory.createParameterFromString(type, value);
            this.value = param.getValue();
            this.uncertainty = param.getUncertainty();
        }
    }

    /**
     * Builds a parameter.
     *
     * @param type  the type of the parameter.
     * @param value string representation of the parameter value. The syntax is:
     *              factor x base ^ exponent.
     * @param unit  the unit of the parameter.
     */
    public ParameterImpl(String type, String value, String unit) throws IllegalParameterException {
        this(type, value);

        if (unit != null){
           this.unit = new DefaultCvTerm(unit);
        }
    }

    /**
     * Builds a parameter.
     *
     * @param type     the type of the parameter.
     * @param factor   the factor of the parameter.
     * @param base     the base of the parameter.
     * @param exponent the exponent of the parameter.
     * @param unit     the unit of the parameter.
     */
    public ParameterImpl(String type, double factor, int base, int exponent, double uncertainty, String unit) {
        this.type = new DefaultCvTerm(type != null ? type : "unknown");
        this.value = new ParameterValue(new BigDecimal(Double.toString(factor)), (short)base, (short)exponent);

        if (unit != null){
            this.unit = new DefaultCvTerm(unit);
        }
        this.uncertainty = new BigDecimal(Double.toString(uncertainty));
    }

    /**
     * {@inheritDoc}
     */
    public String getParameterType() {
        return type.getShortName();
    }

    /**
     * {@inheritDoc}
     */
    public void setType(String type) {
        if (type == null){
            type = "unknown";
        }
        this.type = new DefaultCvTerm(type);
    }

    /**
     * {@inheritDoc}
     */
    public Double getFactor() {
        return this.value.getFactor().doubleValue();
    }

    /**
     * {@inheritDoc}
     */
    public void setFactor(Double factor) {
        ParameterValue newValue = new ParameterValue(new BigDecimal(factor), this.value.getBase(), this.value.getExponent());
        this.value = newValue;
    }

    /**
     * {@inheritDoc}
     */
    public Integer getBase() {
        return (int) this.value.getBase();
    }

    /**
     * {@inheritDoc}
     */
    public void setBase(Integer base) {
        if (base == null){
            ParameterValue newValue = new ParameterValue(this.value.getFactor(), (short)10, this.value.getExponent());
            this.value = newValue;
        }
        else{
            ParameterValue newValue = new ParameterValue(this.value.getFactor(), base.shortValue(), this.value.getExponent());
            this.value = newValue;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Integer getExponent() {
        return (int) this.value.getExponent();
    }

    /**
     * {@inheritDoc}
     */
    public void setExponent(Integer exponent) {
        if (exponent == null){
            ParameterValue newValue = new ParameterValue(this.value.getFactor(), this.value.getBase(), (short)0);
            this.value = newValue;
        }
        else{
            ParameterValue newValue = new ParameterValue(this.value.getFactor(), this.value.getBase(), exponent.shortValue());
            this.value = newValue;
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getValueAsString() {
        return ParameterUtils.getParameterValueAsString(this);
    }

    public void setValue(String value) {
        if (value == null){
            throw new IllegalArgumentException("The parameter value cannot be null");
        }
        try {
            psidev.psi.mi.jami.model.Parameter param = ParameterFactory.createParameterFromString(this.type, value);
            this.value = param.getValue();
            this.uncertainty = param.getUncertainty();

        } catch (IllegalParameterException e) {
            throw new IllegalArgumentException("The parameter value "+value+"is not valid", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getParameterUnit() {
        return unit != null ? unit.getShortName() : null;
    }

    /**
     * {@inheritDoc}
     */
    public void setUnit(String unit) {
        if (unit != null){
            this.unit = new DefaultCvTerm(unit);
        }
        else {
            this.unit = null;
        }
    }

    /**
     * Getter fot property 'uncertainty'.
     *
     * @return a double with the uncertainty.
     */
    public Double getUncertaintyAsDouble() {
        return uncertainty != null ? uncertainty.doubleValue() : 0.0;
    }

    /**
     * Setter for property 'uncertainty'.
     *
     * @param uncertainty a double with the uncertainty
     */
    public void setUncertainty(Double uncertainty) {
        if (uncertainty == null){
            this.uncertainty = null;
        }
        else {
            this.uncertainty = new BigDecimal(Double.toString(uncertainty));
        }
    }

    //////////////////////////
    // Object's override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Parameter");
        sb.append("{type='").append(type.getShortName()).append('\'');
        sb.append(", value='").append(ParameterUtils.getParameterValueAsString(this)).append('\'');
        if (unit != null) {
            sb.append(", unit='").append(unit.getShortName()).append('\'');
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ParameterImpl that = (ParameterImpl) o;

        if (!type.getShortName().equals(that.type.getShortName())) {
            return false;
        }
        if (!getValueAsString().equals(that.getValueAsString())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = type.getShortName().hashCode();
        result = 29 * result + getValueAsString().hashCode();
        return result;
    }

    public CvTerm getType() {
        return type;
    }

    public BigDecimal getUncertainty() {
        return uncertainty;
    }

    public CvTerm getUnit() {
        return unit;
    }

    public ParameterValue getValue() {
        return value;
    }
}

