package psidev.psi.mi.jami.model;

import java.util.Set;

/**
 * Molecule that interacts
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Interactor {

    public String getShortName();
    public void setShortName(String name);

    public String getFullName();
    public void setFullName(String name);

    public ExternalIdentifier getUniqueIdentifier();
    public void setUniqueIdentifier(ExternalIdentifier identifier);

    public Set<ExternalIdentifier> getAlternativeIdentifiers();

    public Set<Checksum> getChecksums();

    public Set<Xref> getXrefs();

    public Set<Annotation> getAnnotations();

    public Set<Alias> getAliases();

    public Organism getOrganism();
    public void setOrganism(Organism organism);

    public CvTerm getType();
    public void setType(CvTerm type);
}
