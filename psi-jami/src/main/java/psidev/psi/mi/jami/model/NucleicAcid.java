package psidev.psi.mi.jami.model;

/**
 * Polymers of nucleotides which can interact with other molecules
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface NucleicAcid extends Interactor {

    public static final String NULCEIC_ACID="nucleic acid";
    public static final String NULCEIC_ACID_MI="MI:0318";

    /**
     * The unique DDBJ/EMBL/GemBank identifier which identifies the nucleic acid.
     * It can be null if it is not known and in such a case, the sequence should be provided.
     * It is a shortcut which should point to the first DDBJ/EMBL/GenBank identifier in the collection of identifiers.
     * @return the DDBJ/EMBL/GemBank identifier
     */
    public String getDdbjEmblGenbank();

    /**
     * Sets the DDBJ/EMBL/GemBank identifier.
     * It will remove the old DDBJ/EMBL/GemBank identifier from the collection of identifiers and replace it
     * with the new DDBJ/EMBL/GemBank identifier. If the new DDBJ/EMBL/GemBank identifier is null, all the existing DDBJ/EMBL/GemBank identifiers will be removed from the
     * collection of identifiers
     * @param id : DDBJ/EMBL/GemBank identifier
     */
    public void setDdbjEmblGenbank(String id);

    /**
     * The unique Refseq identifier which identifies the nucleic acid.
     * It can be null if it is not known and in such a case, the sequence should be provided.
     * It is a shortcut which should point to the first refseq identifier in the collection of identifiers.
     * @return the DDBJ/EMBL/GemBank identifier
     */
    public String getRefseq();

    /**
     * Sets the Refseq identifier.
     * It will remove the old refseq identifier from the collection of identifiers and replace it
     * with the new refseq identifier. If the new refseq identifier is null, all the existing refseq identifiers will be removed from the
     * collection of identifiers
     * @param id : Refseq identifier
     */
    public void setRefseq(String id);

    /**
     * The sequence of nucleotides for this nucleic acid
     * @return the sequence
     */
    public String getSequence();

    /**
     * Sets the sequence
     * @param sequence : the sequence
     */
    public void setSequence(String sequence);
}
