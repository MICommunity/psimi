package psidev.psi.mi.fetcher.ols;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.enricher.cvtermenricher.CvTermFetcher;
import psidev.psi.mi.fetcher.exception.BridgeFailedException;
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

    public OlsFetcher() throws BridgeFailedException{
        bridge = new OlsBridge();
    }


    public CvTerm getCVTermByID(String identifier, String ontology)
            throws BridgeFailedException{

        String name = bridge.fetchFullNameByIdentifier(identifier, ontology);
        if(name == null){
            return null;
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


    public CvTerm getCVTermByName(String name, String ontology)
            throws BridgeFailedException{
        HashMap<String,String> identifierMap = bridge.fetchIDByTerm(name, ontology);

        if(identifierMap == null || identifierMap.size() <1) return null;
        String identifier = null;

        if(log.isDebugEnabled()){
            log.debug("Identifier ["+name+"] gave "+identifierMap.size()+" ids ");
            for(Object key : identifierMap.keySet()){
                log.debug("Term got the ID: "+key.toString()+" with name "+identifierMap.get(key));
            }
        }

        if(identifierMap.size()>1) return null;

        for(Object key : identifierMap.keySet()){
            identifier = key.toString();
        }

        if(identifier==null) return null;
        String identifierTerm = identifierMap.get(identifier);
        if(identifierTerm == null) return null;
        String identifierOntology =  OlsUtil.ontologyGetter(identifier);
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
    public CvTerm completeIdentifiedCvTerm(CvTerm cvTerm, String identifier, String ontology)
            throws BridgeFailedException{

        HashMap metaDataMap = bridge.fetchMetaDataByID(identifier);
        String shortName = bridge.extractShortNameFromMetaData(metaDataMap, ontology);
        if(shortName != null) cvTerm.setShortName(shortName);
        Collection<Alias> synonyms = bridge.extractSynonymsFromMetaData(metaDataMap, ontology);
        if(synonyms != null) cvTerm.getSynonyms().addAll(synonyms);

        return cvTerm;

    }

}
