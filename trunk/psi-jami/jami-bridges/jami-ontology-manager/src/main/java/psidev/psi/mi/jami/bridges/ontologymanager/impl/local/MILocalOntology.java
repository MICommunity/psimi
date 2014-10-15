package psidev.psi.mi.jami.bridges.ontologymanager.impl.local;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.bridges.obo.OboOntologyTermFetcher;
import psidev.psi.mi.jami.bridges.ontologymanager.impl.AbstractMIOntologyAccess;
import psidev.psi.mi.jami.commons.MIFileUtils;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * JAMI implementation of LocalOntology access
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/11</pre>
 */

public class MILocalOntology extends AbstractMIOntologyAccess {
    public static final Log log = LogFactory.getLog(MILocalOntology.class);

    private String md5Signature;

    private int contentSize = -1;

    private URL fileUrl;

    public MILocalOntology() throws OntologyLoaderException {
        super();
    }

    public MILocalOntology(OntologyTermFetcher termBuilder) throws OntologyLoaderException {
        super(termBuilder);
    }


    public MILocalOntology(String dbName, String dbIdentifier, Pattern dbRegexp, String parent) throws OntologyLoaderException {
        super(dbName, dbIdentifier, dbRegexp, parent);
    }

    public MILocalOntology(OntologyTermFetcher termBuilder, String dbName, String dbIdentifier, Pattern dbRegexp, String parent) throws OntologyLoaderException {
        super(termBuilder, dbName, dbIdentifier, dbRegexp, parent);
    }

    public void loadOntology( String ontologyID, String name, String version, String format, URI uri ) throws OntologyLoaderException {
        setOntologyID(ontologyID);

        // first check the format
        if ( "OBO".equals( format ) ) {
            if ( uri == null ) {
                throw new IllegalArgumentException( "The given CvSource doesn't have a URI" );
            } else {


                // if we have a local file, we don't have to load from a URL
                // to specify a local file with a URI you have to follow the following syntax:
                //    [scheme:][//authority][path][?query][#fragment]
                // where authority, query and fragment are empty! An example:
                //    file:///C:/tmp/psi-mi.obo
                // note:    ^ = empty path     ^ = no query and no fragment

                if ( uri.getScheme().equalsIgnoreCase("file") ) {
                    File file = new File(uri);
                    if ( !file.exists() ) {
                        throw new IllegalArgumentException("Could not find the file for URI: " + uri + " - Perhaps the syntax of the URI is wrong!");
                    }
                    setOntologyTermFetcher(new OboOntologyTermFetcher(getDatabaseName(), file.getAbsolutePath()));
                } else {
                    URL url;
                    try {
                        url = uri.toURL();
                    } catch ( MalformedURLException e ) {
                        throw new IllegalArgumentException( "The given CvSource doesn't have a valid URI: " + uri );
                    }

                    // Compute the MD5 signature of the file to load
                    this.md5Signature = computeMD5SignatureFor(url);

                    // Get the size of the file to load
                    this.contentSize = getSizeOfFile(url);

                    // We need to store the url to know if an update has been done later
                    this.fileUrl = url;

                    try {
                        File tempFile = MIFileUtils.storeAsTemporaryFile(url.openStream(), "jami_ontology_"+System.currentTimeMillis(), ".obo");
                        setOntologyTermFetcher(new OboOntologyTermFetcher(getDatabaseName(), tempFile.getAbsolutePath()));
                        tempFile.delete();

                    } catch (IOException e) {
                        throw new OntologyLoaderException( "OboFile parser failed with Exception: ", e );
                    }
                }
            }
        } else {
            throw new OntologyLoaderException( "Unsupported ontology format: " + format );
        }

        if ( log.isInfoEnabled() ) {
            log.info( "Successfully created LocalOntology from values: ontology="
                    + ontologyID + " name=" + name + " version=" + version + " format=" + format + " location=" + uri );
        }
    }

    public boolean isOntologyUpToDate() throws OntologyLoaderException {
        if (this.fileUrl != null){
            if (md5Signature != null){
                boolean isMd5UpToDate = checkUpToDateMd5Signature();
                boolean isContentSizeUpToDate = checkUpToDateContentSize();

                if (isMd5UpToDate && isContentSizeUpToDate){
                    return true;
                }
                else if (!isContentSizeUpToDate && isMd5UpToDate && this.contentSize == -1){
                    return true;
                }
                else if (isContentSizeUpToDate && !isMd5UpToDate && this.md5Signature == null){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isUseTermSynonyms() {
        return true;
    }

    public void setUseTermSynonyms(boolean useTermSynonyms) {
        throw new UnsupportedOperationException("The MI OBO fetcher always load term synonyms");
    }

    /**
     * Computes the md5 signature of the URL
     * @param url
     * @return
     * @throws OntologyLoaderException
     */
    private String computeMD5SignatureFor(URL url) throws OntologyLoaderException {
        InputStream is = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            is = url.openStream();
            byte[] buffer = new byte[8192];
            int read = 0;

            while( (read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);

            }

            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            String output = bigInt.toString(16);

            return output;

        }
        catch(IOException e) {
            throw new OntologyLoaderException("Unable to process file for MD5", e);
        }
        catch (NoSuchAlgorithmException e) {
            throw new OntologyLoaderException("Unable to compute file MD5 signature for the file " + url.getFile(), e);
        } finally {
            try {
                if (is != null){
                    is.close();
                }
            }
            catch(IOException e) {
                throw new OntologyLoaderException("Unable to close input stream for MD5 calculation", e);
            }
        }

    }

    /**
     * Get the size of a file at a given url
     * @param url
     * @return
     * @throws OntologyLoaderException
     */
    private int getSizeOfFile(URL url) throws OntologyLoaderException {
        URLConnection con = null;

        try {
            con = url.openConnection();
            int size = con.getContentLength();

            return size;
        } catch (IOException e) {
            throw new OntologyLoaderException("Unable to open the url", e);
        }
    }

    /**
     *
     * @return true if the MD5 signature of the file containing the ontologies is still the same
     * @throws OntologyLoaderException
     */
    private boolean checkUpToDateMd5Signature() throws OntologyLoaderException {
        if (md5Signature != null){
            String newMd5Signature = computeMD5SignatureFor(this.fileUrl);

            return md5Signature.equals(newMd5Signature);
        }
        return false;
    }

    /**
     *
     * @return true if the content size of the file containing the ontologies is still the same
     * @throws OntologyLoaderException
     */
    private boolean checkUpToDateContentSize() throws OntologyLoaderException {
        if (this.contentSize != -1){
            int newContentSize = getSizeOfFile(this.fileUrl);
            return newContentSize == this.contentSize;
        }

        return false;
    }
}
