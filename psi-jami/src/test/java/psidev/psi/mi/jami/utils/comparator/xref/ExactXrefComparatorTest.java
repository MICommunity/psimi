package psidev.psi.mi.jami.utils.comparator.xref;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Unit tester for ExactXrefComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class ExactXrefComparatorTest {

    private ExactXrefComparator comparator = new ExactXrefComparator();

    @Test
    public void test_identifier_null_after() throws Exception {
        Xref id1 = null;
        Xref id2 = new DefaultXref(CvTermFactory.createChebiDatabase(), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) > 0);
        Assert.assertTrue(comparator.compare(id2, id1) < 0);

        Assert.assertFalse(ExactXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(ExactXrefComparator.hashCode(id1) != ExactXrefComparator.hashCode(id2));
    }

    @Test
    public void test_database_id_null_after() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx");
        Xref id2 = new DefaultXref(CvTermFactory.createChebiDatabase(), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) > 0);
        Assert.assertTrue(comparator.compare(id2, id1) < 0);

        Assert.assertFalse(ExactXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(ExactXrefComparator.hashCode(id1) != ExactXrefComparator.hashCode(id2));
    }

    @Test
    public void test_database_name_if_ids_null() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx");
        Xref id2 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) == 0);
        Assert.assertTrue(comparator.compare(id2, id1) == 0);

        Assert.assertTrue(ExactXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(ExactXrefComparator.hashCode(id1) == ExactXrefComparator.hashCode(id2));
    }

    @Test
    public void test_database_not_mi_after() throws Exception {
        Xref id1 = new DefaultXref(new DefaultCvTerm("chebi", new DefaultXref(new DefaultCvTerm("test"), "XXX")), "CHEBI:xxx");
        Xref id2 = new DefaultXref(CvTermFactory.createChebiDatabase(), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) > 0);
        Assert.assertTrue(comparator.compare(id2, id1) < 0);

        Assert.assertFalse(ExactXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(ExactXrefComparator.hashCode(id1) != ExactXrefComparator.hashCode(id2));
    }

    @Test
    public void test_database_name_if_not_mi() throws Exception {
        Xref id1 = new DefaultXref(new DefaultCvTerm("chebi", new DefaultXref(new DefaultCvTerm("test"), "XXX1")), "CHEBI:xxx");
        Xref id2 = new DefaultXref(new DefaultCvTerm("chebi", new DefaultXref(new DefaultCvTerm("test"), "XXX2")), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) == 0);
        Assert.assertTrue(comparator.compare(id2, id1) == 0);

        Assert.assertTrue(ExactXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(ExactXrefComparator.hashCode(id1) == ExactXrefComparator.hashCode(id2));
    }

    @Test
    public void test_database_name_case_insensitive() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx");
        Xref id2 = new DefaultXref(CvTermFactory.createMICvTerm("CheBi ", null), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) == 0);
        Assert.assertTrue(comparator.compare(id2, id1) == 0);

        Assert.assertTrue(ExactXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(ExactXrefComparator.hashCode(id1) == ExactXrefComparator.hashCode(id2));
    }

    @Test
    public void test_database_id_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createUniprotkbDatabase(), "P12345");
        Xref id2 = new DefaultXref(CvTermFactory.createChebiDatabase(), "CHEBI:xx2");

        Assert.assertEquals(comparator.compare(id1, id2), Xref.UNIPROTKB_MI.compareTo(Xref.CHEBI_MI));
        Assert.assertEquals(comparator.compare(id2, id1), Xref.CHEBI_MI.compareTo(Xref.UNIPROTKB_MI));

        Assert.assertFalse(ExactXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(ExactXrefComparator.hashCode(id1) != ExactXrefComparator.hashCode(id2));
    }

    @Test
    public void test_database_name_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("uniprotkb", null), "P12345");
        Xref id2 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xx2");

        Assert.assertEquals(comparator.compare(id1, id2), Xref.UNIPROTKB.compareTo(Xref.CHEBI));
        Assert.assertEquals(comparator.compare(id2, id1), Xref.CHEBI.compareTo(Xref.UNIPROTKB));

        Assert.assertFalse(ExactXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(ExactXrefComparator.hashCode(id1) != ExactXrefComparator.hashCode(id2));
    }

    @Test
    public void test_id_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createChebiDatabase(), "CHEBI:xx1");
        Xref id2 = new DefaultXref(CvTermFactory.createChebiDatabase(), "CHEBI:xx2");

        Assert.assertEquals(comparator.compare(id1, id2), "CHEBI:xx1".compareTo("CHEBI:xx2"));
        Assert.assertEquals(comparator.compare(id2, id1), "CHEBI:xx2".compareTo("CHEBI:xx1"));

        Assert.assertFalse(ExactXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(ExactXrefComparator.hashCode(id1) != ExactXrefComparator.hashCode(id2));
    }

    @Test
    public void test_id_case_sensitive() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEbi:xXx");
        Xref id2 = new DefaultXref(CvTermFactory.createMICvTerm("CheBi ", null), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) != 0);
        Assert.assertTrue(comparator.compare(id2, id1) != 0);

        Assert.assertFalse(ExactXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(ExactXrefComparator.hashCode(id1) != ExactXrefComparator.hashCode(id2));
    }

    @Test
    public void test_version_null_after() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx");
        Xref id2 = new DefaultXref(CvTermFactory.createMICvTerm("CheBi ", null), "CHEBI:xxx", 2);

        Assert.assertTrue(comparator.compare(id1, id2) > 0);
        Assert.assertTrue(comparator.compare(id2, id1) < 0);

        Assert.assertFalse(ExactXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(ExactXrefComparator.hashCode(id1) != ExactXrefComparator.hashCode(id2));
    }

    @Test
    public void test_version_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", 2);
        Xref id2 = new DefaultXref(CvTermFactory.createMICvTerm("CheBi ", null), "CHEBI:xxx", 1);

        Assert.assertTrue(comparator.compare(id1, id2) > 0);
        Assert.assertTrue(comparator.compare(id2, id1) < 0);

        Assert.assertFalse(ExactXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(ExactXrefComparator.hashCode(id1) != ExactXrefComparator.hashCode(id2));
    }

    @Test
    public void test_qualifier_null_after() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermFactory.createMICvTerm(Xref.IDENTITY, Xref.IDENTITY_MI));
        Xref id2 = new DefaultXref(CvTermFactory.createMICvTerm("CheBi ", null), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) < 0);
        Assert.assertTrue(comparator.compare(id2, id1) > 0);
        Assert.assertFalse(UnambiguousXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(UnambiguousXrefComparator.hashCode(id1) != UnambiguousXrefComparator.hashCode(id2));

    }

    @Test
    public void test_qualifier_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermFactory.createMICvTerm(Xref.IDENTITY, Xref.IDENTITY_MI));
        Xref id2 = new DefaultXref(CvTermFactory.createMICvTerm("CheBi ", null), "CHEBI:xxx", CvTermFactory.createMICvTerm(Xref.SECONDARY, Xref.SECONDARY_MI));

        Assert.assertTrue(comparator.compare(id1, id2) != 0);
        Assert.assertTrue(comparator.compare(id2, id1) != 0);
        Assert.assertFalse(UnambiguousXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(UnambiguousXrefComparator.hashCode(id1) != UnambiguousXrefComparator.hashCode(id2));

    }

    @Test
    public void test_qualifier_id_null_after() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermFactory.createMICvTerm("identity", null));
        Xref id2 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermFactory.createMICvTerm("identity", "MI:0356"));

        Assert.assertTrue(comparator.compare(id1, id2) > 0);
        Assert.assertTrue(comparator.compare(id2, id1) < 0);

        Assert.assertFalse(UnambiguousXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(UnambiguousXrefComparator.hashCode(id1) != UnambiguousXrefComparator.hashCode(id2));
    }

    @Test
    public void test_qualifier_name_if_ids_null() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermFactory.createMICvTerm("identity", null));
        Xref id2 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermFactory.createMICvTerm("identity", null));

        Assert.assertTrue(comparator.compare(id1, id2) == 0);
        Assert.assertTrue(comparator.compare(id2, id1) == 0);

        Assert.assertTrue(UnambiguousXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(UnambiguousXrefComparator.hashCode(id1) == UnambiguousXrefComparator.hashCode(id2));
    }

    @Test
    public void test_qualifier_id_notMi_after() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", new DefaultCvTerm("identity", new DefaultXref(new DefaultCvTerm("test"), "XXX")));
        Xref id2 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermFactory.createMICvTerm("identity", "MI:0356"));

        Assert.assertTrue(comparator.compare(id1, id2) > 0);
        Assert.assertTrue(comparator.compare(id2, id1) < 0);

        Assert.assertFalse(UnambiguousXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(UnambiguousXrefComparator.hashCode(id1) != UnambiguousXrefComparator.hashCode(id2));
    }

    @Test
    public void test_qualifier_name_if_notMi() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", new DefaultCvTerm("identity", new DefaultXref(new DefaultCvTerm("test"), "XXX1")));
        Xref id2 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", new DefaultCvTerm("identity", new DefaultXref(new DefaultCvTerm("test"), "XXX2")));

        Assert.assertTrue(comparator.compare(id1, id2) == 0);
        Assert.assertTrue(comparator.compare(id2, id1) == 0);

        Assert.assertTrue(UnambiguousXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(UnambiguousXrefComparator.hashCode(id1) == UnambiguousXrefComparator.hashCode(id2));
    }

    @Test
    public void test_qualifier_name_case_insensitive() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermFactory.createMICvTerm("IDENTITy ", null));
        Xref id2 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermFactory.createMICvTerm("identity", null));

        Assert.assertTrue(comparator.compare(id1, id2) == 0);
        Assert.assertTrue(comparator.compare(id2, id1) == 0);

        Assert.assertTrue(UnambiguousXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(UnambiguousXrefComparator.hashCode(id1) == UnambiguousXrefComparator.hashCode(id2));
    }

    @Test
    public void test_qualifier_id_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermFactory.createMICvTerm("identity", "MI:xxx"));
        Xref id2 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermFactory.createMICvTerm("identity", "MI:0356"));

        Assert.assertTrue(comparator.compare(id1, id2) != 0);
        Assert.assertTrue(comparator.compare(id2, id1) != 0);

        Assert.assertFalse(UnambiguousXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(UnambiguousXrefComparator.hashCode(id1) != UnambiguousXrefComparator.hashCode(id2));
    }

    @Test
    public void test_qualifier_name_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermFactory.createMICvTerm("identity", null));
        Xref id2 = new DefaultXref(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermFactory.createMICvTerm("primary-reference", null));

        Assert.assertTrue(comparator.compare(id1, id2) != 0);
        Assert.assertTrue(comparator.compare(id2, id1) != 0);

        Assert.assertFalse(UnambiguousXrefComparator.areEquals(id1, id2));
        Assert.assertTrue(UnambiguousXrefComparator.hashCode(id1) != UnambiguousXrefComparator.hashCode(id2));
    }
}
