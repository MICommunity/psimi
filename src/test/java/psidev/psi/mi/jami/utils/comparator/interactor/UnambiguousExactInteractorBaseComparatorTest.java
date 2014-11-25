package psidev.psi.mi.jami.utils.comparator.interactor;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultInteractor;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for UnambiguousExactInteractorBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/05/13</pre>
 */

public class UnambiguousExactInteractorBaseComparatorTest {

    private UnambiguousExactInteractorBaseComparator comparator = new UnambiguousExactInteractorBaseComparator();

    @Test
    public void test_interactor_null_after(){
        Interactor interactor1 = null;
        Interactor interactor2 = new DefaultInteractor("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousExactInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousExactInteractorBaseComparator.hashCode(interactor1) != UnambiguousExactInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_different_types(){
        // unknown interactor type
        Interactor interactor1 = new DefaultInteractor("test");
        // gene interactor type
        Interactor interactor2 = new DefaultInteractor("test", CvTermUtils.createGeneInteractorType());

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousExactInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousExactInteractorBaseComparator.hashCode(interactor1) != UnambiguousExactInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_same_type(){
        // gene interactor type
        Interactor interactor1 = new DefaultInteractor("test", CvTermUtils.createGeneInteractorType());
        // gene interactor type
        Interactor interactor2 = new DefaultInteractor("test", CvTermUtils.createGeneInteractorType());

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousExactInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousExactInteractorBaseComparator.hashCode(interactor1) == UnambiguousExactInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_same_type_different_organisms(){
        // gene interactor type
        Interactor interactor1 = new DefaultInteractor("test", CvTermUtils.createGeneInteractorType(), new DefaultOrganism(-3));
        // gene interactor type
        Interactor interactor2 = new DefaultInteractor("test", CvTermUtils.createGeneInteractorType(), new DefaultOrganism(9606));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousExactInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousExactInteractorBaseComparator.hashCode(interactor1) != UnambiguousExactInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_same_type_ignore_one_organism_because_not_set(){
        // gene interactor type
        Interactor interactor1 = new DefaultInteractor("test", CvTermUtils.createGeneInteractorType());
        // gene interactor type
        Interactor interactor2 = new DefaultInteractor("test", CvTermUtils.createGeneInteractorType(), new DefaultOrganism(9606));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousExactInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousExactInteractorBaseComparator.hashCode(interactor1) != UnambiguousExactInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_same_organism(){
        // unknown interactor type
        Interactor interactor1 = new DefaultInteractor("test", new DefaultOrganism(9606));
        // unknown interactor type
        Interactor interactor2 = new DefaultInteractor("test", new DefaultOrganism(9606));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousExactInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousExactInteractorBaseComparator.hashCode(interactor1) == UnambiguousExactInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_same_organism_different_identifier(){
        // gene interactor type
        Interactor interactor1 = new DefaultInteractor("test", CvTermUtils.createGeneInteractorType(), new DefaultOrganism(9606), XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345"));
        // gene interactor type
        Interactor interactor2 = new DefaultInteractor("test", CvTermUtils.createGeneInteractorType(), new DefaultOrganism(9606), XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12346"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousExactInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousExactInteractorBaseComparator.hashCode(interactor1) != UnambiguousExactInteractorBaseComparator.hashCode(interactor2));
    }
}
