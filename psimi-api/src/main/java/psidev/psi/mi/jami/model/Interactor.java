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
     * @return the alternative identifier
     */
    public Set<ExternalIdentifier> getAlternativeIdentifiers();

    /**
     * Set of checksums computed for this interactor.
     * The set cannot be null so when an interactor does not have a chacksum, the method should return an empty set
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
     * The set cannot be null and if the interactor does not have any annotations, it should return an empty set.
     * @return the annotations
     */
    public Set<Annotation> getAnnotations();

    /**
     *
     * @return
     */
    public Set<Alias> getAliases();

    public Organism getOrganism();
    public void setOrganism(Organism organism);

    public CvTerm getType();
    public void setType(CvTerm type);
}
