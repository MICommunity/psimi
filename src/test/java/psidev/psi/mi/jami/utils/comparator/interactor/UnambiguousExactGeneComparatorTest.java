package psidev.psi.mi.jami.utils.comparator.interactor;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultGene;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for UnambiguousExactGeneComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/13</pre>
 */

public class UnambiguousExactGeneComparatorTest {

    private UnambiguousExactGeneComparator comparator = new UnambiguousExactGeneComparator();

    @Test
    public void test_gene_null_after(){
        Gene interactor1 = null;
        Gene interactor2 = new DefaultGene("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousExactGeneComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_ensembl(){
        // two genes
        Gene interactor1 = new DefaultGene("test2");
        interactor1.setEnsembl("ENSEMBL-xxx1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "ENSEMBL-xxx2"));
        Gene interactor2 = new DefaultGene("test1");
        interactor2.setEnsembl("ENSEMBL-xxx2");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "ENSEMBL-xxx1"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousExactGeneComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_ensembl_same_refseq(){
        // two genes
        Gene interactor1 = new DefaultGene("test2");
        interactor1.setEnsembl("ENSEMBL-xxx1");
        interactor1.setRefseq("xxx1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "ENSEMBL-xxx2"));
        Gene interactor2 = new DefaultGene("test1");
        interactor2.setEnsembl("ENSEMBL-xxx2");
        interactor2.setRefseq("xxx1");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "ENSEMBL-xxx1"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousExactGeneComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_ensembl(){
        // two genes
        Gene interactor1 = new DefaultGene("test2");
        interactor1.setEnsembl("ENSEMBL-xxx1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "ENSEMBL-xxx2"));
        Gene interactor2 = new DefaultGene("test1");
        interactor2.setEnsembl("ENSEMBL-xxx1");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "ENSEMBL-xxx2"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousExactGeneComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_ensembl_genomes_one_ensembl_not_set(){
        // two genes
        Gene interactor1 = new DefaultGene("test2");
        interactor1.setEnsemblGenome("ENSEMBL-xxx1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL_GENOMES, "ENSEMBL-xxx2"));
        Gene interactor2 = new DefaultGene("test1");
        interactor2.setEnsemblGenome("ENSEMBL-xxx2");
        interactor2.setEnsembl("ENSEMBL-xxx");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL_GENOMES, "ENSEMBL-xxx1"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousExactGeneComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_ensembl_genomes_same_refseq_two_ensembl_not_equals(){
        // two genes
        Gene interactor1 = new DefaultGene("test2");
        interactor1.setEnsemblGenome("ENSEMBL-xxx1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL_GENOMES, "ENSEMBL-xxx2"));
        interactor1.setRefseq("xxx1");
        Gene interactor2 = new DefaultGene("test1");
        interactor2.setEnsemblGenome("ENSEMBL-xxx2");
        interactor2.setRefseq("xxx1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousExactGeneComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_ensembl_genomes_same_refseq(){
        // two genes
        Gene interactor1 = new DefaultGene("test2");
        interactor1.setEnsemblGenome("ENSEMBL-xxx1");
        interactor1.setRefseq("xxx1");
        Gene interactor2 = new DefaultGene("test1");
        interactor2.setEnsemblGenome("ENSEMBL-xxx2");
        interactor2.setRefseq("xxx1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousExactGeneComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_ensembl_genomes_more_ensembl_xrefs_not_equals(){
        // two genes
        Gene interactor1 = new DefaultGene("test2");
        interactor1.setEnsemblGenome("ENSEMBL-xxx1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL_GENOMES, "ENSEMBL-xxx2"));
        Gene interactor2 = new DefaultGene("test1");
        interactor2.setEnsemblGenome("ENSEMBL-xxx1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousExactGeneComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_ensembl_genomes(){
        // two genes
        Gene interactor1 = new DefaultGene("test2");
        interactor1.setEnsemblGenome("ENSEMBL-xxx1");
        Gene interactor2 = new DefaultGene("test1");
        interactor2.setEnsemblGenome("ENSEMBL-xxx1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousExactGeneComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_entrez_gene_id(){
        // two genes
        Gene interactor1 = new DefaultGene("test2");
        interactor1.setEntrezGeneId("xxx1");
        Gene interactor2 = new DefaultGene("test1");
        interactor2.setEntrezGeneId("xxx1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousExactGeneComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_entrez_gene_id_one_ensembl_not_set(){
        // two genes
        Gene interactor1 = new DefaultGene("test2");
        interactor1.setEntrezGeneId("xxx1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENTREZ_GENE, "xxx2"));
        Gene interactor2 = new DefaultGene("test1");
        interactor2.setEntrezGeneId("xxx2");
        interactor2.setEnsembl("ENSEMBL-xxx");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENTREZ_GENE, "xxx1"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousExactGeneComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_entrez_gene_id_same_refseq(){
        // two genes
        Gene interactor1 = new DefaultGene("test2");
        interactor1.setEntrezGeneId("xxx1");
        interactor1.setRefseq("xxx1");
        Gene interactor2 = new DefaultGene("test1");
        interactor2.setEntrezGeneId("xxx2");
        interactor2.setRefseq("xxx1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousExactGeneComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_refseq(){
        // two genes
        Gene interactor1 = new DefaultGene("test2");
        interactor1.setRefseq("xxx1");
        Gene interactor2 = new DefaultGene("test1");
        interactor2.setRefseq("xxx1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousExactGeneComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_refseq_one_ensembl_not_set(){
        // two genes
        Gene interactor1 = new DefaultGene("test2");
        interactor1.setRefseq("xxx1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.REFSEQ, "xxx2"));
        Gene interactor2 = new DefaultGene("test1");
        interactor2.setRefseq("xxx2");
        interactor2.setEnsembl("ENSEMBL-xxx");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.REFSEQ, "xxx1"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousExactGeneComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_refseq_gene_id_null_after(){
        // two genes
        Gene interactor1 = new DefaultGene("test2");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.REFSEQ, "xxx2"));
        interactor1.setRefseq("xxx1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.REFSEQ, "xxx2"));
        Gene interactor2 = new DefaultGene("test1");
        interactor2.setEntrezGeneId("xxx1");
        interactor2.setRefseq("xxx2");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousExactGeneComparator.areEquals(interactor1, interactor2));
    }
}
