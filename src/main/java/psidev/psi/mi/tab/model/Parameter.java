package psidev.psi.mi.tab.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 13/06/2012
 * Time: 11:22
 * To change this template use File | Settings | File Templates.
 */
public interface Parameter extends psidev.psi.mi.jami.model.Parameter, Serializable {

    /**
     * Getter fot property 'type'.
     *
     * @return a String with the CVTerm name for the parameter type in the PSI-MI ontology
     */
    public String getParameterType();

    /**
     * Setter for property 'type'.
     *
     * @param type a String with the CVTerm name for the parameter type in the PSI-MI ontology
     */
    public void setType(String type);

    /**
     *  Getter fot property 'factor'.
     *
     * @return a double with the factor of the value in scientific notation.
     */
    public Double getFactor();

    /**
     * Setter for property 'factor'.
     *
     * @param factor a double with the factor of the value in scientific notation.
     */
    public void setFactor(Double factor);

    /**
     *  Getter fot property 'base'.
     *
     * @return a integer with the base in scientific notation.
     */
    public Integer getBase();

    /**
     * Setter for property 'base'.
     *
     * @param base a integer with the base in scientific notation.
     */
    public void setBase(Integer base);

    /**
     * Getter fot property 'exponent'.
     *
     * @returna integer with exponent of the value in scientific notation.
     */
    public Integer getExponent();

    /**
     * Setter for property 'exponent'.
     *
     * @param exponent a integer with the exponent of the value in scientific notation.
     */
    public void setExponent(Integer exponent);

    /**
     * Getter fot property 'value'.
     *
     * @return a String with the representation of the parameter in scientific notation.
     */
    public String getValueAsString();

    /**
     * Setter for property 'value'.
     *
     * @param value a String with the representation of the parameter in scientific notation.
     */
    public void setValue(String value);

    /**
     * Getter fot property 'unit'.
     *
     * @return a String with the unit of the parameter.
     */
    public String getParameterUnit();

    /**
     * Setter for property 'unit'.
     *
     * @param unit a String with the unit of the parameter.
     */
    public void setUnit(String unit);

    /**
     * Getter fot property 'uncertainty'.
     *
     * @return a double with the uncertainty.
     */
    public Double getUncertaintyAsDouble();

    /**
     * Setter for property 'uncertainty'.
     *
     * @param uncertainty a double with the uncertainty
     */
    public void setUncertainty(Double uncertainty);
}
