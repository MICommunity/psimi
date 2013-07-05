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

    /**
     * Finds an ontologyTerm using a termIdentifier and an ontology database name,
     * and will then find its parents and children to the indicated depth.
     * <p>
     * If c
     *
     * @param termIdentifier        The identifier for the CvTerm to fetch.
     * @param ontologyDatabaseName  The name of the ontology to search for. Eg, psi-mi, psi-mod, go. Must not be Null.
     * @param childrenDepth         Flag to note the depth of children that should be found.
     *                              0 if no children should be found, -1 if the depth should be infinite
     * @param parentDepth           Flag to note the depth of parents that should be found.
     *                              0 if no parents should be found, -1 if the depth should be infinite
     * @return          A completed term or null if no term could be found.
     * @throws BridgeFailedException
     */
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
