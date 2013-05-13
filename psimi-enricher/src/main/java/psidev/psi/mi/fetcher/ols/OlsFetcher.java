package psidev.psi.mi.fetcher.ols;

import psidev.psi.mi.enricher.cvtermenricher.CvTermFetcher;
import psidev.psi.mi.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 * Time: 11:25
 */
public class OlsFetcher implements CvTermFetcher {

    OlsBridge bridge;

    public OlsFetcher() throws BridgeFailedException{
        bridge = new OlsBridge();
    }

    public CvTerm getCVTermByID(String identifier, String dbname)
            throws BridgeFailedException{

        String name = bridge.fetchFullNameByIdentifier(identifier);
        if(name == null){
            return null;
        }

        //Todo Ideally this should be a single, simple factory method
        Xref dbxref = new DefaultXref(new DefaultCvTerm(dbname),identifier, CvTermUtils.getIdentity());
        CvTerm cvTerm = new DefaultCvTerm(name,name,dbxref);

        cvTerm.getSynonyms().addAll(bridge.fetchMetaDataByID(identifier, dbname));

        return cvTerm;
    }

    public CvTerm getCVTermByName(String name, String dbname)
            throws BridgeFailedException{
        HashMap<String,String> identifierMap = bridge.fetchIDByTerm(name, null);

        if(identifierMap == null || identifierMap.size()>1){
            return null;
        }
        String identifier = null;
        String iddbname;
        for(Object key : identifierMap.keySet()){
            identifier = identifierMap.get(key);
        }
        if(identifier==null || identifier.split(":")[0].equals(identifier)){
            return null;
        }
        iddbname =  identifier.split(":")[0];

        Xref dbxref = new DefaultXref(new DefaultCvTerm(iddbname),identifier, CvTermUtils.getIdentity());
        CvTerm cvTerm = new DefaultCvTerm(name,name,dbxref);

        cvTerm.getSynonyms().addAll(bridge.fetchMetaDataByID(identifier, iddbname));

        return cvTerm;
    }

}
