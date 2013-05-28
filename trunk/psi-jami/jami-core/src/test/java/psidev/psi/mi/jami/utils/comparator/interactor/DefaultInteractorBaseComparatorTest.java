package psidev.psi.mi.jami.utils.comparator.interactor;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultInteractor;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for DefaultInteractorBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class DefaultInteractorBaseComparatorTest {

    private DefaultInteractorBaseComparator comparator = new DefaultInteractorBaseComparator();

    @Test
    public void test_interactor_null_after(){
        Interactor interactor1 = null;
        Interactor interactor2 = new DefaultInteractor("test");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(DefaultInteractorBaseComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_same_shortname_one_identifier_equal_because_shortName(){
        Interactor interactor1 = new DefaultInteractor("test");
        Interactor interactor2 = new DefaultInteractor("test");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultInteractorBaseComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_same_shortname_identifier_only_both_have_identifiers(){
        Interactor interactor1 = new DefaultInteractor("test");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12346"));
        Interactor interactor2 = new DefaultInteractor("test");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(DefaultInteractorBaseComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_different_shortname_same_identifiers_equal(){
        Interactor interactor1 = new DefaultInteractor("test1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345"));
        Interactor interactor2 = new DefaultInteractor("test2");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultInteractorBaseComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_at_least_one_identifier_equal(){
        Interactor interactor1 = new DefaultInteractor("test1");
        interactor1.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345"));
        Interactor interactor2 = new DefaultInteractor("test2");
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345"));
        interactor2.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12355"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultInteractorBaseComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_same_shortName(){
        Interactor interactor1 = new DefaultInteractor("test1");
        Interactor interactor2 = new DefaultInteractor("test1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultInteractorBaseComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_different_shortName(){
        Interactor interactor1 = new DefaultInteractor("test1");
        Interactor interactor2 = new DefaultInteractor("test2");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(DefaultInteractorBaseComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_shortName_case_sensitive(){
        Interactor interactor1 = new DefaultInteractor("TEST1");
        Interactor interactor2 = new DefaultInteractor("test1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(DefaultInteractorBaseComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_different_shortname_same_fullname(){
        Interactor interactor1 = new DefaultInteractor("test", "test interactor 1");
        Interactor interactor2 = new DefaultInteractor("test1", "test interactor 1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultInteractorBaseComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_different_shortname_ignore_fullName_because_one_is_null(){
        Interactor interactor1 = new DefaultInteractor("test1");
        Interactor interactor2 = new DefaultInteractor("test", "test interactor 1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(DefaultInteractorBaseComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_different_fullname(){
        Interactor interactor1 = new DefaultInteractor("test", "testing interactor");
        Interactor interactor2 = new DefaultInteractor("test1", "test interactor 1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) > 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) < 0);

        Assert.assertFalse(DefaultInteractorBaseComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_fullname_case_sensitive(){
        Interactor interactor1 = new DefaultInteractor("test1", "interactor TEST 1");
        Interactor interactor2 = new DefaultInteractor("test", "interactor test 1");

        Assert.assertTrue(comparator.compare(interactor1, interactor2) < 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) > 0);

        Assert.assertFalse(DefaultInteractorBaseComparator.areEquals(interactor1, interactor2));
    }

    @Test
    public void test_interactor_no_identifiers_different_shortname_fullname_one_same_alias(){
        Interactor interactor1 = new DefaultInteractor("test1", "interactor TEST 1");
        interactor1.getAliases().add(AliasUtils.createGeneName("test name"));
        Interactor interactor2 = new DefaultInteractor("test", "interactor test 1");
        interactor2.getAliases().add(AliasUtils.createGeneName("test name"));
        interactor2.getAliases().add(AliasUtils.createAuthorAssignedName("testing name"));

        Assert.assertTrue(comparator.compare(interactor1, interactor2) == 0);
        Assert.assertTrue(comparator.compare(interactor2, interactor1) == 0);

        Assert.assertTrue(DefaultInteractorBaseComparator.areEquals(interactor1, interactor2));
    }
}
