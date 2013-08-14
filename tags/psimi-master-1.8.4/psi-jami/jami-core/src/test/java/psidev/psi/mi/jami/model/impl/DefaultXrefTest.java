package psidev.psi.mi.jami.model.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for DefaultExternalIdentifier
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultXrefTest {

    @Test
    public void test_create_external_identifier() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("uniprotkb", null), "P12345");

        Assert.assertEquals(CvTermUtils.createMICvTerm("uniprotkb", null), id1.getDatabase());
        Assert.assertEquals("P12345", id1.getId());
        Assert.assertNull(id1.getVersion());
        Assert.assertNull(id1.getQualifier());
    }

    @Test
    public void test_create_external_identifier_with_version() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("uniprotkb", null), "P12345", "2");

        Assert.assertEquals(CvTermUtils.createMICvTerm("uniprotkb", null), id1.getDatabase());
        Assert.assertEquals("P12345", id1.getId());
        Assert.assertNotNull(id1.getVersion());
        Assert.assertEquals(id1.getVersion(),"2");
        Assert.assertNull(id1.getQualifier());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_external_identifier_no_database() throws Exception {
        Xref id1 = new DefaultXref(null, "P12345");
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_external_identifier_no_id() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("uniprotkb", null), null);
    }

    @Test
    public void test_create_xref_with_qualifier() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("uniprotkb", null), "P12345", CvTermUtils.createIdentityQualifier());

        Assert.assertEquals(CvTermUtils.createMICvTerm("uniprotkb", null), id1.getDatabase());
        Assert.assertEquals("P12345", id1.getId());
        Assert.assertNull(id1.getVersion());
        Assert.assertNotNull(id1.getQualifier());
        Assert.assertEquals(CvTermUtils.createMICvTerm("identity", "MI:0356"), id1.getQualifier());
    }

    @Test
    public void test_create_xref_with_qualifier_and_version() throws Exception {
        Xref id1 = new DefaultXref(CvTermUtils.createMICvTerm("uniprotkb", null), "P12345", "2", CvTermUtils.createIdentityQualifier());

        Assert.assertEquals(CvTermUtils.createMICvTerm("uniprotkb", null), id1.getDatabase());
        Assert.assertEquals("P12345", id1.getId());
        Assert.assertNotNull(id1.getVersion());
        Assert.assertEquals(id1.getVersion() , "2");
        Assert.assertNotNull(id1.getQualifier());
        Assert.assertEquals(CvTermUtils.createMICvTerm("identity", "MI:0356"), id1.getQualifier());
    }
}
