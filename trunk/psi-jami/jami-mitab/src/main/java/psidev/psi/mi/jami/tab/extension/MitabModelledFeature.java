package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A MITAB modelled feature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/06/13</pre>
 */

public class MitabModelledFeature extends MitabFeature implements ModelledFeature{

    private ModelledParticipant modelledParticipant;
    private Collection<ModelledFeature> bindingFeatures;

    public MitabModelledFeature() {
        super();
    }

    public MitabModelledFeature(CvTerm type) {
        super(type);
    }

    public MitabModelledFeature(CvTerm type, String interpro) {
        super(type, interpro);
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
        if (this.modelledParticipant != null){
            this.modelledParticipant.removeModelledFeature(this);
        }

        if (participant != null){
            participant.addModelledFeature(this);
        }
    }

    public Collection<ModelledFeature> getLinkedModelledFeatures() {
        if(bindingFeatures == null){
            initialiseBindingFeatures();
        }
        return this.bindingFeatures;
    }
}
