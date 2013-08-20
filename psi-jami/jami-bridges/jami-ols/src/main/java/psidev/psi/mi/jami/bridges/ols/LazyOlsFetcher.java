package psidev.psi.mi.jami.bridges.ols;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 20/08/13
 */
public class LazyOlsFetcher implements CvTermFetcher<CvTerm> {

    public CvTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName) throws BridgeFailedException {
        if(! ontologyDatabaseName.equals("psi-mi"))
            return null;
        LazyCvTerm cvTerm = new LazyCvTerm(termIdentifier);
        if(cvTerm.getMIIdentifier() == null)
            return null;
        return cvTerm;
    }

    public CvTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase) throws BridgeFailedException {
        if(! ontologyDatabase.getMIIdentifier().equals(CvTerm.PSI_MI_MI))
            return null;
        LazyCvTerm cvTerm = new LazyCvTerm(termIdentifier);
        if(cvTerm.getMIIdentifier() == null)
            return null;
        return cvTerm;
    }

    public CvTerm getCvTermByExactName(String searchName, String ontologyDatabaseName) throws BridgeFailedException {
        if(! ontologyDatabaseName.equals("psi-mi"))
            return null;
        LazyCvTerm cvTerm = new LazyCvTerm(searchName , true);
        if(cvTerm.getMIIdentifier() == null)
            return null;
        return cvTerm;
    }

    public CvTerm getCvTermByExactName(String searchName) throws BridgeFailedException {
        LazyCvTerm cvTerm = new LazyCvTerm(searchName , true);
        if(cvTerm.getMIIdentifier() == null)
            return null;
        return cvTerm;
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
