/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import psidev.psi.mi.xml.PsimiXmlForm;
import psidev.psi.mi.xml.converter.ConverterContext;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml253.jaxb.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Converts an entry between Jaxb and the model.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07-Jun-2006</pre>
 */
public class EntryConverter {

    /////////////////////////////
    // Instance variables

    private SourceConverter sourceConverter;
    private ExperimentDescriptionConverter experimentDescriptionConverter;
    private AttributeConverter attributeConverter;
    private AvailabilityConverter availabilityConverter;
    private InteractionConverter interactionConverter;
    private InteractorConverter interactorConverter;

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    ///////////////////////////
    // constructor

    public EntryConverter() {
        sourceConverter = new SourceConverter();
        experimentDescriptionConverter = new ExperimentDescriptionConverter();
        attributeConverter = new AttributeConverter();
        availabilityConverter = new AvailabilityConverter();
        interactionConverter = new InteractionConverter();
        interactorConverter = new InteractorConverter();
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
        experimentDescriptionConverter.setDAOFactory( factory );
        interactionConverter.setDAOFactory( factory );
        interactorConverter.setDAOFactory( factory );
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

    //////////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Entry fromJaxb( psidev.psi.mi.xml253.jaxb.EntryType jEntry ) throws ConverterException {

        if ( jEntry == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB entry." );
        }

        checkDependencies();

        psidev.psi.mi.xml.model.Entry mEntry = new psidev.psi.mi.xml.model.Entry();

        // Initialise the model reading the Jaxb object

        // 1. set encapsulated objects

        // source
        mEntry.setSource( sourceConverter.fromJaxb( jEntry.getSource() ) );

        // availabilities
        if ( jEntry.getAvailabilityList() != null ) {
            for ( AvailabilityType jAvailability : jEntry.getAvailabilityList().getAvailabilities() ) {
                mEntry.getAvailabilities().add( availabilityConverter.fromJaxb( jAvailability ) );
            }
        }

        // experiments
        if ( jEntry.getExperimentList() != null ) {
            for ( ExperimentType jExperiment : jEntry.getExperimentList().getExperimentDescriptions() ) {
                ExperimentDescription exp = experimentDescriptionConverter.fromJaxb( jExperiment );
                if (exp.getPublication() != null){
                    exp.getPublication().setSource(mEntry.getSource());
                }
                mEntry.getExperiments().add( exp);
            }
        }

        // interactors
        if ( jEntry.getInteractorList() != null ) {
            for ( InteractorElementType jInteractor : jEntry.getInteractorList().getInteractors() ) {
                mEntry.getInteractors().add( interactorConverter.fromJaxb( jInteractor ) );
            }
        }

        // interactions
        if ( jEntry.getInteractionList() != null ) {
            for ( EntryType.InteractionList.Interaction jInteraction :
                    jEntry.getInteractionList().getInteractions() ) {
                mEntry.getInteractions().add( interactionConverter.fromJaxb( jInteraction ) );
            }
        }

        // attributes
        if ( jEntry.getAttributeList() != null ) {
            for ( AttributeListType.Attribute jAttribute : jEntry.getAttributeList().getAttributes() ) {
                mEntry.getAttributes().add( attributeConverter.fromJaxb( jAttribute ) );
            }
        }

        if (mEntry.getExperiments().isEmpty()) {
            for (psidev.psi.mi.xml.model.Interaction mInteraction : mEntry.getInteractions()) {
                for (ExperimentDescription exp : mInteraction.getExperiments()){
                    if (exp.getPublication() != null){
                        exp.getPublication().setSource(mEntry.getSource());
                    }
                    mEntry.getExperiments().add(exp);
                }
            }
        }
        if (mEntry.getInteractors().isEmpty()) {
            for (psidev.psi.mi.xml.model.Interaction mInteraction : mEntry.getInteractions()) {
                for (psidev.psi.mi.xml.model.Participant mParticipant : mInteraction.getParticipants()) {
                    mEntry.getInteractors().add(mParticipant.getInteractor());
                }
            }
        }

        // flush the caches
        factory.reset();

        return mEntry;
    }

    public psidev.psi.mi.xml253.jaxb.EntryType toJaxb( psidev.psi.mi.xml.model.Entry mEntry ) throws ConverterException {

        if ( mEntry == null ) {
            throw new IllegalArgumentException( "You must give a non null model entry." );
        }

        checkDependencies();

        psidev.psi.mi.xml253.jaxb.EntryType jEntry = new psidev.psi.mi.xml253.jaxb.EntryType();

        // Initialise the JAXB object reading the model

        // 1. set encapsulated objects

        // source
        if ( mEntry.hasSource() ) {
            jEntry.setSource( sourceConverter.toJaxb( mEntry.getSource() ) );
        }

        // availabilities
        if ( mEntry.hasAvailabilities() ) {
            if ( jEntry.getAvailabilityList() == null ) {
                jEntry.setAvailabilityList( new EntryType.AvailabilityList() );
            }

            for ( Availability mAvailability : mEntry.getAvailabilities() ) {
                jEntry.getAvailabilityList().getAvailabilities().add( availabilityConverter.toJaxb( mAvailability ) );
            }
        }

        // experiments
        if ( mEntry.hasExperiments() ) {
            if ( jEntry.getExperimentList() == null ) {
                jEntry.setExperimentList( new EntryType.ExperimentList() );
            }

            List<Integer> experimentIds = new ArrayList<Integer>();

            for ( psidev.psi.mi.xml.model.ExperimentDescription mExperiment : mEntry.getExperiments() ) {
                if (!experimentIds.contains(mExperiment.getId())) {
                    jEntry.getExperimentList().getExperimentDescriptions().add( experimentDescriptionConverter.toJaxb( mExperiment ) );
                    experimentIds.add(mExperiment.getId());
                }
            }
        }

        // interactors
        if ( mEntry.hasInteractors() ) {
            if ( jEntry.getInteractorList() == null ) {
                jEntry.setInteractorList( new EntryType.InteractorList() );
            }

            List<Integer> interactorIds = new ArrayList<Integer>();

            for ( psidev.psi.mi.xml.model.Interactor mInteractor : mEntry.getInteractors() ) {
                if (!interactorIds.contains(mInteractor.getId())) {
                    jEntry.getInteractorList().getInteractors().add( interactorConverter.toJaxb( mInteractor ) );
                    interactorIds.add(mInteractor.getId());
                }
            }
        }

        // interactions
        for ( Interaction mInteraction : mEntry.getInteractions() ) {
            if ( jEntry.getInteractionList() == null ) {
                jEntry.setInteractionList( new EntryType.InteractionList() );
            }
            jEntry.getInteractionList().getInteractions().add( interactionConverter.toJaxb( mInteraction ) );
        }

        // attributes
        if ( mEntry.hasAttributes() ) {
            if ( jEntry.getAttributeList() == null ) {
                jEntry.setAttributeList( new AttributeListType() );
            }

            for ( Attribute mAttribute : mEntry.getAttributes() ) {
                jEntry.getAttributeList().getAttributes().add( attributeConverter.toJaxb( mAttribute ) );
            }
        }

        if (PsimiXmlForm.FORM_EXPANDED == ConverterContext.getInstance().getConverterConfig().getXmlForm()) {
            jEntry.setExperimentList(null);
            jEntry.setInteractorList(null);
        } else {
            if (jEntry.getExperimentList() == null) {
                jEntry.setExperimentList(new EntryType.ExperimentList());

                List<Integer> experimentIds = new ArrayList<Integer>();

                for (psidev.psi.mi.xml.model.Interaction jInteraction : mEntry.getInteractions()) {
                    for (psidev.psi.mi.xml.model.ExperimentDescription jExperiment : jInteraction.getExperiments()) {
                        if (!experimentIds.contains(jExperiment.getId())) {
                            jEntry.getExperimentList().getExperimentDescriptions().add(experimentDescriptionConverter.toJaxb(jExperiment));
                            experimentIds.add(jExperiment.getId());
                        }
                    }
                }
            }
            if (jEntry.getInteractorList() == null) {
                jEntry.setInteractorList(new EntryType.InteractorList());

                List<Integer> participantIds = new ArrayList<Integer>();

                for (psidev.psi.mi.xml.model.Interaction mInteraction : mEntry.getInteractions()) {
                    for (psidev.psi.mi.xml.model.Participant mParticipant : mInteraction.getParticipants()) {
                        if (!participantIds.contains(mParticipant.getId())) {
                            jEntry.getInteractorList().getInteractors().add(interactorConverter.toJaxb(mParticipant.getInteractor()));
                            participantIds.add(mParticipant.getId());
                        }
                    }
                }
            }
        }

        return jEntry;
    }
}