package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousModelledFeaturecomparator;

/**
 * Default implementation for ModelledFeature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultModelledFeature extends DefaultFeature<ModelledFeature> implements ModelledFeature {

    private ModelledParticipant modelledParticipant;

    public DefaultModelledFeature(ModelledParticipant participant) {
        super();
        this.modelledParticipant = participant;
    }

    public DefaultModelledFeature(ModelledParticipant participant, String shortName, String fullName) {
        super(shortName, fullName);
        this.modelledParticipant = participant;
    }

    public DefaultModelledFeature(ModelledParticipant participant, CvTerm type) {
        super(type);
        this.modelledParticipant = participant;
    }

    public DefaultModelledFeature(ModelledParticipant participant, String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
        this.modelledParticipant = participant;
    }

    public DefaultModelledFeature() {
        super();
    }

    public DefaultModelledFeature(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public DefaultModelledFeature(CvTerm type) {
        super(type);
    }

    public DefaultModelledFeature(String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
    }

    public ModelledParticipant getModelledParticipant() {
        return this.modelledParticipant;
    }

    public void setModelledParticipant(ModelledParticipant participant) {
        this.modelledParticipant = participant;
    }

    public void setModelledParticipantAndAddFeature(ModelledParticipant participant) {
        if (participant != null){
            this.modelledParticipant.addFeature(this);
        }
        else {
            this.modelledParticipant = null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof ModelledFeature)){
            return false;
        }

        // use UnambiguousBiologicalFeature comparator for equals
        return UnambiguousModelledFeaturecomparator.areEquals(this, (ModelledFeature) o);
    }
}
