/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl253;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileParsingErrorType;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.xml.events.InvalidXmlEvent;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;

import java.util.List;

/**
 * Converter to and from JAXB of the class Attribute.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Attribute
 * @see psidev.psi.mi.xml253.jaxb.AttributeListType.Attribute
 * @since <pre>07-Jun-2006</pre>
 */
public class AttributeConverter {

    private List<PsiXml25ParserListener> listeners;

    public AttributeConverter() {
    }

    public void setListeners(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
    }

    ///////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Attribute fromJaxb( psidev.psi.mi.xml253.jaxb.AttributeListType.Attribute jAttribute ) {

        if ( jAttribute == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB attribute." );
        }

        psidev.psi.mi.xml.model.Attribute mAttribute = new psidev.psi.mi.xml.model.Attribute();
        Locator locator = jAttribute.sourceLocation();
        if (locator != null){
            mAttribute.setSourceLocator(new FileSourceLocator(locator.getLineNumber(), locator.getColumnNumber()));
        }
        // Initialise the model reading the Jaxb object

        // 1. set attributes

        mAttribute.setName( jAttribute.getName() );
        if (jAttribute.getName()== null || jAttribute.getName().length() == 0) {
            InvalidXmlEvent evt = new InvalidXmlEvent(FileParsingErrorType.missing_annotation_topic, "It is not a valid attribute because the attribute name is empty or null");
            evt.setSourceLocator(new FileSourceLocator(jAttribute.sourceLocation() != null ? jAttribute.sourceLocation().getLineNumber() : 0, jAttribute.sourceLocation() != null ? jAttribute.sourceLocation().getColumnNumber() : 0));

            for (PsiXml25ParserListener l : listeners){
                l.fireOnInvalidXmlSyntax(evt);
            }
        }
        mAttribute.setNameAc( jAttribute.getNameAc() );
        mAttribute.setValue( jAttribute.getValue() );

        return mAttribute;
    }

    public psidev.psi.mi.xml253.jaxb.AttributeListType.Attribute toJaxb( psidev.psi.mi.xml.model.Attribute mAttribute ) {

        if ( mAttribute == null ) {
            throw new IllegalArgumentException( "You must give a non null model attribute." );
        }

        psidev.psi.mi.xml253.jaxb.AttributeListType.Attribute jAttribute = new psidev.psi.mi.xml253.jaxb.AttributeListType.Attribute();

        // Initialise the JAXB object reading the model

        // 1. set attributes

        jAttribute.setName( mAttribute.getName() );
        jAttribute.setNameAc( mAttribute.getNameAc() );
        jAttribute.setValue( mAttribute.getValue() );

        return jAttribute;
    }
}