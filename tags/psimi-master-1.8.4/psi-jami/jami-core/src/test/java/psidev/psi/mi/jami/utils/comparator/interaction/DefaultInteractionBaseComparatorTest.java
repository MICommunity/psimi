package psidev.psi.mi.jami.utils.comparator.interaction;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultInteraction;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for DefaultInteractionBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class DefaultInteractionBaseComparatorTest {

    @Test
    public void test_interaction_null_after(){
        Interaction interaction1 = null;
        Interaction interaction2 = new DefaultInteraction("test");

        Assert.assertFalse(DefaultInteractionBaseComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_different_interaction_types(){
        Interaction interaction1 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        Interaction interaction2 = new DefaultInteraction("test", new DefaultCvTerm("direct interaction"));

        Assert.assertFalse(DefaultInteractionBaseComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_same_interaction_types(){
        Interaction interaction1 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        Interaction interaction2 = new DefaultInteraction("test", new DefaultCvTerm("association"));

        Assert.assertTrue(DefaultInteractionBaseComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_different_rigids(){
        Interaction interaction1 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction1.setRigid("xxxxx2");
        Interaction interaction2 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction2.setRigid("xxxxx1");
        Assert.assertFalse(DefaultInteractionBaseComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_rigid_null_ignored(){
        Interaction interaction1 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        Interaction interaction2 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction2.setRigid("xxxxx1");

        Assert.assertTrue(DefaultInteractionBaseComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_same_rigids(){
        Interaction interaction1 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction1.setRigid("xxxxx1");
        Interaction interaction2 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction2.setRigid("xxxxx1");

        Assert.assertTrue(DefaultInteractionBaseComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_different_identifiers(){
        Interaction interaction1 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction1.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx"));
        Interaction interaction2 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction2.getIdentifiers().add(XrefUtils.createIdentityXref("mint", "MINT-xxx"));

        Assert.assertFalse(DefaultInteractionBaseComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_empty_identifiers_ignored(){
        Interaction interaction1 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        Interaction interaction2 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction2.getIdentifiers().add(XrefUtils.createIdentityXref("mint", "MINT-xxx"));

        Assert.assertTrue(DefaultInteractionBaseComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_at_least_one_same_identifier(){
        Interaction interaction1 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction1.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx"));
        Interaction interaction2 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction2.getIdentifiers().add(XrefUtils.createIdentityXref("mint", "MINT-xxx"));
        interaction2.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx"));

        Assert.assertTrue(DefaultInteractionBaseComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_shortname_null_after(){
        Interaction interaction1 = new DefaultInteraction(null, new DefaultCvTerm("association"));
        Interaction interaction2 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction2.getIdentifiers().add(XrefUtils.createIdentityXref("mint", "MINT-xxx"));
        interaction2.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx"));

        Assert.assertFalse(DefaultInteractionBaseComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_different_shortnames(){
        Interaction interaction1 = new DefaultInteraction("test 2", new DefaultCvTerm("association"));
        Interaction interaction2 = new DefaultInteraction("test", new DefaultCvTerm("association"));
        interaction2.getIdentifiers().add(XrefUtils.createIdentityXref("mint", "MINT-xxx"));
        interaction2.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx"));

        Assert.assertFalse(DefaultInteractionBaseComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_different_shortnames_case_senistive(){
        Interaction interaction1 = new DefaultInteraction("test 2", new DefaultCvTerm("association"));
        Interaction interaction2 = new DefaultInteraction("TEST 2", new DefaultCvTerm("association"));
        interaction2.getIdentifiers().add(XrefUtils.createIdentityXref("mint", "MINT-xxx"));
        interaction2.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxx"));

        Assert.assertFalse(DefaultInteractionBaseComparator.areEquals(interaction1, interaction2));
    }
}
