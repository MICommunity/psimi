package psidev.psi.mi.jami.enricher.listener.cvterm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.mock.MockCvTermFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.cvterm.MinimumCvTermEnricher;
import psidev.psi.mi.jami.enricher.listener.impl.CvTermEnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.impl.CvTermEnricherLogger;
import psidev.psi.mi.jami.enricher.listener.impl.CvTermEnricherStatisticsWriter;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.AliasUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class CvTermEnricherStatisticsWriterTest {

    protected static final Logger log = LoggerFactory.getLogger(CvTermEnricherLogger.class.getName());

    MinimumCvTermEnricher minimumCvTermEnricher;
    MockCvTermFetcher mockCvTermFetcher;
    CvTermEnricherStatisticsWriter cvTermStatisticsWriter;

    private String SHORT_NAME = "ShortName";
    private String FULL_NAME = "FullName";
    private String MI_ID = "MI:1234";
    private String SYNONYM_NAME = "SynonymName";

    private File successFile , failFile;

    @Before
    public void setup() throws BridgeFailedException, IOException {
        mockCvTermFetcher = new MockCvTermFetcher();
        minimumCvTermEnricher = new MinimumCvTermEnricher(mockCvTermFetcher);

        successFile = new File("success.txt");
        failFile = new File("failed.txt");

        if(successFile.exists()) successFile.delete();
        if(failFile.exists()) failFile.delete();

        assertTrue( ! successFile.exists());
        assertTrue( ! failFile.exists());


        cvTermStatisticsWriter = new CvTermEnricherStatisticsWriter(successFile , failFile);
        CvTermEnricherListenerManager manager = new CvTermEnricherListenerManager();

        manager.addEnricherListener(cvTermStatisticsWriter);
        manager.addEnricherListener(new CvTermEnricherLogger());
        minimumCvTermEnricher.setCvTermEnricherListener(manager);

        CvTerm cvTermFull = new DefaultCvTerm( SHORT_NAME, FULL_NAME, MI_ID);
        cvTermFull.getSynonyms().add(AliasUtils.createAlias(
                "synonym", "MI:1041", SYNONYM_NAME));
        mockCvTermFetcher.addEntry(MI_ID , cvTermFull);
    }

    @After
    public void tearDown() throws IOException {
        cvTermStatisticsWriter.close();

        log.info("******* success file ******");
        BufferedReader successReader = new BufferedReader(new FileReader(successFile));
        String line = successReader.readLine();
        while( line != null){
            log.info(line);
            line = successReader.readLine();
        }
        successReader.close();

        log.info("******* fail file ******");
        BufferedReader failReader = new BufferedReader(new FileReader(failFile));
        line = failReader.readLine();
        while( line != null){
            log.info(line);
            line = failReader.readLine();
        }
        failReader.close();


        if(successFile.exists()) successFile.delete();
        if(failFile.exists()) failFile.delete();
    }


    @Test
    public void test_log_is_written() throws EnricherException, IOException {
        CvTerm term = new DefaultCvTerm(SHORT_NAME,MI_ID);

        minimumCvTermEnricher.enrichCvTerm(term);
        minimumCvTermEnricher.enrichCvTerm(term);
        term.setMIIdentifier("FOO");
        minimumCvTermEnricher.enrichCvTerm(term);

        CvTerm test = new DefaultCvTerm("FOOOO", "BAHHH");
        minimumCvTermEnricher.enrichCvTerm(test);

        cvTermStatisticsWriter.close();

        assertTrue(successFile.exists());
        assertTrue(failFile.exists());

        //assertEquals(2 , count);
    }
}
