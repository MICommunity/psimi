package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.remapper.ProteinRemapper;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.model.Protein;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 16/05/13
 */
public interface ProteinEnricher {

    /**
     *
     *
     * @param proteinToEnrich
     * @throws BridgeFailedException
     * @throws MissingServiceException
     * @throws BadToEnrichFormException
     * @throws BadSearchTermException
     * @throws BadResultException
     * @throws SeguidException
     */
    public boolean enrichProtein(Protein proteinToEnrich)
            throws BridgeFailedException, MissingServiceException, BadToEnrichFormException,
            BadSearchTermException, BadResultException, SeguidException, BadEnrichedFormException;


    /**
     *
     * @param fetcher
     */
    public void setFetcher(ProteinFetcher fetcher);
    /**
     *
     * @return
     */
    public ProteinFetcher getFetcher();

    /**
     *
     * @param listener
     */
    public void setProteinEnricherListener(ProteinEnricherListener listener);
    /**
     * The listener which is fired when changes are made to the proteinToEnrich.
     * @return
     */
    public ProteinEnricherListener getProteinEnricherListener();


    /**
     * Sets the Enricher to use on the protein's organism.
     *
     * @param organismEnricher
     */
    public void setOrganismEnricher(OrganismEnricher organismEnricher);

    /**
     * The Enricher to use on the protein's organism.
     * If the enricher has not been set, a new organismEnricher will be created
     * at the same depth as the proteinEnricher which called it.
     * The default organismEnricher has a mockOrganismFetcher which can be set
     * with the Organism found in the fetched protein.
     * @return
     */
    public OrganismEnricher getOrganismEnricher();

    /**
     * The protein mapper to be used when a protein doesn't hav e a uniprot id or the uniprotID is dead.
     * @param proteinRemapper
     */
    public void setProteinRemapper(ProteinRemapper proteinRemapper);

    /**
     *
     * @return
     */
    public ProteinRemapper getProteinRemapper();
}
