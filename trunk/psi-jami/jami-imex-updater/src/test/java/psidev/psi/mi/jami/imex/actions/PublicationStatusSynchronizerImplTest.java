package psidev.psi.mi.jami.imex.actions;

import edu.ucla.mbi.imex.central.ws.v20.Identifier;
import edu.ucla.mbi.imex.central.ws.v20.Publication;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.imex.PublicationStatus;
import psidev.psi.mi.jami.bridges.imex.extension.ImexPublication;
import psidev.psi.mi.jami.bridges.imex.mock.MockImexCentralClient;
import psidev.psi.mi.jami.imex.actions.impl.PublicationStatusSynchronizerImpl;
import psidev.psi.mi.jami.model.impl.DefaultPublication;

import java.util.Date;

/**
 * Unit tester of publicationStatus synchronizer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/04/12</pre>
 */
public class PublicationStatusSynchronizerImplTest{

    private PublicationStatusSynchronizer imexStatusSynchronizerTest;
    private ImexPublication intactPub1;
    private ImexPublication intactPub2;

    @Before
    public void createImexPublications() throws BridgeFailedException {

        imexStatusSynchronizerTest = new PublicationStatusSynchronizerImpl(new MockImexCentralClient());

        Publication pub1 = new Publication();
        Identifier pubmed = new Identifier();
        pubmed.setNs("pmid");
        pubmed.setAc("12345");
        pub1.getIdentifier().add(pubmed);
        pub1.setStatus("NEW");
        intactPub1 = new ImexPublication(pub1);
        imexStatusSynchronizerTest.getImexCentralClient().createPublication(intactPub1);

        Publication pub2 = new Publication();
        Identifier pubmed2 = new Identifier();
        pubmed2.setNs("pmid");
        pubmed2.setAc("12346");
        pub2.getIdentifier().add(pubmed2);
        pub2.setStatus("INPROGRESS");
        intactPub2 = new ImexPublication(pub2);
        imexStatusSynchronizerTest.getImexCentralClient().createPublication(intactPub2);

    }

    @Test
    public void synchronize_no_change_status_new() throws BridgeFailedException {

        psidev.psi.mi.jami.model.Publication intactPublication = new DefaultPublication("12345");

        imexStatusSynchronizerTest.synchronizePublicationStatusWithImexCentral(intactPublication, intactPub1);

        Assert.assertEquals(PublicationStatus.NEW, intactPub1.getStatus());
    }

    @Test
    public void synchronize_release_status_update() throws BridgeFailedException {

        psidev.psi.mi.jami.model.Publication intactPublication = new DefaultPublication("12346");
        intactPublication.setReleasedDate(new Date());

        imexStatusSynchronizerTest.synchronizePublicationStatusWithImexCentral(intactPublication, intactPub2);

        Assert.assertEquals(PublicationStatus.RELEASED, intactPub2.getStatus());
    }
}
