/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

/**
 * Converter to and from JAXB of the class Availability.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Availability
 * @see psidev.psi.mi.xml253.jaxb.AvailabilityType
 * @since <pre>07-Jun-2006</pre>
 */
public class AvailabilityConverter {

    public AvailabilityConverter() {
    }

    ///////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Availability fromJaxb( psidev.psi.mi.xml253.jaxb.AvailabilityType jAvailability ) {

        if ( jAvailability == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB availability." );
        }

        psidev.psi.mi.xml.model.Availability mAvailability = new psidev.psi.mi.xml.model.Availability();

        // Initialise the model reading the Jaxb object

        // 1. set attributes

        mAvailability.setId( jAvailability.getId() );
        mAvailability.setValue( jAvailability.getValue() );

        return mAvailability;
    }

    public psidev.psi.mi.xml253.jaxb.AvailabilityType toJaxb( psidev.psi.mi.xml.model.Availability mAvailability ) {

        if ( mAvailability == null ) {
            throw new IllegalArgumentException( "You must give a non null model availability." );
        }

        psidev.psi.mi.xml253.jaxb.AvailabilityType jAvailability = new psidev.psi.mi.xml253.jaxb.AvailabilityType();

        // Initialise the JAXB object reading the model

        // 1. set attributes

        jAvailability.setId( mAvailability.getId() );
        jAvailability.setValue( mAvailability.getValue() );

        return jAvailability;
    }
}