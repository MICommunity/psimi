/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.CrossReferenceFactory;
import psidev.psi.mi.xml.model.Bibref;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Xref;

import java.util.Collection;

/**
 * Tansforms BibRef into CrossReferenceImpl.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03-Oct-2006</pre>
 */
public class PublicationConverter {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( PublicationConverter.class );

    public static final String PRIMARY_REFERENCE = "primary-reference";
    public static final String PRIMARY_REFERENCE_REF = "MI:0358";

    public CrossReference toMitab( Bibref bibref ) {

        CrossReference cr = null;

        if ( bibref != null && bibref.getXref() != null ) {

            Collection<DbReference> refs = XrefUtils.searchByType( bibref.getXref(), PRIMARY_REFERENCE, PRIMARY_REFERENCE_REF );
            for ( DbReference ref : refs ) {
                if ( cr == null ) {
                    cr = CrossReferenceFactory.getInstance().build( ref.getDb(), ref.getId() );
                } else {
                    log.warn( "More than once " + PRIMARY_REFERENCE + " found, first one was chosen." );
                }
            }
        }

        return cr;
    }

    public Bibref fromMitab( CrossReference ref ) {
    	Bibref bibref = null;
    	
    	if (ref != null && ref.getIdentifier() != null && ref.getDatabase() != null){
    		String db = ref.getDatabase();
    		String id = ref.getIdentifier();
    		
    		DbReference primaryRef = new DbReference(id, db);
    		primaryRef.setRefType("primary-reference");
    		primaryRef.setRefTypeAc("MI:0358");
    		if (db.equals("pubmed")) primaryRef.setDbAc("MI:0446");
    		Xref xref = new Xref(primaryRef);
    		bibref = new Bibref(xref);
    	} 
    	
        return bibref;
    }
}