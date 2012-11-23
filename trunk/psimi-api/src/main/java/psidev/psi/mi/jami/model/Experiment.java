package psidev.psi.mi.jami.model;

import java.util.Set;

/**
 * Experimental details about an interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Experiment {

    public Publication getPublication();
    public void setPublication(Publication publication);

    public String getShortName();
    public void setShortName(String name);

    public String getFullName();
    public void setFullName(String name);

    public Set<Xref> getXrefs();

    public Set<Annotation> getAnnotations();

    public CvTerm getInteractionDetectionMethod();
    public void setInteractionDetectionMethod(CvTerm term);

    public Organism getHostOrganism();
    public void setHostOrganism(Organism organism);
}
