package psidev.psi.mi.jami.bridges.ensembl;

import net.sf.json.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.impl.DefaultGene;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/08/13
 */
public class EnsemblFetcher
        implements GeneFetcher {

    protected static final Logger log = LoggerFactory.getLogger(EnsemblFetcher.class.getName());

    /**
     * Queries for the gene in Ensembl, then repeats the query on Ensembl Genomes if the entry was not found.
     * @param identifier    An Ensembl or Ensembl Genomes identifier.
     * @return              A complete gene record. Null if the identifier can not be found.
     * @throws BridgeFailedException
     */
    public Gene getGeneByIdentifierOfUnknownType(String identifier) throws BridgeFailedException {
        Gene gene = getGeneByEnsemblIdentifier(identifier);
        if(gene == null)
            gene = getGeneByEnsemblGenomesIdentifier(identifier);
        return gene;
    }

    public Gene getGeneByEnsemblIdentifier(String identifier) throws BridgeFailedException {
        final String server = "http://beta.rest.ensembl.org";
        Gene gene = new DefaultGene(identifier);
        gene.setEnsembl(identifier);
        gene = fetchID(identifier , gene , server);
        if(gene == null) return null;
        gene = fetchXrefs(identifier , gene , server);
        return gene;
    }

    public Gene getGeneByEnsemblGenomesIdentifier(String identifier) throws BridgeFailedException {
        final String server = "http://beta.rest.ensemblgenomes.org/";
        Gene gene = new DefaultGene(identifier);
        gene.setEnsemblGenome(identifier);
        gene = fetchID(identifier , gene , server);
        if(gene == null) return null;
        gene = fetchXrefs(identifier , gene , server);
        return gene;
    }

    /**
     * Makes a query to the address provided. Assumes that the protocol is that of Ensembl and Ensembl Genomes.
     * @param identifier    The identifier to query for.
     * @param geneFetched   The fetched gene to enrich.
     * @param server        The REST server to query against.
     * @return              The completed gene record. Null if it could not be found.
     * @throws BridgeFailedException
     */
    private Gene fetchID(String identifier , Gene geneFetched , String server) throws BridgeFailedException {
        try{
            String queryType = "/lookup/id/";
            URL url = new URL(server + queryType + identifier);

            URLConnection connection = url.openConnection();
            connection.setRequestProperty("format","full");
            connection.setRequestProperty("Content-Type", "application/json");
            HttpURLConnection httpConnection = (HttpURLConnection)connection;

            int responseCode = httpConnection.getResponseCode();
            if(responseCode != 200) {
                String cause;
                if(responseCode == 400) return null; //Entry could not be found
                else if(responseCode == 404)    cause = "Malformed Request";
                else if(responseCode == 429)	cause = "Too Many Requests";
                else if(responseCode == 503)	cause = "Service Unavailable";
                else cause = "unknown";
                throw new BridgeFailedException("Bad response code "+responseCode+", cause is "+cause+".");
            }

            InputStream inputStream = connection.getInputStream();

            String jsonTxt = IOUtils.toString(inputStream);
            log.info(jsonTxt);
            JSONObject jsonObj = (JSONObject) JSONSerializer.toJSON( jsonTxt );

            //TODO integrate the returned information into the fetched gene
            // System.out.println(jsonObj.getString("dbname"));
            // System.out.println(jsonObj.getString("primary_id"));

        } catch (MalformedURLException e) {
            throw new BridgeFailedException(e);
        } catch (IOException e) {
            throw new BridgeFailedException(e);
        }
        return geneFetched;
    }

    private Gene fetchXrefs(String identifier , Gene geneFetched , String server) throws BridgeFailedException {
        try{
            String queryType = "/xrefs/id/";
            URL url = new URL(server + queryType + identifier);

            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            HttpURLConnection httpConnection = (HttpURLConnection)connection;

            int responseCode = httpConnection.getResponseCode();

            if(responseCode != 200) {
                String cause;
                if(responseCode == 400) return null; //Entry could not be found
                else if(responseCode == 404)    cause = "Malformed Request";
                else if(responseCode == 429)	cause = "Too Many Requests";
                else if(responseCode == 503)	cause = "Service Unavailable";
                else cause = "unknown";
                throw new BridgeFailedException("Bad response code "+responseCode+", cause is "+cause+".");
            }

            InputStream inputStream = connection.getInputStream();

            String jsonTxt = IOUtils.toString(inputStream);
            log.info(jsonTxt);
            JSONArray json = (JSONArray) JSONSerializer.toJSON( jsonTxt );


            for(Object obj : json){
                JSONObject JSONobj = (JSONObject)obj;
                if(JSONobj.getString("dbname").equalsIgnoreCase("EntrezGene"))
                    geneFetched.setEntrezGeneId(JSONobj.getString("primary_id"));
                else if(JSONobj.getString("dbname").equalsIgnoreCase("Refseq"))
                    geneFetched.setRefseq(JSONobj.getString("primary_id"));
            }
        } catch (MalformedURLException e) {
            throw new BridgeFailedException(e);
        } catch (IOException e) {
            throw new BridgeFailedException(e);
        }
        return geneFetched;
    }
}
