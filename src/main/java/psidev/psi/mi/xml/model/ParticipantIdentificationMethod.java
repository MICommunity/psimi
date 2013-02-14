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
 * @since <pre>15-Jun-2006</pre>
 */

public class ParticipantIdentificationMethod extends CvType {

    //////////////////////////
    // Instance variable

    private Collection<ExperimentDescription> experiments;
    private Collection<ExperimentRef> experimentRefs;

    ///////////////////////////
    // Getters and Setters

    /**
     * Check if the optional experiments is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExperimentRefs() {
        return experimentRefs != null && !experimentRefs.isEmpty();
    }

    /**
     * Gets the value of the experimentRefList property.
     *
     * @return possible object is {@link ExperimentDescription }
     */
    public Collection<ExperimentRef> getExperimentRefs() {
        if ( experimentRefs == null ) {
            experimentRefs = new ArrayList<ExperimentRef>();
        }
        return experimentRefs;
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

    //////////////////////////
    // Object's override

    // Note: equals and hashCode are those of CvType.

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "ParticipantIdentificationMethod" );
        sb.append( "{experiments=" ).append( experiments );
        sb.append( ",experimentRefs=" ).append( experimentRefs );
        sb.append( '}' );
        return sb.toString();
    }
}