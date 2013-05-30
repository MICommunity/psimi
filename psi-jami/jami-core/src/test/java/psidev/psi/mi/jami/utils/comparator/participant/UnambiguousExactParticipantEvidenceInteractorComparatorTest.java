package psidev.psi.mi.jami.utils.comparator.participant;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultFeatureEvidence;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.InteractorUtils;

/**
 * Unit tester for UnambiguousExactParticipantEvidenceInteractorComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class UnambiguousExactParticipantEvidenceInteractorComparatorTest {

    private UnambiguousExactParticipantEvidenceInteractorComparator comparator = new UnambiguousExactParticipantEvidenceInteractorComparator();

    @Test
    public void test_participant_evidence_null_after(){
        ParticipantEvidence participant1 = null;
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertTrue(comparator.compare(participant1, participant2) > 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) < 0);

        Assert.assertFalse(UnambiguousExactParticipantEvidenceInteractorComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_different_interactors(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertTrue(comparator.compare(participant1, participant2) < 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) > 0);

        Assert.assertFalse(UnambiguousExactParticipantEvidenceInteractorComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_interactors_ignore_different_features(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant1.getFeatureEvidences().add(new DefaultFeatureEvidence("test", "test feature"));
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));

        Assert.assertTrue(comparator.compare(participant1, participant2) == 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) == 0);

        Assert.assertTrue(UnambiguousExactParticipantEvidenceInteractorComparator.areEquals(participant1, participant2));
    }
}
