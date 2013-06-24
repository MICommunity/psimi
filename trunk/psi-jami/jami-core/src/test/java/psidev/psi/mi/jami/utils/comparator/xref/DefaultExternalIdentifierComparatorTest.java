package psidev.psi.mi.jami.utils.comparator.xref;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for DefaultExternalIdentifierComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultExternalIdentifierComparatorTest {

    @Test
    public void test_identifier_null_after() throws Exception {
        Xref id1 = null;
        Xref id2 = new DefaultXref(CvTermUtils.createChebiDatabase(), "CHEBI:xxx");

        Assert.assertFalse(DefaultExternalIdentifierComparator.areEquals(id1, id2));
    }

    @Test
    public void test_database_name_comparison_if_one_database_id_null() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx");
        Xref id2 = new DefaultXref(CvTermUtils.createChebiDatabase(), "CHEBI:xxx");

        Assert.assertTrue(DefaultExternalIdentifierComparator.areEquals(id1, id2));
    }

    @Test
    public void test_database_name_comparison_if_one_database_id_notMi() throws Exception {
        Xref id1 = new DefaultXref(new DefaultCvTerm("chebi", new DefaultXref(new DefaultCvTerm("test"), "XXX")), "CHEBI:xxx");
        Xref id2 = new DefaultXref(CvTermUtils.createChebiDatabase(), "CHEBI:xxx");

        Assert.assertTrue(DefaultXrefComparator.areEquals(id1, id2));
    }

    @Test
    public void test_database_name_case_insensitive() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx");
        Xref id2 = new DefaultXref(CvTermUtils.createMICvTerm("CheBi ", null), "CHEBI:xxx");

        Assert.assertTrue(DefaultExternalIdentifierComparator.areEquals(id1, id2));
    }

    @Test
    public void test_database_id_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createUniprotkbDatabase(), "P12345");
        Xref id2 = new DefaultXref(CvTermUtils.createChebiDatabase(), "CHEBI:xx2");

        Assert.assertFalse(DefaultExternalIdentifierComparator.areEquals(id1, id2));
    }

    @Test
    public void test_database_name_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("uniprotkb", null), "P12345");
        Xref id2 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xx2");

        Assert.assertFalse(DefaultExternalIdentifierComparator.areEquals(id1, id2));
    }

    @Test
    public void test_id_comparison() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createChebiDatabase(), "CHEBI:xx1");
        Xref id2 = new DefaultXref(CvTermUtils.createChebiDatabase(), "CHEBI:xx2");

        Assert.assertFalse(DefaultExternalIdentifierComparator.areEquals(id1, id2));
    }

    @Test
    public void test_id_case_sensitive() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEbi:xXx");
        Xref id2 = new DefaultXref(CvTermUtils.createMICvTerm("CheBi ", null), "CHEBI:xxx");

        Assert.assertFalse(DefaultExternalIdentifierComparator.areEquals(id1, id2));
    }

    @Test
    public void test_ignore_qualifier() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("chebi", null), "CHEBI:xxx", CvTermUtils.createMICvTerm(Xref.IDENTITY, Xref.IDENTITY_MI));
        Xref id2 = new DefaultXref(CvTermUtils.createMICvTerm("CheBi ", null), "CHEBI:xxx");

        Assert.assertTrue(DefaultExternalIdentifierComparator.areEquals(id1, id2));

    }
}
