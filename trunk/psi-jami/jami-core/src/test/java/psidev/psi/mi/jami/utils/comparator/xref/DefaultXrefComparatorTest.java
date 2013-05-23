package psidev.psi.mi.jami.utils.comparator.xref;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for DefaultXrefComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class DefaultXrefComparatorTest {

    private DefaultXrefComparator comparator = new DefaultXrefComparator();

    @Test
    public void test_identifier_null_after() throws Exception {
        Xref id1 = null;
        Xref id2 = new DefaultXref(CvTermUtils.createChebiDatabase(), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) > 0);
        Assert.assertTrue(comparator.compare(id2, id1) < 0);

        Assert.assertFalse(DefaultXrefComparator.areEquals(id1, id2));
    }

    @Test
    public void test_database_name_comparison_if_one_database_id_null() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx");
        Xref id2 = new DefaultXref(CvTermUtils.createChebiDatabase(), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) == 0);
        Assert.assertTrue(comparator.compare(id2, id1) == 0);
        Assert.assertTrue(DefaultXrefComparator.areEquals(id1, id2));
    }

    @Test
    public void test_database_name_comparison_if_one_database_id_notMi() throws Exception {
        Xref id1 = new DefaultXref(new DefaultCvTerm("chebi", new DefaultXref(new DefaultCvTerm("test"), "XXX")), "CHEBI:xxx");
        Xref id2 = new DefaultXref(CvTermUtils.createChebiDatabase(), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) == 0);
        Assert.assertTrue(comparator.compare(id2, id1) == 0);
        Assert.assertTrue(DefaultXrefComparator.areEquals(id1, id2));
    }

    @Test
    public void test_database_name_case_insensitive() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx");
        Xref id2 = new DefaultXref(CvTermUtils.createMICvTerm("CheBi ", null), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) == 0);
        Assert.assertTrue(comparator.compare(id2, id1) == 0);

        Assert.assertTrue(DefaultXrefComparator.areEquals(id1, id2));
    }

    @Test
    public void test_database_id_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createUniprotkbDatabase(), "P12345");
        Xref id2 = new DefaultXref(CvTermUtils.createChebiDatabase(), "CHEBI:xx2");

        Assert.assertEquals(comparator.compare(id1, id2), Xref.UNIPROTKB_MI.compareTo(Xref.CHEBI_MI));
        Assert.assertEquals(comparator.compare(id2, id1), Xref.CHEBI_MI.compareTo(Xref.UNIPROTKB_MI));

        Assert.assertFalse(DefaultXrefComparator.areEquals(id1, id2));
    }

    @Test
    public void test_database_name_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("uniprotkb", null), "P12345");
        Xref id2 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xx2");

        Assert.assertEquals(comparator.compare(id1, id2), Xref.UNIPROTKB.compareTo(Xref.CHEBI));
        Assert.assertEquals(comparator.compare(id2, id1), Xref.CHEBI.compareTo(Xref.UNIPROTKB));

        Assert.assertFalse(DefaultXrefComparator.areEquals(id1, id2));
    }

    @Test
    public void test_id_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createChebiDatabase(), "CHEBI:xx1");
        Xref id2 = new DefaultXref(CvTermUtils.createChebiDatabase(), "CHEBI:xx2");

        Assert.assertEquals(comparator.compare(id1, id2), "CHEBI:xx1".compareTo("CHEBI:xx2"));
        Assert.assertEquals(comparator.compare(id2, id1), "CHEBI:xx2".compareTo("CHEBI:xx1"));

        Assert.assertFalse(DefaultXrefComparator.areEquals(id1, id2));
    }

    @Test
    public void test_id_case_sensitive() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEbi:xXx");
        Xref id2 = new DefaultXref(CvTermUtils.createMICvTerm("CheBi ", null), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) != 0);
        Assert.assertTrue(comparator.compare(id2, id1) != 0);

        Assert.assertFalse(DefaultXrefComparator.areEquals(id1, id2));
    }

    @Test
    public void test_qualifier_null_after() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermUtils.createMICvTerm(Xref.IDENTITY, Xref.IDENTITY_MI));
        Xref id2 = new DefaultXref(CvTermUtils.createMICvTerm("CheBi ", null), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) < 0);
        Assert.assertTrue(comparator.compare(id2, id1) > 0);
        Assert.assertFalse(DefaultXrefComparator.areEquals(id1, id2));

    }

    @Test
    public void test_qualifier_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermUtils.createMICvTerm(Xref.IDENTITY, Xref.IDENTITY_MI));
        Xref id2 = new DefaultXref(CvTermUtils.createMICvTerm("CheBi ", null), "CHEBI:xxx", CvTermUtils.createMICvTerm(Xref.SECONDARY, Xref.SECONDARY_MI));

        Assert.assertTrue(comparator.compare(id1, id2) != 0);
        Assert.assertTrue(comparator.compare(id2, id1) != 0);
        Assert.assertFalse(DefaultXrefComparator.areEquals(id1, id2));

    }

    @Test
    public void test_qualifier_name_comparison_if_one_qualifier_id_null() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermUtils.createMICvTerm("identity", null));
        Xref id2 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermUtils.createMICvTerm("identity", "MI:0356"));

        Assert.assertTrue(comparator.compare(id1, id2) == 0);
        Assert.assertTrue(comparator.compare(id2, id1) == 0);
        Assert.assertTrue(DefaultXrefComparator.areEquals(id1, id2));
    }

    @Test
    public void test_qualifier_name_comparison_if_one_qualifier_id_notMi() throws Exception {

        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx", new DefaultCvTerm("identity", new DefaultXref(new DefaultCvTerm("test"), "XXX")));
        Xref id2 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermUtils.createMICvTerm("identity", "MI:0356"));

        Assert.assertTrue(comparator.compare(id1, id2) == 0);
        Assert.assertTrue(comparator.compare(id2, id1) == 0);
        Assert.assertTrue(DefaultXrefComparator.areEquals(id1, id2));
    }

    @Test
    public void test_qualifier_name_case_insensitive() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermUtils.createMICvTerm("IdenTITY", null));
        Xref id2 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermUtils.createMICvTerm("identity", "MI:0356"));

        Assert.assertTrue(comparator.compare(id1, id2) == 0);
        Assert.assertTrue(comparator.compare(id2, id1) == 0);
        Assert.assertTrue(DefaultXrefComparator.areEquals(id1, id2));
    }

    @Test
    public void test_qualifier_id_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermUtils.createMICvTerm("identity", "MI:xxxx"));
        Xref id2 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermUtils.createMICvTerm("identity", "MI:0356"));

        Assert.assertTrue(comparator.compare(id1, id2) != 0);
        Assert.assertTrue(comparator.compare(id2, id1) != 0);
        Assert.assertFalse(DefaultXrefComparator.areEquals(id1, id2));
    }

    @Test
    public void test_qualifier_name_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermUtils.createMICvTerm("identity", null));
        Xref id2 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermUtils.createMICvTerm("primary-reference", null));

        Assert.assertTrue(comparator.compare(id1, id2) != 0);
        Assert.assertTrue(comparator.compare(id2, id1) != 0);
        Assert.assertFalse(DefaultXrefComparator.areEquals(id1, id2));
    }
}
