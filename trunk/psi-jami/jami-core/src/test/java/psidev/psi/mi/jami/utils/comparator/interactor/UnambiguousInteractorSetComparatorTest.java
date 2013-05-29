package psidev.psi.mi.jami.utils.comparator.interactor;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.InteractorSet;
import psidev.psi.mi.jami.model.impl.DefaultInteractorSet;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester of UnambiguousInteractorSetComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/13</pre>
 */

public class UnambiguousInteractorSetComparatorTest {

    private UnambiguousInteractorSetComparator comparator = new UnambiguousInteractorSetComparator();

    @Test
    public void test_interactor_set_null_after(){
        InteractorSet interactor1 = null;
        InteractorSet interactor2 = new DefaultInteractorSet("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousInteractorSetComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_set_different_identifier(){
        InteractorSet interactor1 = new DefaultInteractorSet("test");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx1"));
        InteractorSet interactor2 = new DefaultInteractorSet("test");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx2"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousInteractorSetComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_different_content(){
        InteractorSet interactor1 = new DefaultInteractorSet("test");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx1"));
        interactor1.add(new DefaultProtein("test2", "P12345"));
        interactor1.add(new DefaultProtein("test3", "P12346"));
        InteractorSet interactor2 = new DefaultInteractorSet("test");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx1"));
        interactor2.add(new DefaultProtein("test4", "P12347"));
        interactor2.add(new DefaultProtein("test5", "P12348"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousInteractorSetComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_same_content(){
        InteractorSet interactor1 = new DefaultInteractorSet("test");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx1"));
        interactor1.add(new DefaultProtein("test2", "P12345"));
        interactor1.add(new DefaultProtein("test3", "P12346"));
        InteractorSet interactor2 = new DefaultInteractorSet("test");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx1"));
        interactor2.add(new DefaultProtein("test2", "P12345"));
        interactor2.add(new DefaultProtein("test3", "P12346"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousInteractorSetComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_same_content_ignore_order(){
        InteractorSet interactor1 = new DefaultInteractorSet("test");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx1"));
        interactor1.add(new DefaultProtein("test3", "P12346"));
        interactor1.add(new DefaultProtein("test2", "P12345"));
        InteractorSet interactor2 = new DefaultInteractorSet("test");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx1"));
        interactor2.add(new DefaultProtein("test2", "P12345"));
        interactor2.add(new DefaultProtein("test3", "P12346"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousInteractorSetComparator.areEquals(interactor1, interactor2));
    }
}
