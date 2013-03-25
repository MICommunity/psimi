/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl254;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;

import java.util.List;

/**
 * Converter to and from JAXB of the class Bibref.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Bibref
 * @since <pre>07-Jun-2006</pre>
 */
public class BibrefConverter {

    /////////////////////
    // instance variable

    AttributeConverter attributeConverter;
    XrefConverter xrefConverter;

    private List<PsiXml25ParserListener> listeners;

    ///////////////////////
    // Constructor

    public BibrefConverter() {
        attributeConverter = new AttributeConverter();
        xrefConverter = new XrefConverter();
    }

    public void setListeners(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
        this.xrefConverter.setListeners(listeners);
        this.attributeConverter.setListeners(listeners);
    }

    ///////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Bibref fromJaxb( psidev.psi.mi.xml254.jaxb.Bibref jBibref ) {

        if ( jBibref == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Bibref." );
        }

        psidev.psi.mi.xml.model.Bibref mBibref = new psidev.psi.mi.xml.model.Bibref();
        Locator locator = jBibref.sourceLocation();
        mBibref.setSourceLocator(new FileSourceLocator(locator.getLineNumber(), locator.getColumnNumber()));

        // 1. set attributes

        // 2. set encapsulated objects

        boolean foundAttributeList = false;
        boolean foundXref = false;
        if ( jBibref.getAttributeList() != null ) {
            foundAttributeList = true;
            for ( psidev.psi.mi.xml254.jaxb.Attribute jAttribute :
                    jBibref.getAttributeList().getAttributes() ) {
                mBibref.getAttributes().add( attributeConverter.fromJaxb( jAttribute ) );
            }
        }

        if ( jBibref.getXref() != null ) {
            foundXref = true;
            mBibref.setXref( xrefConverter.fromJaxb( jBibref.getXref() ) );
        }

        if( foundAttributeList && foundXref ) {
            throw new IllegalArgumentException( "When defining a <bibref>, you must give as a child tag either an <xref> or an <attributeList>." );
        }

        return mBibref;
    }

    public psidev.psi.mi.xml254.jaxb.Bibref toJaxb( psidev.psi.mi.xml.model.Bibref mBibref ) {

        if ( mBibref == null ) {
            throw new IllegalArgumentException( "You must give a non null model Bibref." );
        }

        psidev.psi.mi.xml254.jaxb.Bibref jBibref = new psidev.psi.mi.xml254.jaxb.Bibref();

        // 1. set cross reference

        if (mBibref.getXref() != null){
            jBibref.setXref( xrefConverter.toJaxb( mBibref.getXref() ) );
        }

        // 2. set encapsulated objects

        if (!mBibref.getAttributes().isEmpty()){
            psidev.psi.mi.xml254.jaxb.AttributeList attributeList = jBibref.getAttributeList();
            if (attributeList == null){
               attributeList =  new psidev.psi.mi.xml254.jaxb.AttributeList();
                jBibref.setAttributeList(attributeList);
            }
            
            for ( psidev.psi.mi.xml.model.Attribute mAttribute : mBibref.getAttributes() ) {
                attributeList.getAttributes().add( attributeConverter.toJaxb( mAttribute ) );
            }
        }


        return jBibref;
    }
}