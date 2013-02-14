/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.model;


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
 *     &lt;extension base="{net:sf:psidev:mi}cvType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="experimentRefList" type="{net:sf:psidev:mi}experimentRefListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class ExperimentalPreparation extends CvType {

    private Collection<ExperimentDescription> experiments;
    private Collection<ExperimentRef> experimentRefs;

    ///////////////////////////
    // Constructors

    public ExperimentalPreparation() {
        super();
    }

    public ExperimentalPreparation( Names names, Xref xref ) {
        super( names, xref );
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Check if the optional experiments is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExperiments() {
        return ( experiments != null ) && ( !experiments.isEmpty() );
    }

    /**
     * Gets the value of the experimentRefList property.
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

    //////////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "ExperimentalPreparation" );
        sb.append( "{experiments=" ).append( experiments );
        sb.append( ", experimentRefs=" ).append( experimentRefs );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        if ( !super.equals( o ) ) return false;

        ExperimentalPreparation that = ( ExperimentalPreparation ) o;

        if ( experimentRefs != null ? !experimentRefs.equals( that.experimentRefs ) : that.experimentRefs != null )
            return false;
        if ( experiments != null ? !experiments.equals( that.experiments ) : that.experiments != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + ( experiments != null ? experiments.hashCode() : 0 );
        result = 31 * result + ( experimentRefs != null ? experimentRefs.hashCode() : 0 );
        return result;
    }
}