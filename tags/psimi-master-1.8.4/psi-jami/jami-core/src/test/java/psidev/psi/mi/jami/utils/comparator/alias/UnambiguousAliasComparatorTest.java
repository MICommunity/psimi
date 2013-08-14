package psidev.psi.mi.jami.utils.comparator.alias;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for UnambiguousAliasComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class UnambiguousAliasComparatorTest {

    private UnambiguousAliasComparator comparator = new UnambiguousAliasComparator();

    @Test
    public void test_alias_null_after() throws Exception {
        Alias alias1 = null;
        Alias alias2 = new DefaultAlias("test");

        Assert.assertTrue(comparator.compare(alias1, alias2) > 0);
        Assert.assertTrue(comparator.compare(alias2, alias1) < 0);

        Assert.assertFalse(UnambiguousAliasComparator.areEquals(alias1, alias2));
        Assert.assertTrue(UnambiguousAliasComparator.hashCode(alias1) != UnambiguousAliasComparator.hashCode(alias2));
    }

    @Test
    public void test_alias_type_comparison() throws Exception {
        Alias alias1 = new DefaultAlias(new DefaultCvTerm("synonym"), "brca2");
        Alias alias2 = new DefaultAlias(CvTermUtils.createGeneNameAliasType(), "brca2");

        Assert.assertTrue(comparator.compare(alias1, alias2) != 0);

        Assert.assertFalse(UnambiguousAliasComparator.areEquals(alias1, alias2));
        Assert.assertTrue(UnambiguousAliasComparator.hashCode(alias1) != UnambiguousAliasComparator.hashCode(alias2));
    }

    @Test
    public void test_alias_name_comparison() throws Exception {
        Alias alias1 = new DefaultAlias(CvTermUtils.createGeneNameAliasType(), "brca2");
        Alias alias2 = new DefaultAlias(CvTermUtils.createGeneNameAliasType(), "brca2");

        Assert.assertTrue(comparator.compare(alias1, alias2) == 0);

        Assert.assertTrue(UnambiguousAliasComparator.areEquals(alias1, alias2));
        Assert.assertTrue(UnambiguousAliasComparator.hashCode(alias1) == UnambiguousAliasComparator.hashCode(alias2));
    }

    @Test
    public void test_alias_name_case_sensitive() throws Exception {
        Alias alias1 = new DefaultAlias(CvTermUtils.createGeneNameAliasType(), "bRCa2");
        Alias alias2 = new DefaultAlias(CvTermUtils.createGeneNameAliasType(), "brca2");

        Assert.assertTrue(comparator.compare(alias1, alias2) != 0);

        Assert.assertFalse(UnambiguousAliasComparator.areEquals(alias1, alias2));
        Assert.assertTrue(UnambiguousAliasComparator.hashCode(alias1) != UnambiguousAliasComparator.hashCode(alias2));
    }
}
