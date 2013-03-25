/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml253.jaxb.ExperimentRefListType;
import psidev.psi.mi.xml253.jaxb.ParticipantType;

import java.util.List;

/**
 * Converter to and from JAXB of the class Organism.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Organism
 * @see psidev.psi.mi.xml253.jaxb.BioSourceType
 * @since <pre>07-Jun-2006</pre>
 */
public class HostOrganismConverter {

    //////////////////////////
    // Instance variable

    private OpenCvTypeConverter openCvTypeConverter;
    private NamesConverter namesConverter;
    private List<PsiXml25ParserListener> listeners;

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    /////////////////////////
    // Constructor

    public HostOrganismConverter() {
        openCvTypeConverter = new OpenCvTypeConverter();
        namesConverter = new NamesConverter();
    }

    public void setListeners(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
        this.openCvTypeConverter.setListeners(listeners);
        this.namesConverter.setListeners(listeners);
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

    /////////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.HostOrganism fromJaxb( psidev.psi.mi.xml253.jaxb.ParticipantType.HostOrganismList.HostOrganism jOrganism ) throws ConverterException {

        if ( jOrganism == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Organism." );
        }

        psidev.psi.mi.xml.model.HostOrganism mOrganism = new psidev.psi.mi.xml.model.HostOrganism();
        Locator locator = jOrganism.sourceLocation();
        mOrganism.setSourceLocator(new FileSourceLocator(locator.getLineNumber(), locator.getColumnNumber()));

        // Initialise the model reading the Jaxb object

        // 1. set attributes
        mOrganism.setNcbiTaxId( jOrganism.getNcbiTaxId() );

        // 2. set encapsulated objects

        // names
        if ( jOrganism.getNames() != null ) {
            mOrganism.setNames( namesConverter.fromJaxb( jOrganism.getNames() ) );
        }

        // cell type
        if ( jOrganism.getCellType() != null ) {
            mOrganism.setCellType( openCvTypeConverter.fromJaxb( jOrganism.getCellType(), CellType.class ) );
        }

        // compartment
        if ( jOrganism.getCompartment() != null ) {
            mOrganism.setCompartment( openCvTypeConverter.fromJaxb( jOrganism.getCompartment(), Compartment.class ) );
        }

        // tissue
        if ( jOrganism.getTissue() != null ) {
            mOrganism.setTissue( openCvTypeConverter.fromJaxb( jOrganism.getTissue(), Tissue.class ) );
        }

        // experiments
        if ( jOrganism.getExperimentRefList() != null ) {
            for ( Integer jExperimentId : jOrganism.getExperimentRefList().getExperimentReves() ) {
                // resolve reference
                ExperimentDescription mExperimentDescription = factory.getExperimentDAO().retreive( jExperimentId );
                if ( mExperimentDescription == null ) {
                    mOrganism.getExperimentRefs().add( new ExperimentRef( jExperimentId ) );
                } else {
                    mOrganism.getExperiments().add( mExperimentDescription );
                }
            }
        }

        return mOrganism;
    }

    public psidev.psi.mi.xml253.jaxb.ParticipantType.HostOrganismList.HostOrganism
    toJaxb( psidev.psi.mi.xml.model.HostOrganism mOrganism ) throws ConverterException {

        if ( mOrganism == null ) {
            throw new IllegalArgumentException( "You must give a non null Organism from the model." );
        }

        checkDependencies();

        // Instanciate recipient
        psidev.psi.mi.xml253.jaxb.ParticipantType.HostOrganismList.HostOrganism jOrganism =
                new ParticipantType.HostOrganismList.HostOrganism();

        // Initialise the JAXB object reading the model

        // 1. set attributes
        jOrganism.setNcbiTaxId( mOrganism.getNcbiTaxId() );

        // 2. set encapsulated objects

        // names
        if ( mOrganism.hasNames() ) {
            jOrganism.setNames( namesConverter.toJaxb( mOrganism.getNames() ) );
        }

        // cell type
        if ( mOrganism.hasCellType() ) {
            jOrganism.setCellType( openCvTypeConverter.toJaxb( mOrganism.getCellType() ) );
        }

        // compartment
        if ( mOrganism.hasCompartment() ) {
            jOrganism.setCompartment( openCvTypeConverter.toJaxb( mOrganism.getCompartment() ) );
        }

        // tissue
        if ( mOrganism.hasTissue() ) {
            jOrganism.setTissue( openCvTypeConverter.toJaxb( mOrganism.getTissue() ) );
        }

        // experiments
        if ( mOrganism.hasExperiments() ) {
            if ( jOrganism.getExperimentRefList() == null ) {
                jOrganism.setExperimentRefList( new ExperimentRefListType() );
            }
            for ( ExperimentDescription mExperiemnt : mOrganism.getExperiments() ) {
                jOrganism.getExperimentRefList().getExperimentReves().add( mExperiemnt.getId() );
            }
        } else if ( mOrganism.hasExperimentRefs() ) {
            if ( jOrganism.getExperimentRefList() == null ) {
                jOrganism.setExperimentRefList( new ExperimentRefListType() );
            }
            for ( ExperimentRef mExperiemnt : mOrganism.getExperimentRefs() ) {
                jOrganism.getExperimentRefList().getExperimentReves().add( mExperiemnt.getRef() );
            }
        }

        return jOrganism;
    }
}