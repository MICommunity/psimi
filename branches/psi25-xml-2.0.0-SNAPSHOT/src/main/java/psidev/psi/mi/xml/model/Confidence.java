/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.model.CvTerm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{net:sf:psidev:mi}confidenceType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="experimentRefList" type="{net:sf:psidev:mi}experimentRefListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Confidence implements psidev.psi.mi.jami.model.Confidence{

    private Collection<ExperimentDescription> experiments;
    private Collection<ExperimentRef> experimentRefs;

    private static final String UNKNOWN = "unknown";
    private static final String UNSPECIFIED_VALUE = "unspecified";

    private String value;
    private Unit unit;

    ///////////////////////////
    // Constructors

    public Confidence() {
        this.unit = new Unit();
        this.value = UNSPECIFIED_VALUE;
    }

    public Confidence( Unit unit, String value ) {
        if (unit == null){
           this.unit = new Unit();
        }
        else {
            this.unit = unit;
        }
        this.value = value != null ? value : UNSPECIFIED_VALUE;
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Gets the value of the unit property.
     *
     * @return possible object is {@link OpenCvType }
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     *
     * @param value allowed object is {@link OpenCvType }
     */
    public void setUnit( Unit value ) {
        this.unit = value;
    }

    public CvTerm getType() {
        return unit;
    }

    /**
     * Gets the value of the value property.
     *
     * @return possible object is {@link String }
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is {@link String }
     */
    public void setValue( String value ) {
        this.value = value != null ? value : UNSPECIFIED_VALUE;
    }

    /**
     * Check if the optional experiments is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExperiments() {
        return ( experiments != null ) && ( !experiments.isEmpty() );
    }

    /**
     * Gets the value of the experimentList property.
     *
     * @return possible object is {@link ExperimentDescription }
     */
    public Collection<ExperimentDescription> getExperiments() {
        if ( experiments == null ) {
            experiments = new ArrayList<ExperimentDescription>();
        }
        return experiments;
    }

    /**
     * Check if the optional experimentRefs is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExperimentRefs() {
        return experimentRefs != null && !experimentRefs.isEmpty();
    }

    /**
     * Gets the value of the experimentRef property.
     *
     * @return possible object is {@link ExperimentRef }
     */
    public Collection<ExperimentRef> getExperimentRefs() {
        if ( experimentRefs == null ) {
            experimentRefs = new ArrayList<ExperimentRef>();
        }
        return experimentRefs;
    }

    ////////////////////////////////
    // Object Override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Confidence" );
        sb.append( "{unit=" ).append( unit );
        sb.append( ", value='" ).append( value ).append( '\'' );
        sb.append( ", experiments=" ).append( experiments );
        sb.append( ", experimentRefs=" ).append( experimentRefs );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        final Confidence that = ( Confidence ) o;

        if ( experiments != null ? !experiments.equals( that.experiments ) : that.experiments != null ) return false;
        if ( experimentRefs != null ? !experimentRefs.equals( that.experimentRefs ) : that.experimentRefs != null )
            return false;
        if ( !unit.equals( that.unit ) ) return false;
        if ( !value.equals( that.value ) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = unit.hashCode();
        result = 29 * result + value.hashCode();
        result = 29 * result + ( experiments != null ? experiments.hashCode() : 0 );
        result = 29 * result + ( experimentRefs != null ? experimentRefs.hashCode() : 0 );
        return result;
    }
}