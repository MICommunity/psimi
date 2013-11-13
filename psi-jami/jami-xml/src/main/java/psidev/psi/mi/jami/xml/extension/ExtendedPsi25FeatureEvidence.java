package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.ExperimentalEntity;
import psidev.psi.mi.jami.model.FeatureEvidence;

import java.util.List;

/**
 * PSI-XML 2.5 extension of a feature evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public interface ExtendedPsi25FeatureEvidence extends ExtendedPsi25Feature<ExperimentalEntity, FeatureEvidence>,FeatureEvidence{

    public List<Experiment> getExperiments();
}
