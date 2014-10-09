package psidev.psi.mi.jami.bridges.ontologymanager.impl.ols;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.bridges.ols.OlsOntologyTermFetcher;
import psidev.psi.mi.jami.bridges.ontologymanager.MIOntologyAccess;
import psidev.psi.mi.jami.bridges.ontologymanager.MIOntologyTermI;
import psidev.psi.mi.jami.bridges.ontologymanager.impl.OntologyTermWrapper;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.ontology_manager.impl.ols.AbstractOlsOntology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * jami extension for OlsOntology
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/11/11</pre>
 */

public class MIOlsOntology extends AbstractOlsOntology<MIOntologyTermI> implements MIOntologyAccess {

    public static final Log log = LogFactory.getLog(MIOlsOntology.class);

    private OlsOntologyTermFetcher termFetcher;

    private String databaseName;
    private String databaseIdentifier;
    private Pattern databaseRegexp;
    private String parentIdentifier;

    public MIOlsOntology() throws OntologyLoaderException {
        super();
        // we don't need to load the synonyms, we will process them in another fashion
        this.useTermSynonyms = false;
        try {
            this.termFetcher = new OlsOntologyTermFetcher();
        } catch (BridgeFailedException e) {
            throw new OntologyLoaderException("Impossible to create a new OLS fetcher", e);
        }
        this.databaseName = null;
        this.databaseIdentifier=null;
        this.databaseRegexp=null;
        this.parentIdentifier=null;
    }

    public MIOlsOntology(OlsOntologyTermFetcher termBuilder) throws OntologyLoaderException {
        super();
        // we don't need to load the synonyms, we will process them in another fashion
        this.useTermSynonyms = false;
        if (termBuilder == null){
            throw new IllegalArgumentException("The OntologyTermWrapper fetcher must be non null");
        }
        this.termFetcher = termBuilder;
        this.databaseName = null;
        this.databaseIdentifier=null;
        this.databaseRegexp=null;
        this.parentIdentifier=null;
    }


    public MIOlsOntology(String dbName, String dbIdentifier, Pattern dbRegexp, String parent) throws OntologyLoaderException {
        super();
        // we don't need to load the synonyms, we will process them in another fashion
        this.useTermSynonyms = false;
        try {
            this.termFetcher = new OlsOntologyTermFetcher();
        } catch (BridgeFailedException e) {
            throw new OntologyLoaderException("Impossible to create a new OLS fetcher", e);
        }
        this.databaseName = dbName;
        this.databaseIdentifier=dbIdentifier;
        this.databaseRegexp=dbRegexp;
        this.parentIdentifier=parent;
    }

    public MIOlsOntology(OlsOntologyTermFetcher termBuilder, String dbName, String dbIdentifier, Pattern dbRegexp, String parent) throws OntologyLoaderException {
        super();
        // we don't need to load the synonyms, we will process them in another fashion
        this.useTermSynonyms = false;
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
    protected MIOntologyTermI createNewOntologyTerm(String identifier, String name){
        try {
            if (identifier != null){
                OntologyTerm fetched = this.termFetcher.fetchByIdentifier(identifier, this.databaseName != null ? this.databaseName : name);
                return fetched != null ? new OntologyTermWrapper(fetched) : null;
            }
        } catch (BridgeFailedException e) {
            e.printStackTrace();
        }
        return null;
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

    public Collection<MIOntologyTermI> getRootTerms() {
        Collection<MIOntologyTermI> roots = new ArrayList<MIOntologyTermI>(rootAccs.size());

        for (String acc : rootAccs){
            MIOntologyTermI term = getTermForAccession(acc);

            if (term != null){
                roots.add(term);
            }
        }
        return roots;
    }

    public Pattern getDatabaseRegexp() {
        return this.databaseRegexp;
    }

    public OntologyTermFetcher getOntologyTermFetcher() {
        return termFetcher;
    }
}
