package psidev.psi.mi.jami.model.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ExternalIdentifier;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Unit tester for DefaultExternalIdentifier
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultExternalIdentifierTest {

    @Test
    public void test_create_external_identifier() throws Exception {
        ExternalIdentifier id1 = new DefaultExternalIdentifier(CvTermFactory.createMICvTerm("uniprotkb", null), "P12345");

        Assert.assertEquals(CvTermFactory.createMICvTerm("uniprotkb", null), id1.getDatabase());
        Assert.assertEquals("P12345", id1.getId());
        Assert.assertNull(id1.getVersion());
        Assert.assertNotNull(id1.getQualifier());
        Assert.assertEquals(CvTermFactory.createMICvTerm("identity", null), id1.getQualifier());
    }

    @Test
    public void test_create_external_identifier_with_version() throws Exception {
        ExternalIdentifier id1 = new DefaultExternalIdentifier(CvTermFactory.createMICvTerm("uniprotkb", null), "P12345", 2);

        Assert.assertEquals(CvTermFactory.createMICvTerm("uniprotkb", null), id1.getDatabase());
        Assert.assertEquals("P12345", id1.getId());
        Assert.assertNotNull(id1.getVersion());
        Assert.assertTrue(id1.getVersion() == 2);
        Assert.assertNotNull(id1.getQualifier());
        Assert.assertEquals(CvTermFactory.createMICvTerm("identity", null), id1.getQualifier());
    }
}
