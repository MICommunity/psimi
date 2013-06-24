package psidev.psi.mi.jami.bridges.ols;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
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
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 */
public class OlsBridge{






      /*

    public CvTerm getCvTermByTerm(String searchName, String databaseName){
        return getCvTermByTerm(searchName, databaseName, false);
    }

    public CvTerm getCvTermByTerm(String searchName, CvTerm database)
            throws BridgeFailedException {
        return getCvTermByTerm(searchName, database, false);
    }

    public CvTerm getCvTermByTerm(String searchName, String databaseName, boolean useFuzzySearch)
            throws BridgeFailedException {

        if(searchName == null){
            throw new BadSearchTermException("The provided search term was null.");
        } else if (databaseName == null) {
            throw new BadSearchTermException("The provided database was null. " +
                    "Search term was ["+searchName+"].");
        }

        CvTerm database = new DefaultCvTerm(databaseName);
        return getCvTermByTerm(searchName, database, useFuzzySearch);
    }


    public CvTerm getCvTermByTerm(String searchName, CvTerm database, boolean useFuzzySearch)
            throws BridgeFailedException {

        if(searchName == null){
            throw new BadSearchTermException("The provided search term was null.");
        } else if (database == null) {
            throw new BadSearchTermException("The provided database was null. " +
                    "Search term was ["+searchName+"].");
        }

        HashMap<String,String> identifierMap = null;

        String databaseIdentifier = null;
        if(dbMap.containsKey(database)) databaseIdentifier = dbMap.get(database.getShortName());

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
    /*public HashMap<String,String> fetchIdentifierByBestGuessName(String name, String ontology)
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
    /*public HashMap<String,String> fetchIdentifierByExactName(String name, String ontology)
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
    /*public HashMap<String,String> fetchIdentifierByFuzzyName(String name, String ontology)
            throws BridgeFailedException{

        /**
         * Although the assignment is unchecked,
         * it must be taken on faith that the queryService WILL return String,String
         * Any case where this is not the case would be considered a failure of the bridge.
         */
        /*HashMap<String,String> termNamesMap;

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



    public boolean isIdentifierObsolete(String identifier, String databaseIdentifier)
            throws BridgeFailedException{
        try{
            return queryService.isObsolete(identifier, databaseIdentifier);
        }catch(RemoteException e){
            throw new BridgeFailedException();
        }
    }*/


}

