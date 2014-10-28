package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.model.Source;

/**
 * Utility class for cloning CvTerms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/02/13</pre>
 */

public class CvTermCloner {

    /***
     * This method will copy properties of cv term source in cv term target and will override all the other properties of Target cv term.
     * @param source : the cv term source to copy from
     * @param target : the cv term target to copy to
     */
    public static void copyAndOverrideCvTermProperties(CvTerm source, CvTerm target){
        if (source != null && target != null){
            target.setShortName(source.getShortName());
            target.setFullName(source.getFullName());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getSynonyms().clear();
            target.getSynonyms().addAll(source.getSynonyms());
        }
    }

    /**
     * This method will copy basic properties and parent/children from a ontology term source to a ontology term target
     * @param source : the cv term source to copy from
     * @param target : the cv term target to copy to
     */
    public static void copyAndOverrideOntologyTermProperties(OntologyTerm source, OntologyTerm target){
        if (source != null && target != null){
            copyAndOverrideCvTermProperties(source, target);
            target.getParents().clear();
            target.getParents().addAll(source.getParents());
            target.getChildren().clear();
            target.getChildren().addAll(source.getChildren());
            target.setDefinition(source.getDefinition());
        }
    }

    /**
     * This method will copy basic properties and source specific properties from a source to a target
     * @param source : the source to copy from
     * @param target : the source target to copy to
     */
    public static void copyAndOverrideSourceProperties(Source source, Source target){
        if (source != null && target != null){
            copyAndOverrideCvTermProperties(source, target);
            target.setPublication(source.getPublication());
        }
    }
}
