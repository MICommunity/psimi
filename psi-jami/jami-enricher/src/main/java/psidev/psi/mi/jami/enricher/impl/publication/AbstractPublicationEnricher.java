package psidev.psi.mi.jami.enricher.impl.publication;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.PublicationEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.publication.listener.PublicationEnricherListener;
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

    private PublicationEnricherListener listener = null;
    private PublicationFetcher fetcher = null;

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
