package psidev.psi.mi.jami.utils.comparator.participant;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CausalRelationship;
import psidev.psi.mi.jami.model.impl.DefaultCausalRelationship;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultInteractor;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.utils.InteractorUtils;

/**
 * Unit tester for UnambiguousCausalRelationshipComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/13</pre>
 */

public class UnambiguousCausalRelationshipComparatorTest {

    private UnambiguousCausalRelationshipComparator comparator = new UnambiguousCausalRelationshipComparator();

    @Test
    public void test_causal_relationship_null_after() throws Exception {
        CausalRelationship rel1 = null;
        CausalRelationship rel2 = new DefaultCausalRelationship(new DefaultCvTerm("decreasing"), new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));

        Assert.assertTrue(comparator.compare(rel1, rel2) > 0);
        Assert.assertTrue(comparator.compare(rel2, rel1) < 0);

        Assert.assertFalse(UnambiguousCausalRelationshipComparator.areEquals(rel1, rel2));
        Assert.assertTrue(UnambiguousCausalRelationshipComparator.hashCode(rel1) != UnambiguousCausalRelationshipComparator.hashCode(rel2));
    }

    @Test
    public void test_causal_relationship_different_relation_types() throws Exception {
        CausalRelationship rel1 = new DefaultCausalRelationship(new DefaultCvTerm("increasing"), new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));
        CausalRelationship rel2 = new DefaultCausalRelationship(new DefaultCvTerm("decreasing"), new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));

        Assert.assertTrue(comparator.compare(rel1, rel2) > 0);
        Assert.assertTrue(comparator.compare(rel2, rel1) < 0);

        Assert.assertFalse(UnambiguousCausalRelationshipComparator.areEquals(rel1, rel2));
        Assert.assertTrue(UnambiguousCausalRelationshipComparator.hashCode(rel1) != UnambiguousCausalRelationshipComparator.hashCode(rel2));
    }

    @Test
    public void test_causal_relationship_different_target() throws Exception {
        CausalRelationship rel1 = new DefaultCausalRelationship(new DefaultCvTerm("increasing"), new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));
        CausalRelationship rel2 = new DefaultCausalRelationship(new DefaultCvTerm("increasing"), new DefaultParticipant(new DefaultInteractor("test")));

        Assert.assertTrue(comparator.compare(rel1, rel2) > 0);
        Assert.assertTrue(comparator.compare(rel2, rel1) < 0);

        Assert.assertFalse(UnambiguousCausalRelationshipComparator.areEquals(rel1, rel2));
        Assert.assertTrue(UnambiguousCausalRelationshipComparator.hashCode(rel1) != UnambiguousCausalRelationshipComparator.hashCode(rel2));
    }

    @Test
    public void test_causal_relationship_same_target_same_relation_type() throws Exception {
        CausalRelationship rel1 = new DefaultCausalRelationship(new DefaultCvTerm("decreasing"), new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));
        CausalRelationship rel2 = new DefaultCausalRelationship(new DefaultCvTerm("decreasing"), new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));

        Assert.assertTrue(comparator.compare(rel1, rel2) == 0);
        Assert.assertTrue(comparator.compare(rel2, rel1) == 0);

        Assert.assertTrue(UnambiguousCausalRelationshipComparator.areEquals(rel1, rel2));
        Assert.assertTrue(UnambiguousCausalRelationshipComparator.hashCode(rel1) == UnambiguousCausalRelationshipComparator.hashCode(rel2));
    }
}
