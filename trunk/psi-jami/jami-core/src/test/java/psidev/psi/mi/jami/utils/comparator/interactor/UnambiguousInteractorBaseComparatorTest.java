package psidev.psi.mi.jami.utils.comparator.interactor;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultInteractor;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for UnambiguousInteractorBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/05/13</pre>
 */

public class UnambiguousInteractorBaseComparatorTest {

    private UnambiguousInteractorBaseComparator comparator = new UnambiguousInteractorBaseComparator();

    @Test
    public void test_interactor_null_after(){
        Interactor interactor1 = null;
        Interactor interactor2 = new DefaultInteractor("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousInteractorBaseComparator.hashCode(interactor1) != UnambiguousInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_same_shortname_one_identifier_not_equals(){
        Interactor interactor1 = new DefaultInteractor("test");
        Interactor interactor2 = new DefaultInteractor("test");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousInteractorBaseComparator.hashCode(interactor1) != UnambiguousInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_same_shortname_identifier_only_both_have_identifiers(){
        Interactor interactor1 = new DefaultInteractor("test");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12346"));
        Interactor interactor2 = new DefaultInteractor("test");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousInteractorBaseComparator.hashCode(interactor1) != UnambiguousInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_different_shortname_same_identifiers_equal(){
        Interactor interactor1 = new DefaultInteractor("test1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345"));
        Interactor interactor2 = new DefaultInteractor("test2");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousInteractorBaseComparator.hashCode(interactor1) == UnambiguousInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_one_identifier_different_not_equal(){
        Interactor interactor1 = new DefaultInteractor("test1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345"));
        Interactor interactor2 = new DefaultInteractor("test1");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345"));
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12355"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousInteractorBaseComparator.hashCode(interactor1) != UnambiguousInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_same_shortName(){
        Interactor interactor1 = new DefaultInteractor("test1");
        Interactor interactor2 = new DefaultInteractor("test1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousInteractorBaseComparator.hashCode(interactor1) == UnambiguousInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_different_shortName(){
        Interactor interactor1 = new DefaultInteractor("test1");
        Interactor interactor2 = new DefaultInteractor("test2");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousInteractorBaseComparator.hashCode(interactor1) != UnambiguousInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_shortName_case_sensitive(){
        Interactor interactor1 = new DefaultInteractor("TEST1");
        Interactor interactor2 = new DefaultInteractor("test1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousInteractorBaseComparator.hashCode(interactor1) != UnambiguousInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_different_shortname_same_fullname_not_equal(){
        Interactor interactor1 = new DefaultInteractor("test", "test interactor 1");
        Interactor interactor2 = new DefaultInteractor("test1", "test interactor 1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousInteractorBaseComparator.hashCode(interactor1) != UnambiguousInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_same_shortname_fullname_null_after(){
        Interactor interactor1 = new DefaultInteractor("test1");
        Interactor interactor2 = new DefaultInteractor("test1", "test interactor 1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousInteractorBaseComparator.hashCode(interactor1) != UnambiguousInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_different_fullname(){
        Interactor interactor1 = new DefaultInteractor("test", "testing interactor");
        Interactor interactor2 = new DefaultInteractor("test", "test interactor 1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(UnambiguousInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousInteractorBaseComparator.hashCode(interactor1) != UnambiguousInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_fullname_case_sensitive(){
        Interactor interactor1 = new DefaultInteractor("test", "interactor TEST 1");
        Interactor interactor2 = new DefaultInteractor("test", "interactor test 1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousInteractorBaseComparator.hashCode(interactor1) != UnambiguousInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_same_alias(){
        Interactor interactor1 = new DefaultInteractor("test");
        interactor1.getAliases().add(AliasUtils.createGeneName("test name"));
        Interactor interactor2 = new DefaultInteractor("test");
        interactor2.getAliases().add(AliasUtils.createGeneName("test name"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(UnambiguousInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousInteractorBaseComparator.hashCode(interactor1) == UnambiguousInteractorBaseComparator.hashCode(interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_different_aliases(){
        Interactor interactor1 = new DefaultInteractor("test");
        interactor1.getAliases().add(AliasUtils.createGeneName("test name"));
        Interactor interactor2 = new DefaultInteractor("test");
        interactor2.getAliases().add(AliasUtils.createGeneName("test name"));
        interactor2.getAliases().add(AliasUtils.createAuthorAssignedName("testing name"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(UnambiguousInteractorBaseComparator.areEquals(interactor1, interactor2));
        Assert.assertTrue(UnambiguousInteractorBaseComparator.hashCode(interactor1) != UnambiguousInteractorBaseComparator.hashCode(interactor2));
    }
}
