package psidev.psi.mi.jami.bridges.ols;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.*;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import uk.ac.ebi.ols.soap.Query;
import uk.ac.ebi.ols.soap.QueryServiceLocator;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/05/13
 */
public class OlsFetcher
        implements CvTermFetcher<CvTerm> {

    protected final Logger log = LoggerFactory.getLogger(OlsFetcher.class.getName());
    private Query queryService;

    public OlsFetcher() throws BridgeFailedException {
        try{
            queryService = new QueryServiceLocator().getOntologyQuery();
        }catch (ServiceException e) {
            queryService = null;
            throw new BridgeFailedException(e);
        }
        initialiseDbMap();
    }

    /**
     * The CvTerm shortName for a database as the key and the OLS identifier as the value.
     */
    private Map<String,String> dbMap = new HashMap<String, String>();
    private void initialiseDbMap(){
        dbMap.put("psi-mi", "MI");
        dbMap.put("psi-mod", "MOD");
        dbMap.put("psi-par", "PAR");
        dbMap.put("go","GO");
    }

    /**
     * Uses the identifier and the name of the database to search for a complete form of the cvTerm.
     *
     * @param termIdentifier    The identifier for the CvTerm to fetch.
     * @param ontologyDatabaseName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @return  A full cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public CvTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName)
            throws BridgeFailedException {

        if(termIdentifier == null){
            throw new IllegalArgumentException("The identifier provided for CvTermFetcher was null.");
        } else if (ontologyDatabaseName == null) {
            throw new IllegalArgumentException("The database name provided for CvTermFetcher was null. "+
                    "The identifier was ["+termIdentifier+"].");
        }

        CvTerm ontologyDatabase;
        if(ontologyDatabaseName.equalsIgnoreCase("psi-mi")) ontologyDatabase = CvTermUtils.createPsiMiDatabase();
        else if (ontologyDatabaseName.equalsIgnoreCase("psi-mod")) ontologyDatabase = CvTermUtils.createPsiModDatabase();
        else ontologyDatabase = new DefaultCvTerm(ontologyDatabaseName);

        return getCvTermByIdentifier(termIdentifier, ontologyDatabase);
    }


    /**
     * Uses the identifier and a cvTerm denoting the database to search to fetch a complete from of the term.
     * @param termIdentifier     The identifier for the CvTerm to fetch
     * @param ontologyDatabase  The cvTerm of the ontology to search for.
     * @return  A full cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public CvTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase)
            throws BridgeFailedException {

        if(termIdentifier == null){
            throw new IllegalArgumentException("The identifier provided for CvTermFetcher was null.");
        } else if (ontologyDatabase == null) {
            throw new IllegalArgumentException("The database provided for CvTermFetcher was null. "+
                    "The identifier was ["+termIdentifier+"].");
        }

        // The database marker or null
        String databaseIdentifier = dbMap.get(ontologyDatabase.getShortName());

        String termName = fetchFullNameByIdentifier(termIdentifier, databaseIdentifier);
        if(termName == null) return null;

        Xref identifierXref = new DefaultXref(ontologyDatabase , termIdentifier);
        CvTerm cvTermEnriched = new DefaultCvTerm(termName , termName , identifierXref);

        return completeIdentifiedCvTerm(cvTermEnriched, termIdentifier, ontologyDatabase.getShortName());
    }


    /**
     * Uses the name of the term and the name of the database to search for a complete form of the term.
     *
     * @param searchName    A full or short name for the term to be searched for.
     * @param ontologyDatabaseName  The ontology to search for the term in.
     * @return  A fully enriched cvTerm which matches the search term or null if one cannot be found.
     * @throws BridgeFailedException
     */
    public CvTerm getCvTermByExactName(String searchName, String ontologyDatabaseName)
            throws BridgeFailedException {

        if(searchName == null){
            throw new IllegalArgumentException("The search term provided for CvTermFetcher was null.");
        }else if (ontologyDatabaseName == null) {
            throw new IllegalArgumentException("The database provided for CvTermFetcher was null. "+
                    "The search term was ["+searchName+"].");
        }

        CvTerm ontologyDatabase;
        if(ontologyDatabaseName.equalsIgnoreCase("psi-mi")) ontologyDatabase = CvTermUtils.createPsiMiDatabase();
        else if (ontologyDatabaseName.equalsIgnoreCase("psi-mod")) ontologyDatabase = CvTermUtils.createPsiModDatabase();
        else ontologyDatabase = new DefaultCvTerm(ontologyDatabaseName);

        String databaseIdentifier = dbMap.get(ontologyDatabaseName);


        HashMap<String,String> termNamesMap;

        try{
            termNamesMap = queryService.getTermsByExactName(searchName, databaseIdentifier);
        }catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }

        if(termNamesMap.isEmpty()) return null;
        else if(termNamesMap.size() == 1){
            Map.Entry<String, String> entry = termNamesMap.entrySet().iterator().next();
            if(entry.getValue() == null || entry.getKey() == null){
                throw new IllegalArgumentException("OLS service returned null values in an exact name search.");
            }
            // Key is the Identifier, value is the full name


            Xref identifierXref = new DefaultXref(ontologyDatabase, entry.getKey());
            CvTerm cvTermEnriched = new DefaultCvTerm(entry.getValue(), entry.getValue(), identifierXref);

            return completeIdentifiedCvTerm(cvTermEnriched, entry.getKey(), ontologyDatabaseName);
        }
        return null;
    }

    /**
     * As currently stands, this method will only find search terms in the MI or MOD databases.
     * Searches for a name in OLS, tries to match the identifier (if one is returned) to an ontology database.
     * If a term is found but the ontology can not be resolved, null will be returned.
     *
     * @param searchName    a full or short name to search by.
     * @return              a full cvTerm or null if a CvTerm with a recognised ontology can not be found
     * @throws BridgeFailedException
     */
    public CvTerm getCvTermByExactName(String searchName)
            throws BridgeFailedException {

        if(searchName == null) throw new IllegalArgumentException(
                "The search term provided for CvTermFetcher was null.");


        HashMap<String,String> termNamesMap;

        try{
            termNamesMap = queryService.getTermsByExactName(searchName, null);
        }catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }

        if(termNamesMap.isEmpty()) {
            if(log.isDebugEnabled()) log.debug("Empty result for name "+searchName);
            return null;
        }
        else if(termNamesMap.size() == 1){
            if(log.isDebugEnabled()) log.debug("One entry for name "+searchName);
            Map.Entry<String, String> entry = termNamesMap.entrySet().iterator().next();
            if(entry.getValue() == null || entry.getKey() == null){
                throw new IllegalArgumentException("OLS service returned null values in an exact name search.");
            }
            // Key is the Identifier, value is the full name

            CvTerm ontologyDatabase;
            if(entry.getKey().startsWith("MI:")) ontologyDatabase = CvTermUtils.createPsiMiDatabase();
            else if (entry.getKey().startsWith("MOD:")) ontologyDatabase = CvTermUtils.createPsiModDatabase();
            else {
                ontologyDatabase = new DefaultCvTerm("unknown ontology");
            }


            String ontologyDatabaseName = dbMap.get(ontologyDatabase.getShortName());

            Xref identifierXref = new DefaultXref(ontologyDatabase, entry.getKey());
            CvTerm cvTermEnriched = new DefaultCvTerm(entry.getValue(), entry.getValue(), identifierXref);

            return completeIdentifiedCvTerm(cvTermEnriched, entry.getKey(), ontologyDatabaseName);
        }
        if(log.isDebugEnabled()) log.debug("Many entries for name "+searchName+ " there were "+termNamesMap.size());
        return null;
    }



    public Collection<CvTerm> getCvTermByInexactName(String searchName, String databaseName)
            throws BridgeFailedException {
        return null;
    }

    public Collection<CvTerm> getCvTermByInexactName(String searchName, CvTerm database)
            throws BridgeFailedException {
        /*
        if(searchName == null){
            throw new BadSearchTermException("The provided search term was null.");
        } else if (database == null) {
            throw new BadSearchTermException("The provided database was null. " +
                    "Search term was ["+searchName+"].");
        }

        HashMap<String,String> identifierMap = null;

        String databaseIdentifier = dbMap.get(database.getShortName());

        if(useFuzzySearch){
            identifierMap = bridge.fetchIDByBestGuessTerm(searchName, databaseIdentifier);
        }else{
            identifierMap = bridge.fetchIDByExactTerm(searchName, databaseIdentifier);
        }

        if(identifierMap == null || identifierMap.size() < 1) return null;

        else if(identifierMap.size() > 1){
            if(log.isDebugEnabled()){
                log.debug("The searchName ["+searchName+"] gave "+identifierMap.size()+" IDs. " +
                        "Search was fuzzy: "+useFuzzySearch);
                for(Object key : identifierMap.keySet()){
                    log.debug("Term ["+searchName+"] got the ID: "+key.toString()+" with name "+identifierMap.get(key));
                }
            }
            return null;
        }

        String resultIdentifier = null;
        String resultTerm = null;

        for(Object key : identifierMap.keySet()){
            resultIdentifier = key.toString();
            resultTerm = identifierMap.get(resultIdentifier);
        }

        if(resultIdentifier == null || resultTerm == null){
            throw new BadResultException(
                    "The searchName ["+searchName+"] gave unexpected null results. "
                            +"ID is ["+resultIdentifier+"] and term is ["+resultTerm+"].");
        }

        //Todo Check these fields are used correctly
        Xref identifierXref = new DefaultXref(database, resultIdentifier);
        CvTerm cvTermEnriched = new DefaultCvTerm(resultTerm,resultTerm,identifierXref);

        return completeIdentifiedCvTerm(cvTermEnriched, resultIdentifier, database.getShortName()); */
        return null;
    }

    public Collection<CvTerm> getCvTermsByIdentifiers(Collection<String> identifiers, String ontologyDatabaseName)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<CvTerm> getCvTermsByIdentifiers(Collection<String> identifiers, CvTerm ontologyDatabase)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<CvTerm> getCvTermsByExactNames(Collection<String> searchNames, String ontologyDatabaseName)
            throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public Collection<CvTerm> getCvTermsByExactNames(Collection<String> searchNames)
            throws BridgeFailedException {
        return null;
    }


    //----------
    // Private Methods

    /**
     * Uses the identifier to fetch the full name.
     *
     * @return The full name of the CvTerm or null if it cannot be found
     * @throws BridgeFailedException
     */
    protected String fetchFullNameByIdentifier(String identifier, String ontology)
            throws BridgeFailedException{

        try {
            String fullName = queryService.getTermById(identifier,ontology);
            if(fullName.equals(identifier)){
                // The identifier could not be found.
                // The OLS service echoes back the original query term if it can not be found
                return null;
            } else {
                return fullName;
            }
        } catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }
    }

    /**
     * Adds additional information after the identifier and full name has been resolved.
     * This adds the short name (if it can be found) and the synonyms (if there are any).
     * The short name and synonyms can only be found for MI and MOD identifiers.
     * @param cvTermFetched
     * @param identifier
     * @param databaseName
     * @return
     * @throws BridgeFailedException
     */
    protected CvTerm completeIdentifiedCvTerm(CvTerm cvTermFetched, String identifier, String databaseName)
            throws BridgeFailedException {

        Map metaDataMap = fetchMetaDataByIdentifier(identifier);
        String shortName = extractShortNameFromMetaData(metaDataMap, databaseName);
        if(shortName != null)
            cvTermFetched.setShortName(shortName);
        else if(cvTermFetched.getFullName() != null)
            cvTermFetched.setShortName(cvTermFetched.getFullName());
        Collection<Alias> synonyms = extractSynonymsFromMetaData(metaDataMap, databaseName);
        if(synonyms != null) cvTermFetched.getSynonyms().addAll(synonyms);

        return cvTermFetched;
    }

    /**
     * Retrieve the metadata for an entry.
     * <p>
     * The identifier is used to find the metadata
     * which can be used to find the identifier phrases for short labels and synonyms.
     *
     * @param identifier
     * @return          A new CvTerm with any results that were found filled in.
     * @throws BridgeFailedException
     */
    protected Map fetchMetaDataByIdentifier(String identifier)
            throws BridgeFailedException{

        try{
            return queryService.getTermMetadata(identifier, null);
        }catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }
    }

    /**
     * Scans the meta data to find the short name if one is present.
     * @param metaDataMap
     * @param database
     * @return
     */
    protected String extractShortNameFromMetaData(
            Map metaDataMap, String database){

        String META_DATA_SEPARATOR = "_";
        String SHORTLABEL_IDENTIFIER;
        if(database == null) return null;
        else if(database.equals("psi-mi")) SHORTLABEL_IDENTIFIER = "Unique short label curated by PSI-MI";
        else if(database.equals("psi-mod")) SHORTLABEL_IDENTIFIER = "Short label curated by PSI-MOD";
        else return null;

        if (metaDataMap != null) {
            for (Object key : metaDataMap.keySet()){
                String keyName = (String)key;
                if (keyName.startsWith(SHORTLABEL_IDENTIFIER + META_DATA_SEPARATOR)){
                    return (String)metaDataMap.get(key);
                }
            }
        }
        return null;
    }

    /**
     * Scans the meta data to find the description if one is present.
     * @param metaDataMap
     * @return
     */
    protected String extractDescriptionFromMetaData(
            Map metaDataMap){
        String DEFINITION_KEY = "definition";
        if (metaDataMap != null) {
            for (Object key : metaDataMap.keySet()){
                String keyName = (String)key;
                if (DEFINITION_KEY.equalsIgnoreCase(keyName)){
                    return (String) metaDataMap.get(key);
                }
            }
        }
        return null;
    }

    /**
     * Scans the meta data to find the synonyms if any are present.
     * @param metaDataMap
     * @param database
     * @return
     */
    protected Collection<Alias> extractSynonymsFromMetaData(
            Map metaDataMap, String database){

        String META_DATA_SEPARATOR = "_";
        String SYNONYM_IDENTIFIER;
        String EXACT_SYNONYM_KEY = "exact_synonym";
        if(database == null) return null;
        else if(database.equalsIgnoreCase("psi-mi")) SYNONYM_IDENTIFIER = "Alternate label curated by PSI-MI";
        else if(database.equalsIgnoreCase("psi-mod")) SYNONYM_IDENTIFIER = "Alternate name curated by PSI-MOD";
        else return null;

        if (metaDataMap != null) {
            Collection<Alias> synonyms = new ArrayList<Alias>();
            for (Object key : metaDataMap.keySet()){
                String keyName = (String)key;
                if (keyName.startsWith(SYNONYM_IDENTIFIER + META_DATA_SEPARATOR)){
                    synonyms.add(AliasUtils.createAlias(
                            "synonym", "MI:1041", (String) metaDataMap.get(key)));
                }else if (keyName.startsWith(EXACT_SYNONYM_KEY)){
                    synonyms.add(AliasUtils.createAlias(
                            "synonym", "MI:1041", (String) metaDataMap.get(key)));
                }
            }
            return synonyms;
        }else{
            return null;
        }
    }
}
