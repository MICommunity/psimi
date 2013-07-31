package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.publication.listener.PublicationListener;
import psidev.psi.mi.jami.model.Publication;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public interface PublicationEnricher {

    public void enrichPublication(Publication publicationToEnrich) throws EnricherException;
    public void enrichPublications(Collection<Publication> publicationsToEnrich) throws EnricherException;


    public void setPublicationFetcher(PublicationFetcher fetcher);
    public PublicationFetcher getPublicationFetcher();

    public void setPublicationListener(PublicationListener listener);
    public PublicationListener getPublicationListener();
}
