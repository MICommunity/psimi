package psidev.psi.mi.jami.utils.comparator.cooperativity;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Allostery;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for UnambiguousExactAllosteryComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class UnambiguousExactAllosteryComparatorTest {

    private UnambiguousExactAllosteryComparator comparator = new UnambiguousExactAllosteryComparator();

    @Test
    public void test_allostery_null_after() throws Exception {
        Allostery effect1 = null;
        Allostery effect2 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature", "test feature modification")));

        Assert.assertTrue(comparator.compare(effect1, effect2) > 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) < 0);

        Assert.assertFalse(UnambiguousExactAllosteryComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) != UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_molecule_effector_before() throws Exception {
        Allostery effect1 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultMoleculeEffector(new DefaultModelledParticipant(new DefaultProtein("test protein", XrefUtils.createUniprotIdentity("P12345")))));
        Allostery effect2 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature", "test feature modification")));

        Assert.assertTrue(comparator.compare(effect1, effect2) < 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) > 0);

        Assert.assertFalse(UnambiguousExactAllosteryComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) == UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_different_molecule_effector() throws Exception {
        Allostery effect1 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultMoleculeEffector(new DefaultModelledParticipant(new DefaultProtein("test protein", XrefUtils.createUniprotIdentity("P12345")))));
        Allostery effect2 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultMoleculeEffector(new DefaultModelledParticipant(new DefaultGene("test gene", XrefUtils.createEntrezGeneIdIdentity("xxxx")))));

        // proteins are before genes
        Assert.assertTrue(comparator.compare(effect1, effect2) < 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) > 0);

        Assert.assertFalse(UnambiguousExactAllosteryComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) == UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_same_molecule_effector() throws Exception {
        Allostery effect1 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultMoleculeEffector(new DefaultModelledParticipant(new DefaultProtein("test protein", XrefUtils.createUniprotIdentity("P12345")))));
        Allostery effect2 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultMoleculeEffector(new DefaultModelledParticipant(new DefaultProtein("test protein", XrefUtils.createUniprotIdentity("P12345")))));

        // proteins are before genes
        Assert.assertTrue(comparator.compare(effect1, effect2) == 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) == 0);

        Assert.assertTrue(UnambiguousExactAllosteryComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) == UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_different_feature_modification_effector() throws Exception {
        Allostery effect1 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        Allostery effect2 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 2", "test feature modification 2")));

        // proteins are before genes
        Assert.assertTrue(comparator.compare(effect1, effect2) < 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) > 0);

        Assert.assertFalse(UnambiguousExactAllosteryComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) == UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_same_feature_modification_effector_effector() throws Exception {
        Allostery effect1 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        Allostery effect2 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));

        // proteins are before genes
        Assert.assertTrue(comparator.compare(effect1, effect2) == 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) == 0);

        Assert.assertTrue(UnambiguousExactAllosteryComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) == UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_same_effector_effector_different_mechanism() throws Exception {
        Allostery effect1 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        effect1.setAllostericMechanism(new DefaultCvTerm("test mechanism 2"));
        Allostery effect2 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        effect2.setAllostericMechanism(new DefaultCvTerm("test mechanism 1"));

        // proteins are before genes
        Assert.assertTrue(comparator.compare(effect1, effect2) > 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) < 0);

        Assert.assertFalse(UnambiguousExactAllosteryComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) == UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_same_mechanism() throws Exception {
        Allostery effect1 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        effect1.setAllostericMechanism(new DefaultCvTerm("test mechanism 1"));
        Allostery effect2 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        effect2.setAllostericMechanism(new DefaultCvTerm("test mechanism 1"));
        // proteins are before genes
        Assert.assertTrue(comparator.compare(effect1, effect2) == 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) == 0);

        Assert.assertTrue(UnambiguousExactAllosteryComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) == UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_same_mechanism_different_type() throws Exception {
        Allostery effect1 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        effect1.setAllostericMechanism(new DefaultCvTerm("test mechanism 1"));
        effect1.setAllosteryType(new DefaultCvTerm("test type 2"));
        Allostery effect2 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        effect2.setAllostericMechanism(new DefaultCvTerm("test mechanism 1"));
        effect2.setAllosteryType(new DefaultCvTerm("test type 1"));

        // proteins are before genes
        Assert.assertTrue(comparator.compare(effect1, effect2) > 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) < 0);

        Assert.assertFalse(UnambiguousExactAllosteryComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) == UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_same_type() throws Exception {
        Allostery effect1 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        effect1.setAllostericMechanism(new DefaultCvTerm("test mechanism 1"));
        effect1.setAllosteryType(new DefaultCvTerm("test type 1"));
        Allostery effect2 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        effect2.setAllostericMechanism(new DefaultCvTerm("test mechanism 1"));
        effect2.setAllosteryType(new DefaultCvTerm("test type 1"));
        // proteins are before genes
        Assert.assertTrue(comparator.compare(effect1, effect2) == 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) == 0);

        Assert.assertTrue(UnambiguousExactAllosteryComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) == UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_same_type_different_allosteric_molecule() throws Exception {
        Allostery effect1 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(new DefaultProtein("test protein")),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        effect1.setAllostericMechanism(new DefaultCvTerm("test mechanism 1"));
        effect1.setAllosteryType(new DefaultCvTerm("test type 1"));
        Allostery effect2 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(new DefaultGene("test gene")),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        effect2.setAllostericMechanism(new DefaultCvTerm("test mechanism 1"));
        effect2.setAllosteryType(new DefaultCvTerm("test type 1"));

        // proteins are before genes
        Assert.assertTrue(comparator.compare(effect1, effect2) < 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) > 0);

        Assert.assertFalse(UnambiguousExactAllosteryComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) == UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_same_allosteric_molecule() throws Exception {
        Allostery effect1 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(new DefaultProtein("test protein")),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        effect1.setAllostericMechanism(new DefaultCvTerm("test mechanism 1"));
        effect1.setAllosteryType(new DefaultCvTerm("test type 1"));
        Allostery effect2 = new DefaultAllostery(new DefaultCvTerm("test outcome"),
                new DefaultModelledParticipant(new DefaultProtein("test protein")),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        effect2.setAllostericMechanism(new DefaultCvTerm("test mechanism 1"));
        effect2.setAllosteryType(new DefaultCvTerm("test type 1"));
        // proteins are before genes
        Assert.assertTrue(comparator.compare(effect1, effect2) == 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) == 0);

        Assert.assertTrue(UnambiguousExactAllosteryComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) == UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }

    @Test
    public void test_same_allosteric_molecule_different_outcome() throws Exception {
        Allostery effect1 = new DefaultAllostery(new DefaultCvTerm("test outcome 1"),
                new DefaultModelledParticipant(new DefaultProtein("test protein")),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        effect1.setAllostericMechanism(new DefaultCvTerm("test mechanism 1"));
        effect1.setAllosteryType(new DefaultCvTerm("test type 1"));
        Allostery effect2 = new DefaultAllostery(new DefaultCvTerm("test outcome 2"),
                new DefaultModelledParticipant(new DefaultProtein("test protein")),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature("test feature 1", "test feature modification 1")));
        effect2.setAllostericMechanism(new DefaultCvTerm("test mechanism 1"));
        effect2.setAllosteryType(new DefaultCvTerm("test type 1"));
        // proteins are before genes
        Assert.assertTrue(comparator.compare(effect1, effect2) < 0);
        Assert.assertTrue(comparator.compare(effect2, effect1) > 0);

        Assert.assertFalse(UnambiguousExactAllosteryComparator.areEquals(effect1, effect2));
        Assert.assertTrue(UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect1) != UnambiguousExactCooperativeEffectBaseComparator.hashCode(effect2));
    }
}
