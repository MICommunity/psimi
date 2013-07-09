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
        Assert.assertNull(participant.getInteraction());
    }

    @Test
    public void test_set_unset_modelledInteraction(){

        ModelledParticipant participant = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());
        ModelledInteraction i = new DefaultModelledInteraction("test interaction");
        participant.setInteraction(i);

        Assert.assertTrue(DefaultModelledInteractionComparator.areEquals(new DefaultModelledInteraction("test interaction"), participant.getInteraction()));
        Assert.assertEquals(0, i.getParticipants().size());

        participant.setInteractionAndAddParticipant(i);
        Assert.assertNotNull(participant.getInteraction());
        Assert.assertEquals(1, i.getParticipants().size());
        Assert.assertEquals(participant, i.getParticipants().iterator().next());

        participant.setInteractionAndAddParticipant(null);
        Assert.assertNull(participant.getInteraction());
        Assert.assertEquals(0, i.getParticipants().size());
    }

    @Test
    public void test_add_remove_modelledFeatures(){
        ModelledParticipant participant = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());
        ModelledFeature f = new DefaultModelledFeature("test", "test feature");

        Assert.assertNull(f.getParticipant());

        // add feature and set modelledParticipant
        participant.addFeature(f);
        Assert.assertEquals(participant, f.getParticipant());
        Assert.assertEquals(1, participant.getFeatures().size());

        // remove modelled feature and set participant to null
        participant.removeFeature(f);
        Assert.assertNull(f.getParticipant());
        Assert.assertEquals(0, participant.getFeatures().size());

        // simply add modelled feature
        participant.getFeatures().add(f);
        Assert.assertNull(f.getParticipant());
        Assert.assertEquals(1, participant.getFeatures().size());

        // simply remove modelled feature
        participant.getFeatures().remove(f);
        Assert.assertNull(f.getParticipant());
        Assert.assertEquals(0, participant.getFeatures().size());
    }
}
