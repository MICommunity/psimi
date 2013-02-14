/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{net:sf:psidev:mi}parameterType">
 *       &lt;sequence>
 *         &lt;element name="experimentRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *       &lt;attribute name="uncertainty" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Parameter {

    private ExperimentRef experimentRef;

    private ExperimentDescription experiment;

    private Double uncertainty;

    private Integer base;

    private Integer exponent;

    private double factor;

    private String term;

    private String termAc;

    private String unit;

    private String unitAc;

    ///////////////////////////
    // Constructors

    public Parameter() {
    }

    public Parameter( String term, double factor ) {
        this.term = term;
        this.factor = factor;
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Check if the optional base is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasBase() {
        return base != null;
    }

    /**
     * Gets the value of the base property.
     *
     * @return possible object is {@link Short }
     */
    public int getBase() {
        if ( base == null ) {
            return 10;
        } else {
            return base;
        }
    }

    /**
     * Sets the value of the base property.
     *
     * @param value allowed object is {@link Short }
     */
    public void setBase( int value ) {
        this.base = value;
    }

    /**
     * Check if the optional exponent is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExponent() {
        return exponent != null;
    }

    /**
     * Gets the value of the exponent property.
     *
     * @return possible object is {@link Short }
     */
    public int getExponent() {
        if ( exponent == null ) {
            return 0;
        } else {
            return exponent;
        }
    }

    /**
     * Sets the value of the exponent property.
     *
     * @param value allowed object is {@link Short }
     */
    public void setExponent( int value ) {
        this.exponent = value;
    }

    /**
     * Gets the value of the factor property.
     *
     * @return possible object is {@link Double }
     */
    public double getFactor() {
        return factor;
    }

    /**
     * Sets the value of the factor property.
     *
     * @param value allowed object is {@link Double }
     */
    public void setFactor( double value ) {
        this.factor = value;
    }

    /**
     * Check if the optional term is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasTerm() {
        return term != null;
    }

    /**
     * Gets the value of the term property.
     *
     * @return possible object is {@link String }
     */
    public String getTerm() {
        return term;
    }

    /**
     * Sets the value of the term property.
     *
     * @param value allowed object is {@link String }
     */
    public void setTerm( String value ) {
        this.term = value;
    }

    /**
     * Check if the optional termAc is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasTermAc() {
        return termAc != null;
    }

    /**
     * Gets the value of the termAc property.
     *
     * @return possible object is {@link String }
     */
    public String getTermAc() {
        return termAc;
    }

    /**
     * Sets the value of the termAc property.
     *
     * @param value allowed object is {@link String }
     */
    public void setTermAc( String value ) {
        this.termAc = value;
    }

    /**
     * Check if the optional unit is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasUnit() {
        return unit != null;
    }

    /**
     * Gets the value of the unit property.
     *
     * @return possible object is {@link String }
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     *
     * @param value allowed object is {@link String }
     */
    public void setUnit( String value ) {
        this.unit = value;
    }

    /**
     * Check if the optional unitAc is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasUnitAc() {
        return unitAc != null;
    }

    /**
     * Gets the value of the unitAc property.
     *
     * @return possible object is {@link String }
     */
    public String getUnitAc() {
        return unitAc;
    }

    /**
     * Sets the value of the unitAc property.
     *
     * @param value allowed object is {@link String }
     */
    public void setUnitAc( String value ) {
        this.unitAc = value;
    }

    /**
     * Check if the optional experimentRef is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExperimentRef() {
        return experimentRef != null;
    }

    public ExperimentRef getExperimentRef() {
        return experimentRef;
    }

    public void setExperimentRef( ExperimentRef experimentRef ) {
        this.experimentRef = experimentRef;
    }

    /**
     * Check if the optional experiment is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExperiment() {
        return experiment != null;
    }

    /**
     * Gets the value of the experiment property.
     */
    public ExperimentDescription getExperiment() {
        return experiment;
    }

    /**
     * Sets the value of the experiment property.
     */
    public void setExperiment( ExperimentDescription experiment ) {
        this.experiment = experiment;
    }

    /**
     * Check if the optional uncertainty is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasUncertainty() {
        return uncertainty != null;
    }

    /**
     * Gets the value of the uncertainty property.
     *
     * @return possible object is {@link java.math.BigDecimal }
     */
    public double getUncertainty() {
        if ( uncertainty == null ) {
            uncertainty = new Double( 0 );
        }
        return uncertainty;
    }

    /**
     * Sets the value of the uncertainty property.
     *
     * @param value allowed object is {@link java.math.BigDecimal }
     */
    public void setUncertainty( double value ) {
        this.uncertainty = value;
    }

    //////////////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder( 256 );
        sb.append( "Parameter" );
        sb.append( "{experiment=" ).append( experiment );
        sb.append( ", experimentRef=" ).append( experimentRef );
        sb.append( ", uncertainty=" ).append( uncertainty );
        sb.append( ", base=" ).append( base );
        sb.append( ", exponent=" ).append( exponent );
        sb.append( ", factor=" ).append( factor );
        sb.append( ", term='" ).append( term ).append( '\'' );
        sb.append( ", termAc='" ).append( termAc ).append( '\'' );
        sb.append( ", unit='" ).append( unit ).append( '\'' );
        sb.append( ", unitAc='" ).append( unitAc ).append( '\'' );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        final Parameter parameter = ( Parameter ) o;

        if ( Double.compare( parameter.factor, factor ) != 0 ) {
            return false;
        }
        if ( base != null ? !base.equals( parameter.base ) : parameter.base != null ) {
            return false;
        }
        if ( experiment != null ? !experiment.equals( parameter.experiment ) : parameter.experiment != null ) {
            return false;
        }
        if ( exponent != null ? !exponent.equals( parameter.exponent ) : parameter.exponent != null ) {
            return false;
        }
        if ( !term.equals( parameter.term ) ) {
            return false;
        }
        if ( termAc != null ? !termAc.equals( parameter.termAc ) : parameter.termAc != null ) {
            return false;
        }
        if ( uncertainty != null ? !uncertainty.equals( parameter.uncertainty ) : parameter.uncertainty != null ) {
            return false;
        }
        if ( unit != null ? !unit.equals( parameter.unit ) : parameter.unit != null ) {
            return false;
        }
        if ( unitAc != null ? !unitAc.equals( parameter.unitAc ) : parameter.unitAc != null ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = ( experiment != null ? experiment.hashCode() : 0 );
        result = 29 * result + ( uncertainty != null ? uncertainty.hashCode() : 0 );
        result = 29 * result + ( base != null ? base.hashCode() : 0 );
        result = 29 * result + ( exponent != null ? exponent.hashCode() : 0 );
        temp = factor != +0.0d ? Double.doubleToLongBits( factor ) : 0L;
        result = 29 * result + ( int ) ( temp ^ ( temp >>> 32 ) );
        result = 29 * result + term.hashCode();
        result = 29 * result + ( termAc != null ? termAc.hashCode() : 0 );
        result = 29 * result + ( unit != null ? unit.hashCode() : 0 );
        result = 29 * result + ( unitAc != null ? unitAc.hashCode() : 0 );
        return result;
    }

}