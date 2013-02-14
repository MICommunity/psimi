/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.PsiDAO;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml253.jaxb.AttributeListType;
import psidev.psi.mi.xml253.jaxb.ConfidenceListType;
import psidev.psi.mi.xml253.jaxb.ExperimentType;

/**
 * Converter to and from JAXB of the class ExperimentDescription.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.ExperimentDescription
 * @see psidev.psi.mi.xml253.jaxb.ExperimentType
 * @since <pre>07-Jun-2006</pre>
 */
public class ExperimentDescriptionConverter {

    ////////////////////////
    // Instance variables

    private AttributeConverter attributeConverter;
    private BibrefConverter bibrefConverter;
    private CvTypeConverter cvTypeConverter;
    private NamesConverter namesConverter;
    private ConfidenceConverter confidenceConverter;
    private OrganismConverter organismConverter;
    private XrefConverter xrefConverter;

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    //////////////////////////
    // Constructor

    public ExperimentDescriptionConverter() {
        attributeConverter = new AttributeConverter();
        bibrefConverter = new BibrefConverter();
        cvTypeConverter = new CvTypeConverter();
        namesConverter = new NamesConverter();
        confidenceConverter = new ConfidenceConverter();
        organismConverter = new OrganismConverter();
        xrefConverter = new XrefConverter();
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

        // initialise sub converters
        confidenceConverter.setDAOFactory( factory );
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

    /////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.ExperimentDescription fromJaxb( psidev.psi.mi.xml253.jaxb.ExperimentType jExperimentDescription ) throws ConverterException {

        if ( jExperimentDescription == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB ExperimentDescription." );
        }

        checkDependencies();

        psidev.psi.mi.xml.model.ExperimentDescription mExperimentDescription = new psidev.psi.mi.xml.model.ExperimentDescription();

        // 1. set attributes

        mExperimentDescription.setId( jExperimentDescription.getId() );

        // 2. set encapsulated objects

        // names
        if ( jExperimentDescription.getNames() != null ) {
            mExperimentDescription.setNames( namesConverter.fromJaxb( jExperimentDescription.getNames() ) );
        }

        // bib ref
        if ( jExperimentDescription.getBibref() != null ) {
            mExperimentDescription.setBibref( bibrefConverter.fromJaxb( jExperimentDescription.getBibref() ) );
        }

        // xref
        if ( jExperimentDescription.getXref() != null ) {
            mExperimentDescription.setXref( xrefConverter.fromJaxb( jExperimentDescription.getXref() ) );
        }

        // host organism
        if ( jExperimentDescription.getHostOrganismList() != null ) {
            for ( ExperimentType.HostOrganismList.HostOrganism jHostOrganism :
                    jExperimentDescription.getHostOrganismList().getHostOrganisms() ) {
                mExperimentDescription.getHostOrganisms().add( organismConverter.fromJaxb( jHostOrganism ) );
            }
        }

        // interaction detection method
        if ( jExperimentDescription.getInteractionDetectionMethod() != null ) {
            mExperimentDescription.setInteractionDetectionMethod(
                    cvTypeConverter.fromJaxb( jExperimentDescription.getInteractionDetectionMethod(),
                                              InteractionDetectionMethod.class ) );
        }

        // participant detection method
        if ( jExperimentDescription.getParticipantIdentificationMethod() != null ) {
            mExperimentDescription.setParticipantIdentificationMethod(
                    cvTypeConverter.fromJaxb( jExperimentDescription.getParticipantIdentificationMethod(),
                                              ParticipantIdentificationMethod.class ) );
        }

        // feature detection method
        if ( jExperimentDescription.getFeatureDetectionMethod() != null ) {
            mExperimentDescription.setFeatureDetectionMethod(
                    cvTypeConverter.fromJaxb( jExperimentDescription.getFeatureDetectionMethod(),
                                              FeatureDetectionMethod.class ) );
        }

        // confidence
        if ( jExperimentDescription.getConfidenceList() != null ) {
            for ( ConfidenceListType.Confidence jConfidence :
                    jExperimentDescription.getConfidenceList().getConfidences() ) {
                mExperimentDescription.getConfidences().add( confidenceConverter.fromJaxb( jConfidence ) );
            }
        }

        // attributes
        if ( jExperimentDescription.getAttributeList() != null ) {
            for ( AttributeListType.Attribute jAttribute : jExperimentDescription.getAttributeList().getAttributes() ) {
                mExperimentDescription.getAttributes().add( attributeConverter.fromJaxb( jAttribute ) );
            }
        }

        PsiDAO<ExperimentDescription> experimentDAO = factory.getExperimentDAO();
        experimentDAO.store( mExperimentDescription );

        return mExperimentDescription;
    }

    public psidev.psi.mi.xml253.jaxb.ExperimentType toJaxb( psidev.psi.mi.xml.model.ExperimentDescription mExperimentDescription ) throws ConverterException {

        if ( mExperimentDescription == null ) {
            throw new IllegalArgumentException( "You must give a non null model ExperimentDescription." );
        }

        checkDependencies();

        psidev.psi.mi.xml253.jaxb.ExperimentType jExperimentDescription = new psidev.psi.mi.xml253.jaxb.ExperimentType();

        // 1. set attributes
        jExperimentDescription.setId( mExperimentDescription.getId() );

        // 2. set encapsulated objects

        // names
        if ( mExperimentDescription.getNames() != null ) {
            jExperimentDescription.setNames( namesConverter.toJaxb( mExperimentDescription.getNames() ) );
        }

        // bibref
        if ( mExperimentDescription.getBibref() != null ) {
            jExperimentDescription.setBibref( bibrefConverter.toJaxb( mExperimentDescription.getBibref() ) );
        }

        // xref
        if ( mExperimentDescription.hasXref() ) {
            jExperimentDescription.setXref( xrefConverter.toJaxb( mExperimentDescription.getXref() ) );
        }

        // organisms
        for ( Organism mOrganism : mExperimentDescription.getHostOrganisms() ) {
            if ( jExperimentDescription.getHostOrganismList() == null ) {
                jExperimentDescription.setHostOrganismList( new ExperimentType.HostOrganismList() );
            }
            jExperimentDescription.getHostOrganismList().getHostOrganisms().add(
                    organismConverter.toJaxb( mOrganism, ExperimentType.HostOrganismList.HostOrganism.class ) );
        }

        // interaction detection method
        if ( mExperimentDescription.getInteractionDetectionMethod() != null ) {
            jExperimentDescription.setInteractionDetectionMethod(
                    cvTypeConverter.toJaxb( mExperimentDescription.getInteractionDetectionMethod() ) );
        }

        // participant detection method
        if ( mExperimentDescription.getParticipantIdentificationMethod() != null ) {
            jExperimentDescription.setParticipantIdentificationMethod(
                    cvTypeConverter.toJaxb( mExperimentDescription.getParticipantIdentificationMethod() ) );
        }

        // feature detection method
        if ( mExperimentDescription.getFeatureDetectionMethod() != null ) {
            jExperimentDescription.setFeatureDetectionMethod(
                    cvTypeConverter.toJaxb( mExperimentDescription.getFeatureDetectionMethod() ) );
        }

        // confidence
        for ( Confidence mConfidence : mExperimentDescription.getConfidences() ) {
            if ( jExperimentDescription.getConfidenceList() == null ) {
                jExperimentDescription.setConfidenceList( new ConfidenceListType() );
            }

            jExperimentDescription.getConfidenceList().getConfidences().add( confidenceConverter.toJaxb( mConfidence ) );
        }

        // attributes
        for ( Attribute mAttribute : mExperimentDescription.getAttributes() ) {
            if ( jExperimentDescription.getAttributeList() == null ) {
                jExperimentDescription.setAttributeList( new AttributeListType() );
            }

            jExperimentDescription.getAttributeList().getAttributes().add( attributeConverter.toJaxb( mAttribute ) );
        }

        return jExperimentDescription;
    }
}