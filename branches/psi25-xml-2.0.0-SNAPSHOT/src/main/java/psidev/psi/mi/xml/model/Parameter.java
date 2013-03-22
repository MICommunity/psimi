/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;

import java.math.BigDecimal;

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

public class Parameter implements psidev.psi.mi.jami.model.Parameter, FileSourceContext{

    private ExperimentRef experimentRef;

    private ExperimentDescription experiment;

    private static final String UNSPECIFIED="unspecified";

    private CvTerm type;
    private BigDecimal uncertainty;
    private CvTerm unit;
    private ParameterValue value;

    private FileSourceLocator locator;

    ///////////////////////////
    // Constructors

    public Parameter() {
        this.type = new DefaultCvTerm(UNSPECIFIED);
        this.value = new ParameterValue(new BigDecimal("0"));
    }

    public Parameter( String term, double factor ) {
        this.type = new DefaultCvTerm(term != null ? term : UNSPECIFIED);
        this.value = new ParameterValue(new BigDecimal(Double.toString(factor)));
    }

    ///////////////////////////
    // Getters and Setters

    public FileSourceLocator getSourceLocator() {
        return this.locator;
    }

    public void setSourceLocator(FileSourceLocator locator) {
        this.locator = locator;
    }

    /**
     * Check if the optional base is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasBase() {
        return !(value.getExponent() == 0 && value.getBase() == 10);
    }

    /**
     * Gets the value of the base property.
     *
     * @return possible object is {@link Short }
     */
    public int getBase() {
        return value.getBase();
    }

    /**
     * Sets the value of the base property.
     *
     * @param value allowed object is {@link Short }
     */
    public void setBase( int value ) {
        this.value = new ParameterValue(this.value.getFactor(), (short)value, this.value.getExponent());
    }

    /**
     * Check if the optional exponent is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExponent() {
        return value.getExponent() != 0;
    }

    /**
     * Gets the value of the exponent property.
     *
     * @return possible object is {@link Short }
     */
    public int getExponent() {
        return (int)value.getExponent();
    }

    /**
     * Sets the value of the exponent property.
     *
     * @param value allowed object is {@link Short }
     */
    public void setExponent( int value ) {
        this.value = new ParameterValue(this.value.getFactor(), this.value.getBase(), (short) value);
    }

    /**
     * Gets the value of the factor property.
     *
     * @return possible object is {@link Double }
     */
    public double getFactor() {
        return this.value.getFactor().doubleValue();
    }

    /**
     * Sets the value of the factor property.
     *
     * @param value allowed object is {@link Double }
     */
    public void setFactor( double value ) {
        this.value = new ParameterValue(new BigDecimal(Double.toString(value)), this.value.getBase(), this.value.getExponent());
    }

    /**
     * Check if the optional term is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasTerm() {
        return type != null && type.getShortName() != null;
    }

    /**
     * Gets the value of the term property.
     *
     * @return possible object is {@link String }
     */
    public String getTerm() {
        return type != null ? type.getShortName() : null;
    }

    /**
     * Sets the value of the term property.
     *
     * @param value allowed object is {@link String }
     */
    public void setTerm( String value ) {
        if (type != null){
            type.setShortName(value != null ? value : UNSPECIFIED);
        }
        else {
            type = new DefaultCvTerm(value != null ? value : UNSPECIFIED);
        }
    }

    /**
     * Check if the optional termAc is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasTermAc() {
        return type != null && type.getMIIdentifier() != null;
    }

    /**
     * Gets the value of the termAc property.
     *
     * @return possible object is {@link String }
     */
    public String getTermAc() {
        return type != null ? type.getMIIdentifier() : null;
    }

    /**
     * Sets the value of the termAc property.
     *
     * @param value allowed object is {@link String }
     */
    public void setTermAc( String value ) {
        if (type != null){
            type.setMIIdentifier(value);
        }
        else {
            type = new DefaultCvTerm(UNSPECIFIED, value);
        }
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
    public String getUnitName() {
        return unit != null ? unit.getShortName() : null;
    }

    /**
     * Sets the value of the unit property.
     *
     * @param value allowed object is {@link String }
     */
    public void setUnit( String value ) {
        if (value == null){
            if (this.unit != null && this.unit.getMIIdentifier() != null){
                unit.setShortName(UNSPECIFIED);
            }
            else {
                this.unit = null;
            }
        }
        else if (this.unit == null){
            this.unit = new DefaultCvTerm(value);
        }
        else {
            this.unit.setShortName(value);
        }
    }

    /**
     * Check if the optional unitAc is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasUnitAc() {
        return unit != null && unit.getMIIdentifier() != null;
    }

    /**
     * Gets the value of the unitAc property.
     *
     * @return possible object is {@link String }
     */
    public String getUnitAc() {
        return unit != null ? unit.getMIIdentifier() : null;
    }

    /**
     * Sets the value of the unitAc property.
     *
     * @param value allowed object is {@link String }
     */
    public void setUnitAc( String value ) {
        if (value == null){
            if (this.unit != null){
                unit.setMIIdentifier(null);
            }
            else {
                this.unit = null;
            }
        }
        else if (this.unit == null){
            this.unit = new DefaultCvTerm(UNSPECIFIED, value);
        }
        else {
            this.unit.setMIIdentifier(value);
        }
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
    public double getUncertaintyAsDouble() {
        return uncertainty != null ? uncertainty.doubleValue() : new Double( 0 );
    }

    /**
     * Sets the value of the uncertainty property.
     *
     * @param value allowed object is {@link java.math.BigDecimal }
     */
    public void setUncertainty( double value ) {
        this.uncertainty = new BigDecimal(Double.toString(value));
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
        sb.append( ", base=" ).append( value.getBase() );
        sb.append( ", exponent=" ).append( value.getExponent() );
        sb.append( ", factor=" ).append( value.getFactor() );
        sb.append( ", term='" ).append( getTerm() ).append( '\'' );
        sb.append( ", termAc='" ).append( getTermAc() ).append( '\'' );
        sb.append( ", unit='" ).append( getUnit() ).append( '\'' );
        sb.append( ", unitAc='" ).append( getUnitAc() ).append( '\'' );
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

        if ( Double.compare( parameter.getFactor(), getFactor() ) != 0 ) {
            return false;
        }
        if ( value.getBase() != parameter.getBase() ) {
            return false;
        }
        if ( experiment != null ? !experiment.equals( parameter.experiment ) : parameter.experiment != null ) {
            return false;
        }
        if ( value.getExponent() != parameter.getValue().getExponent() ) {
            return false;
        }
        if ( !getTerm().equals( parameter.getTerm() ) ) {
            return false;
        }
        if ( getTermAc() != null ? !getTermAc().equals( parameter.getTermAc() ) : parameter.getTermAc() != null ) {
            return false;
        }
        if ( uncertainty != null ? getUncertaintyAsDouble() != parameter.getUncertaintyAsDouble() : false) {
            return false;
        }
        if ( getUnit() != null ? !getUnit().equals( getUnit() ) : getUnit() != null ) {
            return false;
        }
        if ( getUnitAc() != null ? !getUnitAc().equals( parameter.getUnitAc() ) : parameter.getUnitAc() != null ) {
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
        result = 29 * result + value.getBase();
        result = 29 * result + value.getExponent();
        temp = getFactor() != +0.0d ? Double.doubleToLongBits( getFactor() ) : 0L;
        result = 29 * result + ( int ) ( temp ^ ( temp >>> 32 ) );
        result = 29 * result + getTerm().hashCode();
        result = 29 * result + ( getTermAc() != null ? getTermAc().hashCode() : 0 );
        result = 29 * result + ( unit != null ? unit.hashCode() : 0 );
        result = 29 * result + ( getUnitAc() != null ? getUnitAc().hashCode() : 0 );
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