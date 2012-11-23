package psidev.psi.mi.jami.model;

import java.util.Set;

/**
 * A participant's feature is defined by shortname, fullName, xrefs, annotations aliases, feature type and ranges.
 * It can also have a feature detection method
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Feature {

    public String getShortName();
    public void setShortName(String name);

    public String getFullName();
    public void setFullName(String name);

    public Set<Xref> getXrefs();

    public Set<Annotation> getAnnotations();

    public CvTerm getType();
    public void setType(CvTerm type);

    public CvTerm getDetectionMethod();
    public void setDetectionMethod(CvTerm method);

    public Set<Range> getRanges();

    public Participant getParticipant();
    public void setParticipant(Participant participant);
}
