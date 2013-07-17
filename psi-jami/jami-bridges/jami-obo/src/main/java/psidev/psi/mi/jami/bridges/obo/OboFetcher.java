package psidev.psi.mi.jami.bridges.obo;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Collection;

/**
 * Simple ftecher based on OBO files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/07/13</pre>
 */

public class OboFetcher extends OboFetcherTemplate<CvTerm> implements CvTermFetcher<CvTerm>{

    public OboFetcher(CvTerm database, AbstractOboLoader<CvTerm> oboLoader, String filePath) {
        super(database, oboLoader, filePath);
    }

    public OboFetcher(String databaseName, AbstractOboLoader<CvTerm> oboLoader, String filePath) {
        super(databaseName, oboLoader, filePath);
    }

    public CvTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CvTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CvTerm getCvTermByExactName(String searchName, String ontologyDatabaseName) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CvTerm getCvTermByExactName(String searchName) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<CvTerm> getCvTermByInexactName(String searchName, String databaseName) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<CvTerm> getCvTermByInexactName(String searchName, CvTerm database) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<CvTerm> getCvTermsByIdentifiers(Collection<String> termIdentifiers, String ontologyDatabaseName) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<CvTerm> getCvTermsByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<CvTerm> getCvTermsByExactNames(Collection<String> searchNames, String ontologyDatabaseName) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<CvTerm> getCvTermsByExactNames(Collection<String> searchNames) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
