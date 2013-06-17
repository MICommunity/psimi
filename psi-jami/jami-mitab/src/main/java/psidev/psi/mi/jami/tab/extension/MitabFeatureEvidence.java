package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A MITAB feature evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/06/13</pre>
 */

public class MitabFeatureEvidence extends MitabFeature implements FeatureEvidence {

    private Collection<CvTerm> detectionMethods;
    private ParticipantEvidence participantEvidence;
    private Collection<FeatureEvidence> bindingSiteEvidences;

    public MitabFeatureEvidence() {
        super();
    }

    public MitabFeatureEvidence(CvTerm type) {
        super(type);
    }

    public MitabFeatureEvidence(CvTerm type, String interpro) {
        super(type, interpro);
    }

    protected void initialiseBindingSiteEvidences(){
        this.bindingSiteEvidences = new ArrayList<FeatureEvidence>();
    }

    protected void initialiseDetectionMethods(){
        this.detectionMethods = new ArrayList<CvTerm>();
    }

    protected void initialiseBindingSiteEvidencesWith(Collection<FeatureEvidence> features){
        if (features == null){
            this.bindingSiteEvidences = Collections.EMPTY_LIST;
        }
        else {
            this.bindingSiteEvidences = features;
        }
    }

    protected void initialiseDetectionMethodsWith(Collection<CvTerm> methods){
        if (methods == null){
            this.detectionMethods = Collections.EMPTY_LIST;
        }
        else {
            this.detectionMethods = methods;
        }
    }

    public Collection<FeatureEvidence> getLinkedFeatureEvidences() {
        if(bindingSiteEvidences == null){
            initialiseBindingSiteEvidences();
        }
        return this.bindingSiteEvidences;
    }

    public Collection<CvTerm> getDetectionMethods() {
        if (detectionMethods == null){
            initialiseDetectionMethods();
        }
        return this.detectionMethods;
    }

    public ParticipantEvidence getParticipantEvidence() {
        return this.participantEvidence;
    }

    public void setParticipantEvidence(ParticipantEvidence participant) {
        this.participantEvidence = participant;
    }

    public void setParticipantEvidenceAndAddFeature(ParticipantEvidence participant) {
        if (this.participantEvidence != null){
            this.participantEvidence.removeFeatureEvidence(this);
        }
        if (participant != null){
            participant.addFeatureEvidence(this);
        }
    }
}
