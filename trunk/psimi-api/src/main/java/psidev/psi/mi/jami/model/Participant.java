package psidev.psi.mi.jami.model;

import java.util.Collection;
import java.util.Set;

/**
 * Participant of an experimental interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Participant {

    public Interactor getInteractor();
    public void setInteractor(Interactor interactor);

    public CvTerm getBiologicalRole();
    public void setBiologicalRole(CvTerm bioRole);

    public CvTerm getExperimentalRole();
    public void setExperimentalRole(CvTerm expRole);

    public Set<Xref> getXrefs();

    public Set<Annotation> getAnnotations();

    public Set<CvTerm> getIdentificationMethods();

    public Set<CvTerm> getExperimentalPreparations();

    public Collection<Feature> getFeatures();

    public Organism getExpressedInOrganism();
    public void setExpressedInOrganism(Organism organism);

    public Set<Confidence> getConfidences();

    public Set<Parameter> getParameters();

    public int getStoichiometry();

    public Interactor getExperimentalInteractor();
    public void setExperimentalInteractor(Interactor interactor);
}
