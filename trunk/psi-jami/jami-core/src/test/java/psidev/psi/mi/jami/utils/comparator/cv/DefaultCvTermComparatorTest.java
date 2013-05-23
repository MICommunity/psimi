package psidev.psi.mi.jami.utils.comparator.cv;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for DefaultCvTermComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultCvTermComparatorTest {

    private DefaultCvTermComparator comparator = new DefaultCvTermComparator();

    @Test
    public void test_cv_null_after() throws Exception {
        CvTerm term1 = null;
        CvTerm term2 = CvTermUtils.createChebiDatabase();

        Assert.assertTrue(comparator.compare(term1, term2) > 0);
        Assert.assertTrue(comparator.compare(term2, term1) < 0);

        Assert.assertFalse(DefaultCvTermComparator.areEquals(term1, term2));
    }

    @Test
    public void test_cv_shortName_comparison_if_one_ontology_id_null() throws Exception {
        // null identifier
        CvTerm term1 = CvTermUtils.createMICvTerm("chebi", null);
        // chebi identifier
        CvTerm term2 = CvTermUtils.createChebiDatabase();

        Assert.assertTrue(comparator.compare(term1, term2) == 0);
        Assert.assertTrue(comparator.compare(term2, term1) == 0);

        Assert.assertTrue(DefaultCvTermComparator.areEquals(term1, term2));
    }

    @Test
    public void test_cv_identifier_comparison() throws Exception {
        // uniprokb identifier
        CvTerm term1 = CvTermUtils.createMICvTerm("chebi", Xref.UNIPROTKB_MI);
        // chebi identifier
        CvTerm term2 = CvTermUtils.createMICvTerm("chebi", Xref.CHEBI_MI);

        Assert.assertTrue(comparator.compare(term1, term2) != 0);
        Assert.assertTrue(comparator.compare(term2, term1) != 0);

        Assert.assertFalse(DefaultCvTermComparator.areEquals(term1, term2));
    }

    @Test
    public void test_at_least_one_matching_identifier() throws Exception {
        CvTerm term1 = new DefaultCvTerm("cell1");
        term1.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("cabri"), "TEST1"));

        CvTerm term2 = new DefaultCvTerm("cell1");
        term2.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("cabri"), "TEST1"));
        term2.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("databaseTest"), "TEST2"));

        Assert.assertTrue(comparator.compare(term1, term2) == 0);
        Assert.assertTrue(comparator.compare(term2, term1) == 0);

        Assert.assertTrue(DefaultCvTermComparator.areEquals(term1, term2));
    }

    @Test
    public void test_no_matching_identifier() throws Exception {
        CvTerm term1 = new DefaultCvTerm("cell1");
        term1.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("cabri"), "TEST1"));

        CvTerm term2 = new DefaultCvTerm("cell1");
        term2.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("databaseTest"), "TEST2"));

        Assert.assertTrue(comparator.compare(term1, term2) != 0);
        Assert.assertTrue(comparator.compare(term2, term1) != 0);

        Assert.assertFalse(DefaultCvTermComparator.areEquals(term1, term2));
    }

    @Test
    public void test_cv_shortName_case_insensitive() throws Exception {
        CvTerm term1 = CvTermUtils.createMICvTerm("chebi", null);
        CvTerm term2 = CvTermUtils.createMICvTerm("CHeBi", null);
        term2.setFullName("chebi database");

        Assert.assertTrue(comparator.compare(term1, term2) == 0);
        Assert.assertTrue(comparator.compare(term2, term1) == 0);

        Assert.assertTrue(DefaultCvTermComparator.areEquals(term1, term2));
    }

    @Test
    public void test_cv_shortName_comparison() throws Exception {
        // uniprokb
        CvTerm term1 = CvTermUtils.createMICvTerm("uniprokb", null);
        // chebi
        CvTerm term2 = CvTermUtils.createMICvTerm("chebi", null);
        term2.setFullName("chebi database");

        Assert.assertTrue(comparator.compare(term1, term2) != 0);
        Assert.assertTrue(comparator.compare(term2, term1) != 0);

        Assert.assertFalse(DefaultCvTermComparator.areEquals(term1, term2));
    }
}
