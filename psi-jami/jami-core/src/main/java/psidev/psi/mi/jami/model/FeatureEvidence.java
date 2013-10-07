package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * The form of a molecule that was actually used to experimentally demonstrate the interaction, that may differ
 * from the sequence described by the identifying accession number.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/12/12</pre>
 */

public interface FeatureEvidence extends Feature<ExperimentalEntity, FeatureEvidence> {

    /**
     * The collection of feature detection methods. Each feature detectionMethod is a controlled vocabulary term.
     * The collection cannot be null. If the featureEvidence does not have any detection methods, it should return an empty collection.
     * Ex: autoradiography, predetermined feature, ...
     * @return the collection of detection methods for this feature
     */
    public <C extends CvTerm> Collection<C> getDetectionMethods();
}
