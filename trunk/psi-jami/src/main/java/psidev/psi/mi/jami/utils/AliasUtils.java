package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Collection;
import java.util.Iterator;

/**
 * Utility class for aliases
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/02/13</pre>
 */

public class AliasUtils {

    public static boolean doesAliasHaveType(Alias alias, String typeId, String typeName){

        if (alias == null || (typeName == null && typeId == null)){
            return false;
        }

        CvTerm type = alias.getType();
        // we can compare identifiers
        if (typeId != null && type.getMIIdentifier() != null){
            // we have the same type id
            return type.getMIIdentifier().equals(typeId);
        }
        // we need to compare type names
        else if (typeName != null) {
            return typeName.toLowerCase().equals(type.getShortName().toLowerCase());
        }

        return false;
    }

    /**
     * This method will return the first Alias having this typeId/type name
     * It will return null if there are no Alias with this type id/name
     * @param aliases : the collection of Alias
     * @param typeId : the type id to look for
     * @param typeName : the type name to look for
     * @return the first alias having this type name/id, null if no Alias with this type name/id
     */
    public static Alias collectFirstAliasWithType(Collection<Alias> aliases, String typeId, String typeName){

        if (aliases == null || (typeName == null && typeId == null)){
            return null;
        }

        for (Alias alias : aliases){
            CvTerm type = alias.getType();
            // we can compare type ids
            if (typeId != null && type.getMIIdentifier() != null){
                // we have the same type id
                if (type.getMIIdentifier().equals(typeId)){
                    return alias;
                }
            }
            // we need to compare alias type Name
            else if (typeName != null && typeName.toLowerCase().equals(type.getShortName().toLowerCase())) {
                // we have the same type name
                if (type.getShortName().toLowerCase().trim().equals(typeName)){
                    return alias;
                }
            }
        }

        return null;
    }

    /**
     * Remove all Alias having this method name/method id from the collection of aliases
     * @param aliases : the collection of Checksum
     * @param typeId : the method id to look for
     * @param typeName : the method name to look for
     */
    public static void removeAllAliasesWithType(Collection<Alias> aliases, String typeId, String typeName){

        if (aliases != null){
            Iterator<Alias> aliasIterator = aliases.iterator();

            while (aliasIterator.hasNext()){
                if (doesAliasHaveType(aliasIterator.next(), typeId, typeName)){
                    aliasIterator.remove();
                }
            }
        }
    }
}
