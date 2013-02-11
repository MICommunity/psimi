package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;

/**
 * Factory for experiment
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class ExperimentFactory {

    public static Experiment createUnknownBasicExperiment(){
        return new DefaultExperiment(PublicationFactory.createUnknownBasicPublication(), CvTermFactory.createMICvTerm(Experiment.UNSPECIFIED_METHOD, Experiment.UNSPECIFIED_METHOD_MI));
    }
}
