package psidev.psi.mi.jami.utils.comparator.cooperativity;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Allostery;
import psidev.psi.mi.jami.model.CooperativeEffect;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.InteractorUtils;

/**
 * Unit tester for UnambiguousExactCooperativeEffectComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class UnambiguousExactCooperativeEffectComparatorTest {

    private UnambiguousExactCooperativeEffectComparator comparator = new UnambiguousExactCooperativeEffectComparator();

    @Test
    public void test_cooperative_effect_null_after() throws Exception {
        Allostery effect1 = null;
        Allostery effect2 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature", "test feature modification")));

        Assert.assertTrue(comparator.compare(effect1, effect2) > 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) < 0);

        Assert.assertFalse(UnambiguousExactCooperativeEffectComparator.areEquals(effect1, effect2));
    }

    @Test
    public void test_allostery_first() throws Exception {
        CooperativeEffect effect1 = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"));
        Allostery effect2 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature", "test feature modification")));

        Assert.assertTrue(comparator.compare(effect1, effect2) > 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) < 0);

        Assert.assertFalse(UnambiguousExactCooperativeEffectComparator.areEquals(effect1, effect2));
    }
}
