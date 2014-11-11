package psidev.psi.mi.jami.utils.comparator.cooperativity;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CooperativeEffect;
import psidev.psi.mi.jami.model.impl.*;

/**
 * Unit tester for UnambiguousExactCooperativeEffectBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class UnambiguousExactCooperativeEffectBaseComparatorTest {

    private UnambiguousExactCooperativeEffectBaseComparator comparator = new UnambiguousExactCooperativeEffectBaseComparator();

    @Test
    public void test_cooperative_effect_null_after() throws Exception {
        CooperativeEffect effect1 = null;
        CooperativeEffect effect2 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"));

        Assert.assertTrue(comparator.compare(effect1, effect2) > 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) < 0);

        Assert.assertFalse(UnambiguousExactCooperativeEffectBaseComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) != UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_compare_different_outcome() throws Exception {
        CooperativeEffect effect1 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome 2"));
        CooperativeEffect effect2 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"));

        Assert.assertTrue(comparator.compare(effect1, effect2) > 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) < 0);

        Assert.assertFalse(UnambiguousExactCooperativeEffectBaseComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) != UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_compare_same_outcome() throws Exception {
        CooperativeEffect effect1 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"));
        CooperativeEffect effect2 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"));

        Assert.assertTrue(comparator.compare(effect1, effect2) == 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) == 0);

        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) == UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_compare_same_outcome_different_response() throws Exception {
        CooperativeEffect effect1 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"), new DefaultCvTerm("test response 2"));
        CooperativeEffect effect2 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"), new DefaultCvTerm("test response"));

        Assert.assertTrue(comparator.compare(effect1, effect2) > 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) < 0);

        Assert.assertFalse(UnambiguousExactCooperativeEffectBaseComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) != UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_compare_same_response() throws Exception {
        CooperativeEffect effect1 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"), new DefaultCvTerm("test response"));
        CooperativeEffect effect2 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"), new DefaultCvTerm("test response"));

        Assert.assertTrue(comparator.compare(effect1, effect2) == 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) == 0);

        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) == UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_compare_same_response_different_evidences() throws Exception {
        CooperativeEffect effect1 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"), new DefaultCvTerm("test response"));
        effect1.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("12345")));
        effect1.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("12346")));
        CooperativeEffect effect2 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"), new DefaultCvTerm("test response"));
        effect2.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("12345")));

        Assert.assertTrue(comparator.compare(effect1, effect2) > 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) < 0);

        Assert.assertFalse(UnambiguousExactCooperativeEffectBaseComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) != UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_compare_same_evidences() throws Exception {
        CooperativeEffect effect1 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"), new DefaultCvTerm("test response"));
        effect1.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("12345")));
        effect1.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("12346")));
        CooperativeEffect effect2 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"), new DefaultCvTerm("test response"));
        effect2.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("12346")));
        effect2.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("12345")));
        Assert.assertTrue(comparator.compare(effect1, effect2) == 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) == 0);

        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) == UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_compare_same_evidences_different_affected_interactions() throws Exception {
        CooperativeEffect effect1 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"), new DefaultCvTerm("test response 2"));
        effect1.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("12345")));
        effect1.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("12346")));
        effect1.getAffectedInteractions().add(new DefaultModelledInteraction("test 1"));
        effect1.getAffectedInteractions().add(new DefaultModelledInteraction("test 2"));
        CooperativeEffect effect2 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"), new DefaultCvTerm("test response"));
        effect2.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("12346")));
        effect2.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("12345")));
        effect2.getAffectedInteractions().add(new DefaultModelledInteraction("test 1"));

        Assert.assertTrue(comparator.compare(effect1, effect2) > 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) < 0);

        Assert.assertFalse(UnambiguousExactCooperativeEffectBaseComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) != UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_compare_same_affected_interactions() throws Exception {
        CooperativeEffect effect1 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"), new DefaultCvTerm("test response"));
        effect1.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("12345")));
        effect1.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("12346")));
        effect1.getAffectedInteractions().add(new DefaultModelledInteraction("test 1"));
        effect1.getAffectedInteractions().add(new DefaultModelledInteraction("test 2"));
        CooperativeEffect effect2 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"), new DefaultCvTerm("test response"));
        effect2.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("12346")));
        effect2.getCooperativityEvidences().add(new DefaultCooperativityEvidence(new DefaultPublication("12345")));
        effect2.getAffectedInteractions().add(new DefaultModelledInteraction("test 2"));
        effect2.getAffectedInteractions().add(new DefaultModelledInteraction("test 1"));
        Assert.assertTrue(comparator.compare(effect1, effect2) == 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) == 0);

        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) == UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }
}
