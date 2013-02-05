package psidev.psi.mi.jami.utils.comparator.xref;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ExternalIdentifier;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultExternalIdentifier;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Unit tester for DefaultExternalIdentifierComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultExternalIdentifierComparatorTest {

    private DefaultExternalIdentifierComparator comparator = new DefaultExternalIdentifierComparator();

    @Test
    public void test_identifier_null_after() throws Exception {
        ExternalIdentifier id1 = null;
        ExternalIdentifier id2 = new DefaultExternalIdentifier(CvTermFactory.createChebiDatabase(), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) > 0);
        Assert.assertTrue(comparator.compare(id2, id1) < 0);
    }

    @Test
    public void test_database_name_comparison_if_one_database_id_null() throws Exception {
        ExternalIdentifier id1 = new DefaultExternalIdentifier(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx");
        ExternalIdentifier id2 = new DefaultExternalIdentifier(CvTermFactory.createChebiDatabase(), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) == 0);
        Assert.assertTrue(comparator.compare(id2, id1) == 0);
    }

    @Test
    public void test_database_name_case_insensitive() throws Exception {
        ExternalIdentifier id1 = new DefaultExternalIdentifier(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xxx");
        ExternalIdentifier id2 = new DefaultExternalIdentifier(CvTermFactory.createMICvTerm("CheBi ", null), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) == 0);
        Assert.assertTrue(comparator.compare(id2, id1) == 0);
    }

    @Test
    public void test_database_id_comparison() throws Exception {
        ExternalIdentifier id1 = new DefaultExternalIdentifier(CvTermFactory.createUniprotkbDatabase(), "P12345");
        ExternalIdentifier id2 = new DefaultExternalIdentifier(CvTermFactory.createChebiDatabase(), "CHEBI:xx2");

        Assert.assertEquals(comparator.compare(id1, id2), Xref.UNIPROTKB_ID.compareTo(Xref.CHEBI_ID));
        Assert.assertEquals(comparator.compare(id2, id1), Xref.CHEBI_ID.compareTo(Xref.UNIPROTKB_ID));
    }

    @Test
    public void test_database_name_comparison() throws Exception {
        ExternalIdentifier id1 = new DefaultExternalIdentifier(CvTermFactory.createMICvTerm("uniprotkb", null), "P12345");
        ExternalIdentifier id2 = new DefaultExternalIdentifier(CvTermFactory.createMICvTerm("chebi", null), "CHEBI:xx2");

        Assert.assertEquals(comparator.compare(id1, id2), Xref.UNIPROTKB.compareTo(Xref.CHEBI));
        Assert.assertEquals(comparator.compare(id2, id1), Xref.CHEBI.compareTo(Xref.UNIPROTKB));
    }

    @Test
    public void test_id_comparison() throws Exception {
        ExternalIdentifier id1 = new DefaultExternalIdentifier(CvTermFactory.createChebiDatabase(), "CHEBI:xx1");
        ExternalIdentifier id2 = new DefaultExternalIdentifier(CvTermFactory.createChebiDatabase(), "CHEBI:xx2");

        Assert.assertEquals(comparator.compare(id1, id2), "CHEBI:xx1".compareTo("CHEBI:xx2"));
        Assert.assertEquals(comparator.compare(id2, id1), "CHEBI:xx2".compareTo("CHEBI:xx1"));
    }

    @Test
    public void test_id_case_sensitive() throws Exception {
        ExternalIdentifier id1 = new DefaultExternalIdentifier(CvTermFactory.createMICvTerm("chebi", null), "CHEbi:xXx");
        ExternalIdentifier id2 = new DefaultExternalIdentifier(CvTermFactory.createMICvTerm("CheBi ", null), "CHEBI:xxx");

        Assert.assertTrue(comparator.compare(id1, id2) != 0);
        Assert.assertTrue(comparator.compare(id2, id1) != 0);
    }
}
