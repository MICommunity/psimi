/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl254;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileParsingErrorType;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.xml.events.InvalidXmlEvent;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;

import java.util.List;

/**
 * Converter to and from JAXB of the class Alias.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Alias
 * @see psidev.psi.mi.xml254.jaxb.Alias
 * @since <pre>07-Jun-2006</pre>
 */
public class AliasConverter {

    private List<PsiXml25ParserListener> listeners;

    ////////////////////////
    // Constructor

    public AliasConverter() {
    }

    public void setListeners(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
    }

    ///////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Alias fromJaxb( psidev.psi.mi.xml254.jaxb.Alias jAlias ) {

        if ( jAlias == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB alias." );
        }

        psidev.psi.mi.xml.model.Alias mAlias = new psidev.psi.mi.xml.model.Alias();
        Locator locator = jAlias.sourceLocation();
        if (locator != null){
            mAlias.setSourceLocator(new FileSourceLocator(locator.getLineNumber(), locator.getColumnNumber()));
        }

        // Initialise the model reading the Jaxb object

        // 1. set attributes
        mAlias.setType( jAlias.getType() );
        mAlias.setTypeAc( jAlias.getTypeAc() );
        mAlias.setValue( jAlias.getValue() );
        if (jAlias.getValue() == null || jAlias.getValue().length() == 0) {
            InvalidXmlEvent evt = new InvalidXmlEvent(FileParsingErrorType.missing_alias_name, "It is not a valid alias because the alias name is empty or null");
            evt.setSourceLocator(new FileSourceLocator(jAlias.sourceLocation() != null ? jAlias.sourceLocation().getLineNumber() : 0, jAlias.sourceLocation() != null ? jAlias.sourceLocation().getColumnNumber() : 0));

            for (PsiXml25ParserListener l : listeners){
                l.fireOnInvalidXmlSyntax(evt);
            }
        }

        return mAlias;
    }

    public psidev.psi.mi.xml254.jaxb.Alias toJaxb( psidev.psi.mi.xml.model.Alias mAlias ) {

        if ( mAlias == null ) {
            throw new IllegalArgumentException( "You must give a non null model alias." );
        }

        psidev.psi.mi.xml254.jaxb.Alias jAlias = new psidev.psi.mi.xml254.jaxb.Alias();

        // Initialise the JAXB object reading the model

        // 1. set attributes

        jAlias.setType( mAlias.getAliasType() );
        jAlias.setTypeAc( mAlias.getTypeAc() );
        jAlias.setValue( mAlias.getValue() );

        return jAlias;
    }
}