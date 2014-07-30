package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultInteractorComparator;

/**
 * Unit tester for DefaultParticipant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/13</pre>
 */

public class DefaultParticipantTest {

    @Test
    public void test_create_participant(){

        Participant p = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertTrue(DefaultInteractorComparator.areEquals(InteractorUtils.createUnknownBasicInteractor(), p.getInteractor()));
        Assert.assertNotNull(p.getAliases());
        Assert.assertNotNull(p.getXrefs());
        Assert.assertNotNull(p.getAnnotations());
        Assert.assertNotNull(p.getCausalRelationships());
        Assert.assertNull(p.getStoichiometry());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), p.getBiologicalRole());

        p = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), (CvTerm) null);
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), p.getBiologicalRole());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_participant_no_interactor() throws Exception {
        Participant p = new DefaultParticipant (null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_participant_set_interactor_null() throws Exception {
        Participant p = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor());
        p.setInteractor(null);
    }

    @Test
    public void test_set_biological_role_null(){

        Participant p = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), p.getBiologicalRole());

        p.setBiologicalRole(new DefaultCvTerm("enzyme"));
        Assert.assertEquals(new DefaultCvTerm("enzyme"), p.getBiologicalRole());

        p.setBiologicalRole(null);
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), p.getBiologicalRole());
    }

    @Test
    public void test_create_participant_with_stoichiometry(){

        Participant p = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), new DefaultStoichiometry(1));
        Assert.assertEquals(new DefaultStoichiometry(1), p.getStoichiometry());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), p.getBiologicalRole());
    }

    @Test
    public void test_set_stoichiometry_as_Integer(){

        Participant p = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor());
        Assert.assertNull(p.getStoichiometry());

        p.setStoichiometry(1);
        Assert.assertEquals(new DefaultStoichiometry(1), p.getStoichiometry());

        p.setStoichiometry((Integer) null);
        Assert.assertNull(p.getStoichiometry());
    }
}
