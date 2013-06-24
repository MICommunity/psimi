package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CausalRelationship;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultParticipantBaseComparator;

/**
 * Unit tester for CausalRelationship
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/13</pre>
 */

public class DefaultCausalRelationshipTest {

    @Test
    public void create_causal_relationship(){

        CausalRelationship rel = new DefaultCausalRelationship(CvTermUtils.createMICvTerm("decreases", "MI:xxxx"), new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));

        Assert.assertEquals(CvTermUtils.createMICvTerm("decreases", "MI:xxxx"), rel.getRelationType());
        org.junit.Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()), rel.getTarget(), true));
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_causal_relationship_no_relationType() throws Exception {
        Participant<Interactor> p = new DefaultParticipant<Interactor>(null);
        CausalRelationship rel = new DefaultCausalRelationship(null, new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_causal_relationship_no_target() throws Exception {
        Participant<Interactor> p = new DefaultParticipant<Interactor>(null);
        CausalRelationship rel = new DefaultCausalRelationship(CvTermUtils.createMICvTerm("decreases", "MI:xxxx"), null);
    }
}
