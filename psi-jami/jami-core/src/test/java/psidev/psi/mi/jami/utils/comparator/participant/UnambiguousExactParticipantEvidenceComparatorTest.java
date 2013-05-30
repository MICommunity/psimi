package psidev.psi.mi.jami.utils.comparator.participant;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.InteractorUtils;

import java.math.BigDecimal;

/**
 * Unit tester for UnambiguousExactParticipantEvidenceComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class UnambiguousExactParticipantEvidenceComparatorTest {

    private UnambiguousExactParticipantEvidenceComparator comparator = new UnambiguousExactParticipantEvidenceComparator();

    @Test
    public void test_modelled_participant_null_after(){
        ParticipantEvidence participant1 = null;
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertTrue(comparator.compare(participant1, participant2) > 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) < 0);

        Assert.assertFalse(UnambiguousExactParticipantEvidenceComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_different_interactors(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertTrue(comparator.compare(participant1, participant2) < 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) > 0);

        Assert.assertFalse(UnambiguousExactParticipantEvidenceComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_interactors(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));

        Assert.assertTrue(comparator.compare(participant1, participant2) == 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) == 0);

        Assert.assertTrue(UnambiguousExactParticipantEvidenceComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_interactors_different_features(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant1.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant2.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant2.addFeatureEvidence(new DefaultFeatureEvidence("test2", "test feature 2"));

        Assert.assertTrue(comparator.compare(participant1, participant2) < 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) > 0);

        Assert.assertFalse(UnambiguousExactParticipantEvidenceComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_interactors_same_features_ignore_order(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant1.addFeatureEvidence(new DefaultFeatureEvidence("test2", "test feature 2"));
        participant1.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant2.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant2.addFeatureEvidence(new DefaultFeatureEvidence("test2", "test feature 2"));

        Assert.assertTrue(comparator.compare(participant1, participant2) == 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) == 0);

        Assert.assertTrue(UnambiguousExactParticipantEvidenceComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_interactors_different_experimental_roles(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant1.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant1.setExperimentalRole(new DefaultCvTerm("bait"));
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant2.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant2.setExperimentalRole(new DefaultCvTerm("prey"));

        Assert.assertTrue(comparator.compare(participant1, participant2) < 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) > 0);

        Assert.assertFalse(UnambiguousExactParticipantEvidenceComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_experimental_roles(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant1.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant1.setExperimentalRole(new DefaultCvTerm("prey"));
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant2.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant2.setExperimentalRole(new DefaultCvTerm("prey"));

        Assert.assertTrue(comparator.compare(participant1, participant2) == 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) == 0);

        Assert.assertTrue(UnambiguousExactParticipantEvidenceComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_experimental_roles_different_methods(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant1.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant1.setExperimentalRole(new DefaultCvTerm("prey"));
        participant1.getIdentificationMethods().add(new DefaultCvTerm("western blot"));
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant2.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant2.setExperimentalRole(new DefaultCvTerm("prey"));
        participant2.getIdentificationMethods().add(new DefaultCvTerm("test blot"));

        Assert.assertTrue(comparator.compare(participant1, participant2) > 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) < 0);

        Assert.assertFalse(UnambiguousExactParticipantEvidenceComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_identification_methods(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant1.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant1.setExperimentalRole(new DefaultCvTerm("prey"));
        participant1.getIdentificationMethods().add(new DefaultCvTerm("western blot"));
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant2.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant2.setExperimentalRole(new DefaultCvTerm("prey"));
        participant2.getIdentificationMethods().add(new DefaultCvTerm("western blot"));

        Assert.assertTrue(comparator.compare(participant1, participant2) == 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) == 0);

        Assert.assertTrue(UnambiguousExactParticipantEvidenceComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_identification_methods_different_preparations(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant1.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant1.setExperimentalRole(new DefaultCvTerm("prey"));
        participant1.getIdentificationMethods().add(new DefaultCvTerm("western blot"));
        participant1.getExperimentalPreparations().add(new DefaultCvTerm("test 2"));
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant2.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant2.setExperimentalRole(new DefaultCvTerm("prey"));
        participant2.getIdentificationMethods().add(new DefaultCvTerm("western blot"));
        participant2.getExperimentalPreparations().add(new DefaultCvTerm("test 1"));

        Assert.assertTrue(comparator.compare(participant1, participant2) > 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) < 0);

        Assert.assertFalse(UnambiguousExactParticipantEvidenceComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_experimental_preparations(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant1.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant1.setExperimentalRole(new DefaultCvTerm("prey"));
        participant1.getIdentificationMethods().add(new DefaultCvTerm("western blot"));
        participant1.getExperimentalPreparations().add(new DefaultCvTerm("test 1"));
        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant2.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant2.setExperimentalRole(new DefaultCvTerm("prey"));
        participant2.getIdentificationMethods().add(new DefaultCvTerm("western blot"));
        participant2.getExperimentalPreparations().add(new DefaultCvTerm("test 1"));

        Assert.assertTrue(comparator.compare(participant1, participant2) == 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) == 0);

        Assert.assertTrue(UnambiguousExactParticipantEvidenceComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_experimental_preparations_different_organisms(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant1.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant1.setExperimentalRole(new DefaultCvTerm("prey"));
        participant1.getIdentificationMethods().add(new DefaultCvTerm("western blot"));
        participant1.getExperimentalPreparations().add(new DefaultCvTerm("test 1"));
        participant1.setExpressedInOrganism(new DefaultOrganism(-1));

        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant2.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant2.setExperimentalRole(new DefaultCvTerm("prey"));
        participant2.getIdentificationMethods().add(new DefaultCvTerm("western blot"));
        participant2.getExperimentalPreparations().add(new DefaultCvTerm("test 1"));
        participant2.setExpressedInOrganism(new DefaultOrganism(9606));

        Assert.assertTrue(comparator.compare(participant1, participant2) < 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) > 0);

        Assert.assertFalse(UnambiguousExactParticipantEvidenceComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_expressed_in_organism(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant1.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant1.setExperimentalRole(new DefaultCvTerm("prey"));
        participant1.getIdentificationMethods().add(new DefaultCvTerm("western blot"));
        participant1.getExperimentalPreparations().add(new DefaultCvTerm("test 1"));
        participant1.setExpressedInOrganism(new DefaultOrganism(-1));

        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant2.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant2.setExperimentalRole(new DefaultCvTerm("prey"));
        participant2.getIdentificationMethods().add(new DefaultCvTerm("western blot"));
        participant2.getExperimentalPreparations().add(new DefaultCvTerm("test 1"));
        participant2.setExpressedInOrganism(new DefaultOrganism(-1));

        Assert.assertTrue(comparator.compare(participant1, participant2) == 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) == 0);

        Assert.assertTrue(UnambiguousExactParticipantEvidenceComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_organism_different_parameters(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant1.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant1.setExperimentalRole(new DefaultCvTerm("prey"));
        participant1.getIdentificationMethods().add(new DefaultCvTerm("western blot"));
        participant1.getExperimentalPreparations().add(new DefaultCvTerm("test 1"));
        participant1.setExpressedInOrganism(new DefaultOrganism(-1));
        participant1.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));

        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant2.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant2.setExperimentalRole(new DefaultCvTerm("prey"));
        participant2.getIdentificationMethods().add(new DefaultCvTerm("western blot"));
        participant2.getExperimentalPreparations().add(new DefaultCvTerm("test 1"));
        participant2.setExpressedInOrganism(new DefaultOrganism(-1));
        participant2.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(4))));
        participant2.getParameters().add(new DefaultParameter(new DefaultCvTerm("test"), new ParameterValue(new BigDecimal(3))));

        Assert.assertTrue(comparator.compare(participant1, participant2) < 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) > 0);

        Assert.assertFalse(UnambiguousExactParticipantEvidenceComparator.areEquals(participant1, participant2));
    }

    @Test
    public void test_same_parameters(){
        ParticipantEvidence participant1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant1.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant1.setExperimentalRole(new DefaultCvTerm("prey"));
        participant1.getIdentificationMethods().add(new DefaultCvTerm("western blot"));
        participant1.getExperimentalPreparations().add(new DefaultCvTerm("test 1"));
        participant1.setExpressedInOrganism(new DefaultOrganism(-1));
        participant1.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));

        ParticipantEvidence participant2 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant2.addFeatureEvidence(new DefaultFeatureEvidence("test1", "test feature 1"));
        participant2.setExperimentalRole(new DefaultCvTerm("prey"));
        participant2.getIdentificationMethods().add(new DefaultCvTerm("western blot"));
        participant2.getExperimentalPreparations().add(new DefaultCvTerm("test 1"));
        participant2.setExpressedInOrganism(new DefaultOrganism(-1));
        participant2.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));

        Assert.assertTrue(comparator.compare(participant1, participant2) == 0);
        Assert.assertTrue(comparator.compare(participant2, participant1) == 0);

        Assert.assertTrue(UnambiguousExactParticipantEvidenceComparator.areEquals(participant1, participant2));
    }
}
