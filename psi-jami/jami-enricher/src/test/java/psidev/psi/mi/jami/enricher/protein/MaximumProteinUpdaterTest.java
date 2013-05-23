package psidev.psi.mi.jami.enricher.protein;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.enricher.event.EnricherEvent;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.mock.protein.MockProteinFetcher;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultProtein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 23/05/13
 * Time: 14:39
 */
public class MaximumProteinUpdaterTest {
    private MaximumProteinUpdater maximumProteinUpdater;
    private MockProteinFetcher fetcher;

    private static final String TEST_SHORTNAME = "test1 shortName";
    private static final String TEST_FULLNAME = "test1 fullName";
    private static final String TEST_AC = "P12345";
    private static final String TEST_SEQUENCE = "GATTACA";

    @Before
    public void initialiseFetcherAndEnricher(){
        this.fetcher = new MockProteinFetcher();
        this.maximumProteinUpdater = new MaximumProteinUpdater(fetcher);

        Protein testProtein = new DefaultProtein(TEST_SHORTNAME, TEST_FULLNAME );
        testProtein.setUniprotkb(TEST_AC);
        testProtein.setSequence(TEST_SEQUENCE);
        fetcher.addNewProtein(TEST_AC, testProtein);
    }

    @Test
    public void test_full_overwrite() throws EnrichmentException {
        Protein protein_with_all_fields = new DefaultProtein("test2 shortName", "test2 fullName");
        protein_with_all_fields.setUniprotkb(TEST_AC);
        protein_with_all_fields.setSequence("TAGTAG");

        assertNotNull(protein_with_all_fields.getShortName());
        assertNotNull(protein_with_all_fields.getFullName());
        assertNotNull(protein_with_all_fields.getUniprotkb());
        assertNotNull(protein_with_all_fields.getSequence());

        this.maximumProteinUpdater.enrichProtein(protein_with_all_fields);

        assertEquals( protein_with_all_fields.getShortName(), TEST_SHORTNAME);
        assertEquals( protein_with_all_fields.getFullName(), TEST_FULLNAME);
        assertEquals( protein_with_all_fields.getSequence(), TEST_SEQUENCE);
    }

    private EnricherEvent event;

    @Test
    public void test_enricher_event_is_fired() throws EnrichmentException {
        Protein protein_to_enrich = new DefaultProtein("test2 shortName", "test2 fullName");
        protein_to_enrich.setUniprotkb(TEST_AC);

        event = null;
        this.maximumProteinUpdater.addEnricherListener(new EnricherListener() {
            public void onEnricherEvent(EnricherEvent e) {
                event = e;
            }
        });

        maximumProteinUpdater.enrichProtein(protein_to_enrich);

        assertNotNull(event);
    }
}
