package psidev.psi.mi.jami.utils.comparator.cooperativity;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.model.impl.DefaultCooperativityEvidence;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultPublication;

/**
 * Unit tester for UnambiguousCooperativityEvidenceComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class UnambiguousCooperativityEvidenceComparatorTest {

    private UnambiguousCooperativityEvidenceComparator comparator = new UnambiguousCooperativityEvidenceComparator();

    @Test
    public void test_cooperativity_evidence_null_after() throws Exception {
        CooperativityEvidence ev1 = null;
        CooperativityEvidence ev2 = new DefaultCooperativityEvidence(new DefaultPublication("12345"));

        Assert.assertTrue(comparator.compare(ev1, ev2) > 0);
        Assert.assertTrue(comparator.compare(ev2, ev1) < 0);

        Assert.assertFalse(UnambiguousCooperativityEvidenceComparator.areEquals(ev1, ev2));
        Assert.assertTrue(UnambiguousCooperativityEvidenceComparator.hashCode(ev1) != UnambiguousCooperativityEvidenceComparator.hashCode(ev2));
    }

    @Test
    public void test_different_publications() throws Exception {
        CooperativityEvidence ev1 = new DefaultCooperativityEvidence(new DefaultPublication("12346"));
        CooperativityEvidence ev2 = new DefaultCooperativityEvidence(new DefaultPublication("12345"));

        Assert.assertTrue(comparator.compare(ev1, ev2) > 0);
        Assert.assertTrue(comparator.compare(ev2, ev1) < 0);

        Assert.assertFalse(UnambiguousCooperativityEvidenceComparator.areEquals(ev1, ev2));
        Assert.assertTrue(UnambiguousCooperativityEvidenceComparator.hashCode(ev1) != UnambiguousCooperativityEvidenceComparator.hashCode(ev2));
    }

    @Test
    public void test_same_publications() throws Exception {
        CooperativityEvidence ev1 = new DefaultCooperativityEvidence(new DefaultPublication("12346"));
        CooperativityEvidence ev2 = new DefaultCooperativityEvidence(new DefaultPublication("12346"));

        Assert.assertTrue(comparator.compare(ev1, ev2) == 0);
        Assert.assertTrue(comparator.compare(ev2, ev1) == 0);

        Assert.assertTrue(UnambiguousCooperativityEvidenceComparator.areEquals(ev1, ev2));
        Assert.assertTrue(UnambiguousCooperativityEvidenceComparator.hashCode(ev1) == UnambiguousCooperativityEvidenceComparator.hashCode(ev2));
    }

    @Test
    public void test_same_publication_different_methods() throws Exception {
        CooperativityEvidence ev1 = new DefaultCooperativityEvidence(new DefaultPublication("12346"));
        ev1.getEvidenceMethods().add(new DefaultCvTerm("method1"));
        ev1.getEvidenceMethods().add(new DefaultCvTerm("method2"));
        CooperativityEvidence ev2 = new DefaultCooperativityEvidence(new DefaultPublication("12346"));
        ev1.getEvidenceMethods().add(new DefaultCvTerm("method2"));

        Assert.assertTrue(comparator.compare(ev1, ev2) > 0);
        Assert.assertTrue(comparator.compare(ev2, ev1) < 0);

        Assert.assertFalse(UnambiguousCooperativityEvidenceComparator.areEquals(ev1, ev2));
        Assert.assertTrue(UnambiguousCooperativityEvidenceComparator.hashCode(ev1) != UnambiguousCooperativityEvidenceComparator.hashCode(ev2));
    }

    @Test
    public void test_same_methods_ignore_order() throws Exception {
        CooperativityEvidence ev1 = new DefaultCooperativityEvidence(new DefaultPublication("12346"));
        ev1.getEvidenceMethods().add(new DefaultCvTerm("method1"));
        ev1.getEvidenceMethods().add(new DefaultCvTerm("method2"));
        CooperativityEvidence ev2 = new DefaultCooperativityEvidence(new DefaultPublication("12346"));
        ev2.getEvidenceMethods().add(new DefaultCvTerm("method2"));
        ev2.getEvidenceMethods().add(new DefaultCvTerm("method1"));

        Assert.assertTrue(comparator.compare(ev1, ev2) == 0);
        Assert.assertTrue(comparator.compare(ev2, ev1) == 0);

        Assert.assertTrue(UnambiguousCooperativityEvidenceComparator.areEquals(ev1, ev2));
        Assert.assertTrue(UnambiguousCooperativityEvidenceComparator.hashCode(ev1) == UnambiguousCooperativityEvidenceComparator.hashCode(ev2));
    }
}
