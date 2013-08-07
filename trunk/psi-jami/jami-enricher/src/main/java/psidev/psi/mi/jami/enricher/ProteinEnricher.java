package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.remapper.ProteinRemapper;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.protein.ProteinEnricherListener;
import psidev.psi.mi.jami.model.Protein;

import java.util.Collection;

/**
 * The Protein enricher is an enricher which can enrich either single protein or a collection.
 * The Protein enricher has one subEnrichers. A protein enricher must be initiated with a protein fetcher.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  16/05/13
 */
public interface ProteinEnricher {

    /**
     * Enrichment of a single Protein.
     * At the end of the enrichment, the listener will be fired
     * @param proteinToEnrich       A protein to enrich
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichProtein(Protein proteinToEnrich) throws EnricherException;

    /**
     * Enrichment of a collection of proteins.
     * @param proteinsToEnrich      The proteins to be enriched
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichProteins(Collection<Protein> proteinsToEnrich) throws EnricherException;


    //====================


    /**
     * Sets the protein fetcher to be used for enrichment.
     * If the fetcher is null, an illegal state exception will be thrown at the the next enrichment.
     * @param fetcher   The fetcher to be used to gather data for enrichment
     */
    public void setProteinFetcher(ProteinFetcher fetcher);

    /**
     * The fetcher to be used for used to collect data.
     * @return  The fetcher which is currently being used for fetching.
     */
    public ProteinFetcher getProteinFetcher();

    /**
     * The proteinEnricherListener to be used.
     * It will be fired at all points where a change is made to the protein
     * @param listener  The listener to use. Can be null.
     */
    public void setProteinEnricherListener(ProteinEnricherListener listener);
    /**
     * The listener which is fired when changes are made to the proteinToEnrich.
     * @return  The current listener. May be null.
     */
    public ProteinEnricherListener getProteinEnricherListener();


    /**
     * The organism enricher which will be used to collect data about the organisms.
     * @param organismEnricher  The organism enricher to be used.
     */
    public void setOrganismEnricher(OrganismEnricher organismEnricher);

    /**
     * The Enricher to use on the protein's organism.
     * @return  The current organism enricher.
     */
    public OrganismEnricher getOrganismEnricher();

    /**
     * The protein mapper to be used when a protein doesn't have a uniprot id or the uniprotID is dead.
     * @param proteinRemapper   The remapper to use.
     */
    public void setProteinRemapper(ProteinRemapper proteinRemapper);

    /**
     * The protein remapper has no default and can be left null
     * @return  The current remapper.
     */
    public ProteinRemapper getProteinRemapper();
}
