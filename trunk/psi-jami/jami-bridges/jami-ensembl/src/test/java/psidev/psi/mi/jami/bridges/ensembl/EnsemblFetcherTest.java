package psidev.psi.mi.jami.bridges.ensembl;


import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/08/13
 */
public class EnsemblFetcherTest {

    @Test
    public void tests() throws Exception {
        EnsemblFetcher fetcher = new EnsemblFetcher();

        fetcher.getGeneByIdentifierOfUnknownType("ENSG00000139618");
        fetcher.getGeneByIdentifierOfUnknownType("ENSG00000172115");
        fetcher.getGeneByIdentifierOfUnknownType("ENSG00000157764");
        fetcher.getGeneByIdentifierOfUnknownType("AT3G52430");

    }


}
