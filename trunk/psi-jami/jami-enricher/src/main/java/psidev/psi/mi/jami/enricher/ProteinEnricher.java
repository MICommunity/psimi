package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.remapper.ProteinRemapper;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.model.Protein;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 16/05/13
 */
public interface ProteinEnricher {

    /**
     * Takes the provided protein, finds additional information and includes it in the object.
     * @param proteinToEnrich
     */
    public void enrichProtein(Protein proteinToEnrich) throws EnricherException;
    public void enrichProteins(Collection<Protein> proteinsToEnrich) throws EnricherException;


    //====================


    /**
     *
     * @param fetcher
     */
    public void setProteinFetcher(ProteinFetcher fetcher);
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
     * The protein mapper to be used when a protein doesn't have a uniprot id or the uniprotID is dead.
     * @param proteinRemapper
     */
    public void setProteinRemapper(ProteinRemapper proteinRemapper);

    /**
     * The protein remapper has no default and can be left null
     * @return
     */
    public ProteinRemapper getProteinRemapper();
}
