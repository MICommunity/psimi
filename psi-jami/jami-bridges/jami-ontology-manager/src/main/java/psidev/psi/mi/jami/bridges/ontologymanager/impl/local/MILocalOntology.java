package psidev.psi.mi.jami.bridges.ontologymanager.impl.local;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.bridges.obo.OboOntologyTermFetcher;
import psidev.psi.mi.jami.bridges.ontologymanager.MIOntologyAccess;
import psidev.psi.mi.jami.bridges.ontologymanager.MIOntologyTermI;
import psidev.psi.mi.jami.bridges.ontologymanager.impl.OntologyTermWrapper;
import psidev.psi.mi.jami.commons.MIFileUtils;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.tools.ontology_manager.impl.local.*;

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
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * JAMI implementation of LocalOntology access
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/11</pre>
 */

public class MILocalOntology implements MIOntologyAccess {
    public static final Log log = LogFactory.getLog(MILocalOntology.class);

    private OboOntologyTermFetcher termFetcher;

    private String databaseName;
    private String databaseIdentifier;
    private Pattern databaseRegexp;
    private String parentIdentifier;

    private Collection<MIOntologyTermI> roots=null;

    private String md5Signature;

    private int contentSize = -1;

    private URL fileUrl;
    private String ontologyID;

    public MILocalOntology() throws OntologyLoaderException {
        super();
        this.databaseName = null;
        this.databaseIdentifier=null;
        this.databaseRegexp=null;
        this.parentIdentifier=null;
    }

    public MILocalOntology(OboOntologyTermFetcher termBuilder) throws OntologyLoaderException {
        super();
        if (termBuilder == null){
            throw new IllegalArgumentException("The OntologyTermWrapper fetcher must be non null");
        }
        this.termFetcher = termBuilder;
        this.databaseName = null;
        this.databaseIdentifier=null;
        this.databaseRegexp=null;
        this.parentIdentifier=null;
    }


    public MILocalOntology(String dbName, String dbIdentifier, Pattern dbRegexp, String parent) throws OntologyLoaderException {
        super();
        this.databaseName = dbName;
        this.databaseIdentifier=dbIdentifier;
        this.databaseRegexp=dbRegexp;
        this.parentIdentifier=parent;
    }

    public MILocalOntology(OboOntologyTermFetcher termBuilder, String dbName, String dbIdentifier, Pattern dbRegexp, String parent) throws OntologyLoaderException {
        super();
        if (termBuilder == null){
            throw new IllegalArgumentException("The OntologyTermWrapper fetcher must be non null");
        }
        this.termFetcher = termBuilder;
        this.databaseName = dbName;
        this.databaseIdentifier=dbIdentifier;
        this.databaseRegexp=dbRegexp;
        this.parentIdentifier=parent;
    }

    public void loadOntology( String ontologyID, String name, String version, String format, URI uri ) throws OntologyLoaderException {
        this.ontologyID = ontologyID;

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
                    this.termFetcher = new OboOntologyTermFetcher(this.databaseName, file.getAbsolutePath());
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
                        this.termFetcher = new OboOntologyTermFetcher(this.databaseName, tempFile.getAbsolutePath());
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

    public void setOntologyDirectory(File directory) {
         // nothing to do
    }

    public Set<MIOntologyTermI> getValidTerms(String accession, boolean allowChildren, boolean useTerm) {
        Set<MIOntologyTermI> collectedTerms = new HashSet<MIOntologyTermI>();

        final MIOntologyTermI term = getTermForAccession( accession );
        if ( term != null ) {
            if ( useTerm ) {
                collectedTerms.add( term );
            }

            if ( allowChildren ) {
                collectedTerms.addAll( getAllChildren( term ) );
            }
        }

        return collectedTerms;
    }

    public String getOntologyID() {
        return this.ontologyID;
    }

    public String getDatabaseIdentifier() {
        return this.databaseIdentifier;
    }

    public String getParentFromOtherOntology() {
        return this.parentIdentifier;
    }

    /**
     * Get the Root terms of the ontology. The way to get it is as follow: pick a term at random, and go to his highest
     * parent.
     *
     * @return a collection of Root term.
     */
    public Collection<MIOntologyTermI> getRootTerms() {

        if ( roots != null ) {
            return roots;
        }

        // it wasn't precalculated, then do it here...
        roots = new HashSet<MIOntologyTermI>();

        try {
            for ( Iterator<OntologyTerm> iterator = termFetcher.fetchRootTerms(this.databaseName).iterator(); iterator.hasNext(); ) {
                OntologyTerm ontologyTerm = iterator.next();

                roots.add( new OntologyTermWrapper(ontologyTerm));
            }
        } catch (BridgeFailedException e) {
            if ( log.isWarnEnabled() ) {
                log.warn( "Error while loading root term from OBO file/URL for ontology: " + ontologyID, e );
            }
        }

        return roots;
    }

    public Pattern getDatabaseRegexp() {
        return this.databaseRegexp;
    }

    public OntologyTermFetcher getOntologyTermFetcher() {
        return this.termFetcher;
    }

    public MIOntologyTermI getTermForAccession( String accession ) {
        OntologyTerm cv = null;
        try {
            cv = termFetcher.fetchByIdentifier( accession, this.databaseName );
            return cv != null ? new OntologyTermWrapper(cv):null;
        } catch (BridgeFailedException e) {
            if ( log.isWarnEnabled() ) {
                log.warn( "Error while loading term "+accession+" from OBO file/ur for ontology: " + ontologyID, e );
            }
        }
        return null;
    }

    public boolean isObsolete( MIOntologyTermI term ) {
        return term.getObsoleteMessage() != null;
    }

    public Set<MIOntologyTermI> getDirectParents(MIOntologyTermI term) {
        Collection<OntologyTerm> parents = term.getDelegate().getParents();
        Set<MIOntologyTermI> directParents = new HashSet<MIOntologyTermI>(parents.size());
        for (OntologyTerm parent : parents){
            directParents.add(new OntologyTermWrapper(parent));
        }
        return directParents;
    }

    public Set<MIOntologyTermI> getDirectChildren(MIOntologyTermI term) {
        Collection<OntologyTerm> children = term.getDelegate().getChildren();
        Set<MIOntologyTermI> directChildren = new HashSet<MIOntologyTermI>(children.size());
        for (OntologyTerm child : children){
            directChildren.add(new OntologyTermWrapper(child));
        }
        return directChildren;
    }

    public Set<MIOntologyTermI> getAllParents(MIOntologyTermI term) {
        Set<MIOntologyTermI> allParents = getDirectParents(term);
        Set<MIOntologyTermI> allParentsClone = new HashSet<MIOntologyTermI>(allParents);
        for (MIOntologyTermI termParent : allParentsClone){
            allParents.addAll(getDirectParents(termParent));
        }
        return allParents;
    }

    public Set<MIOntologyTermI> getAllChildren(MIOntologyTermI term) {
        Set<MIOntologyTermI> allChildren = getDirectChildren(term);
        Set<MIOntologyTermI> allChildrenClone = new HashSet<MIOntologyTermI>(allChildren);
        for (MIOntologyTermI termChild : allChildrenClone){
            allChildren.addAll(getDirectChildren(termChild));
        }
        return allChildren;
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
