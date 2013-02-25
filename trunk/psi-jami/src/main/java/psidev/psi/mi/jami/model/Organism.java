package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * The organism is defined by a taxonomy identifier.
 * It can be used to decsribe the source organism of an interactor, the organism in which the experiment has been performed or
 * the organism where the participants of an interaction have been expressed in.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Organism {

    /**
     * The common name of an organism.
     * It can be null.
     * Ex: human, mouse, ...
     * @return the common name/mnemonic
     */
    public String getCommonName();

    /**
     * Set the common name
     * @param name : common name or mnemonic (or scientific name if no common names/mnemonic)
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setCommonName(String name);

    /**
     * The scientific name of the organism. It can be null
     * Ex: Homo sapiens, Mus musculus, ...
     * @return the scientific name
     */
    public String getScientificName();

    /**
     * Set the scientific name
     * @param name : scientific name
     */
    public void setScientificName(String name);

    /**
     * The taxonomy identifier of the organism. It should be :
     * - '-1' for 'in vitro'
     * - '-2' for 'chemical synthesis'
     * - '-3' for unknown
     * - '-4' for in vivo
     * - valid NCBI taxonomy identifier (Ex: 9606 for human)
     * @return the taxonomy identifier
     */
    public int getTaxId();

    /**
     * Sets the taxonomy identifier of the organism. It should be :
     * - '-1' for 'in vitro'
     * - '-2' for 'chemical synthesis'
     * - '-3' for unknown
     * - '-4' for in vivo
     * - valid NCBI taxonomy identifier (Ex: 9606 for human)
     * @throws IllegalArgumentException if taxid is not valid
     *
     */
    public void setTaxId(int id);

    /**
     * The other names of the organism.
     * It cannot be null and should return an emtpy Collection if no aliases are attached to this organism
     * Ex: Mus muscaris, transgenic mice and house mouse are mouse synonyms/aliases.
     * @return the aliases
     */
    public Collection<Alias> getAliases();

    /**
     * Cell type of the organism.
     * It can be null for the interactor source organisms
     * Ex: Human RCH_ACV -cALL ( B cell precursor) cell line
     * @return the celltype
     */
    public CvTerm getCellType();

    /**
     * Sets the cell type
     * @param cellType : cell type
     */
    public void setCellType(CvTerm cellType);

    /**
     * The subcellular compartment of the organism.
     * It can be null for the interactor source organisms
     * Ex: nuclear
     * @return the compartment
     */
    public CvTerm getCompartment();

    /**
     * Sets the compartment.
     * @param compartment : the compartment
     */
    public void setCompartment(CvTerm compartment);

    /**
     * The source tissue.
     * It can be null for the interacator source organisms
     * Ex: non pigmented ciliary epithelium
     * @return the tissue
     */
    public CvTerm getTissue();

    /**
     * Sets the tissue
     * @param tissue : tissue
     */
    public void setTissue(CvTerm tissue);
}
