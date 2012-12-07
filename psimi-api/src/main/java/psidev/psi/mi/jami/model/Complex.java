package psidev.psi.mi.jami.model;

import java.util.Collection;
import java.util.Set;

/**
 * An interactor composed of several interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Complex extends Interactor {

    public Collection<Experiment> getExperiments();

    public Collection<Component> getParticipants();

    public Set<Parameter> getParameters();
}
