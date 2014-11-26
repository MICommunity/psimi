package psidev.psi.mi.jami.imex.actions;

import edu.ucla.mbi.imex.central.ws.v20.Identifier;
import edu.ucla.mbi.imex.central.ws.v20.Publication;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.imex.extension.ImexPublication;
import psidev.psi.mi.jami.bridges.imex.mock.MockImexCentralClient;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.imex.actions.impl.ImexAssignerImpl;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for IntactImexAssignerImpl
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/04/12</pre>
 */
public class ImexAssignerImplTest {

    private ImexAssigner assignerTest;

    private psidev.psi.mi.jami.model.Publication imexPublication;

    @Before
    public void createImexPublications() throws BridgeFailedException {

        this.assignerTest = new ImexAssignerImpl(new MockImexCentralClient());

        Publication pub = new Publication();
        Identifier pubmed = new Identifier();
        pubmed.setNs("pmid");
        pubmed.setAc("12345");
        pub.getIdentifier().add(pubmed);

        this.imexPublication = new ImexPublication(pub);

        assignerTest.getImexCentralClient().createPublication(imexPublication);
    }

    @Test
    public void assignImexPublication_validPubId_succesfull() throws BridgeFailedException {

        Assert.assertNull(imexPublication.getImexId());

        psidev.psi.mi.jami.model.Publication intactPub = new DefaultPublication("12345");

        psidev.psi.mi.jami.model.Publication assigned = assignerTest.assignImexIdentifier(intactPub, imexPublication);

        Assert.assertNotNull(assigned);

        Assert.assertEquals(1, intactPub.getXrefs().size());
        Xref ref = intactPub.getXrefs().iterator().next();
        Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
        Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
        Assert.assertEquals(imexPublication.getImexId(), ref.getId());
    }

    public void updateImexIdentifiersForAllExperiments() throws BridgeFailedException, EnricherException {

        psidev.psi.mi.jami.model.Publication intactPub = new DefaultPublication("12345");

        Experiment exp1 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp1);

        Experiment exp2 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp2);

        Experiment exp3 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp3);

        Assert.assertEquals(3, intactPub.getExperiments().size());

        Assert.assertTrue(assignerTest.updateImexIdentifierForExperiment(exp1, "IM-1"));
        Assert.assertTrue(assignerTest.updateImexIdentifierForExperiment(exp2, "IM-1"));
        Assert.assertTrue(assignerTest.updateImexIdentifierForExperiment(exp3, "IM-1"));

        for (Experiment exp : intactPub.getExperiments()){
            Assert.assertEquals(1, exp.getXrefs().size());
            Xref ref = exp.getXrefs().iterator().next();
            Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
            Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
            Assert.assertEquals("IM-1", ref.getId());
        }
    }

    @Test(expected = EnricherException.class)
    public void updateImexIdentifiersForAllExperiments_conflict() throws BridgeFailedException, EnricherException {

        psidev.psi.mi.jami.model.Publication intactPub = new DefaultPublication("12345");

        Experiment exp1 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp1);
        exp1.getXrefs().add(XrefUtils.createXrefWithQualifier(Xref.IMEX, Xref.IMEX_MI, "IM-2", Xref.IMEX_PRIMARY, Xref.IMEX_PRIMARY_MI));

        assignerTest.updateImexIdentifierForExperiment(exp1, "IM-1");
    }

    @Test
    public void updateImexIdentifiersForAllExperiments_existingImex() throws BridgeFailedException, EnricherException {

        psidev.psi.mi.jami.model.Publication intactPub = new DefaultPublication("12345");

        Experiment exp1 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp1);
        exp1.getXrefs().add(XrefUtils.createXrefWithQualifier(Xref.IMEX, Xref.IMEX_MI, "IM-1", Xref.IMEX_PRIMARY, Xref.IMEX_PRIMARY_MI));

        Experiment exp2 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp2);

        Experiment exp3 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp3);

        Assert.assertEquals(3, intactPub.getExperiments().size());

        Assert.assertFalse(assignerTest.updateImexIdentifierForExperiment(exp1, "IM-1"));
        Assert.assertTrue(assignerTest.updateImexIdentifierForExperiment(exp2, "IM-1"));
        Assert.assertTrue(assignerTest.updateImexIdentifierForExperiment(exp3, "IM-1"));

        // check that exp1 has not been updated
        Assert.assertEquals(1, exp1.getXrefs().size());
        Xref ref = exp1.getXrefs().iterator().next();
        Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
        Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
        Assert.assertEquals("IM-1", ref.getId());

        Assert.assertEquals(1, exp2.getXrefs().size());
        ref = exp2.getXrefs().iterator().next();
        Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
        Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
        Assert.assertEquals("IM-1", ref.getId());

        Assert.assertEquals(1, exp3.getXrefs().size());
        ref = exp3.getXrefs().iterator().next();
        Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
        Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
        Assert.assertEquals("IM-1", ref.getId());
    }

    @Test
    public void updateImexIdentifiersForAllExperiments_duplicatedImex() throws BridgeFailedException, EnricherException {

        psidev.psi.mi.jami.model.Publication intactPub = new DefaultPublication("12345");

        Experiment exp1 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp1);
        exp1.getXrefs().add(XrefUtils.createXrefWithQualifier(Xref.IMEX, Xref.IMEX_MI, "IM-1", Xref.IMEX_PRIMARY, Xref.IMEX_PRIMARY_MI));
        exp1.getXrefs().add(XrefUtils.createXrefWithQualifier(Xref.IMEX, Xref.IMEX_MI, "IM-1", Xref.IMEX_PRIMARY, Xref.IMEX_PRIMARY_MI));

        Experiment exp2 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp2);

        Experiment exp3 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp3);

        Assert.assertEquals(3, intactPub.getExperiments().size());
        Assert.assertEquals(2, exp1.getXrefs().size());

        Assert.assertTrue(assignerTest.updateImexIdentifierForExperiment(exp1, "IM-1"));
        Assert.assertTrue(assignerTest.updateImexIdentifierForExperiment(exp2, "IM-1"));
        Assert.assertTrue(assignerTest.updateImexIdentifierForExperiment(exp3, "IM-1"));

        for (Experiment exp : intactPub.getExperiments()){
            Assert.assertEquals(1, exp.getXrefs().size());
            Xref ref = exp.getXrefs().iterator().next();
            Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
            Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
            Assert.assertEquals("IM-1", ref.getId());
        }
    }

    @Test
    public void collectExistingInteractionImexIds() throws BridgeFailedException {

        assignerTest.clearInteractionImexContext();

        psidev.psi.mi.jami.model.Publication intactPub = new DefaultPublication("12345");
        intactPub.assignImexId("IM-1");
        Experiment exp1 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp1);
        InteractionEvidence ev1 = new DefaultInteractionEvidence();
        ev1.assignImexId("IM-1-1");
        ev1.setExperimentAndAddInteractionEvidence(exp1);

        Experiment exp2 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp2);
        InteractionEvidence ev2 = new DefaultInteractionEvidence();
        ev2.assignImexId("IM-1-2");
        ev2.setExperimentAndAddInteractionEvidence(exp2);

        Experiment exp3 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp3);
        InteractionEvidence ev3 = new DefaultInteractionEvidence();
        ev3.assignImexId("IM-1");
        ev3.setExperimentAndAddInteractionEvidence(exp3);

        int index = assignerTest.getNextImexChunkNumberAndFilterValidImexIdsFrom(intactPub);
        Assert.assertEquals(3, index);
    }

    @Test
    public void updateImexIdentifiersForAllInteractions() throws BridgeFailedException, EnricherException {

        psidev.psi.mi.jami.model.Publication intactPub = new DefaultPublication("12345");
        intactPub.assignImexId("IM-1");
        Experiment exp1 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp1);
        InteractionEvidence ev1 = new DefaultInteractionEvidence();
        ev1.setExperimentAndAddInteractionEvidence(exp1);
        ev1.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("P12345")));

        Experiment exp2 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp2);
        InteractionEvidence ev2 = new DefaultInteractionEvidence();
        ev2.setExperimentAndAddInteractionEvidence(exp2);
        ev2.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("P12346")));

        Experiment exp3 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp3);
        InteractionEvidence ev3 = new DefaultInteractionEvidence();
        ev3.setExperimentAndAddInteractionEvidence(exp3);
        ev3.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("P12347")));

        assignerTest.clearInteractionImexContext();

        Assert.assertTrue(assignerTest.updateImexIdentifierForInteraction(ev1, "IM-1"));
        Assert.assertTrue(assignerTest.updateImexIdentifierForInteraction(ev2, "IM-1"));
        Assert.assertTrue(assignerTest.updateImexIdentifierForInteraction(ev3, "IM-1"));

        Assert.assertEquals(1, ev1.getXrefs().size());
        Xref ref = ev1.getXrefs().iterator().next();
        Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
        Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
        Assert.assertTrue(ref.getId().startsWith("IM-1-"));

        Assert.assertEquals(1, ev2.getXrefs().size());
        ref = ev2.getXrefs().iterator().next();
        Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
        Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
        Assert.assertTrue(ref.getId().startsWith("IM-1-"));

        Assert.assertEquals(1, ev3.getXrefs().size());
        ref = ev3.getXrefs().iterator().next();
        Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
        Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
        Assert.assertTrue(ref.getId().startsWith("IM-1-"));
        
        Assert.assertNotSame(ev1.getImexId(), ev2.getImexId());
        Assert.assertNotSame(ev2.getImexId(), ev3.getImexId());
    }

    @Test(expected = EnricherException.class)
    public void updateImexIdentifiersForAllInteractions_conflict() throws BridgeFailedException, EnricherException {
        psidev.psi.mi.jami.model.Publication intactPub = new DefaultPublication("12345");
        intactPub.assignImexId("IM-1");
        Experiment exp1 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp1);
        InteractionEvidence ev1 = new DefaultInteractionEvidence();
        ev1.setExperimentAndAddInteractionEvidence(exp1);
        ev1.assignImexId("IM-1-1");
        ev1.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("P12345")));

        Experiment exp2 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp2);
        InteractionEvidence ev2 = new DefaultInteractionEvidence();
        ev2.setExperimentAndAddInteractionEvidence(exp2);
        ev2.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("P12346")));

        Experiment exp3 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp3);
        InteractionEvidence ev3 = new DefaultInteractionEvidence();
        ev3.setExperimentAndAddInteractionEvidence(exp3);
        ev3.assignImexId("IM-2-1");
        ev3.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("P12347")));

        assignerTest.clearInteractionImexContext();

        assignerTest.updateImexIdentifierForInteraction(ev3, "IM-1");
    }

    @Test
    public void updateImexIdentifiersForAllInteractions_existingImex() throws BridgeFailedException, EnricherException {
        psidev.psi.mi.jami.model.Publication intactPub = new DefaultPublication("12345");
        intactPub.assignImexId("IM-1");
        Experiment exp1 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp1);
        InteractionEvidence ev1 = new DefaultInteractionEvidence();
        ev1.setExperimentAndAddInteractionEvidence(exp1);
        ev1.assignImexId("IM-1-1");
        ev1.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("P12345")));

        Experiment exp2 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp2);
        InteractionEvidence ev2 = new DefaultInteractionEvidence();
        ev2.setExperimentAndAddInteractionEvidence(exp2);
        ev2.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("P12346")));

        Experiment exp3 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp3);
        InteractionEvidence ev3 = new DefaultInteractionEvidence();
        ev3.setExperimentAndAddInteractionEvidence(exp3);
        ev3.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("P12347")));

        assignerTest.clearInteractionImexContext();

        Assert.assertFalse(assignerTest.updateImexIdentifierForInteraction(ev1, "IM-1"));
        Assert.assertTrue(assignerTest.updateImexIdentifierForInteraction(ev2, "IM-1"));
        Assert.assertTrue(assignerTest.updateImexIdentifierForInteraction(ev3, "IM-1"));

        Assert.assertEquals(1, ev1.getXrefs().size());
        Xref ref = ev1.getXrefs().iterator().next();
        Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
        Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
        Assert.assertTrue(ref.getId().startsWith("IM-1-"));

        Assert.assertEquals(1, ev2.getXrefs().size());
        ref = ev2.getXrefs().iterator().next();
        Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
        Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
        Assert.assertTrue(ref.getId().startsWith("IM-1-"));

        Assert.assertEquals(1, ev3.getXrefs().size());
        ref = ev3.getXrefs().iterator().next();
        Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
        Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
        Assert.assertTrue(ref.getId().startsWith("IM-1-"));

        Assert.assertNotSame(ev1.getImexId(), ev2.getImexId());
        Assert.assertNotSame(ev2.getImexId(), ev3.getImexId());
    }

    @Test
    public void updateImexIdentifiersForAllInteractions_duplicatedImex() throws BridgeFailedException, EnricherException {
        psidev.psi.mi.jami.model.Publication intactPub = new DefaultPublication("12345");
        intactPub.assignImexId("IM-1");
        Experiment exp1 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp1);
        InteractionEvidence ev1 = new DefaultInteractionEvidence();
        ev1.setExperimentAndAddInteractionEvidence(exp1);
        ev1.assignImexId("IM-1-1");
        ev1.getXrefs().add(XrefUtils.createXrefWithQualifier(Xref.IMEX, Xref.IMEX_MI, "IM-1-1", Xref.IMEX_PRIMARY, Xref.IMEX_PRIMARY_MI));
        ev1.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("P12345")));

        Experiment exp2 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp2);
        InteractionEvidence ev2 = new DefaultInteractionEvidence();
        ev2.setExperimentAndAddInteractionEvidence(exp2);
        ev2.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("P12346")));

        Experiment exp3 = new DefaultExperiment(intactPub);
        intactPub.addExperiment(exp3);
        InteractionEvidence ev3 = new DefaultInteractionEvidence();
        ev3.setExperimentAndAddInteractionEvidence(exp3);
        ev3.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("P12347")));

        assignerTest.clearInteractionImexContext();

        Assert.assertTrue(assignerTest.updateImexIdentifierForInteraction(ev1, "IM-1"));
        Assert.assertTrue(assignerTest.updateImexIdentifierForInteraction(ev2, "IM-1"));
        Assert.assertTrue(assignerTest.updateImexIdentifierForInteraction(ev3, "IM-1"));

        Assert.assertEquals(1, ev1.getXrefs().size());
        Xref ref = ev1.getXrefs().iterator().next();
        Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
        Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
        Assert.assertTrue(ref.getId().startsWith("IM-1-"));

        Assert.assertEquals(1, ev2.getXrefs().size());
        ref = ev2.getXrefs().iterator().next();
        Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
        Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
        Assert.assertTrue(ref.getId().startsWith("IM-1-"));

        Assert.assertEquals(1, ev3.getXrefs().size());
        ref = ev3.getXrefs().iterator().next();
        Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
        Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
        Assert.assertTrue(ref.getId().startsWith("IM-1-"));

        Assert.assertNotSame(ev1.getImexId(), ev2.getImexId());
        Assert.assertNotSame(ev2.getImexId(), ev3.getImexId());
    }
}
