package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.util.ArrayList;
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
     * Method to know if a Xref is a potential identifier.
     * It can be an identity xref or a secondary xref.
     * @param ref : the Xref
     * @return true if the Xref has a qualifier identity or secondary-ac
     */
    public static boolean isXrefAnIdentifier(Xref ref){
        if (ref == null || ref.getQualifier() == null){
            return false;
        }

        String qualifierMI = ref.getQualifier().getMIIdentifier();

        if (qualifierMI != null){
            return (Xref.IDENTITY_MI.equals(qualifierMI) || Xref.SECONDARY_MI.equals(qualifierMI));
        }
        else {
            String qualifier = ref.getQualifier().getShortName().toLowerCase().trim();

            return (Xref.IDENTITY.equals(qualifier) || Xref.SECONDARY.equals(qualifier));
        }
    }

    /**
     * Method to know if a Xref is from the same database (dbId is the MI identifier and dbName is the database shortname)
     * @param ref : the Xref
     * @param dbId : the database MI identifier
     * @param dbName : the database shortname
     * @return true if the Xref has a database MI which is dbId or database shortname which is dbName
     */
    public static boolean isXrefFromDatabase(Xref ref, String dbId, String dbName){

        if (ref == null || (dbName == null && dbId == null)){
            return false;
        }

        CvTerm database = ref.getDatabase();
        // we can compare identifiers
        if (dbId != null && database.getMIIdentifier() != null){
            // we have the same database id
            return database.getMIIdentifier().equals(dbId);
        }
        // we need to compare dbNames
        else if (dbName != null) {
            return dbName.toLowerCase().equals(database.getShortName().toLowerCase());
        }

        return false;
    }

    /**
     * Method to know if a Xref has the same qualifier (qualifierId is the MI identifier and qualifierName is the qualifier shortname)
     * @param ref : the Xref
     * @param qualifierId : the qualifier MI identifier
     * @param qualifierName : the qualifier shortname
     * @return true if the Xref has a qualifier MI which is qualifierId or qualifier shortname which is qualifierName
     */
    public static boolean doesXrefHaveQualifier(Xref ref, String qualifierId, String qualifierName){

        if (ref == null || (qualifierName == null && qualifierId == null)){
            return false;
        }

        CvTerm qualifier = ref.getQualifier();
        if (qualifier == null){
            return false;
        }
        // we can compare identifiers
        if (qualifierId != null && qualifier.getMIIdentifier() != null){
            // we have the same database id
            return qualifier.getMIIdentifier().equals(qualifierId);
        }
        // we need to compare dbNames
        else if (qualifierName != null) {
            return qualifierName.toLowerCase().equals(qualifier.getShortName().toLowerCase());
        }

        return false;
    }

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
            if (dbId != null && database.getMIIdentifier() != null){
                // we have the same database id
                if (database.getMIIdentifier().equals(dbId)){
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

    public static void removeAllXrefsFromDatabaseIfExists(Collection<? extends Xref> refs, String dbId, String dbName){

        if (refs != null && (dbName == null && dbId == null)){
            Collection<? extends Xref> xrefsCopy = new ArrayList<Xref>(refs);
            for (Xref xref : xrefsCopy){
                CvTerm database = xref.getDatabase();
                // we can compare identifiers
                if (dbId != null && database.getMIIdentifier() != null){
                    // we have the same database id
                    if (database.getMIIdentifier().equals(dbId)){
                        // remove xref
                        refs.remove(xref);
                    }
                }
                // we need to compare dbNames
                else if (dbName != null && dbName.toLowerCase().equals(database.getShortName().toLowerCase())) {
                    // remove xref
                    refs.remove(xref);
                }
            }
        }
    }
}
