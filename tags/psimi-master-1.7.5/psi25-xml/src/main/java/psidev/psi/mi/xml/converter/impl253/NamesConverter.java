/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import psidev.psi.mi.xml253.jaxb.NamesType;

/**
 * Converter to and from JAXB of the class Names.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Names
 * @see psidev.psi.mi.xml253.jaxb.NamesType
 * @since <pre>07-Jun-2006</pre>
 */
public class NamesConverter {

    //////////////////////
    // Instance variable

    private AliasConverter aliasConverter;

    /////////////////////
    // Constructor

    public NamesConverter() {
        aliasConverter = new AliasConverter();
    }

    /////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Names fromJaxb( psidev.psi.mi.xml253.jaxb.NamesType jNames ) {

        if ( jNames == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Names." );
        }

        psidev.psi.mi.xml.model.Names mNames = new psidev.psi.mi.xml.model.Names();

        // Initialise the model reading the Jaxb object

        // 1. set attributes

        // shortlabel
        mNames.setShortLabel( jNames.getShortLabel() );

        // fullname
        mNames.setFullName( jNames.getFullName() );

        // 2. set encapsulated objects

        // aliases
        for ( NamesType.Alias jAlias : jNames.getAlias() ) {
            mNames.getAliases().add( aliasConverter.fromJaxb( jAlias ) );
        }

        return mNames;
    }

    public psidev.psi.mi.xml253.jaxb.NamesType toJaxb( psidev.psi.mi.xml.model.Names mNames ) {

        if ( mNames == null ) {
            throw new IllegalArgumentException( "You must give a non null model Names." );
        }

        psidev.psi.mi.xml253.jaxb.NamesType jNames = new psidev.psi.mi.xml253.jaxb.NamesType();

        // Initialise the JAXB object reading the model

        // 1. set attributes

        // shortlabel
        jNames.setShortLabel( mNames.getShortLabel() );

        // fullname
        jNames.setFullName( mNames.getFullName() );

        // 2. set encapsulated objects

        // aliases
        for ( psidev.psi.mi.xml.model.Alias mAlias : mNames.getAliases() ) {
            jNames.getAlias().add( aliasConverter.toJaxb( mAlias ) );
        }

        return jNames;
    }
}