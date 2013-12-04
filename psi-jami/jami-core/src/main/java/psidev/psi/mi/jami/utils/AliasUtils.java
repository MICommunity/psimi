package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAlias;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Utility class for aliases
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/02/13</pre>
 */

public class AliasUtils {

    /**
     * To know if an alias have a specific type.
     * @param alias
     * @param typeId
     * @param typeName
     * @return true if the alias has the type with given name/identifier
     */
    public static boolean doesAliasHaveType(Alias alias, String typeId, String typeName){

        if (alias == null || (typeName == null && typeId == null)){
            return false;
        }

        CvTerm type = alias.getType();
        if (type == null){
            return false;
        }

        // we can compare identifiers
        if (typeId != null && type.getMIIdentifier() != null){
            // we have the same type id
            return type.getMIIdentifier().equals(typeId);
        }
        // we need to compare type names
        else if (typeName != null) {
            return typeName.equalsIgnoreCase(type.getShortName());
        }

        return false;
    }

    /**
     * Extract all the aliases having a specific type.
     * @param aliases
     * @param typeId
     * @param typeName
     * @return the selection of aliases having the specified type (typeId or typeName if no ids)
     */
    public static Collection<Alias> collectAllAliasesHavingType(Collection<? extends Alias> aliases, String typeId, String typeName){

        if (aliases == null || aliases.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Collection<Alias> selectedAliases = new ArrayList<Alias>(aliases.size());

        for (Alias alias : aliases){
            if (doesAliasHaveType(alias, typeId, typeName)){
                selectedAliases.add(alias);
            }
        }

        return selectedAliases;
    }

    /**
     * This method will return the first Alias having this typeId/type name
     * It will return null if there are no Alias with this type id/name
     * @param aliases : the collection of Alias
     * @param typeId : the type id to look for
     * @param typeName : the type name to look for
     * @return the first alias having this type name/id, null if no Alias with this type name/id
     */
    public static Alias collectFirstAliasWithType(Collection<? extends Alias> aliases, String typeId, String typeName){

        if (aliases == null || aliases.isEmpty()){
            return null;
        }

        for (Alias alias : aliases){
            if (doesAliasHaveType(alias, typeId, typeName)){
                return alias;
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

    /**
     * To know if an alias have a specific type and  name.
     * @param alias
     * @param typeId
     * @param typeName
     * @param name: alias name
     * @return true if the alias has the type with given name/identifier
     */
    public static boolean doesAliasHaveTypeAndName(Alias alias, String typeId, String typeName, String name){

        if (alias == null || (typeName == null && typeId == null) || typeName == null){
            return false;
        }

        CvTerm type = alias.getType();
        if (type == null){
            return false;
        }

        // we can compare identifiers
        if (typeId != null && type.getMIIdentifier() != null){
            // we have the same type id
            if (type.getMIIdentifier().equals(typeId)){
                return alias.getName().equals(name);
            }
            else {
                return false;
            }
        }
        // we need to compare type names
        else if (typeName != null) {
            if (typeName.equalsIgnoreCase(type.getShortName())){
                return alias.getName().equals(name);
            }
            else {
                return false;
            }
        }

        return false;
    }

    /**
     * Extract all the aliases having a specific type.
     * @param aliases
     * @param typeId
     * @param typeName
     * @param name: alias name
     * @return the selection of aliases having the specified type (typeId or typeName if no ids)
     */
    public static Collection<Alias> collectAllAliasesHavingTypeAndName(Collection<? extends Alias> aliases, String typeId, String typeName, String name){

        if (aliases == null || aliases.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Collection<Alias> selectedAliases = new ArrayList<Alias>(aliases.size());

        for (Alias alias : aliases){
            if (doesAliasHaveTypeAndName(alias, typeId, typeName, name)){
                selectedAliases.add(alias);
            }
        }

        return selectedAliases;
    }

    /**
     * This method will return the first Alias having this typeId/type name
     * It will return null if there are no Alias with this type id/name
     * @param aliases : the collection of Alias
     * @param typeId : the type id to look for
     * @param typeName : the type name to look for
     * @param name: alias name
     * @return the first alias having this type name/id, null if no Alias with this type name/id
     */
    public static Alias collectFirstAliasWithTypeAndName(Collection<? extends Alias> aliases, String typeId, String typeName, String name){

        if (aliases == null || aliases.isEmpty()){
            return null;
        }

        for (Alias alias : aliases){
            if (doesAliasHaveTypeAndName(alias, typeId, typeName, name)){
                return alias;
            }
        }

        return null;
    }

    /**
     * Remove all Alias having this method name/method id from the collection of aliases
     * @param aliases : the collection of Checksum
     * @param typeId : the method id to look for
     * @param typeName : the method name to look for
     * @param name: alias name
     */
    public static void removeAllAliasesWithTypeAndName(Collection<Alias> aliases, String typeId, String typeName, String name){

        if (aliases != null){
            Iterator<Alias> aliasIterator = aliases.iterator();

            while (aliasIterator.hasNext()){
                if (doesAliasHaveTypeAndName(aliasIterator.next(), typeId, typeName, name)){
                    aliasIterator.remove();
                }
            }
        }
    }

    public static Alias createGeneName(String name){
        return new DefaultAlias(CvTermUtils.createGeneNameAliasType(), name);
    }

    public static Alias createComplexSynonym(String name){
        return new DefaultAlias(CvTermUtils.createComplexSynonym(), name);
    }

    public static Alias createAuthorAssignedName(String name){
        return new DefaultAlias(CvTermUtils.createAuthorAssignedName(), name);
    }

    public static Alias createGeneNameSynonym(String name){
        return new DefaultAlias(CvTermUtils.createGeneNameSynonym(), name);
    }

    public static Alias createIsoformSynonym(String name){
        return new DefaultAlias(CvTermUtils.createIsoformSynonym(), name);
    }

    public static Alias createOrfName(String name){
        return new DefaultAlias(CvTermUtils.createOrfName(), name);
    }

    public static Alias createLocusName(String name){
        return new DefaultAlias(CvTermUtils.createLocusName(), name);
    }

    public static Alias createAlias(String typeName, String typeMi, String name){
        return new DefaultAlias(CvTermUtils.createMICvTerm(typeName, typeMi), name);
    }

    public static Alias createAlias(String typeName, String name){
        return new DefaultAlias(CvTermUtils.createMICvTerm(typeName, null), name);
    }
}
