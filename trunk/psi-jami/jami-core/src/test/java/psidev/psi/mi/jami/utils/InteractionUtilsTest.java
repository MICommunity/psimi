package psidev.psi.mi.jami.utils;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.model.impl.DefaultStoichiometry;

/**
 * Unit tester for InteractionUtils
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/06/13</pre>
 */

public class InteractionUtilsTest {
    
    @Test
    public void test_find_interaction_category_ignore_experimental_role(){

        Assert.assertNull(InteractionUtils.findInteractionCategoryOf(null, false));

        InteractionEvidence binary = new DefaultInteractionEvidence();
        binary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        binary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p2")));

        InteractionEvidence self_intra = new DefaultInteractionEvidence();
        self_intra.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1"), new DefaultStoichiometry(1)));

        InteractionEvidence self_inter= new DefaultInteractionEvidence();
        self_inter.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1"), new DefaultStoichiometry(3)));

        InteractionEvidence self_intra_no_stoichiometry_but_self_exp_role = new DefaultInteractionEvidence();
        self_intra_no_stoichiometry_but_self_exp_role.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        self_intra_no_stoichiometry_but_self_exp_role.getParticipants().iterator().next().setExperimentalRole(CvTermUtils.createSelf());

        InteractionEvidence self_intra_no_stoichiometry_but_putative_self_exp_role = new DefaultInteractionEvidence();
        self_intra_no_stoichiometry_but_putative_self_exp_role.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        self_intra_no_stoichiometry_but_putative_self_exp_role.getParticipants().iterator().next().setExperimentalRole(CvTermUtils.createPutativeSelf());

        InteractionEvidence self_inter_no_stoichiometry = new DefaultInteractionEvidence();
        self_inter_no_stoichiometry.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));

        InteractionEvidence nary = new DefaultInteractionEvidence();
        nary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        nary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p2")));
        nary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p3")));

        Assert.assertEquals(ComplexType.binary, InteractionUtils.findInteractionCategoryOf(binary, false));
        Assert.assertEquals(ComplexType.self_intra_molecular, InteractionUtils.findInteractionCategoryOf(self_intra, false));
        Assert.assertEquals(ComplexType.self_inter_molecular, InteractionUtils.findInteractionCategoryOf(self_inter, false));
        Assert.assertEquals(ComplexType.self_inter_molecular, InteractionUtils.findInteractionCategoryOf(self_intra_no_stoichiometry_but_self_exp_role, false));
        Assert.assertEquals(ComplexType.self_inter_molecular, InteractionUtils.findInteractionCategoryOf(self_intra_no_stoichiometry_but_putative_self_exp_role, false));
        Assert.assertEquals(ComplexType.self_inter_molecular, InteractionUtils.findInteractionCategoryOf(self_inter_no_stoichiometry, false));
        Assert.assertEquals(ComplexType.n_ary, InteractionUtils.findInteractionCategoryOf(nary, false));
    }

    @Test
    public void test_find_interaction_category_check_experimental_role(){

        Assert.assertNull(InteractionUtils.findInteractionCategoryOf(null, false));

        InteractionEvidence binary = new DefaultInteractionEvidence();
        binary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        binary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p2")));

        InteractionEvidence self_intra = new DefaultInteractionEvidence();
        self_intra.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1"), new DefaultStoichiometry(1)));

        InteractionEvidence self_inter= new DefaultInteractionEvidence();
        self_inter.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1"), new DefaultStoichiometry(3)));

        InteractionEvidence self_intra_no_stoichiometry_but_self_exp_role = new DefaultInteractionEvidence();
        self_intra_no_stoichiometry_but_self_exp_role.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        self_intra_no_stoichiometry_but_self_exp_role.getParticipants().iterator().next().setExperimentalRole(CvTermUtils.createSelf());

        InteractionEvidence self_intra_no_stoichiometry_but_putative_self_exp_role = new DefaultInteractionEvidence();
        self_intra_no_stoichiometry_but_putative_self_exp_role.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        self_intra_no_stoichiometry_but_putative_self_exp_role.getParticipants().iterator().next().setExperimentalRole(CvTermUtils.createPutativeSelf());

        InteractionEvidence self_inter_no_stoichiometry = new DefaultInteractionEvidence();
        self_inter_no_stoichiometry.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));

        InteractionEvidence nary = new DefaultInteractionEvidence();
        nary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        nary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p2")));
        nary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p3")));

        Assert.assertEquals(ComplexType.binary, InteractionUtils.findInteractionCategoryOf(binary, true));
        Assert.assertEquals(ComplexType.self_intra_molecular, InteractionUtils.findInteractionCategoryOf(self_intra, true));
        Assert.assertEquals(ComplexType.self_inter_molecular, InteractionUtils.findInteractionCategoryOf(self_inter, true));
        Assert.assertEquals(ComplexType.self_intra_molecular, InteractionUtils.findInteractionCategoryOf(self_intra_no_stoichiometry_but_self_exp_role, true));
        Assert.assertEquals(ComplexType.self_intra_molecular, InteractionUtils.findInteractionCategoryOf(self_intra_no_stoichiometry_but_putative_self_exp_role, true));
        Assert.assertEquals(ComplexType.self_inter_molecular, InteractionUtils.findInteractionCategoryOf(self_inter_no_stoichiometry, true));
        Assert.assertEquals(ComplexType.n_ary, InteractionUtils.findInteractionCategoryOf(nary, true));
    }

    @Test
    public void test_find_interaction_evidence_category(){

        Assert.assertNull(InteractionUtils.findInteractionCategoryOf(null, false));

        InteractionEvidence binary = new DefaultInteractionEvidence();
        binary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        binary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p2")));

        InteractionEvidence self_intra = new DefaultInteractionEvidence();
        self_intra.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1"), new DefaultStoichiometry(1)));

        InteractionEvidence self_inter= new DefaultInteractionEvidence();
        self_inter.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1"), new DefaultStoichiometry(3)));

        InteractionEvidence self_intra_no_stoichiometry_but_self_exp_role = new DefaultInteractionEvidence();
        self_intra_no_stoichiometry_but_self_exp_role.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        self_intra_no_stoichiometry_but_self_exp_role.getParticipants().iterator().next().setExperimentalRole(CvTermUtils.createSelf());

        InteractionEvidence self_intra_no_stoichiometry_but_putative_self_exp_role = new DefaultInteractionEvidence();
        self_intra_no_stoichiometry_but_putative_self_exp_role.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        self_intra_no_stoichiometry_but_putative_self_exp_role.getParticipants().iterator().next().setExperimentalRole(CvTermUtils.createPutativeSelf());

        InteractionEvidence self_inter_no_stoichiometry = new DefaultInteractionEvidence();
        self_inter_no_stoichiometry.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));

        InteractionEvidence nary = new DefaultInteractionEvidence();
        nary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        nary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p2")));
        nary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p3")));

        Assert.assertEquals(ComplexType.binary, InteractionUtils.findInteractionEvidenceCategoryOf(binary));
        Assert.assertEquals(ComplexType.self_intra_molecular, InteractionUtils.findInteractionEvidenceCategoryOf(self_intra));
        Assert.assertEquals(ComplexType.self_inter_molecular, InteractionUtils.findInteractionEvidenceCategoryOf(self_inter));
        Assert.assertEquals(ComplexType.self_intra_molecular, InteractionUtils.findInteractionEvidenceCategoryOf(self_intra_no_stoichiometry_but_self_exp_role));
        Assert.assertEquals(ComplexType.self_intra_molecular, InteractionUtils.findInteractionEvidenceCategoryOf(self_intra_no_stoichiometry_but_putative_self_exp_role));
        Assert.assertEquals(ComplexType.self_inter_molecular, InteractionUtils.findInteractionEvidenceCategoryOf(self_inter_no_stoichiometry));
        Assert.assertEquals(ComplexType.n_ary, InteractionUtils.findInteractionEvidenceCategoryOf(nary));
    }
}
