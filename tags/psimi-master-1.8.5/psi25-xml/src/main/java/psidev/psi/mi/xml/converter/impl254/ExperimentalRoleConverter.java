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
import psidev.psi.mi.xml.model.ExperimentalRole;
import psidev.psi.mi.xml254.jaxb.ExperimentRefList;

/**
 * Converter to and from JAXB of the class ExperimentalRole.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.ExperimentalRole
 * @see psidev.psi.mi.xml254.jaxb.ExperimentalRole
 * @since <pre>07-Jun-2006</pre>
 */
public class ExperimentalRoleConverter {

    ///////////////////////////
    // Instance variables

    private CvTypeConverter cvTypeConverter;

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    public ExperimentalRoleConverter() {
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

    public psidev.psi.mi.xml.model.ExperimentalRole fromJaxb( psidev.psi.mi.xml254.jaxb.ExperimentalRole jExperimentalRole ) throws ConverterException {

        if ( jExperimentalRole == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB ExperimentalRole." );
        }

        // Initialise the model reading the Jaxb object

        // 1. convert the CvType
        ExperimentalRole mExperimentalRole = cvTypeConverter.fromJaxb( jExperimentalRole,
                                                                       ExperimentalRole.class );

        // 2. convert remaining components

        // experiments
        if ( jExperimentalRole.getExperimentRefList() != null ) {
            for ( Integer jExperimentId : jExperimentalRole.getExperimentRefList().getExperimentReves() ) {
                ExperimentDescription mExperiment = factory.getExperimentDAO().retreive( jExperimentId );
                if ( mExperiment == null ) {
                    mExperimentalRole.getExperimentRefs().add( new ExperimentRef( jExperimentId ) );
                } else {
                    mExperimentalRole.getExperiments().add( mExperiment );
                }
            }
        }

        return mExperimentalRole;
    }

    public psidev.psi.mi.xml254.jaxb.ExperimentalRole toJaxb( psidev.psi.mi.xml.model.ExperimentalRole mExperimentalRole ) throws ConverterException {

        if ( mExperimentalRole == null ) {
            throw new IllegalArgumentException( "You must give a non null model ExperimentalRole." );
        }

        // Initialise the JAXB object reading the model

        // 1. convert the CvType
        psidev.psi.mi.xml254.jaxb.ExperimentalRole jExperimentalRole =
                cvTypeConverter.toJaxb( mExperimentalRole,
                                        psidev.psi.mi.xml254.jaxb.ExperimentalRole.class );

        // 2. convert remaining components

        // experiments

        if ( mExperimentalRole.hasExperiments() ) {
            if ( jExperimentalRole.getExperimentRefList() == null ) {
                jExperimentalRole.setExperimentRefList( new ExperimentRefList() );
            }
            for ( ExperimentDescription mExperiment : mExperimentalRole.getExperiments() ) {
                jExperimentalRole.getExperimentRefList().getExperimentReves().add( mExperiment.getId() );
            }
        } else if ( mExperimentalRole.hasExperimentRefs() ) {
            if ( jExperimentalRole.getExperimentRefList() == null ) {
                jExperimentalRole.setExperimentRefList( new ExperimentRefList() );
            }
            for ( ExperimentRef mExperiment : mExperimentalRole.getExperimentRefs() ) {
                jExperimentalRole.getExperimentRefList().getExperimentReves().add( mExperiment.getRef() );
            }
        }

        return jExperimentalRole;
    }
}