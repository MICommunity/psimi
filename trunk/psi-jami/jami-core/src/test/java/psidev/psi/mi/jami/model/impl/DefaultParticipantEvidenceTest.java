package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.comparator.interaction.DefaultInteractionEvidenceComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultInteractorComparator;

/**
 * Unit tester for DefaultParticipantEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class DefaultParticipantEvidenceTest {

    @Test
    public void test_create_empty_participant_evidence(){

        ParticipantEvidence participant = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertTrue(DefaultInteractorComparator.areEquals(InteractorUtils.createUnknownBasicInteractor(), participant.getInteractor()));
        Assert.assertNotNull(participant.getExperimentalPreparations());
        Assert.assertNotNull(participant.getIdentificationMethods());
        Assert.assertNotNull(participant.getFeatureEvidences());
        Assert.assertNotNull(participant.getParameters());
        Assert.assertNotNull(participant.getConfidences());
        Assert.assertNull(participant.getExpressedInOrganism());
        Assert.assertNull(participant.getInteractionEvidence());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), participant.getExperimentalRole());
    }

    @Test
    public void test_set_experiment_role_null(){

        ParticipantEvidence participant = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), participant.getExperimentalRole());

        participant.setExperimentalRole(new DefaultCvTerm("bait"));
        Assert.assertEquals(new DefaultCvTerm("bait"), participant.getExperimentalRole());

        participant.setExperimentalRole(null);
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), participant.getExperimentalRole());
    }

    @Test
    public void test_set_unset_interaction_evidence(){

        ParticipantEvidence participant = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        InteractionEvidence i = new DefaultInteractionEvidence("test interaction");
        participant.setInteractionEvidence(i);

        Assert.assertTrue(DefaultInteractionEvidenceComparator.areEquals(new DefaultInteractionEvidence("test interaction"), participant.getInteractionEvidence()));
        Assert.assertEquals(0, i.getParticipantEvidences().size());

        participant.setInteractionEvidenceAndAddParticipantEvidence(i);
        Assert.assertNotNull(participant.getInteractionEvidence());
        Assert.assertEquals(1, i.getParticipantEvidences().size());
        Assert.assertEquals(participant, i.getParticipantEvidences().iterator().next());

        participant.setInteractionEvidenceAndAddParticipantEvidence(null);
        Assert.assertNull(participant.getInteractionEvidence());
        Assert.assertEquals(0, i.getParticipantEvidences().size());
    }

    @Test
    public void test_add_remove_modelledFeatures(){
        ParticipantEvidence participant = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        FeatureEvidence f = new DefaultFeatureEvidence("test", "test feature");

        Assert.assertNull(f.getParticipantEvidence());

        // add feature and set participantEvidence
        participant.addFeatureEvidence(f);
        Assert.assertEquals(participant, f.getParticipantEvidence());
        Assert.assertEquals(1, participant.getFeatureEvidences().size());

        // remove feature evidence and set participant to null
        participant.removeFeatureEvidence(f);
        Assert.assertNull(f.getParticipantEvidence());
        Assert.assertEquals(0, participant.getFeatureEvidences().size());

        // simply add feature evidence
        participant.getFeatureEvidences().add(f);
        Assert.assertNull(f.getParticipantEvidence());
        Assert.assertEquals(1, participant.getFeatureEvidences().size());

        // simply remove feature evidence
        participant.getFeatureEvidences().remove(f);
        Assert.assertNull(f.getParticipantEvidence());
        Assert.assertEquals(0, participant.getFeatureEvidences().size());
    }
}
