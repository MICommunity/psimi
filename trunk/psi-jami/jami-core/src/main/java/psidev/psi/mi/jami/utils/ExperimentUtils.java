package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;

/**
 * Factory for experiment
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class ExperimentUtils {

    public static Experiment createUnknownBasicExperiment(){
        return new DefaultExperiment(PublicationUtils.createUnknownBasicPublication(), CvTermUtils.createMICvTerm(Experiment.UNSPECIFIED_METHOD, Experiment.UNSPECIFIED_METHOD_MI));
    }

    public static Experiment createExperimentWithoutPublication(){
        return new DefaultExperiment(null, CvTermUtils.createMICvTerm(Experiment.UNSPECIFIED_METHOD, Experiment.UNSPECIFIED_METHOD_MI));
    }
}
