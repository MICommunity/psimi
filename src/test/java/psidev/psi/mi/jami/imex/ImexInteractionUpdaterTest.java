package psidev.psi.mi.jami.imex;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.imex.mock.MockImexCentralClient;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.imex.actions.impl.ImexAssignerImpl;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.impl.*;

/**
 * Unit tester for ImexPublicationUpdater
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/11/14</pre>
 */

public class ImexInteractionUpdaterTest {

    private ImexInteractionUpdater updater;


    @Before
    public void initUpdater() throws BridgeFailedException {
        this.updater = new ImexInteractionUpdater();
        this.updater.setImexAssigner(new ImexAssignerImpl(new MockImexCentralClient()));
    }

    @Test
    public void update_publication_successful() throws EnricherException {

        psidev.psi.mi.jami.model.Publication pub = new DefaultPublication("12345");
        pub.assignImexId("IM-1");
        Experiment exp = new DefaultExperiment(pub);
        pub.getExperiments().add(exp);

        InteractionEvidence ev1 = new DefaultInteractionEvidence();
        ev1.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("P12345")));
        exp.addInteractionEvidence(ev1);
        InteractionEvidence ev2 = new DefaultInteractionEvidence();
        ev2.addParticipant(new DefaultParticipantEvidence(new DefaultProtein("P12346")));
        exp.addInteractionEvidence(ev2);

        updater.enrich(ev1);
        updater.enrich(ev2);

        Assert.assertEquals(1, ev1.getXrefs().size());
        Assert.assertEquals(1, ev2.getXrefs().size());

        Assert.assertEquals("IM-1-1", ev1.getImexId());
        Assert.assertEquals("IM-1-2", ev2.getImexId());
    }
}
