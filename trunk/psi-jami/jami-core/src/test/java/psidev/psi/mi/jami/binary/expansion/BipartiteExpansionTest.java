package psidev.psi.mi.jami.binary.expansion;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.impl.*;
import psidev.psi.mi.jami.exception.ComplexExpansionException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.Collection;
import java.util.Iterator;

/**
 * Unit tester for BipartiteExpansion method
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/06/13</pre>
 */

public class BipartiteExpansionTest {

    private BipartiteExpansion expansion = new BipartiteExpansion();

    @Test
    public void test_method(){

        Assert.assertEquals(CvTermUtils.createMICvTerm(ComplexExpansionMethod.BIPARTITE_EXPANSION, ComplexExpansionMethod.BIPARTITE_EXPANSION_MI), expansion.getMethod());
    }

    @Test
    public void test_is_expandable(){

        Assert.assertFalse(expansion.isInteractionExpandable(null));
        Assert.assertFalse(expansion.isInteractionExpandable(new DefaultModelledInteraction()));
        Assert.assertFalse(expansion.isInteractionExpandable(null));
        Assert.assertFalse(expansion.isInteractionExpandable(new DefaultInteractionEvidence()));
        Assert.assertFalse(expansion.isInteractionExpandable(null));
        Assert.assertFalse(expansion.isInteractionExpandable(new DefaultInteraction()));

        InteractionEvidence interactionEvidence = new DefaultInteractionEvidence();
        interactionEvidence.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        ModelledInteraction modelledInteraction = new DefaultModelledInteraction();
        modelledInteraction.addParticipant(new DefaultModelledParticipant(new DefaultProtein("p1")));
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        interaction.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        Assert.assertTrue(expansion.isInteractionExpandable(interactionEvidence));
        Assert.assertTrue(expansion.isInteractionExpandable(modelledInteraction));
        Assert.assertTrue(expansion.isInteractionExpandable(interaction));
    }

    @Test
    public void test_expand_default_interaction() throws ComplexExpansionException {

        InteractionEvidence binary = new DefaultInteractionEvidence();
        binary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        binary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p2")));

        InteractionEvidence self_intra = new DefaultInteractionEvidence();
        self_intra.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1"), new DefaultStoichiometry(1)));

        InteractionEvidence self_inter= new DefaultInteractionEvidence();
        self_inter.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1"), new DefaultStoichiometry(3)));

        InteractionEvidence nary = new DefaultInteractionEvidence();
        ParticipantEvidence p1 = new DefaultParticipantEvidence(new DefaultProtein("p1"));
        ParticipantEvidence p2 = new DefaultParticipantEvidence(new DefaultProtein("p2"));
        ParticipantEvidence p3 = new DefaultParticipantEvidence(new DefaultProtein("p3"));
        nary.addParticipant(p1);
        nary.addParticipant(p2);
        nary.addParticipant(p3);

        Collection<? extends BinaryInteraction> binaryExpanded = expansion.expand(binary);
        Assert.assertEquals(1, binaryExpanded.size());
        Assert.assertTrue(binaryExpanded.iterator().next() instanceof BinaryInteractionEvidenceWrapper);
        Iterator<? extends Participant> iterator = binary.getParticipants().iterator();
        Assert.assertTrue(binaryExpanded.iterator().next().getParticipantA() == iterator.next());
        Assert.assertTrue(binaryExpanded.iterator().next().getParticipantB() == iterator.next());

        Collection<? extends BinaryInteraction> self_intra_expanded = expansion.expand(self_intra);
        Assert.assertEquals(1, self_intra_expanded.size());
        Assert.assertTrue(self_intra_expanded.iterator().next() instanceof BinaryInteractionEvidenceWrapper);
        Iterator<? extends Participant> iterator2 = self_intra.getParticipants().iterator();
        Assert.assertTrue(self_intra_expanded.iterator().next().getParticipantA() == iterator2.next());
        Assert.assertNull(self_intra_expanded.iterator().next().getParticipantB());

        Collection<? extends BinaryInteraction> self_inter_expanded = expansion.expand(self_inter);
        Assert.assertEquals(1, self_inter_expanded.size());
        Assert.assertTrue(self_inter_expanded.iterator().next() instanceof DefaultBinaryInteractionEvidence);
        Iterator<? extends Participant> iterator3 = self_inter.getParticipants().iterator();
        Assert.assertTrue(self_inter_expanded.iterator().next().getParticipantA() == iterator3.next());
        Assert.assertNotNull(self_inter_expanded.iterator().next().getParticipantB());
        Assert.assertEquals(new DefaultStoichiometry(0), self_inter_expanded.iterator().next().getParticipantB().getStoichiometry());

        Collection<? extends BinaryInteraction> nary_expanded = expansion.expand(nary);
        Assert.assertEquals(3, nary_expanded.size());
        for (BinaryInteraction binaryInteraction : nary_expanded){
            Assert.assertTrue(binaryInteraction instanceof DefaultBinaryInteractionEvidence);
            Assert.assertTrue(binaryInteraction.getParticipantB() == p2 || binaryInteraction.getParticipantB() == p1 || binaryInteraction.getParticipantB() == p3);
            Assert.assertTrue(binaryInteraction.getParticipantA().getInteractor() instanceof Complex);
        }
    }

    @Test
    public void test_expand_interaction_evidence() throws ComplexExpansionException {

        InteractionEvidence binary = new DefaultInteractionEvidence();
        binary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        binary.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p2")));

        InteractionEvidence self_intra = new DefaultInteractionEvidence();
        self_intra.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1"), new DefaultStoichiometry(1)));

        InteractionEvidence self_inter= new DefaultInteractionEvidence();
        self_inter.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("p1"), new DefaultStoichiometry(3)));

        InteractionEvidence nary = new DefaultInteractionEvidence();
        ParticipantEvidence p1 = new DefaultParticipantEvidence(new DefaultProtein("p1"));
        ParticipantEvidence p2 = new DefaultParticipantEvidence(new DefaultProtein("p2"));
        ParticipantEvidence p3 = new DefaultParticipantEvidence(new DefaultProtein("p3"));
        nary.addParticipant(p1);
        nary.addParticipant(p2);
        nary.addParticipant(p3);

        Collection<? extends BinaryInteraction> binaryExpanded = expansion.expand(binary);
        Assert.assertEquals(1, binaryExpanded.size());
        Assert.assertTrue(binaryExpanded.iterator().next() instanceof BinaryInteractionEvidenceWrapper);
        Iterator<? extends Participant> iterator = binary.getParticipants().iterator();
        Assert.assertTrue(binaryExpanded.iterator().next().getParticipantA() == iterator.next());
        Assert.assertTrue(binaryExpanded.iterator().next().getParticipantB() == iterator.next());

        Collection<? extends BinaryInteraction> self_intra_expanded = expansion.expand(self_intra);
        Assert.assertEquals(1, self_intra_expanded.size());
        Assert.assertTrue(self_intra_expanded.iterator().next() instanceof BinaryInteractionEvidenceWrapper);
        Iterator<? extends Participant> iterator2 = self_intra.getParticipants().iterator();
        Assert.assertTrue(self_intra_expanded.iterator().next().getParticipantA() == iterator2.next());
        Assert.assertNull(self_intra_expanded.iterator().next().getParticipantB());

        Collection<? extends BinaryInteraction> self_inter_expanded = expansion.expand(self_inter);
        Assert.assertEquals(1, self_inter_expanded.size());
        Assert.assertTrue(self_inter_expanded.iterator().next() instanceof DefaultBinaryInteractionEvidence);
        Iterator<? extends Participant> iterator3 = self_inter.getParticipants().iterator();
        Assert.assertTrue(self_inter_expanded.iterator().next().getParticipantA() == iterator3.next());
        Assert.assertNotNull(self_inter_expanded.iterator().next().getParticipantB());
        Assert.assertEquals(new DefaultStoichiometry(0), self_inter_expanded.iterator().next().getParticipantB().getStoichiometry());

        Collection<? extends BinaryInteraction> nary_expanded = expansion.expand(nary);
        Assert.assertEquals(3, nary_expanded.size());
        for (BinaryInteraction binaryInteraction : nary_expanded){
            Assert.assertTrue(binaryInteraction instanceof DefaultBinaryInteractionEvidence);
            Assert.assertTrue(binaryInteraction.getParticipantB() == p2 || binaryInteraction.getParticipantB() == p1 || binaryInteraction.getParticipantB() == p3);
            Assert.assertTrue(binaryInteraction.getParticipantA().getInteractor() instanceof Complex);
        }
    }

    @Test
    public void test_expand_modelled_interaction() throws ComplexExpansionException {

        ModelledInteraction binary = new DefaultModelledInteraction();
        binary.addParticipant(new DefaultModelledParticipant(new DefaultProtein("p1")));
        binary.addParticipant(new DefaultModelledParticipant(new DefaultProtein("p2")));

        ModelledInteraction self_intra = new DefaultModelledInteraction();
        self_intra.addParticipant(new DefaultModelledParticipant(new DefaultProtein("p1"), new DefaultStoichiometry(1)));

        ModelledInteraction self_inter= new DefaultModelledInteraction();
        self_inter.addParticipant(new DefaultModelledParticipant(new DefaultProtein("p1"), new DefaultStoichiometry(3)));

        ModelledInteraction nary = new DefaultModelledInteraction();
        ModelledParticipant p1 = new DefaultModelledParticipant(new DefaultProtein("p1"));
        ModelledParticipant p2 = new DefaultModelledParticipant(new DefaultProtein("p2"));
        ModelledParticipant p3 = new DefaultModelledParticipant(new DefaultProtein("p3"));
        nary.addParticipant(p1);
        nary.addParticipant(p2);
        nary.addParticipant(p3);

        Collection<? extends BinaryInteraction> binaryExpanded = expansion.expand(binary);
        Assert.assertEquals(1, binaryExpanded.size());
        Assert.assertTrue(binaryExpanded.iterator().next() instanceof ModelledBinaryInteractionWrapper);
        Iterator<? extends Participant> iterator = binary.getParticipants().iterator();
        Assert.assertTrue(binaryExpanded.iterator().next().getParticipantA() == iterator.next());
        Assert.assertTrue(binaryExpanded.iterator().next().getParticipantB() == iterator.next());

        Collection<? extends BinaryInteraction> self_intra_expanded = expansion.expand(self_intra);
        Assert.assertEquals(1, self_intra_expanded.size());
        Assert.assertTrue(self_intra_expanded.iterator().next() instanceof ModelledBinaryInteractionWrapper);
        Iterator<? extends Participant> iterator2 = self_intra.getParticipants().iterator();
        Assert.assertTrue(self_intra_expanded.iterator().next().getParticipantA() == iterator2.next());
        Assert.assertNull(self_intra_expanded.iterator().next().getParticipantB());

        Collection<? extends BinaryInteraction> self_inter_expanded = expansion.expand(self_inter);
        Assert.assertEquals(1, self_inter_expanded.size());
        Assert.assertTrue(self_inter_expanded.iterator().next() instanceof DefaultModelledBinaryInteraction);
        Iterator<? extends Participant> iterator3 = self_inter.getParticipants().iterator();
        Assert.assertTrue(self_inter_expanded.iterator().next().getParticipantA() == iterator3.next());
        Assert.assertNotNull(self_inter_expanded.iterator().next().getParticipantB());
        Assert.assertEquals(new DefaultStoichiometry(0), self_inter_expanded.iterator().next().getParticipantB().getStoichiometry());

        Collection<? extends BinaryInteraction> nary_expanded = expansion.expand(nary);
        Assert.assertEquals(3, nary_expanded.size());
        for (BinaryInteraction binaryInteraction : nary_expanded){
            Assert.assertTrue(binaryInteraction instanceof DefaultModelledBinaryInteraction);
            Assert.assertTrue(binaryInteraction.getParticipantB() == p2 || binaryInteraction.getParticipantB() == p1 || binaryInteraction.getParticipantB() == p3);
            Assert.assertTrue(binaryInteraction.getParticipantA().getInteractor() instanceof Complex);
        }
    }
}
