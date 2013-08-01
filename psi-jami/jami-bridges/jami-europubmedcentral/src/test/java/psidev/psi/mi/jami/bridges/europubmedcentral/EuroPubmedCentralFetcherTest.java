package psidev.psi.mi.jami.bridges.europubmedcentral;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Publication;

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
    public void test() throws BridgeFailedException {
        log.info("------c-----");
        fetcher.getPublicationByPubmedID("13054692");
        log.info("----c-------");
        fetcher.getPublicationByPubmedID("10831611");
    }

    @Test
    public void test_publicationC() throws BridgeFailedException{

        Publication publication = fetcher.getPublicationByPubmedID("13054692");
        assertEquals( publication.getPubmedId() , "13054692");
        assertEquals( publication.getDoi() , "10.1038/171737a0");
        assertEquals( publication.getTitle() , "Molecular structure of nucleic acids; a structure for deoxyribose nucleic acid.");
        assertEquals( publication.getJournal() , "Nature");

        //month 4
        //year 1953
        //publication.addAuthor: WATSON JD
        //publication.addAuthor: CRICK FH
        //publication.setSource: null
    }

    @Test
    public void test_publicationB() throws  BridgeFailedException{
        Publication publication = fetcher.getPublicationByPubmedID("23671334");
        assertEquals("23671334" , publication.getPubmedId());
        assertEquals("" , publication.getTitle());
    }

    @Test
    public void test_publicationA() throws BridgeFailedException{

        Publication publication = fetcher.getPublicationByPubmedID("10831611");
        assertEquals( publication.getPubmedId() , "10831611");
        assertEquals( publication.getDoi() , "10.1083/jcb.149.5.1073");
        assertEquals( publication.getTitle() , "Zyxin, a regulator of actin filament assembly, targets the mitotic apparatus by interacting with h-warts/LATS1 tumor suppressor.");
        assertEquals( publication.getJournal() , "The Journal of cell biology");
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
