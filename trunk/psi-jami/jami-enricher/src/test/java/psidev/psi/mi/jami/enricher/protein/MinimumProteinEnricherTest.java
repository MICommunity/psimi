package psidev.psi.mi.jami.enricher.protein;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.mock.protein.MockProteinFetcher;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultProtein;

/**
 * Unit tester for MinimumProteinEnricher
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class MinimumProteinEnricherTest {

    private MinimumProteinEnricher minimumProteinEnricher;
    private MockProteinFetcher fetcher;

    private static final String TEST_SHORTNAME = "test1 shortName";
    private static final String TEST_FULLNAME = "test1 fullName";
    private static final String TEST_AC = "P12345";
    private static final String TEST_SEQUENCE = "GATTACA";

    @Before
    public void initialiseFetcherAndEnricher() throws EnrichmentException {
        this.fetcher = new MockProteinFetcher();
        this.minimumProteinEnricher = new MinimumProteinEnricher();
        this.minimumProteinEnricher.setFetcher(this.fetcher);

        Protein testProtein = new DefaultProtein(TEST_SHORTNAME, TEST_FULLNAME );
        testProtein.setUniprotkb(TEST_AC);
        testProtein.setSequence(TEST_SEQUENCE);
        fetcher.addNewProtein(TEST_AC, testProtein);
    }

    @Test
    public void test_set_fullname_if_null() throws EnrichmentException {

        Protein protein_without_fullName = new DefaultProtein("test2 shortName");
        protein_without_fullName.setUniprotkb(TEST_AC);

        this.minimumProteinEnricher.enrichProtein(protein_without_fullName);

        assertNotNull(protein_without_fullName.getFullName());
        assertEquals(TEST_FULLNAME, protein_without_fullName.getFullName());
    }

    //TEST CAN NOT BE IMPLEMENTED WITH CURRENT MOCK.
    //@Test
    //public void test_set_primaryAC_if_null() throws EnrichmentException {
    //
    //    Protein protein_without_AC = new DefaultProtein("test2 shortName");
    //
    //    this.minimumProteinEnricher.enrichProtein(protein_without_AC);
    //
    //    Assert.assertNotNull(protein_without_fullName.getFullName());
    //    Assert.assertEquals("test1 fullName", protein_without_fullName.getFullName());
    //    Assert.assertEquals("test2 shortName", protein_without_fullName.getShortName());
    //}

    @Test
    public void test_set_sequence_if_null() throws EnrichmentException {

        Protein protein_without_sequence = new DefaultProtein("test2 shortName");
        protein_without_sequence.setUniprotkb(TEST_AC);

        this.minimumProteinEnricher.enrichProtein(protein_without_sequence);

        assertNotNull(protein_without_sequence.getSequence());
        assertEquals(TEST_SEQUENCE, protein_without_sequence.getSequence());
    }

    @Test
    public void test_no_overwrite_on_not_null_fields() throws EnrichmentException {
        Protein protein_with_all_fields = new DefaultProtein("test2 shortName", "test2 fullName");
        protein_with_all_fields.setUniprotkb(TEST_AC);
        protein_with_all_fields.setSequence("TAGTAG");

        assertNotNull(protein_with_all_fields.getShortName());
        assertNotNull(protein_with_all_fields.getFullName());
        assertNotNull(protein_with_all_fields.getUniprotkb());
        assertNotNull(protein_with_all_fields.getSequence());

        this.minimumProteinEnricher.enrichProtein(protein_with_all_fields);

        assertEquals( protein_with_all_fields.getShortName(), "test2 shortName");
        assertEquals( protein_with_all_fields.getFullName(), "test2 fullName");
        assertEquals( protein_with_all_fields.getSequence(), "TAGTAG");
    }
}
