/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.xml.PsimiXmlForm;
import psidev.psi.mi.xml.converter.ConverterContext;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.PsiDAO;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml253.jaxb.*;
import psidev.psi.mi.xml253.jaxb.CvType;

import java.util.List;

/**
 * Converter to and from JAXB of the class Interaction.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Interaction
 * @see psidev.psi.mi.xml253.jaxb.InteractionElementType
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

    public psidev.psi.mi.xml.model.Interaction fromJaxb( psidev.psi.mi.xml253.jaxb.EntryType.InteractionList.Interaction jInteraction ) throws ConverterException {

        if ( jInteraction == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Interaction." );
        }

        checkDependencies();

        psidev.psi.mi.xml.model.Interaction mInteraction = new psidev.psi.mi.xml.model.Interaction();

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

                if ( o instanceof ExperimentType ) {

                    log.debug( "Found an experiment" );

                    mExperiment = experimentDescriptionConverter.fromJaxb( ( ExperimentType ) o );

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
                    throw new IllegalStateException( "Expected object type: {ExperimentType, Integer}, found: " +
                                                     o.getClass().getName() );
                }

                if ( mExperiment == null ) {
                    // the DAO doesn't know about it, then keep the reference for potential later resolution
                    mInteraction.getExperimentRefs().add( experimentRef );
                } else {
                    mInteraction.getExperiments().add( mExperiment );
                }
            }
        }

        // participants
        if ( jInteraction.getParticipantList() != null ) {
            for ( ParticipantType jParticipant : jInteraction.getParticipantList().getParticipants() ) {
                Participant participant = participantConverter.fromJaxb( jParticipant );
                mInteraction.getParticipants().add( participant );

                if (participant.getParticipantIdentificationMethods().isEmpty()){
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
                else {
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
            }
        }

        // inferred interactions
        if ( jInteraction.getInferredInteractionList() != null ) {
            for ( InteractionElementType.InferredInteractionList.InferredInteraction jInferredInteraction :
                    jInteraction.getInferredInteractionList().getInferredInteractions() ) {
                mInteraction.getInferredInteractions().add( inferredInteractionConverter.fromJaxb( jInferredInteraction ) );
            }
        }

        // interaction types
        for ( CvType jIntereractionType : jInteraction.getInteractionTypes() ) {
            mInteraction.getInteractionTypes().add( cvTypeConverter.fromJaxb( jIntereractionType, InteractionType.class ) );
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
            for ( ConfidenceListType.Confidence jConfidence : jInteraction.getConfidenceList().getConfidences() ) {
                mInteraction.getConfidencesList().add( confidenceConverter.fromJaxb( jConfidence ) );
            }
        }

        // parameters
        if ( jInteraction.getParameterList() != null ) {
            for ( InteractionElementType.ParameterList.Parameter jParameter : jInteraction.getParameterList().getParameters() ) {
                mInteraction.getParametersList().add( parameterConverter.fromJaxb( jParameter ) );
            }
        }

        // attributes
        if ( jInteraction.getAttributeList() != null ) {
            for ( AttributeListType.Attribute jAttribute : jInteraction.getAttributeList().getAttributes() ) {
                mInteraction.getAttributes().add( attributeConverter.fromJaxb( jAttribute ) );
            }
        }

        // store the interaction via DAO
        PsiDAO<Interaction> interactionDAO = factory.getInteractionDAO();
        interactionDAO.store( mInteraction );

        return mInteraction;
    }

    public psidev.psi.mi.xml253.jaxb.EntryType.InteractionList.Interaction toJaxb( psidev.psi.mi.xml.model.Interaction mInteraction ) throws ConverterException {

        if ( mInteraction == null ) {
            throw new IllegalArgumentException( "You must give a non null model Interaction." );
        }

        checkDependencies();

        psidev.psi.mi.xml253.jaxb.EntryType.InteractionList.Interaction jInteraction =
                new psidev.psi.mi.xml253.jaxb.EntryType.InteractionList.Interaction();

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
        if (PsimiXmlForm.FORM_COMPACT.equals(ConverterContext.getInstance().getConverterConfig().getXmlForm())  &&
				mInteraction.hasExperimentRefs() ) {
        	if ( jInteraction.getExperimentList() == null ) {
                jInteraction.setExperimentList( new InteractionElementType.ExperimentList() );
            }

            final List ids = jInteraction.getExperimentList().getExperimentRevesAndExperimentDescriptions();
            for ( ExperimentRef mExperiment : mInteraction.getExperimentRefs() ) {
                if( ! ids.contains( mExperiment.getRef() ) ) {
                    ids.add(mExperiment.getRef());
                }
            }
        }
        // not compact form: expand the full experiment
        else {
           if ( jInteraction.getExperimentList() == null ) {
                jInteraction.setExperimentList( new InteractionElementType.ExperimentList() );
           }

           for ( ExperimentDescription mExperiment : mInteraction.getExperiments() ) {
               final ExperimentType exp = experimentDescriptionConverter.toJaxb( mExperiment );

               final List ids = jInteraction.getExperimentList().getExperimentRevesAndExperimentDescriptions();
               if (PsimiXmlForm.FORM_COMPACT == ConverterContext.getInstance().getConverterConfig().getXmlForm()) {
                   if( !ids.contains( mExperiment.getId() ) ) {
                       ids.add(mExperiment.getId());
                   }
               } else {
                    if( !ids.contains( exp ) ) {
                        ids.add( exp );
                    }
               }
           }
        }

        // participants
        if ( mInteraction.getParticipants() != null ) {
            if ( jInteraction.getParticipantList() == null ) {
                jInteraction.setParticipantList( new InteractionElementType.ParticipantList() );
            }
            for ( Participant mParticipant : mInteraction.getParticipants() ) {
                jInteraction.getParticipantList().getParticipants().add( participantConverter.toJaxb( mParticipant ) );
            }
        }

        // inferred interactions
        if ( mInteraction.hasInferredInteractions() ) {
            if ( jInteraction.getInferredInteractionList() == null ) {
                jInteraction.setInferredInteractionList( new InteractionElementType.InferredInteractionList() );
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
                jInteraction.setParameterList( new InteractionElementType.ParameterList() );
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
                jInteraction.setConfidenceList( new ConfidenceListType() );
            }

            for ( Confidence mConfidence : mInteraction.getConfidencesList() ) {
                jInteraction.getConfidenceList().getConfidences().add( confidenceConverter.toJaxb( mConfidence ) );
            }
        }

        // attributes
        if ( mInteraction.hasAttributes() ) {
            if ( jInteraction.getAttributeList() == null ) {
                jInteraction.setAttributeList( new AttributeListType() );
            }

            for ( Attribute mAttribute : mInteraction.getAttributes() ) {
                jInteraction.getAttributeList().getAttributes().add( attributeConverter.toJaxb( mAttribute ) );
            }
        }

        return jInteraction;
    }
}