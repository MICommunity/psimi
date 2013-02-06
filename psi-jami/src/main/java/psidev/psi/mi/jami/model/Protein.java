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
     * It is a shortcut which should point to the first uniprotkb identifier in the collection of identifiers.
     * Ex: P12345, P12345-1
     * @return the uniprot AC
     */
    public String getUniprotkb();

    /**
     * Sets the uniprot accession for this protein.
     * It will remove the old uniprotkb identifier from the collection of identifiers and replace it
     * with the new uniprokb identifier. If the new uniprotkb identifier is null, all the existing uniprotkb identifiers will be removed from the
     * collection of identifiers
     * @param ac : the uniprot accession
     */
    public void setUniprotkb(String ac);

    /**
     * The unique refseq identifier which identifies the protein and its sequence.
     * It can be null if the protein does not have a unique refseq identifier and in such a case, all the refseq identifiers should go
     * in the alternative identifiers set for this interactor.
     * It is a shortcut which should point to the first refseq identifier in the collection of identifiers.
     * Ex: NP_001065289.1
     * @return the refseq identifier
     */
    public String getRefseq();

    /**
     * Sets the unique refseq identifier for this protein.
     * It will remove the old refseq identifier from the collection of identifiers and replace it
     * with the new refseq identifier. If the new refseq identifier is null, all the existing refseq identifiers will be removed from the
     * collection of identifiers
     * @param ac : refseq identifier
     */
    public void setRefseq(String ac);

    /**
     * The gene name of a protein.
     * It can be null if it is not known
     * It is a shortcut which should point to the first gene name in the collection of aliases.
     * Ex: BRCA2
     * @return the gene name
     */
    public String getGeneName();

    /**
     * Sets the gene name.
     * It will remove the old gene name from the collection of aliases and replace it
     * with the new gene name. If the new gene name is null, all the existing gene names will be removed from the
     * collection of aliases
     * @param name : gene name
     */
    public void setGeneName(String name);

    /**
     * The rogid for this protein which can be null if the protein does not have a sequence.
     * This checksum is based on the sequence and source organism of a protein
     * It is a shortcut which should point to the first rogid identifier in the collection of checksums.
     * Ex: u1FCes02jPb3CGRj1aDkzpbSiuI9606
     * @return the rogid
     */
    public String getRogid();

    /**
     * Sets the rogid.
     * It will remove the old rogid from the collection of checksums and replace it
     * with the new rogid. If the new rogid is null, all the existing rogid will be removed from the
     * collection of checksums
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
