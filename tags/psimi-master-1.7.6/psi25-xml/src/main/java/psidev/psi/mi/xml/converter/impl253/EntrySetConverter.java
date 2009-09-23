/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;

/**
 * Converter to and from JAXB of the class EntrySet.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.EntrySet
 * @see psidev.psi.mi.xml253.jaxb.EntrySet
 * @since <pre>07-Jun-2006</pre>
 */
public class EntrySetConverter {

    //////////////////////
    // Instance variable

    private EntryConverter entryConverter;

    //////////////////////
    // Constructor

    public EntrySetConverter() {
        entryConverter = new EntryConverter();
    }

    ///////////////////////////////
    // DAO factory stategy

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    /**
     * Set the DAO Factory that holds required DAOs for resolving ids.
     *
     * @param factory the DAO factory
     */
    public void setDAOFactory( DAOFactory factory ) {
        this.factory = factory;

        // initialise the factory in sub-converters
        entryConverter.setDAOFactory( factory );
    }

    /**
     * Checks that the dependencies of that object are fulfilled.
     *
     * @throws ConverterException
     */
    private void checkDependencies() throws ConverterException {
        if ( factory == null ) {
            throw new ConverterException( "Please set a DAO factory in order to resolve ids." );
        }
    }

    ///////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.EntrySet fromJaxb( psidev.psi.mi.xml253.jaxb.EntrySet jEntrySet ) throws ConverterException {

        if ( jEntrySet == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB EntrySet." );
        }

        psidev.psi.mi.xml.model.EntrySet mEntrySet = new psidev.psi.mi.xml.model.EntrySet();

        // Initialise the model reading the Jaxb object

        // 1. set attributes
        mEntrySet.setLevel( jEntrySet.getLevel() );
        mEntrySet.setVersion( jEntrySet.getVersion() );
        mEntrySet.setMinorVersion( jEntrySet.getMinorVersion() );

        // 2. set encapsulated objects
        for ( psidev.psi.mi.xml253.jaxb.EntryType jEntry : jEntrySet.getEntries() ) {
            mEntrySet.getEntries().add( entryConverter.fromJaxb( jEntry ) );
        }

        return mEntrySet;
    }

    public psidev.psi.mi.xml253.jaxb.EntrySet toJaxb( psidev.psi.mi.xml.model.EntrySet mEntrySet ) throws ConverterException {

        if ( mEntrySet == null ) {
            throw new IllegalArgumentException( "You must give a non null model EntrySet." );
        }

        psidev.psi.mi.xml253.jaxb.EntrySet jEntrySet = new psidev.psi.mi.xml253.jaxb.EntrySet();

        // Initialise the JAXB object reading the model

        // 1. set attributes
        jEntrySet.setLevel( 2 );
        jEntrySet.setVersion( 5 );
        jEntrySet.setMinorVersion( 3 );

        // 2. set encapsulated objects
        for ( psidev.psi.mi.xml.model.Entry mEntry : mEntrySet.getEntries() ) {
            jEntrySet.getEntries().add( entryConverter.toJaxb( mEntry ) );
        }

        return jEntrySet;
    }
}