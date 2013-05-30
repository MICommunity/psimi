package psidev.psi.mi.jami.utils.comparator.participant;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.model.impl.DefaultStoichiometry;

/**
 * Unit tester for StoichiometryComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class StoichiometryComparatorTest {

    private StoichiometryComparator comparator = new StoichiometryComparator();

    @Test
    public void test_stoichiometry_null_after() throws Exception {
        Stoichiometry stc1 = null;
        Stoichiometry stc2 = new DefaultStoichiometry(1);

        Assert.assertTrue(comparator.compare(stc1, stc2) > 0);
        Assert.assertTrue(comparator.compare(stc2, stc1) < 0);

        Assert.assertFalse(StoichiometryComparator.areEquals(stc1, stc2));
        Assert.assertTrue(StoichiometryComparator.hashCode(stc1) != StoichiometryComparator.hashCode(stc2));
    }

    @Test
    public void test_compare_different_stoichiometry() throws Exception {
        Stoichiometry stc1 = new DefaultStoichiometry(2);
        Stoichiometry stc2 = new DefaultStoichiometry(3);

        Assert.assertTrue(comparator.compare(stc1, stc2) < 0);
        Assert.assertTrue(comparator.compare(stc2, stc1) > 0);

        Assert.assertFalse(StoichiometryComparator.areEquals(stc1, stc2));
        Assert.assertTrue(StoichiometryComparator.hashCode(stc1) != StoichiometryComparator.hashCode(stc2));
    }

    @Test
    public void test_compare_same_stoichiometry() throws Exception {
        Stoichiometry stc1 = new DefaultStoichiometry(2);
        Stoichiometry stc2 = new DefaultStoichiometry(2);

        Assert.assertTrue(comparator.compare(stc1, stc2) == 0);
        Assert.assertTrue(comparator.compare(stc2, stc1) == 0);

        Assert.assertTrue(StoichiometryComparator.areEquals(stc1, stc2));
        Assert.assertTrue(StoichiometryComparator.hashCode(stc1) == StoichiometryComparator.hashCode(stc2));
    }

    @Test
    public void test_compare_lower_stoichiometry_than_stoichiometry_range() throws Exception {
        Stoichiometry stc1 = new DefaultStoichiometry(2);
        Stoichiometry stc2 = new DefaultStoichiometry(3, 4);

        Assert.assertTrue(comparator.compare(stc1, stc2) < 0);
        Assert.assertTrue(comparator.compare(stc2, stc1) > 0);

        Assert.assertFalse(StoichiometryComparator.areEquals(stc1, stc2));
        Assert.assertTrue(StoichiometryComparator.hashCode(stc1) != StoichiometryComparator.hashCode(stc2));
    }

    @Test
    public void test_compare_stoichiometry_within_stoichiometry_range_after() throws Exception {
        Stoichiometry stc1 = new DefaultStoichiometry(2);
        Stoichiometry stc2 = new DefaultStoichiometry(1, 4);

        Assert.assertTrue(comparator.compare(stc1, stc2) > 0);
        Assert.assertTrue(comparator.compare(stc2, stc1) < 0);

        Assert.assertFalse(StoichiometryComparator.areEquals(stc1, stc2));
        Assert.assertTrue(StoichiometryComparator.hashCode(stc1) != StoichiometryComparator.hashCode(stc2));
    }

    @Test
    public void test_compare_different_stoichiometry_ranges() throws Exception {
        Stoichiometry stc1 = new DefaultStoichiometry(2, 5);
        Stoichiometry stc2 = new DefaultStoichiometry(1, 4);

        Assert.assertTrue(comparator.compare(stc1, stc2) > 0);
        Assert.assertTrue(comparator.compare(stc2, stc1) < 0);

        Assert.assertFalse(StoichiometryComparator.areEquals(stc1, stc2));
        Assert.assertTrue(StoichiometryComparator.hashCode(stc1) != StoichiometryComparator.hashCode(stc2));
    }

    @Test
    public void test_compare_same_minValue_different_maxValue() throws Exception {
        Stoichiometry stc1 = new DefaultStoichiometry(2, 5);
        Stoichiometry stc2 = new DefaultStoichiometry(2, 6);

        Assert.assertTrue(comparator.compare(stc1, stc2) < 0);
        Assert.assertTrue(comparator.compare(stc2, stc1) > 0);

        Assert.assertFalse(StoichiometryComparator.areEquals(stc1, stc2));
        Assert.assertTrue(StoichiometryComparator.hashCode(stc1) != StoichiometryComparator.hashCode(stc2));
    }

    @Test
    public void test_compare_same_stoichiometry_range() throws Exception {
        Stoichiometry stc1 = new DefaultStoichiometry(2, 5);
        Stoichiometry stc2 = new DefaultStoichiometry(2, 5);

        Assert.assertTrue(comparator.compare(stc1, stc2) == 0);
        Assert.assertTrue(comparator.compare(stc2, stc1) == 0);

        Assert.assertTrue(StoichiometryComparator.areEquals(stc1, stc2));
        Assert.assertTrue(StoichiometryComparator.hashCode(stc1) == StoichiometryComparator.hashCode(stc2));
    }
}
