package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;

import java.util.Set;

/**
 * Finds ontology terms in the Ontology Lookup Service
 * as well as having options to recursively find parents and or children.
 *
 * Extends CvTermFetcher with OntologyTerms.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public interface OntologyTermFetcher extends CvTermFetcher<OntologyTerm>{

    /**
     *
     * @param databaseName : ontology database name
     * @return a set of root terms for the ontology matching this database name
     */
    public Set<OntologyTerm> fetchRootTerms(String databaseName);

    /**
     *
     * @param database : database term for the ontology
     * @return a set of root terms for the ontology matching this database term
     */
    public Set<OntologyTerm> fetchRootTerms(CvTerm database);
}
