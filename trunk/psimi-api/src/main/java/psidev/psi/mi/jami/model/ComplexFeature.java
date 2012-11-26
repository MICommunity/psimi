package psidev.psi.mi.jami.model;

import java.util.Collection;
import java.util.Set;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface ComplexFeature{

    public String getShortName();
    public void setShortName(String name);

    public String getFullName();
    public void setFullName(String name);

    public Set<Xref> getXrefs();

    public Set<Annotation> getAnnotations();

    public CvTerm getType();
    public void setType(CvTerm type);

    public Set<Range> getRanges();

    public Participant getParticipant();
    public void setParticipant(Participant participant);

    public Collection<Feature> getBindingSites();
}
