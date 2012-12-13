package psidev.psi.mi.jami.model;

/**
 * Polymers of nucleotides which can interact with other molecules
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface NucleicAcid extends Interactor {

    /**
     * The unique DDBJ/EMBL/GemBank identifier which identifies the nucleic acid.
     * It can be null if it is not known and in such a case, the sequence should be provided.
     * @return the DDBJ/EMBL/GemBank identifier
     */
    public String getDdbjEmblGenbank();

    /**
     * Sets the DDBJ/EMBL/GemBank identifier
     * @param id : DDBJ/EMBL/GemBank identifier
     */
    public void setDdbjEmblGenbank(String id);

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
