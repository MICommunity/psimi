package psidev.psi.mi.jami.utils.comparator.participant;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.utils.InteractorUtils;

/**
 * Unit tester for UnambiguousExactParticipantComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class UnambiguousExactParticipantComparatorTest {

    private UnambiguousExactParticipantComparator comparator = new UnambiguousExactParticipantComparator();

    @Test
    public void test_participant_null_after(){
        Participant participant1 = null;
        Participant participant2 = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertTrue(comparator.compare(participant1, participant2) > 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) < 0);

        Assert.assertFalse(UnambiguousExactParticipantComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_modelled_participant_first(){
        Participant participant1 = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());
        Participant participant2 = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor());
        Participant participant3 = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertTrue(comparator.compare(participant1, participant1) == 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) > 0);
        Assert.assertTrue(comparator.compare(participant3, participant1) > 0);

        Assert.assertTrue(UnambiguousExactParticipantComparator.areEquals(participant1, participant1));
        Assert.assertFalse(UnambiguousExactParticipantComparator.areEquals(participant2, participant1));
        Assert.assertFalse(UnambiguousExactParticipantComparator.areEquals(participant3, participant1));
    }

    @Test
    public void test_participant_evidence_second(){
        Participant participant1 = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());
        Participant participant2 = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor());
        Participant participant3 = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertTrue(comparator.compare(participant1, participant3) < 0);
        Assert.assertTrue(comparator.compare(participant2, participant3) > 0);
        Assert.assertTrue(comparator.compare(participant3, participant3) == 0);

        Assert.assertFalse(UnambiguousExactParticipantComparator.areEquals(participant1, participant3));
        Assert.assertFalse(UnambiguousExactParticipantComparator.areEquals(participant2, participant3));
        Assert.assertTrue(UnambiguousExactParticipantComparator.areEquals(participant3, participant3));
    }

    @Test
    public void test_default_participant_third(){
        Participant participant1 = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());
        Participant participant2 = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor());
        Participant participant3 = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertTrue(comparator.compare(participant1, participant2) < 0);
        Assert.assertTrue(comparator.compare(participant2, participant2) == 0);
        Assert.assertTrue(comparator.compare(participant3, participant2) < 0);

        Assert.assertFalse(UnambiguousExactParticipantComparator.areEquals(participant1, participant2));
        Assert.assertTrue(UnambiguousExactParticipantComparator.areEquals(participant2, participant2));
        Assert.assertFalse(UnambiguousExactParticipantComparator.areEquals(participant3, participant2));
    }
}
