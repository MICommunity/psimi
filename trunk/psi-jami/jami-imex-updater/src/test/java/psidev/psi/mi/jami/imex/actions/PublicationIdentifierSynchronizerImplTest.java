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
import psidev.psi.mi.jami.imex.actions.impl.PublicationIdentifierSynchronizerImpl;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.model.impl.DefaultSource;

/**
 * Unit tester of PublicationIdentifierSynchronizer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/04/12</pre>
 */
public class PublicationIdentifierSynchronizerImplTest {

    private PublicationIdentifierSynchronizer identifierSynchronizerTest;
    private ImexPublication intactPub12345;
    private ImexPublication intactDoi;
    private ImexPublication intactPub12347;
    private ImexPublication intactPubUnassigned;

    @Before
    public void createImexPublications() throws BridgeFailedException {

        identifierSynchronizerTest = new PublicationIdentifierSynchronizerImpl(new MockImexCentralClient());

        Publication pub1 = new Publication();
        Identifier pubmed = new Identifier();
        pubmed.setNs("pmid");
        pubmed.setAc("12345");
        pub1.getIdentifier().add(pubmed);
        pub1.setImexAccession("IM-1");

        intactPub12345 = new ImexPublication(pub1);
        identifierSynchronizerTest.getImexCentralClient().createPublication(intactPub12345);

        Publication pub2 = new Publication();
        Identifier pubmed2 = new Identifier();
        pubmed2.setNs("doi");
        pubmed2.setAc("123/1(a2)");
        pub2.getIdentifier().add(pubmed2);
        pub2.setImexAccession("IM-2");
        intactDoi = new ImexPublication(pub2);
        identifierSynchronizerTest.getImexCentralClient().createPublication(intactDoi);

        Publication pub3 = new Publication();
        Identifier pubmed3 = new Identifier();
        pubmed3.setNs("pmid");
        pubmed3.setAc("12347");
        pub3.getIdentifier().add(pubmed3);
        pub3.setImexAccession("IM-3");
        intactPub12347 = new ImexPublication(pub3);
        identifierSynchronizerTest.getImexCentralClient().createPublication(intactPub12347);

        Publication pub4 = new Publication();
        pub4.setImexAccession("IM-4");
        intactPubUnassigned = new ImexPublication(pub4);
        identifierSynchronizerTest.getImexCentralClient().createPublication(intactPubUnassigned);
    }

    @Test
    public void intact_publication_identifier_synchronized_with_imex_central() throws BridgeFailedException {

        Assert.assertTrue(identifierSynchronizerTest.isPublicationIdentifierInSyncWithImexCentral("12345", "pubmed", intactPub12345));
    }

    @Test
    public void intact_publication_unassigned_not_synchronized_with_imex_central() throws BridgeFailedException {

        Assert.assertFalse(identifierSynchronizerTest.isPublicationIdentifierInSyncWithImexCentral("unassigned604", "pubmed", intactPubUnassigned));
    }

    @Test
    public void intact_publication_identifier_no_identifier_imex_central() throws BridgeFailedException {

        Assert.assertFalse(identifierSynchronizerTest.isPublicationIdentifierInSyncWithImexCentral("12345", "pubmed", intactPubUnassigned));
    }

    @Test
    public void intact_publication_identifier_mismatch_imex_central() throws BridgeFailedException {

        Assert.assertFalse(identifierSynchronizerTest.isPublicationIdentifierInSyncWithImexCentral("12346", "pubmed", intactPub12345));
    }

    @Test
    public void synchronize_intact_publication_identifier_already_synchronized_with_imex_central() throws BridgeFailedException, EnricherException {

        psidev.psi.mi.jami.model.Publication intactPublication = new DefaultPublication("12345");
        intactPublication.setSource(new DefaultSource("intact"));

        identifierSynchronizerTest.synchronizePublicationIdentifier(intactPublication, intactPub12345);
        
        Assert.assertEquals("12345", intactPub12345.getIdentifiers().iterator().next().getId());
    }

    @Test
    public void synchronize_intact_publication_identifier_no_identifier_in_imex_central() throws BridgeFailedException, EnricherException {
        intactPub12345.getIdentifiers().clear();

        psidev.psi.mi.jami.model.Publication intactPublication = new DefaultPublication("12345");
        intactPublication.setSource(new DefaultSource("intact"));

        identifierSynchronizerTest.synchronizePublicationIdentifier(intactPublication, intactPub12345);

        Assert.assertEquals("12345", intactPub12345.getIdentifiers().iterator().next().getId());
    }

    @Test
    public void update_unassigned_identifier() throws BridgeFailedException, EnricherException {
        Assert.assertTrue(intactPubUnassigned.getIdentifiers().isEmpty());

        psidev.psi.mi.jami.model.Publication intactPublication = new DefaultPublication("unassigned604");
        intactPublication.setSource(new DefaultSource("intact"));

        identifierSynchronizerTest.synchronizePublicationIdentifier(intactPublication, intactPubUnassigned);

        Assert.assertEquals("unassigned604", intactPubUnassigned.getIdentifiers().iterator().next().getId());
        intactPubUnassigned.getIdentifiers().clear();
    }

    @Test
    public void synchronized_mismatch_pubmedId_aborted() throws BridgeFailedException {

        psidev.psi.mi.jami.model.Publication intactPublication = new DefaultPublication("12346");
        intactPublication.setSource(new DefaultSource("intact"));

        try {
            identifierSynchronizerTest.synchronizePublicationIdentifier(intactPublication, intactPub12345);
            Assert.assertFalse(true);
        } catch (EnricherException e) {
            Assert.assertEquals("12345", intactPub12345.getIdentifiers().iterator().next().getId());
        }
    }

    @Test
    public void synchronized_mismatch_unassigned_intact_aborted() throws BridgeFailedException {

        psidev.psi.mi.jami.model.Publication intactPublication = new DefaultPublication("unassigned604");
        intactPublication.setSource(new DefaultSource("intact"));

        try {
            identifierSynchronizerTest.synchronizePublicationIdentifier(intactPublication, intactPub12345);
            Assert.assertFalse(true);
        } catch (EnricherException e) {
            Assert.assertEquals("12345", intactPub12345.getIdentifiers().iterator().next().getId());
        }
    }

    @Test
    public void synchronized_mismatch_doi_aborted() throws BridgeFailedException {

        psidev.psi.mi.jami.model.Publication intactPublication = new DefaultPublication();
        intactPublication.setDoi("1234-5(7a)");
        intactPublication.setSource(new DefaultSource("intact"));

        try {
            identifierSynchronizerTest.synchronizePublicationIdentifier(intactPublication, intactDoi);
            Assert.assertFalse(true);
        } catch (EnricherException e) {
            Assert.assertEquals("123/1(a2)", intactDoi.getIdentifiers().iterator().next().getId());
        }
    }

    @Test
    public void synchronized_new_identifier_already_existing_aborted() throws EnricherException {

        psidev.psi.mi.jami.model.Publication intactPublication = new DefaultPublication( "12345");
        intactPublication.setSource(new DefaultSource("intact"));

        try {
            identifierSynchronizerTest.synchronizePublicationIdentifier(intactPublication, intactPubUnassigned);
            Assert.assertFalse(true);
        } catch (BridgeFailedException e) {
            Assert.assertTrue(intactPubUnassigned.getIdentifiers().isEmpty());
        }
    }
}
