package psidev.psi.mi.jami.bridges.uniprot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.bridges.fetcher.GeneIdentifierSource;
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


    public Collection<Gene> getGenesByIdentifier(String identifier, GeneIdentifierSource source , int taxID)
            throws BridgeFailedException {
        return techniqueA(identifier, source ,taxID );
    }


    public Collection<Gene> techniqueA(String identifier, GeneIdentifierSource source , int taxID)
        throws BridgeFailedException {

        if(identifier == null || identifier.isEmpty())
            throw new IllegalArgumentException("Could not perform search on null identifier.");

        Query query = null;
        switch(source){
            case ENSEMBL:
                query = UniProtQueryBuilder.buildDatabaseCrossReferenceQuery(DatabaseType.ENSEMBL, identifier);
                break;
            case REFSEQ:
                query = UniProtQueryBuilder.buildDatabaseCrossReferenceQuery(DatabaseType.REFSEQ , identifier);
                break;
        }

        if(query == null)
            return Collections.emptyList();
        log.info("Query is: "+query.toString());

        EntryIterator<UniProtEntry> entryIterator;

        /*if(taxID > 0 )
            entryIterator = uniProtQueryService.getEntryIterator(
                    uniProtQueryService.getEntryIterator(UniProtQueryBuilder.buildOrganismQuery("human")));
        else  */
            entryIterator = uniProtQueryService.getEntryIterator(query);
        Collection<Gene> genes = new ArrayList<Gene>();
        while(entryIterator.hasNext()){
            Gene gene = UniprotTranslationUtil.getGeneFromEntry(entryIterator.next());
            switch (source){
                case ENSEMBL:   gene.setEnsembl(identifier); break;
                case REFSEQ:    gene.setRefseq(identifier); break;
            }
            genes.add(gene);
        }
        return genes;
    }

    public Collection<Gene> techniqueB(String identifier, GeneIdentifierSource source , int taxID)
            throws BridgeFailedException {

        if(identifier == null || identifier.isEmpty())
            throw new IllegalArgumentException("Could not perform search on null identifier.");

        Query query = null;
        List<String> terms = new ArrayList<String>();
        switch(source){
            case ENSEMBL:
                //Query query = UniProtQueryBuilder.buildDatabaseCrossReferenceQuery(DatabaseType.ENSEMBL, identifier);
                terms.add("xref.ensembl:"+identifier);
                break;
            case REFSEQ:
                //Query query = UniProtQueryBuilder.buildDatabaseCrossReferenceQuery(DatabaseType.REFSEQ , identifier);
                terms.add("xref.refseq:"+identifier);
                break;
        }

        if(terms.isEmpty())
            return Collections.emptyList();

        if(taxID > 0 )
            terms.add("organism.ncbi:"+taxID);

        query = new Query(terms);


        log.info("Query is: "+query.toString());
        log.info("Terms is: "+terms.toString());

        EntryIterator<UniProtEntry> entryIterator = uniProtQueryService.getEntryIterator(query);
        Collection<Gene> genes = new ArrayList<Gene>();
        while(entryIterator.hasNext()){
            Gene gene = UniprotTranslationUtil.getGeneFromEntry(entryIterator.next());
            switch (source){
                case ENSEMBL:   gene.setEnsembl(identifier); break;
                case REFSEQ:    gene.setRefseq(identifier); break;
            }
            genes.add(gene);
        }
        return genes;
    }


    public Collection<Gene> getGenesByIdentifier(String identifier, GeneIdentifierSource source)
            throws BridgeFailedException {
        return getGenesByIdentifier(identifier, source , -3);
    }
}
