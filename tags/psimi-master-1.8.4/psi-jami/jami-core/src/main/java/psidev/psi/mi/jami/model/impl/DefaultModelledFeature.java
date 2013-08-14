package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Default implementation for ModelledFeature
 *
 * Notes: The equals and hashcode methods have NOT been overridden because the ModelledFeature object is a complex object.
 * To compare ModelledFeature objects, you can use some comparators provided by default:
 * - DefaultModelledFeatureComparator
 * - UnambiguousModelledFeatureComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultModelledFeature extends AbstractFeature<ModelledParticipant, ModelledFeature> implements ModelledFeature {

    public DefaultModelledFeature(ModelledParticipant participant) {
        super(CvTermUtils.createBiologicalFeatureType());
        setParticipant(participant);
    }

    public DefaultModelledFeature(ModelledParticipant participant, String shortName, String fullName) {
        super(shortName, fullName, CvTermUtils.createBiologicalFeatureType());
        setParticipant(participant);
    }

    public DefaultModelledFeature(ModelledParticipant participant, CvTerm type) {
        super(type);
        setParticipant(participant);
    }

    public DefaultModelledFeature(ModelledParticipant participant, String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
        setParticipant(participant);
    }

    public DefaultModelledFeature() {
        super(CvTermUtils.createBiologicalFeatureType());
    }

    public DefaultModelledFeature(String shortName, String fullName) {
        super(shortName, fullName, CvTermUtils.createBiologicalFeatureType());
    }

    public DefaultModelledFeature(CvTerm type) {
        super(type);
    }

    public DefaultModelledFeature(String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
    }
}
