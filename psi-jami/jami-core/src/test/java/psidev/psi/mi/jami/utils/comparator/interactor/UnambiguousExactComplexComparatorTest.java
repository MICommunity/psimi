package psidev.psi.mi.jami.utils.comparator.interactor;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.impl.DefaultComplex;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for UnambiguousExactComplexComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class UnambiguousExactComplexComparatorTest {

    private UnambiguousExactComplexComparator comparator = new UnambiguousExactComplexComparator();

    @Test
    public void test_complex_null_after(){
        Complex complex1 = null;
        Complex complex2 = new DefaultComplex("test");

        Assert.assertTrue(comparator.compare(complex1, complex2) > 0);
        Assert.assertTrue(comparator.compare(complex2, complex1) < 0);

        Assert.assertFalse(UnambiguousExactComplexComparator.areEquals(complex1, complex2));
    }

    @Test
    public void test_complex_different_interactors(){
        Complex complex1 = new DefaultComplex("test", new DefaultCvTerm("protein complex"));
        Complex complex2 = new DefaultComplex("test", new DefaultCvTerm("protein-dna complex"));

        Assert.assertTrue(comparator.compare(complex1, complex2) < 0);
        Assert.assertTrue(comparator.compare(complex2, complex1) > 0);

        Assert.assertFalse(UnambiguousExactComplexComparator.areEquals(complex1, complex2));
    }

    @Test
    public void test_complex_same_interactors(){
        Complex complex1 = new DefaultComplex("test", new DefaultCvTerm("protein complex"));
        Complex complex2 = new DefaultComplex("test", new DefaultCvTerm("protein complex"));

        Assert.assertTrue(comparator.compare(complex1, complex2) == 0);
        Assert.assertTrue(comparator.compare(complex2, complex1) == 0);

        Assert.assertTrue(UnambiguousExactComplexComparator.areEquals(complex1, complex2));
    }

    @Test
    public void test_complex_different_interaction_type(){
        Complex complex1 = new DefaultComplex("test", new DefaultCvTerm("protein complex"));
        complex1.setInteractionType(new DefaultCvTerm("phosphorylation"));
        Complex complex2 = new DefaultComplex("test", new DefaultCvTerm("protein complex"));
        complex2.setInteractionType(new DefaultCvTerm("direct interaction"));

        Assert.assertTrue(comparator.compare(complex1, complex2) > 0);
        Assert.assertTrue(comparator.compare(complex2, complex1) < 0);

        Assert.assertFalse(UnambiguousExactComplexComparator.areEquals(complex1, complex2));
    }

    @Test
    public void test_complex_same_interaction_type(){
        Complex complex1 = new DefaultComplex("test", new DefaultCvTerm("protein complex"));
        complex1.setInteractionType(new DefaultCvTerm("phosphorylation"));
        Complex complex2 = new DefaultComplex("test", new DefaultCvTerm("protein complex"));
        complex2.setInteractionType(new DefaultCvTerm("phosphorylation"));

        Assert.assertTrue(comparator.compare(complex1, complex2) == 0);
        Assert.assertTrue(comparator.compare(complex2, complex1) == 0);

        Assert.assertTrue(UnambiguousExactComplexComparator.areEquals(complex1, complex2));
    }

    @Test
    public void test_complex_different_participants(){
        Complex complex1 = new DefaultComplex("test", new DefaultCvTerm("protein complex"));
        complex1.setInteractionType(new DefaultCvTerm("phosphorylation"));
        complex1.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test protein",
                XrefUtils.createUniprotIdentity("P12345"))));
        complex1.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test protein",
                XrefUtils.createUniprotIdentity("P12346"))));
        Complex complex2 = new DefaultComplex("test", new DefaultCvTerm("protein complex"));
        complex2.setInteractionType(new DefaultCvTerm("phosphorylation"));
        complex2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test protein",
                XrefUtils.createUniprotIdentity("P12345"))));
        complex2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test protein",
                XrefUtils.createUniprotIdentity("P12346"))));
        complex2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test protein",
                XrefUtils.createUniprotIdentity("P12347"))));

        Assert.assertTrue(comparator.compare(complex1, complex2) < 0);
        Assert.assertTrue(comparator.compare(complex2, complex1) > 0);

        Assert.assertFalse(UnambiguousExactComplexComparator.areEquals(complex1, complex2));
    }

    @Test
    public void test_complex_self_complex_before(){
        Complex complex1 = new DefaultComplex("test", new DefaultCvTerm("protein complex"));
        complex1.setInteractionType(new DefaultCvTerm("phosphorylation"));
        complex1.addModelledParticipant(new DefaultModelledParticipant(complex1));
        complex1.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test protein",
                XrefUtils.createUniprotIdentity("P12346"))));
        Complex complex2 = new DefaultComplex("test", new DefaultCvTerm("protein complex"));
        complex2.setInteractionType(new DefaultCvTerm("phosphorylation"));
        complex2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test protein",
                XrefUtils.createUniprotIdentity("P12345"))));
        complex2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test protein",
                XrefUtils.createUniprotIdentity("P12346"))));

        Assert.assertTrue(comparator.compare(complex1, complex2) < 0);
        Assert.assertTrue(comparator.compare(complex2, complex1) > 0);

        Assert.assertFalse(UnambiguousExactComplexComparator.areEquals(complex1, complex2));
    }

    @Test
    public void test_complex_same_participants(){
        Complex complex1 = new DefaultComplex("test", new DefaultCvTerm("protein complex"));
        complex1.setInteractionType(new DefaultCvTerm("phosphorylation"));
        complex1.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test protein",
                XrefUtils.createUniprotIdentity("P12347"))));
        complex1.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test protein",
                XrefUtils.createUniprotIdentity("P12345"))));
        complex1.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test protein",
                XrefUtils.createUniprotIdentity("P12346"))));
        Complex complex2 = new DefaultComplex("test", new DefaultCvTerm("protein complex"));
        complex2.setInteractionType(new DefaultCvTerm("phosphorylation"));
        complex2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test protein",
                XrefUtils.createUniprotIdentity("P12345"))));
        complex2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test protein",
                XrefUtils.createUniprotIdentity("P12346"))));
        complex2.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("test protein",
                XrefUtils.createUniprotIdentity("P12347"))));

        Assert.assertTrue(comparator.compare(complex1, complex2) == 0);
        Assert.assertTrue(comparator.compare(complex2, complex1) == 0);

        Assert.assertTrue(UnambiguousExactComplexComparator.areEquals(complex1, complex2));
    }
}
