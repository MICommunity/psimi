package psidev.psi.mi.jami.model.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;

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
        CvTerm uniprotkb = new DefaultCvTerm("uniprotkb", new DefaultXref(CvTermUtils.createPsiMiDatabaseNameOnly(), Xref.UNIPROTKB_MI));

        Assert.assertEquals("uniprotkb", uniprotkb.getShortName());
        Assert.assertEquals(Xref.UNIPROTKB_MI, uniprotkb.getMIIdentifier());
        Assert.assertNotNull(uniprotkb.getAnnotations());
        Assert.assertNotNull(uniprotkb.getSynonyms());
        Assert.assertNotNull(uniprotkb.getXrefs());
    }

    @Test
    public void test_create_cvTerm_fullName() throws Exception {
        CvTerm uniprotkb = new DefaultCvTerm("uniprotkb", "uniprot knowledge database", new DefaultXref(CvTermUtils.createPsiMiDatabaseNameOnly(), Xref.UNIPROTKB_MI));

        Assert.assertEquals("uniprotkb", uniprotkb.getShortName());
        Assert.assertEquals("uniprot knowledge database", uniprotkb.getFullName());
        Assert.assertEquals(Xref.UNIPROTKB_MI, uniprotkb.getMIIdentifier());
        Assert.assertNotNull(uniprotkb.getAnnotations());
        Assert.assertNotNull(uniprotkb.getSynonyms());
        Assert.assertNotNull(uniprotkb.getXrefs());
    }

    @Test
    public void test_create_cvTerm_definition() throws Exception {
        OntologyTerm uniprotkb = new DefaultOntologyTerm("uniprotkb", "uniprot knowledge database", new DefaultXref(CvTermUtils.createPsiMiDatabaseNameOnly(), Xref.UNIPROTKB_MI),
                "sequence database for proteins");

        Assert.assertEquals("uniprotkb", uniprotkb.getShortName());
        Assert.assertEquals("uniprot knowledge database", uniprotkb.getFullName());
        Assert.assertEquals("sequence database for proteins", uniprotkb.getDefinition());
        Assert.assertEquals(Xref.UNIPROTKB_MI, uniprotkb.getMIIdentifier());
        Assert.assertNotNull(uniprotkb.getAnnotations());
        Assert.assertNotNull(uniprotkb.getParents());
        Assert.assertNotNull(uniprotkb.getChildren());
        Assert.assertNotNull(uniprotkb.getSynonyms());
        Assert.assertNotNull(uniprotkb.getXrefs());
    }

    @Test
    public void test_create_cvTerm_mi() throws Exception {
        CvTerm uniprotkb = new DefaultCvTerm("uniprotkb", Xref.UNIPROTKB_MI);

        Assert.assertEquals("uniprotkb", uniprotkb.getShortName());
        Assert.assertEquals(Xref.UNIPROTKB_MI, uniprotkb.getMIIdentifier());
        Assert.assertNotNull(uniprotkb.getAnnotations());
        Assert.assertNotNull(uniprotkb.getSynonyms());
        Assert.assertNotNull(uniprotkb.getXrefs());

        Assert.assertTrue(uniprotkb.getIdentifiers().size() == 1);
        Assert.assertEquals(Xref.UNIPROTKB_MI, uniprotkb.getIdentifiers().iterator().next().getId());
        Assert.assertEquals(CvTerm.PSI_MI, uniprotkb.getIdentifiers().iterator().next().getDatabase().getShortName());
        Assert.assertEquals(Xref.IDENTITY, uniprotkb.getIdentifiers().iterator().next().getQualifier().getShortName());
    }

    @Test
    public void test_create_cvTerm_mod() throws Exception {
        CvTerm term = new DefaultCvTerm("modTerm", new DefaultXref(CvTermUtils.createPsiModDatabase(), "MOD:xxx"));

        Assert.assertEquals("modTerm", term.getShortName());
        Assert.assertEquals("MOD:xxx", term.getMODIdentifier());
        Assert.assertNull(term.getMIIdentifier());
        Assert.assertNotNull(term.getAnnotations());
        Assert.assertNotNull(term.getSynonyms());
        Assert.assertNotNull(term.getXrefs());

        Assert.assertTrue(term.getIdentifiers().size() == 1);
        Assert.assertEquals("MOD:xxx", term.getIdentifiers().iterator().next().getId());
        Assert.assertEquals(CvTerm.PSI_MOD, term.getIdentifiers().iterator().next().getDatabase().getShortName());
        Assert.assertNull(term.getIdentifiers().iterator().next().getQualifier());
    }

    @Test
    public void test_set_cvTerm_mi() throws Exception {
        CvTerm uniprotkb = new DefaultCvTerm("uniprotkb");
        uniprotkb.setMIIdentifier(Xref.UNIPROTKB_MI);

        Assert.assertEquals("uniprotkb", uniprotkb.getShortName());
        Assert.assertEquals(Xref.UNIPROTKB_MI, uniprotkb.getMIIdentifier());
        Assert.assertNotNull(uniprotkb.getAnnotations());
        Assert.assertNotNull(uniprotkb.getSynonyms());
        Assert.assertNotNull(uniprotkb.getXrefs());

        Assert.assertTrue(uniprotkb.getIdentifiers().size() == 1);
        Assert.assertEquals(Xref.UNIPROTKB_MI, uniprotkb.getIdentifiers().iterator().next().getId());
        Assert.assertEquals(CvTerm.PSI_MI, uniprotkb.getIdentifiers().iterator().next().getDatabase().getShortName());
        Assert.assertEquals(Xref.IDENTITY, uniprotkb.getIdentifiers().iterator().next().getQualifier().getShortName());
    }

    @Test
    public void test_set_cvTerm_mod() throws Exception {
        CvTerm term = new DefaultCvTerm("modTerm");
        term.setMODIdentifier("MOD:xxx");

        Assert.assertEquals("modTerm", term.getShortName());
        Assert.assertEquals("MOD:xxx", term.getMODIdentifier());
        Assert.assertNull(term.getMIIdentifier());
        Assert.assertNotNull(term.getAnnotations());
        Assert.assertNotNull(term.getSynonyms());
        Assert.assertNotNull(term.getXrefs());

        Assert.assertTrue(term.getIdentifiers().size() == 1);
        Assert.assertEquals("MOD:xxx", term.getIdentifiers().iterator().next().getId());
        Assert.assertEquals(CvTerm.PSI_MOD, term.getIdentifiers().iterator().next().getDatabase().getShortName());
        Assert.assertEquals(Xref.IDENTITY, term.getIdentifiers().iterator().next().getQualifier().getShortName());
    }

    @Test
    public void test_reset_cvTerm_mi() throws Exception {
        CvTerm uniprotkb = new DefaultCvTerm("uniprotkb", Xref.UNIPROTKB_MI);
        uniprotkb.setMIIdentifier("MI:xxx");

        Assert.assertEquals("uniprotkb", uniprotkb.getShortName());
        Assert.assertEquals("MI:xxx", uniprotkb.getMIIdentifier());
        Assert.assertNotNull(uniprotkb.getAnnotations());
        Assert.assertNotNull(uniprotkb.getSynonyms());
        Assert.assertNotNull(uniprotkb.getXrefs());

        Assert.assertTrue(uniprotkb.getIdentifiers().size() == 1);
        Assert.assertEquals("MI:xxx", uniprotkb.getIdentifiers().iterator().next().getId());
        Assert.assertEquals(CvTerm.PSI_MI, uniprotkb.getIdentifiers().iterator().next().getDatabase().getShortName());
        Assert.assertEquals(Xref.IDENTITY, uniprotkb.getIdentifiers().iterator().next().getQualifier().getShortName());
    }

    @Test
    public void test_reset_cvTerm_mod() throws Exception {
        CvTerm term = new DefaultCvTerm("modTerm", new DefaultXref(CvTermUtils.createPsiModDatabase(), "MOD:xxx"));
        term.setMODIdentifier("MOD:xxx3");

        Assert.assertEquals("modTerm", term.getShortName());
        Assert.assertEquals("MOD:xxx3", term.getMODIdentifier());
        Assert.assertNull(term.getMIIdentifier());
        Assert.assertNotNull(term.getAnnotations());
        Assert.assertNotNull(term.getSynonyms());
        Assert.assertNotNull(term.getXrefs());

        Assert.assertTrue(term.getIdentifiers().size() == 1);
        Assert.assertEquals("MOD:xxx3", term.getIdentifiers().iterator().next().getId());
        Assert.assertEquals(CvTerm.PSI_MOD, term.getIdentifiers().iterator().next().getDatabase().getShortName());
        Assert.assertEquals(Xref.IDENTITY, term.getIdentifiers().iterator().next().getQualifier().getShortName());
    }

    @Test
    public void test_add_cvTerm_mod_identity_replace_mod_without_identity() throws Exception {
        CvTerm term = new DefaultCvTerm("modTerm", new DefaultXref(CvTermUtils.createPsiModDatabase(), "MOD:xxx"));
        term.getIdentifiers().add(new DefaultXref(CvTermUtils.createPsiModDatabase(), "MOD:xxx3", CvTermUtils.createIdentityQualifier()));

        Assert.assertEquals("modTerm", term.getShortName());
        Assert.assertEquals("MOD:xxx3", term.getMODIdentifier());
        Assert.assertNull(term.getMIIdentifier());
        Assert.assertNotNull(term.getAnnotations());
        Assert.assertNotNull(term.getSynonyms());
        Assert.assertNotNull(term.getXrefs());

        Assert.assertTrue(term.getIdentifiers().size() == 2);
    }

    @Test
    public void test_add_cvTerm_mi_identity_replace_mi_without_identity() throws Exception {
        CvTerm term = new DefaultCvTerm("miTerm", new DefaultXref(CvTermUtils.createPsiMiDatabase(), "MI:xxx"));
        term.getIdentifiers().add(new DefaultXref(CvTermUtils.createPsiMiDatabase(), "MI:xxx3", CvTermUtils.createIdentityQualifier()));

        Assert.assertEquals("miTerm", term.getShortName());
        Assert.assertEquals("MI:xxx3", term.getMIIdentifier());
        Assert.assertNull(term.getMODIdentifier());
        Assert.assertNotNull(term.getAnnotations());
        Assert.assertNotNull(term.getSynonyms());
        Assert.assertNotNull(term.getXrefs());

        Assert.assertTrue(term.getIdentifiers().size() == 2);
    }

    @Test
    public void test_remove_cvTerm_mod_identity() throws Exception {
        Xref mod = new DefaultXref(CvTermUtils.createPsiModDatabase(), "MOD:xxx");
        CvTerm term = new DefaultCvTerm("modTerm", mod);
        term.getIdentifiers().remove(mod);

        Assert.assertEquals("modTerm", term.getShortName());
        Assert.assertNull(term.getMODIdentifier());
        Assert.assertNull(term.getMIIdentifier());
        Assert.assertNotNull(term.getAnnotations());
        Assert.assertNotNull(term.getSynonyms());
        Assert.assertNotNull(term.getXrefs());

        Assert.assertTrue(term.getIdentifiers().isEmpty());
    }

    @Test
    public void test_remove_cvTerm_mi_identity() throws Exception {
        Xref mi = new DefaultXref(CvTermUtils.createPsiMiDatabase(), "MI:xxx");
        CvTerm term = new DefaultCvTerm("miTerm", mi);
        term.getIdentifiers().remove(mi);

        Assert.assertEquals("miTerm", term.getShortName());
        Assert.assertNull(term.getMIIdentifier());
        Assert.assertNull(term.getMODIdentifier());
        Assert.assertNotNull(term.getAnnotations());
        Assert.assertNotNull(term.getSynonyms());
        Assert.assertNotNull(term.getXrefs());

        Assert.assertTrue(term.getIdentifiers().isEmpty());
    }

    @Test
    public void test_create_cvTerm_mi_remove_all_mi() throws Exception {
        CvTerm uniprotkb = new DefaultCvTerm("uniprotkb", Xref.UNIPROTKB_MI);
        uniprotkb.setMIIdentifier(null);

        Assert.assertEquals("uniprotkb", uniprotkb.getShortName());
        Assert.assertNull(uniprotkb.getMIIdentifier());
        Assert.assertNotNull(uniprotkb.getAnnotations());
        Assert.assertNotNull(uniprotkb.getSynonyms());
        Assert.assertNotNull(uniprotkb.getXrefs());

        Assert.assertTrue(uniprotkb.getIdentifiers().isEmpty());
    }

    @Test
    public void test_create_cvTerm_mod_remove_all_mod() throws Exception {
        CvTerm term = new DefaultCvTerm("modTerm", new DefaultXref(CvTermUtils.createPsiModDatabase(), "MOD:xxx"));
        term.setMODIdentifier(null);

        Assert.assertEquals("modTerm", term.getShortName());
        Assert.assertNull(term.getMODIdentifier());
        Assert.assertNull(term.getMIIdentifier());
        Assert.assertNotNull(term.getAnnotations());
        Assert.assertNotNull(term.getSynonyms());
        Assert.assertNotNull(term.getXrefs());

        Assert.assertTrue(term.getIdentifiers().isEmpty());
    }
}
