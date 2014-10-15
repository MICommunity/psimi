package psidev.psi.mi.jami.bridges.ontologymanager.impl.ols;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.bridges.ontologymanager.impl.AbstractMIOntologyAccess;
import psidev.psi.tools.ontology_manager.client.OlsClient;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;

import java.net.URI;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * jami extension for OlsOntology
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/11/11</pre>
 */

public class MIOlsOntology extends AbstractMIOntologyAccess {

    public static final Log log = LogFactory.getLog(MIOlsOntology.class);

    private Date lastOntologyUpload;

    private OlsClient olsClient;

    public MIOlsOntology() throws OntologyLoaderException {
        super();
        // preparing cache
        lastOntologyUpload = new Date(System.currentTimeMillis());
    }

    public MIOlsOntology(OntologyTermFetcher termBuilder) throws OntologyLoaderException {
        super(termBuilder);
        // preparing cache
        lastOntologyUpload = new Date(System.currentTimeMillis());
    }


    public MIOlsOntology(String dbName, String dbIdentifier, Pattern dbRegexp, String parent) throws OntologyLoaderException {
        super(dbName, dbIdentifier, dbRegexp, parent);
        // preparing cache
        lastOntologyUpload = new Date(System.currentTimeMillis());
    }

    public MIOlsOntology(OntologyTermFetcher termBuilder, String dbName, String dbIdentifier, Pattern dbRegexp, String parent) throws OntologyLoaderException {
        super(termBuilder, dbName, dbIdentifier, dbRegexp, parent);
        // preparing cache
        lastOntologyUpload = new Date(System.currentTimeMillis());
    }

    public void loadOntology(String ontologyID, String name, String version, String format, URI uri) throws OntologyLoaderException {
        setOntologyID(ontologyID);
        log.info( "Successfully created OlsOntology from values: ontology=" + ontologyID + " name=" + name
                + " version=" + version + " format=" + format + " location=" + uri );
    }

    public boolean isOntologyUpToDate() throws OntologyLoaderException {
        if (this.olsClient == null){
            // preparing OLS access
            log.info( "Creating new OLS query client." );
            try {
                olsClient = new OlsClient();
            } catch ( Exception e ) {
                log.error( "Exception setting up OLS query client!", e );
                throw new OntologyLoaderException( "Exception setting up OLS query client!", e );
            }
        }

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = olsClient.getOntologyLoadDate(getOntologyID());
            Date lastUpdate = dateFormat.parse(dateString);

            if (lastOntologyUpload.after(lastUpdate)){
                return true;
            }

        } catch (RemoteException e) {
            throw new OntologyLoaderException("We can't access the date of the last ontology update.", e);
        } catch (ParseException e) {
            throw new OntologyLoaderException("The date of the last ontology update cannot be parsed.", e);
        }
        return false;
    }
}
