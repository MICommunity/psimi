package psidev.psi.mi.jami.model;

import java.util.Set;

/**
 * A controlled vocabulary term.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/12</pre>
 */

public interface CvTerm {

    public String getShortName();
    public void setShortName(String name);

    public String getFullName();
    public void setFullName(String name);

    public String getDefinition();
    public void setDefinition(String def);

    public ExternalIdentifier getIdentifier();
    public void setIdentifier(ExternalIdentifier identifier);

    public Set<Xref> getXrefs();

    public Set<Annotation> getAnnotations();

    public Set<Alias> getAliases();

}
