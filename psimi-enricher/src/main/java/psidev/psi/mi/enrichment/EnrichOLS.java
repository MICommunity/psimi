package psidev.psi.mi.enrichment;

import psidev.psi.mi.exception.BridgeFailedException;
import psidev.psi.mi.exception.UnrecognizedTermException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.query.psiolsbridge.PsiOlsFetcher;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 03/05/13
 * Time: 10:32
 */
public class EnrichOLS {

    private final static Logger LOGGER = Logger.getLogger(EnrichOLS.class.getName());

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
        PsiOlsFetcher fetcher;

        try{
            fetcher = new PsiOlsFetcher();
        }catch (BridgeFailedException e){
            LOGGER.severe("BridgeFailedException");
            return null;
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
                try{
                    CvTerm cvTest = fetcher.fetchFullNameByIdentifier(identifier);
                    if(cvTest.getFullName() != null){
                        if(cvTerm.getFullName().equals(cvTest.getFullName())){
                            //Has not changed
                            //Do nothing
                        }else{
                            LOGGER.info("Overwriting ["+cvTerm.getFullName()+"] with ["+cvTest.getFullName()+"] for ID ["+identifier+"]");
                            //Todo Log that this has been changed
                            cvTerm.setFullName(cvTest.getFullName());
                        }
                    }
                } catch (UnrecognizedTermException e) {
                    LOGGER.warning("UnrecognizedTermException");
                } catch (BridgeFailedException e) {
                    LOGGER.severe("BridgeFailedException");
                    return null;
                }
            }
        //If ID is not provided, try filling it in from name phrases
        }else{
            List<CvTerm> cvTest = null;
            if(cvTerm.getFullName() != null) {
                try{
                    cvTest = fetcher.fetchIDByTerm(cvTerm.getFullName(), null);
                } catch (UnrecognizedTermException e) {
                    LOGGER.warning("UnrecognizedTermException");
                } catch (BridgeFailedException e) {
                    LOGGER.severe("BridgeFailedException");
                    return null;
                }
            }
            if(cvTest == null){
                try{
                    cvTest = fetcher.fetchIDByTerm(cvTerm.getShortName(), null);
                } catch (UnrecognizedTermException e) {
                    LOGGER.warning("UnrecognizedTermException");
                } catch (BridgeFailedException e) {
                    LOGGER.severe("BridgeFailedException");
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
                LOGGER.severe("BridgeFailedException");
                return null;
            }
        }


        return cvTerm;
    }


}
