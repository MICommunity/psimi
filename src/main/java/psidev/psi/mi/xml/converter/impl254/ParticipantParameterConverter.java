/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl254;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.ExperimentRef;

import java.math.BigDecimal;

/**
 * Converter to and from JAXB of the class Parameter.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Parameter
 * @see psidev.psi.mi.xml254.jaxb.Parameter
 * @since <pre>07-Jun-2006</pre>
 */
public class ParticipantParameterConverter {

    //////////////////////////
    // Instance variable

    private ExperimentDescriptionConverter experimentDescriptionConverter;

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    ////////////////////////
    // Constructor

    public ParticipantParameterConverter() {
        experimentDescriptionConverter = new ExperimentDescriptionConverter();
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

    /////////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Parameter fromJaxb( psidev.psi.mi.xml254.jaxb.Parameter jParameter ) throws ConverterException {

        if ( jParameter == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Parameter." );
        }

        checkDependencies();

        psidev.psi.mi.xml.model.Parameter mParameter = new psidev.psi.mi.xml.model.Parameter();

        // Initialise the model reading the Jaxb object

        // 1. set attributes

        mParameter.setBase( jParameter.getBase() );
        mParameter.setExponent( jParameter.getExponent() );
        mParameter.setFactor( jParameter.getFactor().doubleValue() );
        mParameter.setTerm( jParameter.getTerm() );
        mParameter.setTermAc( jParameter.getTermAc() );
        if ( jParameter.getUncertainty() != null ) {
            mParameter.setUncertainty( jParameter.getUncertainty().doubleValue() );
        }
        mParameter.setUnit( jParameter.getUnit() );
        mParameter.setUnitAc( jParameter.getUnitAc() );

        // 2. set encapsulated objects

        // resolve the experiment id
        int ref = jParameter.getExperimentRef();
        ExperimentDescription experimentDescription = factory.getExperimentDAO().retreive( ref );
        if ( experimentDescription == null ) {
            mParameter.setExperimentRef( new ExperimentRef( ref ) );
        } else {
            mParameter.setExperiment( experimentDescription );
        }

        Locator locator = jParameter.sourceLocation();
        mParameter.setSourceLocator(new FileSourceLocator(locator.getLineNumber(), locator.getColumnNumber()));

        return mParameter;
    }

    public psidev.psi.mi.xml254.jaxb.Parameter toJaxb( psidev.psi.mi.xml.model.Parameter mParameter ) throws ConverterException {

        if ( mParameter == null ) {
            throw new IllegalArgumentException( "You must give a non null model Parameter." );
        }

        checkDependencies();

        psidev.psi.mi.xml254.jaxb.Parameter jParameter =
                new psidev.psi.mi.xml254.jaxb.Parameter();

        // Initialise the JAXB object reading the model

        // 1. set attributes

        jParameter.setBase( ( short ) mParameter.getBase() );
        jParameter.setExponent( ( short ) mParameter.getExponent() );
        jParameter.setFactor( BigDecimal.valueOf( mParameter.getFactor() ) );
        jParameter.setTerm( mParameter.getTerm() );
        jParameter.setTermAc( mParameter.getTermAc() );
        jParameter.setUncertainty( new BigDecimal( mParameter.getUncertaintyAsDouble() ) );
        jParameter.setUnit( mParameter.getUnitName() );
        jParameter.setUnitAc( mParameter.getUnitAc() );

        // 2. set encapsulated objects
        if ( mParameter.hasExperiment() ) {
            jParameter.setExperimentRef( mParameter.getExperiment().getId() );
        } else if ( mParameter.hasExperimentRef() ) {
            jParameter.setExperimentRef( mParameter.getExperimentRef().getRef() );
        }

        return jParameter;
    }
}