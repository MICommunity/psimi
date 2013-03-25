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
 * Converter to and from JAXB of the class DbReference.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.DbReference
 * @see psidev.psi.mi.xml254.jaxb.DbReference
 * @since <pre>07-Jun-2006</pre>
 */
public class DbReferenceConverter {

    public DbReferenceConverter() {
    }

    private List<PsiXml25ParserListener> listeners;

    public void setListeners(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
    }
    /////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.DbReference fromJaxb( psidev.psi.mi.xml254.jaxb.DbReference jDbReference ) {

        if ( jDbReference == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB DbReference." );
        }

        psidev.psi.mi.xml.model.DbReference mDbReference = new psidev.psi.mi.xml.model.DbReference();
        Locator locator = jDbReference.sourceLocation();
        if (locator != null){
            mDbReference.setSourceLocator(new FileSourceLocator(locator.getLineNumber(), locator.getColumnNumber()));
        }
        // Initialise the model reading the Jaxb object

        // 1. set attributes

        mDbReference.setDb( jDbReference.getDb() );
        mDbReference.setDbAc( jDbReference.getDbAc() );

        if (jDbReference.getDb() == null || jDbReference.getDb().length() == 0) {
            InvalidXmlEvent evt = new InvalidXmlEvent(FileParsingErrorType.missing_database, "It is not a valid cross reference because the database is empty or null");
            evt.setSourceLocator(new FileSourceLocator(jDbReference.sourceLocation() != null ? jDbReference.sourceLocation().getLineNumber() : 0, jDbReference.sourceLocation() != null ? jDbReference.sourceLocation().getColumnNumber() : 0));

            for (PsiXml25ParserListener l : listeners){
                l.fireOnInvalidXmlSyntax(evt);
            }
        }

        mDbReference.setId( jDbReference.getId() );

        if (jDbReference.getId() == null || jDbReference.getId().length() == 0) {
            InvalidXmlEvent evt = new InvalidXmlEvent(FileParsingErrorType.missing_database_accession, "It is not a valid cross reference because the database accession is empty or null");
            evt.setSourceLocator(new FileSourceLocator(jDbReference.sourceLocation() != null ? jDbReference.sourceLocation().getLineNumber() : 0, jDbReference.sourceLocation() != null ? jDbReference.sourceLocation().getColumnNumber() : 0));

            for (PsiXml25ParserListener l : listeners){
                l.fireOnInvalidXmlSyntax(evt);
            }
        }

        mDbReference.setRefType( jDbReference.getRefType() );
        mDbReference.setRefTypeAc( jDbReference.getRefTypeAc() );
        mDbReference.setSecondary( jDbReference.getSecondary() );
        mDbReference.setVersion( jDbReference.getVersion() );

        return mDbReference;
    }

    public psidev.psi.mi.xml254.jaxb.DbReference toJaxb( psidev.psi.mi.xml.model.DbReference mDbReference ) {

        if ( mDbReference == null ) {
            throw new IllegalArgumentException( "You must give a non null model DbReference." );
        }

        psidev.psi.mi.xml254.jaxb.DbReference jDbReference = new psidev.psi.mi.xml254.jaxb.DbReference();

        // Initialise the JAXB object reading the model

        // 1. set attributes

        jDbReference.setDb( mDbReference.getDb() );
        jDbReference.setDbAc( mDbReference.getDbAc() );
        jDbReference.setId( mDbReference.getId() );
        jDbReference.setRefType( mDbReference.getRefType() );
        jDbReference.setRefTypeAc( mDbReference.getRefTypeAc() );
        jDbReference.setSecondary( mDbReference.getSecondary() );
        jDbReference.setVersion( mDbReference.getVersion() );

        return jDbReference;
    }
}