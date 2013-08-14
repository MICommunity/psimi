package psidev.psi.mi.jami.utils.comparator.participant;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultModelledFeature;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.InteractorUtils;

/**
 * Unit tester for UnambiguousExactModelledParticipantComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class UnambiguousExactModelledParticipantComparatorTest {

    private UnambiguousExactModelledParticipantComparator comparator = new UnambiguousExactModelledParticipantComparator();

    @Test
    public void test_modelled_participant_null_after(){
        ModelledParticipant participant1 = null;
        ModelledParticipant participant2 = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertTrue(comparator.compare(participant1, participant2) > 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) < 0);

        Assert.assertFalse(UnambiguousExactModelledParticipantComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_different_interactors(){
        ModelledParticipant participant1 = new DefaultModelledParticipant(new DefaultProtein("test protein"));
        ModelledParticipant participant2 = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertTrue(comparator.compare(participant1, participant2) < 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) > 0);

        Assert.assertFalse(UnambiguousExactModelledParticipantComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_interactors(){
        ModelledParticipant participant1 = new DefaultModelledParticipant(new DefaultProtein("test protein"));
        ModelledParticipant participant2 = new DefaultModelledParticipant(new DefaultProtein("test protein"));

        Assert.assertTrue(comparator.compare(participant1, participant2) == 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) == 0);

        Assert.assertTrue(UnambiguousExactModelledParticipantComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_interactors_different_features(){
        ModelledParticipant participant1 = new DefaultModelledParticipant(new DefaultProtein("test protein"));
        participant1.addFeature(new DefaultModelledFeature("test1", "test feature 1"));
        ModelledParticipant participant2 = new DefaultModelledParticipant(new DefaultProtein("test protein"));
        participant2.addFeature(new DefaultModelledFeature("test1", "test feature 1"));
        participant2.addFeature(new DefaultModelledFeature("test2", "test feature 2"));

        Assert.assertTrue(comparator.compare(participant1, participant2) < 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) > 0);

        Assert.assertFalse(UnambiguousExactModelledParticipantComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_interactors_same_features_ignore_order(){
        ModelledParticipant participant1 = new DefaultModelledParticipant(new DefaultProtein("test protein"));
        participant1.addFeature(new DefaultModelledFeature("test2", "test feature 2"));
        participant1.addFeature(new DefaultModelledFeature("test1", "test feature 1"));
        ModelledParticipant participant2 = new DefaultModelledParticipant(new DefaultProtein("test protein"));
        participant2.addFeature(new DefaultModelledFeature("test1", "test feature 1"));
        participant2.addFeature(new DefaultModelledFeature("test2", "test feature 2"));

        Assert.assertTrue(comparator.compare(participant1, participant2) == 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) == 0);

        Assert.assertTrue(UnambiguousExactModelledParticipantComparator.areEquals(participant1, participant2));
    }
}
