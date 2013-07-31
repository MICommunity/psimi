package psidev.psi.mi.jami.enricher.impl.publication.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Publication;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class PublicationEnricherListenerManager
        extends EnricherListenerManager<PublicationEnricherListener>
        implements PublicationEnricherListener{


    public void onPublicationEnriched(Publication publication, EnrichmentStatus status, String message) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onPublicationEnriched(publication, status, message);
        }
    }
}
