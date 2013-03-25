/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl254;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileParsingErrorType;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.PsiDAO;
import psidev.psi.mi.xml.events.MissingElementEvent;
import psidev.psi.mi.xml.events.MultipleHostOrganismsPerExperiment;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml254.jaxb.AttributeList;
import psidev.psi.mi.xml254.jaxb.ConfidenceList;
import psidev.psi.mi.xml254.jaxb.HostOrganism;
import psidev.psi.mi.xml254.jaxb.HostOrganismList;

import java.util.HashSet;
import java.util.List;

/**
 * Converter to and from JAXB of the class ExperimentDescription.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.ExperimentDescription
 * @see psidev.psi.mi.xml254.jaxb.ExperimentDescription
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

    private List<PsiXml25ParserListener> listeners;

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


    public void setListeners(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
        this.xrefConverter.setListeners(listeners);
        this.bibrefConverter.setListeners(listeners);
        this.attributeConverter.setListeners(listeners);
        this.cvTypeConverter.setListeners(listeners);
        this.namesConverter.setListeners(listeners);
        this.cvTypeConverter.setListeners(listeners);
        this.organismConverter.setListeners(listeners);
    }

    public DAOFactory getFactory() {
        return factory;
    }

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

    public psidev.psi.mi.xml.model.ExperimentDescription fromJaxb( psidev.psi.mi.xml254.jaxb.ExperimentDescription jExperimentDescription ) throws ConverterException {

        if ( jExperimentDescription == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB ExperimentDescription." );
        }

        checkDependencies();

        psidev.psi.mi.xml.model.ExperimentDescription mExperimentDescription = new psidev.psi.mi.xml.model.ExperimentDescription();
        Locator locator = jExperimentDescription.sourceLocation();
        mExperimentDescription.setSourceLocator(new PsiXmlFileLocator(locator.getLineNumber(), locator.getColumnNumber(), jExperimentDescription.getId()));
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
        else {
            // we don't have a publication
            if (listeners != null && !listeners.isEmpty()){
                MissingElementEvent evt = new MissingElementEvent("No publication attached to this experiment.", FileParsingErrorType.missing_publication);
                evt.setSourceLocator(mExperimentDescription.getSourceLocator());
                for (PsiXml25ParserListener l : listeners){
                    l.fireOnMissingElementEvent(evt);
                }
            }
        }

        // xref
        if ( jExperimentDescription.getXref() != null ) {
            mExperimentDescription.setXref( xrefConverter.fromJaxb( jExperimentDescription.getXref() ) );
        }

        // host organism
        if ( jExperimentDescription.getHostOrganismList() != null ) {
            for ( psidev.psi.mi.xml254.jaxb.HostOrganism jHostOrganism :
                    jExperimentDescription.getHostOrganismList().getHostOrganisms() ) {
                mExperimentDescription.getHostOrganisms().add( organismConverter.fromJaxb( jHostOrganism ) );

                // we have more than one host organism
                if (listeners != null && !listeners.isEmpty() && jExperimentDescription.getHostOrganismList() != null && jExperimentDescription.getHostOrganismList().getHostOrganisms().size() > 1){
                    MultipleHostOrganismsPerExperiment evt = new MultipleHostOrganismsPerExperiment(mExperimentDescription, new HashSet<Organism>(mExperimentDescription.getHostOrganisms()), "Experiment " +mExperimentDescription.getId() + " contains "+jExperimentDescription.getHostOrganismList().getHostOrganisms().size()+" host organisms.");
                    evt.setSourceLocator(mExperimentDescription.getSourceLocator());
                    for (PsiXml25ParserListener l : listeners){
                        l.fireOnMultipleHostOrganismsPerExperimentEvent(evt);
                    }
                }
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
            for ( psidev.psi.mi.xml254.jaxb.Confidence jConfidence :
                    jExperimentDescription.getConfidenceList().getConfidences() ) {
                mExperimentDescription.getConfidences().add( confidenceConverter.fromJaxb( jConfidence ) );
            }
        }

        // attributes
        if ( jExperimentDescription.getAttributeList() != null ) {
            for ( psidev.psi.mi.xml254.jaxb.Attribute jAttribute : jExperimentDescription.getAttributeList().getAttributes() ) {
                mExperimentDescription.getAttributes().add( attributeConverter.fromJaxb( jAttribute ) );
            }
        }

        PsiDAO<ExperimentDescription> experimentDAO = factory.getExperimentDAO();
        experimentDAO.store( mExperimentDescription );

        return mExperimentDescription;
    }

    public psidev.psi.mi.xml254.jaxb.ExperimentDescription toJaxb( psidev.psi.mi.xml.model.ExperimentDescription mExperimentDescription ) throws ConverterException {

        if ( mExperimentDescription == null ) {
            throw new IllegalArgumentException( "You must give a non null model ExperimentDescription." );
        }

        checkDependencies();

        psidev.psi.mi.xml254.jaxb.ExperimentDescription jExperimentDescription = new psidev.psi.mi.xml254.jaxb.ExperimentDescription();

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
                jExperimentDescription.setHostOrganismList( new HostOrganismList() );
            }
            jExperimentDescription.getHostOrganismList().getHostOrganisms().add(
                    organismConverter.toJaxb( mOrganism, HostOrganism.class ) );
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
                jExperimentDescription.setConfidenceList( new ConfidenceList() );
            }

            jExperimentDescription.getConfidenceList().getConfidences().add( confidenceConverter.toJaxb( mConfidence ) );
        }

        // attributes
        for ( Attribute mAttribute : mExperimentDescription.getAttributes() ) {
            if ( jExperimentDescription.getAttributeList() == null ) {
                jExperimentDescription.setAttributeList( new AttributeList() );
            }

            jExperimentDescription.getAttributeList().getAttributes().add( attributeConverter.toJaxb( mAttribute ) );
        }

        return jExperimentDescription;
    }
}