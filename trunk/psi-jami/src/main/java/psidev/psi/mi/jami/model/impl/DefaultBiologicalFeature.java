package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.BiologicalFeature;
import psidev.psi.mi.jami.model.BiologicalParticipant;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousBiologicalFeaturecomparator;

/**
 * Default implementation for BiologicalFeature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultBiologicalFeature extends DefaultFeature<BiologicalFeature, BiologicalParticipant> implements BiologicalFeature{
    public DefaultBiologicalFeature(BiologicalParticipant participant) {
        super(participant);
    }

    public DefaultBiologicalFeature(BiologicalParticipant participant, String shortName, String fullName) {
        super(participant, shortName, fullName);
    }

    public DefaultBiologicalFeature(BiologicalParticipant participant, CvTerm type) {
        super(participant, type);
    }

    public DefaultBiologicalFeature(BiologicalParticipant participant, String shortName, String fullName, CvTerm type) {
        super(participant, shortName, fullName, type);
    }

    public DefaultBiologicalFeature() {
        super();
    }

    public DefaultBiologicalFeature(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public DefaultBiologicalFeature(CvTerm type) {
        super(type);
    }

    public DefaultBiologicalFeature(String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof BiologicalFeature)){
            return false;
        }

        // use UnambiguousBiologicalFeature comparator for equals
        return UnambiguousBiologicalFeaturecomparator.areEquals(this, (BiologicalFeature) o);
    }
}
