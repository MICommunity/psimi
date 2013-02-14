/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl254;

import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.PsiDAO;
import psidev.psi.mi.xml.model.Feature;
import psidev.psi.mi.xml.model.FeatureRef;
import psidev.psi.mi.xml.model.Participant;
import psidev.psi.mi.xml.model.ParticipantRef;

/**
 * Converter to and from JAXB of the class InferredInteractionParticipant.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.InferredInteractionParticipant
 * @see psidev.psi.mi.xml254.jaxb.Interaction.InferredInteractionList.InferredInteraction.Participant
 * @since <pre>07-Jun-2006</pre>
 */
public class InferredInteractionParticipantConverter {

    ////////////////////////
    // Instance variables

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    /////////////////////////
    // Constructor

    public InferredInteractionParticipantConverter() {
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

    ///////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.InferredInteractionParticipant
    fromJaxb( psidev.psi.mi.xml254.jaxb.InferredInteractionParticipant jInferredInteractionParticipant )
            throws ConverterException {

        if ( jInferredInteractionParticipant == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB InferredInteractionParticipant." );
        }

        checkDependencies();

        psidev.psi.mi.xml.model.InferredInteractionParticipant mInferredInteractionParticipant = new psidev.psi.mi.xml.model.InferredInteractionParticipant();

        // Initialise the model reading the Jaxb object

        // set encapsulated objects

        // participant
        if ( jInferredInteractionParticipant.getParticipantRef() != null ) {

            PsiDAO<Participant> participantDAO = factory.getParticipantDAO();
            Integer ref = jInferredInteractionParticipant.getParticipantRef();
            Participant participant = participantDAO.retreive( ref );
            if ( participant == null ) {
                // not found, create a ref instead
                mInferredInteractionParticipant.setParticipantRef( new ParticipantRef( ref ) );
            } else {
                mInferredInteractionParticipant.setParticipant( participant );
            }

        } else if ( jInferredInteractionParticipant.getParticipantFeatureRef() != null ) {

            // feature
            PsiDAO<Feature> featureDAO = factory.getFeatureDAO();
            Integer ref = jInferredInteractionParticipant.getParticipantFeatureRef();
            Feature feature = featureDAO.retreive( ref );
            if ( feature == null ) {
                // not found
                mInferredInteractionParticipant.setFeatureRef( new FeatureRef( ref ) );
            } else {
                mInferredInteractionParticipant.setFeature( feature );
            }

        } else {
            throw new ConverterException( "Neither a feature or a participant could be found in the given infered interaction participant." );
        }

        return mInferredInteractionParticipant;
    }

    public psidev.psi.mi.xml254.jaxb.InferredInteractionParticipant
    toJaxb( psidev.psi.mi.xml.model.InferredInteractionParticipant mInferredInteractionParticipant ) throws ConverterException {

        if ( mInferredInteractionParticipant == null ) {
            throw new IllegalArgumentException( "You must give a non null model InferredInteractionParticipant." );
        }

        checkDependencies();

        psidev.psi.mi.xml254.jaxb.InferredInteractionParticipant jParticipant =
                new psidev.psi.mi.xml254.jaxb.InferredInteractionParticipant();

        // Initialise the model reading the Jaxb object

        // set encapsulated objects

        if ( mInferredInteractionParticipant.hasFeature() ) {

            jParticipant.setParticipantFeatureRef( mInferredInteractionParticipant.getFeature().getId() );

        } else if ( mInferredInteractionParticipant.hasFeatureRef() ) {

            jParticipant.setParticipantFeatureRef( mInferredInteractionParticipant.getFeatureRef().getRef() );

        } else if ( mInferredInteractionParticipant.hasParticipantRef() ) {

            jParticipant.setParticipantRef( mInferredInteractionParticipant.getParticipantRef().getRef() );

        } else if ( mInferredInteractionParticipant.hasParticipant() ) {

            jParticipant.setParticipantRef( mInferredInteractionParticipant.getParticipant().getId() );

        } else {
            throw new ConverterException( "Neither a feature or a participant could be found in the given infered interaction participant." );
        }

        return jParticipant;
    }
}