package psidev.psi.mi.jami.imex;

import edu.ucla.mbi.imex.central.ws.v20.Identifier;
import edu.ucla.mbi.imex.central.ws.v20.Publication;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.imex.ImexCentralClient;
import psidev.psi.mi.jami.bridges.imex.extension.ImexPublication;
import psidev.psi.mi.jami.bridges.imex.mock.MockImexCentralClient;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.utils.AnnotationUtils;

/**
 * Unit tester for ImexPublicationUpdater
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/11/14</pre>
 */

public class ImexPublicationUpdaterTest {

    private ImexPublicationUpdater updater;


    @Before
    public void initUpdater() throws BridgeFailedException {
        ImexCentralClient client = new MockImexCentralClient();
        this.updater = new ImexPublicationUpdater(client);

        Publication pub = new Publication();
        Identifier pubmed = new Identifier();
        pubmed.setNs("pmid");
        pubmed.setAc("12345");
        pub.getIdentifier().add(pubmed);
        pub.setImexAccession("IM-1");

        client.createPublication(new ImexPublication(pub));
    }

    @Test
    public void update_publication_successful() throws EnricherException {

        psidev.psi.mi.jami.model.Publication pub = new DefaultPublication("12345");
        pub.assignImexId("IM-1");

        updater.enrich(pub);

        Assert.assertEquals(CurationDepth.IMEx, pub.getCurationDepth());
        Assert.assertEquals(2, pub.getAnnotations().size());

        Assert.assertNotNull(AnnotationUtils.collectFirstAnnotationWithTopicAndValue(pub.getAnnotations(),
                ImexPublicationUpdater.FULL_COVERAGE_MI, ImexPublicationUpdater.FULL_COVERAGE, ImexPublicationUpdater.FULL_COVERAGE_TEXT));
        Assert.assertNotNull(AnnotationUtils.collectFirstAnnotationWithTopic(pub.getAnnotations(),
                ImexPublicationUpdater.IMEX_CURATION_MI, ImexPublicationUpdater.IMEX_CURATION));
    }
}
