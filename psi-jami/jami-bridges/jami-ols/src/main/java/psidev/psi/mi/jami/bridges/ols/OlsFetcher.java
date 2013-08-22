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
public class OlsFetcher extends AbstractOlsFetcher<CvTerm>{


    public OlsFetcher() throws BridgeFailedException {
        super();
    }

    @Override
    protected CvTerm instantiateCvTerm(String termName, Xref identity) {
        return new LazyCvTerm(queryService, termName, identity);
    }

}
