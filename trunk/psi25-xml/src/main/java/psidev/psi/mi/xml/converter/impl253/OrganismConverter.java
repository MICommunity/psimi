/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.model.CellType;
import psidev.psi.mi.xml.model.Compartment;
import psidev.psi.mi.xml.model.Tissue;

import java.lang.reflect.Constructor;

/**
 * Converter to and from JAXB of the class Organism.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Organism
 * @see psidev.psi.mi.xml253.jaxb.BioSourceType
 * @since <pre>07-Jun-2006</pre>
 */
public class OrganismConverter {

    private static final Class[] NO_PARAMETER_TYPES = new Class[]{};
    private static final Object[] NO_ARGS = new Object[]{};

    //////////////////////////
    // Instance variable

    private OpenCvTypeConverter openCvTypeConverter;
    private NamesConverter namesConverter;

    /////////////////////////
    // Constructor

    public OrganismConverter() {
        openCvTypeConverter = new OpenCvTypeConverter();
        namesConverter = new NamesConverter();
    }


    public psidev.psi.mi.xml.model.Organism fromJaxb( psidev.psi.mi.xml253.jaxb.BioSourceType jOrganism ) throws ConverterException {

        if ( jOrganism == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Organism." );
        }

        psidev.psi.mi.xml.model.Organism mOrganism = new psidev.psi.mi.xml.model.Organism();

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

        return mOrganism;
    }

    /**
     * Clazz can be in: - psidev.psi.mi.xml253.jaxb.ExperimentType.HostOrganismList.HostOrganism -
     * psidev.psi.mi.xml253.jaxb.InteractorElementType.Organism
     *
     * @param mOrganism
     * @param clazz
     * @return
     * @throws ConverterException
     */
    public <T extends psidev.psi.mi.xml253.jaxb.BioSourceType> T toJaxb( psidev.psi.mi.xml.model.Organism mOrganism,
                                                                      Class<T> clazz )
            throws ConverterException {

        if ( mOrganism == null ) {
            throw new IllegalArgumentException( "You must give a non null Organism from the model." );
        }

        if ( clazz == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB implementation class of Organism." );
        }

        // Instanciate recipient
        T jOrganism = null;

//        if ( ! clazz.isAssignableFrom( psidev.psi.mi.xml253.jaxb.BioSourceType.class ) ) {
//            throw new IllegalArgumentException( "The given class must be a sub type of " +
//                                                "psidev.psi.mi.xml253.jaxb.BioSourceType: " + clazz.getName() );
//        }

        try {
            // request constructor
            Constructor<T> constructor = clazz.getConstructor( NO_PARAMETER_TYPES ); // no-arg constructor

            // instanciate object
            jOrganism = constructor.newInstance( NO_ARGS );

        } catch ( Exception e ) {
            throw new ConverterException( "An exception was thrown while instanciating an model.BioSourceType via reflection. " +
                                          "Nested Exception attached", e );
        }

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

        return jOrganism;
    }
}