/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import psidev.psi.mi.xml.converter.ConverterException;

import java.lang.reflect.Constructor;

/**
 * Converter to and from JAXB of the class OpenCvType.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.OpenCvType
 * @see psidev.psi.mi.xml253.jaxb.OpenCvType
 * @since <pre>18-Jun-2006</pre>
 */
public class CvTypeConverter {

    ///////////////////////
    // Instance variable

    private NamesConverter namesConverter;
    private XrefConverter xrefConverter;

    //////////////////////
    // Constructor

    public CvTypeConverter() {
        namesConverter = new NamesConverter();
        xrefConverter = new XrefConverter();
    }

    //////////////////////
    // Converter methods

    /**
     * Converts an implementation of CvType from JAXB into its model counterpart.
     * <p/>
     * <pre>
     * Example:
     *          InteractorType interactor = ...;
     *          psidev.psi.mi.xml.model.InteractorType mType = null;
     *          psidev.psi.mi.xml253.jaxb.CvType jType = interactor.getInteractorType();
     *          mType = fromJaxb( jType, psidev.psi.mi.xml.model.InteractorType.class );
     * </pre>
     *
     * @param jCvType instance of the JAXB implementation of OpenCvType.
     * @param clazz   type of the instance in the model.
     * @return a converted instance.
     * @throws ConverterException
     */
    public <CV extends psidev.psi.mi.xml.model.CvType> CV fromJaxb( psidev.psi.mi.xml253.jaxb.CvType jCvType,
                                                                    Class<CV> clazz )
            throws ConverterException {

        CV mOpenCvType = null;

        if ( jCvType == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB OpenCvType." );
        }

        if ( clazz == null ) {
            throw new IllegalArgumentException( "You must give a non null implementation class of model.CvType." );
        }

        try {
            // request constructor
            Constructor<CV> constructor = clazz.getConstructor( new Class[]{} ); // no-arg constructor

            // instanciate object
            mOpenCvType = constructor.newInstance( new Object[]{} );

        } catch ( Exception e ) {
            throw new ConverterException( "An exception was thrown while instanciating an model.CvType via reflection. " +
                                          "Nested Exception attached", e );
        }

        // Initialise the model reading the Jaxb object

        // 1. set encapsulated objects

        // names
        mOpenCvType.setNames( namesConverter.fromJaxb( jCvType.getNames() ) );

        // xref
        mOpenCvType.setXref( xrefConverter.fromJaxb( jCvType.getXref() ) );

        return mOpenCvType;
    }

    /**
     * @param mCvType instance of the model implementation of OpenCvType.
     * @return
     */
    public psidev.psi.mi.xml253.jaxb.CvType toJaxb( psidev.psi.mi.xml.model.CvType mCvType ) {

        if ( mCvType == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB OpenCvType." );
        }

        psidev.psi.mi.xml253.jaxb.CvType jCvType = new psidev.psi.mi.xml253.jaxb.CvType();

        // Initialise the model reading the Jaxb object

        // 1. set encapsulated objects

        // names
        jCvType.setNames( namesConverter.toJaxb( mCvType.getNames() ) );

        // xref
        jCvType.setXref( xrefConverter.toJaxb( mCvType.getXref() ) );

        return jCvType;
    }

    /**
     * @param mCvType instance of the model implementation of OpenCvType.
     * @return
     */
    public <CV extends psidev.psi.mi.xml253.jaxb.CvType> CV toJaxb( psidev.psi.mi.xml.model.CvType mCvType,
                                                                 Class<CV> clazz ) throws ConverterException {

        if ( mCvType == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB OpenCvType." );
        }

        if ( clazz == null ) {
            throw new IllegalArgumentException( "You must give a non null implementation class of jaxb.CvType." );
        }

        CV jCvType = null;

        try {
            // request constructor
            Constructor<CV> constructor = clazz.getConstructor( new Class[]{} ); // no-arg constructor

            // instanciate object
            jCvType = constructor.newInstance( new Object[]{} );

        } catch ( Exception e ) {
            throw new ConverterException( "An exception was thrown while instanciating an jaxb.CvType via reflection. " +
                                          "Nested Exception attached", e );
        }

        // Initialise the model reading the Jaxb object

        // 1. set encapsulated objects

        // names
        jCvType.setNames( namesConverter.toJaxb( mCvType.getNames() ) );

        // xref
        jCvType.setXref( xrefConverter.toJaxb( mCvType.getXref() ) );

        return jCvType;
    }
}