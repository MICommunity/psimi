package psidev.psi.mi.jami.model;

import java.util.Set;

/**
 * Molecule or complex of molecules that interacts with other molecules/complexes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Interactor {

    /**
     * The short name of the interactor.
     * It cannot be null or empty.
     * Ex: brca2
     * @return the short name
     */
    public String getShortName();

    /**
     * Sets the short name of an interactor
     * @param name : short name
     * @throws IllegalArgumentException if name is null or empty
     */
    public void setShortName(String name);

    /**
     * The full name of the interactor.
     * It can be null
     * Ex: Breast cancer type 2 susceptibility protein
     * @return the full name
     */
    public String getFullName();

    /**
     * Sets the full name of the interactor
     * @param name : full name
     */
    public void setFullName(String name);

    /**
     * Unique external identifier for the interactor. It should be unambiguous.
     * The unique identifier can be null if the interactor is not registered in any external databases.
     * Ex: uniprotkb:P51587
     * @return the unique identifier
     */
    public ExternalIdentifier getUniqueIdentifier();

    /**
     * Sets the unique identifier for this interactor
     * @param identifier : unique identifier
     */
    public void setUniqueIdentifier(ExternalIdentifier identifier);

    /**
     * Alternative identifiers for this interactor from other databases.
     * The set cannot be null, when an interactor does not have any alternative identifiers, the method should return an empty set.
     * Ex: uniprotkb secondary accession O00183, ...
     * @return the alternative identifier
     */
    public Set<ExternalIdentifier> getAlternativeIdentifiers();

    /**
     * Set of checksums computed for this interactor.
     * The set cannot be null so when an interactor does not have a checksum, the method should return an empty set
     * Ex: rogid:u1FCes02jPb3CGRj1aDkzpbSiuI9606, standard Inchi key, ...
     * @return the set of checksums
     */
    public Set<Checksum> getChecksums();

    /**
     * Set of other xrefs that give more information about the interactor.
     * Ex: GO references to gives function/process/location information
     * @return other xrefs
     */
    public Set<Xref> getXrefs();

    /**
     * Set of annotations for an interactor.
     * The set cannot be null and if the interactor does not have any annotations, the method should return an empty set.
     * Ex: pharmacology, isoform-comment, etc.
     * @return the annotations
     */
    public Set<Annotation> getAnnotations();

    /**
     * Set of aliases for an interactor
     * The set cannot be null and if the interactor does not have any aliases, the method should return an empty set.
     * Ex: complex-synonym, author-assigned name, ...
     * @return the aliases
     */
    public Set<Alias> getAliases();

    /**
     * The original source organism for this interactor.
     * It can be null in case of chemical compounds/synthetic peptides
     * @return the organism
     */
    public Organism getOrganism();

    /**
     * Sets the source organism of this interactor
     * @param organism : source organism
     */
    public void setOrganism(Organism organism);

    /**
     * The molecule type of this interactor.
     * It is a controlled vocabulary term and cannot be null.
     * Ex: protein, gene, small molecule, ...
     * @return interactor type
     */
    public CvTerm getType();

    /**
     * Sets the molecule type for this interactor
     * @param type : molecule type
     * @throws IllegalArgumentException when the type is null
     */
    public void setType(CvTerm type);
}
