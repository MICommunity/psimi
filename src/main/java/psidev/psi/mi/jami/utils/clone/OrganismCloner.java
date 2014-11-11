package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.Organism;

/**
 * Utility class for cloning organisms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/02/13</pre>
 */

public class OrganismCloner {

    /***
     * This method will copy properties of organism source in organism target and will override all the other properties of Target organism.
     * @param source
     * @param target
     */
    public static void copyAndOverrideOrganismProperties(Organism source, Organism target){
        if (source != null && target != null){
            target.setCommonName(source.getCommonName());
            target.setScientificName(source.getScientificName());
            target.setCellType(source.getCellType());
            target.setTissue(source.getTissue());
            target.setCompartment(source.getCompartment());
            target.setTaxId(source.getTaxId());

            // copy collections
            target.getAliases().clear();
            target.getAliases().addAll(source.getAliases());
        }
    }
}
