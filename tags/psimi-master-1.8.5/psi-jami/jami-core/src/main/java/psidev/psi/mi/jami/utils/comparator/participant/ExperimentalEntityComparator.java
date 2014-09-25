package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ExperimentalEntity;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.utils.comparator.feature.FeatureCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.feature.FeatureEvidenceComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic Experimental entity comparator.
 *
 * It will compare the basic properties of an experimental entity using EntityBaseComparator and FeatureEvidenceComparator.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class ExperimentalEntityComparator implements Comparator<ExperimentalEntity> {

    protected EntityBaseComparator participantBaseComparator;
    protected FeatureCollectionComparator featureCollectionComparator;

    /**
     * Creates a new ParticipantEvidenceComparator
     * @param participantBaseComparator : the participant comparator required to compare basic properties of a participant
     *@param featureComparator : the comparator for features
     */
    public ExperimentalEntityComparator(EntityBaseComparator participantBaseComparator,
                                        FeatureEvidenceComparator featureComparator){
        if (participantBaseComparator == null){
            throw new IllegalArgumentException("The entity base comparator is required to compare basic participant properties. It cannot be null");
        }
        this.participantBaseComparator = participantBaseComparator;
        if (featureComparator == null){
            throw new IllegalArgumentException("The feature comparator is required to compare features. It cannot be null");
        }
        this.featureCollectionComparator = new FeatureCollectionComparator(featureComparator);
    }

    public EntityBaseComparator getParticipantBaseComparator() {
        return participantBaseComparator;
    }

    public FeatureCollectionComparator getFeatureCollectionComparator() {
        return featureCollectionComparator;
    }

    /**
     * It will compare the basic properties of an experimental entity using EntityBaseComparator and FeatureEvidenceComparator.
     * @param experimentalParticipant1
     * @param experimentalParticipant2
     * @return
     */
    public int compare(ExperimentalEntity experimentalParticipant1, ExperimentalEntity experimentalParticipant2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (experimentalParticipant1 == experimentalParticipant2){
            return EQUAL;
        }
        else if (experimentalParticipant1 == null){
            return AFTER;
        }
        else if (experimentalParticipant2 == null){
            return BEFORE;
        }
        else {
            participantBaseComparator.setIgnoreInteractors(false);

            // first compares basic participant properties
            int comp = participantBaseComparator.compare(experimentalParticipant1, experimentalParticipant2);
            if (comp != 0){
               return comp;
            }

            // then compares the features
            Collection<? extends FeatureEvidence> features1 = experimentalParticipant1.getFeatures();
            Collection<? extends FeatureEvidence> features2 = experimentalParticipant2.getFeatures();

            return featureCollectionComparator.compare(features1, features2);
        }
    }
}
