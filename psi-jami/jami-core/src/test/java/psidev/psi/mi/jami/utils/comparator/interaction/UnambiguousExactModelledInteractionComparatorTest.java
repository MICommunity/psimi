package psidev.psi.mi.jami.utils.comparator.interaction;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for UnambiguousExactModelledInteractionComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class UnambiguousExactModelledInteractionComparatorTest {

    private UnambiguousModelledInteractionComparator comparator = new UnambiguousModelledInteractionComparator();

    @Test
    public void test_modelled_interaction_null_after(){
        ModelledInteraction interaction1 = null;
        ModelledInteraction interaction2 = new DefaultModelledInteraction("test");

        Assert.assertTrue(comparator.compare(interaction1, interaction2) > 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) < 0);

        Assert.assertFalse(UnambiguousModelledInteractionComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_different_basic_properties(){
        ModelledInteraction interaction1 = new DefaultModelledInteraction("test", new DefaultCvTerm("association"));
        ModelledInteraction interaction2 = new DefaultModelledInteraction("test", new DefaultCvTerm("prediction"));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) < 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) > 0);

        Assert.assertFalse(UnambiguousModelledInteractionComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_same_basic_properties(){
        ModelledInteraction interaction1 = new DefaultModelledInteraction("test", new DefaultCvTerm("prediction"));
        ModelledInteraction interaction2 = new DefaultModelledInteraction("test", new DefaultCvTerm("prediction"));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) == 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) == 0);

        Assert.assertTrue(UnambiguousModelledInteractionComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_different_participants(){
        ModelledInteraction interaction1 = new DefaultModelledInteraction("test", new DefaultCvTerm("prediction"));
        interaction1.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test 1", XrefUtils.createUniprotIdentity("P12345"))));
        interaction1.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test 2", XrefUtils.createUniprotIdentity("P12346"))));
        ModelledInteraction interaction2 = new DefaultModelledInteraction("test", new DefaultCvTerm("prediction"));
        interaction2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test 1", XrefUtils.createUniprotIdentity("P12345"))));
        interaction2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test 2", XrefUtils.createUniprotIdentity("P12346"))));
        interaction2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test 1", XrefUtils.createUniprotIdentity("P12347"))));
        interaction2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test 2", XrefUtils.createUniprotIdentity("P12348"))));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) < 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) > 0);

        Assert.assertFalse(UnambiguousModelledInteractionComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_same_participants(){
        ModelledInteraction interaction1 = new DefaultModelledInteraction("test", new DefaultCvTerm("prediction"));
        interaction1.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test 1", XrefUtils.createUniprotIdentity("P12347"))));
        interaction1.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test 2", XrefUtils.createUniprotIdentity("P12348"))));
        interaction1.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test 1", XrefUtils.createUniprotIdentity("P12345"))));
        interaction1.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test 2", XrefUtils.createUniprotIdentity("P12346"))));
        ModelledInteraction interaction2 = new DefaultModelledInteraction("test", new DefaultCvTerm("prediction"));
        interaction2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test 1", XrefUtils.createUniprotIdentity("P12345"))));
        interaction2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test 2", XrefUtils.createUniprotIdentity("P12346"))));
        interaction2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test 1", XrefUtils.createUniprotIdentity("P12347"))));
        interaction2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test 2", XrefUtils.createUniprotIdentity("P12348"))));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) == 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) == 0);

        Assert.assertTrue(UnambiguousModelledInteractionComparator.areEquals(interaction1, interaction2));
    }
}
