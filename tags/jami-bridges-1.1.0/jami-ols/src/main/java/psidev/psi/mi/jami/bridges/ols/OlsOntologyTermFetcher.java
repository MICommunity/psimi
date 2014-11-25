package psidev.psi.mi.jami.bridges.ols;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.rmi.RemoteException;
import java.util.*;

/**
 * Finds ontology terms in the Ontology Lookup Service
 * as well as having options to recursively find parents and or children.
 *
 * Implements CvTermFetcher, returning the results as OntologyTerms.
 * Further to this, the OntologyTermFetcher is implemented to include methods for finding children and parents.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public class OlsOntologyTermFetcher extends AbstractOlsFetcher<OntologyTerm> implements OntologyTermFetcher{

    public OlsOntologyTermFetcher() throws BridgeFailedException {
        super();
    }

    @Override
    protected OntologyTerm instantiateCvTerm(String termName, Xref identity, String ontologyName) {
        return new LazyOntologyTerm(queryService, termName, identity, ontologyName);
    }

    public Set<OntologyTerm> fetchRootTerms(String databaseName) throws BridgeFailedException{
        if(databaseName == null || databaseName.isEmpty())
            throw new IllegalArgumentException("Can not search for a root term of an ontology without its ontology name.");

        String olsOntologyName = null;
        if(dbMap.containsKey(databaseName))
            olsOntologyName = dbMap.get(databaseName);

        try {
            Map roots = this.queryService.getRootTerms(olsOntologyName);
            Set<OntologyTerm> rootTerms = new HashSet<OntologyTerm>();
            for (Object obj : roots.keySet()){
                if (obj != null){
                    rootTerms.add(new LazyOntologyTerm(queryService, null, createXref((String)obj , databaseName), olsOntologyName));
                }
            }
            return rootTerms;
        } catch (RemoteException e) {
            throw new BridgeFailedException("Cannot query for the root term of "+databaseName, e);
        }
    }

    public Set<OntologyTerm> fetchRootTerms(CvTerm database) throws BridgeFailedException {
        if(database == null || database.getShortName().isEmpty())
            throw new IllegalArgumentException("Can not search for a root term of an ontology without its ontology name.");

        String olsOntologyName = null;
        if(dbMap.containsKey(database.getShortName()))
            olsOntologyName = dbMap.get(database.getShortName());

        try {
            Map roots = this.queryService.getRootTerms(olsOntologyName);
            Set<OntologyTerm> rootTerms = new HashSet<OntologyTerm>();
            for (Object obj : roots.keySet()){
                if (obj != null){
                    rootTerms.add(new LazyOntologyTerm(queryService, null, XrefUtils.createIdentityXref(database.getShortName(), database.getMIIdentifier(), (String)obj),
                            olsOntologyName));
                }
            }
            return rootTerms;
        } catch (RemoteException e) {
            throw new BridgeFailedException("Cannot query for the root term of "+database.getShortName(), e);
        }
    }
}
