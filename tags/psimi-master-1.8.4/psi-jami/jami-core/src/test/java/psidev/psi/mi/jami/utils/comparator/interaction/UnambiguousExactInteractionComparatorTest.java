package psidev.psi.mi.jami.utils.comparator.interaction;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.impl.DefaultInteraction;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;
import psidev.psi.mi.jami.model.impl.DefaultModelledInteraction;

/**
 * Unit tester for UnambiguousExactInteractionComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class UnambiguousExactInteractionComparatorTest {

    private UnambiguousExactInteractionComparator comparator = new UnambiguousExactInteractionComparator();

    @Test
    public void test_interaction_null_after(){
        InteractionEvidence interaction1 = null;
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test");

        Assert.assertTrue(comparator.compare(interaction1, interaction2) > 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) < 0);

        Assert.assertFalse(UnambiguousExactInteractionComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_modelled_interaction_first(){
        ModelledInteraction interaction1 = new DefaultModelledInteraction("test");
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test");
        DefaultInteraction interaction3 = new DefaultInteraction("test");

        Assert.assertTrue(comparator.compare(interaction1, interaction1) == 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) > 0);
        Assert.assertTrue(comparator.compare(interaction3, interaction1) > 0);

        Assert.assertTrue(UnambiguousExactInteractionComparator.areEquals(interaction1, interaction1));
        Assert.assertFalse(UnambiguousExactInteractionComparator.areEquals(interaction2, interaction1));
        Assert.assertFalse(UnambiguousExactInteractionComparator.areEquals(interaction3, interaction1));
    }

    @Test
    public void test_experimental_interaction_second(){
        ModelledInteraction interaction1 = new DefaultModelledInteraction("test");
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test");
        DefaultInteraction interaction3 = new DefaultInteraction("test");

        Assert.assertTrue(comparator.compare(interaction1, interaction2) < 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction2) == 0);
        Assert.assertTrue(comparator.compare(interaction3, interaction2) > 0);

        Assert.assertFalse(UnambiguousExactInteractionComparator.areEquals(interaction1, interaction2));
        Assert.assertTrue(UnambiguousExactInteractionComparator.areEquals(interaction2, interaction2));
        Assert.assertFalse(UnambiguousExactInteractionComparator.areEquals(interaction3, interaction2));
    }

    @Test
    public void test_default_interaction_last(){
        ModelledInteraction interaction1 = new DefaultModelledInteraction("test");
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test");
        DefaultInteraction interaction3 = new DefaultInteraction("test");

        Assert.assertTrue(comparator.compare(interaction1, interaction3) < 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction3) < 0);
        Assert.assertTrue(comparator.compare(interaction3, interaction3) == 0);

        Assert.assertFalse(UnambiguousExactInteractionComparator.areEquals(interaction1, interaction3));
        Assert.assertFalse(UnambiguousExactInteractionComparator.areEquals(interaction2, interaction3));
        Assert.assertTrue(UnambiguousExactInteractionComparator.areEquals(interaction3, interaction3));
    }
}
