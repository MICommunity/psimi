package psidev.psi.mi.jami.bridges.ensembl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.impl.DefaultGene;

import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/08/13
 */
public class EnsemblFetcher
        implements GeneFetcher {


    protected static final Logger log = LoggerFactory.getLogger(EnsemblFetcher.class.getName());


    private final String server = "http://beta.rest.ensembl.org";

    public Gene getGeneByIdentifier(String identifier) throws BridgeFailedException {
        Gene fetchedGene = new DefaultGene(identifier);

        return null;
    }



    public void testA(String identifier) throws Exception {
        String server = "http://beta.rest.ensembl.org";
        String ext = "/xrefs/id/"+identifier+"?";
        URL url = new URL(server + ext);

        URLConnection connection = url.openConnection();
        HttpURLConnection httpConnection = (HttpURLConnection)connection;
        connection.setRequestProperty("Content-Type", "application/json");

        InputStream response = connection.getInputStream();
        int responseCode = httpConnection.getResponseCode();

        if(responseCode != 200) {
            throw new RuntimeException("Response code was not 200. Detected response was "+responseCode);
        }

        String output;
        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            output = builder.toString();
        }
        finally {
            if (reader != null) try {
                reader.close();
            } catch (IOException logOrIgnore) {
                logOrIgnore.printStackTrace();
            }
        }

        System.out.println(output);
    }

    //ENSG00000157764

    public Gene testB(Gene geneFetched , String identifier) throws Exception {
        String queryType = "/lookup/id/";
        URL url = new URL(server + queryType + identifier);

        URLConnection connection = url.openConnection();
        HttpURLConnection httpConnection = (HttpURLConnection)connection;
        connection.setRequestProperty("format","full");
        connection.setRequestProperty("Content-Type", "application/json");

        //log.info(connection.getURL().toString());

        InputStream response = connection.getInputStream();
        int responseCode = httpConnection.getResponseCode();

        if(responseCode != 200) {
            String cause;
            if(responseCode == 400) return null; //Entry could not be found
            else if(responseCode == 404)    cause = "Malformed Request";
            else if(responseCode == 429)	cause = "Too Many Requests";
            else if(responseCode == 503)	cause = "Service Unavailable";
            else cause = "unknown";
            throw new RuntimeException("Bad response code "+responseCode+", cause is "+cause+".");
        }

        String output;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));

            log.info(reader.readLine());
            //output = reader.readLine();



            /*StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            output = builder.toString();  */
        }
        finally {
            if (reader != null) try {
                reader.close();
            } catch (IOException logOrIgnore) {
                logOrIgnore.printStackTrace();
            }
        }
        return null;
        //System.out.println(output);
    }

}
