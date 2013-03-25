/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl254;

import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml254.jaxb.Alias;

import java.util.List;

/**
 * Converter to and from JAXB of the class Names.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Names
 * @see psidev.psi.mi.xml254.jaxb.Names
 * @since <pre>07-Jun-2006</pre>
 */
public class NamesConverter {

    private List<PsiXml25ParserListener> listeners;

    //////////////////////
    // Instance variable

    private AliasConverter aliasConverter;

    /////////////////////
    // Constructor

    public NamesConverter() {
        aliasConverter = new AliasConverter();
    }

    public void setListeners(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
        this.aliasConverter.setListeners(listeners);
    }

    /////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Names fromJaxb( psidev.psi.mi.xml254.jaxb.Names jNames ) {

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
        for ( Alias jAlias : jNames.getAlias() ) {
            mNames.getAliases().add( aliasConverter.fromJaxb( jAlias ) );
        }

        return mNames;
    }

    public psidev.psi.mi.xml254.jaxb.Names toJaxb( psidev.psi.mi.xml.model.Names mNames ) {

        if ( mNames == null ) {
            throw new IllegalArgumentException( "You must give a non null model Names." );
        }

        psidev.psi.mi.xml254.jaxb.Names jNames = new psidev.psi.mi.xml254.jaxb.Names();

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