package psidev.psi.mi.jami.imex.actions;

import edu.ucla.mbi.imex.central.ws.v20.Identifier;
import edu.ucla.mbi.imex.central.ws.v20.Publication;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.imex.extension.ImexPublication;
import psidev.psi.mi.jami.bridges.imex.mock.MockImexCentralClient;
import psidev.psi.mi.jami.imex.actions.impl.ImexCentralPublicationRegisterImpl;
import psidev.psi.mi.jami.model.impl.DefaultPublication;

/**
 * Unit tester of ImexcentralPublicationregister
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/04/12</pre>
 */
public class ImexCentralPublicationRegisterImplTest {

    private ImexCentralPublicationRegister imexCentralRegisterTest;

    @Before
    public void createImexPublications() throws BridgeFailedException {

        this.imexCentralRegisterTest = new ImexCentralPublicationRegisterImpl(new MockImexCentralClient());

        Publication pubmedPub = new Publication();
        Identifier pubmed = new Identifier();
        pubmed.setNs("pmid");
        pubmed.setAc("12345");
        pubmedPub.getIdentifier().add(pubmed);
        pubmedPub.setImexAccession("N/A");
        imexCentralRegisterTest.getImexCentralClient().createPublication(new ImexPublication(pubmedPub));

        Publication doiPub = new Publication();
        Identifier doi = new Identifier();
        doi.setNs("doi");
        doi.setAc("1/a-2345");
        doiPub.getIdentifier().add(doi);
        doiPub.setImexAccession("N/A");
        imexCentralRegisterTest.getImexCentralClient().createPublication(new ImexPublication(doiPub));

        Publication imexPub = new Publication();
        Identifier imex = new Identifier();
        imex.setNs("imex");
        imex.setAc("IM-1");
        imexPub.getIdentifier().add(imex);
        imexPub.setImexAccession("IM-1");
        imexCentralRegisterTest.getImexCentralClient().createPublication(new ImexPublication(imexPub));
    }

    @Test
    public void getExistingPublicationInImexCentral_pubmed() throws BridgeFailedException {

        psidev.psi.mi.jami.model.Publication pubmedPub = imexCentralRegisterTest.getExistingPublicationInImexCentral("12345", "pubmed");
        Assert.assertNotNull(pubmedPub);
    }

    @Test
    public void getExistingPublicationInImexCentral_doi() throws BridgeFailedException {
        psidev.psi.mi.jami.model.Publication doiPub = imexCentralRegisterTest.getExistingPublicationInImexCentral("1/a-2345", "doi");
        Assert.assertNotNull(doiPub);

    }

    @Test
    public void getExistingPublicationInImexCentral_imex() throws BridgeFailedException {
        psidev.psi.mi.jami.model.Publication imexPub = imexCentralRegisterTest.getExistingPublicationInImexCentral("IM-1", "imex");
        Assert.assertNotNull(imexPub);
    }

    @Test
    public void getExistingPublicationInImexCentral_notExisting() throws BridgeFailedException {
        psidev.psi.mi.jami.model.Publication pub = imexCentralRegisterTest.getExistingPublicationInImexCentral("166667", "pubmed");
        Assert.assertNull(pub);
    }

    @Test
    public void registerPublicationInImexCentral_pubmed() throws BridgeFailedException {
        psidev.psi.mi.jami.model.Publication intactPublication = new DefaultPublication("1369");

        psidev.psi.mi.jami.model.Publication pub = imexCentralRegisterTest.registerPublicationInImexCentral(intactPublication);
        Assert.assertNotNull(pub);
    }

    @Test
    public void registerPublicationInImexCentral_unassigned() throws BridgeFailedException {
        psidev.psi.mi.jami.model.Publication intactPublication = new DefaultPublication("unassigned604");

        psidev.psi.mi.jami.model.Publication pub = imexCentralRegisterTest.registerPublicationInImexCentral(intactPublication);
        Assert.assertNotNull(pub);
    }
}
