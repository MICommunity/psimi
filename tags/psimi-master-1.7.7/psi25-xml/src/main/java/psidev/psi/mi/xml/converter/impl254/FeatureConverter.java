/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl254;

import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.PsiDAO;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml254.jaxb.AttributeList;
import psidev.psi.mi.xml254.jaxb.BaseLocation;
import psidev.psi.mi.xml254.jaxb.ExperimentRefList;

/**
 * Converter to and from JAXB of the class Feature.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Feature
 * @see psidev.psi.mi.xml254.jaxb.Feature
 * @since <pre>07-Jun-2006</pre>
 */
public class FeatureConverter {

    ///////////////////////
    // Instance variables

    private CvTypeConverter cvTypeConverter;
    private NamesConverter namesConverter;
    private XrefConverter xrefConverter;
    private OrganismConverter organismConverter;
    private RangeConverter rangeConverter;
    private AttributeConverter attributeConverter;

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    ///////////////////////////////
    // Constructor

    public FeatureConverter() {
        cvTypeConverter = new CvTypeConverter();
        namesConverter = new NamesConverter();
        xrefConverter = new XrefConverter();
        organismConverter = new OrganismConverter();
        rangeConverter = new RangeConverter();
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
     * @throws ConverterException
     */
    private void checkDependencies() throws ConverterException {
        if ( factory == null ) {
            throw new ConverterException( "Please set a DAO factory in order to resolve experiment's id." );
        }
    }

    /////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Feature fromJaxb( psidev.psi.mi.xml254.jaxb.Feature jFeature ) throws ConverterException {

        if ( jFeature == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Feature." );
        }

        checkDependencies();

        psidev.psi.mi.xml.model.Feature mFeature = new psidev.psi.mi.xml.model.Feature();

        // Initialise the model reading the Jaxb object

        // 1. set attributes
        mFeature.setId( jFeature.getId() );

        // 2. set encapsulated objects

        // names
        if ( jFeature.getNames() != null ) {
            mFeature.setNames( namesConverter.fromJaxb( jFeature.getNames() ) );
        }

        // xrefs
        if ( jFeature.getXref() != null ) {
            mFeature.setXref( xrefConverter.fromJaxb( jFeature.getXref() ) );
        }

        // feature type
        if ( jFeature.getFeatureType() != null ) {
            mFeature.setFeatureType( cvTypeConverter.fromJaxb( jFeature.getFeatureType(), FeatureType.class ) );
        }

        // feature detection method
        if ( jFeature.getFeatureDetectionMethod() != null ) {
            mFeature.setFeatureDetectionMethod( cvTypeConverter.fromJaxb( jFeature.getFeatureDetectionMethod(), FeatureDetectionMethod.class ) );
        }

        // attributes
        if ( jFeature.getAttributeList() != null ) {
            for ( psidev.psi.mi.xml254.jaxb.Attribute jAttribute : jFeature.getAttributeList().getAttributes() ) {
                mFeature.getAttributes().add( attributeConverter.fromJaxb( jAttribute ) );
            }
        }

        // ranges
        if ( jFeature.getFeatureRangeList() != null ) {
            for ( BaseLocation jBaseLocation : jFeature.getFeatureRangeList().getFeatureRanges() ) {
                mFeature.getRanges().add( rangeConverter.fromJaxb( jBaseLocation ) );
            }
        }

        // experiments
        if ( jFeature.getExperimentRefList() != null ) {
            PsiDAO<ExperimentDescription> experimentDAO = factory.getExperimentDAO();
            for ( Integer experimentId : jFeature.getExperimentRefList().getExperimentReves() ) {
                ExperimentDescription experimentDescription = experimentDAO.retreive( experimentId );
                if ( experimentDescription == null ) {
                    mFeature.getExperimentRefs().add( new ExperimentRef( experimentId ) );
                } else {
                    mFeature.getExperiments().add( experimentDescription );
                }
            }
        }

        // store feature using DAO
        PsiDAO<Feature> featureDAO = factory.getFeatureDAO();
        featureDAO.store( mFeature );

        return mFeature;
    }

    public psidev.psi.mi.xml254.jaxb.Feature toJaxb( psidev.psi.mi.xml.model.Feature mFeature ) throws ConverterException {

        if ( mFeature == null ) {
            throw new IllegalArgumentException( "You must give a non null model Feature." );
        }

        checkDependencies();

        psidev.psi.mi.xml254.jaxb.Feature jFeature = new psidev.psi.mi.xml254.jaxb.Feature();

        //Initialise the JAXB object reading the model

        // 1. set attributes
        jFeature.setId( mFeature.getId() );

        // 2. set encapsulated objects

        // names
        if ( mFeature.hasNames() ) {
            jFeature.setNames( namesConverter.toJaxb( mFeature.getNames() ) );
        }

        // xref
        if ( mFeature.hasXref() ) {
            jFeature.setXref( xrefConverter.toJaxb( mFeature.getXref() ) );
        }

        // feature type
        if ( mFeature.hasFeatureType() ) {
            jFeature.setFeatureType( cvTypeConverter.toJaxb( mFeature.getFeatureType() ) );
        }

        // feature detection method
        if ( mFeature.hasFeatureDetectionMethod() ) {
            jFeature.setFeatureDetectionMethod( cvTypeConverter.toJaxb( mFeature.getFeatureDetectionMethod() ) );
        }

        //ranges
        if ( mFeature.getRanges() != null ) {
            if ( jFeature.getFeatureRangeList() == null ) {
                jFeature.setFeatureRangeList( new psidev.psi.mi.xml254.jaxb.Feature.FeatureRangeList() );
            }

            for ( Range mRange : mFeature.getRanges() ) {
                jFeature.getFeatureRangeList().getFeatureRanges().add( rangeConverter.toJaxb( mRange ) );
            }
        }

        // experiments
        if ( mFeature.hasExperiments() ) {
            if ( jFeature.getExperimentRefList() == null ) {
                jFeature.setExperimentRefList( new ExperimentRefList() );
            }
            for ( ExperimentDescription mExperiment : mFeature.getExperiments() ) {
                jFeature.getExperimentRefList().getExperimentReves().add( mExperiment.getId() );
            }
        } else if ( mFeature.hasExperimentRefs() ) {
            if ( jFeature.getExperimentRefList() == null ) {
                jFeature.setExperimentRefList( new ExperimentRefList() );
            }
            for ( ExperimentRef mExperiment : mFeature.getExperimentRefs() ) {
                jFeature.getExperimentRefList().getExperimentReves().add( mExperiment.getRef() );
            }
        }

        //attributes
        if ( mFeature.hasAttributes() ) {
            if ( jFeature.getAttributeList() == null ) {
                jFeature.setAttributeList( new AttributeList() );
            }

            for ( Attribute mAttribute : mFeature.getAttributes() ) {
                jFeature.getAttributeList().getAttributes().add( attributeConverter.toJaxb( mAttribute ) );
            }
        }

        return jFeature;
    }
}