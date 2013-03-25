/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl254;


import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.RangeStatus;

import java.util.List;

/**
 * Converter to and from JAXB of the class Range.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Range
 * @see psidev.psi.mi.xml254.jaxb.BaseLocation
 * @since <pre>07-Jun-2006</pre>
 */
public class RangeConverter {

    /////////////////////////
    // Instance variable

    private CvTypeConverter cvTypeConverter;
    private PositionConverter positionConverter;
    private IntervalConverter intervalConverter;

    private List<PsiXml25ParserListener> listeners;

    ////////////////////////
    // Constructor

    public RangeConverter() {
        cvTypeConverter = new CvTypeConverter();
        positionConverter = new PositionConverter();
        intervalConverter = new IntervalConverter();
    }

    public void setListeners(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
        this.cvTypeConverter.setListeners(listeners);
        this.positionConverter.setListeners(listeners);
        this.intervalConverter.setListeners(listeners);
    }

    /////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Range fromJaxb( psidev.psi.mi.xml254.jaxb.BaseLocation jRange ) throws ConverterException {

        if ( jRange == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Range." );
        }

        psidev.psi.mi.xml.model.Range mRange = new psidev.psi.mi.xml.model.Range();
        Locator locator = jRange.sourceLocation();
        mRange.setSourceLocator(new FileSourceLocator(locator.getLineNumber(), locator.getColumnNumber()));

        //Initialise the model reading the Jaxb object

        // 1. set attributes
        if ( jRange.isIsLink() != null ) {
            mRange.setIsLink( jRange.isIsLink() );
        } else {
            // default is false
            mRange.setIsLink( false );
        }

        // 2. set encapsulated objects

        // begin
        if ( jRange.getBegin() != null ) {
            mRange.setBegin( positionConverter.fromJaxb( jRange.getBegin() ) );
        }

        // begin interval
        if ( jRange.getBeginInterval() != null ) {
            mRange.setBeginInterval( intervalConverter.fromJaxb( jRange.getBeginInterval() ) );
        }

        // start status
        if ( jRange.getStartStatus() != null ) {
            mRange.setStartStatus( cvTypeConverter.fromJaxb( jRange.getStartStatus(), RangeStatus.class ) );
        }

        // end
        if ( jRange.getEnd() != null ) {
            mRange.setEnd( positionConverter.fromJaxb( jRange.getEnd() ) );
        }

        // end interval
        if ( jRange.getEndInterval() != null ) {
            mRange.setEndInterval( intervalConverter.fromJaxb( jRange.getEndInterval() ) );
        }

        // end status
        if ( jRange.getEndStatus() != null ) {
            mRange.setEndStatus( cvTypeConverter.fromJaxb( jRange.getEndStatus(), RangeStatus.class ) );
        }

        return mRange;
    }

    public psidev.psi.mi.xml254.jaxb.BaseLocation toJaxb( psidev.psi.mi.xml.model.Range mRange ) {

        if ( mRange == null ) {
            throw new IllegalArgumentException( "You must give a non null model Range." );
        }

        psidev.psi.mi.xml254.jaxb.BaseLocation jRange = new psidev.psi.mi.xml254.jaxb.BaseLocation();

        //Initialise the JAXB object reading the model

        // 1. set attributes
        jRange.setIsLink( mRange.isLink() );

        // 2. set encapsulated objects

        // begin
        if ( mRange.hasBegin() ) {
            jRange.setBegin( positionConverter.toJaxb( mRange.getBegin() ) );
        }

        // begin interval
        if ( mRange.hasBeginInterval() ) {
            jRange.setBeginInterval( intervalConverter.toJaxb( mRange.getBeginInterval() ) );
        }

        // begin status
        if ( mRange.getStartStatus() != null ) {
            jRange.setStartStatus( cvTypeConverter.toJaxb( mRange.getStartStatus() ) );
        }

        // end
        if ( mRange.hasEnd() ) {
            jRange.setEnd( positionConverter.toJaxb( mRange.getEndPosition() ) );
        }

        // end interval
        if ( mRange.hasEndInterval() ) {
            jRange.setEndInterval( intervalConverter.toJaxb( mRange.getEndInterval() ) );
        }

        // end status
        if ( mRange.getEndStatus() != null ) {
            jRange.setEndStatus( cvTypeConverter.toJaxb( mRange.getEndStatus() ) );
        }

        return jRange;
    }
}