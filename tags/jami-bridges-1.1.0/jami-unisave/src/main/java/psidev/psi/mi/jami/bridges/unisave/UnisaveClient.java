package psidev.psi.mi.jami.bridges.unisave;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.SequenceVersionFetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Client to query unisave and collect sequence versions or sequence from a particular version
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/09/13</pre>
 */

public class UnisaveClient implements SequenceVersionFetcher{

    private static final Log log = LogFactory.getLog(UnisaveClient.class);

    private String unisaveUrlRestJson = "http://www.ebi.ac.uk/uniprot/unisave/rest";
    private HttpClient httpClient;
    private int connectionTimeOut = 20;
    private int socketTimeout = 20;

    public UnisaveClient() {

    }

    private Object getDataFromWebService(String query) throws BridgeFailedException{
        HttpGet request = new HttpGet(query);
        request.addHeader("accept", "application/json");
        BufferedReader reader=null;
        Object obj=null;
        try {
            HttpResponse response = getHttpClient().execute(request);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new BridgeFailedException("Failed to query unisave : HTTP error code : " + response.getStatusLine().getStatusCode());
            }
            reader = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            obj = JSONValue.parse(reader);

        } catch (IOException e) {
            throw new BridgeFailedException("Failed to query unisave", e);
        }
        finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    private String buildQuery(Type type, String id, String version) throws BridgeFailedException {
        StringBuilder builder = new StringBuilder().append(this.unisaveUrlRestJson);
        switch (type){
            case ENTRIES:
                builder.append(Type.ENTRIES.valueOf()).append(id);
                break;
            case ENTRY_VERSION:
                if(version != null){
                    builder.append(Type.ENTRY_VERSION.valueOf()).append(id).append("/").append(version);
                }
                else {
                    throw new BridgeFailedException("To use the " + Type.ENTRY_VERSION.valueOf() +
                            " method in the Rest you have to provide a not null version");
                }
                break;
            case ENTRIES_INFO:
                builder.append(Type.ENTRIES_INFO.valueOf()).append(id);
                break;
            case ENTRY_INFO_VERSION:
                if (version != null){
                    builder.append(Type.ENTRY_INFO_VERSION.valueOf()).append(id).append("/").append(version);
                }
                else {
                    throw new BridgeFailedException("To use the " + Type.ENTRY_INFO_VERSION.valueOf() +
                            " method in the Rest you have to provide a not null version");
                }
                break;
            default:
                return null;
        }
        return builder.toString();
    }

    private String getSequence(String content) {
        content = content.substring(content.lastIndexOf("SEQUENCE"));
        content = content.substring(content.indexOf("\n")).trim();
        content = content.replaceAll(" ", "");
        content = content.replaceAll("\n", "");
        return content.replaceAll("\t", "").replaceAll("//", "");
    }


    private String getFastHeader(String content) {
        int init = content.indexOf("FT ");
        int last = content.lastIndexOf("FT ");
        if(init == -1) return null;
        last = content.indexOf("\n", last); //real last
        return content.substring(init, last);
    }

    private String getContentForSequenceVersion(String identifier, int sequence_version) throws BridgeFailedException {
        JSONArray array = (JSONArray) getDataFromWebService(buildQuery(Type.ENTRIES, identifier, null));
        JSONObject jo = null;
        for(int i = 0; i < array.size(); ++i){
            jo = (JSONObject) array.get(i);
            if (sequence_version == Integer.valueOf(String.valueOf(jo.get("sequence_version")))){
                return (String)jo.get("content");
            }
        }
        return null;
    }

    public String getSequenceFor(String identifier, int sequence_version) throws BridgeFailedException{
        String content = getContentForSequenceVersion(identifier, sequence_version);
        if (content != null) return getSequence(content);
        else return null;
    }

    /**
     * Searches for all entry version given its identifier.
     *
     * @param identifier  the identifier of the entry we are interested in.
     *
     * @return list of all versions of the given entry from the most recent to the oldest.
     *
     * @throws BridgeFailedException if the identifier cannot be found in UniSave.
     */
    public List<Integer> getVersions( String identifier ) throws BridgeFailedException {
        JSONArray array = (JSONArray) getDataFromWebService(buildQuery(Type.ENTRIES_INFO, identifier, null));
        List<Integer> list = new ArrayList<Integer>();
        JSONObject jo = null;
        for(int i = 0; i < array.size(); ++i) {
            jo = (JSONObject) array.get(i);
            list.add(Integer.valueOf(String.valueOf(jo.get("entry_version"))));
        }

        if( list.isEmpty() ) {
            throw new BridgeFailedException( "Failed to find any version for identifier " + identifier );
        }
        return list;
    }

    public String getLastSequenceAtTheDate(String identifier, Date date) throws BridgeFailedException {

        if (date == null){
            throw new IllegalArgumentException("The date cannot be null.");
        }

        JSONArray array = (JSONArray) getDataFromWebService(buildQuery(Type.ENTRIES, identifier, null));
        JSONObject jo = null;
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date auxDate = null;
        for(int i = 0; i < array.size(); ++i) {
            jo = (JSONObject) array.get(i);
            try {
                auxDate = formatter.parse((String) jo.get("firstReleaseDate"));
                if(auxDate.before(date)){
                    return getSequence(String.valueOf(jo.get("content")));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Get the map of sequences (and their sequence version in uniprot) existing in unisave before this date
     * @param identifier
     * @param date
     * @return
     * @throws BridgeFailedException
     */
    public Map<Integer, String> getAllSequencesBeforeDate(String identifier, Date date) throws BridgeFailedException {

        if (date == null){
            throw new IllegalArgumentException("The date cannot be null.");
        }

        Map<Integer, String> oldSequences = new HashMap<Integer, String>();
        JSONArray array = (JSONArray) getDataFromWebService(buildQuery(Type.ENTRIES, identifier, null));
        JSONObject jo = null;
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date auxDate = null;
        for(int i = 0; i < array.size(); ++i){
            jo = (JSONObject) array.get(i);
            try {
                auxDate = formatter.parse((String) jo.get("firstReleaseDate") );
                if(auxDate.before(date)){
                    oldSequences.put(Integer.valueOf(String.valueOf(jo.get("sequence_version"))),
                            getSequence((String) jo.get("content")));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return oldSequences;
    }

    /**
     * Retrieve a fasta sequence corresponding to a given EntryVersion.
     *
     * @param version the version for which we want the sequence
     * @return a fasta sequence.
     * @throws BridgeFailedException if the version given doesn't have an entryId that can be found in UniSave.
     */
    public FastaSequence getFastaSequence( String identifier, int version ) throws BridgeFailedException {
        String content = getContentForSequenceVersion(identifier, version);
        if (content != null) return new FastaSequence( getFastHeader(content), getSequence(content) );
        else return null;
    }


    /**
     * Returns the sequence version of a sequence for a certain uniprot ac.
     * Returns -1 if the sequence cannot be found for this uniprot ac
     * @param identifier
     * @param sequence
     * @return
     * @throws BridgeFailedException
     */
    public int getSequenceVersion(String identifier, String sequence) throws BridgeFailedException{
        JSONArray array = (JSONArray) getDataFromWebService(buildQuery(Type.ENTRIES, identifier, null));

        if ( log.isDebugEnabled() ) {
            log.debug( "Collecting version(s) for entry by ac: " + identifier );
            log.debug( "Found " + array.size() + " version(s)" );
        }
        JSONObject jo = null;
        for(int i = 0; i < array.size(); ++i){
            jo = (JSONObject) array.get(i);
            String content = (String)jo.get("content");
            if (sequence.equalsIgnoreCase(getSequence(content))){
                return Integer.valueOf(String.valueOf(jo.get("sequence_version")));
            }
        }
        return -1;
    }

    /**
     * Collects all available sequence update fro mUniSave.
     *
     * @param identifier  identifier of the uniprot entry
     * @param sequence    sequence for which we want the subsequent updates
     * @return a non null ordered list. If the given sequence is found in the entry history, this sequence at least is
     *         returned. If there were say, 2 updates since that protein sequence, the list would contain 3 versions.
     *         If we fail to find a match for the given sequence in UniSave, the list would contain all existing
     *         sequence update available.
     *         The list of ordered from the oldest to the most recent sequence.
     *
     * @throws BridgeFailedException if the identifier cannot be found in UniSave.
     */
    public List<SequenceVersion> getAvailableSequenceUpdate( String identifier, String sequence ) throws BridgeFailedException {

        LinkedList<SequenceVersion> sequenceUpdates = new LinkedList<SequenceVersion>();

        // 1. get all versions ordered from the most recent to the oldest
        if ( log.isDebugEnabled() ) {
            log.debug( "Collecting version(s) for entry by  ac: " + identifier );
        }

        JSONArray array = (JSONArray) getDataFromWebService(buildQuery(Type.ENTRIES, identifier, null));
        JSONObject jo = null;
        int parameterSequenceVersion = getSequenceVersion(identifier, sequence);
        int currentSequenceVersion = -1;
        for(int i = 0 ; i < array.size() ; ++i){
            jo = (JSONObject) array.get(i);
            if(parameterSequenceVersion < Integer.parseInt(String.valueOf(jo.get("sequence_version")))){
                if(currentSequenceVersion != Integer.parseInt(String.valueOf(jo.get("sequence_version")))){
                    //New version of the sequence
                    currentSequenceVersion = Integer.parseInt(String.valueOf(jo.get("sequence_version")));
                    FastaSequence fastaSequence = getFastaSequence(identifier, currentSequenceVersion);
                    sequenceUpdates.add(new SequenceVersion(fastaSequence, currentSequenceVersion));
                }
            }
        }
        return sequenceUpdates;
    }

    private enum Type {
        ENTRIES ("/json/entries/"),
        ENTRY_VERSION ("/json/entry/"),
        ENTRIES_INFO ("/json/entryinfos/"),
        ENTRY_INFO_VERSION ("/json/entryinfo/");

        private final String value;

        Type(String value){ this.value = value;}

        private String valueOf(){return this.value;}

    }

    public String fetchSequenceFromVersion(String id, int version) throws BridgeFailedException{
        return getSequenceFor(id, version);
    }

    public int fetchVersionFromSequence(String id, String sequence) throws BridgeFailedException{
        return getSequenceVersion(id, sequence);
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    private HttpClient getHttpClient(){
        if (this.httpClient == null){
            RequestConfig.Builder requestBuilder = RequestConfig.custom()
                    .setConnectTimeout(this.connectionTimeOut * 1000)
                    .setConnectionRequestTimeout(this.socketTimeout * 1000);

            this.httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestBuilder.build())
                    .disableAutomaticRetries()
                    .build();
        }
        return this.httpClient;
    }
}
