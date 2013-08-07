package psidev.psi.mi.jami.enricher.impl.bioactiveentity;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.enricher.BioactiveEntityEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.bioactiveentity.BioactiveEntityEnricherListener;
import psidev.psi.mi.jami.model.BioactiveEntity;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  07/08/13
 */
public abstract class AbstractBioactiveEntityEnricher
        implements BioactiveEntityEnricher{

    public final int RETRY_COUNT = 5;
    private BioactiveEntityFetcher fetcher = null;
    private BioactiveEntityEnricherListener listener = null;
    protected BioactiveEntity fetchedBioactiveEntity = null;

    public AbstractBioactiveEntityEnricher(BioactiveEntityFetcher fetcher){
        setBioactiveEntityFetcher(fetcher);
    }

    /**
     * Enriches a collection of bioactive entities.
     * @param bioactiveEntitiesToEnrich       The entities to be enriched
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichBioactiveEntities(Collection<BioactiveEntity> bioactiveEntitiesToEnrich) throws EnricherException {
        for(BioactiveEntity bioactiveEntityToEnrich : bioactiveEntitiesToEnrich){
            enrichBioactiveEntity(bioactiveEntityToEnrich);
        }
    }

    /**
     * Enriches a single bioactive entity.
     * @param bioactiveEntityToEnrich   The entity to be enriched.
     * @throws EnricherException        Thrown if problems are encountered in the fetcher
     */
    public void enrichBioactiveEntity(BioactiveEntity bioactiveEntityToEnrich) throws EnricherException {
        if(bioactiveEntityToEnrich == null)
            throw new IllegalArgumentException("Can not enrich null Bioactive Entity");

        fetchedBioactiveEntity = fetchBioactiveEntity(bioactiveEntityToEnrich);

        if(fetchedBioactiveEntity == null){
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onEnrichmentComplete(
                        bioactiveEntityToEnrich , EnrichmentStatus.FAILED ,
                        "Could not fetch a bioactiveEntity.");
            return;
        }

        processBioactiveEntity(bioactiveEntityToEnrich);


        if(getBioactiveEntityEnricherListener() != null)
            getBioactiveEntityEnricherListener().onEnrichmentComplete(
                    bioactiveEntityToEnrich , EnrichmentStatus.SUCCESS , null);
    }

    protected abstract void processBioactiveEntity(BioactiveEntity bioactiveEntityToEnrich);


    private BioactiveEntity fetchBioactiveEntity(BioactiveEntity bioactiveEntityToEnrich) throws EnricherException {
        if(getBioactiveEntityFetcher() == null)
            throw new IllegalStateException("Can not fetch with null fetcher");

        BioactiveEntity fetchedBioactiveEntity = null;

        try {
            fetchedBioactiveEntity = getBioactiveEntityFetcher().getBioactiveEntityByIdentifier(
                    bioactiveEntityToEnrich.getChebi());
            if(fetchedBioactiveEntity != null) return fetchedBioactiveEntity;
        } catch (BridgeFailedException e) {
            int index = 0;
            while(index < RETRY_COUNT){
                try {
                    fetchedBioactiveEntity = getBioactiveEntityFetcher().getBioactiveEntityByIdentifier(
                            bioactiveEntityToEnrich.getChebi());
                    if(fetchedBioactiveEntity != null) return fetchedBioactiveEntity;
                } catch (BridgeFailedException ee) {
                    ee.printStackTrace();
                }
                index++;
            }
            throw new EnricherException("Retried "+RETRY_COUNT+" times", e);
        }


        return fetchedBioactiveEntity;
    }


    /**
     * Sets the bioactiveEntity fetcher to be used for enrichment.
     * If the fetcher is null, an illegal state exception will be thrown at the the next enrichment.
     * @param fetcher   The fetcher to be used to gather data for enrichment
     */
    public void setBioactiveEntityFetcher(BioactiveEntityFetcher fetcher) {
        this.fetcher = fetcher;
    }

    /**
     * Returns the current fetcher which is being used to collect information about entities for enrichment.
     * @return  The current fetcher.
     */
    public BioactiveEntityFetcher getBioactiveEntityFetcher() {
        return fetcher;
    }

    /**
     * Sets the listener to use when the bioactiveEntity has been changed
     * @param listener  The new listener. Can be null.
     */
    public void setBioactiveEntityEnricherListener(BioactiveEntityEnricherListener listener) {
        this.listener = listener;
    }

    /**
     * The current listener of changes to the bioactiveEntities.
     * @return  The current listener. Can be null.
     */
    public BioactiveEntityEnricherListener getBioactiveEntityEnricherListener(){
        return listener;
    }
}
