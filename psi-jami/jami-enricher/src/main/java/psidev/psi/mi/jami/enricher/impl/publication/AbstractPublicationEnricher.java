package psidev.psi.mi.jami.enricher.impl.publication;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.PublicationEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.publication.listener.PublicationEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Publication;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public abstract class AbstractPublicationEnricher
        implements PublicationEnricher{

    public static final int RETRY_COUNT = 5;

    private PublicationEnricherListener listener = null;
    private PublicationFetcher fetcher = null;

    protected Publication publicationFetched = null;

    public AbstractPublicationEnricher(PublicationFetcher fetcher){
        setPublicationFetcher(fetcher);
    }

    public void enrichPublications(Collection<Publication> publicationsToEnrich)
            throws EnricherException{
        for(Publication publicationToEnrich : publicationsToEnrich){
            enrichPublication(publicationToEnrich);
        }
    }

    public void enrichPublication(Publication publicationToEnrich)
            throws EnricherException{

        if( publicationToEnrich == null )
            throw new IllegalArgumentException("Attempted to enrich a null publication.");

        publicationFetched = fetchPublication(publicationToEnrich);
        if(publicationFetched == null){
            if(getPublicationListener() != null)
                getPublicationListener().onPublicationEnriched(
                        publicationToEnrich, EnrichmentStatus.FAILED, "No publication could be found.");
            return;
        }

        processPublication(publicationToEnrich);

        if( getPublicationListener() != null)
            getPublicationListener().onPublicationEnriched(publicationToEnrich , EnrichmentStatus.SUCCESS , null);
    }

    protected abstract void processPublication(Publication publicationToEnrich);

    private Publication fetchPublication(Publication publicationToEnrich) throws EnricherException{
        if(getPublicationFetcher() == null) throw new IllegalStateException("The PublicationEnricher was null.");
        if(publicationToEnrich == null) throw new IllegalArgumentException("Attempted to enrich a null publication.");

        Publication publicationFetched = null;

        if(publicationToEnrich.getPubmedId() != null && publicationToEnrich.getPubmedId().length() > 0){
            try {
                publicationFetched = getPublicationFetcher().getPublicationByPubmedID(publicationToEnrich.getPubmedId());
                if(publicationFetched != null) return publicationFetched;
            } catch (BridgeFailedException e) {
                int index = 0;
                while(index < RETRY_COUNT){
                    try {
                        publicationFetched = getPublicationFetcher().getPublicationByPubmedID(publicationToEnrich.getPubmedId());
                        if(publicationFetched != null) return publicationFetched;
                    } catch (BridgeFailedException ee) {
                        ee.printStackTrace();
                    }
                    index++;
                }
                throw new EnricherException("Re-tried "+RETRY_COUNT+" times", e);
            }
        }

        return publicationFetched;
    }

    public void setPublicationFetcher(PublicationFetcher fetcher){
        this.fetcher = fetcher;
    }
    public PublicationFetcher getPublicationFetcher(){
        return fetcher;
    }

    public void setPublicationListener(PublicationEnricherListener listener){
        this.listener = listener;
    }
    public PublicationEnricherListener getPublicationListener(){
        return listener;
    }
}
