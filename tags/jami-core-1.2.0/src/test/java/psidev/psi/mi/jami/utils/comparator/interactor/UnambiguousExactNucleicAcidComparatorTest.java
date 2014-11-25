package psidev.psi.mi.jami.utils.comparator.interactor;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.NucleicAcid;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultNucleicAcid;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for UnambiguousExactNucleicAcidComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/13</pre>
 */

public class UnambiguousExactNucleicAcidComparatorTest {

    private UnambiguousExactNucleicAcidComparator comparator = new UnambiguousExactNucleicAcidComparator();

    @Test
    public void test_nucleic_acid_null_after(){
        NucleicAcid interactor1 = null;
        NucleicAcid interactor2 = new DefaultNucleicAcid("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousExactNucleicAcidComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_sequence_same_ddbj_embl_genbank(){
        NucleicAcid interactor1 = new DefaultNucleicAcid("test");
        interactor1.setSequence("AAAAA");
        interactor1.setDdbjEmblGenbank("xxxx");
        NucleicAcid interactor2 = new DefaultNucleicAcid("test");
        interactor2.setSequence("APPAA");
        interactor2.setDdbjEmblGenbank("xxxx");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousExactNucleicAcidComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_sequence_null_same_ddbj_embl_genbank(){
        NucleicAcid interactor1 = new DefaultNucleicAcid("test");
        interactor1.setDdbjEmblGenbank("xxxx");
        NucleicAcid interactor2 = new DefaultNucleicAcid("test");
        interactor2.setSequence("APPAA");
        interactor2.setDdbjEmblGenbank("xxxx");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousExactNucleicAcidComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_ddbj_embl_genbank(){
        NucleicAcid interactor1 = new DefaultNucleicAcid("test");
        interactor1.setDdbjEmblGenbank("xxxx2");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.DDBJ_EMBL_GENBANK, "xxxx1"));
        NucleicAcid interactor2 = new DefaultNucleicAcid("test");
        interactor2.setDdbjEmblGenbank("xxxx1");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.DDBJ_EMBL_GENBANK, "xxxx2"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousExactNucleicAcidComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_ddbj_genbank_embl_same_refseq(){
        NucleicAcid interactor1 = new DefaultNucleicAcid("test");
        interactor1.setDdbjEmblGenbank("xxxx");
        interactor1.setRefseq("xxxx1");
        NucleicAcid interactor2 = new DefaultNucleicAcid("test");
        interactor2.setDdbjEmblGenbank("xxxx");
        interactor2.setRefseq("xxxx1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousExactNucleicAcidComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_refseq(){
        NucleicAcid interactor1 = new DefaultNucleicAcid("test");
        interactor1.setDdbjEmblGenbank("xxxx1");
        interactor1.setRefseq("xxxx2");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.DDBJ_EMBL_GENBANK, "xxxx2"));
        NucleicAcid interactor2 = new DefaultNucleicAcid("test");
        interactor2.setDdbjEmblGenbank("xxxx1");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.DDBJ_EMBL_GENBANK, "xxxx2"));
        interactor2.setRefseq("xxxx1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousExactNucleicAcidComparator.areEquals(interactor1, interactor2));
    }
}
