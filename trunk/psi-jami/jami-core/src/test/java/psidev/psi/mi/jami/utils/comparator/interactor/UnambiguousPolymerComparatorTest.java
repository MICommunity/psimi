package psidev.psi.mi.jami.utils.comparator.interactor;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.model.impl.DefaultPolymer;

/**
 * Unit tester for UnambiguousPolymerComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/05/13</pre>
 */

public class UnambiguousPolymerComparatorTest {

    private UnambiguousPolymerComparator comparator = new UnambiguousPolymerComparator();

    @Test
    public void test_polymer_null_after(){
        Polymer interactor1 = null;
        Polymer interactor2 = new DefaultPolymer("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousPolymerComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_properties_before_sequence(){
        Polymer interactor1 = new DefaultPolymer("test");
        interactor1.setSequence("AAGTA");
        Polymer interactor2 = new DefaultPolymer("test2");
        interactor2.setSequence("AAGTA");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousPolymerComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_interactor_properties_different_sequences(){
        Polymer interactor1 = new DefaultPolymer("test");
        interactor1.setSequence("AAGAA");
        Polymer interactor2 = new DefaultPolymer("test");
        interactor2.setSequence("AAGTA");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousPolymerComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_interactor_properties_one_sequence_not_provided(){
        Polymer interactor1 = new DefaultPolymer("test");
        Polymer interactor2 = new DefaultPolymer("test");
        interactor2.setSequence("AAGTA");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousPolymerComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_interactor_properties_same_sequences(){
        Polymer interactor1 = new DefaultPolymer("test");
        interactor1.setSequence("AAGAA");
        Polymer interactor2 = new DefaultPolymer("test");
        interactor2.setSequence("AAGAA");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousPolymerComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_interactor_properties_same_sequences_case_insensitive(){
        Polymer interactor1 = new DefaultPolymer("test");
        interactor1.setSequence("aagaa");
        Polymer interactor2 = new DefaultPolymer("test");
        interactor2.setSequence("AAGAA");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousPolymerComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_interactor_properties_same_sequences_different_organisms(){
        Polymer interactor1 = new DefaultPolymer("test");
        interactor1.setSequence("AAGTA");
        interactor1.setOrganism(new DefaultOrganism(-3));
        Polymer interactor2 = new DefaultPolymer("test");
        interactor2.setSequence("AAGTA");
        interactor2.setOrganism(new DefaultOrganism(9606));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousPolymerComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_same_interactor_properties_same_sequences_same_organisms(){
        Polymer interactor1 = new DefaultPolymer("test");
        interactor1.setSequence("AAGTA");
        interactor1.setOrganism(new DefaultOrganism(9606));
        Polymer interactor2 = new DefaultPolymer("test");
        interactor2.setSequence("AAGTA");
        interactor2.setOrganism(new DefaultOrganism(9606));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousPolymerComparator.areEquals(interactor1, interactor2));
    }
}
