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

    //============

    public Collection<OntologyTerm> findAllParentsOfDeepestChildren(OntologyTerm ontologyTerm)
            throws BridgeFailedException;

    public Collection<OntologyTerm> findAllParentsOfDeepestChildren(OntologyTerm ontologyTerm , Xref identifier)
            throws BridgeFailedException;




    //===========

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName, boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;

    public OntologyTerm getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase, boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;

    public OntologyTerm getCvTermByExactName(String searchName, String ontologyDatabaseName,  boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;

    public OntologyTerm getCvTermByExactName(String searchName , boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;

    //===========

    public Collection<OntologyTerm> getCvTermsByIdentifiersWithOntologyNames(HashMap<String,String> identifiers,  boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;

    public Collection<OntologyTerm> getCvTermsByIdentifiersWithOntologyCvTerms(HashMap<String,CvTerm> identifiers,  boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;

    public Collection<OntologyTerm> getCvTermsByExactNames(HashMap<String,String> termNames,  boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;

    public Collection<OntologyTerm> getCvTermsByExactNames(Collection<String> searchNames,  boolean fetchChildren, boolean fetchParents)
            throws BridgeFailedException;
}
