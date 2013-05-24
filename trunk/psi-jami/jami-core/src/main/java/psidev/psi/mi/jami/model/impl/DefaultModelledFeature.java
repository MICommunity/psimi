package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousModelledFeaturecomparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for ModelledFeature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultModelledFeature extends DefaultFeature implements ModelledFeature {

    private ModelledParticipant modelledParticipant;
    private Collection<ModelledFeature> bindingFeatures;

    public DefaultModelledFeature(ModelledParticipant participant) {
        super(CvTermUtils.createBiologicalFeatureType());
        this.modelledParticipant = participant;
    }

    public DefaultModelledFeature(ModelledParticipant participant, String shortName, String fullName) {
        super(shortName, fullName, CvTermUtils.createBiologicalFeatureType());
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

    protected void initialiseBindingFeatures(){
        this.bindingFeatures = new ArrayList<ModelledFeature>();
    }

    protected void initialiseBindingFeaturesWith(Collection<ModelledFeature> features){
        if (features == null){
            this.bindingFeatures = Collections.EMPTY_LIST;
        }
        else {
            this.bindingFeatures = features;
        }
    }

    public ModelledParticipant getModelledParticipant() {
        return this.modelledParticipant;
    }

    public void setModelledParticipant(ModelledParticipant participant) {
        this.modelledParticipant = participant;
    }

    public void setModelledParticipantAndAddFeature(ModelledParticipant participant) {
        if (participant != null){
            this.modelledParticipant.addModelledFeature(this);
        }
        else {
            this.modelledParticipant.removeModelledFeature(this);
        }
    }

    public Collection<ModelledFeature> getLinkedModelledFeatures() {
        if(bindingFeatures == null){
            initialiseBindingFeatures();
        }
        return this.bindingFeatures;
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
