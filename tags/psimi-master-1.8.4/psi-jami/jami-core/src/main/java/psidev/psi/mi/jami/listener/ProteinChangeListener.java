package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;

import java.util.EventListener;

/**
 * This listener listen to Protein changes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public interface ProteinChangeListener extends EventListener{

    /**
     * Listen to the event where the uniprotkb of a protein has been changed.
     * If oldUniprot is null, it means that a uniprot has been added to the protein.
     * If the uniprotkb of the protein is null, it means that the uniprot of the protein has been removed
     * @param protein
     * @param oldUniprot
     */
    public void onUniprotKbUpdate(Protein protein, String oldUniprot);

    /**
     * Listen to the event where the refseq of a protein has been changed.
     * If oldRefseq is null, it means that a refseq has been added to the protein.
     * If the refseq of the protein is null, it means that the refseq of the protein has been removed
     * @param protein
     * @param oldRefseq
     */
    public void onRefseqUpdate(Protein protein, String oldRefseq);

    /**
     * Listen to the event where the gene name of a protein has been changed.
     * If oldGeneName is null, it means that a gene name has been added to the protein.
     * If the gene name of the protein is null, it means that the gene name of the protein has been removed
     * @param protein
     * @param oldGeneName
     */
    public void onGeneNameUpdate(Protein protein, String oldGeneName);

    /**
     * Listen to the event where the rogid of a protein has been changed.
     * If oldRogid is null, it means that a rogid has been added to the protein.
     * If the rogid of the protein is null, it means that the rogid of the protein has been removed
     * @param protein
     * @param oldRogid
     */
    public void onRogidUpdate(Protein protein, String oldRogid);

    /**
     * Listen to the event where the sequence of a protein has been changed.
     * If oldSequence is null, it means that the sequence has been initialised.
     * If the sequence of the protein is null, it means that the sequence of the protein has been reset
     * @param protein
     * @param oldSequence
     */
    public void onSequenceUpdate(Protein protein, String oldSequence);

    /**
     * Listen to the event where the shortName of a protein has been changed.
     * @param protein
     * @param oldShortName
     */
    public void onShortNameUpdate(Protein protein, String oldShortName);

    /**
     * Listen to the event where the fullName of a protein has been changed.
     * If oldFullName is null, it means that the fullName of the protein has been initialised.
     * If the current fullName of the protein is null, it means that the fullName has been reset
     * @param protein
     * @param oldFullName
     */
    public void onFullNameUpdate(Protein protein, String oldFullName);

    /**
     * Listen to the event where the interactor type of a protein has been initialised.
     * This event happens when a protein does not have any interactor types
     * @param protein : updated protein
     */
    public void onAddedInteractorType(Protein protein);

    /**
     * Listen to the event where the organism of a protein has been initialised.
     * This event happens when a protein does not have any organisms
     * @param protein : updated protein
     */
    public void onAddedOrganism(Protein protein);

    /**
     * Listen to the event where an identifier has been added to the protein identifiers.
     * @param protein
     * @param added
     */
    public void onAddedIdentifier(Protein protein, Xref added);

    /**
     * Listen to the event where an identifier has been removed from the protein identifiers.
     * @param protein
     * @param removed
     */
    public void onRemovedIdentifier(Protein protein, Xref removed);

    /**
     * Listen to the event where a xref has been added to the protein xrefs.
     * @param protein
     * @param added
     */
    public void onAddedXref(Protein protein, Xref added);

    /**
     * Listen to the event where a xref has been removed from the protein xrefs.
     * @param protein
     * @param removed
     */
    public void onRemovedXref(Protein protein, Xref removed);

    /**
     * Listen to the event where an alias has been added to the protein aliases.
     * @param protein
     * @param added
     */
    public void onAddedAlias(Protein protein, Alias added);

    /**
     * Listen to the event where an alias has been removed from the protein aliases.
     * @param protein
     * @param removed
     */
    public void onRemovedAlias(Protein protein, Alias removed);

    /**
     * Listen to the event where a checksum has been added to the protein checksums.
     * @param protein
     * @param added
     */
    public void onAddedChecksum(Protein protein, Checksum added);

    /**
     * Listen to the event where a checksum has been removed from the protein checksums.
     * @param protein
     * @param removed
     */
    public void onRemovedChecksum(Protein protein, Checksum removed);
}
