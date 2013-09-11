package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.OrganismChangeListener;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Organism;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just log organism change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class OrganismChangeLogger implements OrganismChangeListener {

    private static final Logger organismChangeListener = Logger.getLogger("OrganismChangeLogger");

    public void onCommonNameUpdate(Organism organism, String oldCommonName) {
        if (oldCommonName == null){
            organismChangeListener.log(Level.INFO, "The common name has been initialised for the organism " + organism.toString());
        }
        else if (organism.getCommonName() == null){
            organismChangeListener.log(Level.INFO, "The common name has been reset for the organism " + organism.toString());
        }
        else {
            organismChangeListener.log(Level.INFO, "The common name "+oldCommonName+" has been updated with " + organism.getCommonName() + " in the organism " + organism.toString());
        }
    }

    public void onScientificNameUpdate(Organism organism, String oldScientificName) {
        if (oldScientificName == null){
            organismChangeListener.log(Level.INFO, "The scientific name has been initialised for the organism " + organism.toString());
        }
        else if (organism.getScientificName() == null){
            organismChangeListener.log(Level.INFO, "The scientific name has been reset for the organism " + organism.toString());
        }
        else {
            organismChangeListener.log(Level.INFO, "The scientific name "+oldScientificName+" has been updated with " + organism.getScientificName() + " in the organism " + organism.toString());
        }
    }

    public void onTaxidUpdate(Organism organism, String oldTaxid) {
        organismChangeListener.log(Level.INFO, "The taxid "+oldTaxid+" has been updated with " + organism.getTaxId() + " in the organism " + organism.toString());
    }

    public void onAddedAlias(Organism organism, Alias added) {
        organismChangeListener.log(Level.INFO, "The alias "+added.toString()+" has been added to the organism " + organism.toString());
    }

    public void onRemovedAlias(Organism organism, Alias removed) {
        organismChangeListener.log(Level.INFO, "The alias "+removed.toString()+" has been removed from the organism " + organism.toString());
    }
}
