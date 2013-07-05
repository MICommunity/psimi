package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.model.Xref;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public interface OntologyTermFetcher extends CvTermFetcher<OntologyTerm>{

    //===========

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName, int childrenDepth, int parentDepth)
            throws BridgeFailedException;

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase, int childrenDepth, int parentDepth)
            throws BridgeFailedException;

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName, int childrenDepth, int parentDepth)
            throws BridgeFailedException;

    public OntologyTerm getCvTermByExactName(String searchName , int childrenDepth, int parentDepth)
            throws BridgeFailedException;

    //===========

    public Collection<OntologyTerm> getCvTermsByIdentifiers(Collection<String> identifiers, String ontologyDatabaseName, int childrenDepth, int parentDepth)
            throws BridgeFailedException;

    public Collection<OntologyTerm> getCvTermsByIdentifiers(Collection<String> identifiers, CvTerm ontologyDatabase, int childrenDepth, int parentDepth)
            throws BridgeFailedException;

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames, String ontologyDatabaseName,  int childrenDepth, int parentDepth)
            throws BridgeFailedException;

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames,  int childrenDepth, int parentDepth)
            throws BridgeFailedException;
}
