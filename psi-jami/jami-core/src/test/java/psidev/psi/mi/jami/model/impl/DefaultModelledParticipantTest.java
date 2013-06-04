package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.comparator.interaction.DefaultModelledInteractionComparator;

/**
 * Unit tester for DefaultModelledParticipant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class DefaultModelledParticipantTest {

    @Test
    public void test_create_empty_modelledParticipant(){

        ModelledParticipant participant = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertNotNull(participant.getFeatures());
        Assert.assertNull(participant.getModelledInteraction());
    }

    @Test
    public void test_set_unset_modelledInteraction(){

        ModelledParticipant participant = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());
        ModelledInteraction i = new DefaultModelledInteraction("test interaction");
        participant.setModelledInteraction(i);

        Assert.assertTrue(DefaultModelledInteractionComparator.areEquals(new DefaultModelledInteraction("test interaction"), participant.getModelledInteraction()));
        Assert.assertEquals(0, i.getParticipants().size());

        participant.setModelledInteractionAndAddModelledParticipant(i);
        Assert.assertNotNull(participant.getModelledInteraction());
        Assert.assertEquals(1, i.getParticipants().size());
        Assert.assertEquals(participant, i.getParticipants().iterator().next());

        participant.setModelledInteractionAndAddModelledParticipant(null);
        Assert.assertNull(participant.getModelledInteraction());
        Assert.assertEquals(0, i.getParticipants().size());
    }

    @Test
    public void test_add_remove_modelledFeatures(){
        ModelledParticipant participant = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());
        ModelledFeature f = new DefaultModelledFeature("test", "test feature");

        Assert.assertNull(f.getModelledParticipant());

        // add feature and set modelledParticipant
        participant.addModelledFeature(f);
        Assert.assertEquals(participant, f.getModelledParticipant());
        Assert.assertEquals(1, participant.getFeatures().size());

        // remove modelled feature and set participant to null
        participant.removeModelledFeature(f);
        Assert.assertNull(f.getModelledParticipant());
        Assert.assertEquals(0, participant.getFeatures().size());

        // simply add modelled feature
        participant.getFeatures().add(f);
        Assert.assertNull(f.getModelledParticipant());
        Assert.assertEquals(1, participant.getFeatures().size());

        // simply remove modelled feature
        participant.getFeatures().remove(f);
        Assert.assertNull(f.getModelledParticipant());
        Assert.assertEquals(0, participant.getFeatures().size());
    }
}
