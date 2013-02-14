/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

/**
 * Converter to and from JAXB of the class Alias.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Alias
 * @see psidev.psi.mi.xml253.jaxb.NamesType.Alias
 * @since <pre>07-Jun-2006</pre>
 */
public class AliasConverter {

    ////////////////////////
    // Constructor

    public AliasConverter() {
    }

    ///////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Alias fromJaxb( psidev.psi.mi.xml253.jaxb.NamesType.Alias jAlias ) {

        if ( jAlias == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB alias." );
        }

        psidev.psi.mi.xml.model.Alias mAlias = new psidev.psi.mi.xml.model.Alias();

        // Initialise the model reading the Jaxb object

        // 1. set attributes
        mAlias.setType( jAlias.getType() );
        mAlias.setTypeAc( jAlias.getTypeAc() );
        mAlias.setValue( jAlias.getValue() );

        return mAlias;
    }

    public psidev.psi.mi.xml253.jaxb.NamesType.Alias toJaxb( psidev.psi.mi.xml.model.Alias mAlias ) {

        if ( mAlias == null ) {
            throw new IllegalArgumentException( "You must give a non null model alias." );
        }

        psidev.psi.mi.xml253.jaxb.NamesType.Alias jAlias = new psidev.psi.mi.xml253.jaxb.NamesType.Alias();

        // Initialise the JAXB object reading the model

        // 1. set attributes

        jAlias.setType( mAlias.getType() );
        jAlias.setTypeAc( mAlias.getTypeAc() );
        jAlias.setValue( mAlias.getValue() );

        return jAlias;
    }
}