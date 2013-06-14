package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.ProteinChangeListener;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will just protein change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class ProteinChangeLogger implements ProteinChangeListener {

    private static final Logger proteinChangeLogger = Logger.getLogger("ProteinChangeLogger");

    public void onUniprotKbUpdate(Protein protein, String oldUniprot) {

        if (oldUniprot == null){
            proteinChangeLogger.log(Level.INFO, "The uniprotkb " + protein.getUniprotkb() + " has been added to the protein " + protein.toString());
        }
        else if (protein.getUniprotkb() == null){
            proteinChangeLogger.log(Level.INFO, "The uniprotkb "+ oldUniprot+ " has been removed from the protein " + protein.toString());
        }
        else {
            proteinChangeLogger.log(Level.INFO, "The uniprotkb "+oldUniprot+" has been updated with " + protein.getUniprotkb() + " in the protein " + protein.toString());
        }
    }

    public void onRefseqUpdate(Protein protein, String oldRefseq) {
        if (oldRefseq == null){
            proteinChangeLogger.log(Level.INFO, "The refseq id " + protein.getRefseq() + " has been added to the protein " + protein.toString());
        }
        else if (protein.getRefseq() == null){
            proteinChangeLogger.log(Level.INFO, "The refseq id "+oldRefseq+" has been removed from the protein " + protein.toString());
        }
        else {
            proteinChangeLogger.log(Level.INFO, "The refseq id "+oldRefseq+" has been updated with " + protein.getRefseq() + " in the protein " + protein.toString());
        }
    }

    public void onGeneNameUpdate(Protein protein, String oldGeneName) {
        if (oldGeneName == null){
            proteinChangeLogger.log(Level.INFO, "The gene name " + protein.getGeneName() + " has been added to the protein " + protein.toString());
        }
        else if (protein.getGeneName() == null){
            proteinChangeLogger.log(Level.INFO, "The gene name "+oldGeneName+" has been removed from the protein " + protein.toString());
        }
        else {
            proteinChangeLogger.log(Level.INFO, "The gene name "+oldGeneName+" has been updated with " + protein.getGeneName() + " in the protein " + protein.toString());
        }
    }

    public void onRogidUpdate(Protein protein, String oldRogid) {
        if (oldRogid == null){
            proteinChangeLogger.log(Level.INFO, "The rogid " + protein.getRogid() + " has been added to the protein " + protein.toString());
        }
        else if (protein.getRogid() == null){
            proteinChangeLogger.log(Level.INFO, "The rogid "+oldRogid+" has been removed from the protein " + protein.toString());
        }
        else {
            proteinChangeLogger.log(Level.INFO, "The rogid "+oldRogid+" has been updated with " + protein.getRogid() + " in the protein " + protein.toString());
        }
    }

    public void onSequenceUpdate(Protein protein, String oldSequence) {
        if (oldSequence == null){
            proteinChangeLogger.log(Level.INFO, "The sequence has been initialised for the protein " + protein.toString());
        }
        else if (protein.getSequence() == null){
            proteinChangeLogger.log(Level.INFO, "The sequence has been reset for the protein " + protein.toString());
        }
        else {
            proteinChangeLogger.log(Level.INFO, "The sequence "+oldSequence+" has been updated with " + protein.getSequence() + " in the protein " + protein.toString());
        }
    }

    public void onShortNameUpdate(Protein protein, String oldShortName) {
        proteinChangeLogger.log(Level.INFO, "The short name "+oldShortName+" has been updated with " + protein.getShortName() + " in the protein " + protein.toString());
    }

    public void onFullNameUpdate(Protein protein, String oldFullName) {
        if (oldFullName == null){
            proteinChangeLogger.log(Level.INFO, "The full name has been initialised for the protein " + protein.toString());
        }
        else if (protein.getFullName() == null){
            proteinChangeLogger.log(Level.INFO, "The full name has been reset for the protein " + protein.toString());
        }
        else {
            proteinChangeLogger.log(Level.INFO, "The full name "+oldFullName+" has been updated with " + protein.getFullName() + " in the protein " + protein.toString());
        }
    }

    public void onAddedInteractorType(Protein protein) {
        proteinChangeLogger.log(Level.INFO, "The interactor type " + protein.getInteractorType().getShortName() + " has been added to the protein " + protein.toString());
    }

    public void onAddedOrganism(Protein protein) {
        proteinChangeLogger.log(Level.INFO, "The organism " + protein.getOrganism().getTaxId() + " has been added to the protein " + protein.toString());
    }

    public void onAddedIdentifier(Protein protein, Xref added) {
        proteinChangeLogger.log(Level.INFO, "The identifier " + added.toString() + " has been added to the protein " + protein.toString());
    }

    public void onRemovedIdentifier(Protein protein, Xref removed) {
        proteinChangeLogger.log(Level.INFO, "The identifier " + removed.toString() + " has been removed from the protein " + protein.toString());
    }

    public void onAddedXref(Protein protein, Xref added) {
        proteinChangeLogger.log(Level.INFO, "The xref " + added.toString() + " has been added to the protein " + protein.toString());
    }

    public void onRemovedXref(Protein protein, Xref removed) {
        proteinChangeLogger.log(Level.INFO, "The xref " + removed.toString() + " has been removed from the protein " + protein.toString());
    }

    public void onAddedAlias(Protein protein, Alias added) {
        proteinChangeLogger.log(Level.INFO, "The alias " + added.toString() + " has been added to the protein " + protein.toString());
    }

    public void onRemovedAlias(Protein protein, Alias removed) {
        proteinChangeLogger.log(Level.INFO, "The alias " + removed.toString() + " has been removed from the protein " + protein.toString());
    }

    public void onAddedChecksum(Protein protein, Checksum added) {
        proteinChangeLogger.log(Level.INFO, "The checksum " + added.toString() + " has been added to the protein " + protein.toString());
    }

    public void onRemovedChecksum(Protein protein, Checksum removed) {
        proteinChangeLogger.log(Level.INFO, "The checksum " + removed.toString() + " has been removed from the protein " + protein.toString());
    }
}
