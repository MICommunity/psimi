package psidev.psi.mi.query.bridge.ols;

import psidev.psi.mi.exception.BridgeFailedException;
import psidev.psi.mi.exception.UnrecognizedTermException;
import psidev.psi.mi.jami.model.CvTerm;
import uk.ac.ebi.ols.soap.Query;
import uk.ac.ebi.ols.soap.QueryServiceLocator;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 26/04/13
 * Time: 11:43
 */
public class EnrichOLSCvTerm {
    /*
    private String ONTOLOGY = null;
    private String SHORTLABEL_IDENTIFIER = null;
    private String SYNONYM_IDENTIFIER = null;

    private static final String META_DATA_SEPARATOR = "_";
    private static final String DEFINITION_KEY = "definition";

    private String identifier = null;

    boolean overwriteAll = true;
    boolean throwBadID = false;

    Query qs;


    public EnrichOLSCvTerm(Query qs, boolean overwriteAll, boolean throwBadID){
        this.overwriteAll = overwriteAll;
        this.throwBadID = throwBadID;
        this.qs = qs;
    }

    public EnrichOLSCvTerm(Query qs){
        this.qs = qs;
    }   */

    /**
     * Attempts to fill in missing fields for a CvTerm.
     * <p>
     * If an ID is found, this overrides short name and full name
     * If a full name is found but no ID - this is used to find an ID
     * If neither an ID nor fullName are found, the short name is used to find and ID.
     * <p>
     * Once an ID is found, it can be used to overwrite the full and short names
     * (unless the overwriteAll boolean is false).
     *
     * @param cvTerm    the query data
     * @return          results
     * @throws psidev.psi.mi.exception.UnrecognizedTermException    if a dodgy ID was supplied
     * @throws BridgeFailedException        if the query service throws problems
     */
    /*public CvTerm queryOnCvTerm(CvTerm cvTerm)
            throws UnrecognizedTermException, BridgeFailedException{

        //STEP 1:
        //Which ontology?
        if(cvTerm.getMIIdentifier() != null) {
            setIdentifier(cvTerm.getMIIdentifier());
        }else if(cvTerm.getMODIdentifier() != null) {
            setIdentifier(cvTerm.getMODIdentifier());
        }

        //If an identifier is provided, all other details are taken from that
        if(identifier != null) {
            //If the name is missing or overwirteAll - get it from the stuff
            if(cvTerm.getFullName() == null || overwriteAll){
                cvTerm = queryByID(cvTerm);
            }
        }
        //If ids not provided, try filling it in from the full name
        else if(cvTerm.getFullName() != null) {
            cvTerm = queryByTerm(cvTerm, cvTerm.getFullName());
        }

        //if the id was not found from the full name, try the short name!
        if(identifier == null) {
            cvTerm = queryByTerm(cvTerm, cvTerm.getShortName());
        }

        //If there's an identifier to use and the order is to overwrite
        if(identifier != null && overwriteAll) {
            cvTerm = getMetaData(cvTerm);
        }

        if(ONTOLOGY != null && identifier != null){
            System.out.println("Using ontology "+ONTOLOGY);

            if(ONTOLOGY.equals("MI")){
                cvTerm.setMIIdentifier(identifier);
            }
            else if(ONTOLOGY.equals("MOD")){
                cvTerm.setMODIdentifier(identifier);
            }
        } else if (identifier != null){
            System.out.println("can't find a place for "+identifier);
        }

        return cvTerm;
    }

    /**
     * Uses ID to set the full name.
     *
     * @param cvTerm
     * @return
     * @throws UnrecognizedTermException    thrown if the id can not be found and @throwBadID is true
     * @throws BridgeFailedException
     */
    /*public CvTerm queryByID(CvTerm cvTerm)
            throws UnrecognizedTermException, BridgeFailedException{

        String resultTerm;
        try {
            resultTerm = qs.getTermById(identifier,ONTOLOGY);
        } catch (RemoteException e) {
            throw new BridgeFailedException("OLS QS bridge failed on a query by ID.",e);
        }

        if(resultTerm.equals(identifier)){
            if(throwBadID){
                throw new UnrecognizedTermException("Could not retrieve an entry for ["+resultTerm+"]");
            }
        } else {
            cvTerm.setFullName(resultTerm);

        }
        return cvTerm;
    }


    /**
     * Attempts to find an ID from a name.
     *
     * Searches for exact fullName matches first, then for inexact fullName matches.
     * Although this method can search on any name type (short or full),
     * it is only capable of filling in IDs and fullNames.
     * Use @getMetaData to find and set the ShortName
     *
     * @param cvTerm
     * @param name   a fullName, shortName or partial name
     * @return
     * @throws BridgeFailedException
     */
    /*public CvTerm queryByTerm(CvTerm cvTerm, String name)
            throws BridgeFailedException, UnrecognizedTermException{

        HashMap termNamesMap;
        try{
            termNamesMap = qs.getTermsByExactName(name, ONTOLOGY);
        }catch (RemoteException e) {
            throw new BridgeFailedException("OLS QS bridge failed on a query by term name.",e);
        }

        //Exact matches were found
        if (termNamesMap != null) {
            if(termNamesMap.size() == 1){
                Object key = termNamesMap.keySet().iterator().next();
                setIdentifier((String)key);
                cvTerm.setFullName((String)termNamesMap.get(key));
            }
            else if(termNamesMap.size() > 1){
                //what to do if there are multiple exact matches?

                System.out.println("found "+termNamesMap.size());

            }
        }

        //Only search by inexact name if the exact name matches nothing
        if(termNamesMap == null || termNamesMap.size() == 0) {
            try{
                termNamesMap = qs.getTermsByName(name, ONTOLOGY , false);
            }catch (RemoteException e) {
                throw new BridgeFailedException("OLS QS bridge failed on a query by term name.",e);
            }

            if(termNamesMap.size() == 1){
                Object key = termNamesMap.keySet().iterator().next();
                setIdentifier((String)key);
                cvTerm.setFullName((String)termNamesMap.get(key));
            }
            else if(termNamesMap.size() > 1){
                //what to do if there are multiple exact matches?

                System.out.println("found "+termNamesMap.size());

            }
        }
        return cvTerm;
    }

    /**
     * Retrieve the metadata for an entry and set the short name.
     *
     * If a short name is found, it will be set, if not the long name will overwrite.
     * Only use if the short label should be overwritten.
     * The short name will not be truncated.
     *
     * @param cvTerm
     * @return
     * @throws BridgeFailedException
     */
    /*public CvTerm getMetaData(CvTerm cvTerm)
            throws BridgeFailedException{

        String description;
        String shortLabel = null;
        HashMap metaDataMap;
        try{
            metaDataMap = qs.getTermMetadata(identifier,ONTOLOGY);
        }catch (RemoteException e) {
            throw new BridgeFailedException("OLS QS bridge failed on a query for meta data.",e);
        }

        if (metaDataMap != null) {
            for (Object key : metaDataMap.keySet()){
                String keyName = (String)key;
                if (DEFINITION_KEY.equalsIgnoreCase(keyName)){
                    description = (String) metaDataMap.get(key);
                }
                else if (keyName.startsWith(SYNONYM_IDENTIFIER + META_DATA_SEPARATOR){
                    cvTerm.cvTerm.getSynonyms() ;

                }
                else if (keyName.startsWith(SHORTLABEL_IDENTIFIER + META_DATA_SEPARATOR)){
                    shortLabel = (String)metaDataMap.get(key);
                    shortLabel = shortLabel.toLowerCase();
                }
                //System.out.println(keyName+" [is the key, for] "+metaDataMap.get(key));
            }
        }

        if (shortLabel == null && cvTerm.getFullName() != null){
            shortLabel = cvTerm.getFullName().toLowerCase();
        }

        if (shortLabel != null) {
            cvTerm.setShortName(shortLabel);
        }
        return cvTerm;
    }

    /**
     * Set the
     * @param ID
     */
    /*public void setOntology(String ID){
        if(ID.equals("MI")){
            ONTOLOGY = "MI";
            SHORTLABEL_IDENTIFIER = "Unique short label curated by PSI-MI";
            SYNONYM_IDENTIFIER = "Alternate label curated by PSI-MI";
        }else if(ID.equals("MOD")){
            ONTOLOGY = "MOD";
            SHORTLABEL_IDENTIFIER = "Short label curated by PSI-MOD";
        }
    }

    public void setIdentifier(String identifier)
            throws UnrecognizedTermException{

        this.identifier = identifier;
        try{
            setOntology(identifier.split(":")[0]);
        } catch(Exception e){
            if(throwBadID){
                throw new UnrecognizedTermException("Could not resolve an id from ["+identifier+"]");
            }
        }
    }  */
}
