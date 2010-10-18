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
 * Converter to and from JAXB of the class ParticipantIdentificationMethod.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.ParticipantIdentificationMethod
 * @see psidev.psi.mi.xml254.jaxb.ParticipantIdentificationMethod
 * @since <pre>07-Jun-2006</pre>
 */
public class ParticipantIdentificationMethodConverter {

    ///////////////////////////
    // Instance variables

    private CvTypeConverter cvTypeConverter;

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    public ParticipantIdentificationMethodConverter() {
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

    public psidev.psi.mi.xml.model.ParticipantIdentificationMethod
    fromJaxb( psidev.psi.mi.xml254.jaxb.ParticipantIdentificationMethod jParticipantIdentificationMethod )
            throws ConverterException {

        if ( jParticipantIdentificationMethod == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Participant Identification Method." );
        }

        // Initialise the model reading the Jaxb object

        // 1. convert the CvType
        psidev.psi.mi.xml.model.ParticipantIdentificationMethod mParticipantIdentificationMethod =
                cvTypeConverter.fromJaxb( jParticipantIdentificationMethod,
                                          psidev.psi.mi.xml.model.ParticipantIdentificationMethod.class );

        // 2. convert remaining components

        // experiments
        if ( jParticipantIdentificationMethod.getExperimentRefList() != null ) {
            for ( Integer jExperimentId : jParticipantIdentificationMethod.getExperimentRefList().getExperimentReves() ) {
                ExperimentDescription mExperiment = factory.getExperimentDAO().retreive( jExperimentId );
                if ( mExperiment == null ) {
                    mParticipantIdentificationMethod.getExperimentRefs().add( new ExperimentRef( jExperimentId ) );
                } else {
                    mParticipantIdentificationMethod.getExperiments().add( mExperiment );
                }
            }
        }

        return mParticipantIdentificationMethod;
    }

    public psidev.psi.mi.xml254.jaxb.ParticipantIdentificationMethod
    toJaxb( psidev.psi.mi.xml.model.ParticipantIdentificationMethod mParticipantIdentificationMethod ) throws ConverterException {

        if ( mParticipantIdentificationMethod == null ) {
            throw new IllegalArgumentException( "You must give a non null model Participant Identification Method." );
        }

        // Initialise the JAXB object reading the model

        // 1. convert the CvType
        psidev.psi.mi.xml254.jaxb.ParticipantIdentificationMethod jParticipantIdentificationMethod =
                cvTypeConverter.toJaxb( mParticipantIdentificationMethod,
                                        psidev.psi.mi.xml254.jaxb.ParticipantIdentificationMethod.class );

        // 2. convert remaining components

        // experiments
        if ( mParticipantIdentificationMethod.hasExperiments() ) {
            for ( ExperimentDescription mExperiment : mParticipantIdentificationMethod.getExperiments() ) {
                addExperimentRef( jParticipantIdentificationMethod, mExperiment.getId() );
            }
        } else if ( mParticipantIdentificationMethod.hasExperimentRefs() ) {
            for ( ExperimentRef mExperiment : mParticipantIdentificationMethod.getExperimentRefs() ) {
                addExperimentRef( jParticipantIdentificationMethod, mExperiment.getRef() );
            }
        }

        return jParticipantIdentificationMethod;
    }

    protected void addExperimentRef( psidev.psi.mi.xml254.jaxb.ParticipantIdentificationMethod participantIdentificationMethod,
                                     int experimentRef ) {
        if ( participantIdentificationMethod.getExperimentRefList() == null ) {
            participantIdentificationMethod.setExperimentRefList( new ExperimentRefList() );
        }

        participantIdentificationMethod.getExperimentRefList().getExperimentReves().add( experimentRef );
    }
}