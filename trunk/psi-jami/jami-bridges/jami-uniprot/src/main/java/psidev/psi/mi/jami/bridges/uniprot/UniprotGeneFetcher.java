package psidev.psi.mi.jami.bridges.uniprot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.bridges.uniprot.util.UniprotTranslationUtil;
import psidev.psi.mi.jami.model.Gene;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseType;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.uuw.services.remoting.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/08/13
 */
public class UniprotGeneFetcher implements GeneFetcher {


    private UniProtQueryService uniProtQueryService;
    private final Logger log = LoggerFactory.getLogger(UniprotGeneFetcher.class.getName());

    public UniprotGeneFetcher() {
        uniProtQueryService = UniProtJAPI.factory.getUniProtQueryService();
    }

    /**
     * Finds a gene in uniprot using with the ensembl ID, the refseq ID or the ensemblGenomes ID.
     * The organism is optional, a taxid of 0 or less
     * @param identifier    An identifier for the gene.
     * @param taxID         The taxID of the organism.
     * @return              The matching gene records, or an empty collection if no record was found.
     * @throws BridgeFailedException
     */
    public Collection<Gene> fetchGenesByIdentifier(String identifier, int taxID)
            throws BridgeFailedException {

        if(identifier == null || identifier.isEmpty())
            throw new IllegalArgumentException("Could not perform search on null identifier.");

        Query query = null;

        String queryTerms;
        queryTerms = "("+
                "xref.ensembl:"+identifier+" OR "+
                "xref.refseq:"+identifier+" OR "+
                "xref.ensemblbacteria:"+identifier+" OR "+
                "xref.ensemblfungi:"+identifier+" OR "+
                "xref.ensemblmetazoa:"+identifier+" OR "+
                "xref.ensemblprotists:"+identifier+" OR "+
                "xref.ensemblplants:"+identifier+")";
        if(taxID > 0)
            queryTerms = queryTerms +" AND "+"organism.ncbi:"+taxID;

        query = UniProtQueryBuilder.buildQuery(queryTerms) ;

        EntryIterator<UniProtEntry> entryIterator = uniProtQueryService.getEntryIterator(query);
        Collection<Gene> genes = new ArrayList<Gene>();
        if(entryIterator.hasNext()){
            Gene gene = UniprotTranslationUtil.getGeneFromEntry(entryIterator.next() , identifier);
            genes.add(gene);
        }
        return genes;
    }

    public Collection<Gene> fetchGenesByIdentifier(String identifier)
            throws BridgeFailedException {
        return fetchGenesByIdentifier(identifier, -3);
    }
}
