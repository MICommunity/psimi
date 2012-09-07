/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl254;

import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.ExperimentRef;
import psidev.psi.mi.xml254.jaxb.ExperimentRefList;

/**
 * Converter to and from JAXB of the class ExperimentalPreparation.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.ExperimentalPreparation
 * @see psidev.psi.mi.xml254.jaxb.ExperimentalPreparation
 * @since <pre>07-Jun-2006</pre>
 */
public class ExperimentalPreparationConverter {

    ///////////////////////////
    // Instance variables

    private CvTypeConverter cvTypeConverter;

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    public ExperimentalPreparationConverter() {
        cvTypeConverter = new CvTypeConverter();
    }

    ///////////////////////////////
    // DAO factory stategy

    /**
     * Set the DAO Factory that holds required DAOs for resolving ids.
     *
     * @param factory the DAO factory
     */
    public void setDAOFactory( DAOFactory factory ) {
        this.factory = factory;
    }

    /**
     * Checks that the dependencies of that object are fulfilled.
     *
     * @throws ConverterException
     */
    private void checkDependencies() throws ConverterException {
        if ( factory == null ) {
            throw new ConverterException( "Please set a DAO factory in order to resolve experiment's id." );
        }
    }

    /////////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.ExperimentalPreparation
    fromJaxb( psidev.psi.mi.xml254.jaxb.ExperimentalPreparation jExperimentalPreparation )
            throws ConverterException {

        if ( jExperimentalPreparation == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Experimental preparation." );
        }

        // Initialise the model reading the Jaxb object

        // 1. convert the CvType
        psidev.psi.mi.xml.model.ExperimentalPreparation mExperimentalPreparation =
                cvTypeConverter.fromJaxb( jExperimentalPreparation,
                                          psidev.psi.mi.xml.model.ExperimentalPreparation.class );

        // 2. convert remaining components

        // experiments
        if ( jExperimentalPreparation.getExperimentRefList() != null ) {
            for ( Integer jExperimentId : jExperimentalPreparation.getExperimentRefList().getExperimentReves() ) {
                ExperimentDescription mExperiment = factory.getExperimentDAO().retreive( jExperimentId );
                if ( mExperiment == null ) {
                    mExperimentalPreparation.getExperimentRefs().add( new ExperimentRef( jExperimentId ) );
                } else {
                    mExperimentalPreparation.getExperiments().add( mExperiment );
                }
            }
        }

        return mExperimentalPreparation;
    }

    public psidev.psi.mi.xml254.jaxb.ExperimentalPreparation
    toJaxb( psidev.psi.mi.xml.model.ExperimentalPreparation mExperimentalPreparation ) throws ConverterException {

        if ( mExperimentalPreparation == null ) {
            throw new IllegalArgumentException( "You must give a non null model Experimental preparation." );
        }

        // Initialise the JAXB object reading the model

        // 1. convert the CvType
        psidev.psi.mi.xml254.jaxb.ExperimentalPreparation jExperimentalPreparation =
                cvTypeConverter.toJaxb( mExperimentalPreparation,
                                        psidev.psi.mi.xml254.jaxb.ExperimentalPreparation.class );

        // 2. convert remaining components

        // experiments
        if ( mExperimentalPreparation.hasExperiments() ) {
            if ( jExperimentalPreparation.getExperimentRefList() == null ) {
                jExperimentalPreparation.setExperimentRefList( new ExperimentRefList() );
            }
            for ( ExperimentDescription mExperiment : mExperimentalPreparation.getExperiments() ) {
                jExperimentalPreparation.getExperimentRefList().getExperimentReves().add( mExperiment.getId() );
            }
        } else if ( mExperimentalPreparation.hasExperimentRefs() ) {
            if ( jExperimentalPreparation.getExperimentRefList() == null ) {
                jExperimentalPreparation.setExperimentRefList( new ExperimentRefList() );
            }
            for ( ExperimentRef mExperiment : mExperimentalPreparation.getExperimentRefs() ) {
                jExperimentalPreparation.getExperimentRefList().getExperimentReves().add( mExperiment.getRef() );
            }
        }

        return jExperimentalPreparation;
    }
}