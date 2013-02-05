package psidev.psi.mi.jami.model.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Unit tester for DefaultAlias
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultAliasTest {

    @Test
    public void test_create_alias() throws Exception {
        Alias geneName = new DefaultAlias("brca2");

        Assert.assertEquals("brca2", geneName.getName());
        Assert.assertNull(geneName.getType());
    }

    @Test
    public void test_create_alias_with_type() throws Exception {
        Alias geneName = new DefaultAlias(CvTermFactory.createGeneNameAliasType(), "brca2");

        Assert.assertEquals("brca2", geneName.getName());
        Assert.assertNotNull(geneName.getType());
        Assert.assertEquals(CvTermFactory.createGeneNameAliasType(), geneName.getType());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_alias_no_Name() throws Exception {
        Alias geneName = new DefaultAlias(null);
    }
}
