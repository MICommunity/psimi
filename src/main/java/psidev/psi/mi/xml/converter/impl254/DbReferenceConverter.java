/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl254;

/**
 * Converter to and from JAXB of the class DbReference.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.DbReference
 * @see psidev.psi.mi.xml254.jaxb.DbReference
 * @since <pre>07-Jun-2006</pre>
 */
public class DbReferenceConverter {

    public DbReferenceConverter() {
    }

    /////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.DbReference fromJaxb( psidev.psi.mi.xml254.jaxb.DbReference jDbReference ) {

        if ( jDbReference == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB DbReference." );
        }

        psidev.psi.mi.xml.model.DbReference mDbReference = new psidev.psi.mi.xml.model.DbReference();

        // Initialise the model reading the Jaxb object

        // 1. set attributes

        mDbReference.setDb( jDbReference.getDb() );
        mDbReference.setDbAc( jDbReference.getDbAc() );
        mDbReference.setId( jDbReference.getId() );
        mDbReference.setRefType( jDbReference.getRefType() );
        mDbReference.setRefTypeAc( jDbReference.getRefTypeAc() );
        mDbReference.setSecondary( jDbReference.getSecondary() );
        mDbReference.setVersion( jDbReference.getVersion() );

        return mDbReference;
    }

    public psidev.psi.mi.xml254.jaxb.DbReference toJaxb( psidev.psi.mi.xml.model.DbReference mDbReference ) {

        if ( mDbReference == null ) {
            throw new IllegalArgumentException( "You must give a non null model DbReference." );
        }

        psidev.psi.mi.xml254.jaxb.DbReference jDbReference = new psidev.psi.mi.xml254.jaxb.DbReference();

        // Initialise the JAXB object reading the model

        // 1. set attributes

        jDbReference.setDb( mDbReference.getDb() );
        jDbReference.setDbAc( mDbReference.getDbAc() );
        jDbReference.setId( mDbReference.getId() );
        jDbReference.setRefType( mDbReference.getRefType() );
        jDbReference.setRefTypeAc( mDbReference.getRefTypeAc() );
        jDbReference.setSecondary( mDbReference.getSecondary() );
        jDbReference.setVersion( mDbReference.getVersion() );

        return jDbReference;
    }
}