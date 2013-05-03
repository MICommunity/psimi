package psidev.psi.mi.query.psiolsbridge;

import psidev.psi.mi.exception.BridgeFailedException;
import psidev.psi.mi.exception.UnrecognizedTermException;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.factory.AliasFactory;
import uk.ac.ebi.ols.soap.Query;
import uk.ac.ebi.ols.soap.QueryServiceLocator;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * //TODO See @fetchMetaData!
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 26/04/13
 * Time: 15:32
 */
public class PsiOlsFetcher {

    Query qs;

    public PsiOlsFetcher()
            throws BridgeFailedException {

        try{
            qs = new QueryServiceLocator().getOntologyQuery();
        }catch (ServiceException e) {
            qs = null;
            throw new BridgeFailedException("OLS QS bridge failed to initialise.",e);
        }
    }

    /**
     * Uses ID to fetch the full name.
     *
     * @return
     * @throws UnrecognizedTermException    thrown if the id can not be found and @throwBadID is true
     * @throws BridgeFailedException
     */
    public CvTerm fetchByIdentifier(String identifier)
            throws UnrecognizedTermException, BridgeFailedException{

        String resultTerm;
        try {
            resultTerm = qs.getTermById(identifier,null);
        } catch (RemoteException e) {
            throw new BridgeFailedException("OLS QS bridge failed on a query by ID.",e);
        }

        if(resultTerm.equals(identifier)){
            throw new UnrecognizedTermException("Could not retrieve an entry for ["+resultTerm+"]");
        } else {
            CvTerm cvTerm = new DefaultCvTerm(resultTerm);
            cvTerm.setFullName(resultTerm);
            return cvTerm;
        }
    }


    /**
     * Use the identifier to calculate the ontology and then place the identifier in the correct field of tthe CvTerm.
     * <p>
     * CvTerms have multiple fields for identifiers, depending whether it is MI, MOD or PAR.
     * <p>
     * If the correct placement cannot be found, an exception is thrown.
     *
     * @param cvTerm
     * @param identifier
     * @return
     * @throws UnrecognizedTermException
     */
    private CvTerm setID(CvTerm cvTerm, String identifier)
            throws UnrecognizedTermException{

        if(identifier.split(":")[0].equals("MI")){
            cvTerm.setMIIdentifier(identifier);
        }else if(identifier.split(":")[0].equals("MOD")){
            cvTerm.setMODIdentifier(identifier);
        }else if(identifier.split(":")[0].equals("PAR")){
            cvTerm.setPARIdentifier(identifier);
        }else{
            throw new UnrecognizedTermException("Could not resolve an id from ["+identifier+"]");
        }
        return cvTerm;
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
     * @throws UnrecognizedTermException
     */
    public List<CvTerm> fetchByTerm(String name, String ontology)
            throws BridgeFailedException, UnrecognizedTermException{

        List<CvTerm> cvTermList = new ArrayList<CvTerm>();

        cvTermList.addAll(fetchByExactTerm(name, ontology));
        if(cvTermList.size() == 0){
            cvTermList.addAll(fetchByFuzzyTerm(name, ontology));
        }
        if(cvTermList.size() == 0){
            return null;
        }else{
            return cvTermList;
        }
    }

    /**
     * Will attempt to find ID's by querying for an exact name.
     *
     * @param name          The phrase to search for.
     * @param ontology      Can be null. Use to limit ontologies that are searched.
     * @return              Null if no entries found, otherwise a list of one or more will be returned.
     * @throws BridgeFailedException
     * @throws UnrecognizedTermException
     */
    public List<CvTerm> fetchByExactTerm(String name, String ontology)
            throws BridgeFailedException, UnrecognizedTermException{

        List<CvTerm> cvTermList = new ArrayList<CvTerm>();
        HashMap termNamesMap;

        try{
            termNamesMap = qs.getTermsByExactName(name, ontology);
        }catch (RemoteException e) {
            throw new BridgeFailedException("OLS QS bridge failed on a query by term name.",e);
        }

        //Exact matches were found
        if (termNamesMap != null) {
            for(Object key : termNamesMap.keySet()){
                termNamesMap.get(key);
                CvTerm cvTerm = new DefaultCvTerm((String)termNamesMap.get(key));
                cvTerm = setID(cvTerm, (String)key);
                cvTerm.setFullName((String)termNamesMap.get(key));
                cvTermList.add(cvTerm);
            }
        }
        return cvTermList;
    }

    /**
     *
     * @param name
     * @param ontology
     * @return
     * @throws BridgeFailedException
     * @throws UnrecognizedTermException
     */
    public List<CvTerm> fetchByFuzzyTerm(String name, String ontology)
            throws BridgeFailedException, UnrecognizedTermException{

        List<CvTerm> cvTermList = new ArrayList<CvTerm>();
        HashMap termNamesMap;

        try{
            termNamesMap = qs.getTermsByName(name, ontology , false);
        }catch (RemoteException e) {
            throw new BridgeFailedException("OLS QS bridge failed on a query by term name.",e);
        }

        if(termNamesMap != null){
            for(Object key : termNamesMap.keySet()){
                termNamesMap.get(key);
                CvTerm cvTerm = new DefaultCvTerm((String)termNamesMap.get(key));
                cvTerm = setID(cvTerm, (String)key);
                cvTerm.setFullName((String)termNamesMap.get(key));
                cvTermList.add(cvTerm);
            }
        }
        return cvTermList;
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
    public CvTerm fetchMetaData(String identifier)
            throws BridgeFailedException{

        String META_DATA_SEPARATOR = "_";
        String DEFINITION_KEY = "definition";
        String SHORTLABEL_IDENTIFIER = null;
        String SYNONYM_IDENTIFIER = null;


        String description;
        String shortLabel = null;

        HashMap metaDataMap;
        try{
            metaDataMap = qs.getTermMetadata(identifier,null);
        }catch (RemoteException e) {
            throw new BridgeFailedException("OLS QS bridge failed on a query for meta data.",e);
        }

        if(identifier.split(":")[0].equals("MI")){
            SHORTLABEL_IDENTIFIER = "Unique short label curated by PSI-MI";
            SYNONYM_IDENTIFIER = "Alternate label curated by PSI-MI";
        }else if(identifier.split(":")[0].equals("MOD")){
            SHORTLABEL_IDENTIFIER = "Short label curated by PSI-MOD";
            SYNONYM_IDENTIFIER = "Alternate name curated by PSI-MOD";
        }else if(identifier.split(":")[0].equals("PAR")){
            SHORTLABEL_IDENTIFIER = "";
            SYNONYM_IDENTIFIER = "";
        }

        CvTerm cvTerm = new DefaultCvTerm(identifier);

        if (metaDataMap != null) {
            for (Object key : metaDataMap.keySet()){
                String keyName = (String)key;
                if (DEFINITION_KEY.equalsIgnoreCase(keyName)){
                    description = (String) metaDataMap.get(key);
                }
                else if (keyName.startsWith(SYNONYM_IDENTIFIER + META_DATA_SEPARATOR)){
                    Collection<Alias> synonyms = cvTerm.getSynonyms();

                    //TODO Implement the alias factory once the static fields are fixed!
                    //Alias ally = AliasFactory.createAlias("synonym", "MI:1041", metaDataMap.get(key));
                    //synonyms.add(ally);

                }
                else if (keyName.startsWith(SHORTLABEL_IDENTIFIER + META_DATA_SEPARATOR)){
                    cvTerm.setShortName((String)metaDataMap.get(key));
                }
            }
        }

        return cvTerm;
    }
}