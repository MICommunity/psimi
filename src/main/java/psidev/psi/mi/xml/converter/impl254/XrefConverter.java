/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl254;

import psidev.psi.mi.xml254.jaxb.DbReference;

/**
 * Converter to and from JAXB of the class Xref.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Xref
 * @see psidev.psi.mi.xml254.jaxb.Xref
 * @since <pre>07-Jun-2006</pre>
 */
public class XrefConverter {

    //////////////////////////
    // Instance variable

    private DbReferenceConverter dbReferenceConverter;

    ////////////////////////
    // Constructor

    public XrefConverter() {
        dbReferenceConverter = new DbReferenceConverter();
    }

    /////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Xref fromJaxb( psidev.psi.mi.xml254.jaxb.Xref jXref ) {

        if ( jXref == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Xref." );
        }

        psidev.psi.mi.xml.model.Xref mXref = new psidev.psi.mi.xml.model.Xref();

        // Initialise the model reading the Jaxb object

        // 1. set attributes

        mXref.setPrimaryRef( dbReferenceConverter.fromJaxb( jXref.getPrimaryRef() ) );

        // 2. set encapsulated objects

        for ( DbReference jRef : jXref.getSecondaryReves() ) {
            mXref.getSecondaryRef().add( dbReferenceConverter.fromJaxb( jRef ) );
        }

        return mXref;
    }

    public psidev.psi.mi.xml254.jaxb.Xref toJaxb( psidev.psi.mi.xml.model.Xref mXref ) {

        if ( mXref == null ) {
            throw new IllegalArgumentException( "You must give a non null model Xref." );
        }

        psidev.psi.mi.xml254.jaxb.Xref jXref = new psidev.psi.mi.xml254.jaxb.Xref();

        // Initialise the JAXB object reading the model

        // 1. set attributes

        jXref.setPrimaryRef( dbReferenceConverter.toJaxb( mXref.getPrimaryRef() ) );

        // 2. set encapsulated objects

        if ( mXref.hasSecondaryRef() ) {
            for ( psidev.psi.mi.xml.model.DbReference mRef : mXref.getSecondaryRef() ) {
                jXref.getSecondaryReves().add( dbReferenceConverter.toJaxb( mRef ) );
            }
        }

        return jXref;
    }
}