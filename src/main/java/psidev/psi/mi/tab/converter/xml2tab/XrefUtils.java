/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Xref;

import java.util.*;

/**
 * Xref search utility.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03-Oct-2006</pre>
 */
public class XrefUtils {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( XrefUtils.class );

    public static final String PSI_MI = "psi-mi";
    public static final String PSI_MI_REF = "MI:0488";

    /**
     * Collect DbReference that match the given database criteria.
     *
     * @param xref
     * @param name
     * @param miRef
     * @return a non null collection.
     */
    public static Collection<DbReference> searchByDatabase( Xref xref, String name, String miRef ) {

        if ( name == null && miRef == null ) {
            throw new IllegalArgumentException( "You must give either a name of an MI reference (or both)." );
        }

        List<DbReference> collected = new ArrayList<DbReference>( 2 );
        List<DbReference> xrefs = getAllDbReferences( xref );

        for ( DbReference reference : xrefs ) {
            if ( name != null ) {
                if ( name.equalsIgnoreCase( reference.getDb() ) ) {
                    collected.add( reference );
                }
            } else if ( miRef != null ) {
                if ( miRef.equalsIgnoreCase( reference.getDbAc() ) ) {
                    collected.add( reference );
                }
            }
        }

        return collected;
    }

    /**
     * Collect DbReference that match the given database criteria.
     *
     * @param xref
     * @param name
     * @param miRef
     * @return a non null collection.
     */
    public static Collection<DbReference> searchByType( Xref xref, String name, String miRef ) {

        if ( name == null && miRef == null ) {
            throw new IllegalArgumentException( "You must give either a name of an MI reference (or both)." );
        }

        List<DbReference> collected = new ArrayList<DbReference>( 2 );
        List<DbReference> xrefs = getAllDbReferences( xref );

        for ( DbReference reference : xrefs ) {
            // Priority to the MI ref is available.
            if ( miRef != null ) {
                if ( miRef.equalsIgnoreCase( reference.getRefTypeAc() ) ) {
                    collected.add( reference );
                }
            } else if ( name != null ) {
                if ( name.equalsIgnoreCase( reference.getRefType() ) ) {
                    collected.add( reference );
                }
            } 
        }

        return collected;
    }

    /**
     * Collect DbReference that match the given interactorType and database criteria.
     *
     * @param xref
     * @param typeName
     * @param typeMiRef
     * @param dbName
     * @param dbMiRef
     * @return
     */
    public static Collection<DbReference> searchByTypeAndDatabase(Xref xref, String typeName, String typeMiRef, String dbName, String dbMiRef){

        Collection<DbReference> typeRefs = searchByType(xref, typeName, typeMiRef);
    	Collection<DbReference> dbRefs = searchByDatabase(xref, dbName, dbMiRef);
    	        
    	List<DbReference> collected = new ArrayList<DbReference>( 2 );
    	
    	for ( DbReference dbRef : dbRefs ) {
    		for ( DbReference typeRef : typeRefs ) {
    			if (typeRef.equals(dbRef)){
    				collected.add(typeRef);
    			}
    		}
    	}
    	
    	return collected;
    }

    /**
     * Collect all DbReference under an xref.
     *
     * @param xref
     * @return a non null collection.
     */
    public static List<DbReference> getAllDbReferences( Xref xref ) {
        ArrayList<DbReference> xrefs = new ArrayList<DbReference>();

        xrefs.add( xref.getPrimaryRef() );
        if ( xref.hasSecondaryRef() ) {
            for ( DbReference sec : xref.getSecondaryRef() ) {
                xrefs.add( sec );
            }
        }

        return xrefs;
    }

    /**
     * Checks if an Xref has the given PSI-MI reference
     *
     * @param xref
     * @param psiRef
     * @return
     */
    public static boolean hasPsiId( Xref xref, String psiRef ) {

        if ( psiRef == null || "".equals( psiRef.trim() ) ) {
            throw new IllegalArgumentException( "You must give a non null/empty PSI-MI id." );
        }

        if ( xref == null ) {
            return false;
        }

        List<DbReference> list = getAllDbReferences( xref );
        for ( DbReference ref : list ) {
            if ( PSI_MI.equalsIgnoreCase( ref.getDb() ) || PSI_MI_REF.equalsIgnoreCase( ref.getDbAc() ) ) {
                if ( psiRef.equalsIgnoreCase( ref.getId() ) ) {
                    log.debug( "Found Xref( psi-mi, " + psiRef + " )" );
                    return true;
                }
            }
        }
        return false;
    }

    public static List<DbReference> sortByIdentifier( Collection<DbReference> xrefs ) {
        List<DbReference> sortedXrefs = new ArrayList<DbReference>( xrefs );

        Collections.sort( sortedXrefs, new Comparator<DbReference>() {
            public int compare( DbReference x1, DbReference x2 ) {
                return x1.getId().compareTo( x2.getId() );
            }
        } );

        return sortedXrefs;
    }
}