package psidev.psi.mi.jami.bridges.europubmedcentral;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class EuroPubmedCentralFetcherTest {

    protected static final Logger log = LoggerFactory.getLogger(EuroPubmedCentralFetcherTest.class.getName());

    EuroPubmedCentralFetcher fetcher;

    @Before
    public void setup() throws BridgeFailedException {
        fetcher = new EuroPubmedCentralFetcher();
    }

    @Test
    public void test_publicationD() throws BridgeFailedException{

        Publication publication = fetcher.fetchPublicationByIdentifier("10.1038/171737a0" , Xref.DOI);
        assertEquals( "13054692" , publication.getPubmedId() );
        assertEquals( "10.1038/171737a0" , publication.getDoi());
        assertEquals( "Molecular structure of nucleic acids; a structure for deoxyribose nucleic acid." , publication.getTitle());
        assertEquals( "Nature" , publication.getJournal());
        assertTrue(publication.getAuthors().contains("WATSON JD"));
        assertTrue(publication.getAuthors().contains("CRICK FH"));
        //month 4
        //year 1953
        //publication.addAuthor: WATSON JD
        //publication.addAuthor: CRICK FH
        //publication.setSource: null
    }

    @Test
    public void test_publicationC() throws BridgeFailedException{

        Publication publication = fetcher.fetchPublicationByIdentifier("13054692" , Xref.PUBMED);
        assertEquals( "13054692" , publication.getPubmedId() );
        assertEquals( "10.1038/171737a0" , publication.getDoi());
        assertEquals( "Molecular structure of nucleic acids; a structure for deoxyribose nucleic acid." , publication.getTitle());
        assertEquals( "Nature" , publication.getJournal());
        assertTrue(publication.getAuthors().contains("WATSON JD"));
        assertTrue(publication.getAuthors().contains("CRICK FH"));
        //month 4
        //year 1953
        //publication.addAuthor: WATSON JD
        //publication.addAuthor: CRICK FH
        //publication.setSource: null
    }

    @Test
    public void test_publicationB() throws  BridgeFailedException{
        Publication publication = fetcher.fetchPublicationByIdentifier("23671334" , Xref.PUBMED);
        assertEquals("23671334" , publication.getPubmedId());
        assertEquals("A new reference implementation of the PSICQUIC web service." , publication.getTitle());
    }

    @Test
    public void test_publicationA() throws BridgeFailedException{

        Publication publication = fetcher.fetchPublicationByIdentifier("10831611" , Xref.PUBMED);
        assertEquals( "10831611" , publication.getPubmedId() );
        assertEquals( "10.1083/jcb.149.5.1073" , publication.getDoi() );
        assertEquals( "Zyxin, a regulator of actin filament assembly, targets the mitotic apparatus by interacting with h-warts/LATS1 tumor suppressor." , publication.getTitle());
        assertEquals( "The Journal of cell biology" , publication.getJournal());
        // month 5 - year 2000
        //publication.addAuthor: Hirota T
        //publication.addAuthor: Morisaki T
        //publication.addAuthor: Nishiyama Y
        //publication.addAuthor: Marumoto T
        //publication.addAuthor: Tada K
        //publication.addAuthor: Hara T
        //publication.addAuthor: Masuko N
        //publication.addAuthor: Inagaki M
        //publication.addAuthor: Hatakeyama K
        //publication.addAuthor: Saya H
        //publication.setSource: Department of Tumor Genetics and Biology, Kumamoto University School of Medicine, 2-2-1 Honjo, Kumamoto 860-0811, Japan.
        ///publication.addXref: INTACT: EBI-444276
        //publication.addXref: INTACT: EBI-444205
        //publication.addXref: INTACT: EBI-444263
        //publication.addXref: INTACT: EBI-444239
        //publication.addXref: INTACT: EBI-444292
        //publication.addXref: INTERPRO: IPR001781
        //publication.addXref: OMIM: 602002
        //publication.addXref: UNIPROT: O95835
    }
}
