package psidev.psi.mi.jami.bridges.ontologymanager.impl.local;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.bridges.obo.OboOntologyTermFetcher;
import psidev.psi.mi.jami.bridges.ontologymanager.MIOntologyAccess;
import psidev.psi.mi.jami.bridges.ontologymanager.MIOntologyTermI;
import psidev.psi.mi.jami.bridges.ontologymanager.impl.OntologyTermWrapper;
import psidev.psi.mi.jami.commons.MIFileUtils;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.tools.ontology_manager.impl.local.AbstractLocalOntology;
import psidev.psi.tools.ontology_manager.impl.local.AbstractOboLoader;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.ontology_manager.impl.local.OntologyTemplate;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
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

public class MILocalOntology extends AbstractLocalOntology<MIOntologyTermI, OntologyTemplate<MIOntologyTermI>, AbstractOboLoader<MIOntologyTermI, OntologyTemplate<MIOntologyTermI>>>
        implements MIOntologyAccess {

    private OboOntologyTermFetcher termFetcher;

    private String databaseName;
    private String databaseIdentifier;
    private Pattern databaseRegexp;
    private String parentIdentifier;

    private Collection<MIOntologyTermI> roots=null;

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
    @Override
    protected AbstractOboLoader<MIOntologyTermI, OntologyTemplate<MIOntologyTermI>> createNewOBOLoader(File ontologyDirectory) throws OntologyLoaderException {
        return null;
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

        for ( Iterator<OntologyTerm> iterator = termFetcher.getRoots().iterator(); iterator.hasNext(); ) {
            OntologyTerm ontologyTerm = iterator.next();

            roots.add( new OntologyTermWrapper(ontologyTerm));
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
            e.printStackTrace();
        }
        return null;
    }

    public boolean isObsolete( MIOntologyTermI term ) {
        return term.getObsoleteMessage() != null;
    }

    public Set<MIOntologyTermI> getDirectParents( MIOntologyTermI term ) {
        return term.getDelegate().getParents();
    }

    public Set<MIOntologyTermI> getDirectChildren( MIOntologyTermI term ) {
        return ontology.getDirectChildren( term );
    }

    public Set<MIOntologyTermI> getAllParents( MIOntologyTermI term ) {
        return ontology.getAllParents( term );
    }

    public Set<MIOntologyTermI> getAllChildren( MIOntologyTermI term ) {
        return ontology.getAllChildren( term );
    }
}
