package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;

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
        if (typeId != null && type.getOntologyIdentifier() != null){
            // we have the same type id
            return type.getOntologyIdentifier().getId().equals(typeId);
        }
        // we need to compare type names
        else if (typeName != null) {
            return typeName.toLowerCase().equals(type.getShortName().toLowerCase());
        }

        return false;
    }
}
