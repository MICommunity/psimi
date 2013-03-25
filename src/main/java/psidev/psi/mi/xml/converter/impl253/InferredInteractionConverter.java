/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.PsiDAO;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.ExperimentRef;
import psidev.psi.mi.xml.model.InferredInteractionParticipant;
import psidev.psi.mi.xml253.jaxb.ExperimentRefListType;
import psidev.psi.mi.xml253.jaxb.InteractionElementType;

import java.util.List;

/**
 * Converter to and from JAXB of the class InferredInteraction.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.InferredInteraction
 * @see psidev.psi.mi.xml253.jaxb.InteractionElementType.InferredInteractionList.InferredInteraction
 * @since <pre>07-Jun-2006</pre>
 */
public class InferredInteractionConverter {

    ///////////////////////
    // Instance variable
    private InferredInteractionParticipantConverter participantConverter;
    private List<PsiXml25ParserListener> listeners;

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    //////////////////////////
    // Constructor

    public InferredInteractionConverter() {
        participantConverter = new InferredInteractionParticipantConverter();
    }

    public void setListeners(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
        this.participantConverter.setListeners(listeners);
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

        // set factory of sub-converters
        participantConverter.setDAOFactory( factory );
    }

    /**
     * Checks that the dependencies of that object are fulfilled.
     *
     * @throws psidev.psi.mi.xml.converter.ConverterException
     */
    private void checkDependencies() throws ConverterException {
        if ( factory == null ) {
            throw new ConverterException( "Please set a DAO factory in order to resolve experiment's id." );
        }
    }

    ////////////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.InferredInteraction fromJaxb( psidev.psi.mi.xml253.jaxb.InteractionElementType.InferredInteractionList.InferredInteraction jInferredInteraction ) throws ConverterException {

        if ( jInferredInteraction == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB InferredInteraction." );
        }

        checkDependencies();

        psidev.psi.mi.xml.model.InferredInteraction mInferredInteraction = new psidev.psi.mi.xml.model.InferredInteraction();

        // Initialise the JAXB object reading the model

        // set encapsulated objects

        // participants
        for ( InteractionElementType.InferredInteractionList.InferredInteraction.Participant jParticipant :
                jInferredInteraction.getParticipants() ) {
            mInferredInteraction.getParticipant().add( participantConverter.fromJaxb( jParticipant ) );
        }

        // experiments
        if ( jInferredInteraction.getExperimentRefList() != null ) {
            PsiDAO<ExperimentDescription> experimentDAO = factory.getExperimentDAO();
            for ( Integer experimnetId : jInferredInteraction.getExperimentRefList().getExperimentReves() ) {
                ExperimentDescription experimentDescription = experimentDAO.retreive( experimnetId );
                if ( experimentDescription != null ) {
                    mInferredInteraction.getExperiments().add( experimentDescription );
                } else {
                    // not found via DAO, create a reference
                    mInferredInteraction.getExperimentRefs().add( new ExperimentRef( experimnetId ) );
                }
            }
        }

        return mInferredInteraction;
    }

    public psidev.psi.mi.xml253.jaxb.InteractionElementType.InferredInteractionList.InferredInteraction
    toJaxb( psidev.psi.mi.xml.model.InferredInteraction mInferredInteraction ) throws ConverterException {

        if ( mInferredInteraction == null ) {
            throw new IllegalArgumentException( "You must give a non null model InferredInteraction." );
        }

        checkDependencies();

        psidev.psi.mi.xml253.jaxb.InteractionElementType.InferredInteractionList.InferredInteraction jInferredInteraction =
                new psidev.psi.mi.xml253.jaxb.InteractionElementType.InferredInteractionList.InferredInteraction();

        // Initialise the JAXB object reading the model

        // 1. set encapsulated objects

        // participants
        for ( InferredInteractionParticipant mParticipant : mInferredInteraction.getParticipant() ) {
            jInferredInteraction.getParticipants().add( participantConverter.toJaxb( mParticipant ) );
        }

        // experiments

        if ( mInferredInteraction.hasExperiments() ) {
            if ( jInferredInteraction.getExperimentRefList() == null ) {
                jInferredInteraction.setExperimentRefList( new ExperimentRefListType() );
            }
            for ( ExperimentDescription mExperiment : mInferredInteraction.getExperiments() ) {
                Integer experimentId = mExperiment.getId();
                jInferredInteraction.getExperimentRefList().getExperimentReves().add( experimentId );
            }
        } else if ( mInferredInteraction.hasExperimentRefs() ) {
            if ( jInferredInteraction.getExperimentRefList() == null ) {
                jInferredInteraction.setExperimentRefList( new ExperimentRefListType() );
            }
            for ( ExperimentRef mExperiment : mInferredInteraction.getExperimentRefs() ) {
                Integer experimentId = mExperiment.getRef();
                jInferredInteraction.getExperimentRefList().getExperimentReves().add( experimentId );
            }
        }

        return jInferredInteraction;
    }
}