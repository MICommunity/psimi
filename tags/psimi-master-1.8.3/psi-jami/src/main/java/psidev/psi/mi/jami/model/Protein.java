package psidev.psi.mi.jami.model;

/**
 * Polymer of amino acids which can interact with other molecules.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Protein extends Interactor {

    /**
     * The unique uniprot Accession which identifies the protein.
     * It can be null if the protein is not in the UniprotKb database and the sequence should be provided in such a case.
     * Ex: P12345, P12345-1
     * @return the uniprot AC
     */
    public String getUniprotkb();

    /**
     * Sets the uniprot accession for this protein.
     * @param ac : the uniprot accession
     */
    public void setUniprotkb(String ac);

    /**
     * The unique refseq identifier which identifies the protein and its sequence.
     * It can be null if the protein does not have a unique refseq identifier and in such a case, all the refseq identifiers should go
     * in the alternative identifiers set for this interactor.
     * Ex: NP_001065289.1
     * @return the refseq identifier
     */
    public String getRefseq();

    /**
     * Sets the unique refseq identifier for this protein.
     * @param ac : refseq identifier
     */
    public void setRefseq(String ac);

    /**
     * The gene name of a protein.
     * It can be null if it is not known
     * Ex: BRCA2
     * @return the gene name
     */
    public String getGeneName();

    /**
     * Sets the gene name
     * @param name : gene name
     */
    public void setGeneName(String name);

    /**
     * The rogid for this protein which can be null if the protein does not have a sequence.
     * This checksum is based on the sequence and source organism of a protein
     * Ex: u1FCes02jPb3CGRj1aDkzpbSiuI9606
     * @return the rogid
     */
    public String getRogid();

    /**
     * Sets the rogid
     * @param rogid : rogid
     */
    public void setRogid(String rogid);

    /**
     * The sequence of amino acids
     * Ex: MGDVEKGKKI
     * @return the sequence
     */
    public String getSequence();

    /**
     * Sets the sequence.
     * @param sequence : sequence of amino acids
     */
    public void setSequence(String sequence);
}
