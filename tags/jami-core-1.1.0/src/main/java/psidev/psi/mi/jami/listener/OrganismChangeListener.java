package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;

/**
 * This listener listen to Organism changes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public interface OrganismChangeListener extends AliasesChangeListener<Organism> {

    /**
     * Listen to the event where the commonName of an organism has been changed.
     * @param organism : updated organism
     * @param oldCommonName : old common name
     */
    public void onCommonNameUpdate(Organism organism, String oldCommonName);

    /**
     * Listen to the event where the scientificName of an organism has been changed.
     * If oldScientificName is null, it means that the scientificName of the organism has been initialised.
     * If the current scientificName of the organism is null, it means that the scientificName has been reset
     * @param organism : updated organism
     * @param oldScientificName : old scientific name
     */
    public void onScientificNameUpdate(Organism organism, String oldScientificName);

    /**
     * Listen to the event where the taxid of an organism has been changed.
     * @param organism : updated organism
     * @param oldTaxid : old taxid
     */
    public void onTaxidUpdate(Organism organism, String oldTaxid);

    /**
     * Listen to the event where the cell type has been initialised.
     * This event happens when an organism has a new cell type
     * @param organism        The organism which has changed.
     */
    public void onCellTypeUpdate(Organism organism, CvTerm oldType);

    /**
     * Listen to the event where the organism tissue has been initialised.
     * This event happens when a organism has a new tissue
     * @param organism        The organism which has changed.
     */
    public void onTissueUpdate(Organism organism, CvTerm oldType);

    /**
     * Listen to the event where the organism compartment has been initialised.
     * This event happens when a organism has a new compartment
     * @param organism        The organism which has changed.
     */
    public void onCompartmentUpdate(Organism organism, CvTerm oldType);
}
