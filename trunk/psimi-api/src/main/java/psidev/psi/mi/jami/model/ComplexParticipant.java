package psidev.psi.mi.jami.model;

import java.util.Collection;
import java.util.Set;

/**
 * Participant of a biological complex
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */
public interface ComplexParticipant {

    public Interactor getInteractor();
    public void setInteractor(Interactor interactor);

    public CvTerm getBiologicalRole();
    public void setBiologicalRole(CvTerm bioRole);

    public Set<Xref> getXrefs();

    public Set<Annotation> getAnnotations();

    public Collection<ComplexFeature> getFeatures();

    public Set<Parameter> getParameters();

    public int getStoichiometry();
}
