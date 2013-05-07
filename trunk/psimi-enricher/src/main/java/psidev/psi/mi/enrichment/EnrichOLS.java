package psidev.psi.mi.enrichment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.exception.BridgeFailedException;
import psidev.psi.mi.exception.UnrecognizedTermException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.query.psiolsbridge.PsiOlsFetcher;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 03/05/13
 * Time: 10:32
 */
public class EnrichOLS {

    private final Logger log = LoggerFactory.getLogger(EnrichOLS.class.getName());

    private PsiOlsFetcher fetcher = null;

    public EnrichOLS(){
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
    public CvTerm queryOnCvTerm(CvTerm cvTerm) {
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
                            log.info("Overwriting ["+cvTerm.getFullName()+"] with ["+cvTest.getFullName()+"] for ID ["+identifier+"]");
                            cvTerm.setFullName(cvTest.getFullName());
                        }
                    }
                } catch (UnrecognizedTermException e) {
                    log.warn("The ID ["+identifier+"] was not identifier in OLS. UnrecognizedTermException.");
                } catch (BridgeFailedException e) {
                    log.error("BridgeFailedException");
                    return null;
                }
            }
        //If ID is not provided, try filling it in from name phrases
        }else{
            List<CvTerm> cvTest = null;
            if(cvTerm.getFullName() != null) {
                try{
                    cvTest = fetcher.fetchIDByTerm(cvTerm.getFullName(), null);
                    if(log.isDebugEnabled()){log.debug("No ID, searching on the full name");}
                } catch (UnrecognizedTermException e) {
                    if(log.isDebugEnabled()){
                        log.debug("Could not find ID by full name ["+cvTerm.getFullName()+"]. UnrecognizedTermException.");
                    }
                } catch (BridgeFailedException e) {
                    log.error("BridgeFailedException");
                    return null;
                }
            }
            if(cvTest == null){
                try{

                    cvTest = fetcher.fetchIDByTerm(cvTerm.getShortName(), null);
                } catch (UnrecognizedTermException e) {
                    if(log.isDebugEnabled()){
                        log.debug("Could not find ID by short name ["+cvTerm.getShortName()+"]. UnrecognizedTermException.");
                    }
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
                    log.warn("Could not choose between multiple term matches.");
                    //Todo Choose how to deal with multiple term matches
                }
            }
        }

        //If there's an identifier to use and the order is to overwrite
        if(identifier != null && overwriteAll) {
            try{
                CvTerm cvTest = fetcher.fetchMetaDataByID(identifier);
                //Todo Copy over the short name
                //todo copy over the synonyms
            } catch (BridgeFailedException e) {
                log.error("BridgeFailedException");
                return null;
            }
        }


        return cvTerm;
    }
}
