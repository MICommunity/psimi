package psidev.psi.mi.jami.bridges;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.uniprot.UniprotFetcher;
import psidev.psi.mi.jami.model.Protein;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 29/05/13
 * Time: 16:16
 */
public class UniprotFetcherTest {

    private UniprotFetcher fetcher;

    @Before
    public void initialiseFetcher() throws FetcherException {
        fetcher = new UniprotFetcher();
    }

    @Test
    public void test_proteins_returned_by_isoform_identifier() throws FetcherException {
        String[] identifiers = {"Q6ZRI6-3", "P13055-2"};

        for(String identifier : identifiers){
            assertTrue(fetcher.UNIPROT_MASTER_REGEX.matcher(identifier).find());
            assertTrue(fetcher.UNIPROT_ISOFORM_REGEX.matcher(identifier).find());

            Collection<Protein> proteins = fetcher.getProteinsByID(identifier);
            for(Protein protein : proteins){
                assertEquals(identifier, protein.getShortName());
                assertNotNull(protein.getOrganism());
                assertNotNull(protein.getSequence());
                assertTrue(protein.getXrefs().size() == 1);
                assertNull(protein.getRogid());
            }
        }
    }

    @Test
    public void test_protein_returned_by_feature_chain_search() throws FetcherException {
        String[] identifiers = {
                "PRO_0000030311",
                "P19838-PRO_0000030311",
                "PRO_0000021413",
                "PRO_0000021416",
                "PRO_0000021449"};

        for(String identifier : identifiers){
            assertTrue(fetcher.UNIPROT_PRO_REGEX.matcher(identifier).find());
            fetcher.getProteinsByID(identifier);
        }
    }

    @Test
    public void test_sequence_is_correct_in_protein_returned_by_feature_chain_search() throws FetcherException{


        Collection<Protein> proteins;

        //Sequence and length were independently verified at:
        //http://www.uniprot.org/uniprot/P15515
        proteins = fetcher.getProteinsByID("PRO_0000021416");//Fringe case - the end is at the maximum length
        assertTrue(proteins.size() == 1);

        for(Protein protein : proteins){
            assertEquals("DSHEKRHHGYRRKFHEKHHSHREFPFYGDYGSNYLYDN",
                    protein.getSequence());
            assertEquals(38, protein.getSequence().length());
        }

        //Sequence and length were independently verified at:
        //http://www.uniprot.org/uniprot/Q9TQY7
        proteins = fetcher.getProteinsByID("PRO_0000015868"); //Fringe case - the beginning is the first position
        assertTrue(proteins.size() == 1);

        for(Protein protein : proteins){
            assertEquals("FPNQHLCGSHLVEALYLVCGEKGFYYIPRM",
                    protein.getSequence());
            assertEquals(30, protein.getSequence().length());
        }
    }
}
