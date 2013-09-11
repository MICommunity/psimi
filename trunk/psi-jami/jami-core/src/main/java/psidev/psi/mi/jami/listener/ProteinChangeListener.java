package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Protein;

/**
 * This listener listen to Protein changes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public interface ProteinChangeListener extends InteractorChangeListener<Protein>{

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
}
