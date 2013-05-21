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

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 * Time: 11:25
 */
public class OlsFetcher implements CvTermFetcher {

    private final Logger log = LoggerFactory.getLogger(OlsFetcher.class.getName());

    OlsBridge bridge;

    public OlsFetcher() throws FetcherException {
        bridge = new OlsBridge();
    }


    public CvTerm getCvTermByID(String identifier, String database)
            throws FetcherException{

        if(identifier == null){
            throw new NullSearchException("The provided identifier was null.");
        }
        //TODO Change the way ontology moves through this section. Capture database name

        String termName = bridge.fetchFullNameByIdentifier(identifier, ontology);
        if(termName == null){
            throw new EntryNotFoundException("Identifier "+identifier+" returned no termName.");
        }

        if(ontology == null){
            ontology = OlsUtil.ontologyGetter(identifier);
            if(ontology == null) return null;
        }

        //Todo Ideally this should be a single, simple factory method
        Xref dbxref = new DefaultXref(new DefaultCvTerm(ontology),identifier, CvTermUtils.getIdentity());
        CvTerm cvTerm = new DefaultCvTerm(name,name,dbxref);
        return completeIdentifiedCvTerm(cvTerm, identifier, ontology);
    }


    public CvTerm getCvTermByTerm(String searchName, String database)
            throws FetcherException {

        return getCvTermByTerm(searchName, database, false);
    }


    public CvTerm getCvTermByTerm(String searchName, String database, boolean useFuzzySearch)
            throws FetcherException{

        if(searchName == null){
            throw new NullSearchException("The provided searchName was null.");
        }

        HashMap<String,String> identifierMap = null;
        if(useFuzzySearch){
            identifierMap = bridge.fetchIDByBestGuessTerm(searchName, ontology);
        }else{
            identifierMap = bridge.fetchIDByExactTerm(searchName, ontology);
        }

        if(identifierMap == null || identifierMap.size() < 1) {
            throw new EntryNotFoundException(
                    "The searchName ["+searchName+"] gave 0 IDs.");

        }else if(identifierMap.size() > 1){
            if(log.isDebugEnabled()){
                for(Object key : identifierMap.keySet()){
                    log.debug("Term ["+searchName+"] got the ID: "+key.toString()+" with name "+identifierMap.get(key));
                }
            }

            throw new EntryNotFoundException(
                    "The searchName ["+searchName+"] gave "+identifierMap.size()+" IDs.");
        }

        String resultIdentifier = null;
        String resultTerm = null;

        for(Object key : identifierMap.keySet()){
            resultIdentifier = key.toString();
            resultTerm = identifierMap.get(resultIdentifier);
        }

        if(resultIdentifier == null || resultTerm == null){
            throw new BadResultException(
                    "The searchName ["+searchName+"] gave UNEXPECTED null results. "
                    +"ID is ["+resultIdentifier+"] and term is ["+resultTerm+"].");
        }


        String identifierOntology =  OlsUtil.ontologyGetter(resultIdentifier);
        //TODO THROW EXCEPTION
        if(identifierOntology == null) return null;

        //Todo Ideally this should be a single, simple factory method
        Xref dbxref = new DefaultXref(new DefaultCvTerm(identifierOntology),identifier, CvTermUtils.getIdentity());
        CvTerm cvTerm = new DefaultCvTerm(identifierTerm,identifierTerm,dbxref);

        return completeIdentifiedCvTerm(cvTerm, identifier, identifierOntology);
    }



    /**
     * Adds additional information after the identifier and full name has been resolved.
     * This adds the short name (if it can be found) and the synonyms (if there are any).
     * The short name and synonyms can only be found for MI and MOD identifiers.
     * @param cvTerm
     * @param identifier
     * @param ontology
     * @return
     * @throws BridgeFailedException
     */
    private CvTerm completeIdentifiedCvTerm(CvTerm cvTerm, String identifier, String ontology)
            throws FetcherException{

        HashMap metaDataMap = bridge.fetchMetaDataByID(identifier);
        String shortName = bridge.extractShortNameFromMetaData(metaDataMap, ontology);
        if(shortName != null) cvTerm.setShortName(shortName);
        Collection<Alias> synonyms = bridge.extractSynonymsFromMetaData(metaDataMap, ontology);
        if(synonyms != null) cvTerm.getSynonyms().addAll(synonyms);

        return cvTerm;
    }

}
