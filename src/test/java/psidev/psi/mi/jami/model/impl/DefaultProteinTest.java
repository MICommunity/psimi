package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for DefaultProtein
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/13</pre>
 */

public class DefaultProteinTest {

    @Test
    public void create_protein_with_shortname(){

        Protein entity = new DefaultProtein("test");

        Assert.assertEquals(CvTermUtils.createProteinInteractorType(), entity.getInteractorType());
        Assert.assertEquals("test", entity.getShortName());
        Assert.assertNotNull(entity.getAliases());
        Assert.assertNotNull(entity.getIdentifiers());
        Assert.assertNotNull(entity.getXrefs());
        Assert.assertNotNull(entity.getAnnotations());
        Assert.assertNotNull(entity.getChecksums());
        Assert.assertNull(entity.getOrganism());

        entity = new DefaultProtein("test", (String) null);
        Assert.assertEquals(CvTermUtils.createProteinInteractorType(), entity.getInteractorType());
    }

    @Test
    public void create_protein_set_interactor_type_null(){

        Protein interactor = new DefaultProtein("test", CvTermUtils.createUnknownInteractorType());
        Assert.assertEquals(CvTermUtils.createUnknownInteractorType(), interactor.getInteractorType());

        interactor.setInteractorType(null);
        Assert.assertEquals(CvTermUtils.createProteinInteractorType(), interactor.getInteractorType());

        interactor.setInteractorType(CvTermUtils.createUnknownInteractorType());
        Assert.assertEquals(CvTermUtils.createUnknownInteractorType(), interactor.getInteractorType());
    }

    @Test
    public void test_set_unset_uniprot(){

        Protein interactor = new DefaultProtein("test");
        Assert.assertNull(interactor.getUniprotkb());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setUniprotkb("P12345");
        Assert.assertEquals("P12345", interactor.getUniprotkb());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.UNIPROTKB, Xref.UNIPROTKB_MI, "P12345"), interactor.getIdentifiers().iterator().next());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setUniprotkb("P12346");
        Assert.assertEquals("P12346", interactor.getUniprotkb());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.UNIPROTKB, Xref.UNIPROTKB_MI, "P12346"), interactor.getIdentifiers().iterator().next());

        interactor.getIdentifiers().clear();
        Assert.assertNull(interactor.getUniprotkb());
        Assert.assertEquals(0, interactor.getIdentifiers().size());

        interactor.getIdentifiers().add(XrefUtils.createXref(Xref.UNIPROTKB, Xref.UNIPROTKB_MI, "P12346"));
        Assert.assertEquals("P12346", interactor.getUniprotkb());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        interactor.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, Xref.UNIPROTKB_MI, "P12347"));
        Assert.assertEquals("P12347", interactor.getUniprotkb());
        Assert.assertEquals(2, interactor.getIdentifiers().size());

        interactor.setUniprotkb(null);
        Assert.assertNull(interactor.getUniprotkb());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
    }

    @Test
    public void test_set_unset_refseq(){

        Protein interactor = new DefaultProtein("test");
        Assert.assertNull(interactor.getRefseq());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setRefseq("xxxx1");
        Assert.assertEquals("xxxx1", interactor.getRefseq());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.REFSEQ, Xref.REFSEQ_MI, "xxxx1"), interactor.getIdentifiers().iterator().next());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setRefseq("xxxx2");
        Assert.assertEquals("xxxx2", interactor.getRefseq());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.REFSEQ, Xref.REFSEQ_MI, "xxxx2"), interactor.getIdentifiers().iterator().next());

        interactor.getIdentifiers().clear();
        Assert.assertNull(interactor.getRefseq());
        Assert.assertEquals(0, interactor.getIdentifiers().size());

        interactor.getIdentifiers().add(XrefUtils.createXref(Xref.REFSEQ, Xref.REFSEQ_MI, "xxxx2"));
        Assert.assertEquals("xxxx2", interactor.getRefseq());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        interactor.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.REFSEQ, Xref.REFSEQ_MI, "xxxx3"));
        Assert.assertEquals("xxxx3", interactor.getRefseq());
        Assert.assertEquals(2, interactor.getIdentifiers().size());

        interactor.setRefseq(null);
        Assert.assertNull(interactor.getRefseq());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
    }

    @Test
    public void test_set_unset_rogid(){

        Protein interactor = new DefaultProtein("test");
        Assert.assertNull(interactor.getRogid());
        Assert.assertEquals(0, interactor.getChecksums().size());

        interactor.setRogid("xxxx1");
        Assert.assertEquals("xxxx1", interactor.getRogid());
        Assert.assertEquals(1, interactor.getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createRogid("xxxx1"), interactor.getChecksums().iterator().next());

        interactor.setRogid("xxxx2");
        Assert.assertEquals("xxxx2", interactor.getRogid());
        Assert.assertEquals(1, interactor.getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createRogid("xxxx2"), interactor.getChecksums().iterator().next());

        interactor.getChecksums().clear();
        Assert.assertNull(interactor.getRogid());
        Assert.assertEquals(0, interactor.getChecksums().size());

        interactor.getChecksums().add(ChecksumUtils.createRogid("xxxx2"));
        Assert.assertEquals("xxxx2", interactor.getRogid());
        Assert.assertEquals(1, interactor.getChecksums().size());
        interactor.getChecksums().add(ChecksumUtils.createRogid("xxxx3"));
        Assert.assertEquals("xxxx2", interactor.getRogid());
        Assert.assertEquals(2, interactor.getChecksums().size());

        interactor.setRogid(null);
        Assert.assertNull(interactor.getRogid());
        Assert.assertEquals(0, interactor.getChecksums().size());
    }

    @Test
    public void test_set_unset_gene_name(){

        Protein interactor = new DefaultProtein("test");
        Assert.assertNull(interactor.getGeneName());
        Assert.assertEquals(0, interactor.getAliases().size());

        interactor.setGeneName("xxxx1");
        Assert.assertEquals("xxxx1", interactor.getGeneName());
        Assert.assertEquals(1, interactor.getAliases().size());
        Assert.assertEquals(AliasUtils.createGeneName("xxxx1"), interactor.getAliases().iterator().next());

        interactor.setGeneName("xxxx2");
        Assert.assertEquals("xxxx2", interactor.getGeneName());
        Assert.assertEquals(1, interactor.getAliases().size());
        Assert.assertEquals(AliasUtils.createGeneName("xxxx2"), interactor.getAliases().iterator().next());

        interactor.getAliases().clear();
        Assert.assertNull(interactor.getGeneName());
        Assert.assertEquals(0, interactor.getAliases().size());

        interactor.getAliases().add(AliasUtils.createGeneName("xxxx2"));
        Assert.assertEquals("xxxx2", interactor.getGeneName());
        Assert.assertEquals(1, interactor.getAliases().size());
        interactor.getAliases().add(AliasUtils.createGeneName("xxxx3"));
        Assert.assertEquals("xxxx2", interactor.getGeneName());
        Assert.assertEquals(2, interactor.getAliases().size());

        interactor.setGeneName(null);
        Assert.assertNull(interactor.getGeneName());
        Assert.assertEquals(0, interactor.getAliases().size());
    }
}
