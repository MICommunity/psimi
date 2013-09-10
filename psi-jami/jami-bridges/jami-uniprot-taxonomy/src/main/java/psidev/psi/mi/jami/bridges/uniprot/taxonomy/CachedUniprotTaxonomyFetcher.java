package psidev.psi.mi.jami.bridges.uniprot.taxonomy;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.AbstractCachedFetcher;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.model.Organism;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class CachedUniprotTaxonomyFetcher
        extends AbstractCachedFetcher
        implements OrganismFetcher{

    public static final String CACHE_NAME = "uniprot-taxonomy-service-cache";
    private OrganismFetcher organismFetcher;

    public CachedUniprotTaxonomyFetcher() {
        super(CACHE_NAME);
        this.organismFetcher = new UniprotTaxonomyFetcher();
    }


    public Organism fetchOrganismByTaxID(int taxID) throws BridgeFailedException {
        final String key = "GET_BY_TAXID_"+taxID;
        Object data = getFromCache( key );
        if( data == null) {
            data = organismFetcher.fetchOrganismByTaxID(taxID);
            storeInCache(key , data);
        }
        return (Organism )data;
    }

    public Collection<Organism> fetchOrganismsByTaxIDs(Collection<Integer> taxIDs) throws BridgeFailedException {
        if (taxIDs != null){
            List<Integer> ids = new ArrayList<Integer>(taxIDs);
            Collections.sort(ids);
            String key = "GET_ORGANISMS_BY_TAXIDS";
            for (Integer id : ids){
                key= key+"_"+id;
            }
            Object object = getFromCache(key);
            if (object != null){
                return (Collection<Organism>)object;
            }
            else{
                Collection<Organism> entity = organismFetcher.fetchOrganismsByTaxIDs(taxIDs);
                storeInCache(key, entity);
                return entity;
            }
        }
        return organismFetcher.fetchOrganismsByTaxIDs(taxIDs);
    }
}
