package psidev.psi.mi.jami.utils.comparator.interaction;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultInteraction;

import java.util.Date;

/**
 * Unit tester for UnambiguousCuratedInteractionBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class UnambiguousCuratedInteractionBaseComparatorTest {

    private UnambiguousCuratedInteractionBaseComparator comparator = new UnambiguousCuratedInteractionBaseComparator();

    @Test
    public void test_interaction_null_after(){
        Interaction interaction1 = null;
        Interaction interaction2 = new DefaultInteraction("test");

        Assert.assertTrue(comparator.compare(interaction1, interaction2) > 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) < 0);

        Assert.assertFalse(UnambiguousCuratedInteractionBaseComparator.areEquals(interaction1, interaction2));
        Assert.assertTrue(UnambiguousCuratedInteractionBaseComparator.hashCode(interaction1) != UnambiguousCuratedInteractionBaseComparator.hashCode(interaction2));
    }

    @Test
    public void test_different_created_dates(){
        Interaction interaction1 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction1.setCreatedDate(new Date(System.currentTimeMillis()));
        Interaction interaction2 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction2.setCreatedDate(new Date(1));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) > 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) < 0);

        Assert.assertFalse(UnambiguousCuratedInteractionBaseComparator.areEquals(interaction1, interaction2));
        Assert.assertTrue(UnambiguousCuratedInteractionBaseComparator.hashCode(interaction1) != UnambiguousCuratedInteractionBaseComparator.hashCode(interaction2));
    }

    @Test
    public void test_same_created_dates(){
        Interaction interaction1 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction1.setCreatedDate(new Date(1));
        Interaction interaction2 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction2.setCreatedDate(new Date(1));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) == 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) == 0);

        Assert.assertTrue(UnambiguousCuratedInteractionBaseComparator.areEquals(interaction1, interaction2));
        Assert.assertTrue(UnambiguousCuratedInteractionBaseComparator.hashCode(interaction1) == UnambiguousCuratedInteractionBaseComparator.hashCode(interaction2));
    }

    @Test
    public void test_different_updated_dates(){
        Interaction interaction1 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction1.setCreatedDate(new Date(1));
        interaction1.setUpdatedDate(new Date(System.currentTimeMillis()));
        Interaction interaction2 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction2.setCreatedDate(new Date(1));
        interaction2.setUpdatedDate(new Date(1));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) > 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) < 0);

        Assert.assertFalse(UnambiguousCuratedInteractionBaseComparator.areEquals(interaction1, interaction2));
        Assert.assertTrue(UnambiguousCuratedInteractionBaseComparator.hashCode(interaction1) != UnambiguousCuratedInteractionBaseComparator.hashCode(interaction2));
    }

    @Test
    public void test_same_updated_dates(){
        Interaction interaction1 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction1.setCreatedDate(new Date(1));
        interaction1.setUpdatedDate(new Date(2));
        Interaction interaction2 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction2.setCreatedDate(new Date(1));
        interaction2.setUpdatedDate(new Date(2));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) == 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) == 0);

        Assert.assertTrue(UnambiguousCuratedInteractionBaseComparator.areEquals(interaction1, interaction2));
        Assert.assertTrue(UnambiguousCuratedInteractionBaseComparator.hashCode(interaction1) == UnambiguousCuratedInteractionBaseComparator.hashCode(interaction2));
    }
}
