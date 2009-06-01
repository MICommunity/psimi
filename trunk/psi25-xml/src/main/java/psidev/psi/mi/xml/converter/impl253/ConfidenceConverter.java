/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.PsiDAO;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.ExperimentRef;
import psidev.psi.mi.xml253.jaxb.ExperimentRefListType;

/**
 * Converter to and from JAXB of the class Confidence.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Confidence
 * @see psidev.psi.mi.xml253.jaxb.ConfidenceType
 * @since <pre>07-Jun-2006</pre>
 */
public class ConfidenceConverter {

    ////////////////////////
    // instance variable

    private OpenCvTypeConverter openCvTypeConverter;

    private DAOFactory factory;

    ////////////////////////
    // Constructor

    public ConfidenceConverter() {
        openCvTypeConverter = new OpenCvTypeConverter();
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

    ///////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Confidence fromJaxb( psidev.psi.mi.xml253.jaxb.ConfidenceListType.Confidence jConfidence ) throws ConverterException {

        if ( jConfidence == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Confidence." );
        }

        checkDependencies();

        psidev.psi.mi.xml.model.Confidence mConfidence = new psidev.psi.mi.xml.model.Confidence();

        // Initialise the model reading the Jaxb object

        // 1. set attributes
        mConfidence.setValue( jConfidence.getValue() );

        // 2. set encapsulated objects
        mConfidence.setUnit( openCvTypeConverter.fromJaxb( jConfidence.getUnit(),
                                                           psidev.psi.mi.xml.model.Unit.class ) );

        // experiments
        if ( jConfidence.getExperimentRefList() != null ) {
            PsiDAO<ExperimentDescription> experimentDAO = factory.getExperimentDAO();
            for ( Integer experimentId : jConfidence.getExperimentRefList().getExperimentReves() ) {
                ExperimentDescription experimentDescription = experimentDAO.retreive( experimentId );
                if ( experimentDescription == null ) {
                    mConfidence.getExperimentRefs().add( new ExperimentRef( experimentId ) );
                } else {
                    mConfidence.getExperiments().add( experimentDescription );
                }
            }
        }

        return mConfidence;
    }

    public psidev.psi.mi.xml253.jaxb.ConfidenceListType.Confidence toJaxb( psidev.psi.mi.xml.model.Confidence mConfidence ) throws ConverterException {

        if ( mConfidence == null ) {
            throw new IllegalArgumentException( "You must give a non null model Confidence." );
        }

        checkDependencies();

        psidev.psi.mi.xml253.jaxb.ConfidenceListType.Confidence jConfidence =
                new psidev.psi.mi.xml253.jaxb.ConfidenceListType.Confidence();

        // Initialise the JAXB object reading the model

        // 1. set attributes
        jConfidence.setValue( mConfidence.getValue() );

        // 2. set encapsulated objects
        jConfidence.setUnit( openCvTypeConverter.toJaxb( mConfidence.getUnit() ) );

        // experiments
        if ( mConfidence.hasExperiments() ) {
            if ( jConfidence.getExperimentRefList() == null ) {
                jConfidence.setExperimentRefList( new ExperimentRefListType() );
            }
            for ( ExperimentDescription mExperiment : mConfidence.getExperiments() ) {
                jConfidence.getExperimentRefList().getExperimentReves().add( mExperiment.getId() );
            }
        } else if ( mConfidence.hasExperimentRefs() ) {
            if ( jConfidence.getExperimentRefList() == null ) {
                jConfidence.setExperimentRefList( new ExperimentRefListType() );
            }
            for ( ExperimentRef mExperiment : mConfidence.getExperimentRefs() ) {
                jConfidence.getExperimentRefList().getExperimentReves().add( mExperiment.getRef() );
            }
        }

        return jConfidence;
    }
}