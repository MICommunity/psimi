package psidev.psi.mi.jami.bridges.ols;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.model.Xref;

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
public class OntologyOlsFetcher extends AbstractOlsFetcher<OntologyTerm> {

    public OntologyOlsFetcher() throws BridgeFailedException {
        super();
    }

    @Override
    protected OntologyTerm instantiateCvTerm(String termName, Xref identity) {
        return new LazyOntologyTerm(queryService, termName, identity);
    }
}
