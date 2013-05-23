package psidev.psi.mi.jami.enricher.protein;

import junit.framework.Assert;
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

    @Before
    public void initialiseFetcherAndEnricher() throws EnrichmentException {
        this.fetcher = new MockProteinFetcher();
        this.minimumProteinEnricher = new MinimumProteinEnricher();
        this.minimumProteinEnricher.setFetcher(this.fetcher);

        fetcher.addNewProtein("P12345", new DefaultProtein("test", "full name"));
    }

    @Test
    public void test_set_fullname_if_null() throws EnrichmentException {

        Protein protein_without_fullName = new DefaultProtein("test2");
        protein_without_fullName.setUniprotkb("P12345");

        this.minimumProteinEnricher.enrichProtein(protein_without_fullName);

        Assert.assertNotNull(protein_without_fullName.getFullName());
        Assert.assertEquals("full name", protein_without_fullName.getFullName());
        Assert.assertEquals("test2", protein_without_fullName.getShortName());
    }
}
