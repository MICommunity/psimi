package psidev.psi.mi.jami.model;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Interaction involving one to several molecules
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Interaction {

    public String getShortName();
    public void setShortName(String name);

    public String getFullName();
    public void setFullName(String name);

    public Set<Xref> getXrefs();

    public Set<Annotation> getAnnotations();

    public CvTerm getType();
    public void setType(CvTerm term);

    public Collection<ExperimentalParticipant> getParticipants();

    public Set<Parameter> getParameters();

    public Experiment getExperiment();
    public void setExperiment(Experiment experiment);

    public Source getSource();
    public void setSource(Source source);

    public String getAvailability();
    public void setAvailability(String availability);

    public Set<Confidence> getConfidences();

    public boolean isNegative();
    public void setNegative(boolean negative);

    public boolean isModelled();
    public void setModelled(boolean modelled);

    public boolean isInferred();
    public void setInferred(boolean inferred);

    public Date getUpdatedDate();
    public void setUpdatedDate(Date updated);
}
