/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml253.jaxb.AttributeListType;

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
public class OpenCvTypeConverter {

    /////////////////////////
    // Constants

    private static final Class[] NO_PARAMETER_TYPES = new Class[]{};
    private static final Object[] NO_ARGS = new Object[]{};

    //////////////////////
    // Instance variables

    private NamesConverter namesConverter;
    private XrefConverter xrefConverter;
    private AttributeConverter attributeConverter;

    ///////////////////////
    // Constructor

    public OpenCvTypeConverter() {
        namesConverter = new NamesConverter();
        xrefConverter = new XrefConverter();
        attributeConverter = new AttributeConverter();
    }

    /**
     * Converts an implementation of OpenCvType from JAXB into its model counterpart.
     * <p/>
     * <pre>
     * Example:
     *          ConfidenceType confidence = ...;
     *          psidev.psi.mi.xml.model.Unit mUnit = null;
     *          psidev.psi.mi.xml253.jaxb.OpenCvType jUnit = confidence.getUnitName();
     *          mUnit = fromJaxb( jUnit, psidev.psi.mi.xml.model.Unit.class );
     * </pre>
     *
     * @param jOpenCvType instance of the JAXB implementation of OpenCvType.
     * @param clazz       type of the instance in the model.
     * @return a converted instance.
     * @throws ConverterException
     */
    public <CV extends psidev.psi.mi.xml.model.OpenCvType> CV fromJaxb( psidev.psi.mi.xml253.jaxb.OpenCvType jOpenCvType,
                                                                        Class<CV> clazz )
            throws ConverterException {

        CV mOpenCvType = null;

        if ( jOpenCvType == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB OpenCvType." );
        }

        if ( clazz == null ) {
            throw new IllegalArgumentException( "You must give a non null implementation class of model.OpenCvType." );
        }

        if ( !psidev.psi.mi.xml.model.OpenCvType.class.isAssignableFrom( clazz ) ) {
            throw new IllegalArgumentException( "The given Class must be a sub type of OpenCvType: " + clazz.getName() );
        }

        try {
            // request constructor
            Constructor<CV> constructor = clazz.getConstructor( NO_PARAMETER_TYPES ); // no-arg constructor

            // instanciate object
            mOpenCvType = constructor.newInstance( NO_ARGS );

        } catch ( Exception e ) {
            throw new ConverterException( "An exception was thrown while instanciating an model.OpenCvType via reflection. " +
                                          "Nested Exception attached", e );
        }

        // Initialise the model reading the Jaxb object

        // 1. set encapsulated objects

        // names
        if ( jOpenCvType.getNames() != null ) {
            mOpenCvType.setNames( namesConverter.fromJaxb( jOpenCvType.getNames() ) );
        }

        // xref
        if ( jOpenCvType.getXref() != null ) {
            mOpenCvType.setXref( xrefConverter.fromJaxb( jOpenCvType.getXref() ) );
        }

        // attributes
        if ( jOpenCvType.getAttributeList() != null ) {
            for ( AttributeListType.Attribute jAttribute : jOpenCvType.getAttributeList().getAttributes() ) {
                mOpenCvType.getAttributes().add( attributeConverter.fromJaxb( jAttribute ) );
            }
        }

        return mOpenCvType;
    }

    /**
     * @param mOpenCvType instance of the model implementation of OpenCvType.
     * @return
     */
    public psidev.psi.mi.xml253.jaxb.OpenCvType toJaxb( psidev.psi.mi.xml.model.OpenCvType mOpenCvType ) {

        if ( mOpenCvType == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB OpenCvType." );
        }

        psidev.psi.mi.xml253.jaxb.OpenCvType jOpenCvType = new psidev.psi.mi.xml253.jaxb.OpenCvType();

        // Initialise the model reading the Jaxb object

        // 1. set encapsulated objects

        // names
        if ( mOpenCvType.hasNames() ) {
            jOpenCvType.setNames( namesConverter.toJaxb( mOpenCvType.getNames() ) );
        }

        // xref
        if ( mOpenCvType.getXref() != null ) {
            jOpenCvType.setXref( xrefConverter.toJaxb( mOpenCvType.getXref() ) );
        }

        // attributes
        if ( mOpenCvType.hasAttributes() ) {

            if ( jOpenCvType.getAttributeList() == null ) {
                jOpenCvType.setAttributeList( new AttributeListType() );
            }

            for ( Attribute mAttribute : mOpenCvType.getAttributes() ) {
                jOpenCvType.getAttributeList().getAttributes().add( attributeConverter.toJaxb( mAttribute ) );
            }
        }

        return jOpenCvType;
    }
}