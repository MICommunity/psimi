/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.converter.tab2xml.XmlConversionException;
import psidev.psi.mi.tab.model.InteractionType;
import psidev.psi.mi.tab.model.InteractionTypeImpl;
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
@Deprecated
public class InteractionTypeConverter {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( InteractionTypeConverter.class );

    public static final String PSI_MI = "psi-mi";
    public static final String PSI_MI_REF = "MI:0488";
    private static final String MI_PREFIX = "MI:";


    // here we are giving the Class, while now we have to use the Factory
    public InteractionType toMitab( CvType cv ) throws TabConversionException {

        InteractionType myCv = null;

        if ( cv == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB OpenCvType." );
        }

        String name = null;
        String db = "unknown";
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
            db = PSI_MI;

            Iterator<DbReference> iterator = refs.iterator();
            id = iterator.next().getId();
            if ( iterator.hasNext() ) {
                log.warn( cv + " has " + refs.size() + " references to PSI-MI to choose from, picked the first one." );
                int i = 1;
                while ( iterator.hasNext() ) {
                    DbReference dbReference = iterator.next();
                    log.warn( ( ++i ) + ": " + dbReference.getId() );
                }
            }
        }

//        if ( name != null ) {
        myCv = new InteractionTypeImpl( db, id, name );
//        }

        return myCv;
    }

    public <T extends CvType> T fromMitab( InteractionType interactionType, Class<T> clazz ) throws XmlConversionException {
        T myCv = null;

        if ( interactionType == null ) {
            throw new IllegalArgumentException( "You must give a non null tabCv InteractionType." );
        }

        if ( clazz == null ) {
            throw new IllegalArgumentException( "You must give a non null implementation class of CvType." );
        }

        Names names = null;
        Xref xref = null;

        if ( interactionType.hasText() ) {
            String shortLabel = interactionType.getText();
            names = new Names();
            names.setShortLabel( shortLabel );
        }

        if ( interactionType.getIdentifier() != null ) {
            String id = interactionType.getIdentifier();
            DbReference primaryRef = new DbReference( id, PSI_MI );
            primaryRef.setDbAc( PSI_MI_REF );
            primaryRef.setRefType( "identity" );
            primaryRef.setRefTypeAc( "MI:0356" );

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
    
//    public <T extends CvType> T fromMitab(Collection <InteractionType> tabCvs, Class<T> clazz ) throws XmlConversionException {
//    	T myCv = null;
//
//       	if (tabCvs == null){
//    		throw new IllegalArgumentException( "You must give a non null tabCv InteractionType." );
//    	}
//
//    	if (clazz == null){
//    		throw new IllegalArgumentException( "You must give a non null implementation class of CvType." );
//    	}
//
//    	Names names = null;
//    	Xref xref = null;
//
//    	if (!tabCvs.isEmpty()){
//    		DbReference primaryRef = null;
//    		Collection<DbReference> secondaryRefs = new ArrayList<DbReference>();
//        	for (InteractionType tabCv : tabCvs){
//        		if (tabCv.hasText() && names == null){
//            		String shortLabel = tabCv.getText();
//            		names = new Names();
//            		names.setShortLabel(shortLabel);
//            	}
//
//            	if (tabCv.getIdentifier() != null && primaryRef == null){
//            		String id = MI_PREFIX.concat(tabCv.getIdentifier());
//            		primaryRef = new DbReference();
//            		primaryRef.setId(id);
//            	}
//
//            	if (tabCv.getIdentifier() != null && primaryRef != null){
//            		String id = MI_PREFIX.concat(tabCv.getIdentifier());
//            		DbReference secondaryRef = new DbReference();
//            		secondaryRef.setId(id);
//            		secondaryRefs.add(secondaryRef);
//            	}
//        	}
//        	if (primaryRef != null){
//        		if (secondaryRefs != null){
//        			xref = new Xref(primaryRef, secondaryRefs);
//        		}else{
//        			xref = new Xref(primaryRef);
//        		}
//        	}
//    	}
//    	try{
//    		// request constructor
//    		Constructor <T> constructor = clazz.getConstructor(new Class[]{});
//
//    		// instanciate object
//    		myCv = constructor.newInstance(new Object[]{});
//    		if (names != null)	myCv.setNames(names);
//    		if (xref != null)	myCv.setXref(xref);
//
//    	}catch (Exception e) {
//    		throw new XmlConversionException( "An exception was thrown while instanciating an xml.", e );
//		}
//
//    	return myCv;
//    }
}