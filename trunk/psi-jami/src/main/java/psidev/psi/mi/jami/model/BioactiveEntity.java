package psidev.psi.mi.jami.model;

/**
 * Molecules showing activity in a living system and which can interact with other molecules
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface BioactiveEntity extends Interactor {

    /**
     * The CHEBI accession which identifies the bioactive entity.
     * It is a shortcut which should point to the first chebi identifier in the list of identifiers.
     * It can be null
     * Ex: CHEBI:4991
     * @return
     */
    public String getChebi();

    /**
     * Sets the CHEBI identifier. It will remove the old chebi identifier from the list of identifiers and replace it
     * with the new chebi identifier. If the new chebi identifier is null, all the existing chebi identifiers will be removed from the
     * list of identifiers
     * @param id : CHEBI identifier
     */
    public void setChebi(String id);

    /**
     * The Simplified Molecular Input Line Entry System.
     * It is a shortcut which should point to the first smile in the list of checksums.
     * It can be null
     * Ex: OC1C[C@]2(O)CC(O)O[Fe]34(O1)(OC(O)C[C@](O)(CC(O)O3)C(O)O4)OC2O
     * @return the smile
     */
    public String getSmile();

    /**
     * Sets the smile of this small molecule. It will remove the old smile from the list of checksums and replace it
     * with the new smile. If the new smile is null, all the existing smiles will be removed from the
     * list of checksums
     * @param smile: the smile
     */
    public void setSmile(String smile);

    /**
     * The standard InChI Key for this chemical compound
     * It is a shortcut which should point to the first standard Inchi key in the list of checksums.
     * It can be null.
     * Ex: ISOHCBXAFHMIHK-UHFFFAOYSA-N
     * @return the standard InChI key
     */
    public String getStandardInchiKey();

    /**
     * Sets the standard InChI key . It will remove the old standard inchi key from the list of checksums and replace it
     * with the new standard inchi key. If the new standard inchi key is null, all the existing standard inchi keys will be removed from the
     * list of checksums
     * @param key : the standard inchi key
     */
    public void setStandardInchiKey(String key);

    /**
     * The standard InChI for this chemical compound.
     * It is a shortcut which should point to the first standard Inchi in the list of checksums.
     * It can be null.
     * Ex: InChI=1S/2C6H11O7.Fe/c2*7-3(8)1-6(13,5(11)12)2-4(9)10;/h2*3-5,7,9,11,13H,1-2H2;/q2*-3;+6
     * @return the standard InChI
     */
    public String getStandardInchi();

    /**
     * Sets the standard InChI. It will remove the old standard inchi from the list of checksums and replace it
     * with the new standard inchi. If the new standard is null, all the existing standard inchi will be removed from the
     * list of checksums
     * @param inchi: the standard InChI
     */
    public void setStandardInchi(String inchi);
}
