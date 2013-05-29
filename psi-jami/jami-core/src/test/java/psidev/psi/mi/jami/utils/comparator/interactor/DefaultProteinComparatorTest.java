package psidev.psi.mi.jami.utils.comparator.interactor;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for DefaultProteinComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/13</pre>
 */

public class DefaultProteinComparatorTest {

    private DefaultProteinComparator comparator = new DefaultProteinComparator();

    @Test
    public void test_nucleic_acid_null_after(){
        Protein interactor1 = null;
        Protein interactor2 = new DefaultProtein("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(DefaultProteinComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_sequence_same_uniprot(){
        Protein interactor1 = new DefaultProtein("test");
        interactor1.setSequence("AAAAA");
        interactor1.setUniprotkb("P12345");
        Protein interactor2 = new DefaultProtein("test");
        interactor2.setSequence("APPAA");
        interactor2.setUniprotkb("P12345");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(DefaultProteinComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_sequence_null_same_uniprot(){
        Protein interactor1 = new DefaultProtein("test");
        interactor1.setUniprotkb("P12345");
        Protein interactor2 = new DefaultProtein("test");
        interactor2.setSequence("APPAA");
        interactor2.setUniprotkb("P12345");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultProteinComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_uniprotkb(){
        Protein interactor1 = new DefaultProtein("test");
        interactor1.setUniprotkb("P12346");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345"));
        Protein interactor2 = new DefaultProtein("test");
        interactor2.setUniprotkb("P12345");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12346"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(DefaultProteinComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_uniprot_null_ignored(){
        Protein interactor1 = new DefaultProtein("test");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));
        Protein interactor2 = new DefaultProtein("test");
        interactor2.setUniprotkb("P12346");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultProteinComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_uniprot_same_refseq(){
        Protein interactor1 = new DefaultProtein("test");
        interactor1.setUniprotkb("P12345");
        interactor1.setRefseq("xxxx1");
        Protein interactor2 = new DefaultProtein("test");
        interactor2.setSequence("APPAA");
        interactor2.setUniprotkb("P12345");
        interactor2.setRefseq("xxxx1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultProteinComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_refseq(){
        Protein interactor1 = new DefaultProtein("test");
        interactor1.setUniprotkb("P12345");
        interactor1.setRefseq("xxx2");
        Protein interactor2 = new DefaultProtein("test");
        interactor2.setUniprotkb("P12345");
        interactor2.setRefseq("xxx1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(DefaultProteinComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_refseq_null_ignored(){
        Protein interactor1 = new DefaultProtein("test");
        interactor1.setUniprotkb("P12346");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));
        Protein interactor2 = new DefaultProtein("test");
        interactor2.setUniprotkb("P12346");
        interactor2.setRefseq("xxxx");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultProteinComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_gene_names(){
        Protein interactor1 = new DefaultProtein("test");
        interactor1.setUniprotkb("P12346");
        interactor1.setGeneName("name1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));
        Protein interactor2 = new DefaultProtein("test");
        interactor2.setUniprotkb("P12346");
        interactor2.setGeneName("name2");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(DefaultProteinComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_gene_names(){
        Protein interactor1 = new DefaultProtein("test");
        interactor1.setUniprotkb("P12346");
        interactor1.setGeneName("name1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));
        Protein interactor2 = new DefaultProtein("test");
        interactor2.setUniprotkb("P12346");
        interactor2.setGeneName("name1");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultProteinComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_ignore_if_gene_name_null(){
        Protein interactor1 = new DefaultProtein("test");
        interactor1.setUniprotkb("P12346");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));
        Protein interactor2 = new DefaultProtein("test");
        interactor2.setUniprotkb("P12346");
        interactor2.setGeneName("name1");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultProteinComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_rogid(){
        Protein interactor1 = new DefaultProtein("test");
        interactor1.setUniprotkb("P12346");
        interactor1.setGeneName("name1");
        interactor1.setRogid("xxx1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));
        Protein interactor2 = new DefaultProtein("test");
        interactor2.setUniprotkb("P12346");
        interactor2.setGeneName("name1");
        interactor2.setRogid("xxx2");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(DefaultProteinComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_rogid(){
        Protein interactor1 = new DefaultProtein("test");
        interactor1.setUniprotkb("P12346");
        interactor1.setGeneName("name1");
        interactor1.setRogid("xxx1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));
        Protein interactor2 = new DefaultProtein("test");
        interactor2.setUniprotkb("P12346");
        interactor2.setGeneName("name1");
        interactor2.setRogid("xxx1");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultProteinComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_ignore_if_rogid_null(){
        Protein interactor1 = new DefaultProtein("test");
        interactor1.setUniprotkb("P12346");
        interactor1.setRogid("xxx1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));
        Protein interactor2 = new DefaultProtein("test");
        interactor2.setUniprotkb("P12346");
        interactor2.setGeneName("name1");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.ENSEMBL, "xxxx1"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultProteinComparator.areEquals(interactor1, interactor2));
    }
}
