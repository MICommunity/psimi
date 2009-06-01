/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.converter.tab2xml.XmlConversionException;
import psidev.psi.mi.tab.model.InteractionDetectionMethod;
import psidev.psi.mi.tab.model.InteractionDetectionMethodFactory;
import psidev.psi.mi.xml.model.CvType;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Names;
import psidev.psi.mi.xml.model.Xref;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Iterator;

/**
 * Converts an CV term into a suitable format for PSIMITAB.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class InteractionDetectionMethodConverter {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( InteractionDetectionMethodConverter.class );

    public static final String PSI_MI = "psi-mi";
    public static final String PSI_MI_REF = "MI:0488";


    // here we are giving the Class, while now we have to use the Factory
    public InteractionDetectionMethod toMitab( CvType cv ) throws TabConversionException {

        InteractionDetectionMethod myCv = null;

        if ( cv == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB OpenCvType." );
        }

        String name = null;
        String db = null;
        String id = null;

        if ( cv.hasNames() ) {
            name = cv.getNames().getShortLabel();
        }

        // Fetch PSI-MI ref
        Collection<DbReference> refs = XrefUtils.searchByDatabase( cv.getXref(), PSI_MI, PSI_MI_REF );
        if( refs.isEmpty() ) {
            // search by identity
            refs = XrefUtils.searchByType( cv.getXref(), "identity", "MI:0356" );
        }


        if ( !refs.isEmpty() ) {
            Iterator<DbReference> iterator = refs.iterator();
            id = iterator.next().getId();
            if ( iterator.hasNext() ) {
                log.warn( cv + " has " + refs.size() + " references to PSI-MI to choose from, picked the first one." );
                log.warn( "1: " + id );
                int i = 1;
                while ( iterator.hasNext() ) {
                    DbReference dbReference = iterator.next();
                    log.warn( ( ++i ) + ": " + dbReference.getId() );
                }
            }
        }

        if ( id == null ) {
            throw new NullPointerException( "Invalid interaction detection method without identifier: " + name );
        } else {
            int idx = id.indexOf( ":" );
            if ( idx != -1 ) {
                // split db from id
                db = id.substring( 0, idx );
                id = id.substring( idx + 1, id.length() );
            } else {
                throw new IllegalStateException( "We are expecting PSI-MI identifier to be formatted as MI:xxxx." );
            }
        }

        return InteractionDetectionMethodFactory.getInstance().build( db, id, name );
    }

    /**
     * Converts the psidev.psi.mi.tab.model.InteractionDetectionMethod to <T> object.
     *
     * @param <T>
     * @param tabCv
     * @param clazz
     * @return
     * @throws psidev.psi.mi.tab.converter.tab2xml.XmlConversionException
     *
     */
    public <T extends CvType> T fromMitab( InteractionDetectionMethod tabCv, Class<T> clazz ) throws XmlConversionException {

        T myCv = null;
        if ( tabCv == null ) {
            throw new IllegalArgumentException( "You must give a non null tabCv InteractionDetectionMethod." );
        }

        if ( clazz == null ) {
            throw new IllegalArgumentException( "You must give a non null clazz Class." );
        }

        Names names = null;
        Xref xref = null;


        if ( tabCv.hasText() && names == null ) {
            String shortLabel = tabCv.getText();
            names = new Names();
            names.setShortLabel( shortLabel );
            names.setFullName( shortLabel );
        }

        if ( tabCv.getIdentifier() != null && xref == null ) {
            String primaryId = tabCv.getIdentifier();
            DbReference primaryRef = new DbReference();
            primaryRef.setId( primaryId );
            primaryRef.setRefType( "identity" );
            primaryRef.setRefTypeAc( "MI:0356" );
            primaryRef.setDb( PSI_MI );
            primaryRef.setDbAc( PSI_MI_REF );
            xref = new Xref( primaryRef );
        }

        try {
            // request constructor
            Constructor<T> constructor = clazz.getConstructor( new Class[]{} );

            // instanciate object
            myCv = constructor.newInstance( new Object[]{} );
            if ( names != null ) myCv.setNames( names );
            if ( xref != null ) myCv.setXref( xref );

        } catch ( Exception e ) {
            throw new XmlConversionException( "An exception was thrown while instanciating an xml.", e );
        }
        return myCv;
    }
}