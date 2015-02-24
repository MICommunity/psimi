package psidev.psi.mi.jami.bridges.picr;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.picr.io.PicrParsingException;
import psidev.psi.mi.jami.bridges.picr.io.PicrRESTParser;
import psidev.psi.mi.jami.bridges.picr.jaxb.GetUPIForAccessionResponse;
import psidev.psi.mi.jami.bridges.picr.jaxb.GetUPIForAccessionReturn;
import psidev.psi.mi.jami.bridges.picr.jaxb.IdenticalCrossReferences;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The PiCR client
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10-Mar-2010</pre>
 */

public class PicrClient {

    private AccessionMapperService accessionMapperService;
    private PicrRESTParser parser = new PicrRESTParser();
    private static final String wsdlFile = "http://www.ebi.ac.uk/Tools/picr/service?wsdl";

    private static final String restURLForUniprotBestGuess = "http://www.ebi.ac.uk/Tools/picr/rest/getUniProtBestGuess?";
    private static final String accessionMappingURL = "http://www.ebi.ac.uk/picr/AccessionMappingService";
    private static final String accessionMappingName = "AccessionMapperService";
    /**
     * Sets up a logger for that class.
     */
    public static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PicrClient.class.getName());

    public PicrClient(){

    }


    public AccessionMapperInterface getAccessionMapperPort() {
        if (this.accessionMapperService == null){
            try {
                accessionMapperService = new AccessionMapperService(new URL(this.wsdlFile), new QName(accessionMappingURL, accessionMappingName));
            } catch (MalformedURLException e) {
                throw new RuntimeException("Cannot create a new PICR accessionMapping service", e);
            }
        }
        return accessionMapperService.getAccessionMapperPort();
    }

    /**
     * Finds the list of swissProtIds for a provided ID and taxonId
     * @param accession the accession to look for
     * @param taxonId : the organism of the protein
     * @return the swissprotIds if found, empty list otherwise
     * @throws BridgeFailedException : an exception if the given accession is null
     */
    public Collection<String> getSwissprotIdsForAccession(String accession, String taxonId) throws BridgeFailedException{
        Collection<String> swissprotIdList = getIdsForAccession(accession, taxonId, PicrSearchDatabase.SWISSPROT_VARSPLIC, PicrSearchDatabase.SWISSPROT);

        return swissprotIdList;
    }

    /**
     * Finds the list of termblIds for a provided ID and taxonId
     * @param accession the accession to look for
     * @param taxonId : the organism of the protein
     * @return the tremblId if found, empty list otherwise
     * @throws BridgeFailedException : an exception if the given accession is null
     */
    public Collection<String> getTremblIdsForAccession(String accession, String taxonId) throws BridgeFailedException{
        Collection<String> tremblIdList = getIdsForAccession(accession, taxonId, PicrSearchDatabase.TREMBL_VARSPLIC, PicrSearchDatabase.TREMBL);

        return tremblIdList;
    }

    /**
     * Gets the list of uniparcId matching this accession number
     * @param accession
     * @param taxonId
     * @return the list of uniparc Id or empty list if the accession doesn't match any Uniparc sequence
     * @throws BridgeFailedException : an exception if the given accession is null
     */
    public Collection<UPEntry> getUniparcEntries(String accession, String taxonId) throws BridgeFailedException{
        Collection<UPEntry> upEntries = getUPEntriesForAccession(accession, taxonId, PicrSearchDatabase.SWISSPROT_VARSPLIC, PicrSearchDatabase.SWISSPROT, PicrSearchDatabase.TREMBL_VARSPLIC, PicrSearchDatabase.TREMBL);

        return upEntries;
    }

    /**
     * get the list of cross references accessions for a provided Id and taxonId from a list of databases
     * @param accession the accession to look for
     * @param taxonId : the organism of the protein
     * @param databases : the databases to query
     * @return the cross reference IDs if found, empty list otherwise
     * @throws BridgeFailedException : an exception if the given accession is null
     */
    private Collection<String> getIdsForAccession(String accession, String taxonId, PicrSearchDatabase ... databases) throws BridgeFailedException{
        List<UPEntry> upEntries = getUPEntriesForAccession(accession, taxonId, databases);
        Collection<String> idList = new ArrayList<String>();
        for (UPEntry entry : upEntries){
            List<CrossReference> listOfReferences = entry.getIdenticalCrossReferences();
            if (!listOfReferences.isEmpty()) {
                for (CrossReference c : listOfReferences) {
                    String ac = c.getAccession();
                    if (ac != null){
                        idList.add(ac);
                    }
                }
            }
        }
        return idList;
    }

    /**
     * Converts a list of PicrSearchDatabase into String
     * @param databases : the databases to query
     * @return the list of databases
     */
    private List<String> databaseEnumToList(PicrSearchDatabase ... databases) {
        List<String> databaseNames = new ArrayList<String>(databases.length);

        for (PicrSearchDatabase database : databases) {
            databaseNames.add(database.toString());
        }

        return databaseNames;
    }

    /**
     * Finds the list of UPEntries for a provided ID and organism from the provided list of databases
     * @param accession the accession to look for
     * @param taxonId the organism of the protein
     * @param databases the databases to query
     * @return the uniprot ID if found, null otherwise
     * @throws BridgeFailedException : an exception if the given accession is null
     */
    public List<UPEntry> getUPEntriesForAccession(String accession, String taxonId, PicrSearchDatabase ... databases) throws BridgeFailedException{
        if (accession == null){
            throw new BridgeFailedException("The identifier must not be null.");
        }
        if (databases == null) databases = PicrSearchDatabase.values();
        List<UPEntry> entries = Collections.EMPTY_LIST;
        try{
            entries = getAccessionMapperPort().getUPIForAccession(accession, null, databaseEnumToList(databases), taxonId, true);
        }
        catch (Exception e){
            log.error("PICR could not work properly", e);
        }
        return entries;
    }

    /**
     * get the cross references ids for a provided sequence and taxonId from a list of databases
     * @param sequence the sequence to look for
     * @param taxonId : the organism of the protein
     * @param databases : the databases to query
     * @return the list of cross reference IDs if found, empty list otherwise
     * @throws BridgeFailedException : an exception if the given sequence is null
     */
    private ArrayList<String> getIdsForSequence(String sequence, String taxonId, PicrSearchDatabase ... databases) throws BridgeFailedException{
        UPEntry upEntry = getUPEntriesForSequence(sequence, taxonId, databases);
        ArrayList<String> idList = new ArrayList<String>();

        if (upEntry != null){
            List<CrossReference> listOfReferences = upEntry.getIdenticalCrossReferences();
            if (!listOfReferences.isEmpty()) {
                for (CrossReference c : listOfReferences) {
                    String ac = c.getAccession();
                    if (ac != null){
                        idList.add(ac);
                    }
                }
            }
        }
        return idList;
    }

    /**
     * Finds the list of swissProtIds for a provided sequence and taxonId
     * @param sequence the sequence to look for
     * @param taxonId : the organism of the protein
     * @return the swissprotIds if found, empty list otherwise
     * @throws BridgeFailedException : an exception if the given sequence is null
     */
    public ArrayList<String> getSwissprotIdsForSequence(String sequence, String taxonId) throws BridgeFailedException{
        ArrayList<String> swissprotIdList = getIdsForSequence(sequence, taxonId, PicrSearchDatabase.SWISSPROT_VARSPLIC, PicrSearchDatabase.SWISSPROT);

        return swissprotIdList;
    }

    /**
     * Finds the list of termblIds for a provided sequence and taxonId
     * @param sequence the sequence to look for
     * @param taxonId : the organism of the protein
     * @return the tremblId if found, empty list otherwise
     * @throws BridgeFailedException : an exception if the given sequence is null
     */
    public ArrayList<String> getTremblIdsForSequence(String sequence, String taxonId) throws BridgeFailedException{
        ArrayList<String> tremblIdList = getIdsForSequence(sequence, taxonId, PicrSearchDatabase.TREMBL_VARSPLIC, PicrSearchDatabase.TREMBL);

        return tremblIdList;
    }

    /**
     * Gets the uniparcId matching this sequence
     * @param sequence
     * @param taxonId
     * @return the uniparc Id or null if the sequence doesn't match any Uniparc sequence
     */
    public String getUniparcIdFromSequence(String sequence, String taxonId) throws BridgeFailedException{
        UPEntry upEntry = getUPEntriesForSequence(sequence, taxonId, PicrSearchDatabase.SWISSPROT_VARSPLIC, PicrSearchDatabase.SWISSPROT, PicrSearchDatabase.TREMBL_VARSPLIC, PicrSearchDatabase.TREMBL);

        if (upEntry == null){
            return null;
        }

        return upEntry.getUPI();
    }

    /**
     * Get the UPEntry which matches the sequence and taxonId in the given databases
     * @param sequence : sequence of the protein to retrieve
     * @param taxonId : organism of the sequence
     * @param databases : the databases to look into
     * @return an UPEntry instance matching the sequence, taxonId in the specific databases
     * @throws BridgeFailedException : an exception if the given sequence is null
     */
    public UPEntry getUPEntriesForSequence(String sequence, String taxonId, PicrSearchDatabase ... databases) throws BridgeFailedException{
        if (databases == null) databases = PicrSearchDatabase.values();

        if (sequence == null){
            throw  new BridgeFailedException("The sequence must not be null.");
        }

        // sequence has to be in fasta format. If not, create a definition
        if (!sequence.startsWith(">")) {
            sequence = ">mySequence"+System.getProperty("line.separator")+sequence;
        }

        UPEntry entry = null;

        try{
            entry = getAccessionMapperPort().getUPIForSequence(sequence, databaseEnumToList(databases),
                    taxonId,
                    true);
        }
        catch (Exception e){
            log.error("PICR could not work properly", e);
        }

        return entry;
    }

    /**
     * Get an Unique uniprot Id for this accession
     * @param accession : the accession to look at
     * @param taxonId : the organism
     * @return an Unique uniprot Id
     * @throws BridgeFailedException
     */
    public String [] getUniprotBestGuessFor(String accession, String taxonId) throws BridgeFailedException{

        if (accession == null){
            throw new BridgeFailedException("The identifier must not be null.");
        }

        String query = restURLForUniprotBestGuess + "accession=" + accession;

        if (taxonId != null){
            query += "&taxid=" + taxonId;
        }
        URL url = null;
        try {
            url = new URL(query);

            GetUPIForAccessionResponse upiResponse = this.parser.read(url);

            if (upiResponse == null){
                return null;
            }
            else {
                GetUPIForAccessionReturn upiResponeReturn = upiResponse.getGetUPIForAccessionReturn();
                if (upiResponeReturn == null){
                    return null;
                }
                else {
                    IdenticalCrossReferences crossRef = upiResponeReturn.getIdenticalCrossReferences();

                    if (crossRef == null){
                        return null;
                    }
                    else {
                        String [] result = new String [2];

                        result[0] = crossRef.getDatabaseName();
                        result[1] = crossRef.getAccession();
                        return result;
                    }
                }
            }
        } catch (MalformedURLException e) {
            throw new BridgeFailedException("The URL " + query + " is malformed.");
        } catch (PicrParsingException e) {
            throw new BridgeFailedException("Problems while trying to parse the Picr REST xml results at " + query);
        }
    }

}
