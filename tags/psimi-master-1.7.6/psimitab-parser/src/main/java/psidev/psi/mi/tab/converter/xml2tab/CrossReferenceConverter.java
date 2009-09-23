/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.converter.tab2xml.XmlConversionException;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.CrossReferenceFactory;
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
public class CrossReferenceConverter {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( CrossReferenceConverter.class );

    public static final String PSI_MI = "psi-mi";
    public static final String PSI_MI_REF = "MI:0488";


    // here we are giving the Class, while now we have to use the Factory
    public CrossReference toMitab( CvType cv ) throws TabConversionException {

        CrossReference myCv = null;

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
        if ( !refs.isEmpty() ) {
            Iterator<DbReference> iterator = refs.iterator();
            final DbReference dbRef = iterator.next();
            id = dbRef.getId();
            db = dbRef.getDb();
            if ( iterator.hasNext() ) {
                log.warn( cv + " has " + refs.size() + " references to PSI-MI to choose from, picked the first one." );
                int i = 1;
                while ( iterator.hasNext() ) {
                    DbReference dbReference = iterator.next();
                }
            }
        }

        if ( name != null ) {
            CrossReferenceFactory.getInstance().build( db, id, name );
        }

        return myCv;
    }

    public <T extends CvType> T fromMitab( CrossReference tabCv, Class<T> clazz ) throws XmlConversionException {

        T myCv = null;

        if ( tabCv == null ) throw new IllegalArgumentException( "You must give a non null tabCv CrossReference." );

        if ( clazz == null ) throw new IllegalArgumentException( "You must give a non null clazz Class." );


        Names names = null;
        Xref xref = null;

        // Text of CrossReference is similar to shortLabel
        if ( tabCv.hasText() ) {
            String shortLabel = tabCv.getText();
            names = new Names();
            names.setShortLabel( shortLabel );
            names.setFullName( shortLabel );
        }

        // Identifier of CrossReference is part of the id in primaryRef
        if ( tabCv.getIdentifier() != null ) {
            String id = tabCv.getIdentifier();

            DbReference primaryRef = new DbReference( id, PSI_MI );
            primaryRef.setDbAc( PSI_MI_REF );
            primaryRef.setRefType( "identity" );
            primaryRef.setRefTypeAc( "MI:0356" );

            xref = new Xref( primaryRef );
        }

        try {
            // request constructor
            Constructor constructor = clazz.getConstructor( new Class[]{} );

            // instanciate object
            myCv = ( T ) constructor.newInstance( new Object[]{} );
            if ( names != null ) myCv.setNames( names );
            if ( xref != null ) myCv.setXref( xref );
        } catch ( Exception e ) {
            throw new XmlConversionException( "An exception was thrown while instanciating an xml.", e );
        }
        return myCv;
    }
}