package psidev.psi.mi.jami.bridges.ols;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.SourceFetcher;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/05/13
 */
public class OlsSourceFetcher extends AbstractOlsFetcher<Source> implements SourceFetcher{


    public OlsSourceFetcher() throws BridgeFailedException {
        super();
    }

    @Override
    protected Source instantiateCvTerm(String termName, Xref identity, String ontologyName) {
        return new LazySource(queryService, termName, identity, ontologyName);
    }

}
