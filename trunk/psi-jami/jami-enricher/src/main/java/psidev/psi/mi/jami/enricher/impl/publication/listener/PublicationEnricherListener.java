package psidev.psi.mi.jami.enricher.impl.publication.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Publication;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public interface PublicationEnricherListener extends EnricherListener {

    public void onPublicationEnriched(
            Publication publication ,
            EnrichmentStatus status ,
            String message);
}
