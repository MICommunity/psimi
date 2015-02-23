package psidev.psi.mi.jami.utils.comparator.interactor;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;

/**
 * Unit tester for UnambiguousExactInteractorComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/13</pre>
 */

public class UnambiguousExactInteractorComparatorTest {

    private UnambiguousExactInteractorComparator comparator = new UnambiguousExactInteractorComparator();

    @Test
    public void test_interactor_null_after(){
        Interactor interactor1 = null;
        Gene interactor2 = new DefaultGene("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_bioactive_entity_first(){
        Interactor interactor1 = new DefaultInteractor("test");
        Gene interactor2 = new DefaultGene("test");
        BioactiveEntity interactor3 = new DefaultBioactiveEntity("test");
        Protein interactor4 = new DefaultProtein("test");
        NucleicAcid interactor5 = new DefaultNucleicAcid("test");
        InteractorPool interactor6 = new DefaultInteractorPool("test");
        Complex interactor7 = new DefaultComplex("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor3) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor3) > 0);
        Assert.assertTrue(comparator.compare(interactor3, interactor3) == 0);
        Assert.assertTrue(comparator.compare(interactor4, interactor3) > 0);
        Assert.assertTrue(comparator.compare(interactor5, interactor3) > 0);
        Assert.assertTrue(comparator.compare(interactor6, interactor3) > 0);
        Assert.assertTrue(comparator.compare(interactor7, interactor3) > 0);

        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor1, interactor3));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor2, interactor3));
        Assert.assertTrue(UnambiguousExactInteractorComparator.areEquals(interactor3, interactor3));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor4, interactor3));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor5, interactor3));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor6, interactor3));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor7, interactor3));

    }

    @Test
    public void test_protein_second(){
        Interactor interactor1 = new DefaultInteractor("test");
        Gene interactor2 = new DefaultGene("test");
        BioactiveEntity interactor3 = new DefaultBioactiveEntity("test");
        Protein interactor4 = new DefaultProtein("test");
        NucleicAcid interactor5 = new DefaultNucleicAcid("test");
        InteractorPool interactor6 = new DefaultInteractorPool("test");
        Complex interactor7 = new DefaultComplex("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor4) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor4) > 0);
        Assert.assertTrue(comparator.compare(interactor3, interactor4) < 0);
        Assert.assertTrue(comparator.compare(interactor4, interactor4) == 0);
        Assert.assertTrue(comparator.compare(interactor5, interactor4) > 0);
        Assert.assertTrue(comparator.compare(interactor6, interactor4) > 0);
        Assert.assertTrue(comparator.compare(interactor7, interactor4) > 0);

        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor1, interactor4));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor2, interactor4));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor3, interactor4));
        Assert.assertTrue(UnambiguousExactInteractorComparator.areEquals(interactor4, interactor4));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor5, interactor4));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor6, interactor4));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor7, interactor4));

    }

    @Test
    public void test_gene_third(){
        Interactor interactor1 = new DefaultInteractor("test");
        Gene interactor2 = new DefaultGene("test");
        BioactiveEntity interactor3 = new DefaultBioactiveEntity("test");
        Protein interactor4 = new DefaultProtein("test");
        NucleicAcid interactor5 = new DefaultNucleicAcid("test");
        InteractorPool interactor6 = new DefaultInteractorPool("test");
        Complex interactor7 = new DefaultComplex("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor3, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor4, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor5, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor6, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor7, interactor2) > 0);

        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousExactInteractorComparator.areEquals(interactor2, interactor2));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor3, interactor2));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor4, interactor2));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor5, interactor2));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor6, interactor2));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor7, interactor2));

    }

    @Test
    public void test_nucleic_acid_fourth(){
        Interactor interactor1 = new DefaultInteractor("test");
        Gene interactor2 = new DefaultGene("test");
        BioactiveEntity interactor3 = new DefaultBioactiveEntity("test");
        Protein interactor4 = new DefaultProtein("test");
        NucleicAcid interactor5 = new DefaultNucleicAcid("test");
        InteractorPool interactor6 = new DefaultInteractorPool("test");
        Complex interactor7 = new DefaultComplex("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor5) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor5) < 0);
        Assert.assertTrue(comparator.compare(interactor3, interactor5) < 0);
        Assert.assertTrue(comparator.compare(interactor4, interactor5) < 0);
        Assert.assertTrue(comparator.compare(interactor5, interactor5) == 0);
        Assert.assertTrue(comparator.compare(interactor6, interactor5) > 0);
        Assert.assertTrue(comparator.compare(interactor7, interactor5) > 0);

        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor1, interactor5));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor2, interactor5));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor3, interactor5));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor4, interactor5));
        Assert.assertTrue(UnambiguousExactInteractorComparator.areEquals(interactor5, interactor5));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor6, interactor5));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor7, interactor5));

    }

    @Test
    public void test_interactor_set_fifth(){
        Interactor interactor1 = new DefaultInteractor("test");
        Gene interactor2 = new DefaultGene("test");
        BioactiveEntity interactor3 = new DefaultBioactiveEntity("test");
        Protein interactor4 = new DefaultProtein("test");
        NucleicAcid interactor5 = new DefaultNucleicAcid("test");
        InteractorPool interactor6 = new DefaultInteractorPool("test");
        Complex interactor7 = new DefaultComplex("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor6) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor6) < 0);
        Assert.assertTrue(comparator.compare(interactor3, interactor6) < 0);
        Assert.assertTrue(comparator.compare(interactor4, interactor6) < 0);
        Assert.assertTrue(comparator.compare(interactor5, interactor6) < 0);
        Assert.assertTrue(comparator.compare(interactor6, interactor6) == 0);
        Assert.assertTrue(comparator.compare(interactor7, interactor6) > 0);

        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor1, interactor6));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor2, interactor6));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor3, interactor6));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor4, interactor6));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor5, interactor6));
        Assert.assertTrue(UnambiguousExactInteractorComparator.areEquals(interactor6, interactor6));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor7, interactor6));

    }

    @Test
    public void test_complex_sixth(){
        Interactor interactor1 = new DefaultInteractor("test");
        Gene interactor2 = new DefaultGene("test");
        BioactiveEntity interactor3 = new DefaultBioactiveEntity("test");
        Protein interactor4 = new DefaultProtein("test");
        NucleicAcid interactor5 = new DefaultNucleicAcid("test");
        InteractorPool interactor6 = new DefaultInteractorPool("test");
        Complex interactor7 = new DefaultComplex("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor7) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor7) < 0);
        Assert.assertTrue(comparator.compare(interactor3, interactor7) < 0);
        Assert.assertTrue(comparator.compare(interactor4, interactor7) < 0);
        Assert.assertTrue(comparator.compare(interactor5, interactor7) < 0);
        Assert.assertTrue(comparator.compare(interactor6, interactor7) < 0);
        Assert.assertTrue(comparator.compare(interactor7, interactor7) == 0);

        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor1, interactor7));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor2, interactor7));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor3, interactor7));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor4, interactor7));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor5, interactor7));
        Assert.assertFalse(UnambiguousExactInteractorComparator.areEquals(interactor6, interactor7));
        Assert.assertTrue(UnambiguousExactInteractorComparator.areEquals(interactor7, interactor7));

    }
}
