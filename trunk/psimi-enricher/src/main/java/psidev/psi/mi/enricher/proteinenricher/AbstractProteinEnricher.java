package psidev.psi.mi.enricher.proteinenricher;

import psidev.psi.mi.fetcher.exception.BridgeFailedException;
import psidev.psi.mi.fetcher.uniprot.UniprotFetcher;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 14:27
 */
public abstract class AbstractProteinEnricher {

    private ProteinFetcher fetcher;

    public void AbstractProteinEnricher() throws BridgeFailedException {
        fetcher = new UniprotFetcher();
    }

    protected Protein getEnrichedForm(Protein MasterProtein) throws BridgeFailedException {
        return fetcher.getProteinByID(MasterProtein.getUniprotkb());

        //return null;
    }
}
