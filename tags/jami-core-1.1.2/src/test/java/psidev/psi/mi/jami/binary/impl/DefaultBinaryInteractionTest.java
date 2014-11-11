package psidev.psi.mi.jami.binary.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.InteractorUtils;

import java.util.Collection;

/**
 * Unit tester for DefaultBinaryInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/06/13</pre>
 */

public class DefaultBinaryInteractionTest {

    @Test
    public void test_create_empty_new_binary(){
        Participant p1 = new DefaultParticipant(new DefaultProtein("p1"));
        Participant p2 = new DefaultParticipant(new DefaultProtein("p2"));

        BinaryInteraction<Participant> binary = new DefaultBinaryInteraction(p1,
                p2, new DefaultCvTerm("spoke expansion"));

        Assert.assertTrue(p1 == binary.getParticipantA());
        Assert.assertTrue(p2 == binary.getParticipantB());
        Assert.assertEquals(new DefaultCvTerm("spoke expansion"), binary.getComplexExpansion());

        Assert.assertEquals(2, binary.getParticipants().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_cannot_add_participants(){
        Participant p1 = new DefaultParticipant(new DefaultProtein("p1"));
        Participant p2 = new DefaultParticipant(new DefaultProtein("p2"));

        BinaryInteraction<Participant> binary = new DefaultBinaryInteraction(p1,
                p2, new DefaultCvTerm("spoke expansion"));

        ((Collection<Participant>)binary.getParticipants()).add(new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));
    }
}
