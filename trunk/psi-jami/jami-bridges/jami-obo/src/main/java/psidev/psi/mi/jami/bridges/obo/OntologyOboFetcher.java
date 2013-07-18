package psidev.psi.mi.jami.bridges.obo;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;

import java.util.Collection;

/**
 * The ontology fetcher based on OBO file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/07/13</pre>
 */

public class OntologyOboFetcher extends OboFetcherTemplate<OntologyTerm> implements OntologyTermFetcher{
    public OntologyOboFetcher(CvTerm database, String filePath) {
        super(database, new OntologyOboLoader(database), filePath);
    }

    public OntologyOboFetcher(String databaseName, String filePath) {
        super(databaseName, new OntologyOboLoader(databaseName), filePath);
    }

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return getCvTermByIdentifier(termIdentifier, ontologyDatabaseName);
    }

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return getCvTermByIdentifier(termIdentifier, ontologyDatabase);
    }

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return getCvTermByExactName(searchName, ontologyDatabaseName);
    }

    public OntologyTerm getCvTermByExactName(String searchName, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return getCvTermByExactName(searchName);
    }

    public Collection<OntologyTerm> getCvTermsByIdentifiers(Collection<String> termIdentifiers, String ontologyDatabaseName, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return getCvTermsByIdentifiers(termIdentifiers, ontologyDatabaseName);
    }

    public Collection<OntologyTerm> getCvTermsByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return getCvTermsByIdentifiers(termIdentifiers, ontologyDatabase);
    }

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames, String ontologyDatabaseName, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return getCvTermsByExactNames(searchNames, ontologyDatabaseName);
    }

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return getCvTermsByExactNames(searchNames);
    }
}
