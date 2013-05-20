package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * A modelled confidence is a confidence for a modelled interaction.
 *
 * It can be computed from different experiments
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/02/13</pre>
 */

public interface ModelledConfidence extends Confidence {

    public Collection<Experiment> getExperiments();
}
