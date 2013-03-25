/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;

import java.util.List;

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

    private List<PsiXml25ParserListener> listeners;

    public AvailabilityConverter() {
    }

    public void setListeners(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
    }

    ///////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Availability fromJaxb( psidev.psi.mi.xml253.jaxb.AvailabilityType jAvailability ) {

        if ( jAvailability == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB availability." );
        }

        psidev.psi.mi.xml.model.Availability mAvailability = new psidev.psi.mi.xml.model.Availability();
        Locator locator = jAvailability.sourceLocation();
        if (locator != null){
            mAvailability.setSourceLocator(new FileSourceLocator(locator.getLineNumber(), locator.getColumnNumber()));
        }

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