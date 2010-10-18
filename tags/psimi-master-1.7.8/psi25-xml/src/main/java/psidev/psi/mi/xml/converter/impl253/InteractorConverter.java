/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.PsiDAO;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.mi.xml.model.InteractorType;
import psidev.psi.mi.xml253.jaxb.AttributeListType;
import psidev.psi.mi.xml253.jaxb.InteractorElementType;

/**
 * Converter to and from JAXB of the class Interactor.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Interactor
 * @see psidev.psi.mi.xml253.jaxb.InteractorElementType
 * @since <pre>07-Jun-2006</pre>
 */
public class InteractorConverter {

    ///////////////////////
    // Instance variables

    private CvTypeConverter cvTypeConverter;
    private NamesConverter namesConverter;
    private XrefConverter xrefConverter;
    private OrganismConverter organismConverter;
    private AttributeConverter attributeConverter;

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    ///////////////////////////////
    // Constructor

    public InteractorConverter() {
        cvTypeConverter = new CvTypeConverter();
        namesConverter = new NamesConverter();
        xrefConverter = new XrefConverter();
        organismConverter = new OrganismConverter();
        attributeConverter = new AttributeConverter();
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
     * @throws ConverterException should an error occur during conversion.
     */
    private void checkDependencies() throws ConverterException {
        if ( factory == null ) {
            throw new ConverterException( "Please set a DAO factory in order to resolve experiment's id." );
        }
    }

    //////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Interactor fromJaxb( psidev.psi.mi.xml253.jaxb.InteractorElementType jInteractor ) throws ConverterException {

        if ( jInteractor == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Interactor." );
        }

        checkDependencies();

        psidev.psi.mi.xml.model.Interactor mInteractor = new psidev.psi.mi.xml.model.Interactor();

        // Initialise the model reading the Jaxb object

        // 1. set attributes
        mInteractor.setId( jInteractor.getId() );

        // 2. set encapsulated objects
        mInteractor.setInteractorType( cvTypeConverter.fromJaxb( jInteractor.getInteractorType(),
                                                                 InteractorType.class ) );

        // Names
        if ( jInteractor.getNames() != null ) {
            mInteractor.setNames( namesConverter.fromJaxb( jInteractor.getNames() ) );
        }

        // Xrefs
        if ( jInteractor.getXref() != null ) {
            mInteractor.setXref( xrefConverter.fromJaxb( jInteractor.getXref() ) );
        }

        // Organism
        if ( jInteractor.getOrganism() != null ) {
            mInteractor.setOrganism( organismConverter.fromJaxb( jInteractor.getOrganism() ) );
        }

        // sequence
        String sequence = jInteractor.getSequence();
        if ( sequence != null ) {
            sequence = sequence.trim();
        }
        mInteractor.setSequence( sequence );

        // attribute list
        if ( jInteractor.getAttributeList() != null ) {
            for ( AttributeListType.Attribute attribute : jInteractor.getAttributeList().getAttributes() ) {
                mInteractor.getAttributes().add( attributeConverter.fromJaxb( attribute ) );
            }
        }

        // store using DAO
        PsiDAO<Interactor> interactorDAO = factory.getInteractorDAO();
        interactorDAO.store( mInteractor );

        return mInteractor;
    }

    public psidev.psi.mi.xml253.jaxb.InteractorElementType toJaxb( psidev.psi.mi.xml.model.Interactor mInteractor ) throws ConverterException {

        if ( mInteractor == null ) {
            throw new IllegalArgumentException( "You must give a non null model Interactor." );
        }

        checkDependencies();

        psidev.psi.mi.xml253.jaxb.InteractorElementType jInteractor = new psidev.psi.mi.xml253.jaxb.InteractorElementType();

        // Initialise the JAXB object reading the model

        // 1. set attributes
        jInteractor.setId( mInteractor.getId() );

        // 2. set encapsulated objects
        jInteractor.setInteractorType( cvTypeConverter.toJaxb( mInteractor.getInteractorType() ) );

        // Names
        if ( mInteractor.getNames() != null ) {
            jInteractor.setNames( namesConverter.toJaxb( mInteractor.getNames() ) );
        }

        // Xrefs
        if ( mInteractor.hasXref() ) {
            jInteractor.setXref( xrefConverter.toJaxb( mInteractor.getXref() ) );
        }

        // Organism
        if ( mInteractor.hasOrganism() ) {
            jInteractor.setOrganism( organismConverter.toJaxb( mInteractor.getOrganism(), InteractorElementType.Organism.class ) );
        }

        // sequence
        String sequence = mInteractor.getSequence();
        if ( sequence != null ) {
            sequence = sequence.trim();
        }
        jInteractor.setSequence( sequence );

        // attributes
        if ( mInteractor.hasAttributes() ) {
            jInteractor.setAttributeList( new AttributeListType() );
            for ( Attribute attribute : mInteractor.getAttributes() ) {
                jInteractor.getAttributeList().getAttributes().add( attributeConverter.toJaxb( attribute ) );
            }
        }

        return jInteractor;
    }
}