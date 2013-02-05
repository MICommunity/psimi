package psidev.psi.mi.jami.model.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Unit tester for DefaultCvTerm
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultCvTermTest {

    @Test
    public void test_create_cvTerm() throws Exception {
        CvTerm uniprotkb = new DefaultCvTerm("uniprotkb");

        Assert.assertEquals("uniprotkb", uniprotkb.getShortName());
        Assert.assertNotNull(uniprotkb.getAnnotations());
        Assert.assertNotNull(uniprotkb.getParents());
        Assert.assertNotNull(uniprotkb.getChildren());
        Assert.assertNotNull(uniprotkb.getSynonyms());
        Assert.assertNotNull(uniprotkb.getXrefs());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_cvterm_no_shortName() throws Exception {
        CvTerm uniprotkb = new DefaultCvTerm(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_set_null_shortName() throws Exception {
        CvTerm uniprotkb = new DefaultCvTerm("uniprotkb");
        uniprotkb.setShortName(null);
    }

    @Test
    public void test_create_cvTerm_ontologyId() throws Exception {
        CvTerm uniprotkb = new DefaultCvTerm("uniprotkb", new DefaultExternalIdentifier(CvTermFactory.createPsiMiDatabaseNameOnly(), Xref.UNIPROTKB_ID));

        Assert.assertEquals("uniprotkb", uniprotkb.getShortName());
        Assert.assertEquals(new DefaultExternalIdentifier(CvTermFactory.createPsiMiDatabaseNameOnly(), Xref.UNIPROTKB_ID), uniprotkb.getOntologyIdentifier());
        Assert.assertNotNull(uniprotkb.getAnnotations());
        Assert.assertNotNull(uniprotkb.getParents());
        Assert.assertNotNull(uniprotkb.getChildren());
        Assert.assertNotNull(uniprotkb.getSynonyms());
        Assert.assertNotNull(uniprotkb.getXrefs());
    }

    @Test
    public void test_create_cvTerm_fullName() throws Exception {
        CvTerm uniprotkb = new DefaultCvTerm("uniprotkb", "uniprot knowledge database", new DefaultExternalIdentifier(CvTermFactory.createPsiMiDatabaseNameOnly(), Xref.UNIPROTKB_ID));

        Assert.assertEquals("uniprotkb", uniprotkb.getShortName());
        Assert.assertEquals("uniprot knowledge database", uniprotkb.getFullName());
        Assert.assertEquals(new DefaultExternalIdentifier(CvTermFactory.createPsiMiDatabaseNameOnly(), Xref.UNIPROTKB_ID), uniprotkb.getOntologyIdentifier());
        Assert.assertNotNull(uniprotkb.getAnnotations());
        Assert.assertNotNull(uniprotkb.getParents());
        Assert.assertNotNull(uniprotkb.getChildren());
        Assert.assertNotNull(uniprotkb.getSynonyms());
        Assert.assertNotNull(uniprotkb.getXrefs());
    }

    @Test
    public void test_create_cvTerm_definition() throws Exception {
        CvTerm uniprotkb = new DefaultCvTerm("uniprotkb", "uniprot knowledge database", new DefaultExternalIdentifier(CvTermFactory.createPsiMiDatabaseNameOnly(), Xref.UNIPROTKB_ID),
                "sequence database for proteins");

        Assert.assertEquals("uniprotkb", uniprotkb.getShortName());
        Assert.assertEquals("uniprot knowledge database", uniprotkb.getFullName());
        Assert.assertEquals("sequence database for proteins", uniprotkb.getDefinition());
        Assert.assertEquals(new DefaultExternalIdentifier(CvTermFactory.createPsiMiDatabaseNameOnly(), Xref.UNIPROTKB_ID), uniprotkb.getOntologyIdentifier());
        Assert.assertNotNull(uniprotkb.getAnnotations());
        Assert.assertNotNull(uniprotkb.getParents());
        Assert.assertNotNull(uniprotkb.getChildren());
        Assert.assertNotNull(uniprotkb.getSynonyms());
        Assert.assertNotNull(uniprotkb.getXrefs());
    }
}
