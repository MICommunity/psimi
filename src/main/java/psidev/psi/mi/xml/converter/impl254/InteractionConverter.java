/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl254;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Locator;
import psidev.psi.mi.xml.PsimiXmlForm;
import psidev.psi.mi.xml.converter.ConverterContext;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.PsiDAO;
import psidev.psi.mi.xml.events.MultipleExperimentsPerInteractionEvent;
import psidev.psi.mi.xml.events.MultipleInteractionTypesEvent;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml.model.Confidence;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml.model.Feature;
import psidev.psi.mi.xml.model.InferredInteraction;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Parameter;
import psidev.psi.mi.xml.model.Participant;
import psidev.psi.mi.xml254.jaxb.*;
import psidev.psi.mi.xml254.jaxb.CvType;

import java.util.HashSet;
import java.util.List;

/**
 * Converter to and from JAXB of the class Interaction.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Interaction
 * @see psidev.psi.mi.xml254.jaxb.Interaction
 * @since <pre>07-Jun-2006</pre>
 */
public class InteractionConverter {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( InteractionConverter.class );

    ////////////////////////
    // Instance variables

    private CvTypeConverter cvTypeConverter;
    private NamesConverter namesConverter;
    private XrefConverter xrefConverter;
    private ConfidenceConverter confidenceConverter;
    private ParticipantConverter participantConverter;
    private AvailabilityConverter availabilityConverter;
    private ExperimentDescriptionConverter experimentDescriptionConverter;
    private AttributeConverter attributeConverter;
    private InteractionParameterConverter parameterConverter;
    private InferredInteractionConverter inferredInteractionConverter;

    private List<PsiXml25ParserListener> listeners;

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    //////////////////////////////
    // Constructor

    public InteractionConverter() {
        cvTypeConverter = new CvTypeConverter();
        namesConverter = new NamesConverter();
        xrefConverter = new XrefConverter();
        confidenceConverter = new ConfidenceConverter();
        participantConverter = new ParticipantConverter();
        availabilityConverter = new AvailabilityConverter();
        experimentDescriptionConverter = new ExperimentDescriptionConverter();
        attributeConverter = new AttributeConverter();
        parameterConverter = new InteractionParameterConverter();
        inferredInteractionConverter = new InferredInteractionConverter();
    }

    ///////////////////////////////
    // DAO factory stategy


    public void setListeners(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
        this.participantConverter.setListeners(listeners);
        this.cvTypeConverter.setListeners(listeners);
        this.namesConverter.setListeners(listeners);
        this.xrefConverter.setListeners(listeners);
        this.confidenceConverter.setListeners(listeners);
        this.availabilityConverter.setListeners(listeners);
        this.experimentDescriptionConverter.setListeners(listeners);
        this.attributeConverter.setListeners(listeners);
        this.parameterConverter.setListeners(listeners);
        this.inferredInteractionConverter.setListeners(listeners);
    }

    /**
     * Set the DAO Factory that holds required DAOs for resolving ids.
     *
     * @param factory the DAO factory
     */
    public void setDAOFactory( DAOFactory factory ) {
        this.factory = factory;

        // initialise the factory in sub-converters
        inferredInteractionConverter.setDAOFactory( factory );
        parameterConverter.setDAOFactory( factory );
        confidenceConverter.setDAOFactory( factory );
        inferredInteractionConverter.setDAOFactory( factory );
        participantConverter.setDAOFactory( factory );
        experimentDescriptionConverter.setDAOFactory( factory );
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
    // Convertion methods

    public psidev.psi.mi.xml.model.Interaction fromJaxb( psidev.psi.mi.xml254.jaxb.Interaction jInteraction ) throws ConverterException {

        if ( jInteraction == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Interaction." );
        }

        checkDependencies();

        psidev.psi.mi.xml.model.Interaction mInteraction = new psidev.psi.mi.xml.model.Interaction();
        Locator locator = jInteraction.sourceLocation();
        mInteraction.setSourceLocator(new PsiXmlFileLocator(locator.getLineNumber(), locator.getColumnNumber(), jInteraction.getId()));

        // Initialise the model reading the Jaxb object

        // 1. set attributes

        mInteraction.setId( jInteraction.getId() );

        mInteraction.setImexId( jInteraction.getImexId() );

        // 2. set encapsulated objects

        // names
        if ( jInteraction.getNames() != null ) {
            mInteraction.setNames( namesConverter.fromJaxb( jInteraction.getNames() ) );
        }

        // xref
        if ( jInteraction.getXref() != null ) {
            mInteraction.setXref( xrefConverter.fromJaxb( jInteraction.getXref() ) );
        }

        // availability
        if ( jInteraction.getAvailability() != null ) {
            mInteraction.setAvailability( availabilityConverter.fromJaxb( jInteraction.getAvailability() ) );
        }

        // experiments
        if ( jInteraction.getExperimentList() != null ) {
            for ( Object o : jInteraction.getExperimentList().getExperimentRevesAndExperimentDescriptions() ) {

                log.debug( "Reading object of type: " + o.getClass() );

                ExperimentDescription mExperiment = null;
                ExperimentRef experimentRef = null;

                if ( o instanceof psidev.psi.mi.xml254.jaxb.ExperimentDescription) {

                    log.debug( "Found an experiment" );

                    mExperiment = experimentDescriptionConverter.fromJaxb( ( psidev.psi.mi.xml254.jaxb.ExperimentDescription ) o );

                    // store the experiment using the DAO
                    PsiDAO<ExperimentDescription> experimentDAO = factory.getExperimentDAO();
                    experimentDAO.store( mExperiment );

                } else if ( o instanceof Integer ) {

                    Integer ref = ( Integer ) o;
                    experimentRef = new ExperimentRef( ref );

                    // now try to resolve the reference
                    PsiDAO<ExperimentDescription> experimentDAO = factory.getExperimentDAO();
                    mExperiment = experimentDAO.retreive( ( Integer ) o );

                } else {
                    throw new IllegalStateException( "Expected object type: {psidev.psi.mi.xml254.jaxb.ExperimentDescription, Integer}, found: " +
                                                     o.getClass().getName() );
                }

                if ( mExperiment == null ) {
                    // the DAO doesn't know about it, then keep the reference for potential later resolution
                    mInteraction.getExperimentRefs().add( experimentRef );
                } else {
                    mInteraction.getExperiments().add( mExperiment );
                }
            }

            // we have more than one experiment
            if (listeners != null && !listeners.isEmpty() && jInteraction.getExperimentList().getExperimentRevesAndExperimentDescriptions().size() > 1){
                MultipleExperimentsPerInteractionEvent evt = new MultipleExperimentsPerInteractionEvent(mInteraction, new HashSet<ExperimentDescription>(mInteraction.getExperiments()), "Interaction " +mInteraction.getId() + " contains "+jInteraction.getExperimentList().getExperimentRevesAndExperimentDescriptions().size()+" experiments.");
                evt.setSourceLocator(mInteraction.getSourceLocator());
                for (PsiXml25ParserListener l : listeners){
                    l.fireOnMultipleExperimentsPerInteractionEvent(evt);
                }
            }
        }

        // participants
        if ( jInteraction.getParticipantList() != null ) {
            for ( psidev.psi.mi.xml254.jaxb.Participant jParticipant : jInteraction.getParticipantList().getParticipants() ) {
                Participant participant = participantConverter.fromJaxb( jParticipant );
                mInteraction.getParticipants().add( participant );

                if (participant.getParticipantIdentificationMethods().isEmpty()){
                    if (!mInteraction.getExperiments().isEmpty()){
                        for (ExperimentDescription desc : mInteraction.getExperiments()){
                            participant.getParticipantIdentificationMethods().add(desc.getParticipantIdentificationMethod());

                            if (desc.getFeatureDetectionMethod() != null){
                                for (Feature f : participant.getFeatures()){
                                    if (f.getFeatureDetectionMethod() == null){
                                        f.setFeatureDetectionMethod(desc.getFeatureDetectionMethod());
                                    }
                                }
                            }
                        }
                    }
                    else if (!mInteraction.getExperimentRefs().isEmpty()){
                        PsiDAO<ExperimentDescription> experimentDAO = experimentDescriptionConverter.getFactory().getExperimentDAO();

                        for (ExperimentRef ref : mInteraction.getExperimentRefs()){
                            ExperimentDescription desc = experimentDAO.retreive( ref.getRef() );
                            if (desc != null){
                                participant.getParticipantIdentificationMethods().add(desc.getParticipantIdentificationMethod());

                                if (desc.getFeatureDetectionMethod() != null){
                                    for (Feature f : participant.getFeatures()){
                                        if (f.getFeatureDetectionMethod() == null){
                                            f.setFeatureDetectionMethod(desc.getFeatureDetectionMethod());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    if (!mInteraction.getExperiments().isEmpty()){
                        for (ExperimentDescription desc : mInteraction.getExperiments()){
                            if (desc.getFeatureDetectionMethod() != null){
                                for (Feature f : participant.getFeatures()){
                                    if (f.getFeatureDetectionMethod() == null){
                                        f.setFeatureDetectionMethod(desc.getFeatureDetectionMethod());
                                    }
                                }
                            }
                        }
                    }
                    else if (!mInteraction.getExperimentRefs().isEmpty()){
                        PsiDAO<ExperimentDescription> experimentDAO = experimentDescriptionConverter.getFactory().getExperimentDAO();

                        for (ExperimentRef ref : mInteraction.getExperimentRefs()){
                            ExperimentDescription desc = experimentDAO.retreive( ref.getRef() );

                            if (desc != null && desc.getFeatureDetectionMethod() != null){
                                for (Feature f : participant.getFeatures()){
                                    if (f.getFeatureDetectionMethod() == null){
                                        f.setFeatureDetectionMethod(desc.getFeatureDetectionMethod());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // inferred interactions
        if ( jInteraction.getInferredInteractionList() != null ) {
            for ( psidev.psi.mi.xml254.jaxb.InferredInteraction jInferredInteraction :
                    jInteraction.getInferredInteractionList().getInferredInteractions() ) {
                mInteraction.getInferredInteractions().add( inferredInteractionConverter.fromJaxb( jInferredInteraction ) );
            }
        }

        // interaction types
        for ( CvType jIntereractionType : jInteraction.getInteractionTypes() ) {
            mInteraction.getInteractionTypes().add( cvTypeConverter.fromJaxb( jIntereractionType, InteractionType.class ) );
        }
        // we have more than one interaction types
        if (listeners != null && !listeners.isEmpty() && jInteraction.getInteractionTypes().size() > 1){
            MultipleInteractionTypesEvent evt = new MultipleInteractionTypesEvent(mInteraction, new HashSet<InteractionType>(mInteraction.getInteractionTypes()), "Interaction " +mInteraction.getId() + " contains "+jInteraction.getInteractionTypes().size()+" interaction types.");
            evt.setSourceLocator(mInteraction.getSourceLocator());
            for (PsiXml25ParserListener l : listeners){
                l.fireOnMultipleInteractionTypesEvent(evt);
            }
        }

        // modelled
        if ( jInteraction.isModelled() != null ) {
            mInteraction.setModelled( jInteraction.isModelled() );
        } else {
            // default is false.
            mInteraction.setModelled( false );
        }

        // intramolecular
        if ( jInteraction.isIntraMolecular() != null ) {
            mInteraction.setIntraMolecular( jInteraction.isIntraMolecular() );
        } else {
            // default is false.
            mInteraction.setIntraMolecular( false );
        }

        // negative
        if ( jInteraction.isNegative() != null ) {
            mInteraction.setNegative( jInteraction.isNegative() );
        } else {
            // default is false.
            mInteraction.setNegative( false );
        }

        // confidences
        if ( jInteraction.getConfidenceList() != null ) {
            for ( psidev.psi.mi.xml254.jaxb.Confidence jConfidence : jInteraction.getConfidenceList().getConfidences() ) {
                mInteraction.getConfidencesList().add( confidenceConverter.fromJaxb( jConfidence ) );
            }
        }

        // parameters
        if ( jInteraction.getParameterList() != null ) {
            for ( psidev.psi.mi.xml254.jaxb.Parameter jParameter : jInteraction.getParameterList().getParameters() ) {
                mInteraction.getParametersList().add( parameterConverter.fromJaxb( jParameter ) );
            }
        }

        // attributes
        if ( jInteraction.getAttributeList() != null ) {
            for ( psidev.psi.mi.xml254.jaxb.Attribute jAttribute : jInteraction.getAttributeList().getAttributes() ) {
                mInteraction.getAttributes().add( attributeConverter.fromJaxb( jAttribute ) );
            }
        }

        // store the interaction via DAO
        PsiDAO<Interaction> interactionDAO = factory.getInteractionDAO();
        interactionDAO.store( mInteraction );

        return mInteraction;
    }

    public psidev.psi.mi.xml254.jaxb.Interaction toJaxb( psidev.psi.mi.xml.model.Interaction mInteraction ) throws ConverterException {

        if ( mInteraction == null ) {
            throw new IllegalArgumentException( "You must give a non null model Interaction." );
        }

        checkDependencies();

        psidev.psi.mi.xml254.jaxb.Interaction jInteraction =
                new psidev.psi.mi.xml254.jaxb.Interaction();

        // Initialise the JAXB object reading the model

        // 1. set attributes
        jInteraction.setId( mInteraction.getId() );
        jInteraction.setImexId( mInteraction.getImexId() );

        // 2. set encapsulated objects

        // names
        if ( mInteraction.hasNames() ) {
            jInteraction.setNames( namesConverter.toJaxb( mInteraction.getNames() ) );
        }

        // xref
        if ( mInteraction.hasXref() ) {
            jInteraction.setXref( xrefConverter.toJaxb( mInteraction.getXref() ) );
        }

        // availibility
        // TODO do we export an availability or a reference.
        if ( mInteraction.hasAvailability() ) {
            jInteraction.setAvailability( availabilityConverter.toJaxb( mInteraction.getInteractionAvailability() ) );
        }

        // experiments
        // compact form: export the ref
        if ( PsimiXmlForm.FORM_COMPACT == ConverterContext.getInstance().getConverterConfig().getXmlForm() &&
				mInteraction.hasExperimentRefs() ) {
            if ( jInteraction.getExperimentList() == null ) {
                jInteraction.setExperimentList( new ExperimentList() );
            }
            final List ids = jInteraction.getExperimentList().getExperimentRevesAndExperimentDescriptions();
            for ( ExperimentRef mExperiment : mInteraction.getExperimentRefs() ) {
                if( ! ids.contains( mExperiment.getRef() ) ) {
                    ids.add( mExperiment.getRef() );
                }
            }
        } 
        // not compact form: expand the full experiment
        else  {
            if ( jInteraction.getExperimentList() == null ) {
                jInteraction.setExperimentList( new ExperimentList() );
            }
            final List ids = jInteraction.getExperimentList().getExperimentRevesAndExperimentDescriptions();
            for ( ExperimentDescription mExperiment : mInteraction.getExperiments() ) {
                final psidev.psi.mi.xml254.jaxb.ExperimentDescription exp = experimentDescriptionConverter.toJaxb( mExperiment );

                if (PsimiXmlForm.FORM_COMPACT == ConverterContext.getInstance().getConverterConfig().getXmlForm()) {
                    if( ! ids.contains( mExperiment.getId() ) ) {
                        ids.add(mExperiment.getId());
                    }
                } else {
                    if( ! ids.contains( exp ) ) {
                        ids.add( exp );
                    }
                }
            }
        }

        // participants
        if ( mInteraction.getParticipants() != null ) {
            if ( jInteraction.getParticipantList() == null ) {
                jInteraction.setParticipantList( new ParticipantList() );
            }

            for ( Participant mParticipant : mInteraction.getParticipants() ) {
                jInteraction.getParticipantList().getParticipants().add( participantConverter.toJaxb( mParticipant ) );
            }
        }

        // infered interactions
        if ( mInteraction.hasInferredInteractions() ) {
            if ( jInteraction.getInferredInteractionList() == null ) {
                jInteraction.setInferredInteractionList( new psidev.psi.mi.xml254.jaxb.InferredInteractionList() );
            }

            for ( InferredInteraction inferredInteraction : mInteraction.getInferredInteractions() ) {
                jInteraction.getInferredInteractionList().getInferredInteractions().add(
                        inferredInteractionConverter.toJaxb( inferredInteraction ) );
            }
        }

        // interaction type
        if ( mInteraction.hasInteractionTypes() ) {
            for ( InteractionType mInteractionType : mInteraction.getInteractionTypes() ) {
                jInteraction.getInteractionTypes().add( cvTypeConverter.toJaxb( mInteractionType ) );
            }
        }

        // parameters
        if ( mInteraction.hasParameters() ) {
            if ( jInteraction.getParameterList() == null ) {
                jInteraction.setParameterList( new psidev.psi.mi.xml254.jaxb.ParameterList() );
            }

            for ( Parameter mParameter : mInteraction.getParametersList() ) {
                jInteraction.getParameterList().getParameters().add( parameterConverter.toJaxb( mParameter ) );
            }
        }

        // intra molecular
        jInteraction.setIntraMolecular( mInteraction.isIntraMolecular() );

        // modelled
        jInteraction.setModelled( mInteraction.isModelled() );

        // negative
        jInteraction.setNegative( mInteraction.isNegative() );

        // confidence
        if ( mInteraction.hasConfidences() ) {
            if ( jInteraction.getConfidenceList() == null ) {
                jInteraction.setConfidenceList( new ConfidenceList() );
            }

            for ( Confidence mConfidence : mInteraction.getConfidencesList() ) {
                jInteraction.getConfidenceList().getConfidences().add( confidenceConverter.toJaxb( mConfidence ) );
            }
        }

        // attributes
        if ( mInteraction.hasAttributes() ) {
            if ( jInteraction.getAttributeList() == null ) {
                jInteraction.setAttributeList( new AttributeList() );
            }

            for ( Attribute mAttribute : mInteraction.getAttributes() ) {
                jInteraction.getAttributeList().getAttributes().add( attributeConverter.toJaxb( mAttribute ) );
            }
        }

        return jInteraction;
    }
}