package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.OntologyTerm;

/**
 * Utility class for OntologyTerm
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/07/13</pre>
 */

public class OntologyTermUtils {

    /**
     * Method to check if a Cv term is a child of a term having the given mi and/or name
     * @param term
     * @param mi
     * @param name
     * @return true if the ontologTerm is a child of the specific term
     */
    public static boolean isCvTermChildOf(OntologyTerm term, String mi, String name){

        if (term.getMIIdentifier() != null && mi != null){
            if (term.getMIIdentifier().equals(mi)){
                return true;
            }
            else if (!term.getParents().isEmpty()){
                for (OntologyTerm parent : term.getParents()){
                    if (isCvTermChildOf(parent, mi, name)){
                        return true;
                    }
                }
                return false;
            }
            else {
                return false;
            }
        }
        else{
            if (term.getShortName().equalsIgnoreCase(name) || (term.getFullName() != null && term.getFullName().equalsIgnoreCase(name))){
                return true;
            }
            else if (!term.getParents().isEmpty()){
                for (OntologyTerm parent : term.getParents()){
                    if (isCvTermChildOf(parent, mi, name)){
                        return true;
                    }
                }
                return false;
            }
            else {
                return false;
            }
        }
    }
}
