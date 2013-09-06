package psidev.psi.mi.jami.bridges.ols;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
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
public class OlsOntologyTermFetcher extends AbstractOlsFetcher<OntologyTerm> implements OntologyTermFetcher{

    public OlsOntologyTermFetcher() throws BridgeFailedException {
        super();
    }

    @Override
    protected OntologyTerm instantiateCvTerm(String termName, Xref identity, String ontologyName) {
        return new LazyOntologyTerm(queryService, termName, identity, ontologyName);
    }
}
