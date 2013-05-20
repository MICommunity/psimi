package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * A modelled parameter is a parameter for a modelled interaction.
 *
 * It can be extracted from different experiments.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/02/13</pre>
 */

public interface ModelledParameter extends Parameter {

    public Collection<Experiment> getExperiments();
}
