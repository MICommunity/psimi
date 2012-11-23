package psidev.psi.mi.jami.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * A scientific publication which has been curated by an interaction database.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Publication {

    public ExternalIdentifier getIdentifier();
    public void setIdentifier(ExternalIdentifier identifier);

    public String getImexId();
    public void setImexId(String identifier);

    public String getTitle();
    public void setTitle(String title);

    public String getJournal();
    public void setJournal(String journal);

    public int getYear();
    public int setYear(int year);

    public List<String> getAuthors();

    public Set<Xref> getXrefs();

    public Set<Annotation> getAnnotations();

    public Set<Alias> getAliases();

    public Collection<Experiment> getExperiments();
}
