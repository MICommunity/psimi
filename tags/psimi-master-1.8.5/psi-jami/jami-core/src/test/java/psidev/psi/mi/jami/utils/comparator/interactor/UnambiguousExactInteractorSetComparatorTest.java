package psidev.psi.mi.jami.utils.comparator.interactor;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.InteractorPool;
import psidev.psi.mi.jami.model.impl.DefaultInteractorPool;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester of UnambiguousExactInteractorPoolComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/13</pre>
 */

public class UnambiguousExactInteractorSetComparatorTest {

    private UnambiguousExactInteractorPoolComparator comparator = new UnambiguousExactInteractorPoolComparator();

    @Test
    public void test_interactor_set_null_after(){
        InteractorPool interactor1 = null;
        InteractorPool interactor2 = new DefaultInteractorPool("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousExactInteractorPoolComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_set_different_identifier(){
        InteractorPool interactor1 = new DefaultInteractorPool("test");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx1"));
        InteractorPool interactor2 = new DefaultInteractorPool("test");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx2"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousExactInteractorPoolComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_different_content(){
        InteractorPool interactor1 = new DefaultInteractorPool("test");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx1"));
        interactor1.add(new DefaultProtein("test2", "P12345"));
        interactor1.add(new DefaultProtein("test3", "P12346"));
        InteractorPool interactor2 = new DefaultInteractorPool("test");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx1"));
        interactor2.add(new DefaultProtein("test4", "P12347"));
        interactor2.add(new DefaultProtein("test5", "P12348"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousExactInteractorPoolComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_same_content(){
        InteractorPool interactor1 = new DefaultInteractorPool("test");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx1"));
        interactor1.add(new DefaultProtein("test2", "P12345"));
        interactor1.add(new DefaultProtein("test3", "P12346"));
        InteractorPool interactor2 = new DefaultInteractorPool("test");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx1"));
        interactor2.add(new DefaultProtein("test2", "P12345"));
        interactor2.add(new DefaultProtein("test3", "P12346"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousExactInteractorPoolComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_same_content_ignore_order(){
        InteractorPool interactor1 = new DefaultInteractorPool("test");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx1"));
        interactor1.add(new DefaultProtein("test3", "P12346"));
        interactor1.add(new DefaultProtein("test2", "P12345"));
        InteractorPool interactor2 = new DefaultInteractorPool("test");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx1"));
        interactor2.add(new DefaultProtein("test2", "P12345"));
        interactor2.add(new DefaultProtein("test3", "P12346"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousExactInteractorPoolComparator.areEquals(interactor1, interactor2));
    }
}
