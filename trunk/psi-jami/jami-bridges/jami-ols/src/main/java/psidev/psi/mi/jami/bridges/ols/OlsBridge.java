package psidev.psi.mi.jami.bridges.ols;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.utils.AliasUtils;
import uk.ac.ebi.ols.soap.Query;
import uk.ac.ebi.ols.soap.QueryServiceLocator;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 */
public class OlsBridge{

    private Query queryService;

    public OlsBridge()
            throws BridgeFailedException {

        try{
            queryService = new QueryServiceLocator().getOntologyQuery();
        }catch (ServiceException e) {
            queryService = null;
            throw new BridgeFailedException(e);
        }
    }

    /**
     * Uses the identifier to fetch the full name.
     *
     * @return The full name of the CvTerm or null if it cannot be found
     * @throws BridgeFailedException
     */
    public String fetchFullNameByIdentifier(String identifier, String ontology)
            throws BridgeFailedException{

        try {
            String fullName = queryService.getTermById(identifier,ontology);
            if(fullName.equals(identifier)){
                //The identifier could not be found.
                return null;
            } else {
                return fullName;
            }
        } catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }
    }

    /**
     * Will attempt to find ID's by querying for both exact and fuzzy names.
     * <p>
     * If an exact name (or names) is found found, the fuzzy search will not be attempted.
     * <p>
     * Although this method can search on any name type (short or full),
     * it is only capable of filling in IDs and fullNames.
     * Use @getMetaData to find and set the ShortName
     *
     * @param name      The phrase to search for.
     * @param ontology  Can be null. Use to search only one ontology.
     * @return          Null if no entries found, otherwise a list of one or more will be returned.
     * @throws BridgeFailedException
     */
    public HashMap<String,String> fetchIDByBestGuessTerm(String name, String ontology)
            throws BridgeFailedException{

        HashMap<String,String> idList = new HashMap<String, String>();

        idList.putAll(fetchIDByExactTerm(name, ontology));
        if(idList.size() == 0){
            idList.putAll(fetchIDByFuzzyTerm(name, ontology));
        }
        if(idList.size() == 0){
            return null;
        }else{
            return idList;
        }
    }

    /**
     * Will attempt to find ID's by querying for an exact name.
     *
     * @param name          The phrase to search for.
     * @param ontology      Can be null. Use to search only one ontology.
     * @return              Null if no entries found, otherwise a list of one or more will be returned.
     * @throws BridgeFailedException
     */
    public HashMap<String,String> fetchIDByExactTerm(String name, String ontology)
            throws BridgeFailedException{

        HashMap<String,String> termNamesMap;

        try{
            termNamesMap = queryService.getTermsByExactName(name, ontology);
        }catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }

        //Exact matches were found
        if (termNamesMap != null) {
            return termNamesMap;
        }
        return null;
    }

    /**
     *
     * @param name      The phrase to search for.
     * @param ontology  Can be null. Use to search only one ontology.
     * @return          Null if no entries found, otherwise a list of one or more will be returned.
     * @throws BridgeFailedException
     */
    public HashMap<String,String> fetchIDByFuzzyTerm(String name, String ontology)
            throws BridgeFailedException{

        /**
         * Although the assignment is unchecked,
         * it must be taken on faith that the queryService WILL return String,String
         * Any case where this is not the case would be considered a failure of the bridge.
         */
        HashMap<String,String> termNamesMap;

        try{
            termNamesMap = queryService.getTermsByName(name, ontology , false);
        }catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }//TODO confirm this should be caught
        catch (ClassCastException e){
            throw new BridgeFailedException("Unable to receive results from the OLS bridge",e);
        }

        //Exact matches were found
        if (termNamesMap != null && !termNamesMap.isEmpty()) {
            return termNamesMap;
        }
        return null;
    }




    /**
     * Retrieve the metadata for an entry.
     * <p>
     * The identifier is used to find the correct ontology,
     * which can be used to find the identifier phrases for short labels and synonyms.
     *
     * @param identifier
     * @return          A new CvTerm with any results that were found filled in.
     * @throws BridgeFailedException
     */
    public HashMap fetchMetaDataByID(String identifier)
            throws BridgeFailedException{

        try{
            return queryService.getTermMetadata(identifier, null);
        }catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }
    }

    public boolean isIdentifierObsolete(String identifier, String databaseIdentifier)
            throws BridgeFailedException{
        try{
            return queryService.isObsolete(identifier, databaseIdentifier);
        }catch(RemoteException e){
            throw new BridgeFailedException();
        }
    }

    /**
     *
     * @param metaDataMap
     * @param database
     * @return
     */
    public String extractShortNameFromMetaData(
            HashMap metaDataMap, String database){

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

    public String extractDescriptionFromMetaData(
            HashMap metaDataMap){
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

    public Collection<Alias> extractSynonymsFromMetaData(
            HashMap metaDataMap, String database){

        String META_DATA_SEPARATOR = "_";
        String SYNONYM_IDENTIFIER;
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
                }
            }
            return synonyms;
        }else{
            return null;
        }
    }
}

