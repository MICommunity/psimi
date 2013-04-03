package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

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
     * Method to return a sub collection of identifiers (qualifier identity or secondary) in a list of Xrefs
     * @param refs
     * @return the sublist of identifiers (qualifier identity or secondary) from the list of Xrefs
     */
    public static Collection<Xref> collectAllIdentifiersFrom(Collection<? extends Xref> refs){

        if (refs == null || refs.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Collection<Xref> identifiers = new ArrayList<Xref>(refs.size());

        for (Xref ref : refs){
            if (isXrefAnIdentifier(ref)){
                identifiers.add(ref);
            }
        }

        return identifiers;
    }

    /**
     * Collect all cross references having a specific database and qualifier
     * @param refs
     * @param dbId
     * @param dbName
     * @param qualifierId
     * @param qulifierName
     * @return
     */
    public static Collection<Xref> collectAllXrefsHavingDatabaseAndQualifier(Collection<? extends Xref> refs, String dbId, String dbName, String qualifierId, String qulifierName){

        if (refs == null || refs.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Collection<Xref> identifiers = new ArrayList<Xref>(refs.size());

        for (Xref ref : refs){
            if (isXrefFromDatabase(ref, dbId, dbName) && doesXrefHaveQualifier(ref, qualifierId, qulifierName)){
                identifiers.add(ref);
            }
        }

        return identifiers;
    }

    public static Collection<Xref> searchAllXrefsHavingDatabaseAndQualifier( Collection<Xref> xrefs,
                                                            Collection<String> typeMiRefs,
                                                            Collection<String> typeRefs,
                                                            Collection<String> dbMiRefs,
                                                            Collection<String> dbRefs) {

        if (xrefs == null || xrefs.isEmpty() || (typeMiRefs.isEmpty() && typeRefs.isEmpty()) || (dbMiRefs.isEmpty() && dbRefs.isEmpty())){
            return Collections.EMPTY_LIST;
        }
        Collection<Xref> refs = new ArrayList<Xref>(xrefs.size());

        for ( Xref ref : xrefs ) {
            if (ref.getDatabase().getMIIdentifier() != null && !dbMiRefs.isEmpty()) {
                if (!dbMiRefs.contains( ref.getDatabase().getMIIdentifier() )){
                    continue;
                }
            }
            else if (dbRefs.contains( ref.getDatabase().getShortName() )){
                continue;
            }

            if ( ref.getQualifier() == null ) {
                continue;
            }
            else if (ref.getQualifier().getMIIdentifier() != null && !typeMiRefs.isEmpty()){
                if ( !typeMiRefs.contains( ref.getQualifier().getMIIdentifier() )){
                    continue;
                }
            }
            else if (!typeRefs.contains( ref.getQualifier().getShortName() )){
                continue;
            }
            refs.add( ref );
        }

        return refs;
    }

    public static Collection<Xref> searchAllXrefsHavingDatabase( Collection<Xref> xrefs, Collection<String> dbMiRefs, Collection<String> dbRefs) {

        if (xrefs == null || xrefs.isEmpty() || (dbMiRefs.isEmpty() && dbRefs.isEmpty())){
            return Collections.EMPTY_LIST;
        }
        Collection<Xref> refs = new ArrayList<Xref>(xrefs.size());

        for ( Xref ref : xrefs ) {
            if (ref.getDatabase().getMIIdentifier() != null && !dbMiRefs.isEmpty()) {
                if (!dbMiRefs.contains( ref.getDatabase().getMIIdentifier() )){
                    continue;
                }
            }
            else if (!dbRefs.contains( ref.getDatabase().getShortName().trim().toLowerCase() )){
                continue;
            }
            refs.add( ref );
        }

        return refs;
    }

    /**
     * Collect all cross references having a specific qualifier
     * @param refs
     * @param qualifierId
     * @param qulifierName
     * @return
     */
    public static Collection<Xref> collectAllXrefsHavingQualifier(Collection<? extends Xref> refs, String qualifierId, String qulifierName){

        if (refs == null || refs.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Collection<Xref> identifiers = new ArrayList<Xref>(refs.size());

        for (Xref ref : refs){
            if (doesXrefHaveQualifier(ref, qualifierId, qulifierName)){
                identifiers.add(ref);
            }
        }

        return identifiers;
    }

    /**
     * Collect all cross references having a specific database
     * @param refs
     * @param dbId
     * @param dbName
     * @return
     */
    public static Collection<Xref> collectAllXrefsHavingDatabase(Collection<? extends Xref> refs, String dbId, String dbName){

        if (refs == null || refs.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Collection<Xref> identifiers = new ArrayList<Xref>(refs.size());

        for (Xref ref : refs){
            if (isXrefFromDatabase(ref, dbId, dbName)){
                identifiers.add(ref);
            }
        }

        return identifiers;
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
     * This method will return the first Xref from this database having :
     * - identity qualifier
     * - secondary identifier if no identity qualifier
     * - first Xref from this database if no identity or secondary qualifier
     * It will return null if there are no Xrefs with this database id/name
     * @param refs : the collection of Xrefs
     * @param dbId : the database id to look for
     * @param dbName : the database name to look for
     * @return the first identifier having this database name/id, null if no Xrefs with this database name/id
     */
    public static Xref collectFirstIdentifierWithDatabase(Collection<? extends Xref> refs, String dbId, String dbName){

        if (refs == null || (dbName == null && dbId == null)){
            return null;
        }

        Xref firstXref = null;
        Xref firstSecondary = null;
        for (Xref xref : refs){
            CvTerm database = xref.getDatabase();
            // we can compare identifiers
            if (dbId != null && database.getMIIdentifier() != null){
                // we have the same database id
                if (database.getMIIdentifier().equals(dbId)){
                    if (doesXrefHaveQualifier(xref, Xref.IDENTITY_MI, Xref.IDENTITY)){
                        return xref;
                    }
                    // secondary ref
                    else if (doesXrefHaveQualifier(xref, Xref.SECONDARY_MI, Xref.SECONDARY) && firstSecondary != null){
                        firstSecondary = xref;
                    }
                    else if (firstXref != null){
                        firstXref = xref;
                    }
                }
            }
            // we need to compare dbNames
            else if (dbName != null && dbName.toLowerCase().equals(database.getShortName().toLowerCase())) {
                // we have the same database id
                if (database.getShortName().toLowerCase().trim().equals(dbName)){
                    if (doesXrefHaveQualifier(xref, Xref.IDENTITY_MI, Xref.IDENTITY)){
                        return xref;
                    }
                    // secondary ref
                    else if (doesXrefHaveQualifier(xref, Xref.SECONDARY_MI, Xref.SECONDARY) && firstSecondary != null){
                        firstSecondary = xref;
                    }
                    else if (firstXref != null){
                        firstXref = xref;
                    }
                }
            }
        }

        return firstSecondary != null ? firstSecondary : firstXref;
    }

    /**
     * Remove all Xrefs having this database name/database id from the collection of xrefs
     * @param refs : the collection of Xrefs
     * @param dbId : the database id to look for
     * @param dbName : the database name to look for
     */
    public static void removeAllXrefsWithDatabase(Collection<? extends Xref> refs, String dbId, String dbName){

        if (refs != null){
            Iterator<? extends Xref> refIterator = refs.iterator();

            while (refIterator.hasNext()){
                if (isXrefFromDatabase(refIterator.next(), dbId, dbName)){
                    refIterator.remove();
                }
            }
        }
    }
}
