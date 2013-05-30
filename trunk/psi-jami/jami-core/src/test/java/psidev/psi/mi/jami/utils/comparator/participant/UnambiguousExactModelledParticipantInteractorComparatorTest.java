package psidev.psi.mi.jami.utils.comparator.participant;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultModelledFeature;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.InteractorUtils;

/**
 * Unit tester for UnambiguousExactModelledParticipantInteractorComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class UnambiguousExactModelledParticipantInteractorComparatorTest {

    private UnambiguousExactModelledParticipantInteractorComparator comparator = new UnambiguousExactModelledParticipantInteractorComparator();

    @Test
    public void test_modelled_participant_null_after(){
        ModelledParticipant participant1 = null;
        ModelledParticipant participant2 = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertTrue(comparator.compare(participant1, participant2) > 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) < 0);

        Assert.assertFalse(UnambiguousExactModelledParticipantInteractorComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_different_interactors(){
        ModelledParticipant participant1 = new DefaultModelledParticipant(new DefaultProtein("test protein"));
        ModelledParticipant participant2 = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertTrue(comparator.compare(participant1, participant2) < 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) > 0);

        Assert.assertFalse(UnambiguousExactModelledParticipantInteractorComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_interactors_ignore_different_features(){
        ModelledParticipant participant1 = new DefaultModelledParticipant(new DefaultProtein("test protein"));
        participant1.getModelledFeatures().add(new DefaultModelledFeature("test", "test feature"));
        ModelledParticipant participant2 = new DefaultModelledParticipant(new DefaultProtein("test protein"));

        Assert.assertTrue(comparator.compare(participant1, participant2) == 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) == 0);

        Assert.assertTrue(UnambiguousExactModelledParticipantInteractorComparator.areEquals(participant1, participant2));
    }
}
