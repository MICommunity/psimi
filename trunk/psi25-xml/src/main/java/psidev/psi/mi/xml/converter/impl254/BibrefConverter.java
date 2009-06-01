/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl254;

/**
 * Converter to and from JAXB of the class Bibref.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Bibref
 * @since <pre>07-Jun-2006</pre>
 */
public class BibrefConverter {

    /////////////////////
    // instance variable

    AttributeConverter attributeConverter;
    XrefConverter xrefConverter;

    ///////////////////////
    // Constructor

    public BibrefConverter() {
        attributeConverter = new AttributeConverter();
        xrefConverter = new XrefConverter();
    }

    ///////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Bibref fromJaxb( psidev.psi.mi.xml254.jaxb.Bibref jBibref ) {

        if ( jBibref == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Bibref." );
        }

        psidev.psi.mi.xml.model.Bibref mBibref = new psidev.psi.mi.xml.model.Bibref();

        // 1. set attributes

        // 2. set encapsulated objects

        if ( jBibref.getAttributeList() != null ) {
            for ( psidev.psi.mi.xml254.jaxb.Attribute jAttribute :
                    jBibref.getAttributeList().getAttributes() ) {
                mBibref.getAttributes().add( attributeConverter.fromJaxb( jAttribute ) );
            }
        }

        mBibref.setXref( xrefConverter.fromJaxb( jBibref.getXref() ) );

        return mBibref;
    }

    public psidev.psi.mi.xml254.jaxb.Bibref toJaxb( psidev.psi.mi.xml.model.Bibref mBibref ) {

        if ( mBibref == null ) {
            throw new IllegalArgumentException( "You must give a non null model Bibref." );
        }

        psidev.psi.mi.xml254.jaxb.Bibref jBibref = new psidev.psi.mi.xml254.jaxb.Bibref();

        // 1. set attributes

        // 2. set encapsulated objects
        for ( psidev.psi.mi.xml.model.Attribute mAttribute : mBibref.getAttributes() ) {
            jBibref.getAttributeList().getAttributes().add( attributeConverter.toJaxb( mAttribute ) );
        }

        jBibref.setXref( xrefConverter.toJaxb( mBibref.getXref() ) );


        return jBibref;
    }
}