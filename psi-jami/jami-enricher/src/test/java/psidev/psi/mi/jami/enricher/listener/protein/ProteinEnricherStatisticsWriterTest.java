package psidev.psi.mi.jami.enricher.listener.protein;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.protein.MockProteinFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.protein.MinimumProteinEnricher;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.model.impl.DefaultProtein;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public class ProteinEnricherStatisticsWriterTest {



    protected static final Logger log = LoggerFactory.getLogger(ProteinEnricherStatisticsWriterTest.class.getName());


    private File successFile , failFile;

    private MinimumProteinEnricher proteinEnricher;
    private MockProteinFetcher fetcher;
    ProteinEnricherStatisticsWriter logWriter;

    private static final String TEST_SHORTNAME = "test shortName";
    private static final String TEST_FULLNAME = "test fullName";
    private static final String TEST_AC_FULL_PROT = "P12345";
    private static final String TEST_AC_HALF_PROT = "P11111";
    private static final String TEST_SEQUENCE = "GATTACA";
    private static final int TEST_ORGANISM_ID = 1234;
    private static final String TEST_ORGANISM_COMMON = "Common";
    private static final String TEST_ORGANISM_SCIENTIFIC = "Scientific";

    @Before
    public void initialiseFetcherAndEnricher() throws IOException {
        this.fetcher = new MockProteinFetcher();
        this.proteinEnricher = new MinimumProteinEnricher(fetcher);


        successFile = new File("success.txt");
        failFile = new File("failed.txt");

        if(successFile.exists()) successFile.delete();
        if(failFile.exists()) failFile.delete();

        assertTrue( ! successFile.exists());
        assertTrue( ! failFile.exists());

        logWriter = new ProteinEnricherStatisticsWriter(successFile , failFile);
        ProteinEnricherListenerManager manager = new ProteinEnricherListenerManager();

        manager.addEnricherListener(logWriter);
        manager.addEnricherListener(new ProteinEnricherLogger());
        proteinEnricher.setProteinEnricherListener(manager);

        Protein fullProtein = new DefaultProtein(TEST_SHORTNAME, TEST_FULLNAME );
        fullProtein.setUniprotkb(TEST_AC_FULL_PROT);
        fullProtein.setSequence(TEST_SEQUENCE);
        fullProtein.setOrganism(new DefaultOrganism(TEST_ORGANISM_ID, TEST_ORGANISM_COMMON, TEST_ORGANISM_SCIENTIFIC));
        fetcher.addEntry(TEST_AC_FULL_PROT, fullProtein);

        Protein halfProtein = new DefaultProtein(TEST_SHORTNAME);
        halfProtein.setUniprotkb(TEST_AC_HALF_PROT);
        halfProtein.setOrganism(new DefaultOrganism(-3));
        fetcher.addEntry(TEST_AC_HALF_PROT, halfProtein);
    }


    @Test
    public void test_log_is_written() throws EnricherException, IOException {

        Protein term = new DefaultProtein(TEST_SHORTNAME);
        term.setUniprotkb(TEST_AC_FULL_PROT);

        proteinEnricher.enrichProtein(term);
        proteinEnricher.enrichProtein(term);
        term.setUniprotkb("FOO");
        proteinEnricher.enrichProtein(term);
        Protein test = new DefaultProtein("FOOOO", "BAHHH");
        proteinEnricher.enrichProtein(test);

        logWriter.close();

        assertTrue(successFile.exists());
        assertTrue(failFile.exists());
    }



}
