package psidev.psi.mi.DEPRECATEDquery.psiolsbridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.fetcher.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 03/05/13
 * Time: 10:32
 */
@Deprecated
public class PsiOlsEnricher {
    public PsiOlsEnricher(){}
    /*
    private final Logger log = LoggerFactory.getLogger(PsiOlsEnricher.class.getName());

    private PsiOlsFetcher fetcher = null;

    public PsiOlsEnricher(){
        try{
            fetcher = new PsiOlsFetcher();
        }catch (BridgeFailedException e){
            log.error("BridgeFailedException on initial pass.");
        }
    }

    /**
     *
     * <p>
     *
     * <p>
     * The fetcher throws BridgeFailedException which are caught in this method.
     * Any time the exception is caught, a SEVERE log is passed to the logger
     * and the method returns null.
     * //Todo The method could return a half completed CvTerm but this could be confusing.
     *
     * @param cvTerm
     * @return
     */
    /*public CvTerm queryOnCvTerm(CvTerm cvTerm) {
        if(fetcher == null){
            try{
                fetcher = new PsiOlsFetcher();
            }catch (BridgeFailedException e){
                log.error("BridgeFailedException on second pass.");
                return null;
            }
        }

        boolean overwriteAll = true;
        String identifier = null;

        //STEP 1:
        //Which ontology?
        if(cvTerm.getMIIdentifier() != null) {
            identifier = cvTerm.getMIIdentifier();
        }else if(cvTerm.getMODIdentifier() != null) {
            identifier = cvTerm.getMODIdentifier();
        }else if(cvTerm.getPARIdentifier() != null) {
            identifier = cvTerm.getPARIdentifier();
        }

        //TODO: a mismatched identifier creeps through without checks

        //If ID is not provided, try filling it in from name phrases
        if(identifier == null){
            List<CvTerm> cvTest = null;
            //Try searching by full name
            if(cvTerm.getFullName() != null) {
                try{
                    cvTest = fetcher.fetchIDByTerm(cvTerm.getFullName(), null);
                    if(log.isDebugEnabled()){log.debug("No ID, searching on the full name");}
                } catch (BridgeFailedException e) {
                    log.error("BridgeFailedException");
                    return null;
                }
            }
            //Still nothing, try searching by shortname
            if(cvTest == null){
                try{
                    cvTest = fetcher.fetchIDByTerm(cvTerm.getShortName(), null);
                    if(log.isDebugEnabled()){log.debug("No ID, searching on the short name");}
                } catch (BridgeFailedException e) {
                    log.error("BridgeFailedException");
                    return null;
                }
            }
            if(cvTest != null){
                if(cvTest.size() == 1){
                    if(cvTest.get(0).getMIIdentifier() != null){
                        identifier = cvTest.get(0).getMIIdentifier();
                        cvTerm.setMIIdentifier(cvTest.get(0).getMIIdentifier());
                    }else if(cvTest.get(0).getMODIdentifier() != null){
                        identifier = cvTest.get(0).getMODIdentifier();
                        cvTerm.setMODIdentifier(cvTest.get(0).getMODIdentifier());
                    }else if(cvTest.get(0).getPARIdentifier() != null){
                        identifier = cvTest.get(0).getPARIdentifier();
                        cvTerm.setPARIdentifier(cvTest.get(0).getPARIdentifier());
                    }
                }else{
                    log.warn("Could not choose between "+cvTest.size()+" term matches.");
                    if(log.isDebugEnabled()){
                        for(int i = 0; i < cvTest.size() ; i++){
                            String identifierx = null;
                            if(cvTest.get(i).getMIIdentifier() != null){
                                identifierx = cvTest.get(i).getMIIdentifier();
                            }else if(cvTest.get(i).getMODIdentifier() != null){
                                identifierx = cvTest.get(i).getMODIdentifier();
                            }else if(cvTest.get(i).getPARIdentifier() != null){
                                identifierx = cvTest.get(i).getPARIdentifier();
                            }
                            log.debug("Found " + identifierx);
                        }
                    }
                    //Todo Choose how to deal with multiple term matches
                }
            }
        }


        //If an identifier is provided, all other details are taken from that
        if(identifier != null) {
            //If the name is missing or overwirteAll - get it from the stuff
            if(cvTerm.getFullName() == null || overwriteAll){
                if(log.isDebugEnabled()){log.debug("Finding the full name by ID ["+identifier+"].");}
                try{
                    CvTerm cvTest = fetcher.fetchFullNameByIdentifier(identifier);
                    if(cvTest.getFullName() != null){
                        if(cvTerm.getFullName() != null
                                && cvTerm.getFullName().equals(cvTest.getFullName())){
                            //Has not changed
                            //Do nothing
                        }else{
                            if(cvTerm.getFullName() != null){
                                log.info("Overwriting full name ["+cvTerm.getFullName()+"] with ["+cvTest.getFullName()+"] for ID ["+identifier+"]");
                            }
                            cvTerm.setFullName(cvTest.getFullName());
                        }
                    }
                } catch (BridgeFailedException e) {
                    log.error("BridgeFailedException");
                    return null;
                }
            }
        }

        //If there's an identifier to use and the order is to overwrite
        //Try and update the shortname
        if(identifier != null && overwriteAll) {
            try{
                CvTerm cvTest = fetcher.fetchMetaDataByID(identifier);
                //A shortname was found or use the long name
                if(!cvTest.getShortName().equals(""+identifier)
                        && !cvTerm.getShortName().equals(cvTest.getShortName())) {
                    log.info("Overwriting shortname ["+cvTerm.getShortName()+"] with ["+cvTest.getShortName()+"] for ID ["+identifier+"]");
                    cvTerm.setShortName(cvTest.getShortName());
                } else if (cvTerm.getFullName() != null
                        && !cvTerm.getShortName().equals(cvTerm.getFullName())){
                    log.info("Overwriting shortname ["+cvTerm.getShortName()+"] with full name ["+cvTerm.getFullName()+"] for ID ["+identifier+"]");
                    cvTerm.setShortName(cvTerm.getFullName());
                }
                //todo copy over the synonyms
            } catch (BridgeFailedException e) {
                log.error("BridgeFailedException");
                return null;
            }
        }


        return cvTerm;
    }   */
}
