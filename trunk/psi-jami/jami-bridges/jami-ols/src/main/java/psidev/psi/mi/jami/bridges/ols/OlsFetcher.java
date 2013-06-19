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
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 */
public class OlsFetcher
        implements CvTermFetcher {

    private final Logger log = LoggerFactory.getLogger(OlsFetcher.class.getName());

    private OlsBridge bridge;

    public OlsFetcher() throws BridgeFailedException {
        bridge = new OlsBridge();
    }

    private Map<String,String> dbMap = null;
    protected void initialiseDbMap(){
        dbMap = new HashMap<String, String>();
        dbMap.put("psi-mi", "MI");
        dbMap.put("psi-mod", "MOD");
        dbMap.put("psi-par", "PAR");
        dbMap.put("go","GO");
    }

    /**
     * Maps the database name to the database identifier
     *
     * @param database
     * @return
     */
    protected String databaseIdentifierGetter(String database){
        if(database == null)return null;
        if(dbMap == null) initialiseDbMap();
        if(dbMap.containsKey(database)) return dbMap.get(database);
        return null;
    }

    public CvTerm getCvTermByID(String identifier, String databaseName)
            throws BadSearchTermException, BridgeFailedException {

        if(identifier == null){
            throw new BadSearchTermException("The provided identifier was null.");
        } else if (databaseName == null) {
            throw new BadSearchTermException("The provided database was null. " +
                    "Identifier was ["+identifier+"].");
        }

        CvTerm database = new DefaultCvTerm(databaseName);
        return getCvTermByID(identifier, database);
    }

    public CvTerm getCvTermByID(String identifier, CvTerm database)
            throws BridgeFailedException, BadSearchTermException {

        if(identifier == null){
            throw new BadSearchTermException("The provided identifier was null.");
        } else if (database == null) {
            throw new BadSearchTermException("The provided database was null. " +
                    "Identifier was ["+identifier+"].");
        }

        String databaseIdentifier = databaseIdentifierGetter(database.getShortName());

        String termName = bridge.fetchFullNameByIdentifier(identifier, databaseIdentifier);

        if(termName == null) return null;

        //Todo Check these fields are used correctly
        Xref dbxref = new DefaultXref(database,identifier, CvTermUtils.getIdentity());
        CvTerm cvTermEnriched = new DefaultCvTerm(termName,termName,dbxref);

        return completeIdentifiedCvTerm(cvTermEnriched, identifier, database.getShortName());
    }


    public CvTerm getCvTermByTerm(String searchName, String databaseName)
            throws BadResultException, BadSearchTermException, BridgeFailedException {
        return getCvTermByTerm(searchName, databaseName, false);
    }

    public CvTerm getCvTermByTerm(String searchName, CvTerm database)
            throws BadResultException, BadSearchTermException, BridgeFailedException {
        return getCvTermByTerm(searchName, database, false);
    }

    public CvTerm getCvTermByTerm(String searchName, String databaseName, boolean useFuzzySearch)
            throws BadSearchTermException, BridgeFailedException, BadResultException {

        if(searchName == null){
            throw new BadSearchTermException("The provided search term was null.");
        } else if (databaseName == null) {
            throw new BadSearchTermException("The provided database was null. " +
                    "Search term was ["+searchName+"].");
        }

        CvTerm database = new DefaultCvTerm(databaseName);
        return getCvTermByTerm(searchName, database, useFuzzySearch);
    }


    public CvTerm getCvTermByTerm(String searchName, CvTerm database, boolean useFuzzySearch) throws BadSearchTermException, BridgeFailedException, BadResultException {

        if(searchName == null){
            throw new BadSearchTermException("The provided search term was null.");
        } else if (database == null) {
            throw new BadSearchTermException("The provided database was null. " +
                    "Search term was ["+searchName+"].");
        }

        HashMap<String,String> identifierMap = null;
        String databaseIdentifier = databaseIdentifierGetter(database.getShortName());
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
        Xref dbxref = new DefaultXref(database, resultIdentifier, CvTermUtils.getIdentity());
        CvTerm cvTermEnriched = new DefaultCvTerm(resultTerm,resultTerm,dbxref);

        return completeIdentifiedCvTerm(cvTermEnriched, resultIdentifier, database.getShortName());
    }

    /**
     * Adds additional information after the identifier and full name has been resolved.
     * This adds the short name (if it can be found) and the synonyms (if there are any).
     * The short name and synonyms can only be found for MI and MOD identifiers.
     * @param cvTermEnriched
     * @param identifier
     * @param databaseName
     * @return
     * @throws BridgeFailedException
     */
    private CvTerm completeIdentifiedCvTerm(CvTerm cvTermEnriched, String identifier, String databaseName)
            throws BridgeFailedException {

        HashMap metaDataMap = bridge.fetchMetaDataByID(identifier);
        String shortName = bridge.extractShortNameFromMetaData(metaDataMap, databaseName);
        if(shortName != null) cvTermEnriched.setShortName(shortName);
        Collection<Alias> synonyms = bridge.extractSynonymsFromMetaData(metaDataMap, databaseName);
        if(synonyms != null) cvTermEnriched.getSynonyms().addAll(synonyms);

        return cvTermEnriched;
    }

    public String getService() {
        return "Ontology Lookup Service";
    }
}
