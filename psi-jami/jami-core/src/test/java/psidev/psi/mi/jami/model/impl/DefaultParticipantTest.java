package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;

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

        Participant<Interactor> p = new DefaultParticipant<Interactor>(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertEquals(InteractorUtils.createUnknownBasicInteractor(), p.getInteractor());
        Assert.assertNotNull(p.getAliases());
        Assert.assertNotNull(p.getXrefs());
        Assert.assertNotNull(p.getAnnotations());
        Assert.assertNull(p.getCausalRelationship());
        Assert.assertNull(p.getStoichiometry());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), p.getBiologicalRole());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_participant_no_interactor() throws Exception {
        Participant<Interactor> p = new DefaultParticipant<Interactor>(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_participant_set_interactor_null() throws Exception {
        Participant<Interactor> p = new DefaultParticipant<Interactor>(InteractorUtils.createUnknownBasicInteractor());
        p.setInteractor(null);
    }


}
