package psidev.psi.mi.jami.utils.comparator.interactor;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultBioactiveEntity;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for DefaultBioactiveEntityComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/05/13</pre>
 */

public class DefaultBioactiveEntityComparatorTest {

    private DefaultBioactiveEntityComparator comparator = new DefaultBioactiveEntityComparator();

    @Test
    public void test_bioactiveEntity_null_after(){
        BioactiveEntity interactor1 = null;
        BioactiveEntity interactor2 = new DefaultBioactiveEntity("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(DefaultBioactiveEntityComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_properties_before_checksums(){

        BioactiveEntity interactor1 = new DefaultBioactiveEntity("test2");
        interactor1.setStandardInchi("xxx");
        interactor1.setSmile("yyy");
        interactor1.setStandardInchiKey("zzz");
        BioactiveEntity interactor2 = new DefaultBioactiveEntity("test1");
        interactor2.setStandardInchi("xxx");
        interactor2.setSmile("yyy");
        interactor2.setStandardInchiKey("zzz");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(DefaultBioactiveEntityComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_different_chebi(){

        BioactiveEntity interactor1 = new DefaultBioactiveEntity("test");
        interactor1.setStandardInchi("xxx");
        interactor1.setSmile("yyy");
        interactor1.setStandardInchiKey("zzz");
        interactor1.setChebi("CHEBI:xxxx1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.CHEBI, Xref.CHEBI_MI, "CHEBI:xxxx2"));
        BioactiveEntity interactor2 = new DefaultBioactiveEntity("test");
        interactor2.setStandardInchi("xxx");
        interactor2.setSmile("yyy");
        interactor2.setStandardInchiKey("zzz");
        interactor1.setChebi("CHEBI:xxxx2");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.CHEBI, Xref.CHEBI_MI, "CHEBI:xxxx1"));

        // interactor 1 and 2 have same identifiers but the unique chebi which have been set is different
        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(DefaultBioactiveEntityComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_chebi(){

        BioactiveEntity interactor1 = new DefaultBioactiveEntity("test");
        interactor1.setStandardInchi("xxx");
        interactor1.setSmile("yyy");
        interactor1.setStandardInchiKey("zzz");
        interactor1.setChebi("CHEBI:xxxx2");
        BioactiveEntity interactor2 = new DefaultBioactiveEntity("test");
        interactor2.setStandardInchi("xxx");
        interactor2.setSmile("yyy");
        interactor2.setStandardInchiKey("zzz");
        interactor2.setChebi("CHEBI:xxxx2");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.CHEBI, Xref.CHEBI_MI, "CHEBI:xxxx1"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultBioactiveEntityComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_no_chebi_different_standard_inchi_key(){

        BioactiveEntity interactor1 = new DefaultBioactiveEntity("test");
        interactor1.setStandardInchi("xxx");
        interactor1.setSmile("yyy");
        interactor1.setStandardInchiKey("zzz1");
        BioactiveEntity interactor2 = new DefaultBioactiveEntity("test");
        interactor2.setStandardInchi("xxx");
        interactor2.setSmile("yyy");
        interactor2.setStandardInchiKey("zzz2");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(DefaultBioactiveEntityComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_no_chebi_same_standard_inchi_key(){

        BioactiveEntity interactor1 = new DefaultBioactiveEntity("test");
        interactor1.setStandardInchi("xxx");
        interactor1.setSmile("yyy");
        interactor1.setStandardInchiKey("zzz1");
        BioactiveEntity interactor2 = new DefaultBioactiveEntity("test");
        interactor2.setStandardInchi("xxx");
        interactor2.setSmile("yyy");
        interactor2.setStandardInchiKey("zzz1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultBioactiveEntityComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_no_chebi_different_standard_inchi(){

        BioactiveEntity interactor1 = new DefaultBioactiveEntity("test");
        interactor1.setStandardInchi("xxx1");
        interactor1.setSmile("yyy");
        interactor1.setStandardInchiKey("zzz");
        BioactiveEntity interactor2 = new DefaultBioactiveEntity("test");
        interactor2.setStandardInchi("xxx2");
        interactor2.setSmile("yyy");
        interactor2.setStandardInchiKey("zzz");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(DefaultBioactiveEntityComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_no_chebi_same_standard_inchi_no_smile(){

        BioactiveEntity interactor1 = new DefaultBioactiveEntity("test");
        interactor1.setStandardInchi("xxx1");
        interactor1.setStandardInchiKey("zzz1");
        BioactiveEntity interactor2 = new DefaultBioactiveEntity("test");
        interactor2.setStandardInchi("xxx1");
        interactor2.setSmile("yyy");
        interactor2.setStandardInchiKey("zzz1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultBioactiveEntityComparator.areEquals(interactor1, interactor2));
    }


    @Test
    public void test_no_chebi_different_smile(){

        BioactiveEntity interactor1 = new DefaultBioactiveEntity("test");
        interactor1.setStandardInchi("xxx");
        interactor1.setSmile("yyy1");
        interactor1.setStandardInchiKey("zzz");
        BioactiveEntity interactor2 = new DefaultBioactiveEntity("test");
        interactor2.setStandardInchi("xxx");
        interactor2.setSmile("yyy2");
        interactor2.setStandardInchiKey("zzz");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(DefaultBioactiveEntityComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_no_chebi_same_smile_no_standard_inchi(){

        BioactiveEntity interactor1 = new DefaultBioactiveEntity("test");
        interactor1.setStandardInchi("xxx");
        interactor1.setStandardInchiKey("zzz");
        interactor1.setSmile("yyy1");
        BioactiveEntity interactor2 = new DefaultBioactiveEntity("test");
        interactor2.setSmile("yyy1");
        interactor2.setStandardInchiKey("zzz");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultBioactiveEntityComparator.areEquals(interactor1, interactor2));
    }
}
