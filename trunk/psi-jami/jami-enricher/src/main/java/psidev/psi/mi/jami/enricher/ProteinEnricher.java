package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.enricherimplementation.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.model.Protein;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 16/05/13
 * Time: 13:03
 */
public interface ProteinEnricher {

    public void enrichProtein(Protein proteinToEnrich)
            throws BridgeFailedException, MissingServiceException, BadToEnrichFormException,
            BadSearchTermException, BadResultException, SeguidException;

    public void setFetcher(ProteinFetcher fetcher);
    public ProteinFetcher getFetcher();

    public void setProteinEnricherListener(ProteinEnricherListener proteinEnricherListener);
    public ProteinEnricherListener getProteinEnricherListener();

}
