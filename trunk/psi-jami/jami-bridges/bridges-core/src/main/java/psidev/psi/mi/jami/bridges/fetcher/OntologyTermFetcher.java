package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public interface OntologyTermFetcher extends CvTermFetcher<OntologyTerm>{

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName, boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName)
            throws BridgeFailedException;

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase, boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase)
            throws BridgeFailedException;

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName,  boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName)
            throws BridgeFailedException;

    public OntologyTerm getCvTermByExactName(String searchName , boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;

    public OntologyTerm getCvTermByExactName(String searchName)
            throws BridgeFailedException;


    // Inexact names require choices to be made - probably can not be reliably used in an ontology term search
    public Collection<OntologyTerm> getCvTermByInexactName(String searchName, String databaseName)
            throws BridgeFailedException;

    public Collection<OntologyTerm> getCvTermByInexactName(String searchName, CvTerm database)
            throws BridgeFailedException;




    public Collection<OntologyTerm> getCvTermsByIdentifiersWithOntologyNames(HashMap<String,String> identifiers,  boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;

    public Collection<OntologyTerm> getCvTermsByIdentifiersWithOntologyNames(HashMap<String,String> identifiers)
            throws BridgeFailedException;

    public Collection<OntologyTerm> getCvTermsByIdentifiersWithOntologyCvTerms(HashMap<String,CvTerm> identifiers,  boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;

    public Collection<OntologyTerm> getCvTermsByIdentifiersWithOntologyCvTerms(HashMap<String,CvTerm> identifiers)
            throws BridgeFailedException;


    public Collection<OntologyTerm> getCvTermsByExactNames(HashMap<String,String> termNames,  boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;

    public Collection<OntologyTerm> getCvTermsByExactNames(HashMap<String,String> termNames)
            throws BridgeFailedException;


    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames,  boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames)
            throws BridgeFailedException;
}
