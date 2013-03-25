/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml253.jaxb.AttributeListType;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Converter to and from JAXB of the class Source.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Source
 * @since <pre>07-Jun-2006</pre>
 */
public class SourceConverter {

    ///////////////////////
    // Instance variable

    private BibrefConverter bibrefConverter;
    private NamesConverter namesConverter;
    private XrefConverter xrefConverter;
    private AttributeConverter attributeConverter;
    private List<PsiXml25ParserListener> listeners;

    ///////////////////
    // Constructor

    public SourceConverter() {
        bibrefConverter = new BibrefConverter();
        namesConverter = new NamesConverter();
        xrefConverter = new XrefConverter();
        attributeConverter = new AttributeConverter();
    }

    public void setListeners(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
        xrefConverter.setListeners(listeners);
        this.bibrefConverter.setListeners(listeners);
        this.namesConverter.setListeners(listeners);
        this.attributeConverter.setListeners(listeners);
    }

    /////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Source fromJaxb( psidev.psi.mi.xml253.jaxb.EntryType.Source jSource ) {

        if ( jSource == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Source." );
        }

        psidev.psi.mi.xml.model.Source mSource = new psidev.psi.mi.xml.model.Source();
        Locator locator = jSource.sourceLocation();
        mSource.setSourceLocator(new FileSourceLocator(locator.getLineNumber(), locator.getColumnNumber()));

        //Initialise the model reading the Jaxb object

        // 1. set attributes

        // release
        mSource.setRelease( jSource.getRelease() );

        // release date
        XMLGregorianCalendar jReleaseDate = jSource.getReleaseDate();
        if ( jReleaseDate != null ) {
            GregorianCalendar gregorianCalendar = jReleaseDate.toGregorianCalendar();
            Date mReleaseDate = gregorianCalendar.getTime();
            mSource.setReleaseDate( mReleaseDate );
        }

        // 2. set encapsulated objects

        if ( jSource.getBibref() != null ) {
            mSource.setBibref( bibrefConverter.fromJaxb( jSource.getBibref() ) );
        }

        if ( jSource.getNames() != null ) {
            mSource.setNames( namesConverter.fromJaxb( jSource.getNames() ) );
        }

        if ( jSource.getXref() != null ) {
            mSource.setXref( xrefConverter.fromJaxb( jSource.getXref() ) );
        }

        //attributes
        if ( jSource.getAttributeList() != null ) {
            for ( AttributeListType.Attribute jAttribute : jSource.getAttributeList().getAttributes() ) {
                mSource.getAttributes().add( attributeConverter.fromJaxb( jAttribute ) );
            }
        }

        return mSource;
    }

    public psidev.psi.mi.xml253.jaxb.EntryType.Source toJaxb( psidev.psi.mi.xml.model.Source mSource ) throws ConverterException {

        if ( mSource == null ) {
            throw new IllegalArgumentException( "You must give a non null model Source." );
        }

        psidev.psi.mi.xml253.jaxb.EntryType.Source jSource = new psidev.psi.mi.xml253.jaxb.EntryType.Source();

        //Initialise the JAXB object reading the model

        // 1. set attributes

        // release
        jSource.setRelease( mSource.getRelease() );

        // release date
        try {
            Date mReleaseDate = mSource.getReleaseDate();
            if ( mReleaseDate != null ) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime( mReleaseDate );
                XMLGregorianCalendar xmlCal =
                        new PsiXmlCalendar( DatatypeFactory.newInstance().newXMLGregorianCalendar( gregorianCalendar ) );
                jSource.setReleaseDate( xmlCal );
            }
        } catch ( DatatypeConfigurationException e ) {
            throw new ConverterException( "Error while converting Date to XMLGregorianCalendar. See nested Exception", e );
        }

        // 2. set encapsulated objects

        if ( mSource.hasBibref() ) {
            jSource.setBibref( bibrefConverter.toJaxb( mSource.getBibref() ) );
        }

        if ( mSource.hasNames() ) {
            jSource.setNames( namesConverter.toJaxb( mSource.getNames() ) );
        }

        if ( mSource.hasXref() ) {
            jSource.setXref( xrefConverter.toJaxb( mSource.getXref() ) );
        }

        //attributes
        if ( mSource.hasAttributes() ) {
            if ( jSource.getAttributeList() == null ) {
                jSource.setAttributeList( new AttributeListType() );
            }

            for ( Attribute mAttribute : mSource.getAttributes() ) {
                jSource.getAttributeList().getAttributes().add( attributeConverter.toJaxb( mAttribute ) );
            }
        }

        return jSource;
    }
}