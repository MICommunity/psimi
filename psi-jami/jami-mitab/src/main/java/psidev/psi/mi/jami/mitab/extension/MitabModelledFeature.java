package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;

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

    public MitabModelledFeature() {
        super();
    }

    public MitabModelledFeature(CvTerm type) {
        super(type);
    }

    public MitabModelledFeature(CvTerm type, String interpro) {
        super(type, interpro);
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
        return Collections.EMPTY_LIST;
    }
}
