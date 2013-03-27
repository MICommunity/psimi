/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileParsingErrorType;
import psidev.psi.mi.xml.PsimiXmlForm;
import psidev.psi.mi.xml.converter.ConverterContext;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.PsiDAO;
import psidev.psi.mi.xml.events.*;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml253.jaxb.AttributeListType;
import psidev.psi.mi.xml253.jaxb.ConfidenceListType;
import psidev.psi.mi.xml253.jaxb.FeatureElementType;
import psidev.psi.mi.xml253.jaxb.ParticipantType;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Converter to and from JAXB of the class Participant.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Participant
 * @see psidev.psi.mi.xml253.jaxb.ParticipantType
 * @since <pre>07-Jun-2006</pre>
 */
public class ParticipantConverter {

    ///////////////////////////
    // Instance variable

    private CvTypeConverter cvTypeConverter;
    private NamesConverter namesConverter;
    private XrefConverter xrefConverter;
    private ConfidenceConverter confidenceConverter;
    private ExperimentalInteractorConverter experimentalInteractorConverter;
    private FeatureConverter featureConverter;
    private HostOrganismConverter hostOrganismConverter;
    private ParticipantParameterConverter parameterConverter;
    private InteractorConverter interactorConverter;
    private ExperimentalRoleConverter experimentalRoleConverter;
    private ExperimentalPreparationConverter experimentalPreparationConverter;
    private ParticipantIdentificationMethodConverter participantIdentificationMethodConverter;
    private AttributeConverter attributeConverter;

    private List<PsiXml25ParserListener> listeners;

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    /////////////////////
    // Constructor

    public ParticipantConverter() {
        cvTypeConverter = new CvTypeConverter();
        namesConverter = new NamesConverter();
        xrefConverter = new XrefConverter();
        confidenceConverter = new ConfidenceConverter();
        experimentalInteractorConverter = new ExperimentalInteractorConverter();
        featureConverter = new FeatureConverter();
        hostOrganismConverter = new HostOrganismConverter();
        parameterConverter = new ParticipantParameterConverter();
        interactorConverter = new InteractorConverter();
        experimentalRoleConverter = new ExperimentalRoleConverter();
        experimentalPreparationConverter = new ExperimentalPreparationConverter();
        participantIdentificationMethodConverter = new ParticipantIdentificationMethodConverter();
        this.attributeConverter = new AttributeConverter();
    }

    ///////////////////////////////
    // DAO factory stategy


    public void setListeners(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
        this.cvTypeConverter.setListeners(listeners);
        this.namesConverter.setListeners(listeners);
        this.xrefConverter.setListeners(listeners);
        this.confidenceConverter.setListeners(listeners);
        this.experimentalInteractorConverter.setListeners(listeners);
        this.featureConverter.setListeners(listeners);
        this.hostOrganismConverter.setListeners(listeners);
        this.parameterConverter.setListeners(listeners);
        this.interactorConverter.setListeners(listeners);
        this.experimentalRoleConverter.setListeners(listeners);
        this.experimentalPreparationConverter.setListeners(listeners);
        this.participantIdentificationMethodConverter.setListeners(listeners);
        this.attributeConverter.setListeners(listeners);
    }

    /**
     * Set the DAO Factory that holds required DAOs for resolving ids.
     *
     * @param factory the DAO factory
     */
    public void setDAOFactory( DAOFactory factory ) {
        this.factory = factory;

        // set DAO in sub DAOs
        featureConverter.setDAOFactory( factory );
        hostOrganismConverter.setDAOFactory( factory );
        interactorConverter.setDAOFactory( factory );
        confidenceConverter.setDAOFactory( factory );
        parameterConverter.setDAOFactory( factory );
        experimentalPreparationConverter.setDAOFactory(factory);
        experimentalRoleConverter.setDAOFactory(factory);
        experimentalInteractorConverter.setDAOFactory(factory);
        participantIdentificationMethodConverter.setDAOFactory(factory);
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

    ///////////////////////////////
    // Converter methods

    public Participant fromJaxb( ParticipantType jParticipant ) throws ConverterException {

        if ( jParticipant == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Participant." );
        }

        checkDependencies();

        Participant mParticipant = new Participant();
        Locator locator = jParticipant.sourceLocation();
        mParticipant.setSourceLocator(new PsiXmlFileLocator(locator.getLineNumber(), locator.getColumnNumber(), jParticipant.getId()));

        // Initialise the model reading the Jaxb object

        // 1. set attributes
        mParticipant.setId( jParticipant.getId() );

        // 2. set encapsulated objects

        // interactor/interaction
        Interactor mInteractor = null;
        Interaction mInteraction = null;
        boolean foundInteractor = false;

        if ( jParticipant.getInteractorRef() != null ) {

            foundInteractor = true;
            mInteractor = factory.getInteractorDAO().retreive( jParticipant.getInteractorRef() );
            if ( mInteractor == null ) {
                mParticipant.setInteractorRef( new InteractorRef( jParticipant.getInteractorRef() ) );
            } else {
                mParticipant.setInteractor( mInteractor );
            }

        } else if ( jParticipant.getInteractor() != null ) {

            foundInteractor = true;
            mInteractor = interactorConverter.fromJaxb( jParticipant.getInteractor() );
            mParticipant.setInteractor( mInteractor );

        } else if ( jParticipant.getInteractionRef() != null ) {

            foundInteractor = true;
            mInteraction = factory.getInteractionDAO().retreive( jParticipant.getInteractionRef() );
            if ( mInteraction == null ) {
                mParticipant.setInteractionRef( new InteractionRef( jParticipant.getInteractionRef() ) );
            }
            else {
                mParticipant.setInteraction( mInteraction);
            }
        }

        if ( !foundInteractor ) {
            InvalidXmlEvent evt = new InvalidXmlEvent(FileParsingErrorType.participant_without_interactor, "Could not find either an interactor or an interaction for participant (id=" + jParticipant.getId() + ").");
            evt.setSourceLocator(mParticipant.getSourceLocator());
            for (PsiXml25ParserListener l : listeners){
                l.fireOnInvalidXmlSyntax(evt);
            }
        }

        // BiologigicalRoles
        if ( jParticipant.getBiologicalRole() != null ) {
            mParticipant.setBiologicalRole( cvTypeConverter.fromJaxb( jParticipant.getBiologicalRole(), BiologicalRole.class ) );
        }
        // we have more than one identification methods
        else if (listeners != null && !listeners.isEmpty()){
            MissingElementEvent evt = new MissingElementEvent("The biological role is missing for participant " + mParticipant.getId(), FileParsingErrorType.missing_biological_role);
            evt.setSourceLocator(mParticipant.getSourceLocator());

            for (PsiXml25ParserListener l : listeners){
                l.fireOnMissingElementEvent(evt);
            }
        }

        // Names
        if ( jParticipant.getNames() != null ) {
            mParticipant.setNames( namesConverter.fromJaxb( jParticipant.getNames() ) );
        }

        // Xrefs
        if ( jParticipant.getXref() != null ) {
            mParticipant.setXref( xrefConverter.fromJaxb( jParticipant.getXref() ) );
        }

        // ConfidenceList
        if ( jParticipant.getConfidenceList() != null ) {
            for ( ConfidenceListType.Confidence jConfidence : jParticipant.getConfidenceList().getConfidences() ) {
                mParticipant.getConfidenceList().add( confidenceConverter.fromJaxb( jConfidence ) );
            }
        }

        // ExperimentalInteractor
        if ( jParticipant.getExperimentalInteractorList() != null ) {
            for ( ParticipantType.ExperimentalInteractorList.ExperimentalInteractor jExperimentalInteractor :
                    jParticipant.getExperimentalInteractorList().getExperimentalInteractors() ) {
                mParticipant.getExperimentalInteractors().add( experimentalInteractorConverter.fromJaxb( jExperimentalInteractor ) );
            }
        }

        // ExperimentalPreparations
        if ( jParticipant.getExperimentalPreparationList() != null ) {
            for ( ParticipantType.ExperimentalPreparationList.ExperimentalPreparation jExperimentalPreparation :
                    jParticipant.getExperimentalPreparationList().getExperimentalPreparations() ) {
                mParticipant.getParticipantExperimentalPreparations().add( experimentalPreparationConverter.fromJaxb( jExperimentalPreparation ) );
            }
        }

        // ExperimentalRoles
        if ( jParticipant.getExperimentalRoleList() != null ) {
            for ( ParticipantType.ExperimentalRoleList.ExperimentalRole jExperimentalRole :
                    jParticipant.getExperimentalRoleList().getExperimentalRoles() ) {

                mParticipant.getExperimentalRoles().add( experimentalRoleConverter.fromJaxb( jExperimentalRole ) );
            }

            // we have more than one experimental role
            if (listeners != null && !listeners.isEmpty() && jParticipant.getExperimentalRoleList().getExperimentalRoles().size() > 1){
                MultipleExperimentalRolesEvent evt = new MultipleExperimentalRolesEvent(null, mParticipant, new HashSet<ExperimentalRole>(mParticipant.getExperimentalRoles()), "Participant "+mParticipant.getId()+" contains "+jParticipant.getExperimentalRoleList().getExperimentalRoles().size()+" experimental roles.");
                evt.setSourceLocator(mParticipant.getSourceLocator());
                for (PsiXml25ParserListener l : listeners){
                    l.fireOnMultipleExperimentalRolesEvent(evt);
                }
            }
        }

        // Features
        if ( jParticipant.getFeatureList() != null ) {
            for ( FeatureElementType jFeature : jParticipant.getFeatureList().getFeatures() ) {
                mParticipant.getFeatures().add( featureConverter.fromJaxb( jFeature ) );
            }
        }

        // HostOrganisms
        if ( jParticipant.getHostOrganismList() != null ) {
            for ( ParticipantType.HostOrganismList.HostOrganism jOrganism :
                    jParticipant.getHostOrganismList().getHostOrganisms() ) {
                mParticipant.getHostOrganisms().add( hostOrganismConverter.fromJaxb( jOrganism ) );
            }

            // we have more than one expressed in
            if (listeners != null && !listeners.isEmpty() && jParticipant.getHostOrganismList().getHostOrganisms().size() > 1){
                MultipleExpressedInOrganisms evt = new MultipleExpressedInOrganisms(null, mParticipant, new HashSet<HostOrganism>(mParticipant.getHostOrganisms()), "Participant "+mParticipant.getId()+" has been expressed in "+jParticipant.getHostOrganismList().getHostOrganisms().size()+" host organisms.");
                evt.setSourceLocator(mParticipant.getSourceLocator());
                for (PsiXml25ParserListener l : listeners){
                    l.fireOnMultipleExpressedInOrganismsEvent(evt);
                }
            }
        }

        // Parameters
        // We handle interaction's and participant's parameters as they may be different JAXB Type.
        // psidev.psi.mi.xml253.jaxb.ParticipantType.ParameterList.Parameter
        // psidev.psi.mi.xml253.jaxb.InteractionElementType.ParameterList.Parameter
        // TODO once the schema is fixed, revert to a single converter.
        if ( jParticipant.getParameterList() != null ) {
            for ( psidev.psi.mi.xml253.jaxb.ParticipantType.ParameterList.Parameter jParameter :
                    jParticipant.getParameterList().getParameters() ) {
                mParticipant.getParametersList().add( parameterConverter.fromJaxb( jParameter ) );
            }
        }

        // ParticipantIdentificationMethod
        if ( jParticipant.getParticipantIdentificationMethodList() != null ) {
            for ( ParticipantType.ParticipantIdentificationMethodList.ParticipantIdentificationMethod jParticipantIdentificationMethod :
                    jParticipant.getParticipantIdentificationMethodList().getParticipantIdentificationMethods() ) {

                mParticipant.getParticipantIdentificationMethods()
                        .add( participantIdentificationMethodConverter.fromJaxb( jParticipantIdentificationMethod ) );
            }

            // we have more than one identification methods
            if (listeners != null && !listeners.isEmpty() && jParticipant.getParticipantIdentificationMethodList().getParticipantIdentificationMethods().size() > 1){
                MultipleParticipantIdentificationMethodsPerParticipant evt = new MultipleParticipantIdentificationMethodsPerParticipant(null, mParticipant, new HashSet<ParticipantIdentificationMethod>(mParticipant.getParticipantIdentificationMethods()), "Participant "+mParticipant.getId()+" contains "+jParticipant.getParticipantIdentificationMethodList().getParticipantIdentificationMethods().size()+" participant identification methods.");
                evt.setSourceLocator(mParticipant.getSourceLocator());
                for (PsiXml25ParserListener l : listeners){
                    l.fireOnMultipleParticipantIdentificationMethodsEvent(evt);
                }
            }
        }

        // attributes
        if ( jParticipant.getAttributeList() != null ) {
            for (AttributeListType.Attribute attribute : jParticipant.getAttributeList().getAttributes()) {

                mParticipant.getAttributes()
                        .add( attributeConverter.fromJaxb( attribute ) );
            }
        }

        // store the participant via DAO
        PsiDAO<Participant> participantDAO = factory.getParticipantDAO();
        participantDAO.store( mParticipant );

        return mParticipant;
    }

    public psidev.psi.mi.xml253.jaxb.ParticipantType toJaxb( psidev.psi.mi.xml.model.Participant mParticipant ) throws ConverterException {

        if ( mParticipant == null ) {
            throw new IllegalArgumentException( "You must give a non null model Participant." );
        }

        checkDependencies();

        psidev.psi.mi.xml253.jaxb.ParticipantType jParticipant = new psidev.psi.mi.xml253.jaxb.ParticipantType();

        // Initialise the JAXB object reading the model

        // 1. set attributes

        jParticipant.setId( mParticipant.getId() );

        // 2. set sub elements
        // interactor / interaction
        if ( mParticipant.hasInteractor() || mParticipant.hasInteractorRef() ) {
            // compact form: export ref
            if (PsimiXmlForm.FORM_COMPACT == ConverterContext.getInstance().getConverterConfig().getXmlForm()) {
                if ( mParticipant.hasInteractorRef() )
                    jParticipant.setInteractorRef( mParticipant.getInteractorRef().getRef() );
                else
                    jParticipant.setInteractorRef( mParticipant.getInteractor().getId() );
            }
            // not compact form : export the full interactor
            else {
                jParticipant.setInteractor( interactorConverter.toJaxb( mParticipant.getInteractor() ) );
            }
        } else if ( mParticipant.hasInteraction() ) {
            jParticipant.setInteractionRef( mParticipant.getInteraction().getId() );
        } else {
            throw new ConverterException( "Neither an interactor or an interaction was present in participant " + mParticipant.getId(), mParticipant );
        }

        // BiologigicalRoles
        if ( mParticipant.hasBiologicalRole() ) {
            jParticipant.setBiologicalRole( cvTypeConverter.toJaxb( mParticipant.getBiologicalRole() ) );
        }

        // Names
        if ( mParticipant.hasNames() ) {
            jParticipant.setNames( namesConverter.toJaxb( mParticipant.getNames() ) );
        }

        // Xrefs
        if ( mParticipant.hasXref() ) {
            jParticipant.setXref( xrefConverter.toJaxb( mParticipant.getXref() ) );
        }

        // ConfidenceList
        if ( mParticipant.hasConfidences() ) {
            if ( jParticipant.getConfidenceList() == null ) {
                jParticipant.setConfidenceList( new ConfidenceListType() );
            }

            for ( psidev.psi.mi.xml.model.Confidence mConfidence : mParticipant.getConfidenceList() ) {
                jParticipant.getConfidenceList().getConfidences().add( confidenceConverter.toJaxb( mConfidence ) );
            }
        }

        // ExperimentalInteractor
        if ( mParticipant.hasExperimentalInteractors() ) {
            if ( jParticipant.getExperimentalInteractorList() == null ) {
                jParticipant.setExperimentalInteractorList( new ParticipantType.ExperimentalInteractorList() );
            }

            for ( ExperimentalInteractor mExperimentalInteractor : mParticipant.getExperimentalInteractors() ) {
                jParticipant.getExperimentalInteractorList().getExperimentalInteractors().add(
                        experimentalInteractorConverter.toJaxb( mExperimentalInteractor ) );
            }
        }

        // ExperimentalPreparations
        if ( mParticipant.hasExperimentalPreparations() ) {
            if ( jParticipant.getExperimentalPreparationList() == null ) {
                jParticipant.setExperimentalPreparationList( new ParticipantType.ExperimentalPreparationList() );
            }

            for ( ExperimentalPreparation mExperimentalPreparation : mParticipant.getParticipantExperimentalPreparations() ) {
                jParticipant.getExperimentalPreparationList().getExperimentalPreparations().add(
                        experimentalPreparationConverter.toJaxb( mExperimentalPreparation ) );
            }
        }

        // ExperimentalRoles
        if ( mParticipant.hasExperimentalRoles() ) {
            if ( jParticipant.getExperimentalRoleList() == null ) {
                jParticipant.setExperimentalRoleList( new ParticipantType.ExperimentalRoleList() );
            }

            for ( ExperimentalRole mExperimentalRole : mParticipant.getExperimentalRoles() ) {
                jParticipant.getExperimentalRoleList().getExperimentalRoles().add( experimentalRoleConverter.toJaxb( mExperimentalRole ) );
            }
        }

        // Features
        if ( mParticipant.hasFeatures() ) {
            if ( jParticipant.getFeatureList() == null ) {
                jParticipant.setFeatureList( new ParticipantType.FeatureList() );
            }

            for ( Feature mFeature : mParticipant.getFeatures() ) {
                jParticipant.getFeatureList().getFeatures().add( featureConverter.toJaxb( mFeature ) );
            }
        }

        // HostOrganisms
        if ( !mParticipant.getHostOrganisms().isEmpty() ) {
            if ( jParticipant.getHostOrganismList() == null ) {
                jParticipant.setHostOrganismList( new ParticipantType.HostOrganismList() );
            }

            for ( HostOrganism mHostOrganism : mParticipant.getHostOrganisms() ) {
                jParticipant.getHostOrganismList().getHostOrganisms().add( hostOrganismConverter.toJaxb( mHostOrganism ) );
            }
        }

        // Parameters
        if ( mParticipant.hasParameters() ) {
            if ( jParticipant.getParameterList() == null ) {
                jParticipant.setParameterList( new ParticipantType.ParameterList() );
            }

            for ( Parameter mParameter : mParticipant.getParametersList() ) {
                jParticipant.getParameterList().getParameters().add( parameterConverter.toJaxb( mParameter ) );
            }
        }

        // ParticipantIdentificationMethod
        if ( mParticipant.hasParticipantIdentificationMethods() ) {
            boolean hasNewParticipantIdentificationMethods = true;
            if (mParticipant.getInteractionEvidence() != null){
                hasNewParticipantIdentificationMethods = false;
                Collection<ExperimentDescription> experiments = mParticipant.getInteractionEvidence().getExperiments();
                Collection<ExperimentRef> experimentRefs = mParticipant.getInteractionEvidence().getExperimentRefs();
                if (!experiments.isEmpty()){
                    for (ExperimentDescription desc : experiments){

                        if (desc.getParticipantIdentificationMethod() != null && !mParticipant.getParticipantIdentificationMethods().contains(desc.getParticipantIdentificationMethod())){
                            hasNewParticipantIdentificationMethods = true;
                            break;
                        }
                    }
                }
                else if (!experimentRefs.isEmpty()){
                    PsiDAO<ExperimentDescription> experimentDAO = factory.getExperimentDAO();

                    for (ExperimentRef ref : experimentRefs){
                        ExperimentDescription desc = experimentDAO.retreive( ref.getRef() );
                        if (desc != null){
                            if (desc.getParticipantIdentificationMethod() != null && !mParticipant.getParticipantIdentificationMethods().contains(desc.getParticipantIdentificationMethod())){
                                hasNewParticipantIdentificationMethods = true;
                                break;
                            }
                        }
                    }
                }
                else {
                    hasNewParticipantIdentificationMethods = true;
                }
            }
            if (hasNewParticipantIdentificationMethods){
                if ( jParticipant.getParticipantIdentificationMethodList() == null ) {
                    jParticipant.setParticipantIdentificationMethodList( new ParticipantType.ParticipantIdentificationMethodList() );
                }

                for ( ParticipantIdentificationMethod mParticipantIdentificationMethod :
                        mParticipant.getParticipantIdentificationMethods() ) {

                    jParticipant.getParticipantIdentificationMethodList().getParticipantIdentificationMethods()
                            .add( participantIdentificationMethodConverter.toJaxb( mParticipantIdentificationMethod ) );
                }
            }
        }

        // Attributes
        if ( mParticipant.hasAttributes() ) {
            if ( jParticipant.getAttributeList() == null ) {
                jParticipant.setAttributeList( new AttributeListType() );
            }

            for ( Attribute mAttribute :
                    mParticipant.getAttributes() ) {

                jParticipant.getAttributeList().getAttributes()
                        .add( attributeConverter.toJaxb( mAttribute ) );
            }
        }

        return jParticipant;
    }
}