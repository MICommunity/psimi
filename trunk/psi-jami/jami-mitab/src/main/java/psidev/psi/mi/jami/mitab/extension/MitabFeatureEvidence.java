package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;

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

    private ParticipantEvidence participantEvidence;

    public MitabFeatureEvidence() {
        super();
    }

    public MitabFeatureEvidence(CvTerm type) {
        super(type);
    }

    public MitabFeatureEvidence(CvTerm type, String interpro) {
        super(type, interpro);
    }

    public Collection<CvTerm> getDetectionMethods() {
        return Collections.EMPTY_LIST;
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

    public Collection<FeatureEvidence> getLinkedFeatureEvidences() {
        return Collections.EMPTY_LIST;
    }
}
