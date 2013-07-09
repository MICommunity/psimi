package psidev.psi.mi.jami.bridges.uniprot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.uniprot.util.UniprotTranslationUtil;
import psidev.psi.mi.jami.model.Protein;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.AlternativeProductsIsoform;
import uk.ac.ebi.kraken.interfaces.uniprot.features.Feature;
import uk.ac.ebi.kraken.uuw.services.remoting.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  14/05/13
 */
public class UniprotFetcher
        implements ProteinFetcher {

    private UniprotTranslationUtil uniprotTranslationUtil = new UniprotTranslationUtil();

    UniProtQueryService uniProtQueryService = UniProtJAPI.factory.getUniProtQueryService();
    private final Logger log = LoggerFactory.getLogger(UniprotFetcher.class.getName());

    //private UniprotBridge bridge;

    public UniprotFetcher() {
        // log.debug("Starting uniprot bridge");
    }



    public static final Pattern UNIPROT_PRO_REGEX = Pattern.compile("PRO[_|-][0-9]+");
    public static final Pattern UNIPROT_ISOFORM_REGEX = Pattern.compile("-[0-9]");
    // public static final Pattern UNIPROT_MASTER_REGEX = Pattern.compile(
    //         "[A-NR-Z][0-9][A-Z][A-Z0-9][A-Z0-9][0-9]|"+
    //         "[OPQ][0-9][A-Z0-9][A-Z0-9][A-Z0-9][0-9]");

    /**
     *
     * @param identifier
     * @return
     * @throws BridgeFailedException
     */
    public Collection<Protein> getProteinsByIdentifier(String identifier)
            throws BridgeFailedException {

        if(identifier == null) throw new IllegalArgumentException("Could not perform search on null identifier.");

        Collection<Protein> proteins = null;

        if(UNIPROT_PRO_REGEX.matcher(identifier).find()){
            String proIdentifier = identifier.substring(identifier.indexOf("PRO") + 4, identifier.length());
            //log.debug("Searching for the pro identifier ["+proIdentifier+"] (from identifier ["+identifier+"])");
            proteins = fetchFeatureByIdentifier(proIdentifier);
        } else if (UNIPROT_ISOFORM_REGEX.matcher(identifier).find()){ //UNIPROT_MASTER_REGEX.matcher(identifier).find()
                proteins = fetchIsoformsByIdentifier(identifier);
        } else {
            proteins = fetchMastersByIdentifier(identifier);
        }

        if(proteins == null || proteins.isEmpty()){
            return Collections.EMPTY_LIST;
        }

        return proteins;
    }

    public Collection<Protein> getProteinsByIdentifiers(Collection<String> identifiers) throws BridgeFailedException {
        Collection<Protein> proteinResults = new ArrayList<Protein>();

        for(String identifier : identifiers){
            proteinResults.addAll(getProteinsByIdentifier(identifier));
        }

        return proteinResults;//Collections.EMPTY_LIST;
    }




    /**
     *
     * @param identifier
     * @return
     * @throws BridgeFailedException
     */
    public Collection<Protein> fetchMastersByIdentifier(String identifier) throws BridgeFailedException {
        if(identifier == null) throw new IllegalArgumentException(
                "Attempted to search for protein on a null identifier.");

        Collection<Protein> proteins = new ArrayList<Protein>();

        try{
            Query query = UniProtQueryBuilder.buildExactMatchIdentifierQuery(identifier);
            EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);

            while(entries.hasNext()){
                proteins.add(uniprotTranslationUtil.getProteinFromEntry(entries.next())); //TOdo method should be static
            }
        }catch (RemoteDataAccessException e){
            throw new BridgeFailedException("Problem with Uniprot Service.",e);
        }

        // Examples:
        // - one single entry : P12345
        // - uniprot demerge (different uniprot entries with different organisms) : P77681
        // - uniprot demerge (different uniprot entries with same organisms) : P11163

        return proteins;
    }

    /**
     * Queries uniprot for the isoform identifier and returns the results in a list of Proteins
     *
     * @param identifier    the isoform identifier in the form of [MasterID]-[IsoformNumber]
     * @return      the collection of proteins which match the search term
     * @throws BridgeFailedException
     */
    public Collection<Protein> fetchIsoformsByIdentifier(String identifier) throws BridgeFailedException {
        if(identifier == null) throw new IllegalArgumentException(
                "Attempted to search for protein isoform on a null identifier.");
        Collection<Protein> proteins = new ArrayList<Protein>();

        try{                                                       //TODO UPPERCASE?
            Query query = UniProtQueryBuilder.buildExactMatchIdentifierQuery(identifier);
            EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);

            while(entries.hasNext()){
                UniProtEntry entry = entries.next();
                AlternativeProductsIsoform isoform = UniprotTranslationUtil.findIsoformInEntry(entry, identifier);
                if(isoform == null) log.warn("No isoform in entry "+entry.getUniProtId());// TODO should this be more serious?
                else{
                    proteins.add(UniprotTranslationUtil.getProteinIsoformFromEntry(entry, isoform, identifier));
                }
            }
        }catch (RemoteDataAccessException e){
            throw new BridgeFailedException("Problem encountered whilst querying Uniprot service for isoforms.",e);
        }
        return proteins;
    }

    private final static String FEATURE_CHAIN_FIELD = "feature.chain:";
    private final static String FEATURE_PEPTIDE_FIELD = "feature.peptide:";
    private final static String FEATURE_PRO_PEPTIDE_FIELD = "feature.propep:";

    /**
     * Queries uniprot for the feature identifier  and returns the results in a list of protein.
     *
     * The search term is a PRO identifier. These may be encountered in the form [MasterID]-PRO_[number]
     * or in the form PRO-[number]. Only the number should be supplied for the search.
     * The "Pro_" marker and all preceding identifiers must be removed.
     *
     * @param identifier    the identifier for the feature in the form of a 10 digit number.
     * @return      the collection of proteins found in the search
     * @throws BridgeFailedException
     */
    public Collection<Protein> fetchFeatureByIdentifier(String identifier)
            throws  BridgeFailedException {

        if(identifier == null) throw new IllegalArgumentException(
                "Tried to search for protein feature on a null identifier.");

        Collection<Protein> proteins = new ArrayList<Protein>();

        try{
            Query query = UniProtQueryBuilder.buildFullTextSearch(
                    FEATURE_CHAIN_FIELD + identifier + " OR " +
                    FEATURE_PEPTIDE_FIELD + identifier + " OR " +
                    FEATURE_PRO_PEPTIDE_FIELD + identifier );

            EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);

            while(entries.hasNext()){
                UniProtEntry entry = entries.next();
                Feature feature = UniprotTranslationUtil.findFeatureInEntry(entry, identifier);
                proteins.add(UniprotTranslationUtil.getProteinFeatureFromEntry(entry, feature, identifier));
            }
        }catch (RemoteDataAccessException e){
            throw new BridgeFailedException("Problem with Uniprot Service.",e);
        }
        return proteins;
    }
}
