package psidev.psi.mi.fetcher.ols;

import psidev.psi.mi.fetcher.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.utils.factory.AliasFactory;
import uk.ac.ebi.ols.soap.Query;
import uk.ac.ebi.ols.soap.QueryServiceLocator;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 * Time: 11:12
 */
public class OlsBridge{

    Query qs;

    public OlsBridge()
            throws BridgeFailedException {

        try{
            qs = new QueryServiceLocator().getOntologyQuery();
        }catch (ServiceException e) {
            qs = null;
            throw new BridgeFailedException(e);
        }
    }

    /**
     * Uses ID to fetch the full name.
     *
     * @return
     * @throws BridgeFailedException
     */
    public String fetchFullNameByIdentifier(String identifier)
            throws BridgeFailedException{

        try {
            String fullname = qs.getTermById(identifier,null);
            if(fullname.equals(identifier)){
                return null;
            } else {
                return fullname;
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
     * @param ontology  Can be null. Use to limit ontologies that are searched.
     * @return          Null if no entries found, otherwise a list of one or more will be returned.
     * @throws BridgeFailedException
     */
    public HashMap<String,String> fetchIDByTerm(String name, String ontology)
            throws BridgeFailedException{

        HashMap<String,String> idList = new HashMap<String,String>();

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
     * @param ontology      Can be null. Use to limit ontologies that are searched.
     * @return              Null if no entries found, otherwise a list of one or more will be returned.
     * @throws BridgeFailedException
     */
    public HashMap<String,String> fetchIDByExactTerm(String name, String ontology)
            throws BridgeFailedException{

        HashMap<String,String> termNamesMap;

        try{
            termNamesMap = qs.getTermsByExactName(name, ontology);
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
     * @param name
     * @param ontology
     * @return
     * @throws BridgeFailedException
     */
    public HashMap<String,String> fetchIDByFuzzyTerm(String name, String ontology)
            throws BridgeFailedException{
        HashMap termNamesMap;

        try{
            termNamesMap = qs.getTermsByName(name, ontology , false);
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
     * Retrieve the metadata for an entry.
     * <p>
     * The identifier is used to find the correct ontology,
     * which can be used to find the identifier phrases for short labels and synonyms.
     *
     * @param identifier
     * @return          A new CvTerm with any results that were found filled in.
     * @throws BridgeFailedException
     */
    public Collection<Alias> fetchMetaDataByID(String identifier, String dbname)
            throws BridgeFailedException{

        //String SHORTLABEL_IDENTIFIER;
        String SYNONYM_IDENTIFIER;

        if(dbname.equals("MI")){
            //SHORTLABEL_IDENTIFIER = "Unique short label curated by PSI-MI";
            SYNONYM_IDENTIFIER = "Alternate label curated by PSI-MI";
        }
        else if(dbname.equals("MOD")){
            //SHORTLABEL_IDENTIFIER = "Short label curated by PSI-MOD";
            SYNONYM_IDENTIFIER = "Alternate name curated by PSI-MOD";
        }
        else {
            return null;
        }

        String META_DATA_SEPARATOR = "_";
        String DEFINITION_KEY = "definition";

        String description;
        //String shortLabel = null;

        HashMap metaDataMap;
        try{
            metaDataMap = qs.getTermMetadata(identifier,null);
        }catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }


        if (metaDataMap != null) {
            Collection<Alias> synonyms = new ArrayList<Alias>();
            for (Object key : metaDataMap.keySet()){
                String keyName = (String)key;
                if (keyName.startsWith(SYNONYM_IDENTIFIER + META_DATA_SEPARATOR)){

                    synonyms.add(AliasFactory.createAlias("synonym", "MI:1041", (String)metaDataMap.get(key)));
                }
                /*else if (DEFINITION_KEY.equalsIgnoreCase(keyName)){
                    description = (String) metaDataMap.get(key);
                }*/

                //Todo If the ShortLabel is to be used, this must be implemented
                /*else if (keyName.startsWith(SHORTLABEL_IDENTIFIER + META_DATA_SEPARATOR)){
                    cvTerm.setShortName((String)metaDataMap.get(key));
                }*/
            }
            return synonyms;
        }else{
            return null;
        }
    }
}

