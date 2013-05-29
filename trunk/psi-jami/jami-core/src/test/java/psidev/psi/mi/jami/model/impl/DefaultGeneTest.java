package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for DefaultGene
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/13</pre>
 */

public class DefaultGeneTest {

    @Test
    public void create_gene_with_shortname(){

        Gene gene = new DefaultGene("test");

        Assert.assertEquals(CvTermUtils.createGeneInteractorType(), gene.getInteractorType());
        Assert.assertEquals("test", gene.getShortName());
        Assert.assertNotNull(gene.getAliases());
        Assert.assertNotNull(gene.getIdentifiers());
        Assert.assertNotNull(gene.getXrefs());
        Assert.assertNotNull(gene.getAnnotations());
        Assert.assertNotNull(gene.getChecksums());
        Assert.assertNull(gene.getOrganism());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_gene_set_different_interactor_type() throws Exception {
        Gene gene = new DefaultGene("test");

        gene.setInteractorType(CvTermUtils.createUnknownInteractorType());
    }

    @Test
    public void test_set_unset_ensembl(){

        Gene interactor = new DefaultGene("test");
        Assert.assertNull(interactor.getEnsembl());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setEnsembl("ENSEMBL:xxx");
        Assert.assertEquals("ENSEMBL:xxx", interactor.getEnsembl());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.ENSEMBL, Xref.ENSEMBL_MI, "ENSEMBL:xxx"), interactor.getIdentifiers().iterator().next());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setEnsembl("ENSEMBL:xx2");
        Assert.assertEquals("ENSEMBL:xx2", interactor.getEnsembl());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.ENSEMBL, Xref.ENSEMBL_MI, "ENSEMBL:xx2"), interactor.getIdentifiers().iterator().next());

        interactor.getIdentifiers().clear();
        Assert.assertNull(interactor.getEnsembl());
        Assert.assertEquals(0, interactor.getIdentifiers().size());

        interactor.getIdentifiers().add(XrefUtils.createXref(Xref.ENSEMBL, Xref.ENSEMBL_MI, "ENSEMBL:xx2"));
        Assert.assertEquals("ENSEMBL:xx2", interactor.getEnsembl());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        interactor.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, Xref.ENSEMBL_MI, "ENSEMBL:xx3"));
        Assert.assertEquals("ENSEMBL:xx3", interactor.getEnsembl());
        Assert.assertEquals(2, interactor.getIdentifiers().size());

        interactor.setEnsembl(null);
        Assert.assertNull(interactor.getEnsembl());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
    }

    @Test
    public void test_set_unset_ensembl_gemomes(){

        Gene interactor = new DefaultGene("test");
        Assert.assertNull(interactor.getEnsembl());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setEnsemblGenome("ENSEMBL:xxx");
        Assert.assertEquals("ENSEMBL:xxx", interactor.getEnsembleGenome());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.ENSEMBL_GENOMES, Xref.ENSEMBL_GENOMES_MI, "ENSEMBL:xxx"), interactor.getIdentifiers().iterator().next());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setEnsemblGenome("ENSEMBL:xx2");
        Assert.assertEquals("ENSEMBL:xx2", interactor.getEnsembleGenome());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.ENSEMBL_GENOMES, Xref.ENSEMBL_GENOMES_MI, "ENSEMBL:xx2"), interactor.getIdentifiers().iterator().next());

        interactor.getIdentifiers().clear();
        Assert.assertNull(interactor.getEnsembleGenome());
        Assert.assertEquals(0, interactor.getIdentifiers().size());

        interactor.getIdentifiers().add(XrefUtils.createXref(Xref.ENSEMBL_GENOMES, Xref.ENSEMBL_GENOMES_MI, "ENSEMBL:xx2"));
        Assert.assertEquals("ENSEMBL:xx2", interactor.getEnsembleGenome());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        interactor.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL_GENOMES, Xref.ENSEMBL_GENOMES_MI, "ENSEMBL:xx3"));
        Assert.assertEquals("ENSEMBL:xx3", interactor.getEnsembleGenome());
        Assert.assertEquals(2, interactor.getIdentifiers().size());

        interactor.setEnsemblGenome(null);
        Assert.assertNull(interactor.getEnsembleGenome());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
    }

    @Test
    public void test_set_unset_entrez_gene_id(){

        Gene interactor = new DefaultGene("test");
        Assert.assertNull(interactor.getEntrezGeneId());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setEntrezGeneId("xxx");
        Assert.assertEquals("xxx", interactor.getEntrezGeneId());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.ENTREZ_GENE, Xref.ENTREZ_GENE_MI, "xxx"), interactor.getIdentifiers().iterator().next());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setEntrezGeneId("xx2");
        Assert.assertEquals("xx2", interactor.getEntrezGeneId());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.ENTREZ_GENE, Xref.ENTREZ_GENE_MI, "xx2"), interactor.getIdentifiers().iterator().next());

        interactor.getIdentifiers().clear();
        Assert.assertNull(interactor.getEntrezGeneId());
        Assert.assertEquals(0, interactor.getIdentifiers().size());

        interactor.getIdentifiers().add(XrefUtils.createXref(Xref.ENTREZ_GENE, Xref.ENTREZ_GENE_MI, "xx2"));
        Assert.assertEquals("xx2", interactor.getEntrezGeneId());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        interactor.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENTREZ_GENE, Xref.ENTREZ_GENE_MI, "xx3"));
        Assert.assertEquals("xx3", interactor.getEntrezGeneId());
        Assert.assertEquals(2, interactor.getIdentifiers().size());

        interactor.setEnsembl(null);
        Assert.assertNotNull(interactor.getEntrezGeneId());
        Assert.assertEquals(2, interactor.getIdentifiers().size());

        interactor.setEntrezGeneId(null);
        Assert.assertNull(interactor.getEntrezGeneId());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
    }

    @Test
    public void test_set_unset_refseq(){

        Gene interactor = new DefaultGene("test");
        Assert.assertNull(interactor.getRefseq());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setRefseq("xxx");
        Assert.assertEquals("xxx", interactor.getRefseq());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.REFSEQ, Xref.REFSEQ_MI, "xxx"), interactor.getIdentifiers().iterator().next());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setRefseq("xx2");
        Assert.assertEquals("xx2", interactor.getRefseq());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.REFSEQ, Xref.REFSEQ_MI, "xx2"), interactor.getIdentifiers().iterator().next());

        interactor.getIdentifiers().clear();
        Assert.assertNull(interactor.getRefseq());
        Assert.assertEquals(0, interactor.getIdentifiers().size());

        interactor.getIdentifiers().add(XrefUtils.createXref(Xref.REFSEQ, Xref.REFSEQ_MI, "xx2"));
        Assert.assertEquals("xx2", interactor.getRefseq());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        interactor.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.REFSEQ, Xref.REFSEQ_MI, "xx3"));
        Assert.assertEquals("xx3", interactor.getRefseq());
        Assert.assertEquals(2, interactor.getIdentifiers().size());

        interactor.setEnsembl(null);
        Assert.assertNotNull(interactor.getRefseq());
        Assert.assertEquals(2, interactor.getIdentifiers().size());

        interactor.setRefseq(null);
        Assert.assertNull(interactor.getRefseq());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
    }
}
