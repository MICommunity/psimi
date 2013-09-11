package psidev.psi.mi.jami.listener;

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
     * @param organism
     * @param oldCommonName
     */
    public void onCommonNameUpdate(Organism organism, String oldCommonName);

    /**
     * Listen to the event where the scientificName of an organism has been changed.
     * If oldScientificName is null, it means that the scientificName of the organism has been initialised.
     * If the current scientificName of the organism is null, it means that the scientificName has been reset
     * @param organism
     * @param oldScientificName
     */
    public void onScientificNameUpdate(Organism organism, String oldScientificName);

    /**
     * Listen to the event where the taxid of an organism has been changed.
     * @param organism
     * @param oldTaxid
     */
    public void onTaxidUpdate(Organism organism, String oldTaxid);
}
