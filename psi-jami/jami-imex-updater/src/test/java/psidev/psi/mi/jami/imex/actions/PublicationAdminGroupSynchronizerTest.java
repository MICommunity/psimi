package psidev.psi.mi.jami.imex.actions;

import edu.ucla.mbi.imex.central.ws.v20.Identifier;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.imex.extension.ImexPublication;
import psidev.psi.mi.jami.bridges.imex.mock.MockImexCentralClient;
import psidev.psi.mi.jami.imex.actions.impl.PublicationAdminGroupSynchronizerImpl;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.model.impl.DefaultSource;

import java.util.Iterator;

/**
 * Unit tester of PublicationAdminGroupSynchronizer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/04/12</pre>
 */
public class PublicationAdminGroupSynchronizerTest {

    private PublicationAdminGroupSynchronizer imexAdminGroupSynchronizerTest;
    private ImexPublication intactPub;
    private ImexPublication noIntactPub;
    private ImexPublication intactPub2;
    private ImexPublication noIntactPub2;

    @Before
    public void createImexPublications() throws BridgeFailedException {

        imexAdminGroupSynchronizerTest = new PublicationAdminGroupSynchronizerImpl(new MockImexCentralClient());

        edu.ucla.mbi.imex.central.ws.v20.Publication pub = new edu.ucla.mbi.imex.central.ws.v20.Publication();
        Identifier pubmed = new Identifier();
        pubmed.setNs("pmid");
        pubmed.setAc("12345");
        pub.getIdentifier().add(pubmed);
        pub.setAdminGroupList(new edu.ucla.mbi.imex.central.ws.v20.Publication.AdminGroupList());
        pub.getAdminGroupList().getGroup().add("INTACT");
        pub.getAdminGroupList().getGroup().add("INTACT Curators");

        intactPub = new ImexPublication(pub);
        imexAdminGroupSynchronizerTest.getImexCentralClient().createPublication(intactPub);

        edu.ucla.mbi.imex.central.ws.v20.Publication pub2 = new edu.ucla.mbi.imex.central.ws.v20.Publication();
        Identifier pubmed2 = new Identifier();
        pubmed2.setNs("pmid");
        pubmed2.setAc("12346");
        pub2.getIdentifier().add(pubmed2);
        noIntactPub = new ImexPublication(pub2);
        imexAdminGroupSynchronizerTest.getImexCentralClient().createPublication(noIntactPub);

        edu.ucla.mbi.imex.central.ws.v20.Publication pub3 = new edu.ucla.mbi.imex.central.ws.v20.Publication();
        Identifier pubmed3 = new Identifier();
        pubmed3.setNs("pmid");
        pubmed3.setAc("12347");
        pub3.getIdentifier().add(pubmed3);
        pub3.setAdminGroupList(new edu.ucla.mbi.imex.central.ws.v20.Publication.AdminGroupList());
        pub3.getAdminGroupList().getGroup().add("INTACT");
        intactPub2 = new ImexPublication(pub3);
        imexAdminGroupSynchronizerTest.getImexCentralClient().createPublication(intactPub2);

        edu.ucla.mbi.imex.central.ws.v20.Publication pub4 = new edu.ucla.mbi.imex.central.ws.v20.Publication();
        Identifier pubmed4 = new Identifier();
        pubmed4.setNs("pmid");
        pubmed4.setAc("12348");
        pub4.getIdentifier().add(pubmed4);
        noIntactPub2 = new ImexPublication(pub4);

        imexAdminGroupSynchronizerTest.getImexCentralClient().createPublication(noIntactPub2);

    }

    @Test
    public void synchronize_intact_admin() throws BridgeFailedException {

        Publication intactPublication = new DefaultPublication("12346");
        intactPublication.setSource(new DefaultSource("intact"));
        
        imexAdminGroupSynchronizerTest.synchronizePublicationAdminGroup(intactPublication, noIntactPub);
        
        Assert.assertEquals(1, noIntactPub.getSources().size());
        Assert.assertEquals("INTACT", noIntactPub.getSources().iterator().next().getShortName().toUpperCase());
        noIntactPub.getSources().clear();
    }

    @Test
    public void synchronize_intact_admin_alreadyPresent() throws BridgeFailedException {

        Publication intactPublication = new DefaultPublication("12345");
        intactPublication.setSource(new DefaultSource("intact"));

        imexAdminGroupSynchronizerTest.synchronizePublicationAdminGroup(intactPublication, intactPub);

        Assert.assertEquals(2, intactPub.getSources().size());
        Assert.assertEquals("INTACT", intactPub.getSources().iterator().next().getShortName().toUpperCase());
    }

    @Test
    public void synchronize_matrixDb_admin_alreadyPresent() throws BridgeFailedException {

        Publication intactPublication = new DefaultPublication("12347");
        intactPublication.setSource(new DefaultSource("matrixdb"));

        imexAdminGroupSynchronizerTest.synchronizePublicationAdminGroup(intactPublication, intactPub2);

        Assert.assertEquals(2, intactPub2.getSources().size());
        Iterator<Source> group = intactPub2.getSources().iterator();
        
        Assert.assertEquals("INTACT", group.next().getShortName().toUpperCase());
        Assert.assertEquals("MATRIXDB", group.next().getShortName().toUpperCase());
        group.remove();
    }

    @Test(expected = BridgeFailedException.class)
    public void synchronize_unknown_admin() throws BridgeFailedException {

        Publication intactPublication = new DefaultPublication("12348");
        intactPublication.setSource(new DefaultSource("i2d"));

        imexAdminGroupSynchronizerTest.synchronizePublicationAdminGroup(intactPublication, noIntactPub2);
        Assert.assertEquals(1, noIntactPub2.getSources().size());
        Iterator<Source> group = noIntactPub2.getSources().iterator();

        Assert.assertEquals("INTACT", group.next().getShortName().toUpperCase());
        noIntactPub2.getSources().clear();
    }
}
