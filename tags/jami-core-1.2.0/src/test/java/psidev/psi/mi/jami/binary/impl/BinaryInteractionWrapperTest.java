package psidev.psi.mi.jami.binary.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.DefaultInteraction;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.InteractorUtils;

import java.util.Collection;

/**
 * Unit tester for BinaryInteractionWrapper
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/06/13</pre>
 */

public class BinaryInteractionWrapperTest {

    @Test
    public void create_wrapper(){

        Interaction interaction = new DefaultInteraction();

        BinaryInteraction<Participant> binary = new BinaryInteractionWrapper(interaction);
        Assert.assertNull(binary.getShortName());
        Assert.assertNull(binary.getInteractionType());
        Assert.assertNull(binary.getCreatedDate());
        Assert.assertNull(binary.getUpdatedDate());
        Assert.assertNull(binary.getRigid());
        Assert.assertNotNull(binary.getAnnotations());
        Assert.assertNotNull(binary.getXrefs());
        Assert.assertNotNull(binary.getChecksums());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_create_binary_wrapper_no_interactions(){
        BinaryInteraction<Participant> binary = new BinaryInteractionWrapper(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_create_binary_wrapper_more_than_2_participants(){
        Interaction interaction = new DefaultInteraction();
        ((Collection<Participant>)interaction.getParticipants()).add(new DefaultParticipant(new DefaultProtein("p1")));
        ((Collection<Participant>)interaction.getParticipants()).add(new DefaultParticipant(new DefaultProtein("p2")));
        ((Collection<Participant>)interaction.getParticipants()).add(new DefaultParticipant(new DefaultProtein("p3")));

        BinaryInteraction<Participant> binary = new BinaryInteractionWrapper(interaction);
    }

    @Test
    public void create_wrapper_set_unset_participants(){

        Interaction interaction = new DefaultInteraction();
        Participant p1 = new DefaultParticipant(new DefaultProtein("p1"));
        Participant p2 = new DefaultParticipant(new DefaultProtein("p2"));
        ((Collection<Participant>)interaction.getParticipants()).add(p1);
        ((Collection<Participant>)interaction.getParticipants()).add(p2);

        BinaryInteraction<Participant> binary = new BinaryInteractionWrapper(interaction);
        Assert.assertTrue(binary.getParticipantB() == p2);
        Assert.assertTrue(binary.getParticipantA() == p1);

        Participant p3 = new DefaultParticipant(new DefaultProtein("p3"));
        binary.setParticipantA(p3);
        Assert.assertTrue(binary.getParticipantB() == p2);
        Assert.assertTrue(binary.getParticipantA() == p3);
        Assert.assertTrue(interaction.getParticipants().contains(p3));
        Assert.assertFalse(interaction.getParticipants().contains(p1));
    }

    @Test
    public void create_wrapper_self_interaction(){

        Interaction interaction = new DefaultInteraction();
        Participant p1 = new DefaultParticipant(new DefaultProtein("p1"));
        ((Collection<Participant>)interaction.getParticipants()).add(p1);

        BinaryInteraction<Participant> binary = new BinaryInteractionWrapper(interaction);
        Assert.assertTrue(binary.getParticipantB() == null);
        Assert.assertTrue(binary.getParticipantA() == p1);

        Participant p3 = new DefaultParticipant(new DefaultProtein("p3"));
        binary.setParticipantA(p3);
        Assert.assertTrue(binary.getParticipantB() == null);
        Assert.assertTrue(binary.getParticipantA() == p3);
        Assert.assertTrue(interaction.getParticipants().contains(p3));
        Assert.assertFalse(interaction.getParticipants().contains(p1));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_cannot_add_participants(){
        Interaction interaction = new DefaultInteraction();
        Participant p1 = new DefaultParticipant(new DefaultProtein("p1"));
        ((Collection<Participant>)interaction.getParticipants()).add(p1);

        BinaryInteraction<Participant> binary = new BinaryInteractionWrapper(interaction);

        ((Collection<Participant>)binary.getParticipants()).add(new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));
    }
}
