package psidev.psi.mi.jami.bridges.ols;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/05/13
 */
public class OlsCvTermFetcher extends AbstractOlsFetcher<CvTerm>{


    public OlsCvTermFetcher() throws BridgeFailedException {
        super();
    }

    @Override
    protected CvTerm instantiateCvTerm(String termName, Xref identity, String ontologyName) {
        return new LazyCvTerm(queryService, termName, identity, ontologyName);
    }

}
