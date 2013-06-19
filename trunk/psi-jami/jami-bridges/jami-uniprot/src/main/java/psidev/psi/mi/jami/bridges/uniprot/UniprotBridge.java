package psidev.psi.mi.jami.bridges.uniprot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.uniprot.uniprotutil.UniprotToJAMI;
import psidev.psi.mi.jami.model.Protein;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.AlternativeProductsComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.AlternativeProductsIsoform;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.CommentType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformId;
import uk.ac.ebi.kraken.interfaces.uniprot.features.*;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI;
import uk.ac.ebi.kraken.uuw.services.remoting.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 */
public class UniprotBridge {

    private static final Logger log = LoggerFactory.getLogger(UniprotBridge.class.getName());
    private UniprotToJAMI uniprotToJAMI = new UniprotToJAMI();
    UniProtQueryService uniProtQueryService = UniProtJAPI.factory.getUniProtQueryService();

    /**
     *
     * @param identifier
     * @return
     * @throws BridgeFailedException
     */
    public Collection<Protein> fetchMastersByIdentifier(String identifier)
            throws BadResultException, BadSearchTermException, BridgeFailedException {

        if(identifier == null) throw new BadSearchTermException(
                "Tried to search for protein on a null identifier.");

        Collection<Protein> proteins = new ArrayList<Protein>();

        try{
            Query query = UniProtQueryBuilder.buildExactMatchIdentifierQuery(identifier);
            EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);

            while(entries.hasNext()){
                proteins.add(uniprotToJAMI.getProteinFromEntry(entries.next()));
            }
        }catch (RemoteDataAccessException e){
            throw new BridgeFailedException("Problem with Uniprot Service.",e);
        }

        // Examples:
        // - one single entry : P12345
        // - uniprot demerge (different uniprot entries with different organisms) : P77681
        // - uniprot demerge (different uniprot entries with same organisms) : P11163
        // In your enricher, if you have several entries,
        // try to look for the one with the same organism as the protein you try to enrich.
        // If you don't find one or several entries have the same organism,
        // fire a specific event because we want to track these proteins.

        return proteins;
    }

    /**
     *
     * @param identifier
     * @return
     * @throws BadSearchTermException
     * @throws BadResultException
     * @throws BridgeFailedException
     */
    public Collection<Protein> fetchIsoformsByIdentifier(String identifier) throws BadSearchTermException, BadResultException, BridgeFailedException {

        if(identifier == null) throw new BadSearchTermException(
                "Tried to search for protein isoform on a null identifier.");

        Collection<Protein> proteins = new ArrayList<Protein>();

        try{                                                       //TODO UPPERCASE?
            Query query = UniProtQueryBuilder.buildExactMatchIdentifierQuery(identifier);
            EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);

            while(entries.hasNext()){
                UniProtEntry entry= entries.next();
                AlternativeProductsIsoform isoform = findIsoformInEntry(entry, identifier);
                if(isoform == null) log.debug("No isoform in entry "+entry.getUniProtId());
                else{
                    proteins.add(UniprotToJAMI.getProteinIsoformFromEntry(entry, isoform, identifier));
                }
            }
        }catch (RemoteDataAccessException e){
            throw new BridgeFailedException("Problem with Uniprot Service.",e);
        }
        return proteins;
    }

    /**
     *
     * Entry => comments => Isoforms => ids
     * There will be one ID matching the search identifier for each entry.
     *
     * @param entry
     * @param identifier
     */
    private static AlternativeProductsIsoform findIsoformInEntry(UniProtEntry entry, String identifier){

        List<AlternativeProductsComment> comments = entry.getComments(CommentType.ALTERNATIVE_PRODUCTS );

        for ( AlternativeProductsComment comment : comments ) {
            List<AlternativeProductsIsoform> isoforms = comment.getIsoforms();
            for ( AlternativeProductsIsoform isoform : isoforms ){
                for( IsoformId id :  isoform.getIds()){
                    if(identifier.equals(id.getValue())) return isoform;
                }
            }
        }
        return null;
    }


    protected final static String FEATURE_CHAIN_FIELD = "feature.chain:";
    protected final static String FEATURE_PEPTIDE_FIELD = "feature.peptide:";
    protected final static String FEATURE_PRO_PEPTIDE_FIELD = "feature.propep:";

    /**
     *
     * @param identifier
     * @return
     * @throws BadResultException
     * @throws BridgeFailedException
     * @throws BadSearchTermException
     */
    public Collection<Protein> fetchFeatureBySearch(String identifier)
            throws BadResultException, BridgeFailedException, BadSearchTermException {

        if(identifier == null) throw new BadSearchTermException(
                "Tried to search for protein feature on a null identifier.");

        Collection<Protein> proteins = new ArrayList<Protein>();

        try{
            Query query = UniProtQueryBuilder.buildFullTextSearch(
                    FEATURE_CHAIN_FIELD + identifier + " OR " +
                    FEATURE_PEPTIDE_FIELD + identifier + " OR " +
                    FEATURE_PRO_PEPTIDE_FIELD + identifier );

            EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);

            while(entries.hasNext()){
                UniProtEntry entry= entries.next();
                Feature feature = findFeatureInEntry(entry, identifier);
                proteins.add(UniprotToJAMI.getProteinFeatureFromEntry(entry, feature, identifier));
            }
        }catch (RemoteDataAccessException e){
                throw new BridgeFailedException("Problem with Uniprot Service.",e);
            }
        return proteins;

    }

    /**
     * Searches a uniprot entry to find the feature with a matching identifier.
     * @param entry
     * @param identifier
     * @return
     */
    private static Feature findFeatureInEntry(UniProtEntry entry, String identifier){
        Collection<ChainFeature> chainFeatures = entry.getFeatures( FeatureType.CHAIN );
        for(ChainFeature f : chainFeatures){
            if(f.getFeatureId().getValue().equals(identifier)) return f;
        }

        Collection<PeptideFeature> peptideFeatures = entry.getFeatures( FeatureType.PEPTIDE );
        for(PeptideFeature f : peptideFeatures){
            if(f.getFeatureId().getValue().equals(identifier))return f;
        }

        Collection<ProPepFeature> proPepFeatures = entry.getFeatures( FeatureType.PROPEP );
        for(ProPepFeature f : proPepFeatures){
            if(f.getFeatureId().getValue().equals(identifier)) return f;
        }
        return null;
    }

}