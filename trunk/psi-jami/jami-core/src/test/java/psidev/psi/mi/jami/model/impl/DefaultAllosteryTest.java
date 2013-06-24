package psidev.psi.mi.jami.model.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Allostery;
import psidev.psi.mi.jami.model.FeatureModificationEffector;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.comparator.cooperativity.DefaultFeatureModificationEffectorComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultModelledParticipantComparator;

/**
 * Unit tester for DefaultAllostery
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class DefaultAllosteryTest {

    @Test
    public void test_create_allostery() throws Exception {
        Allostery<FeatureModificationEffector> allostery = new DefaultAllostery<FeatureModificationEffector>(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test", "test effector")));

        Assert.assertEquals(new DefaultCvTerm("test outcome"), allostery.getOutCome());
        Assert.assertTrue(DefaultModelledParticipantComparator.areEquals(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()), allostery.getAllostericMolecule(), true));
        Assert.assertTrue(DefaultFeatureModificationEffectorComparator.areEquals(new DefaultFeatureModificationEffector(new DefaultModelledFeature("test", "test effector")), allostery.getAllostericEffector()));
        Assert.assertNull(allostery.getAllostericMechanism());
        Assert.assertNull(allostery.getAllosteryType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_create_allostery_no_outcome() throws Exception {
        Allostery<FeatureModificationEffector> allostery = new DefaultAllostery<FeatureModificationEffector>(null,
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test", "test effector")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_create_allostery_no_effector() throws Exception {
        Allostery<FeatureModificationEffector> allostery = new DefaultAllostery<FeatureModificationEffector>(new DefaultCvTerm("test outcome"),
                null,
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test", "test effector")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_create_allostery_no_allosteric_molecule() throws Exception {
        Allostery<FeatureModificationEffector> allostery = new DefaultAllostery<FeatureModificationEffector>(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_set_effector_null() throws Exception {
        Allostery<FeatureModificationEffector> allostery = new DefaultAllostery<FeatureModificationEffector>(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test", "test effector")));

        allostery.setAllostericEffector(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_set_allosteric_molecule_null() throws Exception {
        Allostery<FeatureModificationEffector> allostery = new DefaultAllostery<FeatureModificationEffector>(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test", "test effector")));

        allostery.setAllostericEffector(null);
    }
}
