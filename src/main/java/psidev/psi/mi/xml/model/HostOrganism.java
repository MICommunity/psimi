/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.model;


import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO comment this
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21-Jun-2006</pre>
 */

public class HostOrganism implements NamesContainer {

    private Organism organism = new Organism();

    private Collection<ExperimentDescription> experiments;
    private Collection<ExperimentRef> experimentRefs;

    public HostOrganism() {
    }

    ///////////////////////////////////
    // Delegate from Organism

    /**
     * Check if the optional names is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasNames() {
        return getNames() != null;
    }

    public Names getNames() {
        return organism.getNames();
    }

    public void setNames( Names value ) {
        organism.setNames( value );
    }

    /**
     * Check if the optional cellType is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasCellType() {
        return organism.getCellType() != null;
    }

    public CellType getCellType() {
        return organism.getCellType();
    }

    public void setCellType( CellType value ) {
        organism.setCellType( value );
    }

    /**
     * Check if the optional compartment is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasCompartment() {
        return organism.getCompartment() != null;
    }

    public Compartment getCompartment() {
        return organism.getCompartment();
    }

    public void setCompartment( Compartment value ) {
        organism.setCompartment( value );
    }

    /**
     * Check if the optional tissue is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasTissue() {
        return organism.getTissue() != null;
    }

    public Tissue getTissue() {
        return organism.getTissue();
    }

    public void setTissue( Tissue value ) {
        organism.setTissue( value );
    }

    public int getNcbiTaxId() {
        return organism.getNcbiTaxId();
    }

    public void setNcbiTaxId( int value ) {
        organism.setNcbiTaxId( value );
    }

    public Organism getOrganism() {
        return organism;
    }

    ////////////////////////////
    // Experiments

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

    //////////////////////////////
    // Override object


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "HostOrganism" );
        sb.append( "{organism=" ).append( organism );
        sb.append( ", experiments=" ).append( experiments );
        sb.append( ", experimentRefs=" ).append( experimentRefs );
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

        final HostOrganism that = ( HostOrganism ) o;

        if ( organism != null ? !organism.equals( that.organism ) : that.organism != null ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return ( organism != null ? organism.hashCode() : 0 );
    }
}