package psidev.psi.mi.jami.enricher.impl.cvterm.listener;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.cvterm.MockCvTermFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermEnricherMinimum;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.AliasUtils;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: gabe
 * Date: 18/07/13
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 */
public class CvTermEnricherLogWriterBasicTest {

    CvTermEnricherMinimum cvTermEnricherMinimum;
    MockCvTermFetcher mockCvTermFetcher = new MockCvTermFetcher();
    CvTermEnricherLogWriterBasic logWriter;

    private String SHORT_NAME = "ShortName";
    private String FULL_NAME = "FullName";
    private String MI_ID = "MI:1234";
    private String SYNONYM_NAME = "SynonymName";

    @Before
    public void setup() throws BridgeFailedException, IOException {
        cvTermEnricherMinimum = new CvTermEnricherMinimum();
        cvTermEnricherMinimum.setCvTermFetcher(mockCvTermFetcher);

        logWriter = new CvTermEnricherLogWriterBasic(new File("success.txt"),new File("fail.txt"));
        CvTermEnricherListenerManager manager = new CvTermEnricherListenerManager();

        manager.addEnricherListener(logWriter);
        manager.addEnricherListener(new CvTermEnricherLogger());
        cvTermEnricherMinimum.setCvTermEnricherListener(manager);

        CvTerm cvTermFull = new DefaultCvTerm( SHORT_NAME, FULL_NAME, MI_ID);
        cvTermFull.getSynonyms().add(AliasUtils.createAlias(
                "synonym", "MI:1041", SYNONYM_NAME));
        mockCvTermFetcher.addCvTerm(MI_ID , cvTermFull);

    }


    @Test
    public void test_log_is_written() throws EnricherException, IOException {
        CvTerm term = new DefaultCvTerm(SHORT_NAME,MI_ID);



        cvTermEnricherMinimum.enrichCvTerm(term);
        cvTermEnricherMinimum.enrichCvTerm(term);
        term.setMIIdentifier("FOO");
        cvTermEnricherMinimum.enrichCvTerm(term);

        CvTerm test = new DefaultCvTerm("FOOOO", "BAHHH");

        cvTermEnricherMinimum.enrichCvTerm(test);

        logWriter.close();
    }

}
