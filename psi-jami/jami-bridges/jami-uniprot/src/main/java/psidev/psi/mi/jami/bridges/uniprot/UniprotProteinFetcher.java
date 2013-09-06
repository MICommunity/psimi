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

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  14/05/13
 */
public class UniprotProteinFetcher
        implements ProteinFetcher {

    private final Logger log = LoggerFactory.getLogger(UniprotProteinFetcher.class.getName());

    private UniprotTranslationUtil uniprotTranslationUtil;
    private UniProtQueryService uniProtQueryService;

    // PRO regular expression: "PRO" followed by "-" OR "_" then any number of digits.
    public static final Pattern UNIPROT_PRO_REGEX = Pattern.compile("PRO[_|-][0-9]+");
    // Isoform regular expression: "-" followed by any number of digits.
    public static final Pattern UNIPROT_ISOFORM_REGEX = Pattern.compile("-[0-9]");


    public UniprotProteinFetcher() {
        uniProtQueryService = UniProtJAPI.factory.getUniProtQueryService();
        uniprotTranslationUtil = new UniprotTranslationUtil();
    }


    /**
     * Takes the various type of uniprot protein identifier and uses the uniprotJAPI to retrieve the matching proteins.
     * @param identifier    A Uniprot protein identifier, a Uniprot protein isoform identifier or a PRO identifier.
     * @return              The proteins which match the given identifier.
     * @throws BridgeFailedException
     */
    public Collection<Protein> fetchProteinsByIdentifier(String identifier)
            throws BridgeFailedException {

        if(identifier == null) throw new IllegalArgumentException("Could not perform search on null identifier.");

        Collection<Protein> proteins = null;

        String idType = identifierType(identifier);

        if(idType.equals(PROID)){
            // Truncate the pro identifier to allow the search to take place
            String proIdentifier = identifier.substring(identifier.indexOf("PRO") + 4, identifier.length());
            proteins = fetchFeatureByIdentifier(proIdentifier);
        } else if (idType.equals(ISOID)){
            proteins = fetchIsoformsByIdentifier(identifier);
        } else if (idType.equals(MASID)) {
            proteins = fetchMasterProteinsByIdentifier(identifier);
        }

        if(proteins == null || proteins.isEmpty())
            return Collections.EMPTY_LIST;
        else
            return proteins;
    }

    private final String PROID = "pro";
    private final String MASID = "mas";
    private final String ISOID = "iso";
    private String identifierType(String identifier){
        if(UNIPROT_PRO_REGEX.matcher(identifier).find()){
            return PROID;
        } else if (UNIPROT_ISOFORM_REGEX.matcher(identifier).find()){
            return ISOID;
        } else {
            return MASID;
        }
    }

    /**
     * Takes the various type of uniprot protein identifier
     * and uses the uniprotJAPI to retrieve the matching proteins.
     * @param identifiers   The identifiers to search for.
     * @return              The proteins which match an identifier in the query.
     * @throws BridgeFailedException
     */
    public Collection<Protein> fetchProteinsByIdentifiers(Collection<String> identifiers) throws BridgeFailedException {
        List<String> masterIdentifiers = new ArrayList<String>();
        List<String> featureIdentifiers = new ArrayList<String>();
        List<String> isoformIdentifiers = new ArrayList<String>();

        for(String identifier : identifiers){
            String idType = identifierType(identifier);
            if(idType.equals(MASID))
                masterIdentifiers.add(identifier);
            else if(idType.equals(ISOID))
                isoformIdentifiers.add(identifier);
            else if(idType.equals(PROID))
                featureIdentifiers.add(identifier);
        }

        Collection<Protein> proteinResults = new ArrayList<Protein>();

        // == Masters ===========
        proteinResults.addAll(fetchMasterProteinsByIdentifiers(masterIdentifiers));

        // == Isoforms ==========
        proteinResults.addAll(fetchIsoformsByIdentifiers(isoformIdentifiers));

        // == Features ===========
        for(String identifier : featureIdentifiers){
            proteinResults.addAll(fetchFeatureByIdentifier(identifier));
        }
        return proteinResults;
    }

    /**
     *
     * @param identifier    The identifier for a master protein
     * @return              The master proteins which match the identifier.
     * @throws BridgeFailedException
     */
    public Collection<Protein> fetchMasterProteinsByIdentifier(String identifier) throws BridgeFailedException {
        if(identifier == null) throw new IllegalArgumentException(
                "Attempted to search for protein on a null identifier.");
        Collection<Protein> proteins = new ArrayList<Protein>();

        try{
            Query query = UniProtQueryBuilder.buildExactMatchIdentifierQuery(identifier);
            EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);

            while(entries.hasNext()){
                proteins.add(uniprotTranslationUtil.getProteinFromEntry(entries.next()));
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
     *
     * @param identifiers    The identifier for a master protein
     * @return              The master proteins which match the identifier.
     * @throws BridgeFailedException
     */
    public Collection<Protein> fetchMasterProteinsByIdentifiers(List<String> identifiers) throws BridgeFailedException {
        if(identifiers == null) throw new IllegalArgumentException(
                "Attempted to search for protein on a null identifier.");
        Collection<Protein> proteins = new ArrayList<Protein>();

        try{
            Query query = UniProtQueryBuilder.buildIDListQuery(identifiers);
            EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);

            while(entries.hasNext()){
                proteins.add(uniprotTranslationUtil.getProteinFromEntry(entries.next()));
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

        try{
            Query query = UniProtQueryBuilder.buildExactMatchIdentifierQuery(identifier);
            EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);

            while(entries.hasNext()){
                UniProtEntry entry = entries.next();
                AlternativeProductsIsoform isoform = UniprotTranslationUtil.findIsoformInEntry(entry, identifier);
                if(isoform == null) log.warn("No isoform in entry "+entry.getUniProtId());
                else{
                    proteins.add(uniprotTranslationUtil.getProteinIsoformFromEntry(entry, isoform, identifier));
                }
            }
        }catch (RemoteDataAccessException e){
            throw new BridgeFailedException("Problem encountered whilst querying Uniprot service for isoforms.",e);
        }
        return proteins;
    }

    /**
     * Queries uniprot for the isoform identifier and returns the results in a list of Proteins
     *
     * @param identifiers    the isoform identifier in the form of [MasterID]-[IsoformNumber]
     * @return      the collection of proteins which match the search term
     * @throws BridgeFailedException
     */
    public Collection<Protein> fetchIsoformsByIdentifiers(List<String> identifiers) throws BridgeFailedException {
        if(identifiers == null) throw new IllegalArgumentException(
                "Attempted to search for protein isoform on a null identifier.");
        Collection<Protein> proteins = new ArrayList<Protein>();

        try{
            Query query = UniProtQueryBuilder.buildIDListQuery(identifiers);
            EntryIterator<UniProtEntry> entries = uniProtQueryService.getEntryIterator(query);

            while(entries.hasNext()){
                UniProtEntry entry = entries.next();
                Map<String , AlternativeProductsIsoform> matchingIsoform = UniprotTranslationUtil.findIsoformInEntry(entry, identifiers);
                if(matchingIsoform.isEmpty()) log.warn("No isoform in entry "+entry.getUniProtId());
                else{
                    String identifier = matchingIsoform.entrySet().iterator().next().getKey();
                    AlternativeProductsIsoform isoform = matchingIsoform.get(identifier);
                    proteins.add(uniprotTranslationUtil.getProteinIsoformFromEntry(entry, isoform, identifier));
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
                proteins.add(uniprotTranslationUtil.getProteinFeatureFromEntry(entry, feature, identifier));
            }
        }catch (RemoteDataAccessException e){
            throw new BridgeFailedException("Problem with Uniprot Service.",e);
        }
        return proteins;
    }

}