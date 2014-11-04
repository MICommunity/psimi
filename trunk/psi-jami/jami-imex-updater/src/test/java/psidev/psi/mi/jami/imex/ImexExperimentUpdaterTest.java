package psidev.psi.mi.jami.imex;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.imex.mock.MockImexCentralClient;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.imex.actions.impl.ImexAssignerImpl;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for ImexPublicationUpdater
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/11/14</pre>
 */

public class ImexExperimentUpdaterTest {

    private ImexExperimentUpdater updater;


    @Before
    public void initUpdater() throws BridgeFailedException {
        this.updater = new ImexExperimentUpdater();
        this.updater.setImexAssigner(new ImexAssignerImpl(new MockImexCentralClient()));
    }

    @Test
    public void update_publication_successful() throws EnricherException {

        psidev.psi.mi.jami.model.Publication pub = new DefaultPublication("12345");
        pub.assignImexId("IM-1");
        Experiment exp = new DefaultExperiment(pub);
        pub.getExperiments().add(exp);

        updater.enrich(exp);

        Assert.assertEquals(1, exp.getXrefs().size());

        Assert.assertNotNull(XrefUtils.collectAllXrefsHavingDatabaseQualifierAndId(exp.getXrefs(),
                Xref.IMEX_MI, Xref.IMEX, "IM-1", Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY));
    }
}
