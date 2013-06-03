package psidev.psi.mi.jami.model;

import java.math.BigDecimal;

/**
 * A parameter numeric value
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/11/12</pre>
 */

public class ParameterValue extends Number{

    private short base=10;
    private BigDecimal factor;
    private short exponent=0;

    public ParameterValue(BigDecimal factor, short base, short exponent){
        if (factor == null){
            throw new IllegalArgumentException("The factor is required and cannot be null");
        }
        this.base = base;
        this.factor = factor;
        this.exponent = exponent;
    }

    public ParameterValue(BigDecimal value){
        if (value == null){
            throw new IllegalArgumentException("The value is required and cannot be null");
        }
        this.base = 10;
        this.factor = value;
        this.exponent = 0;
    }

    @Override
    public int intValue() {
        return factor.multiply(BigDecimal.valueOf(Math.pow(base, exponent))).intValue();
    }

    @Override
    public long longValue() {
        return factor.multiply(BigDecimal.valueOf(Math.pow(base, exponent))).longValue();
    }

    @Override
    public float floatValue() {
        return factor.multiply(BigDecimal.valueOf(Math.pow(base, exponent))).floatValue();
    }

    @Override
    public double doubleValue() {
        return factor.multiply(BigDecimal.valueOf(Math.pow(base, exponent))).doubleValue();
    }

    /**
     * Base of the parameter expression. Defaults to 10.
     * @return the base
     */
    public short getBase() {
        return base;
    }

    /**
     * The "main" value of the parameter.
     * @return the factor
     */
    public BigDecimal getFactor() {
        return factor;
    }

    /**
     * Exponent of the base. By default is 0.
     * @return the exponent
     */
    public short getExponent() {
        return exponent;
    }

    public String toString(){
        return (base != 0 && factor.doubleValue() != 0 ? factor.toString()+(exponent != 0 ? "x"+base+"^("+exponent+")" : "") : "0");
    }
}
