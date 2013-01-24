package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.util.Collection;

/**
 * Utility class for Xrefs
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/13</pre>
 */

public class XrefUtils {

    /**
     * Retrives a unique Xref having a database that matches the database id (if set) or the database name.
     * It will return null if it cannot find a single Xref with a database match or if it finds more than one xrefs with a database
     * match.
     * @param refs
     * @param dbId
     * @param dbName
     * @return the unique Xref, null if not unique
     */
    public static Xref collectUniqueXrefFromDatabaseIfExists(Collection<? extends Xref> refs, String dbId, String dbName){

        if (refs == null || (dbName == null && dbId == null)){
            return null;
        }

        Xref uniqueXref = null;
        for (Xref xref : refs){
            CvTerm database = xref.getDatabase();
            // we can compare identifiers
            if (dbId != null && database.getOntologyIdentifier() != null){
                // we have the same database id
                if (database.getOntologyIdentifier().getId().equals(dbId)){
                    // it is a unique xref
                    if (uniqueXref == null){
                        uniqueXref = xref;
                    }
                    // we could not find a unique xref from this database so we return null
                    else {
                        return null;
                    }
                }
            }
            // we need to compare dbNames
            else if (dbName != null && dbName.toLowerCase().equals(database.getShortName().toLowerCase())) {
                // it is a unique xref
                if (uniqueXref == null){
                    uniqueXref = xref;
                }
                // we could not find a unique xref from this database so we return null
                else {
                    return null;
                }
            }
        }

        return uniqueXref;
    }
}
