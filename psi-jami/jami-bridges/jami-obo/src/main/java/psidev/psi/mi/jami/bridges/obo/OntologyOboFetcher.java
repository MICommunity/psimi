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
    public OntologyOboFetcher(CvTerm database, AbstractOboLoader<OntologyTerm> oboLoader, String filePath) {
        super(database, oboLoader, filePath);
    }

    public OntologyOboFetcher(String databaseName, AbstractOboLoader<OntologyTerm> oboLoader, String filePath) {
        super(databaseName, oboLoader, filePath);
    }

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public OntologyTerm getCvTermByExactName(String searchName, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<OntologyTerm> getCvTermsByIdentifiers(Collection<String> termIdentifiers, String ontologyDatabaseName, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<OntologyTerm> getCvTermsByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames, String ontologyDatabaseName, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames, int childrenDepth, int parentDepth) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
