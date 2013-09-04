package psidev.psi.mi.jami.bridges.uniprot;


import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.bridges.uniprot.util.UniprotTranslationUtil;
import psidev.psi.mi.jami.model.Gene;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseType;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.uuw.services.remoting.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/08/13
 */
public class UniprotGeneFetcher implements GeneFetcher {


    private UniProtQueryService uniProtQueryService;
    //private final Logger log = LoggerFactory.getLogger(UniprotGeneFetcher.class.getName());

    public UniprotGeneFetcher() {
        uniProtQueryService = UniProtJAPI.factory.getUniProtQueryService();
    }

    // == GENE FETCHER ========================================

    public Collection<Gene> getGenesByEnsemblIdentifier(String identifier){
        if(identifier == null || identifier.isEmpty())
            throw new IllegalArgumentException("Could not perform search on null identifier.");

        Query query = UniProtQueryBuilder.buildDatabaseCrossReferenceQuery(DatabaseType.ENSEMBL , identifier);
        EntryIterator<UniProtEntry> entryIterator = uniProtQueryService.getEntryIterator(query);
        Collection<Gene> genes = new ArrayList<Gene>();
        if(entryIterator.hasNext()){
            Gene gene = UniprotTranslationUtil.getGeneFromEntry(entryIterator.next());
            gene.setEnsembl(identifier);
            genes.add(gene);
        }
        return genes;
    }


    public Collection<Gene> getGenesByEnsemblGenomesIdentifier(String identifier){
        /* if(identifier == null || identifier.isEmpty())
            throw new IllegalArgumentException("Could not perform search on null identifier.");

        Query query = UniProtQueryBuilder.buildDatabaseCrossReferenceQuery(DatabaseType. , identifier);
        EntryIterator<UniProtEntry> entryIterator = uniProtQueryService.getEntryIterator(query);
        Collection<Gene> genes = new ArrayList<Gene>();
        if(entryIterator.hasNext()){
            Gene gene = UniprotTranslationUtil.getGeneFromEntry(entryIterator.next());
            gene.setEnsemblGenome(identifier);
            genes.add(gene);
        }
        return genes;  */
        return null;
    }
}
