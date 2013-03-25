/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.ExperimentRef;
import psidev.psi.mi.xml.model.ExperimentalRole;
import psidev.psi.mi.xml253.jaxb.ExperimentRefListType;
import psidev.psi.mi.xml253.jaxb.ParticipantType;

import java.util.List;

/**
 * Converter to and from JAXB of the class ExperimentalRole.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.ExperimentalRole
 * @see psidev.psi.mi.xml253.jaxb.ParticipantType.ExperimentalRoleList.ExperimentalRole
 * @since <pre>07-Jun-2006</pre>
 */
public class ExperimentalRoleConverter {

    ///////////////////////////
    // Instance variables

    private CvTypeConverter cvTypeConverter;
    private List<PsiXml25ParserListener> listeners;

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    public ExperimentalRoleConverter() {
        cvTypeConverter = new CvTypeConverter();
    }

    public void setListeners(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
        cvTypeConverter.setListeners(this.listeners);
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

    public psidev.psi.mi.xml.model.ExperimentalRole fromJaxb( psidev.psi.mi.xml253.jaxb.ParticipantType.ExperimentalRoleList.ExperimentalRole jExperimentalRole ) throws ConverterException {

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

    public psidev.psi.mi.xml253.jaxb.ParticipantType.ExperimentalRoleList.ExperimentalRole toJaxb( psidev.psi.mi.xml.model.ExperimentalRole mExperimentalRole ) throws ConverterException {

        if ( mExperimentalRole == null ) {
            throw new IllegalArgumentException( "You must give a non null model ExperimentalRole." );
        }

        // Initialise the JAXB object reading the model

        // 1. convert the CvType
        ParticipantType.ExperimentalRoleList.ExperimentalRole jExperimentalRole =
                cvTypeConverter.toJaxb( mExperimentalRole,
                                        ParticipantType.ExperimentalRoleList.ExperimentalRole.class );

        // 2. convert remaining components

        // experiments

        if ( mExperimentalRole.hasExperiments() ) {
            if ( jExperimentalRole.getExperimentRefList() == null ) {
                jExperimentalRole.setExperimentRefList( new ExperimentRefListType() );
            }
            for ( ExperimentDescription mExperiment : mExperimentalRole.getExperiments() ) {
                jExperimentalRole.getExperimentRefList().getExperimentReves().add( mExperiment.getId() );
            }
        } else if ( mExperimentalRole.hasExperimentRefs() ) {
            if ( jExperimentalRole.getExperimentRefList() == null ) {
                jExperimentalRole.setExperimentRefList( new ExperimentRefListType() );
            }
            for ( ExperimentRef mExperiment : mExperimentalRole.getExperimentRefs() ) {
                jExperimentalRole.getExperimentRefList().getExperimentReves().add( mExperiment.getRef() );
            }
        }

        return jExperimentalRole;
    }
}